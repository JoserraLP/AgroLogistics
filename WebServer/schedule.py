from flask import Blueprint, render_template, request, redirect, url_for, session
from flask_login import login_required, current_user
from datetime import datetime
from .models import ProducerEvent
from . import db

import calendar

# Main blueprint
schedule = Blueprint('schedule', __name__)

def calc_calendar(date):
    year = date.year
    this_month = date.month
    yearInfo = dict()

    for month in range(this_month, 13):
        days = calendar.monthcalendar(year, month)
        #if len(days) != 6:
            #days.append([0 for _ in range(7)])
        month_addr = calendar.month_abbr[month]
        yearInfo[month_addr] = days
    
    return yearInfo

@schedule.route('/schedule', methods=['GET', 'POST'])
@login_required
def show_schedule():
    ''' Schedule page.
    
        Returns:
            Logistic center schedule page

        list(calendar.month_abbr).index(month_abbr)
    '''
    if request.method == 'POST':
        month_addr = request.form.get('month')
        month = list(calendar.month_abbr).index(month_addr)
        days = calendar.monthcalendar(datetime.today().year, month)
        return render_template('schedule.html', calendar=days, month=month_addr)
    else:
        args = request.args
        month = args.get('month')
        day = args.get('day')

        if month is None or day is None:
            date = datetime.today()
            this_month = calendar.month_abbr[date.month]
            days = calendar.monthcalendar(date.year, date.month)
            return render_template('schedule.html', calendar=days, month=this_month)
        else:
            event = ProducerEvent.query.filter_by(user_id=current_user.id, day=day, month=list(calendar.month_abbr).index(month)).first()
            if not event: empty = True 
            else: empty = False
            return render_template('schedule_day.html', month=list(calendar.month_abbr).index(month), day=day, empty=empty)

@schedule.route('/event', methods=['POST'])
@login_required
def add_event():

    day = request.form.get('day')
    month = request.form.get('month')
    
    event = ProducerEvent(
        user_id = current_user.id,
        day = day,
        month = month
    )
    db.session.add(event)
    db.session.commit()

    return redirect(url_for('main.profile'))