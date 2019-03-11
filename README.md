# Docs

## Catalog

* [Android](#Android)
  * [Android-基础](#Android-基础)
  * [Android-第三方库](#Android-第三方库)
* [Design-Patterns](#Design-Patterns)  
* [Other](#other)
  * [Ubuntu](#ubuntu)
  * [Mysql](#mysql)
  * [Nginx](#nginx)
  * [Git](#git)
  * [Vim](#vim)
  * [Shadowsocks](https://github.com/itwangxiang/docs/wiki/VPS.Shadowsocks)
* [Asset](#asset)
  
## Android

### Android-基础

### Android-第三方库

#### OkHttp

OkHttp 库的设计和实现的首要目标是高效
有如下优点：

* OkHttp 同步和异步调用
* OkHttp 提供了对最新的 HTTP 协议版本 HTTP/2 和 SPDY 的支持，这使得对同一个主机发出的所有请求都可以共享相同的套接字连接
* 如果 HTTP/2 和 SPDY 不可用，OkHttp 会使用连接池来复用连接以提高效率
* OkHttp 提供了对 GZIP 的默认支持来降低传输内容的大小
* OkHttp 也提供了对 HTTP 响应的缓存机制，可以避免不必要的网络请求
* 当网络出现问题时，OkHttp 会自动重试一个主机的多个 IP 地址

## Design-Patterns

[todo](https://github.com/itwangxiang/docs/issues/1)

## Other

### Ubuntu

* [Overview](docs/vps/ubuntu.md)

### Mysql

* [Overview](docs/vps/mysql.md)

### Nginx

* [Overview](docs/vps/nginx.md)

### Git

* [Overview](docs/tool/git.md)
* [设置代理](docs/tool/git-set-proxy.md)

### Vim

* [Overview](docs/tool/vim.md)

## Asset

* [Window 下设置 JAVA 环境变量](asset/set-jdk-env-variables.ps1)
* [Pac.txt](asset/pac.txt)
