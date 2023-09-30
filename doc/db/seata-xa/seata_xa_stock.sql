CREATE database if NOT EXISTS `seata_xa_stock` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `seata_xa_stock`;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `xa_stock`(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `xa_stock` VALUES (1, 'C100000', 10);

commit;