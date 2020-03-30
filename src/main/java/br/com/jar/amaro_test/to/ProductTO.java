package br.com.jar.amaro_test.to;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTO extends TaggableTransferObject<ProductTO> implements Cloneable {

	private Long id;
	private String name;

	public ProductTO() {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", getTags()=");
		builder.append(getTags());
		builder.append(", getTagsVector()=");
		builder.append(getTagsVector());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final TaggableTransferObject<?> father = (TaggableTransferObject<?>) super.clone();
		final ProductTO clone = new ProductTO();
		clone.setTags(father.getTags());
		clone.setTagsVector(father.getTagsVector());
		clone.id = this.id;
		clone.name = this.name;

		return father;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
