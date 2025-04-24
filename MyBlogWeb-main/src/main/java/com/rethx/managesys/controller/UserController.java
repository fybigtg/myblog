package com.rethx.managesys.controller;

import com.rethx.managesys.common.Result;
import com.rethx.managesys.entity.User;
import com.rethx.managesys.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Result listUser() {
        List<User> users = userService.listUser();
        return Result.success(users);
    }

    @GetMapping("/page")
    public Result pageListUser(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String keyWord,
                               @RequestParam String email,
                               @RequestParam String address) {
        HashMap<String,Object> users =
                userService.pageListUser(pageNum, pageSize, keyWord, email, address);
        return Result.success(users);
    }

    @GetMapping("/export")
    public Result exportUser(HttpServletResponse response) throws IOException {
        userService.exportUser(response);
        return Result.success();
    }

    @PostMapping("/import")
    public Result importUser(MultipartFile file) throws IOException {
        userService.importUser(file);
        return Result.success();
    }

    @PostMapping()
    public Result saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatchUser(@RequestBody List<Integer> ids) {
        userService.deleteBatchUser(ids);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

}
