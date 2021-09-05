package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    List<UserFile> getAllFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)"+
    " VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(UserFile file);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    UserFile getFile(String fileName, Integer userId);

    @Delete("DELETE FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    Integer deleteFile(String fileName, Integer userId);

}
