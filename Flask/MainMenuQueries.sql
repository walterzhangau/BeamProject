DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spUpdateLocation` (
IN p_Email varchar(50),
IN p_Latitude varchar(50),
IN p_Longitude varchar(50)
)
BEGIN

Update tblUsers Set 
`latitude`= p_Latitude,
`longitude`= p_Longitude
where email = p_Email;

END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spListAllFriendLocations` (
IN p_Email varchar(50)
)
BEGIN



select username,longitude,latitude from ((select distinct(user_one_id)as Users, status  from relationship where user_two_id = ( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email) and status = 3) union 
(select distinct(user_two_id), status from relationship where user_one_id = ( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email) and status = 3) order by Users) as A inner join `tscelsi`.`tblUsers` on A.Users = `tscelsi`.`tblUsers`.user_id;


END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spFriendLocation` (
IN p_User varchar(50)
)
BEGIN

select username, longitude,latitude from `tscelsi`.`tblUsers`
where username = p_User;

END$$

DELIMITER ;


