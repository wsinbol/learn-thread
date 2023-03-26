package com.symbol.learnthread.chapter2;

import com.symbol.learnthread.utils.Tools;

/**
 * @author SymbolWong
 * @description thread.join方法的作用和子到父的可见性
 *
 * @date 2023/3/11 19:02
 */
public class ThreadJoinVisibility {
    static int data = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Tools.randomPause(50);
                data = 1;
            }
        });

        thread.start();
        try{
            // 通过join控制 thread 的未来死亡时间,0 表示不限时间
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //无论randomPause多久，输出的data恒等于1
        System.out.println("data = " + data);
    }
}
