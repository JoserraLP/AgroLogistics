package com.unex.agrologistics.data.remote;

public class LogisticCenterResponseItem {

    // Logistic Center attributes
    private int id;

    private String name;

    private double capacity_kg;

    private double cooled_capacity_kg;

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
    public double getCapacityKg() {
        return capacity_kg;
    }

    /**
     * Set the logistic center capacity in KG
     * @param capacity_kg Logistic center capacity in KG
     */
    @SuppressWarnings("unused")
    public void setCapacityKg(double capacity_kg) {
        this.capacity_kg = capacity_kg;
    }

    /**
     * Return the logistic center cooled capacity in KG
     * @return Logistic center cooled capacity in KG
     */
    public double getCooledCapacityKg() {
        return cooled_capacity_kg;
    }

    /**
     * Set the logistic center capacity in KG
     * @param cooled_capacity_kg Logistic center cooled capacity in KG
     */
    @SuppressWarnings("unused")
    public void setCooledCapacityKg(double cooled_capacity_kg) {
        this.cooled_capacity_kg = cooled_capacity_kg;
    }
}
