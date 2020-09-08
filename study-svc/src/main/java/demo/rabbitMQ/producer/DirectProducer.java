package demo.rabbitMQ.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/7 21:17
 * @version 1.0
 */
public class DirectProducer {

    private final static String DIRECT_EXCHANGE_NAME = "direct_logs";

    private final static String TOPIC_EXCHANGE_NAME = "topic_logs";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        // 生产者确认模式开启设置
        channel.confirmSelect();
        // 异步确认需要实现对信道监听并实现返回确认消息时候的做法和返回拒绝消息的做法
        channel.addConfirmListener(new ConfirmListener() {
            // deliverytag 消息主键（代表一次唯一的投递）,单调递增的正整数
            // multiple 批处理,默认为false
            @Override
            public void handleAck(long deliverytag, boolean multiple) throws IOException {
                System.out.println("ACK = deliverytag = " + deliverytag + "multiple = " + multiple);
            }

            @Override
            public void handleNack(long deliverytag, boolean multiple) throws IOException {
                System.out.println("ACK = deliverytag = " + deliverytag + "multiple = " + multiple);
            }
        });

        // 无法找到合适队列时会调用这个returnListener
        //1、mandatory参数为true，投递消息时无法找到一个合适的队列
        //消息返回给生产者
        //false 丢弃消息(缺省)
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("msg:"+msg);
            }
        });

        // channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String[] info = new String[]{"win.info", "win.error", "win.warning"};
        for (int i = 0; i < 3; i++) {
            String server = info[1];
            String message = "hello World" + (i + 1);

            // 同步不需要设置mandatory参数缺省默认为false
            // channel.basicPublish(TOPIC_EXCHANGE_NAME, server, null, message.getBytes());
            channel.basicPublish(TOPIC_EXCHANGE_NAME, server, true,null, message.getBytes());
            System.out.println("Send " + server + ":" + message);
            // 生产者发送确认模式 同步机制实现会一直阻塞
           /* try {
                if(channel.waitForConfirms()){
                    System.out.println("消息发送成功！！！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }

        channel.close();
        connection.close();

    }
}
