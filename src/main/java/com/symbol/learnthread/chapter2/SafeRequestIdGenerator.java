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
public final class SafeRequestIdGenerator {
    private final static SafeRequestIdGenerator INSTANCE = new SafeRequestIdGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    /**
     * 共享变量出没，请注意！！！
     */
    private volatile Integer  sequence = -1;

    private SafeRequestIdGenerator(){}

    public static SafeRequestIdGenerator getInstance(){
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
    public synchronized Integer nextSequence(){

        if (sequence > SEQ_UPPER_LIMIT) {
            sequence = 0;
        }else {
            sequence++;
        }
        return sequence;
    }

    /**
     * 下面的方法是错误的，因为多个线程看到的sequence变量并不是同一个，
     * 所以每次synchronized加锁都加到了不同的对象上，进而导致线程安全问题
     * 正确的做法应该是对instance加锁，即 synchronized(instance)
     * @return
     */
    public Integer nextSequence1(){
        synchronized (sequence){
            if (sequence > SEQ_UPPER_LIMIT) {
                sequence = 0;
            }else {
                sequence++;
            }
            return sequence;
        }
    }

    public String nextId(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = format.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        // short sequenceNo = this.nextSequence(this.sequence);
        // this.sequence = sequenceNo;
        Integer sequenceNo = this.nextSequence();
        return "0049" + timestamp + df.format(sequenceNo);
    }
}
