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

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteid} "+
    "AND userid=#{userId}")
    Integer updateRow(Note userNote);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getUserNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId} AND userid=#{userId}")
    Integer deleteNotes(Integer noteId, Integer userId);
}
