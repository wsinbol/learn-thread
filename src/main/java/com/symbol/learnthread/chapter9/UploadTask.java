package com.symbol.learnthread.chapter9;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 18:34
 */
public class UploadTask implements Callable<File> {
    private final File file;
    public UploadTask(File file) {
        this.file = file;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public File call() throws Exception {
        upload(file);
        return file;
    }

    private void upload(File file) {
        // todo 实现上传过程
        System.out.println(file + "已经上传");
    }
}
