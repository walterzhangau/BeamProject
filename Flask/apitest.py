import unittest
import api
import requests
import json


#Testing login with pre existing account
class TestFlaskApiLogin(unittest.TestCase):
    def test_Login(self):
        response = requests.post('http://0.0.0.0:4444/Login?email=walter@gmail.com&password=p')
        self.assertEqual(response.json(), {u'Message': u'Login success', u'StatusCode': u'201'})
        
#updating the location with latitude and longitude
class TestFlaskApiUpdateLocation(unittest.TestCase):
    def test_updateLocation(self):
        response = requests.post('http://0.0.0.0:4444/UpdateLocation?email=test@gmail.com&longitude=3&latitude=2')
        self.assertEqual(response.json(), {u'Message': u'Updated Location', u'StatusCode': u'204'})
        
#listing all of test's friends
class TestFlaskApiListFriends(unittest.TestCase):
    def test_ListFriends(self):
        response = requests.post('http://0.0.0.0:4444/ListFriends?email=test@gmail.com')
        self.assertEqual(response.json(), {u'25': [u'test5', 3], u'3': [u'Evan', 3]})
        
#sending friend requests to somebody who is already a friend and a non existent user
class TestFlaskApiSendFriendReq(unittest.TestCase):
    def test_FriendRequestAlreadySent(self):
        response = requests.post('http://0.0.0.0:4444/SendFriendRequest?email=test@gmail.com&user=test5')
        self.assertEqual(response.json(), {u'error': u'(1062, u"Duplicate entry \'18-25\' for key \'unique_users_id\'")'})
    def test_UserNotExist(self):
        response = requests.post('http://0.0.0.0:4444/SendFriendRequest?email=test@gmail.com&user=notauser')
        self.assertEqual(response.json(), {u'Message': u'Username Does Not Exist or you are already friends', u'StatusCode': u'1004'}   )

#listing tests location
class TestFlaskApiFriendLocation(unittest.TestCase):
    def test_friendLoc(self):
        response = requests.post('http://0.0.0.0:4444/FriendLocation?user=test')
        self.assertEqual(response.json(), {u'latitude': u'2', u'user': u'test', u'longitude': u'3'})
        
if __name__ == "__main__":
    unittest.main()
