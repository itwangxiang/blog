# EventBus 源码分析

> 3.1.1

## 使用

1. 定义事件

   ```java
   public static class MessageEvent { /* Additional fields if needed */ }
   ```

2. 准备订阅者：声明并注释您的订阅方法，可选择指定一个线程模式

   ```java
   @Subscribe(threadMode = ThreadMode.MAIN)  
   public void onMessageEvent(MessageEvent event) {/* Do   something */};
   ```

3. 注册和注销您的订阅者

   ```java
   @Override
   public void onStart() {
       super.onStart();
       EventBus.getDefault().register(this);
   }

   @Override
   public void onStop() {
       super.onStop();
       EventBus.getDefault().unregister(this);
   }
   ```

4. 发布事件

   ```java
   EventBus.getDefault().post(new MessageEvent());
   ```

## 简单流程

1. Register subscriber (注册订阅者)

2. post (发送事件) -> invoke @Subscribe (执行订阅方法)

3. Unregister subscriber(注销订阅者)
