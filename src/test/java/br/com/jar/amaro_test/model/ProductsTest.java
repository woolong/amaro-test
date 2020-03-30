package br.com.jar.amaro_test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.jar.amaro_test.bo.model.Products;
import br.com.jar.amaro_test.bo.model.TagsEnum;
import br.com.jar.amaro_test.to.ProductTO;

@SpringBootTest
public class ProductsTest {

	@MockBean
	private Products products;

	private When _when;

	@BeforeEach
	public void beforeAll() {
		this._when = new When();
	}

	@Test
	public void testFindAllTagsVector__checkRealImpl() throws Exception {
		_when.defaultTOForTagsVector().findAllTagsVectorAsIs();

		final ProductTO to = products.findByPk(1l);
		final List<Integer> tagsVector = products.findAllTagsVector(to);
		assertEquals(TagsEnum.values().length, tagsVector.size());

		for (String tagName : to.getTags()) {
			assertEquals(1, tagsVector.get(TagsEnum.valueOf(tagName).ordinal()));
		}
	}

	@Test
	public void testFindSimilarsByEuclideanDistance__checkRealImpl() throws Exception {

		_when.defaultTO().compareTo().similarAsIs();

		final ProductTO to = products.findByPk(1l);
		assertNotNull(to);

		final List<ProductTO> listToCompare = products.findAllWithTagsVector();
		assertNotNull(listToCompare);

		final Predicate<ProductTO> filter = o -> !o.getId().equals(to.getId());
		final List<ProductTO> similars = products.findSimilarsByEuclideanDistance(to, listToCompare, filter);
//		similars.stream().forEach(System.out::println);
		assertNotNull(similars);
		assertEquals(3, similars.size());

		final Long[] expectedIds = new Long[] { 30l, 40l, 50l };
		similars.forEach(p -> {
			for (Long id : expectedIds) {
				if (id.equals(p.getId())) {
					return;
				}
			}
			fail(String.format("Product id %s was expected!", p.getId()));
		});
	}

	private class Given {

		protected ProductTO getProductOne() {
			return createTO(1l, "Product to Compare", new Integer[] { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		}

		protected List<ProductTO> getAllProducts() {
			final List<ProductTO> all = new ArrayList<ProductTO>();
			all.add(getProductOne());
			all.add(createTO(20, null, new Integer[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			all.add(createTO(30, null, new Integer[] { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			all.add(createTO(40, null, new Integer[] { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			all.add(createTO(50, null, new Integer[] { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			all.add(createTO(60, null, new Integer[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			all.add(createTO(70, null, new Integer[] { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
			return all;
		}

		protected ProductTO getProductOnlyTagNames() {
			final ProductTO to = getProductOne();
			to.setTagsVector(null);

			to.addTag(TagsEnum.basics.name()) //
					.addTag(TagsEnum.descolado.name()) //
					.addTag(TagsEnum.viagem.name());

			return to;
		}

		private ProductTO createTO(long id, String name, Integer[] integers) {
			final ProductTO item = new ProductTO();
			item.setId(id);
			item.setName((name != null)
										? name
										: "Prdct " + id);

			item.setTagsVector(Arrays.asList(integers));
			return item;
		}
	}

	private class When {

		private Given _given = new Given();

		public When defaultTO() {
			when(products.findByPk(Mockito.eq(1l))).thenReturn(_given.getProductOne());
			return this;
		}

		public When defaultTOForTagsVector() {
			final ProductTO to = _given.getProductOnlyTagNames();
			when(products.findByPk(Mockito.anyLong())).thenReturn(to);
			return this;
		}

		public When findAllTagsVectorAsIs() {
			when(products.findAllTagsVector(any())).thenCallRealMethod();
			return this;
		}

		public When similarAsIs() {
			when(products.findSimilarsByEuclideanDistance(any(), any(), any())).thenCallRealMethod();
			return this;
		}

		public When compareTo() {
			when(products.findAllWithTagsVector()).thenReturn(_given.getAllProducts());
			return this;
		}

	}

}
