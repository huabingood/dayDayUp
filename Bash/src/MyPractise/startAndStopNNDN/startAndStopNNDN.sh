#!/usr/bin/env bash


# insersting this prompt information can't display in the IDEA but use CLI and the echo .
# read -p 'start or stop? 1 is start; 2 is stop;please:' input

# get the choos close or start the NN and DN.
echo "start or stop? 1 is start; 2 is stop;please:"
read input

# this can't use.
# if the command run right
function isCommandRunRight(){
    if [ $1 -ne 0 ]
    then
        echo "${2} can ${3} right."
    else
        echo -e "\e[1;31m${2} can't ${3} right. Have fun!\e[0m"
    fi
}

# run command
case ${input} in
    1)
    echo "You choose start the NN and DN."
    bash /opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/sbin/hadoop-daemon.sh start namenode
    # isCommandRunRight $? NN start
    bash /opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/sbin/hadoop-daemon.sh start datanode
    # isCommandRunRight $? DN start
    ;;
    2)
    echo "You choose stop the NN and DN."
    bash /opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/sbin/hadoop-daemon.sh stop datanode
    # isCommandRunRight $? DN stop
    bash /opt/huabingood/pseudoDistributeHadoop/hadoop-2.6.0-cdh5.10.0/sbin/hadoop-daemon.sh stop namenode
    # isCommandRunRight $? NN stop
    ;;
    *)
    echo "You give me the error input information."
    exit
    ;;
esac
