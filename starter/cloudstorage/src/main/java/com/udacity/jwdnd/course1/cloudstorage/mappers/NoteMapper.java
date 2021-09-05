package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) "+
    "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insertNote(Note note);

    @Update("UPDATE NOTES SET notedescription=#{noteDescription} where notetitle=#{noteTitle} "+
    "and userid=#{userId}")
    Integer updateRow(String noteDescription, String noteTitle, Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getUserNotes(Integer userId);
}
