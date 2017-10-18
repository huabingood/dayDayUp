-- create database huabingood;

use huabingood;
-- 创建表
--   create table employee (
--      eid int,
--      ename string,
--        edate string
--    )
--   partitioned by (gender string)
--   row format delimited fields terminated by ',';

-- select ename from employee where gender="${hiveconf:date}" and ename="${hiveconf:name}";

select max(edate) from employee;

