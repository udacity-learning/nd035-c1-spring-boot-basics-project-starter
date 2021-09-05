package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadedFile, Model model){

        Integer fileId=null;
        boolean filenameExists=false;

        if(!fileService.isFileExists(uploadedFile)){
            fileId=fileService.addFiles(uploadedFile);
        } else{
            filenameExists=true;
        }


        if(filenameExists){
            model.addAttribute("filenameExits", true);
            model.addAttribute("exitingFilename", uploadedFile.getOriginalFilename());
        } else if(fileId==null) {
            model.addAttribute("fileUploadError", true);
        } else{
            model.addAttribute("fileUploadSuccess", true);
        }

        //get all files of the user to display
        List<UserFile> files = WorkFlowHelper.userFiles;
        if(!files.isEmpty()){
            List<String> filenames = new ArrayList<>();
            for(UserFile file : files){
                filenames.add(file.getFileName());
            }
            model.addAttribute("filenames", filenames);
        }
        model.addAttribute("newnote", new NoteForm());
        return "home";
    }

    @GetMapping("/viewFile/{file}")
    public void downloadFile(@PathVariable("file") String filename, HttpServletResponse response) throws IOException{
        UserFile userFile = fileService.getUserFile(filename);
        byte[] byteArray = userFile.getFileData();

        response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
        response.setHeader("Content-Disposition", "attachment; filename=" + userFile.getFileName());
        response.setContentLength(byteArray.length);

        OutputStream os=response.getOutputStream();
        try {
            os.write(byteArray, 0, byteArray.length);
        } finally {
            os.close();
        }
    }

    @GetMapping("/deleteFile/{file}")
    public String deleteFile(@PathVariable("file") String filename, Model model){
        boolean isFileDeleted = fileService.deleteUserFile(filename);

        //get all files of the user to display
        List<UserFile> files = WorkFlowHelper.userFiles;
        if(!files.isEmpty()){
            List<String> filenames = new ArrayList<>();
            for(UserFile file : files){
                filenames.add(file.getFileName());
            }
            model.addAttribute("filenames", filenames);
        }
        model.addAttribute("isFileDeleted", isFileDeleted);
        model.addAttribute("newnote", new NoteForm());
        return "home";
    }
}
