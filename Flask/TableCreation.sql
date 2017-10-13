CREATE TABLE IF NOT EXISTS `tscelsi`.`tblUsers` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(256) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `first_name` VARCHAR(20) NULL,
  `last_name` VARCHAR(20) NULL,
  `phone` VARCHAR(16) NULL,
  `location` VARCHAR(20) NULL,
  PRIMARY KEY(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `relationship` (
  `user_one_id` INT(10) UNSIGNED NOT NULL,
  `user_two_id` INT(10) UNSIGNED NOT NULL,
  `status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0',
  FOREIGN KEY (`user_one_id`)    REFERENCES `tscelsi`.`tblUsers`(`user_id`),
  FOREIGN KEY (`user_two_id`)    REFERENCES `tscelsi`.`tblUsers`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `relationship`
ADD UNIQUE KEY `unique_users_id` (`user_one_id`,`user_two_id`);

ALTER TABLE `tblUsers`
ADD `longitude` varchar(20);
ALTER TABLE `tblUsers`
ADD `latitude` varchar(20);

