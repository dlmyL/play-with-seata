CREATE DATABASE if NOT EXISTS `seata_xa_account` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use `seata_xa_account`;

CREATE TABLE IF NOT EXISTS `xa_account` (
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL,
    `money`   int(11) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `xa_account` VALUES (1, 'U100000', 900);

commit;