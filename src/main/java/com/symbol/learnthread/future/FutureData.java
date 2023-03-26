package com.symbol.learnthread.future;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/26 20:09
 */
public class FutureData implements Data{
    protected RealData realData = null;
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if (isReady) {
            return;
        }
        this.realData = realData;
        this.isReady = true;
        /**
         * realData 已经注入，通知 getResult
         */
        notifyAll();
    }
    @Override
    public synchronized String getResult(){
        while (!isReady) {
            try{
                /**
                 * 一直等待，直到realData注入完成
                 */
                wait();
            }catch (Exception e){

            }
        }
        return realData.getResult();
    }


}
