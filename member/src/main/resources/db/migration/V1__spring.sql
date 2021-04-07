/*用户表 */
DROP TABLE IF EXISTS `user`;
create table `userinfo`(
   `id` int(11) primary key auto_increment,
   `username` varchar(255),
   `password` varchar(255),
   `salt` varchar(255),
   `enabled` tinyint(1) default '1'
   ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into userinfo(`username`,`password`,`salt`) values ('admin','038bdaf98f2037b31f1e75b5b4c9b26e','admin');