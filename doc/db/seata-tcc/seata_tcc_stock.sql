CREATE DATABASE if NOT EXISTS `seata_tcc_stock` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use `seata_tcc_stock`;

CREATE TABLE IF NOT EXISTS `tcc_stock`(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `tcc_stock` VALUES (1, 'C100000', 10);

CREATE TABLE `tcc_stock_tx` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tx_id` varchar(100) NOT NULL  COMMENT '事务id',
    `count` int DEFAULT NULL COMMENT '冻结库存',
    `state` int DEFAULT NULL COMMENT '状态:0-try;1-confirm;2-cancel',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;