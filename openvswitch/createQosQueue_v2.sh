#!/bin/bash
#
#	Function : ovs-vsctl clear Qos then create Qos (1m 10m) 
#	Parameters: [otherLimitFile]
#	weili116627@sohu-inc.com
#


filePath=$(cd $(dirname $0);pwd)
PATH=/opt/apps/openvswitch/bin/:/opt/apps/openvswitch/sbin/:$PATH
#### get ip interface netmask
IP=$(/sbin/ifconfig |grep "inet addr:" | head -1 | cut -f 2 -d ":" | cut -f 1 -d " ");
INTERFACE=`grep -rw $IP /etc/sysconfig/network-scripts/ |grep -i IPADDR|cut -d- -f3|cut -d: -f1`
NETMASK=`grep -rw NETMASK /etc/sysconfig/network-scripts/ |grep $INTERFACE|cut -d'=' -f2|tr -d "\""`

###test###
#IP=10.121.44.1
#INTERFACE=em1
#NETMASK=255.255.254.0



####Parameter check
#if [ $# -lt 3 ]
#then
#        echo
#        echo "please input ipaddr and netmask interface"
#        echo "USAGE:sh $0 192.168.0.1 255.255.255.0 eth0"
#        echo ;echo
#	exit 1
#fi

if [ $# -eq 0 ]
then
	ipaddr=$IP
	netmask=$NETMASK
	interface=$INTERFACE
fi

if [ $# -eq 1 ]
then
	ipaddr=$IP
	netmask=$NETMASK
	interface=$INTERFACE
	otherLimitFile=$1
	if [ ! -e "$otherLimitFile" ]
	then
		echo  " File $otherLimitFile not exists !!!"
		exit 1
	fi
	file $otherLimitFile |grep -q CRLF && echo "file  $otherLimitFile  is dos format" && exit 1
	OVSQos=`cat $otherLimitFile|uniq`
fi

out=`ipcalc  -bn $ipaddr   $netmask`
if [ $? -gt 0 ]
then
        echo "get ip or netmask error1." && exit 1

fi
echo $out" "$interface
export $out
#echo  "BROADCAST:"$BROADCAST
#echo  "NETWORK:"$NETWORK

### add default 1M 10M Qos Queue
START_3=`echo $NETWORK|awk -F'.' '{print $3}'`
START_4=`echo $NETWORK|awk -F'.' '{print $4}'`
END_3=`echo $BROADCAST|awk -F'.' '{print $3}'`
END_4=`echo $BROADCAST|awk -F'.' '{print $4}'`
IP_PRE2=`echo $NETWORK|awk -F'.' '{print $1"."$2}'`

echo "----------------------------------"
A=""
B=""
count=1
if [ ! -e $filePath/defaultLimit.sh ]
then

\rm  $filePath/${IP}.json
for i in `seq $START_3 $END_3`
do
	for j in `seq $START_4  $END_4`
	do

		Alignment_j=""
		if [ $j -lt 10 ]
		then	
			Alignment_j="00"$j
		elif [ $j -lt 100 ] && [ $j -ge 10 ]
		then	
			Alignment_j="0"$j
		else
			Alignment_j=$j
	
		fi
	
		Alignment_i=""
		if [ $i -lt 10 ]
		then	
			Alignment_i="00"$i
		elif [ $i -lt 100 ] && [ $i -ge 10 ]
		then	
			Alignment_i="0"$i
		else
			Alignment_i=$i
	
		fi

		Q1="1"$Alignment_i$Alignment_j
		Q10="10"$Alignment_i$Alignment_j
		N1=$count
		N10=$((count+1))

		A=$A$N1"=@q$Q1,"$N10"=@q$Q10,"
		B=$B" -- --id=@q$Q1 create queue  other-config:max-rate=8000000 other-config:min-rate=8000000 -- --id=@q$Q10 create queue  other-config:max-rate=10000000 other-config:min-rate=10000000"
		
		echo $N1"|"$Q1"|1|"$IP_PRE2"."$i"."$j    >>$filePath/${IP}.json
		echo $N10"|"$Q10"|10|"$IP_PRE2"."$i"."$j  >>$filePath/${IP}.json
		count=$((N10+1))
	done
done

#default 1m 10m command START
Z1="ovs-vsctl set port $interface qos=@newqos -- --id=@newqos create qos type=linux-htb   other-config:max-rate=10000000000 other-config:min-rate=10000000000 queues=0=@q0,"
Q0=" -- --id=@q0 create queue other-config:max-rate=1000000000 other-config:min-rate=1000000000 "
echo $Z1$A$B$Q0 >$filePath/defaultLimit.sh
### add 1m 10m END
fi


####START if other ip("e.g /tmp/ovsqos.json") qos exists then add to ....
Z=`cat  $filePath/defaultLimit.sh`
OA=""
OB=""

if [ $# -eq 1 ] && [ -e $otherLimitFile ]
then
        for qosip in  $OVSQos
        do
                echo $qosip
                tmp=`echo $qosip|tr -d " "`
                LIMIT_M=`echo $tmp|awk -F"|" '{print $1}'`
                IPADDRESS=`echo $tmp|awk -F"|" '{print $2}'`

                ip3=`echo $IPADDRESS|awk -F'.' '{print $3}'`
                ip4=`echo $IPADDRESS|awk -F'.' '{print $4}'`

                Alignment_3=""
                if [ $ip3 -lt 10 ]
                then
                        Alignment_3="00"$ip3
                elif [ $ip3 -lt 100 ] && [ $ip3 -ge 10 ]
                then
                        Alignment_3="0"$ip3
                else
                        Alignment_3=$ip3

                fi

                Alignment_4=""
                if [ $ip4 -lt 10 ]
                then
                        Alignment_4="00"$ip4
                elif [ $ip4 -lt 100 ] && [ $ip4 -ge 10 ]
                then
                        Alignment_4="0"$ip4
                else
                        Alignment_4=$ip4

                fi

                Qother=$LIMIT_M$Alignment_3$Alignment_4
                OA=$OA$Qother"=@q$Qother,"
                OB=$OB" -- --id=@q$Qother  create queue  other-config:max-rate=${LIMIT_M}000000 other-config:min-rate=${LIMIT_M}000000 "
        done
fi
######END


##### clear  Qos Queue
ovs-vsctl -- --all destroy queue -- --all destroy QoS  -- clear Port $interface qos

####run create Qos commmand
$Z$OA$OB &>/dev/null
[ $? -eq 0 ] && echo "ok!" && exit 0
echo "fail!" && exit 1

