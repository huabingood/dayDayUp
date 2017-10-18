#!/usr/bin/env bash

#########################################################
## 主调脚本
## @author huabingood
#########################################################

source ./functionFiles/env.sh
source ./hqlLists.sh
source ./functionFiles/functions.sh

## 判断对方是日常定时任务还是从跑脚本
# 1 代表日常定时任务
# 2 代表重跑某些指标

case $1 in
    1)
    rerun_term=""
    runDaily
    ;;
    2)
    rerun_term="${rerunStartTime}-${rerunEndTime}"
    startTime=${rerunStartTime}
    endTime=${rerunEndTime}
    runDaily
    ;;
    *)
    ehco -e "\033[31m you give a wrong number.the 1 is run daily and 2 ir rerun.\033[0m"
    echo -e "the param run dail or rerun is unknow.\n" > ${logDir}/${otherLog}_${today}.log
esac

# 将数据从hive表中导入到mysql中