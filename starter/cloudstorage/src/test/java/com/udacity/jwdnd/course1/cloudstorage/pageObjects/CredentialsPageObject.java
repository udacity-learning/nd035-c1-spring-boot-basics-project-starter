package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialsPageObject {

    private WebDriver webDriver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabHref;

    @FindBy(id = "addNewCred")
    private WebElement addNewCredBtn;

    @FindBy(id = "credentialTable")
    private WebElement credTable;

    @FindBy(id = "credential-url")
    private WebElement credUrl;

    @FindBy(id = "credential-username")
    private WebElement credUsername;

    @FindBy(id = "credential-password")
    private WebElement credPassword;

    @FindBy(xpath = "//div[@id='credentialModal']//div[@class='modal-footer']//button[@class='btn btn-primary']")
    private WebElement saveChangesBtn;

    @FindBy(xpath = "//table[@id='credentialTable']/tbody/tr")
    private List<WebElement> credTablesRows;

    @FindBy(id = "editCred")
    private WebElement editCredBtn;

    @FindBy(id = "deleteCred")
    private WebElement deleteCredBtn;

    public WebElement getEditCredBtn() {
        return editCredBtn;
    }

    public WebElement getDeleteCredBtn() {
        return deleteCredBtn;
    }

    public CredentialsPageObject(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public WebElement getCredentialsTabHref() {
        return credentialsTabHref;
    }

    public List<WebElement> getCredTablesRows() {
        return credTablesRows;
    }

    public WebElement getAddNewCredBtn() {
        return addNewCredBtn;
    }

    public WebElement getCredTable() {
        return credTable;
    }

    public WebElement getCredUrl() {
        return credUrl;
    }

    public WebElement getCredUsername() {
        return credUsername;
    }

    public WebElement getCredPassword() {
        return credPassword;
    }

    public WebElement getSaveChangesBtn() {
        return saveChangesBtn;
    }

    public void createCred(String url, String username, String password){
        getCredentialsTabHref().click();
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(getAddNewCredBtn()));
        getAddNewCredBtn().click();
        inputCredInfoAndSubmit(url, username, password);
        getSaveChangesBtn().click();
    }

    public void inputCredInfoAndSubmit(String url, String username, String password){
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(getCredUrl()));

        getCredUrl().clear();
        getCredUrl().sendKeys(url);

        getCredUsername().clear();
        getCredUsername().sendKeys(username);

        getCredPassword().clear();
        getCredPassword().sendKeys(password);
    }

    public WebElement getCredentials(String username){
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(getCredTable()));

        for(WebElement row : getCredTablesRows()){
            WebElement cred = this.webDriver.findElement(By.id("usernameField"));
            if(cred.getText().equalsIgnoreCase(username)){
                return row;
            }
        }
        return null;
    }

    public void editCred(String oldUsername, String urlNew, String usernameNew, String passwordNew){
        WebElement oldCred = getCredentials(oldUsername);
        oldCred.findElement(By.id("editCred")).click();
        inputCredInfoAndSubmit(urlNew, usernameNew, passwordNew);
        getSaveChangesBtn().click();
    }

    public boolean deleteCred(String username){
        WebElement cred = getCredentials(username);
        cred.findElement(By.id("deleteCred")).click();

        return getCredentials(username)==null?true:false;
    }
}
