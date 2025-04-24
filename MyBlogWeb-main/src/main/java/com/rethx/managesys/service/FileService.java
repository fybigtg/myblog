package com.rethx.managesys.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.rethx.managesys.entity.UploadFile;
import com.rethx.managesys.mapper.FileMapper;
import com.rethx.managesys.mapper.UserMapper;
import com.rethx.managesys.utils.FileUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service
public class FileService {

    @Value("${files.download.path}")
    private String downloadPath;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    public String upload(MultipartFile file,String fileUploadPath) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //存储到硬盘
        File uploadParentFile = new File(fileUploadPath);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定义唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(uploadParentFile + "/" + uuid + StrUtil.DOT + type);
//        //获取文件md5
//        String md5 = SecureUtil.md5(uploadFile);
//        UploadFile getfile = getFileByMd5(md5);

        file.transferTo(uploadFile);
        String url = downloadPath + "/file/" + uuid;
        //存储到数据库
        UploadFile saveFile = new UploadFile();
        saveFile.setIsDelete(false);
        saveFile.setFilename(originalFilename);
        saveFile.setUuid(uuid);
        saveFile.setType(type);
        saveFile.setUrl(url);
//        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }


    public boolean download(String uuid, String fileUploadPath, HttpServletResponse response) throws IOException {
        if(fileMapper.getTypeByUuid(uuid) == null){
            return false;
        }
        //根据唯一标识码获取文件
        String fileUuid = uuid + StrUtil.DOT + fileMapper.getTypeByUuid(uuid);
        String fileFullName = fileMapper.getFilenameByUuid(uuid);
        File uploadFile = new File(fileUploadPath + "/" + fileUuid);
        //设置输出流格式
        ServletOutputStream sos = response.getOutputStream();
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileFullName,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        sos.write(FileUtil.readBytes(uploadFile));
        sos.flush();
        sos.close();
        return true;
    }

    public List<UploadFile> listFile() {
        return fileMapper.list();
    }

    public HashMap<String, Object> pageListUser(Integer pageNum, Integer pageSize, String keyword) {
        List<UploadFile> pageList = fileMapper.pageListFile((pageNum-1)*pageSize, pageSize, keyword);
        Integer total = fileMapper.getCount();
        HashMap<String,Object> fileList = new HashMap<>();
        fileList.put("total",total);
        fileList.put("pageList",pageList);
        return fileList;
    }

    public void deleteFile(Integer id) {
        fileMapper.setDeleteTrueById(id);
    }

    public void deleteFileBatch(List<Integer> ids) {
        for(Integer id : ids){
            fileMapper.setDeleteTrueById(id);
        }
    }

    public void insertFile(UploadFile file) {
        fileMapper.insert(file);
    }

    public boolean checkUuid(String uuid, Integer id) {
        String cUuid = fileMapper.getUuidById(id);
        return uuid.equals(cUuid);
    }

    public String getContent(String path, String uuid, Integer id) {
        String type = fileMapper.getTypeById(id);
        String filepath = path+"/"+uuid+"."+type;
        return FileUtils.fileToStr(filepath);
    }

    public String currentUser(Integer currId) {
        return userMapper.getNicknameById(currId);
    }

    public HashMap<String, Object> currPageListUser(Integer pageNum, Integer pageSize, Integer currid) {
        String writer = userMapper.getNicknameById(currid);
        List<UploadFile> pageList = fileMapper.currPageListFile((pageNum-1)*pageSize, pageSize, writer);
        Integer total = fileMapper.currGetCount(writer);
        HashMap<String,Object> fileList = new HashMap<>();
        fileList.put("total",total);
        fileList.put("pageList",pageList);
        return fileList;
    }

    public void deleteCurrFile(Integer fileid, Integer currid) {
        String writer = fileMapper.getWriterById(fileid);
        String nickname = userMapper.getNicknameById(currid);
        if(writer.equals(nickname)){
            fileMapper.deleteById(fileid);
        }
    }


//    private UploadFile getFileByMd5(String md5){
//        //查询文件是否存在md5
//        QueryWrapper<UploadFile> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("md5",md5);
//       return fileMapper.selectOne(queryWrapper);
//    }
}
