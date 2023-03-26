package com.symbol.learnthread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author SymbolWong
 * @description 该例子是 Client 的推荐实现
 * @date 2023/3/26 20:33
 */
public class FutureTask2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new RealData2("data"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);
        System.out.println("请求完毕");

        /**
         * 模拟去做其他任务
         */
        Thread.sleep(1000);
        System.out.println("futureTask.get() = " + futureTask.get());
    }
}
