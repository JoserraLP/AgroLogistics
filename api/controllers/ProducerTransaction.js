




'use strict';

var ProducerTransaction = require('../service/ProducerTransactionService');

module.exports.deleteProducerTransaction = function deleteProducerTransaction (req, res, next) {

    ProducerTransaction.deleteProducerTransaction(req.swagger.params, res, next);

};

module.exports.getProducerTransactions = function getProducerTransactions (req, res, next) {

    ProducerTransaction.getProducerTransactions(req.swagger.params, res, next);

};

module.exports.postProducerTransaction = function postProducerTransaction (req, res, next) {

    ProducerTransaction.postProducerTransaction(req.swagger.params, res, next);

};

module.exports.putProducerTransaction = function putProducerTransaction (req, res, next) {

    ProducerTransaction.putProducerTransaction(req.swagger.params, res, next);

};
