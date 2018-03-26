#!/usr/bin/env bash

# this is ENV
SBIN_HOME=/d/bigData/pseudoDistributed/hadoop-2.6.0-cdh5.14.0/sbin
SPARK_SBIN_HOME=/d/bigData/pseudoDistributed/spark-2.2.0-bin-hadoop2.6/sbin

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

function start_all(){
    echo -e "\033[34m start the HDFS. \033[0m"
    bash ${SBIN_HOME}/hadoop-daemon.sh start namenode
    bash ${SBIN_HOME}/hadoop-daemon.sh start datanode

    echo -e "\033[34m start the yarn. \033[0m"
    bash ${SBIN_HOME}/yarn-daemon.sh start resourcemanager
    bash ${SBIN_HOME}/yarn-daemon.sh start nodemanager

    echo -e "\033[34m start the jobHistory. \033[0m"
    bash ${SBIN_HOME}/mr-jobhistory-daemon.sh start historyserver

}

function stop_all(){
    echo -e "\033[34m stop the jobHistory. \033[0m"
    bash ${SBIN_HOME}/mr-jobhistory-daemon.sh stop historyserver

    echo -e "\033[34m stop the yarn. \033[0m"
    bash ${SBIN_HOME}/yarn-daemon.sh stop nodemanager
    bash ${SBIN_HOME}/yarn-daemon.sh stop resourcemanager

    echo -e "\033[34m stop the HDFS. \033[0m"
    bash ${SBIN_HOME}/hadoop-daemon.sh stop datanode
    bash ${SBIN_HOME}/hadoop-daemon.sh stop namenode


}

# 启动spark
function start_spark(){
    echo -e "\033[34m start the Spark. \033[0m"
    bash ${SPARK_SBIN_HOME}/start-all.sh
}

# 关闭spark
function stop_spark(){
    echo -e "\033[34m stop the Spark. \033[0m"
    bash ${SPARK_SBIN_HOME}/stop-all.sh
}

# show pid
function show_pid(){
    echo -e "\033[34m this is PID. \033[0m"
    jps
}

# run command
case ${input} in
    1)
    start_all
    start_spark
    show_pid
    ;;
    2)
    stop_spark
    stop_all
    show_pid
    ;;
    *)
    echo "You give me the error input information."
    exit
    ;;
esac
