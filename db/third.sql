database: dataflow

CREATE TABLE `all_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `third_order_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '0京东，1天猫',
  `total_amount` decimal(19,2) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `all_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `third_order_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '0京东，1天猫',
  `total_amount` decimal(19,2) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `jd_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



