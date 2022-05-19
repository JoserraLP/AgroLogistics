package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "producer_event")

public class ProducerEvent {

    // Producer Event attributes
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("product_id")
    @Expose
    @ColumnInfo(name = "product_id")
    private int product_id;

    @SerializedName("product_name")
    @Expose
    @ColumnInfo(name = "product_name")
    private String product_name;

    @SerializedName("logistic_center_id")
    @Expose
    @ColumnInfo(name = "logistic_center_id")
    private int logistic_center_id;

    @SerializedName("logistic_center_name")
    @Expose
    @ColumnInfo(name = "logistic_center_name")
    private String logistic_center_name;

    @SerializedName("producer_id")
    @Expose
    @ColumnInfo(name = "producer_id")
    private int producer_id;

    @SerializedName("producer_name")
    @Expose
    @ColumnInfo(name = "producer_name")
    private String producer_name;

    @SerializedName("product_category")
    @Expose
    @ColumnInfo(name = "product_category")
    private String product_category;

    @SerializedName("amount_kg")
    @Expose
    @ColumnInfo(name = "amount_kg")
    private double amount_kg;

    @SerializedName("date")
    @Expose
    @ColumnInfo(name = "date")
    private String date;

    @SerializedName("price")
    @Expose
    @ColumnInfo(name = "price")
    private double price;

    @SerializedName("storage_type")
    @Expose
    @ColumnInfo(name = "storage_type")
    private String storage_type;

    /**
     * ProducerEvent initializer
     */
    public ProducerEvent() {
        this.id = -1;
        this.product_id = -1;
        this.product_name = "";
        this.logistic_center_id = -1;
        this.logistic_center_name = "";
        this.producer_id = -1;
        this.producer_name = "";
        this.product_category = "";
        this.amount_kg = -1;
        this.date = "";
        this.price = -1;
        this.storage_type = "";
    }

    /**
     * Producer event constructor
     * @param id Producer event id
     * @param product_id Product id
     * @param product_name Product name                  
     * @param logistic_center_id Logistic center id
     * @param logistic_center_name Logistic center name
     * @param producer_id Producer id
     * @param producer_name Producer name
     * @param product_category Product category
     * @param amount_kg Amount in kg
     * @param date Producer event date
     * @param price Producer event price
     * @param storage_type Producer event storage type
     */
    public ProducerEvent(int id, int product_id, String product_name, int logistic_center_id,
                         String logistic_center_name, int producer_id, String producer_name,
                         String product_category, double amount_kg, String date, double price,
                         String storage_type) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.logistic_center_id = logistic_center_id;
        this.logistic_center_name = logistic_center_name;
        this.producer_id = producer_id;
        this.producer_name = producer_name;
        this.product_category = product_category;
        this.amount_kg = amount_kg;
        this.date = date;
        this.price = price;
        this.storage_type = storage_type;
    }


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
     * Return the product id
     * @return Product id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Set the product id
     * @param product_id Product id
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Return the product name
     * @return Product id
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
    @SuppressWarnings("unused")
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
     * Return the producer id
     * @return Producer id
     */
    public int getProducer_id() {
        return producer_id;
    }

    /**
     * Set the producer id
     * @param producer_id Producer id
     */
    public void setProducer_id(int producer_id) {
        this.producer_id = producer_id;
    }

    /**
     * Return the producer name
     * @return Producer name
     */
    @SuppressWarnings("unused")
    public String getProducer_name() {
        return producer_name;
    }

    /**
     * Set the producer name
     * @param producer_name Producer name
     */
    @SuppressWarnings("unused")
    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
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
