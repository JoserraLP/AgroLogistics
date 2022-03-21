'use strict';

const connection = require("../config/config");

/**
 * Delete estimated stock item
 * Delete estimated stock item
 *
 * estimatedStockID Integer Estimated Stock ID

 * returns String
 **/
module.exports.deleteEstimatedStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM estimated_stock WHERE id = " + req.estimatedStockID.originalValue

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
 * Returns estimated stock items
 * Returns the estimated stock of a given date
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getEstimatedStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;
    var logistic_center_id = req.logistic_center_id.originalValue;
    var product_id = req.product_id.originalValue;
    var product_category = req.product_category.originalValue;

    "SELECT estimated_stock.id, estimated_stock.product_id, product.name AS product_name, estimated_stock.logistic_center_id, "
                + "logistic_center.name AS logistic_center_name, estimated_stock.product_category, estimated_stock.amount_kg, estimated_stock.date "
                + "FROM estimated_stock INNER JOIN product ON estimated_stock.product_id = product.id " 
                + "INNER JOIN logistic_center ON estimated_stock.logistic_center_id = logistic_center.id ";

    var conditions = [];

    if (day != undefined && month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day);
    } else if (month != undefined && year != undefined){
        conditions.push(" YEAR(date) = '" + year + "' AND MONTH(date) = " + month);
    } else if (year != undefined){
        conditions.push(" YEAR(date) = '" + year + "'");
    }

    if (logistic_center_id != undefined)  conditions.push(" logistic_center_id = " + logistic_center_id);
    
    if (product_id != undefined) conditions.push(" product_id = " + product_id);
    
    if (product_category != undefined) conditions.push(" product_category = " + product_category);

    if (conditions.length > 0){
        query = query + " WHERE " + conditions.join(" AND ");
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
 * Creates new estimated stock item
 * Creates new estimated stock item
 *
 * estimatedStock EstimatedStock 

 * returns String
 **/
module.exports.postEstimatedStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO estimated_stock SET ?'

    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date
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
 * Modifies an estimated stock item
 * Modifies a estimated stock item
 *
 * estimatedStock EstimatedStock 

 * returns String
 **/
module.exports.putEstimatedStock = function(req, res, next) {
    //Parameters
    console.log(req);
    
    // Create a query
    var query = 'UPDATE estimated_stock SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        product_id: req.undefined.originalValue.product_id,
        logistic_center_id: req.undefined.originalValue.logistic_center_id,
        product_category: req.undefined.originalValue.product_category,
        amount_kg: req.undefined.originalValue.amount_kg,
        date: req.undefined.originalValue.date
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




