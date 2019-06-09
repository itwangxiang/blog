# ubuntu

## firewall 命令

```bash
firewall-cmd --list-ports # 查看端口
firewall-cmd --zone=public --add-port=80/tcp --permanent # 开启端口
firewall-cmd --reload # 重启防火墙
```
