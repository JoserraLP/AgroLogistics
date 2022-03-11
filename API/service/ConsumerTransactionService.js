'use strict';

const connection = require("../config/config");

/**
 * Delete consumer transaction
 * Delete consumer transaction
 *
 * consumerTransactionID Integer Consumer transaction ID

 * returns String
 **/
module.exports.deleteConsumerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM consumer_transaction WHERE id = " + req.consumerTransactionID.originalValue

    // Execute query
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Returns all the consumer transaction
 * Returns all the consumer transaction
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getConsumerTransactions = function(req, res, next) {
    //Parameters
    console.log(req);

    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;

    var query = "";

    if (day != undefined && month != undefined && year != undefined){
        query = "SELECT * FROM consumer_transaction WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day;
    } else if (month != undefined && year != undefined){
        query = "SELECT * FROM consumer_transaction WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month;
    } else if (year != undefined){
        query = "SELECT * FROM consumer_transaction WHERE YEAR(date) = '" + year + "'";
    } else {
        query = "SELECT * FROM consumer_transaction";
    }

    if (query){
        // Execute query
        connection.query(query, function (error, results, fields) {
            if (error) throw error;
            console.log('The solution is: ', results);
            res.send({
                message: results
            });
        });
    }
};


/**
 * Creates new consumer transaction
 * Creates new consumer transaction
 *
 * consumerTransaction ConsumerTransaction 

 * returns String
 **/
module.exports.postConsumerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'INSERT INTO consumer_transaction SET ?'

    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        consumer_id: req.undefined.originalValue.consumer_id,
        product_category: req.undefined.originalValue.product_category,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date,
        price: req.undefined.originalValue.price,
        storage_type: req.undefined.originalValue.storage_type
    }

    // Execute query
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Modifies a consumer transaction
 * Modifies a consumer transaction
 *
 * consumerTransaction ConsumerTransaction 

 * returns String
 **/
module.exports.putConsumerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE consumer_transaction SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        consumer_id: req.undefined.originalValue.consumer_id,
        product_category: req.undefined.originalValue.product_category,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date,
        price: req.undefined.originalValue.price,
        storage_type: req.undefined.originalValue.storage_type
    }

    // Execute query
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};




