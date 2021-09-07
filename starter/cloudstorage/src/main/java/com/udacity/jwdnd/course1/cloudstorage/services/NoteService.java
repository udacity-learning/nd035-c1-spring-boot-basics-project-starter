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
        Integer rows = null;

        if(newNote.getNoteId()!=null){
            return updateNote(newNote);
        } else{
            Note userNote = new Note(null, newNote.getNoteTitle(), newNote.getNoteDescription(), userId);
            rows = noteMapper.insertNote(userNote);
            if(rows!=null && rows.intValue()>0){
                WorkFlowHelper.setUserNotes(new NoteForm(userNote.getNoteid(), userNote.getNoteTitle(), userNote.getNoteDescription(), userId));
                return rows;
            }
        }
        return rows;
    }

    public Integer updateNote(NoteForm newNote){
        NoteForm editNote = WorkFlowHelper.getUserNotes().stream().filter(element -> element.getNoteId().equals(newNote.getNoteId()))
                .findAny().orElse(null);
        Note userNote = new Note(newNote.getNoteId(), newNote.getNoteTitle(), newNote.getNoteDescription(), editNote.getUserId());
        Integer rows = noteMapper.updateRow(userNote);
        if(rows!=null && rows.intValue()>0){
            editNote.setNoteTitle(newNote.getNoteTitle());
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
                    WorkFlowHelper.setUserNotes(new NoteForm(note.getNoteid(),note.getNoteTitle(), note.getNoteDescription(), user.getUserid()));
                }
            }
        }
        return WorkFlowHelper.getUserNotes();
    }

    public Integer deleteUserNotes(Integer noteId){
        User user = WorkFlowHelper.getUser();
        Integer rows = noteMapper.deleteNotes(noteId, user.getUserid());
        if(rows!=null && rows.intValue()>0){
            WorkFlowHelper.userNotes.removeIf(element -> (element.getNoteId().equals(noteId)));
        }
        return rows;
    }
}
