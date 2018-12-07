DROP DATABASE IF EXISTS db_blog;
/*创建数据库，并设置编码*/
CREATE DATABASE db_blog DEFAULT CHARACTER SET utf8;

USE db_blog;

CREATE TABLE `t_blogger` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博主id',
  `username` VARCHAR(50) NOT NULL COMMENT '博主姓名',
  `password` VARCHAR(100) NOT NULL COMMENT '博主密码',
  `profile` TEXT COMMENT '博主信息',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '博主昵称',
  `sign` VARCHAR(100) DEFAULT NULL COMMENT '博主签名',
  `imagename` VARCHAR(100) DEFAULT NULL COMMENT '博主头像路径',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `t_blogtype` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `type_name` VARCHAR(30) DEFAULT NULL COMMENT '博客类别',
  `order_num` INT(11) DEFAULT NULL COMMENT '博客排序',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;


CREATE TABLE `t_blog` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '博客类型',
  `title` VARCHAR(200) NOT NULL COMMENT '博客题目',
  `summary` VARCHAR(400) DEFAULT NULL COMMENT '博客摘要',
  `releaseDate` DATETIME DEFAULT NULL COMMENT '发布日期',
  `clickHit` INT(11) DEFAULT NULL COMMENT '评论次数',
  `replyHit` INT(11) DEFAULT NULL COMMENT '回复次数',
  `content` TEXT COMMENT '博客内容',
  `keyWord` VARCHAR(200) DEFAULT NULL COMMENT '关键字',
  `type_id` INT(11) DEFAULT NULL COMMENT '外键关联博客类别',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `t_blog_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `t_blogtype` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

create table t_comment
(
  id int auto_increment comment '评论表id',
  user_ip varchar(50) null comment '评论者ip',
  content varchar(1000) null comment '评论内容',
  comment_date datetime null comment '评论日期',
  state int null comment '审核状态',
  blog_id int null comment '外键关联具体博客',
  constraint `PRIMARY`
  primary key (id),
  constraint t_comment_ibfk_1
  foreign key (blog_id) references t_blog (id)
)
;

create index blog_id
  on t_comment (blog_id)
;



create table t_link
(
  id int auto_increment comment '友情链接表id',
  link_name varchar(100) null comment '友情链接名',
  link_url varchar(200) null comment '友情链接url',
  order_num int null comment '链接排序',
  constraint `PRIMARY`
  primary key (id)
)
;




