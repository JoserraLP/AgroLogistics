package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="consumer_transaction",foreignKeys = {
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id"),
        @ForeignKey(entity = Consumer.class, parentColumns = "id", childColumns = "consumer_id"),
        @ForeignKey(entity = LogisticCenter.class, parentColumns = "id",
                childColumns = "logistic_center_id")})

public class ConsumerTransaction {

    // Consumer Transaction attributes
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("product_id")
    @Expose
    @ColumnInfo(name = "product_id")
    private int product_id;

    @SerializedName("logistic_center_id")
    @Expose
    @ColumnInfo(name = "logistic_center_id")
    private int logistic_center_id;

    @SerializedName("consumer_id")
    @Expose
    @ColumnInfo(name = "consumer_id")
    private int consumer_id;

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
     * ConsumerTransaction initializer
     */
    @SuppressWarnings("unused")
    public ConsumerTransaction() {
        this.id = -1;
        this.product_id = -1;
        this.logistic_center_id = -1;
        this.consumer_id = -1;
        this.product_category = "";
        this.amount_kg = 0.0d;
        this.date = "";
        this.price = 0.0d;
        this.storage_type = "";
    }

    /**
     * Consumer transaction constructor
     * @param id Consumer transaction id
     * @param product_id Product id
     * @param logistic_center_id Logistic center id
     * @param consumer_id Consumer id
     * @param product_category Product category
     * @param amount_kg Amount in kg
     * @param date Consumer transaction date
     * @param price Consumer transaction price
     * @param storage_type Consumer transaction storage type
     */
    @Ignore
    public ConsumerTransaction(int id, int product_id, int logistic_center_id, int consumer_id,
                               String product_category, double amount_kg, String date, double price,
                               String storage_type) {
        this.id = id;
        this.product_id = product_id;
        this.logistic_center_id = logistic_center_id;
        this.consumer_id = consumer_id;
        this.product_category = product_category;
        this.amount_kg = amount_kg;
        this.date = date;
        this.price = price;
        this.storage_type = storage_type;
    }

    /**
     * Return the consumer transaction id
     * @return Consumer transaction id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the consumer transaction id
     * @param id Consumer transaction id
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
     * Return the consumer transaction amount in kg
     * @return Consumer transaction amount in kg
     */
    public double getAmount_kg() {
        return amount_kg;
    }

    /**
     * Set the consumer transaction amount in kg
     * @param amount_kg Consumer transaction amount in kg
     */
    public void setAmount_kg(double amount_kg) {
        this.amount_kg = amount_kg;
    }

    /**
     * Return the consumer transaction date
     * @return Consumer transaction date
     */
    public String getDate() {
        return date;
    }
    /**
     * Set the consumer transaction date
     * @param date Consumer transaction date
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
     * Return the consumer transaction storage type
     * @return Consumer transaction storage type
     */
    public String getStorage_type() {
        return storage_type;
    }

    /**
     * Set the consumer transaction storage type
     * @param storage_type Consumer transaction storage type
     */
    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }
}
