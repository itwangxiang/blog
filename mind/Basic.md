# Basic


## 操作系统

## 数据结构

### 复杂度

- 时间复杂度

- 空间复杂度

## 算法

## 面向对象设计 - SOLID

### S - 单一功能原则

### O - 开闭原则

### L - 里氏替换原则

### I - 接口隔离原则

### D - 依赖反转原则

## 设计模式

### 创建型

- 单例模式

- 工厂模式

- 建造者模式

	- 封装一个复杂对象的构建过程，并可以按步骤构造

### 结构型

- 适配器模式

	- 将一个类的方法接口转换成客户希望的另外一个接口

- 代理模式

	- 为其他对象提供一个代理以便控制这个对象的访问

- 桥接模式

	- 将抽象部分和它的实现部分分离，使它们都可以独立的变化

- 装饰模式

	- 动态的给对象添加新的功能

### 行为型

- 观察者模式

	- 对象间的一对多的依赖关系

- 责任链模式

	- 使的多个对象都有处理这个请求的机会

## 网络

### TCP/IP四层模型

- 应用层

	- HTTP

	- FTP

- 传输层

	- TCP

	- UDP

	- RTP

	- SCTP

- 网络层

	- IP

- 网络访问层

	- 以太网

	- Wi-Fi

	- MPLS

### 协议

- TCP - Transmission Control Protocol - 传输控制协议

	- 三次握手

		- Client: SYN (seq = x)

		- Service: SYN( seq = y) , ACK = x+1

		- Client: ACK = y +1

	- 传输

		- Client: seq = x +1 ACK = y + 1

		- Service: ACK = x + 2

	- 四次挥手

		- Client: FIN seq= x+2 ACK = y+1

		- Service: ACK = x +3

		- Service: FIN seq = y +1 

		- Client: ACK = y + 2

- UDP - User Datagram Protocol - 用户数据包协议

- HTTPS - HyperText Transfer Protocol Secure - 超文本传输安全协议

	- 加密方式

		- TLS - Transport Layer Security - 传输层安全性协议

		- SSL - Secure Sockets Layer - 安全套接层

	- 流程

		- 由 HTTP 进行通信，但利用 SSL/TLS 进行加密数据包

### Socket

