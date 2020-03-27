package br.com.jar.amaro_test.bo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jar.amaro_test.to.ProductTO;

@Service
public class ProductsBusiness {

	public List<ProductTO> findAll() {
		final List<String> names = Arrays.asList("Produto 1", "Produto 2");
		final AtomicLong counter = new AtomicLong();

		final List<ProductTO> tos = names.stream().map(name -> {
			return new ProductTO(counter.incrementAndGet(), name).addTag("nice").addTag("vintage");
		}).collect(Collectors.toList());
		return tos;
	}

	public ProductTO findByPk(long id) {
		return new ProductTO(id, "Produto");
	}
}
