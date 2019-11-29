# Java


## JVM

### 栈内存

### 堆内存

### GC

### volatile 关键字

## 基础篇

### 概念

- 类

	- 创建对象的蓝图，描述了所创建的对象共同的属性和方法

	- 声明

		- 构造函数

		- 变量

			- 声明

				- 访问修饰符 类型 变量名称

		- 方法

			- 声明

				- 访问修饰符 返回类型 方法名称 括号中的参数列表 {}

			- 重载

			- 参数值传递

				- 基本类型传递的是值的副本

				- 引用类型传递的是引用的副本

					- 都是指向同一个地址

	- 内部类

		- 成员内部类

		- 匿名内部类

		- 静态内部类

- 对象

- 接口

	- 接口是一组具有空体的相关方法

- 包

### 关键字

- static

- final

- 访问权限

	- public

	- protected

	- no modifier

	- private

- synchronized , volatile

### OOP

- 继承(实现)

	- 抽象类（单继承），接口（多继承）

- 封装

	- 开闭原则

- 多态(override)

### 数据类型

- 原始数据类型(8种)

	- 整数

		- byte

		- short

		- int

		- long

	- 小数

		- float

		- double

	- char

	- boolean

- 枚举类型

	- enum

- 引用类型

	- String

	- 数组

	- 自定义的类和接口

### 基础语法

- 变量

	- 实例变量（非静态变量）

	- 类变量（静态变量）

	- 局部变量

	- 参数

- 运算符

	- 赋值运算符

		- =

	- 算数运算符

		- +

		- -

		- *

		- /

		- %

	- 一元操作符

		- -

		- +

		- --

		- ++

		- !

	- 平等与关系运算符

	- 条件运算符

		- &&

		- ||

		- ?:

	- 类型比较运算符

		- instanceof

	- 按位和位移运算符

- 流程控制

	- 决策声明

		- if-then

		- if-then-else

		- switch

	- 循环语句

		- while

		- do-while

		- for

			- foreach

	- 分支语句

		- break

		- continue

			- 跳过for，while或do-while循环的当前迭代

		- return

			- 从当前方法退出，控制流返回到调用方法的位置

### 注解

- 运行时注解

- 编译时注解(apt)

### 装箱

### 发射

### 异常

## 容器

### Collection

- List

	- Vector

	- ArrayList

		- CopyOnWriteArrayList

			- 线程安全

	- LinkedList

- Set

	- HashSet

	- TreeSet

- Queue

### Map

- HashMap

- TreeMap

	- 根据 key 排序 - 可自定义排序

- HashTable

	- key - value 都不能为空 ，线程同步

- LinkedHashMap

	- 按插入排序 - 或访问排序

### Iterator

### hashCode(),equals(),compareTo()

### Collections / Arrays

## IO/NIO

## 并发

### 线程

- 概念

- 生命周期

- 线程安全

	- 不可变

		- final

	- 线程封闭

		- ThreadLocal

	- 同步

		- 悲观锁

			- synchronized

			- Lock.lock() +  Lock.unlock()

			- 锁优化

		- 非阻塞同步（乐观锁）

			- CAS + volatile

	- 工具类

		- 并发容器(JUC)

			- ConcurrentHashMap

			- CopyOnWriteArrayList

- 线程协作

	- 阻塞与唤醒

	- 让步

		- Thread.join() Thread.yield()

	- 取消(中断)

		- Thread.interrupted()

	- 工具类

- 活跃度

	- 死锁

		- 必要因素

			- 互斥

				- 一个资源每次只能被一个线程使用

			- 请求与保持

				- 一个线程因请求资源而阻塞时，对已获得的资源保持不放

			- 不剥夺资源

				- 线程已获得的资源，在未使用完之前，不能强行剥夺

			- 循环等待

				- 若干线程之间形成一种头尾相接的循环等待资源关系

		- 预防

			- 以确定的循序获得锁

			- 超时放弃

	- 饥饿，活锁

- 工具类

	- 

- 通信方式

	- 全局变量

	- 消息

	- 事件

- 线程复用

	- 线程池

		- 执行器 ExecutorService

			- newFixedThreadPool(n)

			- newSingleThreadPool()

			- newCachedThreadPool()

			- newScheduleThreadPool()

			- execute / submit

		- 任务 - Runable/Callable/Future

		- 线程数量

			- 高频短时

				- CPU 核心数 + 1

			- 高频长时

				- CPU 密集型

					- CPU 核心数 + 1

				- IO 密集型

					- 2 * CPU核心数 + 1

			- 最佳线程数目 = （线程等待时间与线程CPU时间之比 + 1）* CPU数目

## IPC

### 管道

### 信号量

### 内存共享

### Socket

### 消息队列

### AIDL（Android）

## JVM

### Java 内存模型 -JMM

- 内存

	- 主内存

	- 工作内存

		- 线程使用的变量

		- 主内存副本拷贝

- volatile

### 运行时数据区

- 线程私有

	- 程序计数器

	- 虚拟机栈

	- 本地方法栈

		- Native 方法

- 共有

	- Java 堆

	- 方法区

### GC

- 引用分类

	- 强引用

		- FinalReference

	- 软引用

		- SoftReference

	- 弱引用

		- WeakReference

	- 虚引用

- [算法](https://juejin.im/entry/5abc9f616fb9a028c42e4722)

	- 垃圾判定

		- 引用计数算法 - Reference Counting

		- 可达性分析算法 - 根搜索算法

	- 垃圾回收

		- 标记清除

			- 碎片

		- 复制

			- 空间占用

		- 标记整理

			- 效率低

		- 分代

			- 新生代

			- 老年代

- 垃圾收集器

### 类加载

### class 文件结构

### Dalvik / ART

