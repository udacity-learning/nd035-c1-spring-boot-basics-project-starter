package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageObjects.CredentialsPageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.HomePageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.pageObjects.SignUpPageObject;
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
	private static String username;
	private static String password;
	private static String firstName;
	private static String lastName;
	private static String baseUrl;

	private SignUpPageObject signUp;
	private LoginPageObject loginPageObject;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		username = "johnnash";
		password = "johnnash";
		firstName = "john";
		lastName = "nash";
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

	public void signupSetup(){
		getSignupPage();
		signUp = new SignUpPageObject(driver);
		signUp.setSignupFormAndSubmit(firstName, lastName, username, password);
		signUp.getSignupSubmitBtn().submit();
	}

	public void loginUser(){
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
		signupSetup();
		String actualMessage = signUp.getSignupSuccess().getText();
		String expectedMessage = "You successfully signed up! Please continue to the login page.";
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void loginTest(){
		//signup setup
		signupSetup();

		//login test
		loginUser();

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
		//signup setup
		signupSetup();

		//login user
		loginUser();

		HomePageObject homePageObject = new HomePageObject(driver);

		String noteTitle = "test1";
		String noteDesc = "this is test1";
		homePageObject.createNote(noteTitle, noteDesc);

		// exists
		WebElement actualResult = homePageObject.getNoteCreated(noteTitle);
		Assertions.assertNotNull(actualResult);

		// view Description
		String actualTitle = actualResult.findElement(By.tagName("th")).getText();
		String actualDesc = actualResult.findElement(By.id("noteDescription")).getText();
		Assertions.assertEquals(noteTitle, actualTitle);
		Assertions.assertEquals(noteDesc, actualDesc);
	}

	@Test
	public void noteEditAndDelete() throws Exception{
		//signup setup
		signupSetup();

		//login user
		loginUser();

		HomePageObject homePageObject = new HomePageObject(driver);

		String noteTitle = "test1";
		String noteDesc = "this is test1";
		homePageObject.createNote(noteTitle, noteDesc);


		// edit note
		String newNoteTitle = "newtest1";
		String newNoteDesc = "this is new test1";
		homePageObject.editNote(noteTitle, newNoteTitle, newNoteDesc);

		// exists
		WebElement actualResult = homePageObject.getNoteCreated(newNoteTitle);
		Assertions.assertNotNull(actualResult);

		// view new note
		String actualTitle = actualResult.findElement(By.tagName("th")).getText();
		String actualDesc = actualResult.findElement(By.id("noteDescription")).getText();
		Assertions.assertEquals(newNoteTitle, actualTitle);
		Assertions.assertEquals(newNoteDesc, actualDesc);

		// delete note
		boolean isDeleted = homePageObject.deleteNote(newNoteTitle);
		Assertions.assertTrue(isDeleted);
	}

	@Test
	public void credCreationTest(){
		//signup setup
		signupSetup();

		//login user
		loginUser();

		String url = "http://www.google.com/";
		String username = "johanash";
		String password = "johnnash";
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
	}

	@Test
	public void credEditAndDelete() throws Exception {
		//signup setup
		signupSetup();

		//login user
		loginUser();

		String url = "http://www.google.com/";
		String username = "johanash";
		String password = "johnnash";
		CredentialsPageObject credentialsPageObject = new CredentialsPageObject(driver);
		credentialsPageObject.createCred(url, username, password);

		// edit cred
		String urlNew = "http://www.google.com/editcred";
		String usernameNew = "johanash1";
		String passwordNew = "johnnash1";
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
	}
}
