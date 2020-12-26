
# Java

- [Java](#java)
  - [集合](#集合)
    - [非线程安全集合](#非线程安全集合)
    - [线程安全集合](#线程安全集合)
  - [并发](#并发)
    - [线程](#线程)
    - [`ExecutorService` 和 `Thread Pools`](#executorservice-和-thread-pools)
    - [死锁/活锁/饥饿锁 乐观锁/悲观锁](#死锁活锁饥饿锁-乐观锁悲观锁)
    - [CountDownLatch/Semaphore](#countdownlatchsemaphore)

## 集合

### 非线程安全集合

- `List`: 有序集合
  - ArrayList
    - 数据结构：`基于泛型数组`
    - 特点：查询速度快，增删速度慢
  - LinkedList
    - 数据结构：`基于链表结构`
    - 特点：查询速度慢，增删速度快
- `Map`: 将键映射到值的双列集合
  - HashMap
    - 数据结构： `基于哈希表`
    - 特点：存取无序
    - 源码
      - `DEFAULT_INITIAL_CAPACITY` 默认为 16
      - `MAXIMUM_CAPACITY` 默认为 2 的 30 次幂
      - `DEFAULT_LOAD_FACTOR` 默认负载因子 0.75
      - 每次会扩容长度为以前的2倍
  - TreeMap
    - 有序
    - 数据结构 -> `基于红黑树`
  - LinkedHashMap
    - 数据结构： `基于链表和哈希表`
    - 特点：存取有序
  - EnumMap:
    - 特点：枚举类型作为键值的Map
  - IdentityHashMap:
    - 特点：使用 “==” 来比较引用
  - WeakHashMap:
    - 特点：将键存储在 WeakReference 中
    - 场景：用于数据缓存中
- `Set`: 不能包含重复元素的集合
  - HashSet:
    - 数据结构： `基于哈希表`
    - 特点：存取无序
  - TreeSet
    - 数据结构： `基于二叉树`
    - 特点：排序
  - LinkedHashSet
    - 数据结构： `基于链表和哈希表`
    - 特点：存取有序
  - EnumSet:
    - 特点：值为枚举类型的Set
  - BitSet
- `Queue/Deque`: 队列
  - ArrayDeque
    - 特点：基于有首尾指针的数组（环形缓冲区）
  - PriorityQueue
    - 特点：基于优先级的队列

### 线程安全集合

- `List`
  - CopyOnWriteArrayList
    - 特点：避免了多线程操作的线程安全问题
    - 原理：先复制，再操作，最后替换
    - 场景：用在遍历操作比更新操作多的集合，比如 listeners / observers 集合
- `Queue/Deque`
  - ArrayBlockingQueue
    - 特点：基于数组实现的一个有界阻塞队，大小不能重新定义
  - ConcurrentLinkedQueue
    - 特点：基于链表实现的无界队列
  - DelayQueue
  - LinkedBlockingQueue / LinkedBlockingDeque
    - 特点：可选择有界或者无界基于链表的实现
  - LinkedTransferQueue
    - 特点：基于链表的无界队列
  - SynchronousQueue
    - 特点：有界队列
- `Map`
  - ConcurrentHashMap
  - ConcurrentSkipListMap
    - `并发有序`  
- `Set`  
  - ConcurrentSkipListSet
  - CopyOnWriteArraySet

## 并发

### 线程

- 创建
  - 继承 `Thread` 并重写 `run()` 方法 - [ThreadExample.java](code/java/src/cn/todev/examples/concurrency/ThreadExample.java)

  ```java
  public class ThreadExample extends Thread {

    // run() method contains the code that is executed by the thread.
    @Override
    public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating thread...");
        Thread thread = new ThreadExample();

        System.out.println("Starting thread...");
        thread.start();
    }
  }
  ```

  - 实现 `Runnable` 接口 - [RunnableExample.java](code/java/src/cn/todev/examples/concurrency/RunnableExample.java)  

  ```java
  public class RunnableExample implements Runnable {

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating Runnable...");
        Runnable runnable = new RunnableExample();

        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);

        System.out.println("Starting Thread...");
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());
    }
  }
  ```  

  - 内部类实现 - [RunnableExampleAnonymousClass.java](code/java/src/cn/todev/examples/concurrency/RunnableExampleAnonymousClass.java)  

  ```java
  public class RunnableExampleAnonymousClass {

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating Runnable...");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside : " + Thread.currentThread().getName());
            }
        };

        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);

        System.out.println("Starting Thread...");
        thread.start();
    }
  }
  ```

- 休眠
  - 使用 `sleep()`
- 等待
  - 使用 `join()`

### `ExecutorService` 和 `Thread Pools`

- Executors 框架
  - 优点
    - 线程创建
    - 线程管理
    - 任务提交和执行
  - 方法
    - `execute` - 执行任务
    - `submit` - 提交任务
    - `shutdown()` - 停止接受新任务，等待先前提交的任务执行，然后终止执行程序
    - `shutdownNow()` - 中断正在运行的任务并立即关闭执行程序
  - Code - - [ExecutorsExample.java](code/java/src/cn/todev/examples/concurrency/ExecutorsExample.java)

  ```java
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  import java.util.concurrent.TimeUnit;

  public class ExecutorsExample {
      public static void main(String[] args) {
          System.out.println("Inside : " + Thread.currentThread().getName());

          System.out.println("Creating Executor Service with a thread pool of Size 2");
          ExecutorService executorService = Executors.newFixedThreadPool(2);

          Runnable task1 = () -> {
              System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
              try {
                  TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException ex) {
                  throw new IllegalStateException(ex);
              }
          };

          Runnable task2 = () -> {
              System.out.println("Executing Task2 inside : " + Thread.currentThread().getName());
              try {
                  TimeUnit.SECONDS.sleep(4);
              } catch (InterruptedException ex) {
                  throw new IllegalStateException(ex);
              }
          };

          Runnable task3 = () -> {
              System.out.println("Executing Task3 inside : " + Thread.currentThread().getName());
              try {
                  TimeUnit.SECONDS.sleep(3);
              } catch (InterruptedException ex) {
                  throw new IllegalStateException(ex);
              }
          };


          System.out.println("Submitting the tasks for execution...");
          executorService.submit(task1);
          executorService.submit(task2);
          executorService.submit(task3);

          //executorService 会一直监听新的任务（即阻塞线程），直到关闭他为止
          executorService.shutdown();
      }
  }
  ```

- 线程池
  - 简介：与 `Runnable` 或 `Callable` 任务分开存在的一堆工作线程，由 `executorService` 管理
  - 机制：任务通过 `Blocking Queue` 提交到线程池
    - 如果任务的数量大过活动线程数量，则会将他插入 `Blocking Queue` 中，一直等到有可用线程为止
    - 如果 `Blocking Queue` 已满，则拒绝新任务
  - Code
    - [ScheduledExecutorsExample.java](code/java/src/cn/todev/examples/concurrency/ScheduledExecutorsExample.java)
    - [ScheduledExecutorsPeriodicExample.java](code/java/src/cn/todev/examples/concurrency/ScheduledExecutorsPeriodicExample.java)

- `Callable` 与 `Future`
  - 方法
    - `cancel()`: 尝试取消执行任务，如果成功取消则返回true，否则返回false
    - `cancel(boolean mayInterruptIfRunning)`: 当 mayInterruptIfRunning 为 true 时，则当前正在执行任务的线程将被中断，否则将允许正在进行的任务完成
    - `isCancelled`: 任务是否被取消
    - `isDone`: 任务是否已完成
  - Code - [FutureAndCallableExample.java](code/java/src/cn/todev/examples/concurrency/FutureAndCallableExample.java)

### 死锁/活锁/饥饿锁 乐观锁/悲观锁

- 死锁 - 其中两个或多个线程永远被阻塞，互相等待
- 活锁 - 其中两个线程一直在让资源，都无法使用资源
- 饥饿锁 - 其他线程一直占用资源，导致永远获取不到资源
- 乐观锁 - 假设不会发生并发冲突，只在提交操作时检查是否违反数据完整性
- 悲观锁 - 假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作

### CountDownLatch/Semaphore

- CountDownLatch
  - 作用 - 允许一个或多个线程等待，直到在其他线程中执行的一组操作完成
  - 方法
    - `await()`
    - `await(long timeout, TimeUnit unit)`
    - `countDown()`
  - Code

    ```java
    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    ```

- Semaphore - 信号量
  - 方法
    - `acquire()`
    - `acquire(int permits)`
    - `release()`  
    - `release(int permits)`
