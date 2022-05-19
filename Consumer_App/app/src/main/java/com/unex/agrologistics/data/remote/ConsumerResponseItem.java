package com.unex.agrologistics.data.remote;

public class ConsumerResponseItem {

    // Consumer attributes
    private int id;

    private String name;

    private String email;

    private String password;

    /**
     * Return the producer id
     * @return Producer id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the producer id
     * @param id Producer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the producer name
     * @return Producer name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the producer name
     * @param name Producer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the producer email
     * @return Producer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the producer email
     * @param email Producer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the producer password
     * @return Producer password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the producer password
     * @param password Producer password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
