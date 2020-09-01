package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/8/31 10:40
 * @version 1.0
 */
@RestController
public class FilterController {

    @RequestMapping(value = "/testFilter", method = RequestMethod.GET)
    public String testFilter() {
        System.out.println("testFilter开始执行");
        return "自定义过滤器执行成功！！！";
    }

}

