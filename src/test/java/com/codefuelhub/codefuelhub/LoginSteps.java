package com.codefuelhub.codefuelhub;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.ProxySettings;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

	public class LoginSteps extends AbstractPageStepDefinition {
		private Scenario scenario;
		static Eyes eyes;
		WebDriver dr ;
		
		@Before("@Login")
		public void initiateBrowser(Scenario scenario){
			this.scenario = scenario;
			eyes = initApplitools(eyes);
			
			init();
			dr = initWebDriver();
			dr.manage().window().maximize();
			dr = setWinApplit(dr, "login", eyes);			
			
		}
			
		@After("@Login")
		public void testShutDown(){
			if (dr != null) {
				dr.quit();
				System.out.println("closing webdriver...");
				}
			
			dr = null;
		}
		
		@Given("^I browse to login page$")
		public void navigateToLoginPage()  {
			goToHubPage();
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			try {
				if(waitForElement(By.id("loginBtn"))){
					dr.findElement(By.id("loginBtn")).click();
					a.waitForVisibleElement(dr, By.id("myModal"), 60);
				}
				else
					System.out.println("Login element wasn't found!"+ false);
				
			} catch (Exception e) {
				scenario.write(takeScreenShot(dr, "click_login_frontPage"));
				System.out.println("Failed to click on ligon button on front page: " + e.getMessage());
				scenario.write("Failed to click on ligon button on front page: " + e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\click_login_frontPage... ");
			}
		}
		
		public void goToHubPage(){
			dr.get(BASE_URL);
		}
		
		public void setDriver(WebDriver driver) {
			dr = driver;
		}
		public WebDriver getDriver(){
			return dr;
		}
		
		public boolean waitForElement(By locator) {
			
			AbstractPageStepDefinition abs = new AbstractPageStepDefinition();
			return abs.waitForVisibleElement(dr, locator, 10000);
			
		}
								
		
		@When("^I enter ([^\"]*) and ([^\"]*) first time$")
		public void enterUserAndPass(String username, String password)  {				
			
			dr.switchTo().frame("myFrame");
			dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			try {
				if (username != "skip"){ //if known user, use it, else use new created user
					if(username.contains("Codefuel") || username.contains("Super"))
						dr.findElement(By.id("Email")).sendKeys(username);
					else
						dr.findElement(By.id("Email")).sendKeys(MAIL_ADD);
				}
				dr.findElement(By.id("Password")).sendKeys(password);
				dr.findElement(By.id("login")).click();
			
			} catch(Exception e){
				System.out.println("Couldn't fill login: "+ e.getMessage());
				scenario.write("Couldn't fill login: "+ e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fill_fail...");
				scenario.write(takeScreenShot(dr, "login_fill_fail"));
			}
		}
				
		@When("^click Login$")
		public void clickLogin()  {
			try {
				dr.findElement(By.id("loginBtn")).click();
				
			} catch (Exception e) {
				System.out.println("Couldn't fill login: "+ e.getMessage());
				scenario.write("Couldn't fill login: "+ e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fill_fail...");
				scenario.write(takeScreenShot(dr, "login_fill_fail"));
			}
		}

		@Then("^validate login pass$")
		public void validateLogin() {
			
			AbstractPageStepDefinition a = new AbstractPageStepDefinition();
			try {
			
				a.waitUntilElementClassAttrChange(dr,By.tagName("body"), "pg-loaded", 60000);
				boolean successLogin = dr.getCurrentUrl().contains("dashboard") || dr.getCurrentUrl().contains("newUser") || dr.getCurrentUrl().contains("activeUser");
				
				System.out.println(dr.getCurrentUrl());
				
				Assert.assertTrue(successLogin);
				if(!successLogin) {
					System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
					scenario.write(takeScreenShot(dr, "login_fail"));
				}
			} catch (Exception e) {
				System.out.println("Couldn't verify login: "+ e.getMessage());
				scenario.write("Couldn't verify login: "+ e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
				scenario.write(takeScreenShot(dr, "login_fail"));
			}
			
			
		}
		
		@Then("^validate login Fail$")
		public void validate_login_fail() {
			try {
				Assert.assertFalse(dr.getCurrentUrl().contains("dashboard"));
			} catch (Exception e) {
				
				System.out.println("Couldn't verify failed login: "+ e.getMessage());
				scenario.write("Couldn't verify failed login: "+ e.getMessage());
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_fail...");
				scenario.write(takeScreenShot(dr, "login_fail"));
			}
		}

		
		@Then("^validate warning message ([^\"]*)$")
		public void validateLoginFailMessage(String message) {
			boolean found = false;
			String pageSource = dr.getPageSource();
			found = pageSource .contains(message);
			Assert.assertTrue(found);
			
			if(found){
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message...");
				scenario.write(takeScreenShot(dr, "login_message"));
			} else{
				System.out.println("Find screen shot at: " + PS_FILE_NAME + "\\login_message_fail...");
				scenario.write(takeScreenShot(dr, "login_message_fail"));
			}
		}
		
		@And("^User log out$")
		public void userLogout() {
			try {
				dr.findElement(By.id("logout")).click();
			} catch (Exception e) {
				System.out.println("Couldn't perform logout: "+ e.getMessage());
				scenario.write("Couldn't perform logout: "+ e.getMessage());
			}
		}
		
		@And("^verify login window$")
		public void verifyLoginAplitools()  {
		    verifyAplitools("login", eyes, dr);
		}
		
	}
