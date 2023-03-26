package com.symbol.learnthread.chapter4;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/10 20:59
 */
public class BigFileDownloader {
    protected final URL requestUrl;
    protected final Long fileSize;

    public BigFileDownloader(String requestUrl) throws MalformedURLException {
        this.requestUrl = new URL(requestUrl);
        fileSize = retieveFileSize(requestUrl);
    }

    protected void download(){
        DownTask dt = new DownTask();
        dispatchWork(dt);
    }

    protected void dispatchWork(final DownTask dt){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dt.run();
                }catch (Exception e){
                    System.out.println("出现异常了！");
                }
            }
        });
        thread.setName("downloader");
        thread.start();
    }

    private Long retieveFileSize(String requestUrl) {
        return -1L;
    }

}
