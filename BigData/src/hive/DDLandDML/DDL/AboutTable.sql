## 创建表
# 注意
1.字段名如果包含.:以及关键字的话，必须使用``来标识。
2.在hive0.13之前，字段名是不支持Unicode字符的;为了兼容，在0.13之后可以使用hive.support.quoted.identifiers=none来

-- 创建一个全新的表
1.external表示外部表，即该表删除只删除元数据信息，不会删除hdfs上的内容，必须指定手工删除
2.partition表示分区，如果加分区的话，查询只会查询分区内容，加快速度。可以多级分区。
3.row 那一行表示字段之间的分割符，hive中默认的是'\001'(即16进制的0).我们可以在vim中使用替换将该符号替换。使用Ctrl+V+A,显示的是^A.
4.line那一行表示行之间的分割符
5.collection那一段表示array,sstruct中的分割符
6.map中表示的是map这一数据类型的分隔符
7.store那一段，表示在hdfs上的存储类型
create [external] table [if not exists] [db_name.]table_name(
  column columnType comment 'string',
  column columnType comment 'string'
) [partition by (partition partitionType,partition partitionType)]
[row format delimited fields terminated by '\001']
[line terminated by ',']
[COLLECTION ITEMS TERMINATED BY '\002']
[MAP KEYS TERMINATED BY '\003']
[location 'hdfs_location']
[store as textfile];

-- 克隆表
1.有了external关键字，就必须有location关键字
create [external] table [if not exists] [db_name.]table_name
like exissting_table
[location 'hdfs_path']

-- 从一个表中查询出来部分字段
1.此时是没有办法创建external表的，也是不能用location关键字的。
create  table [if not exitst] [db_name.]table_name
[partition]
[row format]
[store as]
as select column_name,column_name from existing_table ;


## 删除表，清空数据
drop table [if exists] table_name;
# 清空表
truncate table table_name [partition partition_name='string',……];

## 修改表
-- 重命名
alter table old_table_name rename to new_table_name;

-- 修改分区
-- 添加分区(是往已有的分区中添加新的分区，比如已有分区daytime,可以添加daytime='20171213')
alter table table_name add [ if not exists ] partition(partition='string',……);
-- 删除分区 drop
alter table table_name add [ if not exists ] partition(partition='string',……);
-- 这个是真正的添加分区touch
alter table table_name touch partition partition_name;


-- 添加字段
1.删除字段的话，实际上只是删除元数据信息而已，不会删除真实的字段的值。
alter table table_name add column (column_name column_type,……);

