package br.com.jar.amaro_test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private static final String BASE_URL;
	static {
		String base = ProductsController.BASE_URL;
		base = base.startsWith("/")
									? base
									: "/" + base;
		base = base.endsWith("/")
								  ? base
								  : base + "/";
		BASE_URL = base;
	}

//	@Test
	public void all_statusOK() throws Exception {
		this.mockMvc.perform(get(BASE_URL)).andDo(print()).andExpect(status().isOk());
	}

//	@Test
	public void allTagsVector_statusOK() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "tagsVector")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void similarity_statusOK() throws Exception {
		final String allTagsVector = this.mockMvc.perform(get(BASE_URL + "tagsVector")).andReturn().getResponse().getContentAsString();
		final MvcResult result = this.mockMvc //
				.perform(post(BASE_URL + 7533 + "/similarity/tags").contentType(MediaType.APPLICATION_JSON).content(allTagsVector)) //
//				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

//	@Test
//	public void test() throws Exception {
//		final List<ProductTO> listToCompare = new ArrayList<ProductTO>();
//		final ProductTO obj = new ProductTO(1L, "Produto a comparar");
//		obj.setTagsVector(Arrays.asList(new Integer[] { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//
////		for (int i = 0; i < 5; i++) {
////			final ProductTO item = new ProductTO(Long.getLong(String.valueOf(1 * i)), "Prd" + i);
////			item.setTagsVector(Arrays.asList(new Integer[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
////			listToCompare.add(item);
////		}
//
//		listToCompare.add(createProduto(new Integer[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//		listToCompare.add(createProduto(new Integer[] { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//		listToCompare.add(createProduto(new Integer[] { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//		listToCompare.add(createProduto(new Integer[] { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//		listToCompare.add(createProduto(new Integer[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//		listToCompare.add(createProduto(new Integer[] { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
//
//		final List<Tuple<ProductTO, Double>> similarities = listToCompare //
//				.parallelStream() //
////				.stream() //
//				.map(obj2 -> {
//					final Optional<Integer> reduce = Arrays //
////							.asList(new Tags[] { Tags.neutro, Tags.veludo, Tags.couro, Tags.basics }) //
//							.asList(Tags.values()) //
//							.parallelStream() //
////							.stream() //
//							.map(tag -> {
////								System.out.println("COMPARANDO: " + tag);
//								return tag;
//							}) //
//							.map(tag -> {
//								final Integer tagOrigin = obj.getTagsVector().get(tag.ordinal());
//								final Integer tagCompare = obj2.getTagsVector().get(tag.ordinal());
//								final int log = (int) Math.pow((tagOrigin - tagCompare), 2);
////								System.out.println("\t IDX[" + tag.ordinal() + "](" + tagOrigin + "-" + tagCompare + ")^2 = " + log);
//								return log;
//							}) //
//							.reduce(new BinaryOperator<Integer>() {
//								@Override
//								public Integer apply(Integer t, Integer u) {
//									final int partial_distance = t + u;
////									System.out.println(String.format("\nREDUCING: %s - %s = %s", t, u, partial_distance));
//									return partial_distance;
//								}
//							});
//
//					final Integer vector_sum = reduce.get();
//					final double distance = Math.sqrt(vector_sum);
//					final double similarity = (1d / (1d + distance));
//					System.out.println("Produto: " + obj2.getName() + "\n\tdistancia: " + distance + "\n\tsimilarity(" + similarity + ")");
//					return new Tuple<ProductTO, Double>(obj2, similarity);
//				}) //
//				.sorted(new Comparator<Tuple<ProductTO, Double>>() {
//
//					@Override
//					public int compare(Tuple<ProductTO, Double> o1, Tuple<ProductTO, Double> o2) {
//						return o2.getSecond().compareTo(o1.getSecond());
//					}
//				}) //
//				.limit(3) //
//				.collect(Collectors.toList());
//
//		for (Tuple<ProductTO, Double> tuple : similarities) {
//			System.out.println(String.format("Similaridade Produto[%s] %s", tuple.getFirst().getName(), tuple.getSecond()));
//		}
//
//	}
//
//	private static final AtomicLong COUNTER = new AtomicLong();
//
//	private ProductTO createProduto(Integer[] integers) {
//		final long id = COUNTER.incrementAndGet();
//		final ProductTO item = new ProductTO(id, "Prd " + id);
//		item.setTagsVector(Arrays.asList(integers));
//		return item;
//	}

}
