package com.unex.agrologistics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="consumer")
public class Consumer {

    // Consumer attributes
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("password")
    @Expose
    @ColumnInfo(name = "password")
    private String password;

    /**
     * Consumer initializer
     */
    @SuppressWarnings("unused")
    public Consumer() {
        this.id = -1;
        this.name = "";
        this.email = "";
        this.password = "";
    }

    /**
     * Consumer constructor
     * @param id Consumer id
     * @param name Consumer name
     * @param email Consumer email
     * @param password Consumer password
     */
    @Ignore
    public Consumer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Return the consumer id
     * @return Consumer id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the consumer id
     * @param id Consumer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the consumer name
     * @return Consumer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the consumer name
     * @param name Consumer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the consumer email
     * @return Consumer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the consumer email
     * @param email Consumer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the consumer password
     * @return Consumer password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the consumer password
     * @param password Consumer password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
