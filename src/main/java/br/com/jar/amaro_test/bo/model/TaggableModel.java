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

/**
 * Class Abstraction for {@link TaggableTransferObject} default manipulations, such as to construct a vector from tags
 * and
 * to check similarity between taggableTOs.
 * 
 * @author raphael
 *
 * @param <O>
 *            TaggableTransferObject implementation
 */
public class TaggableModel<O extends TaggableTransferObject<?>> {

	protected O addRandomTags(O obj) {
		int maxTags = TagsEnum.values().length;
		final Random r = new Random();
		int numberOfNewTags = r.nextInt(maxTags);

		for (int i = 0; i < numberOfNewTags; i++) {
			obj.addTag(TagsEnum.values()[r.nextInt(maxTags)].name());
		}

		return obj;
	}

	/**
	 * Given obj, creates an binary vector (in ways of List<Integer>) giving 1 or 0 as value by the enum.ordinal.
	 * 
	 * @param obj
	 * @return
	 */
	public List<Integer> findAllTagsVector(O obj) {
		final List<String> tags = obj.getTags();
		return Stream//
				.of(TagsEnum.values()) //
				.map(tag -> {
					return tags.contains(tag.name())
													 ? 1
													 : 0;
				}) //
				.collect(Collectors.toList());
	}

	/**
	 * This method calculates the similarity between the given `to` and all items inside `listToCompare`, comparing
	 * their `tagsVector` attribute by applying the following formula:
	 * 
	 * <pre>
	 * Distance = sqrt((v1[0] - v2[0])^2 + (v1[1] - v2[1])^2 + .. + (v1[N-1] - v2[N-1])^2)
	 * Similarity = 1/(1 + Distance)
	 * </pre>
	 * 
	 * After that, return the 3 most similars objects by the above criteria.
	 * 
	 * @param to
	 * @param listToCompare
	 * @return the 3 most closest objects by the above criteria
	 */
	public List<O> findSimilarsByEuclideanDistance(O to, List<O> listToCompare, Predicate<O> filter) {

		final List<O> similarities = listToCompare //
				.parallelStream() //
				.filter(filter) //
				.map(obj2 -> {
					final Optional<Integer> reduce = Arrays //
							.asList(TagsEnum.values()) //
							.parallelStream() //
							.map(tag -> {
								final Integer tagOrigin = to.getTagsVector().get(tag.ordinal());
								final Integer tagCompare = obj2.getTagsVector().get(tag.ordinal());
								final int log = (int) Math.pow((tagOrigin - tagCompare), 2);
								return log;
							}) //
							.reduce(new BinaryOperator<Integer>() {
								@Override
								public Integer apply(Integer t, Integer u) {
									final int partial_distance = t + u;
									return partial_distance;
								}
							});

					final Integer vector_sum = reduce.get();
					final double distance = Math.sqrt(vector_sum);
					final double similarity = (1d / (1d + distance));
					obj2.setSimilarity(similarity);
					return obj2;
				}) //
				.sorted(new Comparator<O>() {
					@Override
					public int compare(O o1, O o2) {
						return o2.getSimilarity().compareTo(o1.getSimilarity());
					}
				}) //
				.limit(3) //
				.collect(Collectors.toList());

		return similarities;
	}
}
