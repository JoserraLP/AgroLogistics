SERVER_API_URL = "http://localhost:8080"

OCCUPIED_DAY_DICT = {
    '0': 'EMPTY',
    '1': 'LOW',
    '5': 'MEDIUM',
    '10': 'FULL'
}

INFO_LABELS = {
    'actual_stock': 'Actual stock',
    'estimated_stock': 'Estimated stock',
    'consumer_event': 'Consumer events',
    'consumer_transaction': 'Consumer transactions',
    'producer_event': 'Producer events',
    'producer_transaction': 'Producer transactions',
    'summary': 'Stock\' evolution'
}

# if the selected range is only one day -> Show per hour
# if the selected range is less than a week -> Three per day (at 8:00, 14:00, 20:00)
# if the selected range is more than week -> Two per day (at 8:00 and 16:00)
# if the selected range is more than two weeks -> One per day (14:00)

PLOT_DAY_RANGE = {
    1: {
        'hours': [1 for i in range(0, 25)],  # All the hours of the day
        'date_format': "%H:%M:%S"},
    7: {
        'hours': [8, 6, 6],  # Hours (8:00, 14:00, 20:00)
        'date_format': "%Y-%m-%d %H:%M:%S"},
    14: {
        'hours': [8, 8],  # Hours (8:00, 16:00)
        'date_format': "%Y-%m-%d %H:%M:%S"},
    999999: {
        'hours': [0],  # One per day
        'date_format': "%Y-%m-%d"
    }
}

PUBLIC_KEY_DIR = "./WebServer/keys/pub_key.pem"
PRIVATE_KEY_DIR = "./WebServer/keys/private_key.pem"
