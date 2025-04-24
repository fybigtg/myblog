package com.rethx.managesys.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.rethx.managesys.entity.User;
import com.rethx.managesys.entity.UserAccount;
import com.rethx.managesys.mapper.UserMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService{
//public class UserService extends ServiceImpl<UserMapper,User> {

    @Autowired
    private UserMapper userMapper;

    public List<User> listUser() {
        return userMapper.listUser();
    }

    public void saveUser(User user) {
        if(user.getId() == null){
            user.setPasswd("123456");
            userMapper.insertUser(user);
        }else{
            userMapper.updateUser(user);
        }
    }

    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    public HashMap<String, Object> pageListUser(Integer pageNum, Integer pageSize,
                                                String  keyWord, String email, String address) {
        List<User> pageList = userMapper.pageListUser((pageNum-1)*pageSize, pageSize, keyWord, email, address);
        Integer total = userMapper.getCountUser(keyWord, email, address);
        HashMap<String,Object> userList = new HashMap<>();
        userList.put("total",total);
        userList.put("pageList",pageList);
        return userList;
    }

    public void saveBatchUser(List<User> userList){
        for(User user : userList){
            saveUser(user);
        }
    }

    public void deleteBatchUser(List<Integer> ids) {
        for(Integer id : ids){
            deleteUser(id);
        }
    }

    public void exportUser(HttpServletResponse response) throws IOException {
        List<User> userList = userMapper.listUser();
        //在内存操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","电子邮箱");
        writer.addHeaderAlias("phone","联系方式");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("updateTime","更新时间");

        writer.write(userList,true);
        //设置浏览器相应的格式
        response.setContentType("application/vnd.openxmalformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    public void importUser(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(is);
        List<User> userList = reader.readAll(User.class);
        saveBatchUser(userList);
    }
}
