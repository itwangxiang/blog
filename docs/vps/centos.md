# Centos

## [Shadowsocks](https://github.com/shadowsocks/shadowsocks/blob/master/README.md)

### 安装

```bash
yum install python-setuptools && easy_install pip
pip install shadowsocks
```

### 使用

```bash
ssserver -p 443 -k password -m rc4-md5 # 前台启动
sudo ssserver -p 443 -k password -m rc4-md5 --user nobody -d start # 后台启动
sudo ssserver -d stop # 停止
sudo less /var/log/shadowsocks.log # 查看日志
```

#### [注：使用配置文件](https://github.com/shadowsocks/shadowsocks/wiki/Configuration-via-Config-File)

创建配置文件 `/etc/shadowsocks.json` ，例如：

```json
{
    "server":"my_server_ip",
    "server_port":8388,
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"mypassword",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}
```

前台运行：

```bash
ssserver -c /etc/shadowsocks.json
```

后台运行：

```bash
ssserver -c /etc/shadowsocks.json -d start #启动
ssserver -c /etc/shadowsocks.json -d stop #停止
```

### BBR 加速

[一键安装最新内核并开启 BBR 脚本](https://github.com/teddysun/across)

```bash
wget --no-check-certificate https://github.com/teddysun/across/raw/master/bbr.sh && chmod +x bbr.sh && ./bbr.sh
```

### 开机启动

创建 `/etc/systemd/system/shadowsocks.service` , 内容如下：

```text
[Unit]
Description=Shadowsocks

[Service]
TimeoutStartSec=0
ExecStart=/usr/bin/ssserver -c /etc/shadowsocks.json

[Install]
WantedBy=multi-user.target
```

设置文件权限

```bash
chmod +x /etc/systemd/system/shadowsocks.service
```

设置开机启动

```bash
systemctl enable shadowsocks.service
```

测试服务

```bash
systemctl start shadowsocks # 启动
systemctl status shadowsocks # 查看状态
```

## 其他常用

### 修改密码

```bash
passwd
```

### firewall 命令 （centos 默认会开启防火墙，需要开启端口才能被访问）

```bash
firewall-cmd --list-ports # 查看端口
firewall-cmd --zone=public --add-port=80/tcp --permanent # 开启端口
firewall-cmd --reload # 重启防火墙
```
