import requests
from flask import Blueprint, render_template, request, redirect, url_for
from flask_login import login_required, current_user
import pytz
from agrologistics_web.static.constants import SERVER_API_URL, INFO_LABELS, PLOT_DAY_RANGE
from datetime import date, datetime, timedelta
import json

# Main blueprint
statistics = Blueprint('statistics', __name__)


@statistics.route('/statistics')
@login_required
def show_options():
    # Retrieve products
    products = requests.get(SERVER_API_URL + '/product').json()['message']

    return render_template("statistics.html", products=products)


@statistics.route('/statistics/query')
@login_required
def select_options():
    # Retrieve param values
    args = request.args
    start_date = args.get('start_date')
    end_date = args.get('end_date')
    selected_option = args.get('option')
    product_id = args.get('product')
    product_category = args.get('product_category')

    actual_end_date = increase_date(end_date, 1)

    params = {
        'range': start_date + '/' + actual_end_date,
        'logistic_center_id': current_user.id
    }

    labels_info, data_label, plot_type = None, None, None

    if selected_option == 'summary':
        # Process the data

        # Perform request to retrieve total actual stock
        actual_stock = requests.get(SERVER_API_URL + '/actual_stock', params=params).json()['message']

        # Process info
        actual_stock_used = process_info(start_date=start_date, end_date=end_date, info=actual_stock,
                                         selected_option=selected_option)

        # Perform request to retrieve total estimated stock
        estimated_stock = requests.get(SERVER_API_URL + '/estimated_stock', params=params).json()['message']

        # Process info
        estimated_stock_used = process_info(start_date=start_date, end_date=end_date, info=estimated_stock,
                                            selected_option=selected_option)

        # Perform request to retrieve maximum capacity of the logistic center
        logistic_center = requests.get(SERVER_API_URL + '/logistic_center',
                                       params={'id': current_user.id}).json()['message'][0]

        # Retrieve maximum capacity
        maximum_capacity = logistic_center['capacity_kg']

        data = {}

        # Process all the information and gather into a single dict with the information
        for k, v in actual_stock_used.items():
            data[k] = {
                'actual_stock': actual_stock_used[k],
                'estimated_stock': estimated_stock_used[k],
                'capacity': maximum_capacity
            }

        # Store in JSON
        data = json.dumps(data)

        # Three different kind of information
        data_label = json.dumps({'label': ['Actual Stock (Global)', 'Estimated Stock (Global)', 'Maximum capacity']})

    else:
        # Retrieve single statistics information
        if product_id and product_category:
            params.update({
                'product_id': product_id,
                'product_category': product_category
            })
            # Retrieve additional parameters
            if selected_option in ['actual_stock', 'estimated_stock']:
                data_label = json.dumps({'label': ["Amount (kg)", "Maximum capacity"]})
            elif selected_option == 'price':
                data_label = json.dumps({'label': ["Price (€)"]})
        else:
            data_label = json.dumps({'label': ["Amount of events"]})

        # Perform request to retrieve information
        if selected_option == 'price':
            info = requests.get(SERVER_API_URL + '/producer_transaction', params=params).json()['message']
        else:
            info = requests.get(SERVER_API_URL + '/' + selected_option, params=params).json()['message']

        data = None

        if statistics:
            # Process the data to show
            data = process_info(start_date, actual_end_date, info, selected_option)

            # Perform request to retrieve maximum capacity of the logistic center
            logistic_center = requests.get(SERVER_API_URL + '/logistic_center',
                                           params={'id': current_user.id}).json()['message'][0]

            # Retrieve maximum capacity
            maximum_capacity = logistic_center['capacity_kg']

            for k, v in data.items():
                if selected_option == 'price' or 'event' in selected_option or 'transaction' in selected_option:
                    data[k] = {
                        selected_option: v
                    }
                else:
                    data[k] = {
                        selected_option: v,
                        'capacity': maximum_capacity
                    }

            # Store in JSON
            data = json.dumps(data)

    labels_info = {'date': "from " + start_date + " to " + end_date if start_date != end_date else str(start_date),
                   'info': INFO_LABELS[selected_option]}

    plot_type = json.dumps({'type': 'line'})

    return render_template("statistics_data.html", data=data, labels_info=labels_info, data_label=data_label,
                           plot_type=plot_type)


