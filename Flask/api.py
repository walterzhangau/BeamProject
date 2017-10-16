from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flaskext.mysql import MySQL
import json

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
            parser.add_argument('username', type=str, help='username to create user')
            parser.add_argument('email', type=str, help='Email address to create user')
            parser.add_argument('password', type=str, help='Password to create user')
            args = parser.parse_args()

            _userEmail = args['email']
            _userUsername = args['username']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateUser',(_userUsername,_userPassword,_userEmail))
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
            print(data[0][0] == "Success")
            if data[0][0] == "Success":
                conn.commit()
                return {'StatusCode':'201','Message': 'Login success'}
            else:
                return {'StatusCode':'1001','Message':"Wrong Username and Password"}

        except Exception as e:
            return {'error': str(e)}
        
class ListFriends(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address for friends')
            args = parser.parse_args()

            
            _userEmail = args['email']
            
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spListFriends', [_userEmail] )
            data = cursor.fetchall()
            
            if len(data) is not 0:
                conn.commit()
                friend_list = {}
                for (user_id, friend, status) in data:
                    friend_list.update({user_id:(friend,status)})
                
                return friend_list
            else:
                return {'StatusCode':'1002','Message':"NO FRIENDS"}

        except Exception as e:
            return {'error': str(e)}
        
class ListFriendRequests(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='friend requests')
            args = parser.parse_args()
            _userEmail = args['email']
            
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spListFriendRequests', [_userEmail] )
            data = cursor.fetchall()
            
            if len(data) is not 0:
                conn.commit()
                friend_list = {}
                for (friend, ids) in data:
                    friend_list.update({ids:friend})
                
                return friend_list
            else:
                return {'StatusCode':'1003','Message':"NO Requests"}

        except Exception as e:
            return {'error': str(e)}
        
class SendFriendRequest(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address of user who is sending request')
            parser.add_argument('user', type=str, help='Username of of person who is recieving request')
            args = parser.parse_args()

            
            _userEmail = args['email']
            _userUser = args['user']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spSendFriendRequest',(_userEmail,_userUser))
            data = cursor.fetchall()
            
            if data == ():
                conn.commit()
                return {'StatusCode':'202','Message': 'Friend Request Sent'}
            else:
                return {'StatusCode':'1004','Message':"Username Does Not Exist or you are already friends"}

        except Exception as e:
            return {'error': str(e)}
        
class AcceptFriendRequest(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address of user who is sending request')
            parser.add_argument('user', type=str, help='Username of of person who is recieving request')
            args = parser.parse_args()

            
            _userEmail = args['email']
            _userUser = args['user']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spAcceptFriendRequest',(_userEmail,_userUser))
            data = cursor.fetchall()
            
            if data == ():
                conn.commit()
                return {'StatusCode':'203','Message': 'Accepted Friend Request'}
            else:
                return {'StatusCode':'1004','Message':"Error"}

        except Exception as e:
            return {'error': str(e)}

class UpdateLocation(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address of user who is sending request')
            parser.add_argument('location', type=str, help='Location of user')
            args = parser.parse_args()

            
            _userEmail = args['email']
            _userLocation = args['location']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spUpdateLocation',(_userEmail,_userLocation))
            data = cursor.fetchall()
            
            if data == ():
                conn.commit()
                return {'StatusCode':'204','Message': 'Updated Location'}
            else:
                return {'StatusCode':'1004','Message':"Error"}

        except Exception as e:
            return {'error': str(e)}
        
        
        
class ListAllFriendLocations(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address for friends location')
            args = parser.parse_args()

            
            _userEmail = args['email']
            
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spListAllFriendLocations', [_userEmail] )
            data = cursor.fetchall()
            
            if len(data) is not 0:
                conn.commit()
                friend_list = {}
                for (ids, location) in data:
                    friend_list.update({ids:location})
                
                return friend_list
            else:
                return {'StatusCode':'1005','Message':"NO FRIEND LOCATION"}

        except Exception as e:
            return {'error': str(e)}
        
class FriendLocation(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('user', type=str, help='user to find location of')
            args = parser.parse_args()

            
            _userUser = args['user']
            
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spFriendLocation', [_userUser] )
            data = cursor.fetchall()
            print(data)
            if len(data) is not 0:
                
                conn.commit()
                friend_list = {}
                for (ids, location) in data:
                    friend_list.update({ids:location})
                
                return friend_list
            else:
                return {'StatusCode':'1005','Message':"NO Location"}

        except Exception as e:
            return {'error': str(e)}
        
class GetUsername(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='email of username to find')
            args = parser.parse_args()

            
            _userEmail = args['email']
            
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spUsernamefromEmail', [_userEmail] )
            data = cursor.fetchall()

            if len(data) is not 0:

                conn.commit()
                username = {}
                username.update({"username":data[0][0]})
            
                return username
            else:
                return {'StatusCode':'1005','Message':"NO Location"}

        except Exception as e:
            return {'error': str(e)}

api.add_resource(CreateUser, '/CreateUser')
api.add_resource(Login, '/Login')
api.add_resource(ListFriends, '/ListFriends')
api.add_resource(ListFriendRequests, '/ListFriendRequests')
api.add_resource(SendFriendRequest, '/SendFriendRequest')
api.add_resource(AcceptFriendRequest, '/AcceptFriendRequest')
api.add_resource(UpdateLocation, '/UpdateLocation')
api.add_resource(ListAllFriendLocations, '/ListAllFriendLocations')
api.add_resource(FriendLocation, '/FriendLocation')
api.add_resource(GetUsername, '/GetUsername')


if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0', port = 4444)


