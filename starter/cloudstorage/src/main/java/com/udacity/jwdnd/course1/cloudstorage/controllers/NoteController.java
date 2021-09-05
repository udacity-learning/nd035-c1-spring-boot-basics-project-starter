package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/noteupload")
    public String uploadNote(@ModelAttribute("newnote") NoteForm newNote, Model model) {
        Integer rows = noteService.addNewNote(newNote);
        model.addAttribute("notes", WorkFlowHelper.getUserNotes());
        return "home";
    }
}
