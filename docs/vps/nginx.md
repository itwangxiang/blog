
# Nginx

## 安装/卸载

### ubuntu

安装

```bash
#!apt-get install nginx
```

卸载

```bash
apt-get remove nginx nginx-common # 卸载删除除了配置文件以外的所有文件。
apt-get purge nginx nginx-common # 卸载所有东东，包括删除配置文件。
apt-get autoremove # 在上面命令结束后执行，主要是卸载删除Nginx的不再被使用的依赖包。
apt-get remove nginx-full nginx-common #卸载删除两个主要的包。
```

### centos

安装/卸载

```bash
## 安装
yum install nginx
systemctl start nginx.service
## 卸载
systemctl stop nginx.service
systemctl disable nginx.service
rm -rf /usr/sbin/nginx
rm -rf /etc/nginx
rm -rf /etc/init.d/nginx
yum remove nginx
```

## 常用配置

### 设置 HTTPS

将 SSL 签名保存 `/etc/nginx/certs/*`

新建 `/etc/nginx/conf.d/***.conf`

内容如下：

```xml
server {
    listen 443;
    server_name www.todev.cn;
    access_log /var/log/nginx/www.todev.cn.access.log;

    ssl on;
    ssl_certificate     certs/www.todev.cn.crt;
    ssl_certificate_key certs/www.todev.cn.key;
    location / {
        proxy_pass   http://127.0.0.1:3000/;
    }
}

server {
    listen 80;
    server_name  todev.cn;
    return 301 https://www.todev.cn$request_uri;
}
```
