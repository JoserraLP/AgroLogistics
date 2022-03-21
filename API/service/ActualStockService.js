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
    var logistic_center_id = req.logistic_center_id.originalValue;
    var product_id = req.product_id.originalValue;
    var product_category = req.product_category.originalValue;

    var query = "SELECT actual_stock.id, actual_stock.product_id, product.name AS product_name, actual_stock.logistic_center_id, "
                + "logistic_center.name AS logistic_center_name, actual_stock.product_category, actual_stock.amount_kg, actual_stock.date "
                + "FROM actual_stock INNER JOIN product ON actual_stock.product_id = product.id " 
                + "INNER JOIN logistic_center ON actual_stock.logistic_center_id = logistic_center.id ";
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




