package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @GetMapping()
    public String homePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> filenames = fileService.getFileNames(auth.getPrincipal().toString());
        model.addAttribute("filenames", filenames);
        return "home";
    }

    @PostMapping()
    public String homePagecheck(){
        return "home";
    }
}
