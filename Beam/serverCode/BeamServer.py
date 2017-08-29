import socket #for sockets
import sys # for exit
from thread import *

HOST = '' #means all available interfaces
PORT = 8889 #non-privileged port

try:
    #create an AF_INET, TCP socket
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error, msg:
        print 'Failed to create socket. Error code: ' + str(msg[0]) + ', Error Message: ' + msg[1]
        sys.exit();

try:
    s.bind((HOST, PORT))
except socket.error, msg:
    print 'Bind failed. Error Code: ' + str(msg[0]) + ', Message: ' + msg[1]
    sys.exit()

s.listen(2)


#thread to handle client connections:
def clientthread(conn):
    conn.send("Welcome to the server. Type something and hit enter.\n")
    while True:
        data = conn.recv(1024)
        
        reply = 'OK...' + data
        if not data:
            break
        conn.sendall(reply)

    #came out of loop, close connection
    conn.close()

while 1:
    conn, addr = s.accept()
    #display client info
    print 'Connected with ' + addr[0] + ':' + str(addr[1])
    start_new_thread(clientthread, (conn,))

#close socket    
s.close()
