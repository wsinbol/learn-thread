package com.symbol.learnthread.chapter1;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/10 20:01
 */
public class WelcomeTaskRunnable implements Runnable{
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.printf("[3] Welcome! I'm %s.%n",Thread.currentThread().getName());
    }
}
