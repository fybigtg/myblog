package com.rethx.managesys.mapper;

import com.rethx.managesys.entity.UploadFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    String getTypeByUuid(@Param("uuid") String uuid);

    void insert(UploadFile saveFile);


    @Select("SELECT * FROM db_myblog.file WHERE isdelete=0")
    List<UploadFile> list();

    @Select("SELECT COUNT(*) FROM db_myblog.file WHERE isdelete=0")
    Integer getCount();

    @Select("SELECT * FROM db_myblog.file WHERE isdelete=0 AND filename LIKE concat('%',#{keyword},'%') ORDER BY date DESC LIMIT #{begin} , #{size}")
    List<UploadFile> pageListFile(@Param("begin") Integer begin,
                                  @Param("size") Integer size,
                                  @Param("keyword") String keyword);

    @Update("UPDATE db_myblog.file SET isdelete=1 WHERE id=#{id}")
    void setDeleteTrueById(@Param("id") Integer id);

    @Select("SELECT filename FROM db_myblog.file WHERE uuid=#{uuid} AND isdelete=0")
    String getFilenameByUuid(@Param("uuid") String uuid);

    @Select("SELECT uuid FROM db_myblog.file WHERE id=#{id} AND isdelete=0")
    String getUuidById(Integer id);

    @Select("SELECT type FROM db_myblog.file WHERE id=#{id} AND isdelete=0")
    String getTypeById(Integer id);

    @Select("SELECT * FROM db_myblog.file WHERE writer=#{writer} AND isdelete=0 ORDER BY date DESC LIMIT #{begin} , #{size}")
    List<UploadFile> currPageListFile(@Param("begin") Integer begin,
                                      @Param("size") Integer size, 
                                      @Param("writer") String writer);

    @Select("SELECT COUNT(*) FROM db_myblog.file WHERE writer=#{writer} AND isdelete=0")
    Integer currGetCount(String writer);

    @Select("SELECT writer FROM db_myblog.file WHERE id=#{fileid}")
    String getWriterById(Integer fileid);

    @Update("UPDATE db_myblog.file SET isdelete=1 WHERE id=#{fileid}")
    void deleteById(Integer fileid);
}
