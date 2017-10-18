#!/usr/bin/env bash

###################################################
## 将HQLFiles中所有要跑的脚本写到此数组中
## 脚本名称使用英文双引号，数组间的分隔符使用英文空格
## 建议所有脚本名称以.hql结尾
## 脚本名会是指标名，也是mysql数据库中的表名
## @author huabingood
###################################################

## 正常执行的脚本
# 例如：sqlLists=("abc.sql" "test.sql")
sqlLists=("abc.sql" "test.sql")


## 重跑的脚本，注意这里的起止时间是针对整个重跑脚本的
# 例如：rerunSqlLists=("test.sql")
rerunSqlLists=("test.sql")
# 重跑开始时间
# 例如：rerunStartTime="20171018"
rerunStartTime="20171018"
# 重跑结束时间
# 例如：rerunEndTime="20171029"
rerunEndTime="20171029"
