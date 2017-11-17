#!/usr/bin/env bash

## 数据链接使用到的各种参数
# 数据中间落地路径及文件
middlePath="/home/huabingood/middle.txt"

# 数据从哪里来
fromDB="my_test"
fromTable="test1"
fromHost="192.168.30.101"
fromPort="3306"
fromUser="root"
fromPassword="123456"
fromCmd="select a from test1;"

# 数据到哪里去
toDB="my_test"
toTable="test3"
toHost="192.168.30.101"
toPort="3306"
toUser="root"
toPassword="123456"
toCmd="load data local infile '${middlePath}' replace into table ${toTable};"




######################## 具体执行数据从哪里来 #################

## 执行来源数据库相关的内容
function executeFromCmd(){
    mysql -u ${fromUser} -h ${fromHost} -P ${fromPort} -D ${fromDB} -p${fromPassword} -e "$1"
}

## 执行mysql语句，下载数据，如果执行成功，就清空数据表
function loadData(){

    executeFromCmd 'select a from test1;' > ${middlePath}

    if [ $? -ne 0  ];then
        echo "从${fromDB}中的${fromTable}表中下载数据存在问题，请关注问题所在!"
        exit 1
    else
        # echo "aaaa"
        executeFromCmd "truncate ${fromTable};"
    fi
}
## 如果文件为空的话，就退出;如果文件不为空就删除文件的第一行
function dealDataFile(){
    if [  ! -s "${middlePath}" ] ;then
        echo "数据库中没有数据，正常退出！"
        exit 0
    else
        sed -i "1d" ${middlePath}
    fi
}

################## 具体执行数据到哪里去 ##########################
# 执行mysql命令
function execute2Cmd(){
    mysql -u ${toUser} -h ${toHost} -P ${toPort} -D ${toDB} -p${toPassword} -e "$1"
}

# 将数据去重追加到指定数据库的表中,追加成功将中间文件删除
function inputData(){
    execute2Cmd "${toCmd}"

    if [ $? -ne 0 ] ;then
        echo "追加失败！"
        exit 2
    else
        rm ${middlePath}
    fi

}


################## 调用函数，执行整个命令 ###################
loadData

dealDataFile

inputData
