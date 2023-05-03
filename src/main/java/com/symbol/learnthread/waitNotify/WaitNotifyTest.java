package com.symbol.learnthread.waitNotify;

import java.util.Vector;

/**
 * @author SymbolWong
 * @description wait-notify 演示案例
 * @date 2023/5/3 16:47
 */
public class WaitNotifyTest {
    public static void main(String[] args) {
        Vector<Integer> pool = new Vector<>();
        Producer producer = new Producer(pool, 10);
        Cosumer cosumer = new Cosumer(pool);
        new Thread(producer).start();
        new Thread(cosumer).start();
    }

}
