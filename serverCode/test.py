import ConfigParser
from flask import Flask, jsonify, request
from flask_restful import Resource, Api
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
api = Api(app)


config = ConfigParser.ConfigParser()
config.read('db.conf')

app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://' + config.get('DB', 'user') + \
                                        ':' + config.get('DB', 'password') + '@' + \
                                        config.get('DB', 'host') + '/' + config.get('DB', 'db')

app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

mysql = SQLAlchemy()

# map models
class Users(mysql.Model):  
    __tablename__ = 'users'
    id = mysql.Column(mysql.Integer, primary_key=True)
    username = mysql.Column(mysql.String(16), nullable=False)
    phone = mysql.Column(mysql.String(16), nullable=False)
    email = mysql.Column(mysql.String(255), nullable=False)
    password = mysql.Column(mysql.String(48), nullable=True)
    first_name = mysql.Column(mysql.String(20), nullable=True)
    last_name = mysql.Column(mysql.String(20), nullable=True)

    def __repr__(self):
        return '<Items (%s, %s) >' % (self.username, self.password)

@app.route('/user', methods=['GET'])
def getUser():
    data = Users.query.all()
    data_all = []
    for user in data:
        data_all.append([user.id, user.username, user.password])
    return jsonify(users=data_all)

def get_tasks():
    return jsonify({'tasks': tasks})

if __name__ == '__main__':
    app.run(debug=True)
