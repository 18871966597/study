package demo.redis.handler;

import org.springframework.stereotype.Component;

/**
 * <p>
 *     消息订阅者
 * </p>
 *
 * @author ll Create on 20/7/9 16:11
 * @version 1.0
 */
@Component
public class SecondMessageReceiver {

    /**
     * 接收消息方法
     */
    public void receiverMessage2(String message) {
        System.out.println("MessageReceiver2收到一条新消息：" + message);
    }
}