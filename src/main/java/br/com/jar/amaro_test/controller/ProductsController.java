package br.com.jar.amaro_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.jar.amaro_test.bo.ProductsBusiness;
import br.com.jar.amaro_test.to.ProductTO;

@RestController
public class ProductsController {

	public static final String BASE_URL = "products";

	@Autowired
	private ProductsBusiness productsBusiness;

	@GetMapping(BASE_URL)
	public List<ProductTO> all() {
		return productsBusiness.findAll();
	}

	@GetMapping(BASE_URL + "/{id}")
	public ProductTO all(@PathVariable long id) {
		return productsBusiness.findByPk(id);
	}

}
