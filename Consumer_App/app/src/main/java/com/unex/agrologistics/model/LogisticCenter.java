package com.unex.agrologistics.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="logistic_center", primaryKeys = {"id", "email"})
public class LogisticCenter {

    // Logistic Center attributes
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("capacity_kg")
    @Expose
    @ColumnInfo(name = "capacity_kg")
    private double capacity_kg;

    @SerializedName("cooled_capacity_kg")
    @Expose
    @ColumnInfo(name = "cooled_capacity_kg")
    private double cooled_capacity_kg;

    @SerializedName("email")
    @Expose
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("password")
    @Expose
    @ColumnInfo(name = "password")
    private byte[] password;

    /**
     * Logistic Center initializer
     */
    public LogisticCenter() {
        this.id = -1;
        this.name = "";
        this.capacity_kg = 0.0d;
        this.cooled_capacity_kg = 0.0d;
        this.email = "";
        this.password = new byte[0];
    }

    /**
     * Logistic center constructor
     * @param id Logistic center id
     * @param name Logistic center name
     * @param capacity_kg Logistic center capacity in KG
     * @param cooled_capacity_kg Logistic center cooled capacity in KG  
     * @param email Logistic center email
     * @param password Logistic center password      
     */
    @Ignore
    public LogisticCenter(int id, String name, double capacity_kg, double cooled_capacity_kg,
                          @NonNull String email, byte[] password) {
        this.id = id;
        this.name = name;
        this.capacity_kg = capacity_kg;
        this.cooled_capacity_kg = cooled_capacity_kg;
        this.email = email;
        this.password = password;
    }

    /**
     * Return the logistic center id
     * @return Logistic center id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the logistic center id
     * @param id Logistic center id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the logistic center name
     * @return Logistic center name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the logistic center name
     * @param name Logistic center name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the logistic center capacity in KG
     * @return Logistic center capacity in KG
     */
    public double getCapacity_kg() {
        return capacity_kg;
    }

    /**
     * Set the logistic center capacity in KG
     * @param capacity_kg Logistic center capacity in KG
     */
    public void setCapacity_kg(double capacity_kg) {
        this.capacity_kg = capacity_kg;
    }

    /**
     * Return the logistic center cooled capacity in KG
     * @return Logistic center cooled capacity in KG
     */
    public double getCooled_capacity_kg() {
        return cooled_capacity_kg;
    }

    /**
     * Set the logistic center capacity in KG
     * @param cooled_capacity_kg Logistic center cooled capacity in KG
     */
    @SuppressWarnings("unused")
    public void setCooled_capacity_kg(double cooled_capacity_kg) {
        this.cooled_capacity_kg = cooled_capacity_kg;
    }

    /**
     * Return the logistic center email
     * @return Logistic center email
     */
    @NonNull
    public String getEmail() {
        return email;
    }

    /**
     * Set the logistic center email
     * @param email Logistic center email
     */
    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    /**
     * Return the logistic center password
     * @return Logistic center password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * Set the logistic center password
     * @param password Logistic center password
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }
}