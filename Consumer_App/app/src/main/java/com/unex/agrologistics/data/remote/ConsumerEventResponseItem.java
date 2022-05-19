package com.unex.agrologistics.data.remote;

public class ConsumerEventResponseItem {

    // Consumer event attributes
    private int id;

    private int product_id;

    private String product_name;

    private int logistic_center_id;

    private String logistic_center_name;

    private int consumer_id;

    private String consumer_name;

    private String product_category;

    private double amount_kg;

    private String date;

    private double price;

    private String storage_type;

    /**
     * Return the producer event id
     * @return Producer event id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the producer event id
     * @param id Producer event id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the product event id
     * @return Product event id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Set the product event id
     * @param product_id Product event id
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Return the product name
     * @return Product name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * Set the product name
     * @param product_name Product name
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * Return the logistic center id
     * @return Logistic center id
     */
    public int getLogistic_center_id() {
        return logistic_center_id;
    }

    /**
     * Set the logistic center id
     * @param logistic_center_id Logistic center id
     */
    public void setLogistic_center_id(int logistic_center_id) {
        this.logistic_center_id = logistic_center_id;
    }

    /**
     * Return the logistic center name
     * @return Logistic center name
     */
    public String getLogistic_center_name() {
        return logistic_center_name;
    }

    /**
     * Set the logistic center name
     * @param logistic_center_name Logistic center name
     */
    @SuppressWarnings("unused")
    public void setLogistic_center_name(String logistic_center_name) {
        this.logistic_center_name = logistic_center_name;
    }

    /**
     * Return the consumer id
     * @return Consumer id
     */
    public int getConsumer_id() {
        return consumer_id;
    }

    /**
     * Set the consumer id
     * @param consumer_id Consumer id
     */
    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    /**
     * Return the consumer name
     * @return Consumer name
     */
    public String getConsumer_name() {
        return consumer_name;
    }

    /**
     * Set the consumer name
     * @param consumer_name Consumer name
     */
    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    /**
     * Return the product category
     * @return Product category
     */
    public String getProduct_category() {
        return product_category;
    }

    /**
     * Set the product category
     * @param product_category Product category
     */
    @SuppressWarnings("unused")
    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    /**
     * Return the producer event amount in kg
     * @return Producer event amount in kg
     */
    public double getAmount_kg() {
        return amount_kg;
    }

    /**
     * Set the producer event amount in kg
     * @param amount_kg Producer event amount in kg
     */
    public void setAmount_kg(double amount_kg) {
        this.amount_kg = amount_kg;
    }

    /**
     * Return the producer event date
     * @return Producer event date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the producer event date
     * @param date Producer event date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Return the product price
     * @return Product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the product price
     * @param price Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the producer event storage type
     * @return Producer event storage type
     */
    public String getStorage_type() {
        return storage_type;
    }

    /**
     * Set the producer event storage type
     * @param storage_type Producer event storage type
     */
    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }
}
