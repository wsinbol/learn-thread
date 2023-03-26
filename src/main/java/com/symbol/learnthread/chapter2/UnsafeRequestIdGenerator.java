package com.symbol.learnthread.chapter2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SymbolWong
 * @description
 * @date 2023/3/11 16:25
 */

/**
 * 术语定义：
 * 状态变量：即类的实例变量、静态变量
 * 共享变量：可以被多个线程共同访问的变量
 */
public final class UnsafeRequestIdGenerator {
    private final static UnsafeRequestIdGenerator INSTANCE = new UnsafeRequestIdGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    /**
     * 共享变量出没，请注意！！！
     */
    private short sequence = -1;

    private UnsafeRequestIdGenerator(){}

    public static UnsafeRequestIdGenerator getInstance(){
        return INSTANCE;
    }

    /**
     * 下面的sequence使用的是局部变量
     * 局部变量不会导致竞态
      */
    public short nextSequence(short sequenceTemp){
        if (sequenceTemp > SEQ_UPPER_LIMIT) {
            sequenceTemp = 0;
        }else {
            sequenceTemp++;
        }
        return sequenceTemp;
    }

    /**
     * 在 short 前添加 synchronized 锁就可以保证线程安全
     * synchronized 使修饰的方法在任一时刻只能被一个线程执行，
     * 使得该方法涉及的共享变量在任一时刻只能够有一个线程访问
     * 避免了交错执行导致脏数据
     * @return
     */
    public short nextSequence(){

        if (sequence > SEQ_UPPER_LIMIT) {
            sequence = 0;
        }else {
            sequence++;
        }
        return sequence;
    }

    public String nextId(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = format.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        // short sequenceNo = this.nextSequence(this.sequence);
        // this.sequence = sequenceNo;
        short sequenceNo = this.nextSequence();
        return "0049" + timestamp + df.format(sequenceNo);
    }
}
