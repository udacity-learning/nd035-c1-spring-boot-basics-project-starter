package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;
    private User user;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<String> getFileNames(String username){
        user = userService.getUserDetails(username);
        WorkFlowHelper.setUser(user);

        List<File> files = fileMapper.getAllFiles(user.getUserid());
        List<String> filenames = new ArrayList<>();
        for(File file : files){
            WorkFlowHelper.userFiles.add(file);
            filenames.add(file.getFileName());
        }
        return filenames;
    }

}
