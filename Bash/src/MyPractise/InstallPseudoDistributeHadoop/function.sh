#!/bin/bash

source ./install-env.sh

# 创建文件夹，并清空文件夹
function creatFile(){
	if [ -e $1 ]
	then
		echo "文件夹已经存在！将清空${1}!!!\n"
		isContinue "清空${1}" "将要清空${1}"
		rm -rf ${1}/*
		isSuccess $? "${1}清空成功，准备解压文件。" "${1}清空失败，请检查原因！！！"
	else
		mkdir ${1}
		isSuccess $? "${1}创建成功，准备解压文件。" "${1}创建失败，请检查原因！！！"
	fi
}

# 询问是否继续进项操作
function isContinue(){
	echo -e "\e[1;31m是否${1}【y/n】\e[0m"
	read choose
	if [ "${choose}" = "y" ] || [ "${choose}" = "yes" ] || [ "${choose}" = "Y" ] || [ "${choose}" = "YES" ]
	then
		echo -e "\e[1;31m${1}\e[0m"
	else
		exit
	fi
}

# 判断前一个操作是否成功，如果错误就全部推倒重来
function isSuccess(){
	if [ $1 -eq 0 ]
	then
		echo "${2}"
	else
		echo -e "\e[1;31m${3}\e[0m"
		rm -rf ${INSTALL_PATH}
		exit
	fi
}

# 解压缩文件
function myTar(){
	filePath=`find ../software -name ${1}* | awk -F "/" '{print $NF}'`
	tar -zxvf ../software/${filePath} -C ${2} 1>>/dev/null 2>>/dev/null
	isSuccess $? "${1}解压成功。" "${2}解压失败，请查找原因！！！"
}

# 修改hadoop的非xml类的配置文件
function notXMLHadoop(){
	filePath=`ls ${1} | grep hadoop`
	for i in ${myArray[@]}
	do
		sed -i '/export JAVA_HOME=\$/d' ${1}/${filePath}${2}/${i}
		sed -i "\$a export JAVA_HOME=${MY_JAVA_HOME}" ${1}/${filePath}${2}/${i}
	    isSuccess $? "$i修改成功" "$i修改失败"
	done

	# 修改slaves文件
	sed -i "1a `hostname`" ${1}/${filePath}${2}/slaves
	isSuccess $? "slaves修改成功" "slaves修改失败"
	sed -i '1d' ${1}/${filePath}${2}/slaves
}

# 修改hadoop中的xml类的配置文件
function alterHadoopXml(){

	myArray=($@)
	filePath=`ls ${INSTALL_PATH} | grep hadoop`
	for ((i=1;i<${#myArray[@]};i+=2))
	do
		j=$((i+1))
		java -jar ../lib/JavaStudy.jar /${INSTALL_PATH}/${filePath}${HADOOP_PROFILEPATH}/${myArray[0]} add ${myArray[$i]} ${myArray[$j]}
		isSuccess $? "${myArray[0]}中的${myArray[$i]}修改成功" "${myArray[0]}中的${myArray[$i]}修改失败，请检查原因！！！"
	done
}

# 修改当前用户的.bashrc文件，添加hadoop中pid等配置的存放路径
function alterBashrc(){
	bashrcPathe=~/.bashrc
	mkdir ${HADOOP_TMP_DIR}/myPID
	mkdir ${NameNodeDir}
	mkdir ${DataNodeDir}
	sed -i "/myPIDDir=}/d" ${bashrcPathe}
	sed -i "\$a myPIDDir=${HADOOP_TMP_DIR}" ${bashrcPathe}
	sed -i "/export HADOOP_PID_DIR=${myPIDDir}/d" ${bashrcPathe}
	sed -i '$a export HADOOP_PID_DIR=${myPIDDir}' ${bashrcPathe}
	sed -i '/export YARN_PID_DIR=${myPIDDir}/d' ${bashrcPathe}
	sed -i '$a export YARN_PID_DIR=${myPIDDir}' ${bashrcPathe}
	sed -i '/export HADOOP_MAPRED_PID_DIR=${myPIDDir}/d' ${bashrcPathe}
	sed -i '$a export HADOOP_MAPRED_PID_DIR=${myPIDDir}' ${bashrcPathe}
	sed -i '/export HBASE_PID_DIR=${myPIDDir}/d' ${bashrcPathe}
	sed -i '$a export HBASE_PID_DIR=${myPIDDir}' ${bashrcPathe}

	source ~/.bashrc
}

# 格式化NameNode
function formatNameNode(){
	filePath=`ls ${INSTALL_PATH} | grep hadoop`
	${INSTALL_PATH}/${filePath}/bin/hdfs namenode -format 1>>/dev/null 2>>/dev/null
	isSuccess $? "NameNode格式化成功！" "NameNode格式化失败，请查找原因！！！"
}

# 配置好hive的配置文件
function setHiveNotXML(){
	echo "开始修改hive的非xml配置文件"
	filePath=`ls ${INSTALL_PATH} | grep hive`
	cp ${INSTALL_PATH}/${filePath}/conf/hive-env.sh.template ${INSTALL_PATH}/${filePath}/conf/hive-env.sh
	cp ${INSTALL_PATH}/${filePath}/conf/hive-exec-log4j.properties.template ${INSTALL_PATH}/${filePath}/conf/hive-exec-log4j.properties
	cp ${INSTALL_PATH}/${filePath}/conf/hive-log4j.properties.template ${INSTALL_PATH}/${filePath}/conf/hive-log4j.properties
	touch  ${INSTALL_PATH}/${filePath}/conf/hive-site.xml
	echo '<?xml version="1.0" encoding="UTF-8" standalone="no"?>' > ${INSTALL_PATH}/${filePath}/conf/hive-site.xml
	sed -i '1a <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>' ${INSTALL_PATH}/${filePath}/conf/hive-site.xml
	sed -i '2a <configuration>' ${INSTALL_PATH}/${filePath}/conf/hive-site.xml
	sed -i '3a </configuration>' ${INSTALL_PATH}/${filePath}/conf/hive-site.xml
	# 修改hive-env.sh
	hadoopPath=`ls ${INSTALL_PATH} | grep hadoop`
	sed -i "\$a HADOOP_HOME=${INSTALL_PATH}/${hadoopPath}" ${INSTALL_PATH}/${filePath}/conf/hive-env.sh
	sed -i "\$a HIVE_CONF_DIR=${INSTALL_PATH}/${filePath}/conf" ${INSTALL_PATH}/${filePath}/conf/hive-env.sh
	sed -i "\$a export JAVA_HOME=${MY_JAVA_HOME}" ${INSTALL_PATH}/${filePath}/conf/hive-env.sh

	# 修改hive-log4j.properties文件
	mkdir ${hiveLogs}
	sed -i "s@hive.log.dir=.*@hive.log.dir=${hiveLogs}@g" ${INSTALL_PATH}/${filePath}/conf/hive-log4j.properties
}

# 修改hive的xml文件
function alterHiveXml(){
	myArray=($@)
	filePath=`ls ${INSTALL_PATH} | grep hive`
	for ((i=1;i<${#myArray[@]};i+=2))
	do
		j=$((i+1))
		java -jar ../lib/JavaStudy.jar /${INSTALL_PATH}/${filePath}/conf/${myArray[0]} add ${myArray[$i]} ${myArray[$j]}
		isSuccess $? "${myArray[0]}中的${myArray[$i]}修改成功" "${myArray[0]}中的${myArray[$i]}修改失败，请检查原因！！！"
	done
}

# 创建hive的配置文件夹，并Java的mysql驱动jar包复制到hive中的lib文件夹中
function hiveOver(){
	mkdir ${HIVE_TMP_DIR}
	filePath=`ls ${INSTALL_PATH} | grep hive`
	cp ../lib/${mysqlConnetor} ${INSTALL_PATH}/${filePath}/lib
	isSuccess $? "hadoop与hive的伪分布式安装成功，just have fun!" "复制mysql的jar包失败，请检查原因！！！"
}

















