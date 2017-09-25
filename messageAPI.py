from flask import Flask, render_template
from flask_socketio import SocketIO
# from flaskext.mysql import MySQL

# mysql = MySQL()
app = Flask(__name__)

# # MySQL configurations
# app.config['MYSQL_DATABASE_USER'] = 'tscelsi'
# app.config['MYSQL_DATABASE_PASSWORD'] = 'tscelsi_2017'
# app.config['MYSQL_DATABASE_DB'] = 'tscelsi'
# app.config['MYSQL_DATABASE_HOST'] = 'info20003db.eng.unimelb.edu.au'
# app.config['SECRET_KEY'] = 'secret!'

# mysql.init_app(app)
socketio = SocketIO(app)
global textMessage

@socketio.on('received')
def handle_recieved(received):
	textMessage = str(received)

@socketio.on('message')
def handle_message(message):
	send("Hello")

if __name__ == '__main__':
    socketio.run(app)