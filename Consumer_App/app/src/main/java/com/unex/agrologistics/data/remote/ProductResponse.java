package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class ProductResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<ProductResponseItem> message;

    /**
     * Return all the tesults of the response
     * @return A Products list
     */
    public ArrayList<ProductResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the product information
     */
    public void setMessage(ArrayList<ProductResponseItem> message) {
        this.message = message;
    }

    /**
     * Return all the Results of the response
     * @return A Result class
     */
    public Results getResults() { return results; }

    /**
     * Set the results of the response
     * @param results Result class
     */
    @SuppressWarnings("unused")
    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {

        // Results array of ProductResponseItem
        private ArrayList<ProductResponseItem> items;

        /**
         * Return all the ProductResponseItem of the response
         * @return A ArrayList of ProductResponseItem
         */
        @SuppressWarnings("unused")
        public ArrayList<ProductResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (ProductResponseItem) of the results
         * @param items ArrayList of ProductResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<ProductResponseItem> items) {
            this.items = items;
        }
    }
}
