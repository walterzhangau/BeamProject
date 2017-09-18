DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spUpdateLocation` (
IN p_Email varchar(50),
IN p_Location varchar(50)
)
BEGIN

Update tblUsers Set `location`= p_Location
where email = p_Email;


END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spListAllFriendLocations` (
IN p_Email varchar(50)
)
BEGIN

select username, location from relationship 
inner join tblUsers on relationship.user_two_id = tblUsers.user_id
where user_one_id =( ( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email) 
	 OR
     user_two_id = ( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email))
and status = 3;


END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spFriendLocation` (
IN p_User varchar(50)
)
BEGIN

select username, location from `tscelsi`.`tblUsers`
where username = p_User;

END$$

DELIMITER ;

select * from tblUsers;
drop procedure spFriendLocation;
Call spFriendLocation("walternam")
