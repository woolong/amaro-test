package br.com.jar.amaro_test.bo;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jar.amaro_test.bo.model.Products;
import br.com.jar.amaro_test.to.ProductTO;

/**
 * Encapsulates business logic for Products.
 * 
 * @author raphael
 *
 */
@Service
public class ProductsBusiness {

	@Autowired
	private Products products;

	public List<ProductTO> findAll() {
		return products.findAll();
	}

	public List<ProductTO> findAllWithTagsVector() {
		return products.findAllWithTagsVector();
	}

	public ProductTO findByPk(long id) {
		return products.findByPk(id);
	}

	public List<ProductTO> findSimilarityByTags(ProductTO obj, List<ProductTO> listToCompare) {
		final Predicate<ProductTO> filter = o -> !o.getId().equals(obj.getId());

		return products.findSimilarsByEuclideanDistance(obj, listToCompare, filter);
	}
}
