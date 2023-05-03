package com.symbol.learnthread.waitNotify;

import lombok.SneakyThrows;

import java.util.Vector;

/**
 * @author SymbolWong
 * @description
 * @date 2023/5/3 17:02
 */
public class Cosumer implements Runnable{

    private Vector<Integer> pool;
    private Integer size;

    public Cosumer(Vector<Integer> pool){
        this.pool = pool;
    }

    @SneakyThrows
    @Override
    public void run() {
        for(;;){
            System.out.println("消费一个商品");
            consume();
        }

    }

    private void consume() throws InterruptedException {
        while (pool.isEmpty()) {
            synchronized (pool){
                System.out.println("消费者等待生产者生产商品，当前商品数量为：" + pool.size());
                pool.wait();
            }
        }

        synchronized (pool){
            pool.remove(0);
            pool.notifyAll();
        }
    }
}
