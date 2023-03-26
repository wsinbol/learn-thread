package com.symbol.learnthread.chapter8;

import java.util.concurrent.*;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/10 22:08
 */
public class TaskResultRetrievalDemo {

    final ThreadPoolExecutor executor = new ThreadPoolExecutor(0,16,4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(16),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public Future<String> recognizeImage(final String imageFile){

        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        return submit;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TaskResultRetrievalDemo demo = new TaskResultRetrievalDemo();
        Future<String> future = demo.recognizeImage("imageFile");
        future.get();
    }
}
