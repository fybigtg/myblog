package com.rethx.managesys.service;

import com.rethx.managesys.entity.User;
import com.rethx.managesys.utils.TokenUtils;
import com.rethx.managesys.entity.AccountForm;
import com.rethx.managesys.entity.UserAccount;
import com.rethx.managesys.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public User login(UserAccount userAccount) {
        Integer id = loginMapper.login(userAccount);
        if(id!= null){
            User user = loginMapper.GetUserById(id);
            user.setToken(TokenUtils.genToken(id.toString(), user.getPasswd()));
            return user;
        }
        return null;
    }

    public void signupUser(AccountForm accountForm) {
        loginMapper.insertUserAccount(accountForm);
    }

    public Boolean check(String username) {
        return loginMapper.checkUsername(username) == 0;
    }
}
