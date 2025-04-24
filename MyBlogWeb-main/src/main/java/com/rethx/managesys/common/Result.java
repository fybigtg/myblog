package com.rethx.managesys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;   //响应码：1 成功，0 失败
    private String msg;
    private Object data;

    public static Result success(){     //增删改，响应成功
        return new Result("1","success",null);
    }
    public static Result success(Object data){  //查询，响应成功
        return new Result("1","success",data);
    }
    public static Result error(String msg){     //响应失败
        return new Result("0",msg,null);
    }
    public static Result error(String code ,String msg){     //响应失败
        return new Result(code,msg,null);
    }
}
