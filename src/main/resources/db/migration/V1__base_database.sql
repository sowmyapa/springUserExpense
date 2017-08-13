CREATE TABLE `user` (
  `user_id` varchar(100) NOT NULL PRIMARY KEY,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `expense` (
  `user_id` varchar(100),
  `expense_id` varchar(100) NOT NULL PRIMARY KEY,
  `expense_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `expense_amount` DOUBLE DEFAULT NULL,
  `expense_vat` DOUBLE DEFAULT NULL,
  `expense_reason` varchar(100) DEFAULT NULL,
   FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;