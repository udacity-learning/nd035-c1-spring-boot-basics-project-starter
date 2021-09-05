package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    public Integer addNewNote(NoteForm newNote){
        Integer userId = WorkFlowHelper.getUser().getUserid();
        boolean noteExists = WorkFlowHelper.getUserNotes().stream().anyMatch(element -> element.getNoteTitle().contains(newNote.getNoteTitle()));
        Integer rows = null;
        if(newNote!=null){
            newNote.setUserId(userId);
        }

        if(noteExists){
            rows=updateNote(newNote);
            if(rows!=null && rows.intValue()>0){
                return rows;
            }
        } else{
            rows = noteMapper.insertNote(new Note(null, newNote.getNoteTitle(), newNote.getNoteDescription(), userId));
            if(rows!=null && rows.intValue()>0){
                WorkFlowHelper.setUserNotes(newNote);
                return rows;
            }
        }

        return 0;
    }

    public Integer updateNote(NoteForm newNote){
        NoteForm editNote = WorkFlowHelper.getUserNotes().stream().filter(element -> element.getNoteTitle().equals(newNote.getNoteTitle()))
                .findAny().orElse(null);
        Integer rows = noteMapper.updateRow(editNote.getNoteDescription(), editNote.getNoteTitle(), editNote.getUserId());
        if(rows!=null && rows.intValue()>0){
            editNote.setNoteDescription(newNote.getNoteDescription());
        }
        return rows;
    }

    public List<NoteForm> getUserNotes(){
        User user = WorkFlowHelper.getUser();
        List<Note> userNotes = noteMapper.getUserNotes(user.getUserid());

        if(!CollectionUtils.isEmpty(userNotes)){
            for(Note note: userNotes){
                if(note.getUserId().equals(user.getUserid())){
                    WorkFlowHelper.setUserNotes(new NoteForm(note.getNoteTitle(), note.getNoteDescription(), user.getUserid()));
                }
            }
        }
        return WorkFlowHelper.getUserNotes();
    }
}
