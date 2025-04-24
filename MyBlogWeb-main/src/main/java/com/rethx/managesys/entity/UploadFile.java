package com.rethx.managesys.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UploadFile {

    private Integer id;
    private String filename;
    private String writer;
    private String uuid;
    private String type;
    private String url;
    private Boolean isDelete;
    private LocalDateTime date;

}
