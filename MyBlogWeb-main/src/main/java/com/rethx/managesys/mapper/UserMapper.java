package com.rethx.managesys.mapper;

import com.rethx.managesys.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM db_myblog.user")
    List<User> listUser();

    @Insert("INSERT INTO db_myblog.user " +
            "(username, passwd, nickname, email, phone, create_time, update_time)" +
            " VALUES (#{username}, #{password}, #{nickname}, #{email}, #{phone}, now(), now())")
    void insertUser(User user);

    void updateUser(User user);


    void deleteUser(@Param("id") Integer id);

    Integer getCountUser(@Param("keyWord") String keyWord,
                         @Param("email") String email,
                         @Param("address") String address);

    List<User> pageListUser(@Param("begin") Integer begin,
                            @Param("size") Integer size,
                            @Param("keyWord") String keyWord,
                            @Param("email") String email,
                            @Param("address") String address);

    @Select("select * from db_myblog.user where id=#{id}")
    User getUserById(Integer id);

    @Select("SELECT nickname FROM db_myblog.user WHERE id=#{currId}")
    String getNicknameById(Integer currId);
}
