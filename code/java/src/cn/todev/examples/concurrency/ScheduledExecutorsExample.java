package cn.todev.examples.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorsExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
          System.out.println("Executing Task At " + System.nanoTime());
        };

        System.out.println("Submitting task at " + System.nanoTime() + " to be executed after 5 seconds.");
        scheduledExecutorService.schedule(task, 5, TimeUnit.SECONDS);
        
        scheduledExecutorService.shutdown();
    }
}