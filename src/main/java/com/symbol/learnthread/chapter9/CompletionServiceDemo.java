package com.symbol.learnthread.chapter9;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author SymbolWong
 * @description CompletionService 演示，异步任务批量执行
 * @date 2023/3/26 17:58
 */
public class CompletionServiceDemo implements Closeable {
    private final CompletionService<File> completionService;
    private final ExecutorService es;
    private final ExecutorService dispatcher;

    public CompletionServiceDemo() {
        this.es = Executors.newSingleThreadExecutor();
        this.dispatcher = Executors.newSingleThreadExecutor();
        this.completionService = new ExecutorCompletionService<File>(this.es);
    }

    public static void main(String[] args) {
        CompletionServiceDemo uploader = new CompletionServiceDemo();
        Set<File> files = new HashSet<>();
        files.add(new File("1"));
        files.add(new File("2"));
        files.add(new File("3"));
        files.add(new File("5"));
        uploader.uploadFiles(files);
    }

    public void uploadFiles(final Set<File> files){
        dispatcher.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                doUploadFiles(files);
            }
        });
    }

    private void doUploadFiles(Set<File> files) throws InterruptedException, ExecutionException {
        // 批量提交上传任务
        for (File file : files) {
            completionService.submit(new UploadTask(file));
        }

        Future<File> future;
        HashSet<File> uploadFiles = new HashSet<>();

        // 获取批量任务的执行结果
        for (File file : files) {
            future = completionService.take();
            uploadFiles.add(future.get());
        }

        System.out.println(uploadFiles);

    }

    @Override
    public void close() throws IOException {

    }
}
