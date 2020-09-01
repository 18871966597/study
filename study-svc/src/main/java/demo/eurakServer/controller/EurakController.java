package demo.eurakServer.controller;

import demo.eurakServer.Server.Service.EurakServiceInstance;
import demo.eurakServer.model.EurakEntityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/23 16:46
 * @version 1.0
 */
@RestController
public class EurakController {

    @Autowired
    EurakServiceInstance eurakServiceInstance;

    // 定义服务注册
    // 注册时需要向注册中心发生什么东西(ip,port,ServicId,未知的)=>构建一个类做数据传输
    @RequestMapping(value = "/heartBeat", method = RequestMethod.POST)
    public boolean heartBeat(@RequestBody EurakEntityInstance eurakEntityInstance) {
        return eurakServiceInstance.heartBeat(eurakEntityInstance);
    }

    @RequestMapping(value = "/getServer", method = RequestMethod.POST)
    public List<EurakEntityInstance> getServer(String serviceId) {
        return eurakServiceInstance.getServer(serviceId);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Map<String, Object> getAll() {
        return eurakServiceInstance.getAll();
    }

}
