#!/usr/bin/env bash

hiveTables=`hive -e "show tables;"`




for i in ${hiveTables}
do
   echo ${i} | grep "kylin"

   if [[ $? -eq 0 ]];then
    hive -e "drop table if exists ${i};"
   fi

done
