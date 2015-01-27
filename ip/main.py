#!/usr/bin/env python
# coding: utf-8
import os,sys,json
import string



from ipip import IP
from ipip import IPX

IP.load(os.path.abspath("mydata4vipday2.dat"))
#address = IP.find("118.28.8.8")
#print address


jsonfile=open("20141001.txt")
#jsonfile=open("20141001-20141101.txt")
jsontext = jsonfile.read()
dataDict = json.loads(jsontext);

list = dataDict["list"]
match=0
smatch=0
nomatch=0
snomatch=0
nomatchList=[]
count=0
for element in list:
    ip = element["ip"]
    #print ip
    #print element["city"]
    address = IP.find(ip).split()[1:]
    if len(address) == 1:
        #print "====",address[0],"===="
        pass
    address = string.join(address,",")
    userSelect = element["tocity"]

    #if len(userSelect.split(",")) == 1:
    #    print "$$$$",userSelect,"$$$$"
    address = address.encode("utf-8")
    userSelect  = userSelect.encode("utf-8")
    #print ip + ":"+ userSelect + "--->" +  address,
    print ip,userSelect+"--->"+address,  #element["comparison"].encode("utf-8"),
    if (userSelect == address or address.find(userSelect) >= 0):
        match+=1
        print True,
    else:
        nomatch+=1
        print False,
    #####
    if ((userSelect == address) or  userSelect.find(address)>=0) or address.find(userSelect) >=0:
        smatch+=1
        print "s" ,True
    else:
        snomatch+=1
        print "s" , False
    count+=1
print "count:"+str(count),"match:"+str(match) + " nomatch:"+str(nomatch)
print "%.4f%%" % (float(match)/float(count)*100)
print "count:"+str(count),"smatch:"+str(smatch) + " snomatch:"+str(snomatch)
print "%.4f%%" % (float(smatch)/float(count)*100)
