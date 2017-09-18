from flask import Flask, render_template
from flask_socketio import SocketIO
# from flask.ext.mysql import MySQL

# mysql = MySQL()
app = Flask(__name__)

# MySQL configurations
# app.config['MYSQL_DATABASE_USER'] = 'tscelsi'
# app.config['MYSQL_DATABASE_PASSWORD'] = 'tscelsi_2017'
# app.config['MYSQL_DATABASE_DB'] = 'tscelsi'
# app.config['MYSQL_DATABASE_HOST'] = 'info20003db.eng.unimelb.edu.au'
# app.config['SECRET_KEY'] = 'secret!'

# mysql.init_app(app)
socketio = SocketIO(app)

@socketio.on('userMessage')
def handle_json(userMessage):
    print('received json: ' + str(userMessage))

if __name__ == '__main__':
    socketio.run(app)