package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="estimated_stock",
        primaryKeys = { "id", "product_id", "logistic_center_id"},
        foreignKeys = {
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id"),
        @ForeignKey(entity = LogisticCenter.class, parentColumns = "id", childColumns = "logistic_center_id")})

public class EstimatedStock {

    // Estimated Stock attributes
    @SerializedName("id")
    @Expose
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
     * EstimatedStock initializer
     */
    public EstimatedStock() {
        this.id = -1;
        this.product_id = -1;
        this.logistic_center_id = -1;
        this.product_category = "";
        this.amount_kg = 0.0d;
        this.date = "";
    }

    /**
     * Estimated Stock constructor
     * @param id Estimated stock id
     * @param product_id Product id
     * @param logistic_center_id Logistic center id
     * @param product_category Product category
     * @param amount_kg Amount of product
     * @param date Date of the stock
     */
    @Ignore
    public EstimatedStock(int id, int product_id, int logistic_center_id, String product_category, 
                          double amount_kg, String date) {
        this.id = id;
        this.product_id = product_id;
        this.logistic_center_id = logistic_center_id;
        this.product_category = product_category;
        this.amount_kg = amount_kg;
        this.date = date;
    }

    /**
     * Return the estimated stock id
     * @return Estimated stock id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the estimated stock id
     * @param id Estimated Stock id
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
     * Return the estimated stock amount kg
     * @return Estimated stock amount kg
     */
    public double getAmount_kg() {
        return amount_kg;
    }

    /**
     * Set the estimated stock amount kg
     * @param amount_kg Estimated stock amount kg
     */
    public void setAmount_kg(double amount_kg) {
        this.amount_kg = amount_kg;
    }

    /**
     * Return the estimated stock date
     * @return Estimated stock date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the estimated stock date
     * @param date Estimated stock date
     */
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EstimatedStock{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", logistic_center_id=" + logistic_center_id +
                ", product_category='" + product_category + '\'' +
                ", amount_kg=" + amount_kg +
                ", date='" + date + '\'' +
                '}';
    }
}