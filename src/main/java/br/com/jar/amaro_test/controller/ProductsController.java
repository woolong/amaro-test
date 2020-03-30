package br.com.jar.amaro_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jar.amaro_test.bo.ProductsBusiness;
import br.com.jar.amaro_test.to.ProductTO;

@RestController
public class ProductsController {

	private static final String BASE_URL = "/products";
	public static final String URL_FINDALL = BASE_URL;
	public static final String URL_FINDBYPK = BASE_URL + "/{id}";
	public static final String URL_TAGSVECTOR = BASE_URL + "/tagsVector";
	public static final String URL_SIMILARITY_BY_TAGS = BASE_URL + "/{id}/similarity/tags";

	@Autowired
	private ProductsBusiness productsBusiness;

	@GetMapping(BASE_URL)
	public List<ProductTO> all() {
		return productsBusiness.findAll();
	}

	@GetMapping(URL_TAGSVECTOR)
	public List<ProductTO> allTagsVector() {
		return productsBusiness.findAllWithTagsVector();
	}

	@GetMapping(URL_FINDBYPK)
	public ProductTO all(@PathVariable long id) {
		return productsBusiness.findByPk(id);
	}

	@PostMapping(URL_SIMILARITY_BY_TAGS)
	public List<ProductTO> similarityTags(@PathVariable long id, @RequestBody List<ProductTO> otherProducts) {
		final ProductTO obj = productsBusiness.findByPk(id);
		if (obj == null) {
			return null;
		}
		return productsBusiness.findSimilarityByTags(obj, otherProducts);
	}
}
