import requests
from flask import Blueprint, render_template, request, redirect, url_for, flash, session
from flask_login import login_required

from agrologistics_web.static.constants import SERVER_API_URL

# Transaction blueprint
transaction = Blueprint('transaction', __name__)


@transaction.route('/add_consumer_transaction', methods=['GET', 'POST'])
@login_required
def add_consumer_transaction():
    """
    Add consumer transaction to the database.

        Returns:
            Logistic center stock page
    """
    # POST method -> Perform the API query to save the transaction
    if request.method == 'POST':

        form_values = request.form.to_dict()

        # Append ID of the event
        form_values['id'] = session.get('event_info')['id']

        requests.post(SERVER_API_URL + "/consumer_transaction", json=form_values)

        return redirect(url_for('schedule.consumer_schedule'))
    # GET method -> show stock page
    else:

        args = request.args
        event_id = args.get('event_id')

        consumer_events = session.get('consumer_events')

        event_info = [item for item in consumer_events if int(item['id']) == int(event_id)][0]
        session['event_info'] = event_info

        if event_info:
            return render_template('add_transaction.html', event_info=event_info)
        else:
            flash("Error when retrieving data")
            return redirect(url_for('main.index'))


@transaction.route('/see_consumer_transaction', methods=['GET'])
@login_required
def see_consumer_transaction():
    args = request.args
    id = args.get('id')

    params = {'id': id}

    # Perform request to retrieve number of consumer events
    consumer_transaction = requests.get(SERVER_API_URL + '/consumer_transaction', params=params).json()['message']

    if len(consumer_transaction) > 0:
        return render_template('transaction.html', event_info=consumer_transaction[0])
    else:
        flash("Error retrieving transaction")
        return redirect(url_for('main.index'))

@transaction.route('/add_producer_transaction', methods=['GET', 'POST'])
@login_required
def add_producer_transaction():
    """
    Add producer transaction to the database.

        Returns:
            Logistic center stock page
    """
    # POST method -> Perform the API query to save the transaction
    if request.method == 'POST':

        form_values = request.form.to_dict()

        # Append ID of the event
        form_values['id'] = session.get('event_info')['id']

        requests.post(SERVER_API_URL + "/producer_transaction", json=form_values)

        return redirect(url_for('schedule.producer_schedule'))
    # GET method -> show stock page
    else:

        args = request.args
        event_id = args.get('event_id')

        producer_events = session.get('producer_events')

        event_info = [item for item in producer_events if int(item['id']) == int(event_id)][0]
        session['event_info'] = event_info

        if event_info:
            return render_template('add_transaction.html', event_info=event_info)
        else:
            flash("Error when retrieving data")
            return redirect(url_for('main.index'))


@transaction.route('/see_producer_transaction', methods=['GET'])
@login_required
def see_producer_transaction():
    args = request.args
    id = args.get('id')

    params = {'id': id}

    # Perform request to retrieve number of producer events
    producer_transaction = requests.get(SERVER_API_URL + '/producer_transaction', params=params).json()['message']

    if len(producer_transaction) > 0:
        return render_template('transaction.html', event_info=producer_transaction[0])
    else:
        flash("Error retrieving transaction")
        return redirect(url_for('main.index'))
