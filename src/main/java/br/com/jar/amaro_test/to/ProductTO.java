package br.com.jar.amaro_test.to;

public class ProductTO extends TaggableTransferObject<ProductTO> {

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
