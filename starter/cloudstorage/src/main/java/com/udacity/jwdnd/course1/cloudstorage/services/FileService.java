package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controllers.FileController;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileMapper fileMapper;
    private UserService userService;
    private User user;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<String> getFileNames(String username){
        user=WorkFlowHelper.getUser();
        List<UserFile> files = fileMapper.getAllFiles(user.getUserid());
        List<String> filenames = new ArrayList<>();
        for(UserFile file : files){
            WorkFlowHelper.userFiles.add(file);
            filenames.add(file.getFileName());
        }
        return filenames;
    }

    public boolean isFileExists(MultipartFile uploadedFile){
        boolean filenameExists = false;
        if(!uploadedFile.isEmpty()){
            for(UserFile file: WorkFlowHelper.userFiles){
                if(file.getFileName().equals(uploadedFile.getOriginalFilename()))
                    filenameExists=true;
            }
        }
        return filenameExists;
    }

    public Integer addFiles(MultipartFile uploadedFile) {
        Integer userId = WorkFlowHelper.getUser() != null ? WorkFlowHelper.getUser().getUserid() : null;

        if (userId != null && !uploadedFile.isEmpty()) {
            try {
                UserFile fileToUpload = new UserFile(null, uploadedFile.getOriginalFilename(), uploadedFile.getContentType(),
                        Long.toString(uploadedFile.getSize()), userId, uploadedFile.getBytes());
                Integer fileId = fileMapper.insertFile(fileToUpload);
                WorkFlowHelper.userFiles.add(fileToUpload);
                logger.info("file "+uploadedFile.getOriginalFilename()+" uploaded successfully");
                return fileId;
            } catch (IOException e) {
                logger.error("file: " + uploadedFile.getOriginalFilename() + " upload failed");
            }
        } else{
            logger.warn("file not found. Upload failed");
        }
        return null;
    }

    public UserFile getUserFile(String filename){
        UserFile userFile = fileMapper.getFile(filename, WorkFlowHelper.getUser().getUserid());
        logger.info("file size: "+userFile.getFileSize());
        return userFile;
    }

    public boolean deleteUserFile(String filename){
        int rowsDeleted = fileMapper.deleteFile(filename, WorkFlowHelper.user.getUserid());

        if(rowsDeleted>0){
            logger.info("file "+filename+" is deleted successfully");
            WorkFlowHelper.userFiles.removeIf(element -> (element.getFileName().equals(filename) &&
                    element.getUserId().equals(WorkFlowHelper.getUser().getUserid())));
            return true;
        }
        return false;
    }

}
