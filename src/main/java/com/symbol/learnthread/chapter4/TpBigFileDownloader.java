package com.symbol.learnthread.chapter4;

import java.net.MalformedURLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author SymbolWong
 * @description 使用线程池执行任务
 * @date 2023/3/10 21:31
 */
public class TpBigFileDownloader extends BigFileDownloader{
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(2,16,4,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(16),new CallerRunsPolicy());

    public TpBigFileDownloader(String requestUrl) throws MalformedURLException {
        super(requestUrl);
    }

    public static void main(String[] args) throws MalformedURLException {
        TpBigFileDownloader downloader = new TpBigFileDownloader("url");
        downloader.download();
    }

    @Override
    protected void dispatchWork(DownTask dt) {
        // execute 是无返回值的
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        // submit方法有返回值
        Future<?> submit = executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    dt.run();
                } catch (Exception e) {
                    System.out.println("又出现异常了！！！");
                }
            }
        });

        // 使用 lambda 形式创建
        executor.submit(() -> {
            try{
                dt.run();
            }catch (Exception e){
                System.out.println("又出现异常了！！！");
            }
        });
    }

    @Override
    protected void download() {
        super.download();
    }
}
