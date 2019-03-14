# Docs

## Catalog

* [Android](#Android)
  * [基础篇](#基础篇)
  * [原理篇](#原理篇)
  * [核心篇](#核心篇)
  * [开源篇](#开源篇)
* [Design-Patterns](#Design-Patterns)  
* [System,Software,Tool](#System,Software,Tool)
  * [Ubuntu](#ubuntu)
  * [Mysql](#mysql)
  * [Nginx](#nginx)
  * [Git](#git)
  * [Vim](#vim)
  * [Shadowsocks](https://github.com/itwangxiang/docs/wiki/VPS.Shadowsocks)
* [Asset](#asset)
  
## Android

### 基础篇

* 四大组件

  * Activity
  * Service
  * BroadcastReceiver
  * Content Provider

* 布局

  * LinearLayout
  * RelativeLayout
  * FrameLayout
  * TableLayout  

* 其他

  * AlertDialog,popupWindow,Activity 的区别
  * Application 和 Activity 的 Context 对象的区别
  * BroadcastReceiver，LocalBroadcastReceiver 的区别

### 原理篇

* View 刷新机制，绘制流程，以及事件传递分发机制
* Touch 事件传递流程
* Handler 机制和底层实现
* AsyncTask 机制，原理以及不足
* ThreadLocal原理
* LruCache 是什么，以及默认缓存大小
* ANR 产生的原因，以及如何定位和修正
* OOM 是什么？以及如何避免？
* 内存泄漏和内存溢出区别？
* 为什么不能在子线程更新UI
* 线程池有没有上限

### 核心篇
  
* Activies
* 架构组件-Architecture Components
* Intent 和 Intent 过滤器
* 界面和导航
* 动画和过度
* 音频和视频
* 后台任务
* 应用数据和文件
* 用户数据和身份
* 用户位置
* 触摸和输入
* 相机
* 传感器
* 网络连接
* 基于网络的内容（WebView）

### 开源篇

* [OkHttp](https://github.com/square/okhttp)

  > 适用于 Android 和 Java 应用程序的 HTTP + HTTP/2 客户端

  * OkHttp 支持同步调用和异步调用
  * OkHttp 提供了对最新的 HTTP 协议版本 HTTP/2 和 SPDY 的支持，这使得对同一个主机发出的所有请求都可以共享相同的套接字连接
  * 如果 HTTP/2 和 SPDY 不可用，OkHttp 会使用连接池来复用连接以提高效率
  * OkHttp 提供了对 GZIP 的默认支持来降低传输内容的大小
  * OkHttp 也提供了对 HTTP 响应的缓存机制，可以避免不必要的网络请求
  * 当网络出现问题时，OkHttp 会自动重试一个主机的多个 IP 地址

* [Retrofit](https://github.com/square/retrofit)
  
  > 适用于 Android 和 Java 的类型安全的 HTTP 客户端

  * 默认基于 OkHttp 封装的一套 RESTful 网络请求框架
  * 通过注解直接配置请求
  * 使用不同 Json Converter 来序列化数据
  * 提供对 RxJava 的支持

* [Glide](https://github.com/bumptech/glide)  

  > Glide是一个快速高效的Android图片加载库，注重于平滑的滚动

  * Glide 支持拉取，解码和展示视频快照，图片，和GIF动画
  * Glide 使用简明的流式语法API

* [ButterKnife](https://github.com/JakeWharton/butterknife)

  > 将 Android 视图和回调绑定到字段和方法

  * 通过在字段上使用 `@BindView` 消除 `findViewById` 回调
  * 在列表或数组中组合多个视图。一次性使用操作，设置器或属性操作它们
  * 通过使用`@OnClick`和其他方法注释方法来消除侦听器的匿名内部类
  * 通过在字段上使用资源注释来消除资源查找

* [Rxjava](https://github.com/ReactiveX/RxJava)

  > RxJava  -  JVM的Reactive Extensions  - 一个使用 Java VM 的可观察序列组成异步和基于事件的程序的库

  * 基于事件流的链式调用、逻辑简洁 & 使用简单
  * 扩展了观察者模式，以支持数据/事件序列，并增加了操作符，他可以将将序列清晰的组合在一起的

* [Logger](https://github.com/orhanobut/logger)

  > 简单，漂亮，功能强大的 android 记录器

## Design-Patterns

[todo](https://github.com/itwangxiang/docs/issues/1)

## System,Software,Tool

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
