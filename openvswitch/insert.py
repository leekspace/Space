import MySQLdb
import os
import glob

def insertSceovs():
	db = MySQLdb.connect("10.121.35.233","sce","sce","sceovs" )
	cursor = db.cursor()
	number = 100
	sql = """select * from t_ovs_qos   limit  %d """
	try:
    		cursor.execute(sql%(number))
		datas = cursor.fetchall()
		for data in datas:
			count = len(data)
			for i in range(0,10):
				print data[i],
			print
	except Exception,e:
		print e
		db.close()
def selectNodeIp(db,nodeIp):
	bool =  True;
	sql="""select * from t_ovs_qos  where node like "%%%s%%" """
	try:
		cursor = db.cursor()
		cursor.execute(sql%(nodeIp))
		datas = cursor.fetchall()
		if(len(datas) == 0 ):
			bool =  False;
	except Exception,e:
		print e
		cursor.close()
	return True
def insert(db,sql):
	cursor = db.cursor()
	try:
		cursor = db.cursor()
		cursor.execute(sql)
		cursor.close()
	except Exception,e:
		print e
		cursor.close()

def run(db):
	filesRead = r"[0-9]*.[0-9]*.[0-9]*.[0-9]*.json"
	list = glob.glob(filesRead)
	for i in list:
		filename = os.path.basename(i)
		nodeIp =  filename[:-5]
		print "nodeIp:" + nodeIp
		if selectNodeIp(db,nodeIp):
			print "NodeIp:" + nodeIp  + " is exists!!!"
			continue

		file_object = open(filename)
		for line in file_object:
			#print nodeIp +" --- " +line
			lines = line.split("|")
			if(len(lines) < 4):
				continue
			port = lines[0]
			tag  = lines[1]
			bandwidth  = lines[2]
			ip = lines[3]
			print nodeIp + port + tag + bandwidth +  ip
			sql = """insert into  t_ovs_qos(port,tag,bandwidth,ip,node) values(port,tag,bandwidth,ip,node)"""
			insert(db,sql)
					
	
def main():
	try:
		db = MySQLdb.connect("10.121.35.233","sce","sce","sceovs" )
		run(db)
		db.close()
	except Exception,e:
		print e
		db.close()

if __name__=='__main__':
	main()
