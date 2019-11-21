# Blog

## Catalog

- [基础](#基础)
  - [算法](#算法)
  - [设计模式](#设计模式)
- [Java](#Java)
  - [集合](#集合)
- [Android](#Android)
  - [基础篇](#基础篇)
  - [原理篇](#原理篇)
  - [核心篇](#核心篇)
  - [开源篇](#开源篇)
- [Go](#Go)
  - [交叉编译](#交叉编译)
- [VPS](#VPS)
  - [Linux](#Linux)
  - [Ubuntu](#ubuntu)
  - [Centos](#centos)
  - [Nginx](#nginx)
- [Tool](#Tool)
  - [Mysql](#Mysql)
  - [Git](#git)
  - [Vim](#vim)
  - [ADB](#adb)
  - [Shadowsocks](https://github.com/itwangxiang/docs/wiki/VPS.Shadowsocks)
- [Asset](#asset)

---

## 基础

### 算法

#### 排序算法

> 对一序列对象根据某个关键字进行排序

##### 冒泡排序

```java
public static void bubbleSort(int[] array) {
    for (int i = 0; i < array.length; i++)
        for (int j = 0; j < array.length - i - 1; j++)
            if (array[j + 1] < array[j]) {
                int temp = array[j + 1];
                array[j + 1] = array[j];
                array[j] = temp;
            }
}
```

##### 选择排序

```java
public static void selectionSort(int[] arr) {
    int min, temp;
    for (int i = 0; i < arr.length; i++) {
        // 初始化未排序序列中最小数据数组下标
        min = i;
        for (int j = i + 1; j < arr.length; j++) {
            // 在未排序元素中继续寻找最小元素，并保存其下标
            if (arr[j] < arr[min]) {
                min = j;
            }
        }
        // 将未排序列中最小元素放到已排序列末尾
        if (min != i) {
            temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }
}
```

##### 快速排序

```java
public static void quickSort(int[] arr, int head, int tail) {
    if (head >= tail || arr == null || arr.length <= 1) {
        return;
    }
    int i = head, j = tail, pivot = arr[(head + tail) / 2];
    while (i <= j) {
        while (arr[i] < pivot) {
            ++i;
        }
        while (arr[j] > pivot) {
            --j;
        }
        if (i < j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            ++i;
            --j;
        } else if (i == j) {
            ++i;
        }
    }
    quickSort(arr, head, j);
    quickSort(arr, i, tail);
}
```

### [设计模式](docs/basics/design-pattern.md)

[todo](https://github.com/itwangxiang/docs/issues/1)

#### [创建型](docs/basics/design-pattern.md#创建型)

#### [结构型](docs/basics/design-pattern.md#结构型)

#### [行为型](docs/basics/design-pattern.md#行为型)


## Java

### 集合

- Coliection
  - List: 有序集合
    - ArrayList
    - LinkedList
  - Set: 不能包含重复元素的集合
    - HashSet
    - TreeSet
    - LinkedHashSet
- Map: 将键映射到值的对象
  - HashMap
  - TreeMap
  - LinkedHashMap

### 线程

- 什么是线程？

  线程是操作系统能够进行运算调度的最小单位，它被包含在进程之中，是进程中的实际运作单位

- 线程和进程有什么区别？

  线程是进程的子集，一个进程可以有很多线程，每条线程并行执行不同的任务。不同的进程使用不同的内存空间，而所有的线程共享一片相同的内存空间。别把它和栈内存搞混，每个线程都拥有单独的栈内存用来存储本地数据。

- 如何在 Java 中实现线程？

  在语言层面有两种方式。java.lang.Thread 类的实例就是一个线程但是它需要调用 java.lang.Runnable 接口来执行，由于线程类本身就是调用的 Runnable 接口所以你可以继承 java.lang.Thread 类或者直接调用 Runnable 接口来重写 run()方法实现线程

- Thread 类中的 `start()` 和 `run()` 方法有什么区别？

  `start()` 方法被用来启动新创建的线程，而且 `start()` 内部调用了 `run()` 方法，这和直接调用 `run()` 方法的效果不一样。当你调用 `run()` 方法的时候，只会是在原来的线程中调用，没有新的线程启动，`start()` 方法才会启动新线程

- Java 中 `Runnable` 和 `Callable` 有什么不同？

  Runnable 和 Callable 都代表那些要在不同的线程中执行的任务。

  Runnable 从 JDK1.0 开始就有了，Callable 是在 JDK1.5 增加的。

  它们的主要区别是 Callable 的 `call()` 方法可以返回值和抛出异常，而 Runnable 的 `run()` 方法没有这些功能。Callable 可以返回装载有计算结果的 Future 对象

- 什么是 ThreadLocal 变量？

  ThreadLocal 是 Java 里一种特殊的变量。每个线程都有一个 ThreadLocal 就是每个线程都拥有了自己独立的一个变量，竞争条件被彻底消除了

- 什么是线程池？ 为什么要使用它？

  创建线程要花费昂贵的资源和时间，如果任务来了才创建线程那么响应时间会变长，而且一个进程能创建的线程数有限。

  为了避免这些问题，在程序启动的时候就创建若干线程来响应处理，它们被称为线程池，里面的线程叫工作线程。

  从 JDK1.5 开始，Java API 提供了 Executor 框架让你可以创建不同的线程池

- `参考资料`
  - [50 道 Java 线程面试题](http://www.importnew.com/12773.html)

## Android

### 系统架构图

<img src="https://github.com/itwangxiang/blog/blob/master/asset/img/android_system_structure.png" height="900" width="600">

### 基础篇

- 四大组件

  - Activity
    - [生命周期](https://developer.android.com/guide/components/activities/activity-lifecycle#java)
      > onCreate -> onStart -> onResume -> onPause -> onStop -> onDestory
    - [启动模式](https://developer.android.com/guide/components/activities/tasks-and-back-stack#TaskLaunchModes)
      - standard
      - singleTop
      - singleTask
      - singleInstance
  - Service
    - startService
      > `onCreate()` 、`onStartCommand()`、`onStart()`、`onDestroy()`
    - bindService
      > `bindService()`、`onCreate()` 、`IBinder onBind(Intent intent)`、`unBindService()`、`onDestroy()`
  - BroadcastReceiver
  - Content Provider

- 布局

  - LinearLayout
  - RelativeLayout
  - FrameLayout
  - TableLayout

- 其他
  - AlertDialog,popupWindow,Activity 的区别
  - Application 和 Activity 的 Context 对象的区别
  - BroadcastReceiver，LocalBroadcastReceiver 的区别

### 原理篇

- Touch 事件传递机制

  - ViewGroup : `dispatchTouchEvent()` -> `onInterceptTouchEvent() == true` -> `onTouchListener.onTouch()` -> `onTouchEvent()` -> `onClick`
  - View : `dispatchTouchEvent()` -> `onTouchListener.onTouch()` -> `onTouchEvent()` -> `onClick`

- 屏幕刷新机制

  - Android 应用程序调用 SurfaceFlinger 服务把经过测量、布局和绘制后的 Surface 渲染到显示屏幕上

  - `参考资料`
    - [Android 显示原理简介](http://djt.qq.com/article/view/987)

- `Handler`

  - 流程
    - 等待消息：Looper.loop();
    - 消息入队：
      1. Handler.sendMessage(msg)
      2. Looper.MessageQueue.enqueueMessage(msg)
    - 处理消息：Looper.loop();
    - 消息出队：
      1. Handler.dispatchMessage(msg)
      2. Handler.handleMessage(msg)

- `AsyncTask` [Doc](https://developer.android.com/reference/android/os/AsyncTask)

  > `AsyncTask` 可以正确，方便地使用 UI 线程。此类允许您执行后台操作并在 UI 线程上发布结果，而无需操作线程和/或处理程序

- `ActivityThread`

  它管理应用程序进程中主线程的执行，按照活动管理器的请求调度和执行活动、广播和其他操作

  1. LauncherActivity 通过 Binder 进程间通信的方式将应用的信息通过 Intent 的方式传递给 AMS ，由 AMS 进行调度。
  2. 如果系统中不存在该进程时，AMS 将会请求 Zygote 服务去 fork 一个子进程，成功后返回一个 pid 给 AMS，并由 AndroidRuntime 机制调起 ActivityThread 中的 main() 方法。
  3. 紧接着，应用程序的 Main Looper 被创建，ActivityThread 被实例化成为对象并将 Application 的信息以进程间通信的方式再次回馈给 AMS。
  4. AMS 接收到客户端发来的请求数据之后，首先将应用程序绑定，并启动应用程序的 Activity，开始执行 Activity 的生命周期

- `ThreadLocal`
- `LruCache` 缓存策略

  > 包含对有限数量值的强引用的缓存。每次访问一个值时，它都会移动到队列的头部。将值添加到完整缓存时，该队列末尾的值将被逐出，并且可能符合垃圾回收的条件

  - 原理：
    - `LruCache` 中维护了一个 `LinkedHashMap` 集合并将其设置顺序排序。
    - 当调用 `put()` 方法时，就会在集合中添加元素，并调用 `trimToSize()` 判断缓存是否已满，如果满了就用 `LinkedHashMap` 的迭代器删除队尾元素，即近期最少访问的元素。
    - 当调用 `get()` 方法访问缓存对象时，就会调用 `LinkedHashMap` 的 `get()` 方法获得对应集合元素，同时会更新该元素到队头

- ANR 产生的原因，以及如何定位和修正
- OOM 是什么？以及如何避免？
- 内存泄漏和内存溢出区别？
- 为什么不能在子线程更新 UI
- 线程池
  - `参考资料`
    - [Android 线程池原理及使用](https://www.jianshu.com/p/7b2da1d94b42)

### 优化篇

- 内存优化
- UI 优化
- 网络优化
- 启动优化
- 电量优化

> 参考资料

- [Android 性能优化全方面解析](https://juejin.im/post/5a0d30e151882546d71ee49e#heading-17)

### 视频篇

### 网络篇

### 开源篇

- [OkHttp](https://github.com/square/okhttp)

  > 适用于 Android 和 Java 应用程序的 HTTP + HTTP/2 客户端

  - OkHttp 支持同步调用和异步调用
  - OkHttp 提供了对最新的 HTTP 协议版本 HTTP/2 和 SPDY 的支持，这使得对同一个主机发出的所有请求都可以共享相同的套接字连接
  - 如果 HTTP/2 和 SPDY 不可用，OkHttp 会使用连接池来复用连接以提高效率
  - OkHttp 提供了对 GZIP 的默认支持来降低传输内容的大小
  - OkHttp 也提供了对 HTTP 响应的缓存机制，可以避免不必要的网络请求
  - 当网络出现问题时，OkHttp 会自动重试一个主机的多个 IP 地址

- Retrofit [官网](https://github.com/square/retrofit)

  > 适用于 Android 和 Java 的类型安全的 HTTP 客户端

  - 默认基于 OkHttp 封装的一套 RESTful 网络请求框架
  - 通过注解直接配置请求
  - 使用不同 Json Converter 来序列化数据
  - 提供对 RxJava 的支持

- Glide [官网](https://github.com/bumptech/glide)

  > Glide 是一个快速高效的 Android 图片加载库，注重于平滑的滚动

  - Glide 支持拉取，解码和展示视频快照，图片，和 GIF 动画
  - Glide 使用简明的流式语法 API

- ButterKnife [官网](https://github.com/JakeWharton/butterknife)

  > 将 Android 视图和回调绑定到字段和方法

  - 通过在字段上使用 `@BindView` 消除 `findViewById` 回调
  - 在列表或数组中组合多个视图。一次性使用操作，设置器或属性操作它们
  - 通过使用`@OnClick`和其他方法注释方法来消除侦听器的匿名内部类
  - 通过在字段上使用资源注释来消除资源查找

- Rxjava [官网](https://github.com/ReactiveX/RxJava)

  > RxJava - JVM 的 Reactive Extensions - 一个使用 Java VM 的可观察序列组成异步和基于事件的程序的库

  - 基于事件流的链式调用、逻辑简洁 & 使用简单
  - 扩展了观察者模式，以支持数据/事件序列，并增加了操作符，他可以将将序列清晰的组合在一起的

- Logger [官网](https://github.com/orhanobut/logger)

  > 简单，漂亮，功能强大的 android 记录器

- EventBus [官网](https://github.com/greenrobot/EventBus) - [源码](docs/source/EventBus.md)

  > 适用于 Android 和 Java 的事件总线，可简化 Activities, Fragments, Threads, Services 等之间的通信。减少代码，提高质量

- 批量渠道打包 
    
    - [AndroidMultiChannelBuildTool](https://github.com/GavinCT/AndroidMultiChannelBuildTool)


## Go

### 交叉编译

Mac 下编译

```bash
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build main.go
CGO_ENABLED=0 GOOS=windows GOARCH=amd64 go build main.go
```

Linux 下编译

```bash
# mac
CGO_ENABLED=0 GOOS=darwin GOARCH=amd64 go build main.go
# windows
CGO_ENABLED=0 GOOS=windows GOARCH=amd64 go build main.go
```

Windows 下编译

```bash
# mac
SET CGO_ENABLED=0
SET GOOS=darwin
SET GOARCH=amd64
go build main.go

#linux
SET CGO_ENABLED=0
SET GOOS=linux
SET GOARCH=amd64
go build main.go
```



## VPS

### Linux

- [概要](docs/vps/linux.md)
  
  后台任务，杀掉

### Ubuntu

- [概要](docs/vps/ubuntu.md)

### Centos

- [概要](docs/vps/centos.md)
  
  修改密码、搭建 Shadowsocks、设置防火墙

### Nginx

- [概要](docs/vps/nginx.md)

## Tool

### MySql

- [概要](docs/tool/mysql.md)

### Git

- [概要](docs/tool/git.md)
- [设置代理](docs/tool/git-set-proxy.md)
- [Commit Message 规范](docs/tool/git-commit-message-specification.md)

### Vim

- [概要](docs/tool/vim.md)

### ADB

- 常规
  
  ```bash
  adb tcpip 5555 //设置远程设备监听端口
  adb connect ip:port //连接远程设备
  adb reconnect //重新连接设备
  adb shell am start -n ｛package｝/.{activity} //启动程序
  adb shell setprop persist.service.adb.tcp.port 5555 //设置系统重启后，远程设备监听端口
  adb shell wifitest -z "W 00:1f:2e:3d:4c:5b" //设置 WI-FI MAC
  adb shell wifitest -z "B 00:1f:2e:3d:4c:5b" //设置 蓝牙 MAC
  ```

## Asset

- [Window 下设置 JAVA 环境变量](asset/set-jdk-env-variables.ps1)
- [Pac.txt](asset/pac.txt)
