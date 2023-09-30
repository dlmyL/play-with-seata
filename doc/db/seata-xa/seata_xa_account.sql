CREATE database if NOT EXISTS `seata_xa_account` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `seata_xa_account`;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `xa_account` (
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL,
    `money`   int(11) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `xa_account` VALUES (1, 'U100000', 900);

commit;