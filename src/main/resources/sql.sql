CREATE SCHEMA work;
use work;
create table db_user(
  id int primary key not null auto_increment comment '主键，自增',
  account varchar(100) not null comment '账号',
  password varchar(20) null comment '密码',
  base_info json null comment '基础信息',
  sign_in json null comment '每日签到',
  weath json null comment '财富',
  friends json null comment '好友',
  gift_bag json null comment '成长礼包',
  task json null comment '任务',
  uid mediumtext null comment 'uid',
  user_name varchar(50) null comment '用户名',
  create_time date null comment '创建时间',
  last_edit_time date null comment '最后更新时间',
  generalize_uid mediumtext null comment '推广人id',
  friend_red_envelopes json null comment '红包'
)engine = InnoDB default charset = utf8 comment = '玩家数据';

CREATE TABLE db_admininfo
(
  id int PRIMARY KEY NOT NULL AUTO_INCREMENT comment '自增主键',
  timer date default null comment '当天时间',
  top_up int default 0 comment '当天充值数',
  online json default null comment '当天平均在线人数',
  regist_num int default null comment '当天注册人数',
  regist_uid mediumtext default 0 comment '当天最新注册的id',
  system_weath json default null comment '系统财富记录'
)engine = InnoDB default charset = utf8 comment = '玩家数据';


CREATE TABLE db_generalize
(
  id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  num int comment '邀请人数',
  self_uid mediumtext NOT NULL comment '自己的uid',
  target_uid mediumtext NOT NULL comment '被邀请的人的uid',
  award mediumtext comment '奖励',
  target_user_name varchar(50) not null comment '用户名',
  all_award mediumtext comment '这个人所给的奖励',
  create_time datetime comment '邀请时间'
)engine = InnoDB default charset = utf8 comment = '推广系统';

create table db_bank(
  id int primary key not null AUTO_INCREMENT,
  uid mediumtext comment 'uid',
  gold mediumtext comment '金币'
)engine = InnoDB default charset = utf8 comment = '玩家数据';

CREATE TABLE db_system_message
(
  id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  uid int comment '发起消息的uid',
  head_icon varchar(5000) comment '发起消息的头像',
  user_name varchar(50) comment '发起消息的用户名',
  vip_lv int(2) comment '发起人的vip等级 ',
  create_time datetime comment '发起时间',
  msg varchar(5000) comment '发起的消息内容'
)engine = InnoDB default charset = utf8 comment = '玩家数据';

CREATE TABLE db_red_evenlopes
(
  id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  uid mediumtext comment '发起红包的uid',
  head_icon varchar(5000) comment '发起红包的头像',
  user_name varchar(50) comment '发起红包的用户名',
  vip_lv int(2) comment '发起人的vip等级 ',
  red_evenlopes_type int(1) comment '发起的红包类型 1 普通红包 2 运气红包 3 好友红包',
  has_get int comment '是否已经领取',
  give_player json comment '被那些人领了 那个领了多少',
  num int(2) comment '红包个数',
  numed int(2) comment '已经被领了几个',
  explainx varchar(200) null comment '描述',
  residue_gold int comment '剩余的钱',
  create_time mediumtext comment '发起时间',
  last_edit_time mediumtext comment '领完的时间'
)engine = InnoDB default charset = utf8 comment = '玩家数据';


rename table user to db_user;
rename table admininfo to db_admininfo;
use work;
select account,password from db_user where account='3';
update db_user set password='xxxxx' where account='3';
use mysql;
select user,host from user where user='root';
update user set host='%' where user = 'root';
TRUNCATE TABLE db_user;