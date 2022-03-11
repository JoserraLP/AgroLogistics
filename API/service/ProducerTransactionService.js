'use strict';

const connection = require("../config/config");

/**
 * Delete producer transaction
 * Delete producer transaction
 *
 * producerTransactionID Integer Producer transaction ID

 * returns String
 **/
module.exports.deleteProducerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = "DELETE FROM producer_transaction WHERE id = " + req.producerTransactionID.originalValue

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
 * Returns all the producer transaction
 * Returns all the producer transaction
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getProducerTransactions = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;

    var query = "";

    if (day != undefined && month != undefined && year != undefined){
        query = "SELECT * FROM producer_transaction WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day;
    } else if (month != undefined && year != undefined){
        query = "SELECT * FROM producer_transaction WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month;
    } else if (year != undefined){
        query = "SELECT * FROM producer_transaction WHERE YEAR(date) = '" + year + "'";
    } else {
        query = "SELECT * FROM producer_transaction";
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
 * Creates new producer transaction
 * Creates new producer transaction
 *
 * producerTransaction ProducerTransaction 

 * returns String
 **/
module.exports.postProducerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'INSERT INTO producer_transaction SET ?'

    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        producer_id: req.undefined.originalValue.producer_id,
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
 * Modifies a producer transaction
 * Modifies a producer transaction
 *
 * producerTransaction ProducerTransaction 

 * returns String
 **/
module.exports.putProducerTransaction = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'UPDATE producer_transaction SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        producer_id: req.undefined.originalValue.producer_id,
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




