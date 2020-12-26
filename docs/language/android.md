
# Android

## 系统架构图

![android_system_structure](/asset/img/android_system_structure.png)

## 基础篇

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
    - 作用：从 Android 系统和其他 Android 应用程序发送或接收广播消息
    - 注册方式：静态/动态
    - 类型
      - 普通广播 `Normal Broadcast`
      - 系统广播 `System Broadcast`
      - 有序广播 `Ordered Broadcast`
      - 粘性广播 `Sticky Broadcast`
      - 应用内广播 `Local Broadcast`
  - Content Provider

- 布局

  - LinearLayout
  - RelativeLayout
  - FrameLayout
  - TableLayout

- View
  - `SurfaceView`
    - 双缓冲机制
    - 子线程绘制
  - `TextureView`  
    - TextureView可用于显示内容流。
    - 例如，这样的内容流可以是视频或OpenGL场景。
    - 内容流可以来自应用程序的进程，也可以来自远程进程
  - `VideoView`
  - `WebView`

- 其他
  - AlertDialog,popupWindow,Activity 的区别
  - Application 和 Activity 的 Context 对象的区别
  - BroadcastReceiver，LocalBroadcastReceiver 的区别

## 原理篇

- View 绘制
  - measure
    - MeasureSpec
      - `UNSPECIFIED` - 未明确模式
      - `EXACTLY` - 精确模式
      - `AT_MOST` - 最多模式
  - layout
  - draw
    - `onDraw(Canvas canvas)`

- Touch 事件传递机制

  - ViewGroup : `dispatchTouchEvent()` -> `onInterceptTouchEvent() == true` -> `onTouchListener.onTouch()` -> `onTouchEvent()` -> `onClick`
  - View : `dispatchTouchEvent()` -> `onTouchListener.onTouch()` -> `onTouchEvent()` -> `onClick`
  
- View 滑动冲突
  - 外部拦截法

    ```java
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (满足父容器的拦截要求) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }
    ```

  - 内部拦截法
    - 示例
      - 子 View

        ```java
        public boolean dispatchTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    parent.requestDisallowInterceptTouchEvent(true);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    int deltaX = x - mLastX;
                    int deltaY = y - mLastY;
                    if (父容器需要此类点击事件) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    break;
                }
                default:
                    break;
            }

            mLastX = x;
            mLastY = y;
            return super.dispatchTouchEvent(event);
        }
        ```

      - 父 View

        ```java
        public boolean onInterceptTouchEvent(MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                return false;
            } else {
                return true;
            }
        }
        ```

    - 注意点：
      - 内部拦截法要求父View不能拦截ACTION_DOWN事件，由于ACTION_DOWN不受FLAG_DISALLOW_INTERCEPT标志位控制，一旦父容器拦截ACTION_DOWN那么所有的事件都不会传递给子View。
      - 滑动策略的逻辑放在子View的dispatchTouchEvent方法的ACTION_MOVE中，如果父容器需要获取点击事件则调用 parent.requestDisallowInterceptTouchEvent(false)方法，让父容器去拦截事件。

- `Handler`

  - `post` 流程
    - 等待消息：Looper.loop();
    - 消息入队：
      1. Handler.sendMessage(msg)
      2. Looper.MessageQueue.enqueueMessage(msg)
    - 处理消息：Looper.loop();
      1. 循环 MessageQueue.next()
      2. 调用 `msg.target.dispatchMessage(msg)`
    - 消息出队：
      1. Handler.dispatchMessage(msg)
      2. Handler.handleMessage(msg)
  - `postDelayed` 流程
    - 消息是通过 `MessageQueen` 中的 `enqueueMessage`()方法加入消息队列中的，并且它在放入中就进行好排序，链表头的延迟时间小，尾部延迟时间最大
    - `Looper.loop()` 通过 `MessageQueue` 中的 `next()` 去取消息
    - `next()` 中如果当前链表头部消息是延迟消息，则根据延迟时间进行消息队列会阻塞，不返回给 `Looper message`，知道时间到了，返回给 `message`
    - 如果在阻塞中有新的消息插入到链表头部则唤醒线程
    - `Looper` 将新消息交给回调给 `handler` 中的 `handleMessage` 后，继续调用 `MessageQueen` 的 `next()` 方法，如果刚刚的延迟消息还是时间未到，则计算时间继续阻塞

