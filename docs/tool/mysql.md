# mysql

## 安装

```bash
sudo apt-get install mysql-server mysql-client
```

## 查看端口

```bash
sudo netstat -tap | grep mysql
```

## 设置初始密码

```bash
sudo mysql_secure_installation
```

## 登录

```bash
mysql -u root -ppassword
```

## 常用SQL

```mysql
-- 设置密码安全等级
set global validate_password_policy=0;
-- 创建库
Create DATABASE IF NOT EXISTS `todev_dev` default charset utf8 COLLATE utf8_general_ci;
-- 设置 todev_dev 访问密钥
-- create user `todev_dev` identified by '111111';
-- grant all privileges on `todev_dev`.* to `todev_dev`@`%` identified by '111111';
ALTER USER 'todev_dev'@'%' IDENTIFIED BY '111111';
```

## 常见问题

### 无法远程连接

```bash
## 打开配置文件
vi /etc/mysql/mysql.conf.d/mysqld.cnf
## 注释 bind-address
#bind-address = 127.0.0.1
## 重启
service mysql restart
```
