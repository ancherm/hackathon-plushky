from flask import Flask, jsonify, render_template, request, redirect
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.db'
db = SQLAlchemy(app)
class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50))
    lastName = db.Column(db.String(50))
    patronymic = db.Column(db.String(50))
    group = db.Column(db.String(50))
    quantity = db.Column(db.Integer)
    def __repr__(self):
        return '<User %r>' % self.name

@app.route('/users', methods=['GET'])
def get_users():
    users_from_db = User.query.all()
    user_list = [{'id': user.id, 'name': user.name, 'lastName':user.lastName, 'patronymic':user.patronymic,'group':user.group, 'quantity':user.quantity} for user in users_from_db]
    return jsonify(user_list)

@app.route('/endPoint', methods=['POST','GET'])
def create_user():
    if request.method == 'POST':
        name = request.form.get('name')
        lastName = request.form.get('lastName')
        patronymic = request.form.get('patronymic')
        group = request.form.get('group')
        quantity = request.form.get('quantity')
        user = User(name=name)
        try:
            db.session.add(user)
            db.session.commit()
            return redirect('/users')
        except:
            return "ERROR"
    else:
        return render_template("create_user.html")

if __name__ == '__main__':
    app.run(host='0.0.0.0')