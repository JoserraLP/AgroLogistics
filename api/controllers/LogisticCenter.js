




'use strict';

var LogisticCenter = require('../service/LogisticCenterService');

module.exports.deleteLogisticCenter = function deleteLogisticCenter (req, res, next) {

    LogisticCenter.deleteLogisticCenter(req.swagger.params, res, next);

};

module.exports.getLogisticCenter = function getLogisticCenter (req, res, next) {

    LogisticCenter.getLogisticCenter(req.swagger.params, res, next);

};

module.exports.getLogisticCenters = function getLogisticCenters (req, res, next) {

    LogisticCenter.getLogisticCenters(req.swagger.params, res, next);

};

module.exports.postLogisticCenter = function postLogisticCenter (req, res, next) {

    LogisticCenter.postLogisticCenter(req.swagger.params, res, next);

};

module.exports.putLogisticCenter = function putLogisticCenter (req, res, next) {

    LogisticCenter.putLogisticCenter(req.swagger.params, res, next);

};
