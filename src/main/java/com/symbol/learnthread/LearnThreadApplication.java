package com.symbol.learnthread;

import com.symbol.learnthread.chapter1.FileDownloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LearnThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnThreadApplication.class, args);
        System.out.printf("[1] Welcome! I'm %s.%n", Thread.currentThread().getName());

        // WelcomeThread welcomeThread = new WelcomeThread();
        // welcomeThread.start();
        //
        // Thread thread = new Thread(new WelcomeTaskRunnable());
        // thread.start();

        List<String> urls = Arrays.asList("www.baidu.com", "www.google.com", "www.bing.com");
        for (String url : urls) {
            Thread downloadThread = new Thread(new FileDownloader(url));
            downloadThread.start();
        }

    }

}
