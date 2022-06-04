package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String userLogout() {
        WorkFlowHelper.userFiles.clear();
        WorkFlowHelper.setUser(null);
        WorkFlowHelper.userNotes.clear();
        WorkFlowHelper.userCredentials.clear();
        return "login";
    }
}
