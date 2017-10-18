-- 在hive中创建总结果表 bs_result_total
use irscupdb;

create table bs_result_total(
  rerun_term string, -- 主要记录批量重跑的时间，格式为20171018-20171019
  intnl_org_nm string,
  root_ins_nm string,
  at_seg string , -- 从这以下的内容：case when语句没有加别名
  trans_chnl string,
  trans_id string, -- 从这往上的内容：case when语句没有加别名
  mcc_group string,
  bishu string,
  jine string,
  EXT_CUPS_DISC_AT string,
  BRAND_DISC_AT string,
  shouru string
) partitioned by (settle_dt string,bs_index string)


-- 创建mysql表
create table bs_result_total(
  rerun_term varchar(30),
  intnl_org_nm varchar(100),
  root_ins_nm varchar(100),
  at_seg varchar(10),
  trans_chnl varchar(10),
  trans_id varchar(10),
  mcc_group VARCHAR(20),
  bishu DECIMAL(7,2),
  jine DECIMAL(15,4),
  EXT_CUPS_DISC_AT DECIMAL(15,4),
  BRAND_DISC_AT DECIMAL(15,4),
  shouru DECIMAL(15,4),
  settle_dt varchar(15),
  bs_index VARCHAR(50)
)


-- 将数据下载到本地磁盘中
hive -e "select * from bs_result_total where settle_dt=${startTime};" >${middlePath}/middle.txt
-- 加载到mysql表中
expysql -s "load data local infile '${middlePath}/middle.txt' into table bs_result_total;" > ${logDir}/${otherLog}_${today}.log

-- 执行成功后删除文件
rm ${middlePath}/middle.txt > ${logDir}/${otherLog}_${today}.log
