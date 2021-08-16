package com.udacity.jwdnd.course1.cloudstorage.utils;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

import java.util.List;

public class WorkFlowHelper {

    public static User user;
    public static List<File> userFiles;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        WorkFlowHelper.user = user;
    }
}
