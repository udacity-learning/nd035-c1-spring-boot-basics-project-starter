package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/noteupload")
    public String uploadNote(@ModelAttribute("newnote") NoteForm newNote, Model model) {
        Integer rows = noteService.addNewNote(newNote);
        loadDataForDisplay(model);
        return "home";
    }

    @GetMapping("/deleteNote/{delete}")
    public String deleteNote(@PathVariable("delete") Integer noteId, Model model){
        Integer rows = noteService.deleteUserNotes(noteId);
        loadDataForDisplay(model);
        model.addAttribute("newnote", new NoteForm());
        return "home";
    }

    public void loadDataForDisplay(Model model){
        List<UserFile> files = WorkFlowHelper.userFiles;
        if(!files.isEmpty()){
            List<String> filenames = new ArrayList<>();
            for(UserFile file : files){
                filenames.add(file.getFileName());
            }
            model.addAttribute("filenames", filenames);
        }
        model.addAttribute("notes", WorkFlowHelper.getUserNotes());
        model.addAttribute("credentials", WorkFlowHelper.getUserCredentials());
        model.addAttribute("newcredential", new CredentialForm());
        model.addAttribute("activeTab2", true); // this one is to keep current (Notes) tab active
        model.addAttribute("noteTabPaneActive", true);
    }
}
