package com.unex.agrologistics.data.remote;

import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("message")
    private MessageInfo message;

    /**
     * Return all the results of the response
     * @return A LogisticCenters list
     */
    public MessageInfo getMessage() {
        return message;
    }

    /**
     * Set the message of the response
     * @param message Message with the message response information
     */
    public void setMessage(MessageInfo message) {
        this.message = message;
    }

    private static class MessageInfo {

        @SerializedName("affectedRows")
        public int affectedRows;

        @SerializedName("insertId")
        public int insertId;

        /**
         * Return the number of affected rows
         * @return Number of affected rows
         */
        @SuppressWarnings("unused")
        public int getAffectedRows() {
            return affectedRows;
        }

        /**
         * Set the number of affected rows
         * @param affectedRows Number of affected rows
         */
        @SuppressWarnings("unused")
        public void setAffectedRows(int affectedRows) {
            this.affectedRows = affectedRows;
        }

        /**
         * Return the inserted id
         * @return Inserted id
         */
        public int getInsertId() {
            return insertId;
        }

        /**
         * Set the inserted id
         * @param insertId New item  inserted id
         */
        @SuppressWarnings("unused")
        public void setInsertId(int insertId) {
            this.insertId = insertId;
        }
    }
}