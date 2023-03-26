package com.symbol.learnthread.future;

import java.util.concurrent.Callable;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 20:29
 */
public class RealData2 implements Callable<String> {

    private String param;

    public RealData2(String param){
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(param);
            Thread.sleep(1000);
        }

        return sb.toString();
    }
}
