package com.dsp.soybean.demo.po;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author dsp
 * @date 2020-08-02
 */
public class LockTest {

    private static final Map<String,String> params = new ConcurrentHashMap<>();


    public static void testCountDownLatch(){
        int threadCount = 2000;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for(int i=0; i< threadCount; i++){
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                try {
                    params.put("sid", "线程id=" +Thread.currentThread().getId());
                    String result = HttpUtil.httpGet("http://127.0.0.1:8080/demo/lock", params);
                    System.out.println("线程result" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadCount+"个线程已经执行完毕");
    }

    public static void main(String[] args) {
        testCountDownLatch();
    }
}
