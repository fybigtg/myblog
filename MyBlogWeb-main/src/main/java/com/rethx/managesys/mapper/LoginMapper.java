package com.rethx.managesys.mapper;


import com.rethx.managesys.entity.AccountForm;
import com.rethx.managesys.entity.User;
import com.rethx.managesys.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT id from db_myblog.user where username=#{username} and passwd=#{passwd}")
    Integer login(UserAccount userAccount);

    @Select("SELECT * from db_myblog.user where id=#{id}")
    User GetUserById(Integer id);

    void insertUserAccount(AccountForm accountForm);

    @Select("SELECT count(username) from db_myblog.user where username=#{username}")
    Integer checkUsername(String username);
}
