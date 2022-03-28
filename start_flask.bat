@echo off

:: change to current directory
cd %cd%

:: ------ FLASK ------
set FLASK_APP=WebServer

flask run --host=0.0.0.0