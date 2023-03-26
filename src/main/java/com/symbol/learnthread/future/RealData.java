package com.symbol.learnthread.future;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 20:03
 */
public class RealData implements Data{
    private final String result;

    public RealData(String param) throws InterruptedException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(param);
            Thread.sleep(1000);
        }
        result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
