import socket #for sockets
import sys # for exit
import mysql.connector
import json
from threading import *

HOST = '' #means all available interfaces
PORT = 8889 #non-privileged port

try:
	#create an AF_INET, TCP socket
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	print ("Socket created")
except socket.error:
		print ('Failed to create socket.')
		sys.exit();

try:
	s.bind((HOST, PORT))
	print ("socket bound on: " + HOST + "," + str(PORT))
except socket.error:
	print ('Bind failed.')
	sys.exit()

s.listen(2)
print ("listening...")


#thread to handle client connections:
def clientthread(conn):
	conn.send("Welcome to the server. Type something and hit enter.\n")
	cnx = mysql.connector.connect(user= 'tscelsi', password= 'tscelsi_2017',host='info20003db.eng.unimelb.edu.au',database='tscelsi')
	cursor = cnx.cursor()
	query = ("SELECT * from users")
	cursor.execute(query)
	data = conn.recv(1024)
	print (data.strip() + ": this is the data")

    if (data.strip() == "sendMeData"):
		
		data= {}
		for (a,username, phone,email,password,first_name,last_name) in cursor:
			print("{}, {} ,{}, {} ,{}, {} ".format(a,username, phone,email,password,first_name,last_name))
			data.update({email:username})
		
		json_data = json.dumps(data)
		conn.send(json_data)

     if (data.strip() == "sendMeData"):
         
         
         
         
         
	conn.sendall("bye")
	cursor.close()
	cnx.close()
	#came out of loop, close connection
	conn.close()

while 1:
	conn, addr = s.accept()
	#display client info
	print ('Connected with ' + addr[0] + ':' + str(addr[1]))
	start_new_thread(clientthread, (conn,))

#close socket    
s.close()
