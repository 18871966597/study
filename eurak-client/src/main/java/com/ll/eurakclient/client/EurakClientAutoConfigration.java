package com.ll.eurakclient.client;

import com.alibaba.fastjson.JSON;
import com.ll.eurakclient.model.EurakClient;
import com.ll.eurakclient.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/26 14:52
 * @version 1.0
 */
@Configuration // 配置类
@EnableConfigurationProperties(EurakClientProperties.class)
public class EurakClientAutoConfigration {

    @Autowired
    private EurakClient eurakClient;

    // 动态创建eurakclient实例
    @Bean
    public EurakClient creat(EurakClientProperties pro) {
        System.out.println("开始初始化客户端！！！");
        EurakClient eurakClient = new EurakClient();
        eurakClient.setHost(pro.getHost());
        eurakClient.setPort(pro.getPort());
        eurakClient.setServerName(pro.getServerName());
        eurakClient.setAdress(pro.getAdress());
        return eurakClient;
    }

    // 将配置文件的bean向注册中心注册，调用注册中心的接口进行注册
    // c创建一个线程池发送注册信息并保持心跳
    ScheduledExecutorService heartBeat = Executors.newScheduledThreadPool(2);

    @PostConstruct
    public void register() {
        heartBeat.scheduleAtFixedRate(() -> {
            System.out.println("发送心跳到注册中心！！！");
            // 发送http请求注册到服务中心  //httpclient okhttp
            try {
                String s = HttpUtils.postRequest(eurakClient.getAdress(), JSON.toJSONString(eurakClient));
                System.out.println("注册中心返回注册结果！" + s);
            } catch (IOException e) {
                System.out.println("心跳错误异常！！！");
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

}
