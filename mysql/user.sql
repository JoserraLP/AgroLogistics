drop user 'user'@'%'; 
flush privileges;
create user 'user'@'%' identified by 'root';
grant all privileges on *.* to 'user'@'%' with grant option;
flush privileges;