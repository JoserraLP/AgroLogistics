import datetime

from ..models import User, Role, ProducerEvent
from werkzeug.security import generate_password_hash

#from ..static.constants import DESCRIPTIONS

def insert_user_data(db):
    ''' Insert data in the database

        Parameters:
            db (object): database instance

    '''

    try:
        # ------- Users ------- #

        user = User(
            email = "admin@admin.com",
            password = generate_password_hash("admin", method='sha256'),
            name = "admin",
            location = "Spain",
            min_stock = 0,
            max_stock = 0,
            # Necessary to redirect to authorized views
            email_confirmed_at=datetime.datetime.utcnow()
        )

        user.roles.append(Role(name='admin'))

        db.session.add(user)

        user = User(
            email = "center@center.com",
            password = generate_password_hash("center", method='sha256'),
            name = "center",
            location = "Spain",
            min_stock = 0,
            max_stock = 50,
            email_confirmed_at=datetime.datetime.utcnow()
        )

        user.roles.append(Role(name='logistics_center'))

        db.session.add(user)

        # ------- Events ------- #

        event = ProducerEvent(
            product_id = 1,
            logistic_center_id = 2,
            productor_id = 1,
            product_category = "1",
            amount_kg = 20,
            date = datetime.datetime(2022, 3, 30),
            price = 100,
            storage_type = "cajas"
        )

        db.session.add(event)

        event = ProducerEvent(
            product_id = 1,
            logistic_center_id = 2,
            productor_id = 1,
            product_category = "1",
            amount_kg = 20,
            date = datetime.datetime(2022, 3, 28),
            price = 100,
            storage_type = "cajas"
        )

        db.session.add(event)
        db.session.commit()
        
    except Exception as e:
        print(e)

def insert_observation_data(db, obs):

    try:
        observation = Observation(
            name = obs[0],
            description = DESCRIPTIONS[obs[0]],
            location = obs[1].strip()
        )

        db.session.add(observation)
        db.session.commit()

    except Exception as e:
        print(e)