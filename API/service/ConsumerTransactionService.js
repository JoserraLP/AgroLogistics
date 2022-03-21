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
    var id = req.id.originalValue;
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;
    var logistic_center_id = req.logistic_center_id.originalValue;
    var product_id = req.product_id.originalValue;
    var consumer_id = req.consumer_id.originalValue;

    var query =  "SELECT consumer_transaction.id, consumer_transaction.product_id, product.name AS product_name, consumer_transaction.logistic_center_id, "
    + "logistic_center.name AS logistic_center_name, consumer_transaction.consumer_id, consumer.name AS consumer_name, consumer_transaction.product_category, "
    + "consumer_transaction.amount_kg, consumer_transaction.date, consumer_transaction.price, consumer_transaction.storage_type "
    + "FROM consumer_transaction INNER JOIN product ON consumer_transaction.product_id = product.id " 
    + "INNER JOIN logistic_center ON consumer_transaction.logistic_center_id = logistic_center.id "
    + "INNER JOIN consumer ON consumer_transaction.consumer_id = consumer.id";
    var conditions = [];

    if (id != undefined){
        query = query + " WHERE id = " + id; 
    } 
    else {
        if (day != undefined && month != undefined && year != undefined){
            conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day);
        } else if (month != undefined && year != undefined){
            conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month);
        } else if (year != undefined){
            conditions.push(" YEAR(date) = '" + year + "'");
        }

        if (logistic_center_id != undefined)  conditions.push(" logistic_center_id = " + logistic_center_id);
        
        if (product_id != undefined) conditions.push(" product_id = " + product_id);
        
        if (consumer_id != undefined) conditions.push(" consumer_id = " + consumer_id);

        if (conditions.length > 0){
            query = query + " WHERE " + conditions.join(" AND ");
        }
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
        id: req.undefined.originalValue.id, 
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        consumer_id: req.undefined.originalValue.consumer_id,
        product_category: req.undefined.originalValue.product_category,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date,
        price: req.undefined.originalValue.price,
        storage_type: req.undefined.originalValue.storage_type
    }

    // Update the estimated stock for a given date
    var stock_query = 'INSERT INTO actual_stock SET ?'

    var actual_stock_query = 'SELECT * FROM actual_stock WHERE product_id = ' + req.undefined.originalValue.product_id
        + ' AND logistic_center_id = ' + req.undefined.originalValue.logistic_center_id + ' AND product_category = "' 
        + req.undefined.originalValue.product_category + "\" AND DATE(date) < '" + req.undefined.originalValue.date  + '\' ORDER BY id DESC LIMIT 1'

    // Execute query
    connection.query(actual_stock_query, function (error, results, fields) {
        if (error) throw error;
        var actual_stock = results[0];

        var new_actual_stock = {
            'product_id': actual_stock.product_id,
            'logistic_center_id': actual_stock.logistic_center_id,
            'product_category': actual_stock.product_category,
            'date': req.undefined.originalValue.date,
            'amount_kg': actual_stock['amount_kg'] - req.undefined.originalValue.amount_kg
        }
        // Execute query
        connection.query(stock_query, [new_actual_stock], function (error, results, fields) {
            if (error) throw error;
        });
    });

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




