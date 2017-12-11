#!/bin/bash

###############################
# 判断hive中某个分区是否存在    #
#                             #
# @author huabingood          #
# @date 20171211              #
###############################
source /home/huabingood/.bashrc

## 环境变量
hiveDB='sdcupdb'
hiveTable='sd_event_tbl'

yesterday=`date -d "-1 day" +"%Y%m%d"`





## 方案一：可能存在bug
## 将所有的内容按照=号分割，
## 通常观测到最后一个分区的值通过是最新添加的，但没有定论，这里存在bug
## 获取hive表中的所有分区
#hivePartitons=$( hive -e "show partitions ${hiveDB}.${hiveTable};")
#findPartitions=$( echo ${hivePartitons} | awk -F "=" '{print $NF}' )

#if [ ${findPartitions} -eq ${yesterday} ];then
#    sleep 1800
#    #TODO 这里调用主脚本执行

#fi
###############################
## 方案二：
## 将查询出来的结果写到本地文件中，然后遍历读取文件中的行，判断查找的字符串是否存在

## 查询分区，将查询结果写到本地
middle="/home/huabingood"
partitionString="statdate=2017-10-23"
hive -e "show partitions ${hiveDB}.${hiveTable};">${middle}/partitions_${yesterday}.txt

while read line
do
    result=$( echo "${line}" | grep "${partitionString}" )

    if [ -n "${result}" ];then
        sleep 1800

        TODO 这里调用主调脚本

        rm ${middle}/partitions_${yesterday}.txt
        exit 0
    fi
done < ${middle}/partitions_${yesterday}.txt
