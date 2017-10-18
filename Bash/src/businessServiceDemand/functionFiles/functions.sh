#!/usr/bin/env bash

#######################################################
## 所有执行的函数
## @author huabingood
#######################################################




## 获取hqlLists中数组中所有需要执行的HQL脚本，并判断该脚本是否存在，如果存在就执行
function runDaily(){
    for hqlFile in ${sqlLists[@]}
    do
        fileIsExist

        hqlName=${hqlFile%.*} # 将脚本名去除后缀
        logPath=${hqlName}_${today}.log  # 拼接日志文件名

        exDailyHive -S -f ${hqlFilesPath}/${hqlFile}

    done
}

## 日常执行执行hive时加载的配置参数（不包括程序自己设置的参数）
## 这里面输出日志的时候，必须使用hive.root.logger=INFO,DRFA.我不知道为什么使用DRFA，但是如果不使用的话就不会向新指定的日志文件中输出
function exDailyHive(){
    /opt/huabingood/pseudoDistributeHadoop/hive-1.1.0-cdh5.10.0/bin/hive "$@"\
     --hiveconf hive.root.logger=${rootLogger} \
     --hiveconf hive.log.dir=${logDir} \
     --hiveconf hive.log.file=${logPath} \
     --hiveconf startTime=${startTime} \
     --hiveconf endTime=${endTime} \
     --hiveconf rerun_term=${rerun_term}

}

## 判断文件是否存在
function fileIsExist(){

    filePath=${hqlFilesPath}/${hqlFile}

    if [ !-f ${filePaht} ]
    then
        echo -e "${hqlFile} not exists or is not a regular file." > ${logDir}/${otherLog}_${today}.log
        break
    fi
}

## 这种设置好像没有，无法使用一直报错
function aaaa(){
/opt/huabingood/pseudoDistributeHadoop/hive-1.1.0-cdh5.10.0/bin/hive "$@" \
    --hiveconf hive.root.logger=INFO,aaa \
    --hiveconf log4j.appender.aaa=org.apache.log4j.DailyRollingFileAppender \
    --hiveconf log4j.appender.aaa.Threshold=DEBUG \
    --hiveconf log4j.appender.aaa.ImmediateFlush=true \
    --hiveconf log4j.appender.aaa.Append=true\
    --hiveconf log4j.appender.aaa.File=/home/huabingood/log/BigData_Practise.log \
    --hiveconf log4j.appender.aaa.DatePattern='.'yyyy-MM-dd \
    --hiveconf log4j.appender.aaa.layout=org.apache.log4j.PatternLayout \

}