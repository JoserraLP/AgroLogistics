@echo off

:: change to current directory
cd %cd%

:: ------ FLASK ------
set FLASK_APP=webserver/agrologistics_web

flask run --host=0.0.0.0