package com.unex.agrologistics.data.remote;

public class ProductResponseItem {

    // Product attributes
    private int id;

    private String name;

    /**
     * Return the product id
     * @return Product id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the product id
     * @param id Product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the product name
     * @return Product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product name
     * @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }
}
