package com.symbol.learnthread.waitNotify;

import lombok.SneakyThrows;

import java.util.Vector;

/**
 * @author SymbolWong
 * @description
 * @date 2023/5/3 16:48
 */
public class Producer implements Runnable {

    private Vector<Integer> pool;
    private Integer size;

    public Producer(Vector<Integer> pool, Integer size){
        this.pool = pool;
        this.size = size;
    }

    private void producer(Integer i) throws InterruptedException {
        while (pool.size() == size) {
            synchronized (pool){
                System.out.println("生产者等待消费者消费商品，当前商品数量为：" + pool.size());
                pool.wait(); // 等待消费者消费
            }
        }

        synchronized (pool){
            pool.add(i);
            pool.notifyAll(); // 通知消费者消费
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        for (;;){
            System.out.println("生产一个商品");
            producer(1);
        }

    }
}
