package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String baseUrl;

	private SignUpPageObject signUp;
	private LoginPageObject loginPageObject;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		baseUrl = "http://localhost:";
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();

	}

	@AfterEach
	public void afterEach() {

		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signupSetup(String firstName, String lastName, String username, String password){
		getSignupPage();
		signUp = new SignUpPageObject(driver);
		signUp.setSignupFormAndSubmit(firstName, lastName, username, password);
		signUp.getSignupSubmitBtn().submit();
	}

	public void loginUser(String username, String password){
		getLoginPage();
		loginPageObject = new LoginPageObject(driver);
		loginPageObject.userLogin(username, password);
	}

	@Test
	public void getLoginPage() {
		driver.get(baseUrl+ this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage(){
		driver.get(baseUrl + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void unauthorizedAccessTest(){
		driver.get(baseUrl + this.port + "/home");
		String title = driver.getTitle();
		Assertions.assertEquals("Login", title);
	}

	@Test
	public void submitSignupFormTest(){
		signupSetup("john", "nash", "johnnash","johnnash");
		String actualMessage = signUp.getSignupSuccess().getText();
		String expectedMessage = "You successfully signed up! Please continue to the login page.";
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void loginTest(){

		String username = "johnnash1";
		String password = "johnnash";

		//signup setup
		signupSetup("john", "nash", username,password);

		//login test
		loginUser(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		// logout action test
		HomePageObject homePageObject = new HomePageObject(driver);
		homePageObject.getLogoutBtn().submit();

		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals("http://localhost:"+this.port+"/logout", driver.getCurrentUrl());

		getLoginPage();
		driver.get("http://localhost:"+this.port+"/home");
		String expected = "Login";

		Assertions.assertEquals(expected, driver.getTitle());
	}

	@Test
	public void noteCreationTest() throws Exception{
		String username = "johnnash2";
		String password = "johnnash";

		//signup setup
		signupSetup("john", "nash", username,password);

		//login test
		loginUser(username, password);

		NotePageObject notePageObject = new NotePageObject(driver);

		String noteTitle = "test1";
		String noteDesc = "this is test1";
		notePageObject.createNote(noteTitle, noteDesc);

		// exists
		WebElement actualResult = notePageObject.getNoteCreated(noteTitle);
		Assertions.assertNotNull(actualResult);

		// view Description
		String actualTitle = actualResult.findElement(By.tagName("th")).getText();
		String actualDesc = actualResult.findElement(By.id("noteDescription")).getText();
		Assertions.assertEquals(noteTitle, actualTitle);
		Assertions.assertEquals(noteDesc, actualDesc);

		HomePageObject homePageObject = new HomePageObject(driver);
		homePageObject.getLogoutBtn().submit();
	}

	@Test
	public void noteEditAndDelete() throws Exception{
		String username = "johnnash3";
		String password = "johnnash";

		//signup setup
		signupSetup("john", "nash", username,password);

		//login test
		loginUser(username, password);

		NotePageObject notePageObject = new NotePageObject(driver);

		String noteTitle = "test1";
		String noteDesc = "this is test1";
		notePageObject.createNote(noteTitle, noteDesc);

		// edit note
		String newNoteTitle = "newtest1";
		String newNoteDesc = "this is new test1";
		notePageObject.editNote(noteTitle, newNoteTitle, newNoteDesc);

		// exists
		WebElement actualResult = notePageObject.getNoteCreated(newNoteTitle);
		Assertions.assertNotNull(actualResult);

		// view new note
		String actualTitle = actualResult.findElement(By.tagName("th")).getText();
		String actualDesc = actualResult.findElement(By.id("noteDescription")).getText();
		Assertions.assertEquals(newNoteTitle, actualTitle);
		Assertions.assertEquals(newNoteDesc, actualDesc);

		// delete note
		boolean isDeleted = notePageObject.deleteNote(newNoteTitle);
		Assertions.assertTrue(isDeleted);

		HomePageObject homePageObject = new HomePageObject(driver);
		homePageObject.getLogoutBtn().submit();
	}

	@Test
	public void credCreationTest(){
		String username = "johnnash4";
		String password = "johnnash";

		//signup setup
		signupSetup("john", "nash", username,password);

		//login test
		loginUser(username, password);

		String url = "http://www.google.com/";
		CredentialsPageObject credentialsPageObject = new CredentialsPageObject(driver);
		credentialsPageObject.createCred(url, username, password);

		// exists
		WebElement actualResult = credentialsPageObject.getCredentials(username);
		Assertions.assertNotNull(actualResult);

		// view credentials
		String actualUrl = actualResult.findElement(By.id("urlField")).getText();
		String actualUsername = actualResult.findElement(By.id("usernameField")).getText();
		String actualPassword = actualResult.findElement(By.id("passwordField")).getText();
		Assertions.assertEquals(url, actualUrl);
		Assertions.assertEquals(username, actualUsername);
		Assertions.assertNotEquals(password, actualPassword);

		HomePageObject homePageObject = new HomePageObject(driver);
		homePageObject.getLogoutBtn().submit();
	}

	@Test
	public void credEditAndDelete() throws Exception {
		String username = "johnnash5";
		String password = "johnnash";

		//signup setup
		signupSetup("john", "nash", username,password);

		//login test
		loginUser(username, password);

		String url = "http://www.google.com/";
		CredentialsPageObject credentialsPageObject = new CredentialsPageObject(driver);
		credentialsPageObject.createCred(url, username, password);

		// edit cred
		String urlNew = "http://www.google.com/editcred";
		String usernameNew = "johanash6";
		String passwordNew = "johnnash6";
		credentialsPageObject.editCred(username, urlNew, usernameNew, passwordNew);

		// exists
		WebElement actualResult = credentialsPageObject.getCredentials(usernameNew);
		Assertions.assertNotNull(actualResult);

		// view new credentials
		String actualUrl = actualResult.findElement(By.id("urlField")).getText();
		String actualUsername = actualResult.findElement(By.id("usernameField")).getText();
		String actualPassword = actualResult.findElement(By.id("passwordField")).getText();
		Assertions.assertEquals(urlNew, actualUrl);
		Assertions.assertEquals(usernameNew, actualUsername);
		Assertions.assertNotEquals(passwordNew, actualPassword);

		// delete Credentials
		boolean isDeleted = credentialsPageObject.deleteCred(usernameNew);
		Assertions.assertTrue(isDeleted);

		HomePageObject homePageObject = new HomePageObject(driver);
		homePageObject.getLogoutBtn().submit();
	}
}