def increase_date(date, days=1):
    """
    Increase a given date

    :param date: date to be increased by n days. Format YYYY-mm-dd
    :type date: str
    :param days: number of days to increase. Default to 1.
    :type days: int

    :return: date increased by "days" number of days. Format YYYY-mm-dd

    """
    # Retrieve date info
    year, month, day = [int(x) for x in date.split('-')]
    # Define the UTC
    utc = pytz.UTC
    # Create the date object
    actual_date = datetime(year, month, day, hour=0, minute=0, second=0).replace(tzinfo=utc)
    # Increase the number of days
    actual_date = actual_date + timedelta(days=days)
    # Format the date string
    actual_date = actual_date.strftime("%Y-%m-%d")
    return actual_date


def process_info(start_date, end_date, info, selected_option):
    data = {}

    utc = pytz.UTC

    start_year, start_month, start_day = [int(x) for x in start_date.split('-')]
    end_year, end_month, end_day = [int(x) for x in end_date.split('-')]
    start_date = datetime(start_year, start_month, start_day, hour=0, minute=0, second=0).replace(tzinfo=utc)
    end_date = datetime(end_year, end_month, end_day, hour=0, minute=0, second=0).replace(tzinfo=utc)

    difference_days = end_date - start_date

    for day_limit, day_limit_values in PLOT_DAY_RANGE.items():
        if difference_days.days <= day_limit:
            actual_date = start_date
            for day in range(0, difference_days.days):
                for hour in day_limit_values['hours']:
                    actual_date = actual_date + timedelta(hours=hour)
                    if selected_option in ['actual_stock', 'estimated_stock']:  # Retrieve the stock (amount kg)
                        # Append amount of stock
                        stock_amount = [item['amount_kg'] for item in
                                                  get_info_by_date(info, start_date, actual_date)]

                        if len(stock_amount) == 0:
                            if not data:
                                data[actual_date.strftime(day_limit_values['date_format'])] = 0
                            else:
                                data[actual_date.strftime(day_limit_values['date_format'])] = list(data.items())[-1][1]
                        else:
                            data[actual_date.strftime(day_limit_values['date_format'])] = float(sum(stock_amount))

                    elif selected_option == 'price':  # Retrieve the price

                        # Append amount of stock
                        price_amount = sum([item['price'] for item in get_info_by_date(info, start_date, actual_date)])

                        if price_amount == 0:
                            if not data:
                                data[actual_date.strftime(day_limit_values['date_format'])] = 0
                            else:
                                data[actual_date.strftime(day_limit_values['date_format'])] = list(data.items())[-1][1]
                        else:
                            data[actual_date.strftime(day_limit_values['date_format'])] = price_amount

                    elif selected_option == 'summary':
                        # Append amount of stock
                        stock_amount = [item['amount_kg'] for item in
                                        get_info_by_date(info, start_date, actual_date)]

                        if len(stock_amount) == 0:
                            if not data:
                                data[actual_date.strftime(day_limit_values['date_format'])] = 0
                            else:
                                data[actual_date.strftime(day_limit_values['date_format'])] = list(data.items())[-1][1]
                        else:
                            data[actual_date.strftime(day_limit_values['date_format'])] = float(sum(stock_amount))

                    else:
                        # Append number of events
                        num_events = len(get_info_by_date(info, start_date, actual_date))
                        data[actual_date.strftime(day_limit_values['date_format'])] = num_events

                # Increase the day
                actual_date = actual_date.replace(hour=0) + timedelta(days=1)
            # Only check first info that is valid
            break
    return data


def get_info_by_date(data, start_date, end_date):
    info = []
    for index, item in enumerate(data):
        # Retrieve item date time
        item_date = datetime.strptime(item['date'], "%Y-%m-%dT%H:%M:%S.%f%z")
        # Check if valid range
        if end_date >= item_date >= start_date:
            # Append to list and remove from item
            info.append(item)
            del data[index]
    return info
