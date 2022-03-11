




'use strict';

var ConsumerTransaction = require('../service/ConsumerTransactionService');

module.exports.deleteConsumerTransaction = function deleteConsumerTransaction (req, res, next) {

    ConsumerTransaction.deleteConsumerTransaction(req.swagger.params, res, next);

};

module.exports.getConsumerTransactions = function getConsumerTransactions (req, res, next) {

    ConsumerTransaction.getConsumerTransactions(req.swagger.params, res, next);

};

module.exports.postConsumerTransaction = function postConsumerTransaction (req, res, next) {

    ConsumerTransaction.postConsumerTransaction(req.swagger.params, res, next);

};

module.exports.putConsumerTransaction = function putConsumerTransaction (req, res, next) {

    ConsumerTransaction.putConsumerTransaction(req.swagger.params, res, next);

};
