import datetime
import json
import ast
import rsa

from flask import Blueprint, render_template, redirect, url_for, request, flash
import requests
from flask_login import login_user, login_required, logout_user, current_user
from .models import User, Role
from . import db

from .static.constants import PRIVATE_KEY_DIR, PUBLIC_KEY_DIR, SERVER_API_URL

# Auth blueprint
auth = Blueprint('auth', __name__)


# -------------- Login -------------- #

def do_login(request):
    """ Login the user specified in the form if the password is correct, otherwise redirect to the login and show an error message.

        Parameters:
            request (object): Request with the data necessary to login

        Returns:
            Redirect the user to its profile page or to login page
    """

    # Retrieve login data
    email = request.form.get('email')
    password = request.form.get('password')
    remember = True if request.form.get('remember') else False

    # Search user in the db
    user = User.query.filter_by(email=email).first()

    decrypted_password = ""

    if user:
        # Load the private key
        with open(PRIVATE_KEY_DIR, mode='rb') as private_file:
            key_priv_data = private_file.read()
            private_key = rsa.PrivateKey.load_pkcs1(key_priv_data)

        # Decrypt password (retrieved from byte array)
        user_password = ast.literal_eval(user.password.decode("utf-8"))
        decrypted_password = rsa.decrypt(user_password, private_key)

    # Check if user actually exists
    # Get the input password, hash it, and compare it to the hashed password stored in database
    if not user or password != decrypted_password.decode('utf-8'):
        # If user doesn't exist or password is wrong, reload the page
        flash('Please check your login details and try again.')  # Message
        return redirect(url_for('auth.login'))

    # The user has the right credentials so do the login
    login_user(user, remember=remember)

    # Redirect to user profile
    return redirect(url_for('main.profile'))


@auth.route('/login', methods=['GET', 'POST'])
def login():
    ''' Login method

        Returns:
            Login form page or profile page depending on login data
    '''

    if current_user.is_authenticated:
        return redirect(url_for('main.profile'))

    else:
        # POST method -> do the login
        if request.method == 'POST':
            return do_login(request)
        # GET method -> show login form
        else:
            return render_template('login.html')


# -------------- Signup -------------- #

def do_signup(request):
    ''' Sign up the user specified in the form if the email isn't already used, otherwise redirect to the sign up and show an error message.

        Parameters:
            request (object): Request with the data necessary to sign up

        Returns:
            Redirect the user to its profile page or to sign up page
    '''

    # Retrieve sign up data
    name = request.form.get('name')
    email = request.form.get('email')
    password = request.form.get('password')
    capacity_kg = request.form.get('capacity_kg')
    cooled_capacity_kg = request.form.get('cooled_capacity_kg')

    # Search user in the db
    user = User.query.filter_by(email=email).first()

    # Check if email already exists
    if user:
        # If email is already used, reload the page
        flash('Email already registered.')  # Message
        return redirect(url_for('auth.signup'))

    with open(PUBLIC_KEY_DIR, mode='rb') as public_file:
        key_pub_data = public_file.read()
        public_key = rsa.PublicKey.load_pkcs1(key_pub_data)

        # Generate encrypted password
        password = rsa.encrypt(password.encode('utf-8'), public_key)
        # Represent as list of bytes
        password_bytes = str(list(password)).encode('utf-8')


    # The user has the right credentials so do the sign up
    # A new user is created with the form data
    new_user = User(
        email=email,
        password=password_bytes,
        name=name,
        capacity_kg=capacity_kg,
        cooled_capacity_kg=cooled_capacity_kg,
        email_confirmed_at=datetime.datetime.utcnow()
    )

    data = {
        'email': email,
        'password': str(list(password)),
        'name': name,
        'capacity_kg': capacity_kg,
        'cooled_capacity_kg': cooled_capacity_kg
    }

    # Perform post on API
    requests.post(SERVER_API_URL + '/logistic_center', json=data)

    # Search the role
    logistics_center_role = db.session.query(Role).filter_by(name="logistics_center").first()

    new_user.roles.append(logistics_center_role)

    # Add the new user to the database
    db.session.add(new_user)
    db.session.commit()

    login_user(new_user, remember=False)

    # Redirect to user profile
    return redirect(url_for('main.profile'))


@auth.route('/signup', methods=['GET', 'POST'])
def signup():
    ''' Sign up method

        Returns:
            Sing up form page or profile page depending on sign up data
    '''

    if current_user.is_authenticated:
        return redirect(url_for('main.profile'))

    else:
        # POST method -> do the login
        if request.method == 'POST':
            return do_signup(request)
        # GET method -> show login form
        else:
            return render_template('signup.html')


# -------------- Logout -------------- #

@auth.route('/logout')
@login_required
def logout():
    ''' Logout method

        Returns:
            Redirect to web server main page
    '''
    logout_user()
    return redirect(url_for('main.index'))
