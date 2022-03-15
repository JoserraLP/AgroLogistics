from flask import Blueprint, render_template, request, redirect, url_for
from flask_login import login_required, current_user
from .models import User
from . import db

# Main blueprint
stock = Blueprint('stock', __name__)

@stock.route('/stock', methods=['GET', 'POST'])
@login_required
def set_stock():
    ''' Stock page. 
    
        Returns:
            Logistic center stock page
    '''
    # POST method -> save max and min stock
    if request.method == 'POST':
        # current_user.min_stock = request.form.get('min')
        # current_user.max_stock = request.form.get('max')
        
        id = current_user.id
        user = User.query.filter_by(id=id).first()
        user.min_stock = request.form.get('min')
        user.max_stock = request.form.get('max')

        db.session.commit()
        return redirect(url_for('main.index'))
    # GET method -> show stock page
    else:
        return render_template('stock.html', min=current_user.min_stock, max=current_user.max_stock)