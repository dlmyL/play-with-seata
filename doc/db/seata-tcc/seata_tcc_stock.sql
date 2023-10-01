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

commit;