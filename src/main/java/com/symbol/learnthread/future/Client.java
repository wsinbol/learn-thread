package com.symbol.learnthread.future;

import lombok.SneakyThrows;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 20:17
 */
public class Client {
    public Data request(final String queryStr){
        FutureData futureData = new FutureData();
        new Thread(){
            @SneakyThrows
            @Override
            public void run(){
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }.start();

        return futureData;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        // System.out.println("data.getResult() = " + data.getResult());
        try{
            /**
             * 用 sleep 代替对其他业务逻辑的处理
             * 在处理这些业务逻辑过程中，RealData被创建，进而充分利用了等待时间
             */
            System.out.println("我去处理其他业务了");
            Thread.sleep(1000);
        }catch (Exception e){

        }

        System.out.println("data.getResult() = " + data.getResult());

    }
}
