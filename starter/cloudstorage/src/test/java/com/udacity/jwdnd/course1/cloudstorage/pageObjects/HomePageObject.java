package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageObject {

    private WebDriver webDriver;

    @FindBy(xpath = "//button[@class='btn btn-secondary float-right']")
    private WebElement logoutBtn;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTabHref;

    @FindBy(id = "fileUploadSuccess")
    private WebElement fileUploadSuccess;

    @FindBy(id = "filenameExits")
    private WebElement filenameExits;

    @FindBy(id = "fileUploadError")
    private WebElement fileUploadError;

    @FindBy(id = "fileUpload")
    private WebElement chooseFileBtn;

    @FindBy(xpath = "//div[@id='nav-files']//div[@class='col-sm-4']/button[@type='type']")
    private WebElement uploadActionBtn;

    public HomePageObject(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public WebElement getLogoutBtn() {
        return logoutBtn;
    }

    public WebElement getFilesTabHref() {
        return filesTabHref;
    }

    public WebElement getFileUploadSuccess() {
        return fileUploadSuccess;
    }

    public WebElement getFilenameExits() {
        return filenameExits;
    }

    public WebElement getFileUploadError() {
        return fileUploadError;
    }

    public WebElement getChooseFileBtn() {
        return chooseFileBtn;
    }

    public WebElement getUploadActionBtn() {
        return uploadActionBtn;
    }

}
