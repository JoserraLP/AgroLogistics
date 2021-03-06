'use strict';

var fs = require('fs'),
http = require('http'),
path = require('path');

var express = require("express");
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json({
    strict: false
}));
var oasTools = require('oas-tools');
var jsyaml = require('js-yaml');
var serverAddress = '0.0.0.0';
var serverPort = 8080;

var spec = fs.readFileSync(path.join(__dirname, '/api/openapi.yaml'), 'utf8');
var oasDoc = jsyaml.safeLoad(spec);


var options_object = {
    controllers: path.join(__dirname, './controllers'),
    loglevel: 'info',
    strict: false,
    router: true,
    validator: true
};

oasTools.configure(options_object);

oasTools.initialize(oasDoc, app, function() {

    http.createServer(app).listen(serverPort, serverAddress, function() {
        /*
        console.log("App running at http://172.30.0.3:" + serverPort);
        console.log("________________________________________________________________");
        if (options_object.docs !== false) {
            console.log('API docs (Swagger UI) available on http://172.30.0.3:' + serverPort + '/docs');
            console.log("________________________________________________________________");
        }
        */
    });
});



// MySQL failure
// https://stackoverflow.com/questions/50093144/mysql-8-0-client-does-not-support-authentication-protocol-requested-by-server