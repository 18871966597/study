package demo.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.redis.handler.FirstMessageReceiver;
import demo.redis.handler.SecondMessageReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static demo.redis.enums.TopicEnum.FIRST_TOPIC;
import static demo.redis.enums.TopicEnum.SECOND_TOPIC;

/**
 * redis配置
 *
 * @author ll
 * @create 2020-07-10 9:59
 */
@Configuration//相当于xml中的beans
public class RedisConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter1,
                                            MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫chat的通道
        container.addMessageListener(listenerAdapter1, new PatternTopic(FIRST_TOPIC.getName()));
        //订阅了一个叫chat的通道
        container.addMessageListener(listenerAdapter2, new PatternTopic(SECOND_TOPIC.getName()));
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     *
     * @param firstReceiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter1(FirstMessageReceiver firstReceiver) {
        //给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //不填defaultListenerMethod默认调用handleMessage
        return new MessageListenerAdapter(firstReceiver, "receiverMessage1");
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     *
     * @param secondReceiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter2(SecondMessageReceiver secondReceiver) {
        //给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //不填defaultListenerMethod默认调用handleMessage
        return new MessageListenerAdapter(secondReceiver, "receiverMessage2");
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 使用Jackson2JsonRedisSerialize替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置key和value的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}