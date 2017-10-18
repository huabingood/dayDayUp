#!/usr/bin/env bash

#############################################################
## 执行脚本时的所需要的参数与环境变量
## @author huabingood
#############################################################

# HQL语句执行时间,所有的时间均设置为一天以前
startTime=$(date -d "1 days ago" +%Y%m%d)
endTime=$(date -d "1 days ago" +%Y%m%d)
today=$(date +%Y%m%d)
now=$(date %Y%m%d-%H:%M:%S)

# 数据写到哪张表中
insertHiveTable="huabingood.bs_test"

# 日志相关
rootLogger="INFO,DRFA"
logDir="/home/huabingood/log"
otherLog="otherLog"

# 所有hql脚本所在的文件夹
hqlFilesPath="./HQLFiles"

# 倒数据过程中的中间磁盘路径
middlePath="/home/huabingood/"





# 测试变量，必须删除
echo ${startTime}
echo ${endTime}