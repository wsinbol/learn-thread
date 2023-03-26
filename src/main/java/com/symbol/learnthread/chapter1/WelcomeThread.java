package com.symbol.learnthread.chapter1;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/10 19:51
 */
public class WelcomeThread extends Thread{
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        System.out.printf("[2] Welcome!I'm %s.%n", Thread.currentThread().getName());
    }
}
