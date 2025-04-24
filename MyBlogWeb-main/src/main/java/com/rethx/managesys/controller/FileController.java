package com.rethx.managesys.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.rethx.managesys.common.Result;
import com.rethx.managesys.entity.AccountForm;
import com.rethx.managesys.entity.UploadFile;
import com.rethx.managesys.service.FileService;
import com.rethx.managesys.utils.FileUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 文件上传接口
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Value("${files.download.path}")
    private String fileDownloadPath;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result upload(@RequestBody JSONObject param) throws IOException {
        String filename = param.getString("filename");
        String type = param.getString("type");
        Integer currId = param.getInteger("currid");
        String uuid = IdUtil.fastSimpleUUID();
        String filepath = fileUploadPath+"/"+uuid+"."+type;
        FileUtils.strToFile(param.getString("content"),filepath);
        String url = fileDownloadPath+"/file/"+uuid;

        String writer = fileService.currentUser(currId);
        UploadFile file = new UploadFile();
        file.setWriter(writer);
        file.setFilename(filename);
        file.setType(type);
        file.setUuid(uuid);
        file.setUrl(url);

        fileService.insertFile(file);
        return Result.success(url);
    }

    @GetMapping("/{uuid}")
    public Result download(@PathVariable String uuid, @RequestParam Integer id) throws IOException {
        if(!fileService.checkUuid(uuid,id)){
            return Result.error("文件不存在");
        }
        String res = fileService.getContent(fileUploadPath,uuid,id);
        return Result.success(res);
    }

    @GetMapping()
    public Result listFile(){
        return Result.success(fileService.listFile());
    }

    @GetMapping("/page")
    public Result pageListFile(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String keyword) {
        HashMap<String,Object> files = fileService.pageListUser(pageNum, pageSize, keyword);
        return Result.success(files);
    }

    @GetMapping("/currpage")
    public Result currPageListFile(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer currid) {
        HashMap<String,Object> files = fileService.currPageListUser(pageNum, pageSize, currid);
        return Result.success(files);
    }

    @DeleteMapping("/{fileid}")
    public Result deleteFile(@PathVariable Integer fileid,@RequestParam Integer currid){
        fileService.deleteCurrFile(fileid,currid);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteFileBatch(@RequestBody List<Integer> ids){
        fileService.deleteFileBatch(ids);
        return Result.success();
    }




}
