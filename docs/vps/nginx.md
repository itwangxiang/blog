
# 基础

## 安装

```bash
#!apt-get install nginx
```

## 卸载

```bash
sudo apt-get remove nginx nginx-common # 卸载删除除了配置文件以外的所有文件。
sudo apt-get purge nginx nginx-common # 卸载所有东东，包括删除配置文件。
sudo apt-get autoremove # 在上面命令结束后执行，主要是卸载删除Nginx的不再被使用的依赖包。
sudo apt-get remove nginx-full nginx-common #卸载删除两个主要的包。
```

## 配置

> 将 SSL 签名保存 /etc/nginx/certs/...

### 新建

```bash
cd /etc/nginx/conf.d/
touch ***.conf
```

### 编辑

```bash
vi /etc/nginx/conf.d/***.conf
```

```text
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
