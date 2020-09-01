package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/31 10:40
 * @version 1.0
 */
@RestController
public class InterceptorController {

    @RequestMapping(value = "/testInterceptor", method = RequestMethod.GET)
    public String testInterceptor() {
        System.out.println("testInterceptor开始执行");
        return "自定义拦截器执行成功！！！";
    }
}

