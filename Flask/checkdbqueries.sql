select * from `tscelsi`.`tblUsers`;

select * from relationship;

drop procedure spUserLogin;
drop procedure spCreateUser;
drop procedure spListFriendRequests;
drop procedure spSendFriendRequest;
drop procedure spListFriends;
drop procedure spFriendLocation;
drop procedure spUpdateLocation;
drop procedure spListAllFriendLocations;

drop table relationship;

Call spUpdateLocation("evan@gmail.com", "222","222");
Call spFriendLocation("evan");

call spListFriends("evan@gmail.com");
call spListFriendRequests("grace@gmail.com");
call spSendFriendRequest("evan@gmail.com", "georgee" );
call spListAllFriendLocations("evan@gmail.com");
CALL spCreateUser("George","kk","george@gmail.com");
CALL spUserLogin("evan@gmail.com","123");

SET SQL_SAFE_UPDATES = 0;