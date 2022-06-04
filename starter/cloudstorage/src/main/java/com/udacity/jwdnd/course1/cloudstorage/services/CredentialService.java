package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.WorkFlowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    public Integer addUserCredential(CredentialForm credentialForm){
        User user = WorkFlowHelper.getUser();
        Integer rows = null;
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        if(credentialForm.getCredentialId()!=null){
            return updateUserCredential(credentialForm, encodedKey, encryptedPassword);
        } else {
            Credential userCredential = new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(),
                    encodedKey, encryptedPassword, user.getUserid());
            rows = credentialMapper.addCredential(userCredential);

            if(rows.intValue()>0){
                WorkFlowHelper.setUserCredentials(new CredentialForm(userCredential.getCredentialId(), credentialForm.getUrl(), credentialForm.getUsername(), credentialForm.getPassword(),
                        encryptedPassword, user.getUserid()));
            }
        }
        return rows;
    }

    public Integer deleteUserCredential(Integer credentialId){
        User user = WorkFlowHelper.getUser();
        Integer rows = credentialMapper.deleteUserCredential(credentialId, user.getUserid());
        if(rows.intValue()>0){
            WorkFlowHelper.getUserCredentials().removeIf(element -> (element.getCredentialId().equals(credentialId)));
        }

        return rows;
    }

    private Integer updateUserCredential(CredentialForm credentialForm, String encodedKey, String encryptedPassword){
        CredentialForm updateCredential = WorkFlowHelper.getUserCredentials().stream().filter(element -> (element.getCredentialId().equals(credentialForm.getCredentialId())))
                .findAny().orElse(null);
        Credential userCredential = new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUsername(),
                encodedKey, encryptedPassword, updateCredential.getUserId());
        Integer rowsUpdated = credentialMapper.updateUserCredential(userCredential);
        if(rowsUpdated!=null && rowsUpdated.intValue()>0){
            updateCredential.setUrl(userCredential.getUrl());
            updateCredential.setUsername(userCredential.getUsername());
            updateCredential.setPassword(credentialForm.getPassword());
            updateCredential.setEncryptedPassword(userCredential.getPassword());
        }

        return rowsUpdated;
    }

    public List<CredentialForm> getUserCredentials(){
        User user = WorkFlowHelper.getUser();

        List<Credential> userCredentials = credentialMapper.getUserCredentials(user.getUserid());
        if(!CollectionUtils.isEmpty(userCredentials)){
            for(Credential credential: userCredentials){
                String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
                WorkFlowHelper.setUserCredentials(new CredentialForm(credential.getCredentialId(), credential.getUrl(), credential.getUsername(),
                        decryptedPassword, credential.getPassword(), user.getUserid()));
            }
        }
        return CollectionUtils.isEmpty(userCredentials)? Collections.emptyList():WorkFlowHelper.getUserCredentials();
    }
}
