package demo.user.dao;

import demo.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findUserByName(@Param("name") String name);

}
