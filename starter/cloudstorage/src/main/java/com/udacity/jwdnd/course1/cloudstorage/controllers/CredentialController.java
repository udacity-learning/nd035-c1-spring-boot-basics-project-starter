package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/uploadCredential")
    public String uploadCredential(@ModelAttribute("newcredential")CredentialForm newCredential, Model model){
        Integer rows = credentialService.addUserCredential(newCredential);
        loadDataForDisplay(model);
        return "home";
    }

    @GetMapping("/deleteCredential/{delete}")
    public String deleteCredential(@PathVariable("delete") Integer credentialId, Model model){

        Integer rows = credentialService.deleteUserCredential(credentialId);
        loadDataForDisplay(model);
        model.addAttribute("newcredential", new CredentialForm());

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
        model.addAttribute("newnote", new NoteForm());
        model.addAttribute("activeTab3", true); // this one is to keep current (Credentials) tab active
        model.addAttribute("credTabPaneActive", true);
    }

}
