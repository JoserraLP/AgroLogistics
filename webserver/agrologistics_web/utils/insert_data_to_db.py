import ast
import datetime
import requests
from sqlalchemy.dialects import sqlite

from agrologistics_web.static.constants import SERVER_API_URL
from agrologistics_web.models import User, Role


def insert_user_data(db):
    """
    Insert data in the database

    :param db: database instance
    :type db: object
    """
    logistic_centers = requests.get(SERVER_API_URL + '/logistic_center').json()['message']

    try:
        # ------- Users ------- #

        logistic_center_rol = Role(name='logistics_center')

        for logistic_center in logistic_centers:
            user = User(
                id=logistic_center['id'],
                email=logistic_center['email'],
                password=bytes(logistic_center['password']['data']),
                name=logistic_center['name'],
                capacity_kg=logistic_center['capacity_kg'],
                cooled_capacity_kg=logistic_center['cooled_capacity_kg'],
                email_confirmed_at=datetime.datetime.utcnow(),
                colors="num_events#0:rgb(255, 255, 255);1:rgb(246, 21, 21);5:rgb(248, 147, 110);10:rgb(134, 134, 134)" # Default color
            )

            user.roles.append(logistic_center_rol)

            db.session.add(user)

        db.session.commit()

    except Exception as e:
        print(e)
