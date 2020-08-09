CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,0) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `receive_name` varchar(10) DEFAULT NULL,
  `receive_address` varchar(50) DEFAULT NULL,
  `receive_mobile` varchar(11) DEFAULT NULL,
  `receive_user` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(10) DEFAULT NULL,
  `update_user` varchar(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

