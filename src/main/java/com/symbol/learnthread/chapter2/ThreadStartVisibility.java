package com.symbol.learnthread.chapter2;

import com.symbol.learnthread.utils.Tools;
import lombok.SneakyThrows;

/**
 * @author SymbolWong
 * @description 演示父线程与子线程的变量可见性问题
 * 结论：
 * 父线程在子线程启动前对共享变量的更新对于子线程的可见性是有保证的
 * 父线程在子线程启动后对共享变量的更新对于子线程的可见性是不保证的
 * @date 2023/3/11 18:40
 */
public class ThreadStartVisibility {
    static int data = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Tools.randomPause(50);
                // 输出的值为1或2，表明父线程的更新对子线程的共享变量可见性是不保证的
                System.out.println(data);
            }
        });

        data = 1;
        thread.start();
        Tools.randomPause(50);
        //通过注释下一行代码查看执行效果
        data = 2;



    }
}
