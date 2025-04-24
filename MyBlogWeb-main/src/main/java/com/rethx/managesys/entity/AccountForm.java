package com.rethx.managesys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm {

    private String username;
    private String passwd;
    private String nickname;
    private String phone;
    private String email;

}
