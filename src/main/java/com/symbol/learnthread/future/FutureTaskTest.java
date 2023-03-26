package com.symbol.learnthread.future;

import java.util.concurrent.*;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 21:42
 */
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试FutureTask获取异步结果";
            }
        });

        /**
         * 使用专门的线程来执行futureTask
         */
        new Thread(futureTask).start();
        System.out.println("futureTask.get() = " + futureTask.get());

    }

    /**
     * 使用线程池来执行futureTask
     */
    public static void main1(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试FutureTask获取异步结果";
            }
        });
        executorService.execute(futureTask);
        System.out.println("futureTask.get() = " + futureTask.get());
        executorService.shutdown();
    }


}
