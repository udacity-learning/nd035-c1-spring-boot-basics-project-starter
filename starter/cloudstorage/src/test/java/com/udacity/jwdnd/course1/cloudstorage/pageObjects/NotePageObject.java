package com.udacity.jwdnd.course1.cloudstorage.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePageObject {

    private WebDriver webDriver;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabHref;

    @FindBy(id = "addNewNote")
    private WebElement addNewNoteBtn;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDescription;

    @FindBy(xpath = "//div[@id='noteModal']//button[@class='btn btn-primary']")
    private WebElement noteSubmitBtn;

    @FindBy(id = "userTable")
    private WebElement noteTable;

    public NotePageObject(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public WebElement getNotesTabHref() {
        return notesTabHref;
    }

    public WebElement getAddNewNoteBtn() {
        return addNewNoteBtn;
    }

    public WebElement getInputNoteTitle() {
        return inputNoteTitle;
    }

    public WebElement getInputNoteDescription() {
        return inputNoteDescription;
    }

    public WebElement getNoteSubmitBtn() {
        return noteSubmitBtn;
    }

    public WebElement getNoteTable() {
        return noteTable;
    }

    public void createNote(String title, String description) throws Exception{
        getNotesTabHref().click();
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(getAddNewNoteBtn()));
        getAddNewNoteBtn().click();
        inputDataToNote(title, description);
        getNoteSubmitBtn().click();
    }

    public void inputDataToNote(String title, String description){
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);

        webDriverWait.until(ExpectedConditions.visibilityOf(getInputNoteTitle()));
        getInputNoteTitle().clear();
        getInputNoteTitle().sendKeys(title);
        getInputNoteDescription().clear();
        getInputNoteDescription().sendKeys(description);
    }

    public WebElement getNoteCreated(String title){
        WebDriverWait webDriverWait = new WebDriverWait(this.webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(getNoteTable()));
        List<WebElement> tableRows = getNoteTable().findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for(WebElement row : tableRows){
            String noteTitle = row.findElement(By.tagName("th")).getText();
            if(noteTitle.equalsIgnoreCase(title)){
                return row;
            }
        }
        return null;
    }

    public void editNote(String title, String newNoteTitle, String newNoteDesc){
        WebElement oldNote = getNoteCreated(title);
        oldNote.findElement(By.tagName("button")).click();
        inputDataToNote(newNoteTitle, newNoteDesc);
        getNoteSubmitBtn().click();
    }

    public boolean deleteNote(String title){
        WebElement note = getNoteCreated(title);
        note.findElement(By.tagName("a")).click();

        return getNoteCreated(title)==null?true:false;
    }
}
