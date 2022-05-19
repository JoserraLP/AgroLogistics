package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="actual_stock",foreignKeys = {
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id"),
        @ForeignKey(entity = LogisticCenter.class, parentColumns = "id",
                childColumns = "logistic_center_id")})

public class ActualStock {

    // Actual Stock attributes
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

    /**
     * Actual Stock initializer
     */
    @SuppressWarnings("unused")
    public ActualStock() {
        this.id = -1;
        this.product_id = -1;
        this.logistic_center_id = -1;
        this.product_category = "";
        this.amount_kg = 0.0d;
        this.date = "";
        this.date = "";
    }

    /**
     * Actual Stock constructor
     * @param id Actual stock id
     * @param product_id Product id
     * @param logistic_center_id Logistic center id
     * @param product_category Product category
     * @param amount_kg Amount of product
     * @param date Date of the stock
     */
    @Ignore
    public ActualStock(int id, int product_id, int logistic_center_id, String product_category,
                       double amount_kg, String date) {
        this.id = id;
        this.product_id = product_id;
        this.logistic_center_id = logistic_center_id;
        this.product_category = product_category;
        this.amount_kg = amount_kg;
        this.date = date;
    }

    /**
     * Return the actual stock id
     * @return Actual stock id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the actual stock id
     * @param id Actual Stock id
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
     * Return the actual stock amount kg
     * @return Actual stock amount kg
     */
    public double getAmount_kg() {
        return amount_kg;
    }

    /**
     * Set the actual stock amount kg
     * @param amount_kg Actual stock amount kg
     */
    public void setAmount_kg(double amount_kg) {
        this.amount_kg = amount_kg;
    }

    /**
     * Return the actual stock date
     * @return Actual stock date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the actual stock date
     * @param date Actual stock date
     */
    public void setDate(String date) {
        this.date = date;
    }
}
