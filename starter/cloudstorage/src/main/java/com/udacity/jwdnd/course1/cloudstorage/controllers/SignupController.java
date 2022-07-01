package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model){
        String signupErrorMessage = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
            signupErrorMessage = "The username already exists";
        }

        if(signupErrorMessage==null){
            Integer id = userService.createUser(user);
            if(id.intValue()<0){
                signupErrorMessage = "There was an error signing up. Please try again.";
            }
        }

        if(signupErrorMessage==null){
            model.addAttribute("signupSuccess", true);
            return "login";
        } else {
            model.addAttribute("signupError", true);
            model.addAttribute("signupErrorMessage",signupErrorMessage);
        }

        return "signup";
    }
}
