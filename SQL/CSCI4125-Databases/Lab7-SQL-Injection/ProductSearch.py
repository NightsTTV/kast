import sqlite3

try:
	search = input("Enter your product search:\n")

	conn = sqlite3.connect('secret.db')
	cur = conn.cursor()

	query1 = "SELECT * FROM Product WHERE name = '%s' OR lower(name) LIKE lower('%s')" % (search, "%" + search + "%")
	cur.execute(query1)

	rows = cur.fetchall()

	print("\n\nProducts from search:")
	for row in rows:
		print(row)

except Exception as e:
	print(e)