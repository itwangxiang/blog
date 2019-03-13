# Docs

## Catalog

* [Android](#Android)
  * [Android-基础](#Android-基础)
  * [Android-第三方库](#Android-第三方库)
    * [OkHttp](#OkHttp)
    * [ButterKnife](#ButterKnife)
    * [Rxjava](#Rxjava)
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

#### [OkHttp](https://github.com/square/okhttp)

> An HTTP+HTTP/2 client for Android and Java applications

* OkHttp 支持同步调用和异步调用
* OkHttp 提供了对最新的 HTTP 协议版本 HTTP/2 和 SPDY 的支持，这使得对同一个主机发出的所有请求都可以共享相同的套接字连接
* 如果 HTTP/2 和 SPDY 不可用，OkHttp 会使用连接池来复用连接以提高效率
* OkHttp 提供了对 GZIP 的默认支持来降低传输内容的大小
* OkHttp 也提供了对 HTTP 响应的缓存机制，可以避免不必要的网络请求
* 当网络出现问题时，OkHttp 会自动重试一个主机的多个 IP 地址

#### [ButterKnife](https://github.com/JakeWharton/butterknife)

> Bind Android views and callbacks to fields and methods

* 通过在字段上使用 `@BindView` 消除 `findViewById` 回调
* 在列表或数组中组合多个视图。一次性使用操作，设置器或属性操作它们
* 通过使用`@OnClick`和其他方法注释方法来消除侦听器的匿名内部类
* 通过在字段上使用资源注释来消除资源查找

#### [Rxjava](https://github.com/ReactiveX/RxJava)

> RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM

* 异步、简洁（逻辑、代码读写）
* 含有大量操作符

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
