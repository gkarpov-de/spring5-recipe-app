# Two DB
create database if not exists sfg_dev;
create database if not exists sfg_prod;

# Two users
create user if not exists 'sfg_dev_user'@'localhost' identified by 'abrakadabra';
create user if not exists 'sfg_prod_user'@'localhost' identified by 'abrakadabra';
create user if not exists 'sfg_dev_user'@'%' identified by 'abrakadabra';
create user if not exists 'sfg_prod_user'@'%' identified by 'abrakadabra';

# Permission for Development DB
grant select on sfg_dev.* to 'sfg_dev_user'@'localhost';
grant insert on sfg_dev.* to 'sfg_dev_user'@'localhost';
grant update on sfg_dev.* to 'sfg_dev_user'@'localhost';
grant delete on sfg_dev.* to 'sfg_dev_user'@'localhost';
grant select on sfg_dev.* to 'sfg_dev_user'@'%';
grant insert on sfg_dev.* to 'sfg_dev_user'@'%';
grant update on sfg_dev.* to 'sfg_dev_user'@'%';
grant delete on sfg_dev.* to 'sfg_dev_user'@'%';

# Permission for Production DB
grant select on sfg_prod.* to 'sfg_prod_user'@'localhost';
grant insert on sfg_prod.* to 'sfg_prod_user'@'localhost';
grant update on sfg_prod.* to 'sfg_prod_user'@'localhost';
grant delete on sfg_prod.* to 'sfg_prod_user'@'localhost';
grant select on sfg_dev.* to 'sfg_dev_user'@'%';
grant insert on sfg_dev.* to 'sfg_dev_user'@'%';
grant update on sfg_dev.* to 'sfg_dev_user'@'%';
grant delete on sfg_dev.* to 'sfg_dev_user'@'%';
