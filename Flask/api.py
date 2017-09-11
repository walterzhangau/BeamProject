from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flaskext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)



# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'tscelsi'
app.config['MYSQL_DATABASE_PASSWORD'] = 'tscelsi_2017'
app.config['MYSQL_DATABASE_DB'] = 'tscelsi'
app.config['MYSQL_DATABASE_HOST'] = 'info20003db.eng.unimelb.edu.au'

mysql.init_app(app)

api = Api(app)

class CreateUser(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address to create user')
            parser.add_argument('password', type=str, help='Password to create user')
            args = parser.parse_args()

            
            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateUser',(_userEmail,_userPassword))
            data = cursor.fetchall()
            
            if len(data) is 0:
                conn.commit()
                return {'StatusCode':'200','Message': 'User creation success'}
            else:
                return {'StatusCode':'1000','Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}
        
class Login(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address thats loggin in')
            parser.add_argument('password', type=str, help='Password to match Email')
            args = parser.parse_args()

            
            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spUserLogin',(_userEmail,_userPassword))
            data = cursor.fetchall()
            print data[0][0] == "Success"
            if data[0][0] == "Success":
                conn.commit()
                return {'StatusCode':'201','Message': 'Login success'}
            else:
                return {'StatusCode':'1001','Message':"Wrong Username and Password"}

        except Exception as e:
            return {'error': str(e)}
        
api.add_resource(CreateUser, '/CreateUser')
api.add_resource(Login, '/Login')

if __name__ == '__main__':
    app.run(debug=True)
