#!/data/python/bin/python                                                                                                              
# -*- coding: utf8 -*-
#ʹÓpython·¢ËÓ¼þ½ű¾
import smtplib
import getopt,sys,os
from email.mime.text import MIMEText
import logging

LOG_FILENAME="sendmail.log"
logging.basicConfig(filename=LOG_FILENAME,level=logging.DEBUG,format='%(asctime)s %(levelname)s %(message)s')

mailto_list=[]
mail_host="smtp.163.com"
mail_user="auto_servers"
mail_user2="auto201205"
mail_pass="auto_servers!@#"
mail_postfix="163.com"
def send_mail(sub,to_list,content,mailuser):
    me=mail_user+"<"+mailuser+"@"+mail_postfix+">"
    msg = MIMEText(content,_subtype='plain',_charset='utf-8')
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = ";".join(to_list)
    try:
        s = smtplib.SMTP()
        s.connect(mail_host)
        s.login(mailuser,mail_pass)
        s.sendmail(me, to_list, msg.as_string())
        s.close()
        return True
    except Exception, e:
        print str(e)
        logging.debug(str(e))
        return False
if __name__ == '__main__':
    content =  sys.stdin.read()
    if len(sys.argv) < 3:
        print "argv:emailaddress subject text"
        sys.exit()
    mailto_list = sys.argv[2].split(',')
    if send_mail(sys.argv[1],mailto_list,content,mail_user):
        print "ok"
        logging.debug("from:" + mail_user +"  to:"+ str(mailto_list) + "ok" )
    elif send_mail(sys.argv[1],mailto_list,content,mail_user2):
        print "ok"
        logging.debug("from:" + mail_user2 + "  to:"+ str(mailto_list) + "ok" )
    else:
        logging.debug( " to:"+ str(mailto_list) + "error" )
        print "error"
