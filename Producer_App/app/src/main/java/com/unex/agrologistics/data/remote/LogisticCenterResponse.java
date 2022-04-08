package com.unex.agrologistics.data.remote;

import java.util.ArrayList;

public class LogisticCenterResponse {

    // Results field
    private Results results;

    // Message field
    private ArrayList<LogisticCenterResponseItem> message;

    /**
     * Return all the results of the response
     * @return A LogisticCenters list
     */
    public ArrayList<LogisticCenterResponseItem> getMessage() { return message; }

    /**
     * Set the message of the response
     * @param message Message with the Logistic center information
     */
    public void setMessage(ArrayList<LogisticCenterResponseItem> message) {
        this.message = message;
    }

    /**
     * Return all the results of the response
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

        // Results array of LogisticCenterResponseItem
        private ArrayList<LogisticCenterResponseItem> items;

        /**
         * Return all the LogisticCenterResponseItem of the response
         * @return A ArrayList of LogisticCenterResponseItem
         */
        @SuppressWarnings("unused")
        public ArrayList<LogisticCenterResponseItem> getItems() {
            return items;
        }

        /**
         * Set the items (LogisticCenterResponseItem) of the results
         * @param items ArrayList of LogisticCenterResponseItem
         */
        @SuppressWarnings("unused")
        public void setItems(ArrayList<LogisticCenterResponseItem> items) {
            this.items = items;
        }
    }
}
