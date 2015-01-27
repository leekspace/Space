#!/bin/bash

#### get ip interface netmask
PATH=/opt/apps/openvswitch/bin/:/opt/apps/openvswitch/sbin/:$PATH
IP=$(/sbin/ifconfig |grep "inet addr:" | head -1 | cut -f 2 -d ":" | cut -f 1 -d " ");
INTERFACE=`grep -rw $IP /etc/sysconfig/network-scripts/ |grep -i IPADDR|cut -d- -f3|cut -d: -f1`
NETMASK=`grep -rw NETMASK /etc/sysconfig/network-scripts/ |grep $INTERFACE|cut -d'=' -f2|tr -d "\""`
filePath=$(cd $(dirname $0);pwd)

###test###
#IP=10.121.44.1
#INTERFACE=em1
#NETMASK=255.255.254.0

result=`ovs-dpctl show |grep $INTERFACE`
echo $IP:${result}  >$filePath/ipPortIndex_$IP.txt
