package br.com.jar.amaro_test.bo.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.jar.amaro_test.to.TaggableTransferObject;
import br.com.jar.amaro_test.to.Tuple;

public class TaggableModel<O extends TaggableTransferObject<?>> {

	public enum Tags {
		neutro, veludo, couro, basics, festa, workwear, inverno, boho, estampas, balada, colorido, casual, liso, moderno, passeio, metal, viagem, delicado, descolado, elastano,
	}

//	private static final Map<String, Integer> TAGS_BY_INDEX;
//	static {
//		TAGS_BY_INDEX = Stream.of(Tags.values()) //
//				.collect(Collectors.toUnmodifiableMap(tag -> {
//					return tag.name();
//				}, tag -> {
//					return tag.ordinal();
//				}));
//	}

	protected O addRandomTags(O obj) {
		int maxTags = Tags.values().length;
		final Random r = new Random();
		int numberOfNewTags = r.nextInt(maxTags);

		for (int i = 0; i < numberOfNewTags; i++) {
			obj.addTag(Tags.values()[r.nextInt(maxTags)].name());
		}

		return obj;
	}

	public List<Integer> findAllTagsVector(O obj) {
		List<String> tags = obj.getTags();
		return Stream//
				.of(Tags.values()) //
				.map(tag -> {
					return tags.contains(tag.name())
													 ? 1
													 : 0;
				}) //
				.collect(Collectors.toList());
	}

	/**
	 * This method calculates the similarity between the given `obj` and all items inside `listToCompare`, comparing
	 * their `tagsVector` attribute by applying the following formula:
	 * 
	 * <pre>
	 * Similarity = 1/(1 + Distance), where
	 * Distance = sqrt((v1[0] - v2[0])^2 + (v1[1] - v2[1])^2 + .. + (v1[N-1] - v2[N-1])^2)
	 * </pre>
	 * 
	 * After that, return the 3 most similars objects by the above criteria.
	 * 
	 * @param obj
	 * @param listToCompare
	 * @return the 3 most closest objects by the above criteria
	 */
	public List<O> findSimilarsByEuclideanDistance(O obj, List<O> listToCompare, Predicate<O> filter) {

		final List<O> similarities = listToCompare //
				.parallelStream() //
//				.stream() //
				.filter(filter) //
				.map(obj2 -> {
					final Optional<Integer> reduce = Arrays //
//							.asList(new Tags[] { Tags.neutro, Tags.veludo, Tags.couro, Tags.basics }) //
							.asList(Tags.values()) //
							.parallelStream() //
//							.stream() //
//							.map(tag -> {
//								System.out.println("COMPARANDO: " + tag);
//								return tag;
//							}) //
							.map(tag -> {
								final Integer tagOrigin = obj.getTagsVector().get(tag.ordinal());
								final Integer tagCompare = obj2.getTagsVector().get(tag.ordinal());
								final int log = (int) Math.pow((tagOrigin - tagCompare), 2);
//								System.out.println("\t IDX[" + tag.ordinal() + "](" + tagOrigin + "-" + tagCompare + ")^2 = " + log);
								return log;
							}) //
							.reduce(new BinaryOperator<Integer>() {
								@Override
								public Integer apply(Integer t, Integer u) {
									final int partial_distance = t + u;
//									System.out.println(String.format("\nREDUCING: %s - %s = %s", t, u, partial_distance));
									return partial_distance;
								}
							});

					final Integer vector_sum = reduce.get();
					final double distance = Math.sqrt(vector_sum);
					final double similarity = (1d / (1d + distance));
//					System.out.println("Produto: " + obj2 + "\n\tdistancia: " + distance + "\n\tsimilarity(" + similarity + ")");
					return new Tuple<O, Double>(obj2, similarity);
				}) //
				.sorted(new Comparator<Tuple<O, Double>>() {

					@Override
					public int compare(Tuple<O, Double> o1, Tuple<O, Double> o2) {
						return o2.getSecond().compareTo(o1.getSecond());
					}
				}) //
				.limit(3) //
				.map(tuple -> {
					return tuple.getFirst();
				}) //
				.collect(Collectors.toList());

		return similarities;
	}
}
