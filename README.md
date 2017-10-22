# Beam

The Beam App is an Augmented Reality (AR) app that allows users to locate their friend in real time using the camera on their phone. Beam also allows users to add their friends, chat to them and locate them on a map.

## Getting Started

* Clone the Project
* Open Android Studio, chose File > Import project, select the root folder of the project.


### Prerequisites

Chat Server

```
Flask-SocketIO
```

Server

```
Flask
flask_restful
flaskext.mysql
json
```

### Installing

In order to use the chat server, Flask-SocketIO needs to be install

How to install flask-socketio

```
pip install flask-socketio
```


In order to use the server which holds access to the database (api.py),
the following needs to be installed

```
pip install Flask
pip install Flask-RESTful
pip install flask-mysql
```

## Running servers
Run Api.py with Python 2 or 3
Run messageApi.py with Python 2 or 3


## Running the tests

To run the tests for api.py, api.py needs to be running, then on a seperate terminal or computor
run:
```
python apitest.py
```

### Break down into end to end tests

apitest.py uses known pre-existing data and calls SQL created procedures to these, 
there are tests for adding data, updating data and retrieving data. If all tests pass
that means there is seamless connection with the SQL database.

## Deployment

On one machine 
(Can split servers between machines)

Run VPN connecting to remote.unimelb.edu.au 

Change final string IP in ServerCommunication directory, serverConnection.java to IP of machine running Api.py

Change final string IP in myAPllication directory, messagingActivity.java to IP of machine running messageApi.py


## Authors

* **Grace Geddes** 
* **Evan Lewis**
* **Walter Zhang**



## Acknowledgments

* ReadMe Template borrowed from https://gist.github.com/PurpleBooth/109311bb0361f32d87a2
* MessageAdapter.Java based on code from 'Design Chat Bubble UI in Android' by Hong Thai
* Link - /http://www.devexchanges.info/2016/03/design-chat-bubble-ui-in-android.html

* Thanks to our tutor, William
