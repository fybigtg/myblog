package com.rethx.managesys.controller;

import com.rethx.managesys.common.Constants;
import com.rethx.managesys.common.Result;
import com.rethx.managesys.entity.AccountForm;
import com.rethx.managesys.entity.User;
import com.rethx.managesys.entity.UserAccount;
import com.rethx.managesys.service.LoginService;
import com.rethx.managesys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口
 */

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/account")
    public Result getAccount(@RequestParam Integer id){
        User user = userService.getUserById(id);
        if(user == null) return Result.error(Constants.CODE_700,"用户错误");
        return Result.success(user);
    }

    /**
     * 登录账户
     * @param userAccount
     * @return
     */
    @PostMapping()
    public Result login(@RequestBody UserAccount userAccount) {
        if(loginService.login(userAccount) == null) {
            return Result.error("用户名或密码错误！");
        }
        return Result.success(loginService.login(userAccount));
    }

    /**
     * 注册账户
     * @param accountForm
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody AccountForm accountForm) {
        if(!loginService.check(accountForm.getUsername())){
            return Result.error("用户名已存在！");
        }
        loginService.signupUser(accountForm);
        return Result.success();
    }

}
