CREATE DATABASE if NOT EXISTS `seata_tcc_order` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use `seata_tcc_order`;

CREATE TABLE IF NOT EXISTS `tcc_order`(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        varchar(255) DEFAULT NULL,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT 0,
    `money`          int(11) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tcc_order_tx` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tx_id` varchar(100) NOT NULL  COMMENT '事务id',
    `state` int DEFAULT NULL COMMENT '状态:0-try;1-confirm;2-cancel',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;
