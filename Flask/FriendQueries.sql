DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spListFriends` (
IN p_Email varchar(50)
)
BEGIN

select username, user_id from relationship 
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

CREATE PROCEDURE `spListFriendRequests` (
IN p_Email varchar(50)
)
BEGIN

select username, user_id from relationship 
inner join tblUsers on relationship.user_two_id = tblUsers.user_id
where user_one_id = (( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email) 
	 OR
     user_two_id = ( 
     select user_id from `tscelsi`.`tblUsers` 
     where email = p_Email))
and status = 2;


END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spSendFriendRequest` (
IN p_Email varchar(50),
IN p_User varchar(50)
)
BEGIN

IF ( select exists (select 1 from `tscelsi`.`tblUsers` where username = p_User) ) THEN

INSERT INTO relationship (user_one_id, user_two_id, status)
VALUES ((select user_id from `tscelsi`.`tblUsers` where email = p_Email),
        (select user_id from `tscelsi`.`tblUsers` where username = P_User),
        2);
ELSE

select 'Username Doesnt Exist';

END IF;

END$$

DELIMITER ;

DELIMITER $$
USE `tscelsi`$$

CREATE PROCEDURE `spAcceptFriendRequest` (
IN p_Email varchar(50),
IN p_User varchar(50)
)
BEGIN

Update relationship Set `status`= 3
where (user_one_id = (select user_id from `tscelsi`.`tblUsers` where email = p_Email)
	  And 
      user_two_id = (select user_id from `tscelsi`.`tblUsers` where username = p_User))
      or
      (user_two_id = (select user_id from `tscelsi`.`tblUsers` where email = p_Email)
	  And 
      user_one_id = (select user_id from `tscelsi`.`tblUsers` where username = p_User));

END$$

DELIMITER ;

select * from relationship;
select * from tscelsi.tblUsers;


drop procedure spListFriendRequests;
drop procedure spSendFriendRequest;
drop procedure spListFriends;


Update relationship Set `status`= 2
where (user_one_id = (select user_id from `tscelsi`.`tblUsers` where email = "evan@gmail.com")
	  And 
      user_two_id = (select user_id from `tscelsi`.`tblUsers` where username = "walternam"))
      or
      (user_two_id = (select user_id from `tscelsi`.`tblUsers` where email = "evan@gmail.com")
	  And 
      user_one_id = (select user_id from `tscelsi`.`tblUsers` where username = "walternam"));
      

call spListFriends("grace@gmail.com");
call spListFriendRequests("grace@gmail.com");
call spSendFriendRequest("evan@gmail.com", "georgee" );
     