#!/bin/bash 
#
##Function : run ovs and create bridge "br0"  
#
##Parameters: < start |  stop >
#
##Requirements: redhat6.3  openvswitch-2.1.3    
#
##leekspace@gmail.com 


source   /etc/profile
export PATH=/opt/apps/openvswitch/bin:/opt/apps/openvswitch/sbin:$PATH


##CONFIG

IP=$(/sbin/ifconfig |grep "inet addr:" | grep '10.12' | cut -f 2 -d ":" | cut -f 1 -d " ")
NETMASK=$(/sbin/ifconfig |grep "inet addr:" | grep '10.12' | cut -f 4 -d ":" | cut -f 1 -d " ")
GATEWAY=$(/sbin/route -n |tail -1|awk '{print $2}')
net_name=`grep -rw $IP /etc/sysconfig/network-scripts/ |grep -i IPADDR|cut -d- -f3|cut -d: -f1`

OVS_BRIDGE=br0
OVS_PATH=/opt/apps/openvswitch
Controller="tcp:10.121.40.83 tcp:10.121.40.82"
CenterController="10.121.41.10"
FAIL_MODE=standalone
DESC=openvswitch
DEBUG=1


fun_reset_netgate(){
	[ $DEBUG -eq 0 ] &&  echo "fun_reset_netgate"
	route del default gw $GATEWAY
	route add default gw $GATEWAY
}

fun_check_ovsstatus(){
	[ $DEBUG -eq 0 ] &&  echo "fun_check_ovsstatus"
	ps aux|grep vswitchd|grep -v -q grep;result=$?
	if [ $result -eq 0 ]
	then
		echo "ovs is  running!" && return 0
	fi
	
	echo "ovs is not running!" && return 1
}

fun_del_ovsbridge(){
	[ $DEBUG -eq 0 ] && echo "fun_del_ovsbridge"
	fun_check_ovsstatus || exit
	ovs-vsctl --timeout 5 del-port $OVS_BRIDGE $net_name
	ifconfig $OVS_BRIDGE down
	ifconfig $net_name $IP netmask $NETMASK up
	fun_reset_netgate
	ovs-vsctl --timeout 5  del-br $OVS_BRIDGE

	killall ovs-vswitchd &>/dev/null
	killall ovsdb-server &>/dev/null
	sleep 1
	ovsswitch_pid="/var/run/ovs-vswitchd.pid"
	ovsdb_pid="/var/run/ovsdb-server.pid"
	ps -p `cat $ovsswitch_pid` &>/dev/null;r=$?
	[ $r -eq 0 ] && kill -9 `cat $ovsswitch_pid` && \rm $ovsswitch_pid

	ps -p `cat $ovsdb_pid` &>/dev/null;r=$?
	[ $r -eq 0 ] && kill -9 `cat $ovsdb_pid` && \rm $ovsdb_pid

	fun_check_ovsstatus;check=$?
	if [ $check -eq 0 ]  
	then
		echo "fail!"
	else
		/etc/init.d/network restart
	fi
}


fun_install_ovsmodel(){
	[ $DEBUG -eq 0 ] &&  echo "fun_install_ovsmodel"
	rmmod bridge &>/dev/null
	rmmod openvswitch  &>/dev/null
	modprobe libcrc32c &>/dev/null
	modprobe openvswitch &>/dev/null
	insmod  $OVS_PATH/module/openvswitch.ko &>/dev/null
	lsmod |grep openvswitch;result=$?
	if [ ! $result -eq 0 ]
	then
		echo "insmod openvswitch.ko fail ! "
		exit 1
	fi	
	cd $OVS_PATH
	[ ! -e $OVS_PATH/etc/openvswitch ] && mkdir -p $OVS_PATH/etc/openvswitch
        rm -f  $OVS_PATH/etc/openvswitch/conf.db	 
        ovsdb-tool create $OVS_PATH/etc/openvswitch/conf.db  $OVS_PATH/share/openvswitch/vswitch.ovsschema
}


fun_run_ovsdb(){
	[ $DEBUG -eq 0 ] &&  echo "fun_run_ovsdb"
	ovs-vsctl --no-wait init &
        num=`ps aux |grep -c svscan`
        if [ $num -le 1 ]
           then
               chmod  -R 755 /service/
               csh -cf '/command/svscanboot' &
               sleep 10
        fi
}

fun_add_ovsbridge(){
	[ $DEBUG -eq 0 ] &&  echo "fun_add_ovsbridge"
	ovs-vsctl --timeout=5 add-br $OVS_BRIDGE
	ovs-vsctl --timeout=5 set-fail-mode $OVS_BRIDGE $FAIL_MODE
	ovs-vsctl --timeout=5 add-port $OVS_BRIDGE $net_name -- set Interface $net_name ofport_request=2
	ovs-vsctl --timeout=5 set-controller $OVS_BRIDGE $Controller
	/sbin/ifconfig $OVS_BRIDGE $IP netmask $NETMASK up
	/sbin/ifconfig $net_name 0
	fun_reset_netgate
	$OVS_PATH/bin/ovs-vsctl  --timeout=5 show 
}

fun_recovery_network(){
	############if run fail then recovery  
	[ $DEBUG -eq 0 ] &&  echo "fun_recovery_network"
	sleep 4
	ping $CenterController   -w 5;pingResult=$?
	if [ ! $pingResult -eq 0 ]    ###online
	then
		ovs-vsctl --timeout=10 del-port $OVS_BRIDGE $net_name
		ifconfig $net_name $IP netmask $NETMASK up
		ifconfig $OVS_BRIDGE down
		ovs-vsctl --timeout=10  del-br $OVS_BRIDGE
		fun_reset_netgate
		/etc/init.d/network restart
		echo "network is recovery!"
	fi
}



#####main#######
case "$1" in
	start)
	echo -n "Starting $DESC: "
	fun_check_ovsstatus  && exit 1
	echo  "Please Wait 10s ."
	fun_install_ovsmodel
	fun_run_ovsdb
	fun_add_ovsbridge
	fun_recovery_network
        ;;
  	stop)
        echo -n "Stopping $DESC: "
	fun_del_ovsbridge
	fun_recovery_network
        ;;
	status)
	fun_check_ovsstatus
	;;
  	*)
        echo "Usage: $NAME {start|stop}" >&2
        exit 1
        ;;
esac
