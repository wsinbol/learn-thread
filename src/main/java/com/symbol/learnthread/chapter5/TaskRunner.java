package com.symbol.learnthread.chapter5;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/11 20:34
 */
public class TaskRunner {
    protected final BlockingQueue<Runnable> channel;
    protected volatile Thread workThread;

    public TaskRunner(BlockingQueue<Runnable> channel) {
        this.channel = channel;
        this.workThread = new WorkThread();
    }

    public TaskRunner(){
        this(new LinkedBlockingQueue<Runnable>());
    }

    public void submit(Runnable task) throws InterruptedException {
        this.channel.put(task);
    }

    public void init(){
        final Thread t = this.workThread;
        if (t!=null) {
            t.start();
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable task = null;
            // 注意：下面这种代码写法实际上可能导致工作者线程永远无法终止！
            // “5.6 对不起，打扰一下：线程中断机制”中我们将会解决这个问题。
            try{
                for (;;){
                    task = channel.take();
                    try{
                        task.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
