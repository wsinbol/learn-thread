package com.symbol.learnthread.future;

import java.util.concurrent.*;

/**
 * @author SymbolWong
 * @description 测试Future获取异步结果
 * 一般配合线程池一起使用
 * @date 2023/3/26 21:38
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试Future获取异步结果";
            }
        });

        System.out.println("future.get() = " + future.get());
        executorService.shutdown();

    }
}
