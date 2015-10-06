package com.codefuelhub.codefuelhub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MailinatorImplement extends AbstractPageStepDefinition{

	WebDriver dr ;	
	
	public void initiateBrowser(){
		dr = initWebDriver();
		dr.manage().window().maximize();
	}
	
	public void navigateToMail() {
		dr.get("http://mailinator.com/");
	}
	
	public void setDriver(WebDriver driver){
		dr = driver;
	}
	
	public void createMailAddress(String mailAddress){
		AbstractPageStepDefinition a = new AbstractPageStepDefinition();
		
		a.waitForVisibleElement(dr, By.xpath("//*[@id=\"inboxfield\"]"), 3000);
		
		WebElement inboxElem = dr.findElement(By.xpath("//*[@id=\"inboxfield\"]"));
		inboxElem.sendKeys(mailAddress);
		WebElement btn = dr.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div[2]/div/div/btn"));
		
		btn.click();
		
	}
	public void clickOnMailRecieved(String xpath, String mail){
		dr.findElement(By.xpath(xpath)).click();
		
	}
	
	public void clickOnLinkInMail(String link) {
		dr.switchTo().frame(dr.findElement(By.name("rendermail")));
		dr.findElement(By.partialLinkText(link)).click();
		
	}
	
}
