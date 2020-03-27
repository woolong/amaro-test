package br.com.jar.amaro_test.to;

public class ProductTO extends TaggableTransferObject<ProductTO> {

    private final Long id;
    private String name;

    public ProductTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

