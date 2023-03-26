package com.symbol.learnthread.chapter1;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/10 20:26
 */
public class FileDownloader implements Runnable{
    private final String fileUrl;

    public FileDownloader(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public void run() {
        System.out.printf("Download from %s.%n",this.fileUrl);
    }
}
