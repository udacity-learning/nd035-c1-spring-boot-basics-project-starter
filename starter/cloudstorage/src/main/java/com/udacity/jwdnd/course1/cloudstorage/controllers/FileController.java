package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadedFile, Model model){
        logger.info("uploaded filename : "+uploadedFile.getOriginalFilename());
        return "home";
    }
}
