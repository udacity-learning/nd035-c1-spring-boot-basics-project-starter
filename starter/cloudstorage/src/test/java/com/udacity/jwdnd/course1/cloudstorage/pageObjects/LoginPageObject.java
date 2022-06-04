package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    private WebElement loginSubmit;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    public LoginPageObject(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getInputUsername() {
        return inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public WebElement getLoginSubmit() {
        return loginSubmit;
    }

    public void userLogin(String username, String password){
        getInputUsername().sendKeys(username);
        getInputPassword().sendKeys(password);
        getLoginSubmit().submit();
    }
}
