from flask import Blueprint, render_template, request, url_for, redirect
from flask_login import login_required, current_user
from agrologistics_web import db

# Main blueprint
from agrologistics_web.models import User

main = Blueprint('main', __name__)


@main.route('/')
def index():
    ''' Main page. 
    
        Returns:
            Main web server page
    '''
    return render_template('index.html')


@main.route('/profile')
@login_required
def profile():
    ''' Profile page. 
    
        Returns:
            User profile page
    '''

    # Search user in the db
    user = User.query.filter_by(name=current_user.name).first()

    if user:
        user_data = {
            'email': user.email,
            'capacity_kg': user.capacity_kg,
            'cooled_capacity_kg': user.cooled_capacity_kg
        }

        return render_template('profile.html', name=current_user.name, user_data=user_data)


@main.route('/select_colors', methods=['GET', 'POST'])
@login_required
def select_colors():
    ''' Select range colors for schedule.'''

    if request.method == 'GET':
        # Search user in the db
        user = User.query.filter_by(name=current_user.name).first()

        user_option, user_colors = user.colors.split('#')

        user_colors_dict = [(value[0], parse_rgb_to_hex(value[1])) for value in
                            [item.split(':') for item in user_colors.split(';')]]

        return render_template('select_colors.html', user_option=user_option, user_colors_dict=user_colors_dict)
    elif request.method == 'POST':
        form_values = request.form.to_dict()

        processed_colors = form_values['option'] + '#0:rgb(255,255,255);' + form_values['first_value'] + ':' + \
                           parse_hex_to_rgb(form_values['first_color'].lstrip('#')) + ';' + form_values['second_value'] \
                           + ':' + parse_hex_to_rgb(form_values['second_color'].lstrip('#')) + ';' + \
                           form_values['third_value'] + ':' + parse_hex_to_rgb(form_values['third_color'].lstrip('#'))

        # Search user in the db
        user = User.query.filter_by(name=current_user.name).first()

        # Update user
        user.colors = processed_colors

        db.session.commit()

        return redirect(url_for('main.profile'))


def parse_hex_to_rgb(color):
    return 'rgb' + str(tuple(int(color[i:i + 2], 16) for i in (0, 2, 4)))


def parse_rgb_to_hex(color):
    return '#%02x%02x%02x' % tuple([int(x) for x in color.replace('rgb', '').replace('(', '').replace(')', '').split(',')])
