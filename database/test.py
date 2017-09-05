import mysql.connector
import json
cnx = mysql.connector.connect(user= 'tscelsi', password= 'tscelsi_2017',
                              host='info20003db.eng.unimelb.edu.au',
                              database='tscelsi')


cursor = cnx.cursor()

query = ("SELECT * from users")



cursor.execute(query)
data= {}

for (id,username, phone,email,password,first_name,last_name) in cursor:
  print("{}, {} ,{}, {} ,{}, {} ".format(id,username, phone,email,password,first_name,last_name))
  data.update({id:username})


json_data = json.dumps(data)

print(json_data)

for x in range(0, 3):
  print x
cursor.close()
cnx.close()
