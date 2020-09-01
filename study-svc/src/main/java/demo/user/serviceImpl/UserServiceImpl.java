package demo.user.serviceImpl;

import demo.user.dao.UserMapper;
import demo.user.dto.User;
import demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUserByName(String name) {
        List<User> users = userMapper.findUserByName(name);
        return users;
    }
}
