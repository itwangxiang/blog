# Android

## 工具

### Android Studio

- Build

	- 添加依赖

		- implementation
		- api

	- 优化速度

### Gradle

## 资料

### 面试

- android-interview-guide

## 系统架构

### 应用框架

### Java 系统框架

### C++ 系统框架

### Linux 内核层

## 基础篇

### 四大组件

- Activity

	- 生命周期
	- 启动模式
	- 横竖屏切换

		- onNewIntent()

- Service

	- 生命周期
	- 启动模式

		- startService
		- bindService

	- IPC(AIDL)
	- Service/IntentService

- BroadcastReceiver

	- 注册方式

		- 静态注册
		- 动态注册

	- 生命周期
	- 有序

		- sendOrderedBroadcast

- ContentProvider

	- 数据共享

- 其他相关

	- Application
	- context
	- Intent

### 常用组件

- Fragment

	- 生命周期
	- 懒加载
	- 与Activity 通信

- RecyclerView

	- 四级缓存 + 局部刷新
	- 与 ListView 比较

- WebView

	- JsBridge
	- deeplink
	- 首屏加速
	- 离线包

- Window

	- Toast
	- Dialog
	- PopupWindow

### 布局

- LinearLayout
- RelativeLayout

### AndroidManifest.xml

## View 篇

### 位置参数

- top/left/bottom/right
- x / y 

	- 左上角坐标

- ttranslationX / ttranslationY

	- 相对于父控件的偏移量

- width/height

	- width

		-  = right - left

	- height

		- = bottom - top

### MotionEvent / TouchSlop

- MotionEvent

	- ACTION_DOWN
	- ACTION_MOVE
	- ACTION_UP

- TouchSlop

	- 滑动最小距离

### VelocityTracker/GestureDetector/Scroller

- VelocityTracker

	- 速度追踪

- GestureDetector

	- 手势检查

- Scroller

	- 弹性滑动

### 滑动

- 通过 View 提供的 scrollTo/scrollBy

	- 移动的是 View 内容

- 通过动画给 View 施加平移

	- View 动画 - 操作 translationX 和 translationY (xml  - set)
	- 属性动画

		- ObjectAnimator

			- ofFloat

- 通过改变 View 的 LayoutParams
- 嵌套滑动

### 事件分发

- ViewGroup

	- dispatchTouchEvent

		- onInterceptTouchEvent

			- onTouchListener.onTouch()

				- onTouchEvent()

- View

	- dispatchTouchEvent

		- onTouchListener.onTouch()

			- onTouchEvent()

### 自定义

- 类型

	- 直接继承 View 的控件

		- wrap_content 做特殊处理
		- padding 做特殊处理

	- 继承 ViewGroup 派生特殊的 Layout

		- 处理 ViewGroup 的测量、布局
		- 处理子元素的测量和布局

	- 继承特定的View - 比如TextView

		- 不需要自己处理 wrap_content 和 padding

	- 继承特定的ViewGroup (比如 LinearLayout)

		- 不需要自己处理 ViewGroup 的测量和布局

- 定义自定义属性

	- declare-styleable

- 测量 measure

	- MeasureSpec

		- UNSPECIFIED

			- 父节点没有对子节点施加任何约束

		- EXACTLY

			- 父节点已经为子节点确定了确切的大小

		- AT_MOST

			- 子元素可以任意大，直到指定的大小

- 布局 layout
- 绘制 draw

	- requestLayout() 与 invalidate() 区别

### 绘制 Drawable  资源

- 位图文件 BitmapDrawable
- 九宫格文件 NinePatchDrawable
- 图层列表 LayerDrawable
- 状态列表 StateListDrawable
- 级别列表 LevelListDrawable
- 转换可绘制对象 TransitionDrawable
- 插入可绘制对象 InserDrawable
- 裁剪可绘制对象 ClipDrawable
- 缩放可绘制对象 ScaleDrawable
- 形状可绘制对象 ShapeDrawable

### 动画 Animation 资源

- 分类

	- View 动画

		- 补间动画 Tween

			- 平移动画 TranslateAnimation
			- 缩放动画 ScaleAnimation
			- 旋转动画 RotateAnimation
			- 透明度动画 AlphaAnimation

		- 帧动画 Frame

	- 属性动画

		- 类型

			- ValueAnimator

				- ObjectAnimator

		- 概念

			- 插值器 Interpolator
			- 估值器 Evaluator
			- 监听器 

				- AnimatorUpdateListener
				- s

- 用途

	- 作用于 View
	- 作用于 ViewGroup

		- LayoutAnimation

	- 作用于 Activity

		- overridePendingTransition

## Window

### WindowManager

## 进阶篇

### 动画

- Animation
- OpenGL

### 优化

- 布局优化

	- 过度绘制

- 内存优化

	- OOM
	- ANR
	- 分析

		- Heap
		- adb shell
		- TraceView
		- Dalvk 日志
		- Logcat
		- MAT

- 冷启动

	- 避免启动白屏

- APK 瘦身
- Webview 首屏优化
- 流量/电池

### 适配

### 动态化

- 插件化
- 热修复
- RN/weex

### 组件化

- 页面路由

### 消息推送/保活

- 进程保活
- 长连接保活

	- 心跳包

### 安全

- 服务器安全
- 通讯安全
- APK 加固
- 打包流程
- 数据加密

### 通讯

- HttpUrlConnection
- Socket
- Bluetooth
- NFC
- Headset
- USB
- WIFI

## 机制篇

### 线程通信

- Handler

### 进程通信(IPC)

- 数据序列化

	- Serializable
	- Parcelable

- 方式

	- Bundle
	- 文件共享
	- AIDL
	- Messenger
	- ContentProvider
	- Socket

### Bitmap

- 计算大小
- 裁剪，压缩等优化
- 复用

### 存储

- SharedPreferences
- File
- SQLite

### 流程

- 系统启动

	- Loader 层

		- Boot Rom

	- Kernel 层

		- swapper 进程

			- 初始化进程管理、内存管理、加载驱动

		- kthreadd 内核进程

	- Native 层

		- Init 进程

			- fork 用户守护进程
			- 启动 Binder 的 ServiceManager
			- fork Zygote 进程

				- Java进程（虚拟机进程）

	- Framework 层

		- Media Server 进程
		- Zygote 进程
		- System Server 进程

			- 负责启动和管理整个Java Framework

	- App 层

		- Launcher 进程

- App 启动流程

	- Launcher 进程

		- 通过 Binder  发消息 给 AMS

	- System Server 进程 - AMS

		- 通过 Socket 让 Zygote 进程 fork

	- Zygote 进程 -  fork 新进程
	- App 进程

		- ActivityThread - Looper

## 多媒体篇

### 音频

### 视频

- 视频播放

	- MediaPlayer
	- VideoView

- 视频编解码

## NDK篇

### JNI

### C

### C++

## 开源框架

### 网络

- OkHttp
- Retrofit

### 图片加载

- Fressco
- Glide
- Picasso

### 响应式

- RxJava
- RxAndroid

### 事件

- EventBus

### 依赖注入

- ButterKnife
- Dagger2

### 数据库

- GreenDao
- OrmLite
- Realm

### 内存泄漏排查

- LeakCanary

## 第三方扩展

### 支付

### 分享

### 推送

### 统计

### 地图

### 语言识别

### 图片识别 OCR

### 广告

*XMind: ZEN - Trial Version*