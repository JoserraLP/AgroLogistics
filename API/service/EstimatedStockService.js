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

    var query = "";

    if (day != undefined && month != undefined && year != undefined){
        query = "SELECT * FROM estimated_stock WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day;
    } else if (month != undefined && year != undefined){
        query = "SELECT * FROM estimated_stock WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month;
    } else if (year != undefined){
        query = "SELECT * FROM estimated_stock WHERE YEAR(date) = '" + year + "'";
    } else {
        query = "SELECT * FROM estimated_stock";
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




