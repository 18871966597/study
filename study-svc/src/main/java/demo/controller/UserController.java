package demo.controller;

import com.alibaba.fastjson.JSONObject;
import demo.user.dto.User;
import demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public JSONObject findUserByName(@RequestParam(value = "name", required = true) String name) throws Exception {
        List<User> list = new ArrayList<>();
        list = userService.findUserByName(name);
        if (CollectionUtils.isEmpty(list)) {
            throw new Exception("查询数据为空！！！");
        }
        return (JSONObject) list;
    }
}
