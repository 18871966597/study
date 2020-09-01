package demo.redis.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 消息发送者
 * </p>
 *
 * @author ll Create on 20/7/9 16:11
 * @version 1.0
 */
@EnableScheduling//开启定时器功能
@Component
public class MessageSender {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 间隔2秒，通过RedisTemplate对象向redis消息队列chat频道发布消息
     */
    /*@Scheduled(fixedDelay = 2000)
    public void sendMessage1() {
        redisTemplate.convertAndSend(FIRST_TOPIC.getName(), System.currentTimeMillis());
    }

    *//**
     * 间隔2秒，通过RedisTemplate对象向redis消息队列chat频道发布消息
     *//*
    @Scheduled(fixedDelay = 2000)
    public void sendMessage2() {
        redisTemplate.convertAndSend(SECOND_TOPIC.getName(), System.currentTimeMillis());
    }*/
    //@Scheduled(fixedDelay = 1000000)
    public void setMessage() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");
        for (int i = 0; i < 100; i++) {
            Object o = redisTemplate.opsForList().leftPush("temp:taskId:001",String.valueOf(i));
            System.out.println("加入成功"+o.toString());
        }
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                Object o =redisTemplate.opsForList().rightPop("temp:taskId:001", 1, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + "取出来的STR数据"+o.toString());
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "线程执行完毕!!");
    }

    //@Scheduled(fixedDelay = 1000)
    public void getMessage(){
        Object o =redisTemplate.opsForList().rightPop("temp:taskId:001", 1, TimeUnit.SECONDS);
        System.out.println("被方法1取出来的STR数据"+o.toString());
    }

    //@Scheduled(fixedDelay = 1000)
    public void getMessage2(){
        Object o =redisTemplate.opsForList().rightPop("temp:taskId:001", 1, TimeUnit.SECONDS);
        System.out.println("被方法2取出来的STR数据"+o.toString());
    }
}
