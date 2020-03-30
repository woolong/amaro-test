package br.com.jar.amaro_test.bo.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jar.amaro_test.to.ProductTO;

/**
 * Encapsulates access to {@link ProductTO} instances.
 * 
 * @author raphael
 *
 */
@Service
public class Products extends TaggableModel<ProductTO> {

	private static Map<Long, ProductTO> ALL;
	static {
		try {
			ALL = new HashMap<Long, ProductTO>();

			final File products = ResourceUtils.getFile("classpath:static/products.json");
			final ProductTO[] allProducts = new ObjectMapper().readValue(products, ProductTO[].class);
			for (ProductTO to : allProducts) {
				ALL.put(to.getId(), to);
			}
		} catch (Exception e) {
			throw new Error("Product list could not be imported!", e);
		}
	}

	public List<ProductTO> findAll() {
		return List.copyOf(ALL.values());
	}

	public List<ProductTO> findAllWithTagsVector() {
		return this.findAll().stream() //
				.map(p -> {
					p.setTagsVector(this.findAllTagsVector(p));
//					System.out.println("Product: " + p);
					return p;
				}) //
				.collect(Collectors.toList());
	}

	public ProductTO findByPk(long id) {
		return ALL.get(id);
	}

}
