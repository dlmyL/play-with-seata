CREATE database if NOT EXISTS `seata_xa_order` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `seata_xa_order`;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `xa_order`(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        varchar(255) DEFAULT NULL,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT 0,
    `money`          int(11) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;
