package demo.eurakServer.serverServiceImpl;

import demo.eurakServer.serverService.EurakServiceInstance;
import demo.eurakServer.model.EurakEntityInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/23 16:35
 * @version 1.0
 */
@Component
public class EurakServerInstanceImpl implements EurakServiceInstance {

    private static final Logger logger = LoggerFactory.getLogger(EurakServerInstanceImpl.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // 注册中心应该有什么东西
    // 1.放这些服务的东西(jdk提供 Map,set,List,queue)
    // 2.怎么获取服务，根据什么获取(ServiseId){Map<Key,Value>的形式存放}
    // 说明服务启动回向map中放东西,获取服务的时候就是把map中的数据取出来

    /*
     * @description: 存放注册的服务实例
     *
     *    获取服务时应该一个服务ID有多个服务故 Object 中存放List
     */
    private static Map<String, Object> SERVER_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean heartBeat(EurakEntityInstance instance) {
        // 1.相同的ip,port。正常心跳
        // 2.ip或者port不同。但是key存在，服务集群注册
        // 3.key存在，instances为null  或者key不存在 都是新服务的注册
        // 定义一个新的状态码
        switch (renew(instance)) {
            case "404":
                // 404 服务不存在需要注册的新服务
                addNewServer(instance);
                break;
            case "303":
                // 303 集群部署
                addNewServer(instance);
                break;
            // case "200":
            // 正常服务的心跳.修改更新时间和状态
            // addNewServer(instance);
            // break;
            default:
                System.out.println("啥也没干！！！");
                break;
        }
        return true;
    }

    // 新服务注册
    void addNewServer(EurakEntityInstance instance) {
        List<EurakEntityInstance> instances = (List<EurakEntityInstance>) SERVER_MAP.get(instance.getServerName());
        if (CollectionUtils.isEmpty(instances)) {
            instances = new ArrayList<>();
        }
        instance.setStatus("UP");//第一次设置未上线状态
        instance.setIsDirtWithTime(System.currentTimeMillis());//设置第一次上线的时间
        instances.add(instance);
        SERVER_MAP.put(instance.getServerName(), instances);
    }

    @Override
    public String renew(EurakEntityInstance instance) {
        // 如果服务的key存在
        if (SERVER_MAP.containsKey(instance.getServerName())) {
            List<EurakEntityInstance> instances = (List<EurakEntityInstance>) SERVER_MAP.get(instance.getServerName());
            if (CollectionUtils.isEmpty(instances)) {
                return "404";// 服务不存在 需要新增服务的
            } else {
                for (EurakEntityInstance oldInstance : instances) {
                    if (oldInstance.getHost().equals(instance.getHost()) && oldInstance.getPort().equals(instance.getPort())) {
                        // 修改原有服务的正常心跳
                        oldInstance.setStatus("UP");
                        oldInstance.setIsDirtWithTime(System.currentTimeMillis());
                    } else {
                        // 存在key但是需要新增服务。集群部署
                        return "303";
                    }
                }
            }
        } else {
            return "404";// 服务不存在 需要新增服务的
        }
        return "500";
    }

    // 下线服务的线程池
    ScheduledExecutorService removeSchedule = Executors.newScheduledThreadPool(2);

    /*ScheduledExecutorService removeSchedule = (ScheduledExecutorService) new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));*/

    @PostConstruct
    public void downService() {
        removeSchedule.scheduleAtFixedRate(() -> {
            System.out.println("执行任务下线功能");
            for (Map.Entry serverMap : SERVER_MAP.entrySet()) {
                List<EurakEntityInstance> list = (List<EurakEntityInstance>) serverMap.getValue();
                for (EurakEntityInstance instance : list) {
                    if (System.currentTimeMillis() - instance.getIsDirtWithTime() > 30000) {
                        instance.setStatus("DOWN");
                        // 当服务下线后发送一个异步的处理事件
                        applicationEventPublisher.publishEvent("IP：" + instance.getHost() + ",port:" + instance.getPort() +
                                "，服务名称：" + instance.getServerName() + ":下线了!!");
                    } else {
                        instance.setStatus("UP");
                        instance.setIsDirtWithTime(System.currentTimeMillis());
                    }
                }
            }
        }, 60, 30, TimeUnit.SECONDS);
    }


    @Override
    public List<EurakEntityInstance> getServer(String serviceId) {
        List<EurakEntityInstance> instances = (List<EurakEntityInstance>) SERVER_MAP.get(serviceId);
        return instances.stream().filter(ins -> "UP".equals(ins.getStatus())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getAll() {
        return SERVER_MAP;
    }
}
