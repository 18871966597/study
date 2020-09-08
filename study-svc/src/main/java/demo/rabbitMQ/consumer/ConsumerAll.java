package demo.rabbitMQ.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/9/8 15:39
 * @version 1.0
 */
public class ConsumerAll {
    private final static String DIRECT_EXCHANGE_NAME = "direct_logs";
    private final static String TOPIC_EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        // channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明随机队列
        String queueName = channel.queueDeclare().getQueue();
        // *代表一个层级，#代表所有的
        // win下面所有的都会被接受到
        String server = "win.#";
        // 队列绑定到交换器。  一个队列可以绑定多个交换器
        channel.queueBind(queueName, TOPIC_EXCHANGE_NAME, server);

        System.out.println("waiting message .......");

        Consumer consumerA = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                // envelope 信封
                String message = new String(body, "UTF-8");
                System.out.println("Accept:" + envelope.getRoutingKey() + ":" + message);
                // 消费者返回确认消息(DeliveryTag,是否批量回复)
                this.getChannel().basicAck(envelope.getDeliveryTag(),false);
                System.out.println("ack:" + envelope.getRoutingKey() + ":" + message);

            }
        };

        channel.basicConsume(queueName, false, consumerA);
    }

}

