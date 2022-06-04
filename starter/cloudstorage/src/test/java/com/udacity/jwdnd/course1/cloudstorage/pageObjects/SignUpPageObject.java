package com.udacity.jwdnd.course1.cloudstorage.pageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPageObject {

    @FindBy(xpath = "//div[@class='alert alert-dark']")
    private WebElement signupSuccess;

    @FindBy(xpath = "//div[@class='alert alert-danger']")
    private WebElement signupError;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    private WebElement signupSubmitBtn;


    public SignUpPageObject(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getSignupSuccess() {
        return signupSuccess;
    }

    public WebElement getSignupError() {
        return signupError;
    }

    public WebElement getInputFirstName() {
        return inputFirstName;
    }

    public WebElement getInputLastName() {
        return inputLastName;
    }

    public WebElement getInputUsername() {
        return inputUsername;
    }

    public WebElement getInputPassword() {
        return inputPassword;
    }

    public WebElement getSignupSubmitBtn() {
        return signupSubmitBtn;
    }

    public void setSignupFormAndSubmit(String firstName, String lastName, String username, String password){
        getInputFirstName().sendKeys(firstName);
        getInputLastName().sendKeys(lastName);
        getInputUsername().sendKeys(username);
        getInputPassword().sendKeys(password);
    }
}
