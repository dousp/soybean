package com.dsp.soybean.demo.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dsp
 * @date 2020-08-02
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    public static int COUNT = 3;

    @ResponseBody
    @RequestMapping("/lock")
    public String lock(@RequestParam("sid") String serverId) {
        Long counter = redisTemplate.opsForValue().increment("COUNTER", 1);
        redisTemplate.expire("COUNTER", 43200, TimeUnit.SECONDS);

        if (null != counter && counter>3) {
            return "大于3了";
        }
        RLock lock = redissonClient.getFairLock("TEST");
        try {
            lock.lock(5, TimeUnit.SECONDS);

            logger.info("Request Thread - " + counter + "[" + serverId +"] locked and begun...");
            if (COUNT > 0) {
                COUNT = COUNT - 1;
                Thread.sleep(1000);
            } else {
                return "为0了";
            }
            logger.info("Request Thread - " + counter + "[" + serverId +"] ended successfully...");
        } catch (Exception ex) {
            logger.error("Error occurred");
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
            logger.info("Request Thread - " + counter + "[" + serverId +"] unlocked...");
        }

        return "卖出lock-" + counter + "[" + serverId +"]" + COUNT;
    }

    @RequestMapping("/bucket")
    public void bucket() {
        // 设置字符串
        RBucket<String> keyObj = redissonClient.getBucket("k1");
        keyObj.set("v1236");
    }

}