- `AsyncTask` [Doc](https://developer.android.com/reference/android/os/AsyncTask)

  > `AsyncTask` 可以正确，方便地使用 UI 线程。此类允许您执行后台操作并在 UI 线程上发布结果，而无需操作线程和/或处理程序

- `ActivityThread`

  它管理应用程序进程中主线程的执行，按照活动管理器的请求调度和执行活动、广播和其他操作

  1. LauncherActivity 通过 Binder 进程间通信的方式将应用的信息通过 Intent 的方式传递给 AMS ，由 AMS 进行调度。
  2. 如果系统中不存在该进程时，AMS 将会请求 Zygote 服务去 fork 一个子进程，成功后返回一个 pid 给 AMS，并由 AndroidRuntime 机制调起 ActivityThread 中的 main() 方法。
  3. 紧接着，应用程序的 Main Looper 被创建，ActivityThread 被实例化成为对象并将 Application 的信息以进程间通信的方式再次回馈给 AMS。
  4. AMS 接收到客户端发来的请求数据之后，首先将应用程序绑定，并启动应用程序的 Activity，开始执行 Activity 的生命周期

- `内存泄露`
  - 场景
    - 单例
    - 匿名内部类
    - Context
    - Handler
    - Cursor，Stream
    - WebView
  - 排查工具
    - dumpsys

      ```bash
      adb shell dumpsys meminfo <packageName>
      ```

    - LeakCanary

- `内存溢出 - OOM`
  - 原因
    - 内存泄露
    - 内存占用过多的对象

- `ThreadLocal`
- `LruCache` 缓存策略

  > 包含对有限数量值的强引用的缓存。每次访问一个值时，它都会移动到队列的头部。将值添加到完整缓存时，该队列末尾的值将被逐出，并且可能符合垃圾回收的条件

  - 原理：
    - `LruCache` 中维护了一个 `LinkedHashMap` 集合并将其设置顺序排序。
    - 当调用 `put()` 方法时，就会在集合中添加元素，并调用 `trimToSize()` 判断缓存是否已满，如果满了就用 `LinkedHashMap` 的迭代器删除队尾元素，即近期最少访问的元素。
    - 当调用 `get()` 方法访问缓存对象时，就会调用 `LinkedHashMap` 的 `get()` 方法获得对应集合元素，同时会更新该元素到队头

- 屏幕刷新机制

  - Android 应用程序调用 SurfaceFlinger 服务把经过测量、布局和绘制后的 Surface 渲染到显示屏幕上

  - `参考资料`
    - [Android 显示原理简介](http://djt.qq.com/article/view/987)

- ANR 产生的原因，以及如何定位和修正
- OOM 是什么？以及如何避免？
- 内存泄漏和内存溢出区别？
- 为什么不能在子线程更新 UI

## 优化篇

- 内存优化
- UI 优化
- 网络优化
- 启动优化
- 电量优化

> 参考资料

- [Android 性能优化全方面解析](https://juejin.im/post/5a0d30e151882546d71ee49e#heading-17)

## 视频篇

## 网络篇

## 开源篇

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

#### 关键术语和概念

- `视频` - 泛指将一系列的静态影像以电信号方式加以捕捉、纪录、处理、存储、发送与重现的各种技术
- `帧率` - 用于测量显示帧数的量度
  - 单位：
    - FPS（每秒显示帧数） - 一般来说 FPS 用于描述影片、电子绘图或游戏每秒播放多少帧
    - Hz（赫兹） - 每一秒周期性事件发生的次数
  - 视觉暂留 - 如果所看画面之帧率高于每秒约10至12帧的时候，就会认为是连贯的
- `长宽比` - 是用来描述影音画面与画面元素的比例
  - 常见的比例 - 4:3 / 16:9
- `封装格式` - 压缩过的视频数据和音频数据打包成一个文件的规范
  - AVI、RMVB、MKV、ASF、WMV、MP4、3GP、FLV
- `编解码`
  - 编码 - 对视频进行压缩
    - 视频编码格式 - `H264`、`Xvid`
    - 音频编码格式 - `MP3`、`AAC`
  - 解码 - 对视频进行解压缩
- `音画同步`
  - 将视频同步到音频上
  - 将音频同步到视频上
  - 将视频和音频同步外部的时钟上  
- `RTC` - 实时通信
  - 采集
  - 前处理
  - 编码
  - 传输
  - 解码
  - 后处理
  - 缓冲
  - 渲染

#### 解码

- 硬解 - 硬件解码是图形芯片厂商提出的用GPU资源解码视频流的方案
  - `MediaPlayer`
    - 代表播放器 - Android 自带的 [VideoView](https://developer.android.com/reference/android/widget/VideoView)
  - `MediaCodec`
    - 代表播放器 - Google 的 [ExoPlayer](https://github.com/google/ExoPlayer)
- 软解 - 相对于硬件解码，传统的软件解码是用CPU承担解码工作
  - `FFmpeg`
    - 代表播放器 - Bilibili 的 [ijkplayer](https://github.com/Bilibili/ijkplayer)

### 外设篇

#### [低功耗蓝牙(Bluetooth Low Energy)](https://developer.android.com/guide/topics/connectivity/bluetooth-le)

- 关键术语和概念
  - 通用属性配置文件 `Generic Attribute Profile` (GATT) - GATT 配置文件是一种通用规范，内容针对在 BLE 链路上发送和接收称为“属性”的简短数据片段
  - 属性协议 `Attribute Protocol` (ATT) — 属性协议 (ATT) 是 GATT 的构建基础，二者的关系也被称为 GATT/ATT
  - 特征 `Characteristic` — 特征包含一个值和 0 至 n 个描述特征值的描述符
  - 描述符 `Descriptor` — 描述符是描述特征值的已定义属性
  - 服务 `Service` - 服务是一系列特征
- 使用流程
  - 设置 `BLE`
    - 获取 `BluetoothAdapter`
    - 启用蓝牙

        ```kotlin
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        ```

  - 查找 `BLE` 设备
    - 方法
      - `startLeScan(BluetoothAdapter.LeScanCallback)` - 查找 BLE 设备
      - `startLeScan(UUID[], BluetoothAdapter.LeScanCallback)` - 扫描特定类型的外围设备
    - 注意事项(由于扫描非常耗电)
      - 找到所需设备后，立即停止扫描
      - 绝对不进行循环扫描，并设置扫描时间限制
    - Code

        ```kotlin
        private var mScanning: Boolean = false

        //返回扫描结果
        private val leScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
            runOnUiThread {
                //todo
            }
        }

        //在预定义的扫描时间段后停止扫描
        handler.postDelayed({
            mScanning = false
            bluetoothAdapter.stopLeScan(leScanCallback)
        }, 10000)
        mScanning = true
        bluetoothAdapter.startLeScan(leScanCallback)
        ```

  - 连接 GATT 服务器
    - 方法 `BluetoothGatt connectGatt (Context context, boolean autoConnect, BluetoothGattCallback callback)`
    - Code

        ```kotlin
        private val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(
                    gatt: BluetoothGatt,
                    status: Int,
                    newState: Int
            ) {

            }

            // New services discovered
            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED)
                    else -> Log.w(TAG, "onServicesDiscovered received: $status")
                }
            }

            // Result of a characteristic read operation
            override fun onCharacteristicRead(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    status: Int
            ) {
                when (status) {
                        BluetoothGatt.GATT_SUCCESS -> {
                            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)
                        }
                }
            }

            // Characteristic notification
            override fun onCharacteristicChanged(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic
            ) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)
            }
        }
        ```

  - 读取 BLE 属性
  - 接收 GATT 通知 - `setCharacteristicNotification()`
  - 关闭客户端应用 - `close()`

### 串口通信

#### [android-serialport-api](https://github.com/cepr/android-serialport-api)

- `SerialPort` - 获取串口的类(其实就是获取输入输出流)
  - `public SerialPort(File device, int baudrate, int flags)`
    - 参数
      - `device` - 要操作的文件对象
      - `baudrate` - 波特率
      - `flags` - 文件操作的标志
    - 流程  
      - JNI - `FileDescriptor open(String path, int baudrate, int flags)`
      - C - `int open(const char * pathname, int flags)`
        - `pathname` - 指向欲打开的文件路径字符串
        - `flags` - 文件的打开打开方式: O_RDONLY 以只读方式打开文件O_WRONLY 以只写方式打开文件O_RDWR 以可读写方式打开文件
        - `return` 若所有欲核查的权限都通过了检查则返回0 值, 表示成功, 只要有一个权限被禁止则返回-1
  - 读数据 - `getInputStream()`

      ```java
      class ReadThread extends Thread {
          @Override
          public void run() {
              super.run();
              while(!isInterrupted()) {
                  int size;
                  try {
                      byte[] buffer = new byte[64];
                      if (getInputStream() == null) return;
                      size = getInputStream().read(buffer);
                      if (size > 0) {
                          onDataReceived(buffer, size);
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                      return;
                  }
              }
          }
      }
      ```

  - 写数据 - `getOutputStream()`

      ```java
      String commandStr = "";
      FileOutputStream mOutputStream = getOutputStream();
      byte[] text = StringUtils.hexStringToBytes(commandStr);
      try {
          mOutputStream.write(text);
          mOutputStream.flush();
      } catch (IOException e) {
          e.printStackTrace();
      }
      ```

- `SerialPortFinder` - 获取硬件地址的类
- 常见问题
  - 包名 - `android_serialport_api`
  - 写入权限时，`/system/xbin/su` or `/system/bin/su`
  - 设备需要 Root 权限
