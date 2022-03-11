'use strict';

const connection = require("../config/config");

/**
 * Delete product item
 * Delete product item
 *
 * productID Integer Product ID

 * returns String
 **/
module.exports.deleteProduct = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = "DELETE FROM product WHERE id = " + req.productID.originalValue
    
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
 * Get one product's information
 * Get one product
 *
 * id Integer ID of the product to get information

 * returns String
 **/
module.exports.getProduct = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create query
    var query = "SELECT * FROM product WHERE id = '" + req.id.originalValue + "'"
    
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
 * Returns all the products
 * Returns all the products
 *

 * returns String
 **/
module.exports.getProducts = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create query
    var query = "SELECT * FROM product"
    
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
 * Creates new product
 * Creates new product
 *
 * product Product 

 * returns String
 **/
module.exports.postProduct = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'INSERT INTO product SET ?'

    // Create data
    var data = {
        name: req.undefined.originalValue.name,
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
 * Modifies a product
 * Modifies a product
 *
 * product Product 

 * returns String
 **/
module.exports.putProduct = function(req, res, next) {
    //Parameters
    console.log(req);

    // Create a query
    var query = 'UPDATE product SET ? WHERE id = ' + req.undefined.originalValue.id
    
    // Create data
    var data = {
        name: req.undefined.originalValue.name,
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




