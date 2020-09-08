package demo.rabbitMQ.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        // channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String[] info = new String[]{"win.info", "win.error", "win.warning"};
        for (int i = 0; i < 3; i++) {
            String server = info[1];
            String message = "hello World" + (i + 1);

            channel.basicPublish(TOPIC_EXCHANGE_NAME, server, null, message.getBytes());
            System.out.println("Send " + server + ":" + message);

        }

        channel.close();
        connection.close();

    }
}
