package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="product")
public class Product {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    /**
     * Product initializer
     */
    public Product() {
        this.id = -1;
        this.name = "";
    }

    /**
     * Product constructor
     * @param id Product id
     * @param name Product name
     */
    @Ignore
    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
