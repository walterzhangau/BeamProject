import unittest
import api
import requests
import json

class TestFlaskApiLogin(unittest.TestCase):
    def test_Login(self):
        response = requests.post('http://0.0.0.0:5000/Login?email=walter@gmail.com&password=p')
        self.assertEqual(response.json(), {u'Message': u'Login success', u'StatusCode': u'201'})

if __name__ == "__main__":
    unittest.main()
