package demo.eurakServer.serverService;

import demo.eurakServer.model.EurakEntityInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/23 17:17
 * @version 1.0
 */
@Service
public interface EurakServiceInstance {

    /*
     *注册服务接口
     */
    // 定义心跳
    public boolean heartBeat(EurakEntityInstance eurakEntityInstance);

    // 服务注册,服务更新,服务续约
    public String renew(EurakEntityInstance eurakEntityInstance);
    /*
     *提供服务接口
     */

    // 获取在线服务
    public List<EurakEntityInstance> getServer(String serviceId);

    // 获取所有服务
    public Map<String, Object> getAll();

}
