package com.symbol.learnthread.threadlocal;

/**
 * @author SymbolWong
 * @description ThreadLocal 实例
 * @date 2023/3/27 22:35
 */
public class ThreadLocalTest {

    static ThreadLocal<String> localVariable = new ThreadLocal<String>();

    public static void main(String[] args) {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadOne local variable");
                print("threadOne");
                System.out.println("after remove localVariable.get() = " + localVariable.get());
            }
        });


        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadTwo local variable");
                print("threadTwo");
                System.out.println("after remove localVariable.get() = " + localVariable.get());
            }
        });

        threadOne.start();
        threadTwo.start();
    }

    private static void print(String str) {
        System.out.println("localVariable.get() = " + localVariable.get());
        localVariable.remove();
    }
}
