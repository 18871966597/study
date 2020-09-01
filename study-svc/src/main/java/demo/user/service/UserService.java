package demo.user.service;

import demo.user.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> findUserByName(String name);
}
