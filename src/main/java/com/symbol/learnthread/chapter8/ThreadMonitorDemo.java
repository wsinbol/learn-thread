package com.symbol.learnthread.chapter8;

import com.symbol.learnthread.utils.Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author SymbolWong
 * @description 线程监控
 * 借助 UncaughtExceptionHandler
 * @date 2023/3/11 21:12
 */
public class ThreadMonitorDemo {
    volatile boolean init = false;
    static int threadIndex = 0;
    final BlockingQueue<String> channel = new ArrayBlockingQueue<String>(1000);

    public synchronized void init() {
        if (this.init) {
            return;
        }
        WorkThread workThread = new WorkThread();
        workThread.setName("Worker0-" + threadIndex++);
        workThread.setUncaughtExceptionHandler(new ThreadMonitor());
        workThread.start();
        this.init = true;
    }

    public void submit(String task) throws InterruptedException {
        this.channel.put(task);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadMonitorDemo demo = new ThreadMonitorDemo();
        demo.init();
        for (int i = 0; i < 100; i++) {
            demo.submit("task-" + i);
        }
        Thread.sleep(2000);
        System.exit(0);
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            System.out.println("开始执行任务！");
            String msg = null;
            for (; ; ) {
                try {
                    msg = channel.take();
                    process(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        private void process(String msg) {

            double v = Math.random() * 100;
            System.out.printf("Current msg is: %s. And current value is %s.%n", msg,v);

            if ((int) v < 50) {
                throw new RuntimeException("模拟随机异常");
            }
            Tools.randomPause(200);
        }
    }

    private class ThreadMonitor implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.printf("Current thread is: %s.It is still alive:%s", t.getName(), t.isAlive());
            init = false;
            init();
        }
    }
}
