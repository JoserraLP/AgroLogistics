from flask_login import UserMixin
from agrologistics_web import db
from sqlalchemy.dialects.oracle import BLOB


class User(UserMixin, db.Model):
    """
    Class representing a web server logistic center user

    id: int
        Logistic center user identifier
    email: str
        Logistic center user email
    password: str
        Logistic center user password
    name: str
        Logistic center user name
    capacity: float
        Logistic center capacity
    cooled_capacity_kg: float
        Logistic center cooled capacity
    email_confirmed_at: date
        Email confirmation date
    roles: list[str] 
        User roles 
    """

    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(100), unique=True)
    password = db.Column(BLOB)
    name = db.Column(db.String(100))
    capacity_kg = db.Column(db.Float)
    cooled_capacity_kg = db.Column(db.Float)
    # Datatype#first_value:first_color;second_value:second_color;third_value:third_color;
    # Datatype can be: num_events or capacity
    colors = db.Column(db.String(100))


    # Necessary to Flask user
    email_confirmed_at = db.Column(db.DateTime())

    # Define the relationship to Role via UserRoles
    roles = db.relationship('Role', secondary='user_roles')

    # Necessary to Flask user
    def has_roles(self, *args):
        """
        Check if the user has the roles specified in *args

        :param args: roles to check
        :type args: list

        :return: true if the user has the role, otherwise false
        :rtype: bool
        """
        return any(elem in [role.name for role in self.roles] for elem in args[0])

    def get_roles(self):
        """
        User roles getter

        :return: user roles
        :rtype: list
        """
        return self.roles


class Role(db.Model):
    """
    Class representing a role

    id : int 
        Role identifier
    name : str 
        Role name
    """
    __tablename__ = 'roles'
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(50), unique=True)


# Define the UserRoles association table
class UserRoles(db.Model):
    """
    Class representing the association between a user and a role

    id : int 
        UserRole identifier
    user_id : int 
        User identifier
    role_id : int 
        Role identifier
    """
    __tablename__ = 'user_roles'
    id = db.Column(db.Integer(), primary_key=True)
    user_id = db.Column(db.Integer(), db.ForeignKey('users.id', ondelete='CASCADE'))
    role_id = db.Column(db.Integer(), db.ForeignKey('roles.id', ondelete='CASCADE'))
