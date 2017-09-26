#!/bin/bash

source ./install-env.sh
source ./functions.sh

# 创建文件安装路径
creatFile ${INSTALL_PATH}

# 解压hadoop，hive到安装目录下
myTar hadoop ${INSTALL_PATH}
myTar hive ${INSTALL_PATH}

# 修改hadoop中非xml的配置文件
notXMLHadoop ${INSTALL_PATH} ${HADOOP_PROFILEPATH}
isSuccess $? "非XML修改成功" "非XML修改失败，请检查原因！！！"

# 修改hadoop中xml的配置文件
# 创建hadoop临时文件夹的存放路径
mkdir -p ${HADOOP_TMP_DIR}
alterHadoopXml "${coreSite[@]}"
alterHadoopXml "${hdfsSite[@]}"

# 设置pid的存放路径

# 格式化NameNode
formatNameNode

# 修改hive的的非xml配置文件
setHiveNotXML
isSuccess $? "非XML修改成功" "非XML修改失败，请检查原因！！！"

# 修改hive的xml的配置文件hive-site.xml
alterHiveXml "${hiveSite[@]}"

# 将Java的mysql的jar包驱动放到hive中的lib文件夹里面
hiveOver

