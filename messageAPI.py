from flask import Flask, render_template, request, session
from flask_socketio import SocketIO, send, emit
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
global room
clients = []


@socketio.on('message')
def handle_received(received):
	data = eval(received)
	sender = 0
	# print(data)
	sessionID = request.sid
	room = sessionID
	clients.append((data['senderID'], sessionID),)
	# print(clients)
	for x in clients:
		if clients[sender] == data['receiverID']:
			room = clients[x].sessionID
			break
	#emit(received, room = room)
	send(received, room = room)

if __name__ == '__main__':
    socketio.run(app)