# Blog

## Catalog

- [Basic](#Basic) - [思维导图](mind/Basic.pdf)
  - [算法](#算法)
  - [设计模式](#设计模式)
- [Java](docs/language/java.md) - [思维导图](mind/Java.pdf)
  - [集合](docs/language/java.md#集合)
    - [非线程安全集合](docs/language/java.md#非线程安全集合)
    - [线程安全集合](docs/language/java.md#线程安全集合)
  - [并发](docs/language/java.md#并发)
    - [线程](docs/language/java.md#线程)
    - [`ExecutorService` 和 `Thread Pools`](#executorservice-和-thread-pools)
    - [死锁/活锁/饥饿锁 乐观锁/悲观锁](#死锁活锁饥饿锁-乐观锁悲观锁)
    - [CountDownLatch/Semaphore](#countdownlatchsemaphore)
- [Kotlin](docs/language/kotlin.md)
  - [基础](docs/language/kotlin.md#基础)
    - [基础语法](docs/language/kotlin.md#基础语法)
    - [惯用语法](docs/language/kotlin.md#惯用语法)
- [Android](docs/language/android.md) - [思维导图](mind/Android.pdf)
  - [基础篇](docs/language/android.md#基础篇)
  - [原理篇](docs/language/android.md#原理篇)
  - [核心篇](docs/language/android.md#核心篇)
  - [开源篇](docs/language/android.md#开源篇)
  - [外设篇](docs/language/android.md#外设篇)
    - [低功耗蓝牙](docs/language/android.md#低功耗蓝牙Bluetooth-Low-Energy)
    - [串口通信](docs/language/android.md#串口通信)
- [Flutter](#Flutter)
- [Go](#Go)
  - [交叉编译](#交叉编译)
- [VPS](#VPS)
  - [Mac](#Mac)
    - [用户环境变量](#用户环境变量)
  - [Linux](#Linux)
  - [Ubuntu](#ubuntu)
  - [Centos](#centos)
  - [Nginx](#nginx)
  - [Docker](#docker)
- [Tool](#Tool)
  - [Google 搜索技巧](#Google-搜索技巧)
  - [Mysql](#Mysql)
  - [Git](#git)
    - [设置 Git 代理](#设置-Git-代理)
    - [设置 Git SSH 代理](#设置-Git-SSH-代理)
    - [Commit Message 规范](#Commit-Message-规范)
  - [Vim](#vim)
  - [ADB](#adb)
  - [Shadowsocks](https://github.com/itwangxiang/docs/wiki/VPS.Shadowsocks)
- [Asset](#asset)
  - book
    - [设计模式_可复用面向对象软件的基础](asset/book/设计模式_可复用面向对象软件的基础.pdf)
    - [HeadFirst_设计模式](asset/book/HeadFirst_设计模式.pdf)

---

## Basic

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

### 设计模式

- 相关书籍
  - [HeadFirst_设计模式.pdf](asset/book/HeadFirst_设计模式.pdf)
  - [设计模式_可复用面向对象软件的基础.pdf](asset/book/设计模式_可复用面向对象软件的基础.pdf)

#### 创建型

- 工厂方法模式 `Factory Method Pattern`
  - UML 图
  ![factory](asset/img/factory_method.jpg)
  - Code
    - [FactoryMethodPattern.java](code/java/src/cn/todev/examples/pattern/FactoryMethodPattern.java)
  - Java 中用例
    - [java.util.Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)

- 建造者模式 `Builder Pattern`
- 单例模式 `Singleton Pattern`

#### 结构型

#### 行为型

## Flutter

### 基础

#### [安装](https://flutter.dev/docs/get-started/install)

- window
  - `PATH` environment

    ```bash
    # 临时设置环境
    $Env:path += ";D:\_SDK\flutter\bin"

    # 授权系统执行脚本权限
    $set-executionpolicy remotesigned

    # 为当前用户设置环境
    $Add-Content -Path $Profile.CurrentUserAllHosts -Value '$Env:Path += ";D:\_SDK\flutter\bin"'
    ```

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

### Mac

#### 用户环境变量

- bash

```bash
vim ~/.bash_profile

export ANDROID_HOME=/Users/xxx/Library/Android/sdk
export PATH=${PATH}:${ANDROID_HOME}/tools
export PATH=${PATH}:${ANDROID_HOME}/platform-tools

source .bash_profile
```

- zsh

```bash
vim ~/.zshrc

export ANDROID_HOME=/Users/xxx/Library/Android/sdk
export PATH=${PATH}:${ANDROID_HOME}/tools
export PATH=${PATH}:${ANDROID_HOME}/platform-tools

source .zshrc
```  

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

### Docker

[log](https://docs.docker.com/engine/reference/commandline/logs/)

> docker logs [OPTIONS] CONTAINER

- options
   - `--details` 
   - `--follow , -f` - 跟踪日志输出
   - `--tail` - 从日志末尾开始显示的行数
   - `--since` - 显示自时间戳记以来的日志（例如2013-01-02T13：23：37）或相对时间（例如42m，持续42分钟）

## Tool

### Google 搜索技巧

> [Google 搜索帮助文档](https://support.google.com/websearch/answer/2466433)

- 完全匹配 - `""`

  ```text
  "关键字"
  "最高的建筑"
  ```

- 排除关键字 - `-`

  ```text
  关键字 -排除关键字
  中国美食 -麻辣
  ```

- 组合搜索 - `OR`

  ```text
  关键字 OR 关键字
  马拉松 OR 比赛
  ```

- 搜索特定网站 - `site:`

  ```text
  关键字 site:网址
  美女 site:youtube.com
  ```

- 文件类型 - `filetype:`

  ```text
  关键字 filetype:文件类型
  ```

- 模糊匹配 - `*`

  ```text
  关键字*关键字
  ```  

- 搜索 # 标签  - `#`

  ```text
  #关键字
  ```

- 同义词 - `~`

  ```text
  关键字 ~同义关键字
  ```  

- 关键词 - `intitle`  

### MySql

- [概要](docs/tool/mysql.md)

### Git

#### [概要](docs/tool/git.md)

#### 设置 Git 代理

- 设置

  ```bash
  git config --global http.proxy http://127.0.0.1:1087
  git config --global https.proxy https://127.0.0.1:1087

  # 只对 github.com
  git config --global http.https://github.com.proxy http://127.0.0.1:1087
  git config --global https.https://github.com.proxy https://127.0.0.1:1087
  ```

- 取消

  ```bash
  git config --global --unset http.proxy
  git config --global --unset https.proxy

  # github.com
  git config --global --unset http.https://github.com.proxy
  git config --global --unset https.https://github.com.proxy
  ```

- 设置终端临时代理

  ```bash
  export http_proxy=http://127.0.0.1:1080
  export https_proxy=http://127.0.0.1:1080
  curl https://www.google.com # 测试
  ```

#### 设置 Git SSH 代理

```bash
# 这里的 -a none 是 NO-AUTH 模式，参见 https://bitbucket.org/gotoh/connect/wiki/Home 中的 More detail 一节
ProxyCommand connect -S 127.0.0.1:1080 -a none %h %p

Host github.com
  User git
  Port 22
  Hostname github.com
  # 注意修改路径为你的路径
  IdentityFile "C:\Users\bookey\.ssh\id_rsa"
  TCPKeepAlive yes

Host ssh.github.com
  User git
  Port 443
  Hostname ssh.github.com
  # 注意修改路径为你的路径
  IdentityFile "C:\Users\bookey\.ssh\id_rsa"
  TCPKeepAlive yes
```


#### [Commit Message 规范](docs/tool/git-commit-message-specification.md)

```bash
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

```bash
# head: <type>(<scope>): <subject>
# - type: feat, fix, docs, style, refactor, test, chore
# - scope: can be empty (eg. if the change is a global or difficult to assign to a single component)
# - subject: start with verb (such as 'change'), 50-character line
#
# body: 72-character wrapped. This should answer:
# * Why was this change necessary?
# * How does it address the problem?
# * Are there any side effects?
#
# footer:
# - Include a link to the ticket, if any.
# - BREAKING CHANGE
#
```

### Vim

- [概要](docs/tool/vim.md)

### ADB

- 常规
  
  ```bash
  adb tcpip 5555 //设置远程设备监听端口
  adb connect ip:port //连接远程设备
  adb -s 20b5c60c shell ifconfig wlan0 //查看 IP
  adb reconnect //重新连接设备
  adb shell am start -n ｛package｝/.{activity} //启动程序
  adb shell setprop persist.service.adb.tcp.port 5555 //设置系统重启后，远程设备监听端口
  adb shell wifitest -z "W 00:1f:2e:3d:4c:5b" //设置 WI-FI MAC
  adb shell wifitest -z "B 00:1f:2e:3d:4c:5b" //设置 蓝牙 MAC
  ```

## Asset

- [Window 下设置 JAVA 环境变量](asset/set-jdk-env-variables.ps1)
- [Pac.txt](asset/pac.txt)
