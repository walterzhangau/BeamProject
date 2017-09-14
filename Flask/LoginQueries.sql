CREATE TABLE IF NOT EXISTS   `tscelsi`.`tblUser`  (
  `id` INT NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(16) NOT NULL,
  `phone` VARCHAR(16) NULL,
  `email` VARCHAR(255) NULL,
  `Password` VARCHAR(40) NOT NULL,
  `first_name` VARCHAR(20) NULL,
  `last_name` VARCHAR(20) NULL,

PRIMARY KEY (`id`))
ENGINE = InnoDB;


DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spCreateUser` (
IN p_Username varchar(50),
IN p_Password varchar(50)
)
BEGIN

if ( select exists (select 1 from tblUser where UserName = p_username) ) THEN

    select 'Username Exists !!';

ELSE

insert into tblUser
(
    UserName,
    Password
)
values
(
    p_Username,
    p_Password
);

END IF;

END$$

DELIMITER ;

select * from tblUser;
CALL spCreateUser("Grace","paswordboi68");

CALL spUserLogin("evan","12345");

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spUserLogin` (
IN p_Username varchar(50),
IN p_Password varchar(50)
)
BEGIN

if ( select exists (select 1 from tblUser where UserName = p_username AND Password = p_password ) ) THEN

    select 'Success';

ELSE

select 'Fail';

END IF;

END$$

DELIMITER ;