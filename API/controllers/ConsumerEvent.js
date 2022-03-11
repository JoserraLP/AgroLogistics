




'use strict';

var ConsumerEvent = require('../service/ConsumerEventService');

module.exports.deleteConsumerEvent = function deleteConsumerEvent (req, res, next) {

    ConsumerEvent.deleteConsumerEvent(req.swagger.params, res, next);

};

module.exports.getConsumerEvents = function getConsumerEvents (req, res, next) {

    ConsumerEvent.getConsumerEvents(req.swagger.params, res, next);

};

module.exports.postConsumerEvent = function postConsumerEvent (req, res, next) {

    ConsumerEvent.postConsumerEvent(req.swagger.params, res, next);

};

module.exports.putConsumerEvent = function putConsumerEvent (req, res, next) {

    ConsumerEvent.putConsumerEvent(req.swagger.params, res, next);

};
