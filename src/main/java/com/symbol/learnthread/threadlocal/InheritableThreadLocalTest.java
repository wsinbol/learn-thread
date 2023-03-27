package com.symbol.learnthread.threadlocal;

/**
 * @author SymbolWong
 * @description ThreadLocal继承示例
 * @date 2023/3/27 22:48
 */
public class InheritableThreadLocalTest {
    // public static ThreadLocal<String> localVariable = new ThreadLocal<>();
    public static ThreadLocal<String> localVariable = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        localVariable.set("parent variable");
        System.out.println("parent localVariable = " + localVariable.get());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child localVariable.get() = " + localVariable.get());
            }
        });

        thread.start();
    }
}
