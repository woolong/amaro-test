package br.com.jar.amaro_test.to;

import java.util.ArrayList;
import java.util.List;

public abstract class TaggableTransferObject<T extends TaggableTransferObject<?>> {

	private List<String> tags;
	private List<Integer> tagsVector;
	private Double similarity;

	public TaggableTransferObject() {
		super();
	}

	@SuppressWarnings("unchecked")
	public T addTag(String tag) {
		if (this.tags == null) {
			this.tags = new ArrayList<String>();
		}

		this.tags.add(tag);

		return (T) this;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<Integer> getTagsVector() {
		return tagsVector;
	}

	public void setTagsVector(List<Integer> tagsVector) {
		this.tagsVector = tagsVector;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

}
