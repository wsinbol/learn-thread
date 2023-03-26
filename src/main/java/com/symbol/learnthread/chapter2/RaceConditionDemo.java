package com.symbol.learnthread.chapter2;

import lombok.SneakyThrows;

/**
 * @author SymbolWong
 * @description 竞态演示demo
 * @date 2023/3/11 16:33
 */
public class RaceConditionDemo {
    public static void main(String[] args) {
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new WorkThread(i, 10);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    static class WorkThread extends Thread{
        private final int requestCount;
        public WorkThread(int i, int requestCount){
            super("worker-"+i);
            this.requestCount = requestCount;
        }

        @SneakyThrows
        @Override
        public void run() {
            int i = this.requestCount;
            String requestId;

            UnsafeRequestIdGenerator instance = UnsafeRequestIdGenerator.getInstance();
            // SafeRequestIdGenerator instance = SafeRequestIdGenerator.getInstance();
            while (i-- > 0) {
                requestId = instance.nextId();
                sleep(500);
                System.out.printf("%s got requestId is %s.%n", Thread.currentThread().getName(),
                        requestId);
            }

        }
    }
}
