#!/bin/bash

INSTALL_PATH="/opt/hadoop/pseudoDistributeHadoop"  # 请输入安装hadoop的绝对路径。改路径下应该没有内容！
# mysql的配置情况
mysqlIP="192.168.11.101"
mysqlPort="3306"
mysqlDatabase="hive_test"
mysqlUser="root"
mysqlPwd="123456"
mysqlConnetor="mysql-connector-java-5.1.42-bin.jar"



# 以下内容请不要修改！！！
HADOOP_PROFILEPATH="/etc/hadoop"
# hadoop临时文件的存放路径
HADOOP_TMP_DIR="${INSTALL_PATH}/tmp/hadoop"
HIVE_TMP_DIR="${INSTALL_PATH}/tmp/hive"
# 这里存放自己的JAVA_HOME
MY_JAVA_HOME="/opt/softWare/jdk1.8.0_152"
# 需要往一下文件中修改其中的JAVA_HOME
myArray=("hadoop-env.sh" "mapred-env.sh" "yarn-env.sh")

# hadoop中需要修改的xml配置文件的文件名和修改属性
coreSite=("core-site.xml" "fs.defaultFS" "hdfs://`hostname`:9000" "hadoop.tmp.dir" "${HADOOP_TMP_DIR}")
hdfsSite=("hdfs-site.xml" "dfs.replication" "1")

# hive日志文件的存放路径
hiveLogs="${INSTALL_PATH}/tmp/hive_logs"
# hive-site.xml文件的配置
hiveSite=("hive-site.xml" "javax.jdo.option.ConnectionURL" "jdbc:mysql://${mysqlIP}:${mysqlPort}/${mysqlDatabase}?createDatabaseIfNotExist=true&useSSL=false&characterEncoding=utf8" "javax.jdo.option.ConnectionDriverName" "com.mysql.jdbc.Driver" "javax.jdo.option.ConnectionUserName" "${mysqlUser}" "javax.jdo.option.ConnectionPassword" "${mysqlPwd}" "hive.exec.scratchdir" "${HIVE_TMP_DIR}" "system:java.io.tmpdir" "${HIVE_TMP_DIR}" "hive.cli.print.header" "true" "hive.cli.print.current.db" "true" "hive.metastore.warehouse.dir" "/opt/hadoop/warehouse")


