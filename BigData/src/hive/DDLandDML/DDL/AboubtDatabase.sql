## 创建hive库
## 模板
create table [if not exists] database_name
  [comment database_comment]
  [location hdfs_path];


## 删除数据库
## 模板
## 默认是restrict,即数据库不为空无法删除。该功能在hive0.8添加
drop table [if exists] databases_name [restrict|cascade];


## 显示数据库
# 方式一
show databases;
# 方式二
show databases like 't.*';

## 显示数据库中所有的表
show tables;
# 显示自定数据库的表
show tables in database_name;
# 模糊查询表名
show tables 'abc*';

## 详细显示数据库的信息
desc database database_name;
# 使用extended字段，可以显示更多的信息
desc database extended database_name;


## 使用数据表
use database_name;

## 显示当前正在使用的数据库（可以通过hive的参数配置在CLI中显示）
select current_database();


