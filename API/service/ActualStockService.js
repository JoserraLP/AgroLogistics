'use strict';

const connection = require("../config/config");

/**
 * Delete actual stock item
 * Delete actual stock item
 *
 * actualStockID Integer Actual Stock ID

 * returns String
 **/
module.exports.deleteActualStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM actual_stock WHERE id = " + req.actualStockID.originalValue
    
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
 * Returns actual stock items
 * Returns the actual stock of a given date
 *
 * day Integer Day (optional)
 * month Integer Month (optional)
 * year Integer Year (optional)

 * returns String
 **/
module.exports.getActualStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Process the dates
    var day = req.day.originalValue;
    var month = req.month.originalValue;
    var year = req.year.originalValue;

    var query = "";

    if (day != undefined && month != undefined && year != undefined){
        query = "SELECT * FROM actual_stock WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month + " AND DAY(date) = " + day;
    } else if (month != undefined && year != undefined){
        query = "SELECT * FROM actual_stock WHERE YEAR(date) = '" + year + "' AND MONTH(date) = " + month;
    } else if (year != undefined){
        query = "SELECT * FROM actual_stock WHERE YEAR(date) = '" + year + "'";
    } else {
        query = "SELECT * FROM actual_stock";
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
 * Creates new actual stock item
 * Creates new actual stock item
 *
 * actualStock ActualStock 

 * returns String
 **/
module.exports.postActualStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO actual_stock SET ?'

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
 * Modifies an actual stock item
 * Modifies a actual stock item
 *
 * actualStock ActualStock 

 * returns String
 **/
module.exports.putActualStock = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE actual_stock SET ? WHERE id = ' + req.undefined.originalValue.id
    
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




