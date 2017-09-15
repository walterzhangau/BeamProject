DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spCreateUser` (
IN p_Username varchar(50),
IN p_Password varchar(50),
IN p_Email varchar(50)
)
BEGIN

if ( select exists (select 1 from `tscelsi`.`tblUsers` where username = p_Username) ) THEN

    select 'Username Exists !!';

ELSE

insert into `tscelsi`.`tblUsers`
(
    username,
    password,
    email

)
values
(
    p_Username,
    p_Password,
    p_Email
);

END IF;

END$$

DELIMITER ;


DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spUserLogin` (
IN p_Email varchar(50),
IN p_Password varchar(50)
)
BEGIN

if ( select exists (select 1 from tblUsers where email = p_Email AND password = p_Password ) ) THEN

    select 'Success';

ELSE

select 'Fail';

END IF;

END$$

DELIMITER ;