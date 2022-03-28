from flask import Blueprint, render_template, request, redirect, url_for, session
from flask_login import login_required, current_user
from datetime import datetime
from .models import ProducerEvent
from .static.constants import SERVER_API_URL, OCCUPIED_DAY_DICT

from . import db
import requests
import calendar

# Main blueprint
schedule = Blueprint('schedule', __name__)


def calc_calendar(date):
    year = date.year
    this_month = date.month
    yearInfo = dict()

    for month in range(this_month, 13):
        days = calendar.monthcalendar(year, month)
        month_addr = calendar.month_abbr[month]
        yearInfo[month_addr] = days

    return yearInfo


def process_day_info(month_id, month_days, data):
    for week_idx, week in enumerate(month_days):
        for day_idx, day in enumerate(week):
            if day != 0:
                str_day = str(day)
                if day < 10:
                    str_day = '0' + str_day
                str_month_id = str(month_id)
                if month_id < 10:
                    str_month_id = '0' + str_month_id
                concat_date = str(datetime.now().year) + "-" + str_month_id + "-" + str_day
                # Retrieve only data related to the day
                day_data = [item for item in data if concat_date in item['date']]

                num_events = len(day_data)
                occupied_degree = [v for k, v in OCCUPIED_DAY_DICT.items() if int(k) <= num_events][-1]

                month_days[week_idx][day_idx] = {
                    'day': day,
                    'events': day_data,
                    'occupied_degree': occupied_degree
                }

    return month_days


def process_events(events, transactions):
    transaction_ids = [transaction['id'] for transaction in transactions]
    for idx, event in enumerate(events):
        if event['id'] in transaction_ids:
            events[idx]['blocked'] = True
        else:
            events[idx]['blocked'] = False
    return events


@schedule.route('/consumer_schedule', methods=['GET', 'POST'])
@login_required
def consumer_schedule():
    """ Schedule page.

        Returns:
            Logistic center schedule page

        list(calendar.month_abbr).index(month_abbr)
    """

    if request.method == 'POST':
        month_addr = request.form.get('month')
        month = list(calendar.month_abbr).index(month_addr)
        month_name = calendar.month_abbr[month]
        days = calendar.monthcalendar(datetime.today().year, month)
    else:
        # Get current date
        date = datetime.today()
        # Get current month
        month = date.month
        month_name = calendar.month_abbr[month]
        days = calendar.monthcalendar(date.year, date.month)

    params = {
        'year': 2022,
        'month': month,
        'logistic_center_id': current_user.id
    }

    # Perform request to retrieve number of consumer events
    consumer_events = requests.get(SERVER_API_URL + '/consumer_event', params=params)

    days = process_day_info(month, days, consumer_events.json()['message'])

    return render_template('schedule.html', calendar=days, month_name=month_name, month_num=month,
                           schedule_type="consumer")


@schedule.route('/consumer_day', methods=['GET'])
@login_required
def consumer_day():
    args = request.args
    month = args.get('month')
    day = args.get('day')

    params = {
        'year': 2022,
        'month': month,
        'day': day,
        'logistic_center_id': current_user.id
    }

    # Perform request to retrieve number of consumer events
    consumer_events = requests.get(SERVER_API_URL + '/consumer_event', params=params).json()['message']

    # Process date to fit the form (YYYY-MM-DDThh:mm)
    for event in consumer_events:
        event['date'] = event['date'][:-8]

    session['consumer_events'] = consumer_events

    transaction_params = {
        'logistics_center_id': current_user.id
    }
    # Retrieve the consumer transactions by logistic center and date
    consumer_transactions = requests.get(SERVER_API_URL + '/consumer_transaction', params=transaction_params).json()[
        'message']

    consumer_events = process_events(consumer_events, consumer_transactions)

    return render_template('schedule_day.html', day=day, month=month, events=consumer_events,
                           schedule_type="consumer")


@schedule.route('/producer_schedule', methods=['GET', 'POST'])
@login_required
def producer_schedule():
    """ Schedule page.

        Returns:
            Logistic center schedule page

        list(calendar.month_abbr).index(month_abbr)
    """

    if request.method == 'POST':
        month_addr = request.form.get('month')
        month = list(calendar.month_abbr).index(month_addr)
        month_name = calendar.month_abbr[month]
        days = calendar.monthcalendar(datetime.today().year, month)
    else:
        # Get current date
        date = datetime.today()
        # Get current month
        month = date.month
        month_name = calendar.month_abbr[month]
        days = calendar.monthcalendar(date.year, date.month)

    params = {
        'year': 2022,
        'month': month,
        'logistic_center_id': current_user.id
    }

    # Perform request to retrieve number of producer events
    producer_events = requests.get(SERVER_API_URL + '/producer_event', params=params)

    days = process_day_info(month, days, producer_events.json()['message'])

    return render_template('schedule.html', calendar=days, month_name=month_name, month_num=month,
                           schedule_type="producer")


@schedule.route('/producer_day', methods=['GET'])
@login_required
def producer_day():
    args = request.args
    month = args.get('month')
    day = args.get('day')

    params = {
        'year': 2022,
        'month': month,
        'day': day,
        'logistic_center_id': current_user.id
    }

    # Perform request to retrieve number of producer events
    producer_events = requests.get(SERVER_API_URL + '/producer_event', params=params).json()['message']

    # Process date to fit the form (YYYY-MM-DDThh:mm)
    for event in producer_events:
        event['date'] = event['date'][:-8]

    session['producer_events'] = producer_events

    transaction_params = {
        'logistic_center_id': current_user.id
    }
    # Retrieve the producer transactions by logistic center and date
    producer_transactions = requests.get(SERVER_API_URL + '/producer_transaction', params=transaction_params).json()[
        'message']

    producer_events = process_events(producer_events, producer_transactions)

    return render_template('schedule_day.html', day=day, month=month, events=producer_events,
                           schedule_type="producer")

