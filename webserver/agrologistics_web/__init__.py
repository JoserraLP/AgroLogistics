import os

import time

from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager
from flask_user import UserManager

from agrologistics_web.config import default

from agrologistics_web.static.constants import PRIVATE_KEY_DIR, PUBLIC_KEY_DIR

import rsa

db = SQLAlchemy()

def create_app():
    ''' Create a Flask app, configure it. register some project blueprints as 'auth' or 'main',
        initialize related services as SQLAlchemy (with data insertion) and the flask login manager.

        Returns:
            app (object): Configured Flask app

    '''

    # Create Flask app
    app = Flask(__name__, instance_relative_config=True)

    # Configure the application with the config file
    app.config.from_object(default)

    # Register project blueprints

    # -> Auth routes
    from agrologistics_web.auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint)

    # -> Non-auth routes
    from agrologistics_web.main import main as main_blueprint
    app.register_blueprint(main_blueprint)

    from agrologistics_web.statistics import statistics as statistics_blueprint
    app.register_blueprint(statistics_blueprint)

    from agrologistics_web.schedule import schedule as schedule_blueprint
    app.register_blueprint(schedule_blueprint)

    from agrologistics_web.transaction import transaction as transaction_blueprint
    app.register_blueprint(transaction_blueprint)

    # Import models to create the tables
    from agrologistics_web.models import User, Role

    # Init the Flask-User Manager service
    user_manager = UserManager(app, db, User)

    # Init the SQLAlchemy - DB service
    db.init_app(app)

    # Create the tables with the application context
    with app.app_context():
        db.create_all()
        # If there are no users and no roles create and insert them on the db
        if not User.query.limit(1).all() and not Role.query.limit(1).all():
            from agrologistics_web.utils.insert_data_to_db import insert_user_data
            insert_user_data(db)

    # Create the LoginManager
    login_manager = LoginManager()

    # Set the login manager main view as the login service
    login_manager.login_view = 'auth.login'

    # Init the LoginManager service
    login_manager.init_app(app)

    # Define the user loader for the LoginManager
    @login_manager.user_loader
    def load_user(user_id):
        # since the user_id is just the primary key of our user table, use it in the query for the user
        try:
            return User.query.get(int(user_id))
        except:
            return None

    # Generate and store keys
    if not os.path.exists(PRIVATE_KEY_DIR) and not os.path.exists(PUBLIC_KEY_DIR):
        public_key, private_key = rsa.newkeys(512)
        with open(PUBLIC_KEY_DIR, 'w+') as fp:
            fp.write(public_key.save_pkcs1().decode())
        with open(PRIVATE_KEY_DIR, 'w+') as fp:
            fp.write(private_key.save_pkcs1().decode())

    return app