import sqlite3
#We know the user: Dr. Wagner
try:
	uname = input("Enter your user name:")
	password = input("Enter your password:")

    
	conn = sqlite3.connect('secret.db')
	cur = conn.cursor()

	query1 = "SELECT Token FROM Users WHERE uname = '%s' AND password = '%s'" % (uname, password)
	cur.execute(query1)

	row = cur.fetchall()[0]

	print("\n\nToken for user is: %s" % row)
except Exception as e:
	print(e)