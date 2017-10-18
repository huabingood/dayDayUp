#!/usr/bin/env bash

date="20171008"
ename="huabingood"
/opt/huabingood/pseudoDistributeHadoop/hive-1.1.0-cdh5.10.0/bin/hive -hiveconf date=${date} -hiveconf name=${ename}  -f /opt/huabingood/practise/everyDayLanguagePractise/Bash/src/businessServiceDemand/HQlFiles/abc.sql
echo "aaa"

/opt/huabingood/pseudoDistributeHadoop/hive-1.1.0-cdh5.10.0/bin/hive -hiveconf date=${date} -hiveconf name=${ename}  -f /opt/huabingood/practise/everyDayLanguagePractise/Bash/src/businessServiceDemand/HQlFiles/test.sql