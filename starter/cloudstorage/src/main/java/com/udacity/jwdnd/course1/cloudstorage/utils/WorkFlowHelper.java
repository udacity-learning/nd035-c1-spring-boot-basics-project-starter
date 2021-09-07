package com.udacity.jwdnd.course1.cloudstorage.utils;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowHelper {

    public static User user;
    public static List<UserFile> userFiles = new ArrayList<>();
    public static List<NoteForm> userNotes = new ArrayList<>();
    public static List<CredentialForm> userCredentials = new ArrayList<>();

    public static List<CredentialForm> getUserCredentials() {
        return userCredentials;
    }

    public static void setUserCredentials(CredentialForm credentialForm) {
        WorkFlowHelper.userCredentials.add(credentialForm);
    }

    public static List<NoteForm> getUserNotes() {
        return userNotes;
    }

    public static void setUserNotes(NoteForm userNote) {
        WorkFlowHelper.userNotes.add(userNote);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        WorkFlowHelper.user = user;
    }
}
