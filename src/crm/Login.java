package src.crm;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import src.testUtils.Helper;


public class Login extends Helper{
		@Test
		public void Login_CRM() throws Exception{
			/*
			 * Checking login button is present in home page
			 */
	  
			if(driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).getAttribute("value").equals("Login"))
				Reporter.log("<p>" + "Login button is available in Lead CRM home page");
			else
				Assert.fail("Login Button is not present");
	
			/*
			 * Getting username and password from spreadsheet 1 and trying to log in
			 */
			Reporter.log("<p>" + "Trying to login with different username and passwords from excel sheets");
			int rows = sh0.getRows();
			for(int i=0;i<rows;i++){
			int j=0;
			driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(sh0.getCell(j, i).getContents());
			j++;
			driver.findElement(By.id(or.getProperty("passwd"))).sendKeys(sh0.getCell(j, i).getContents());
			driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).click();
			sleep(2);
			//WebElement error_msg = driver.findElement(By.id(or.getProperty("errormsg_id"))).findElements(By.tagName(or.getProperty("errormsg_tagname"))).get(2);
			//System.out.println(error_msg);
			WebElement error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label")).get(2);
			Reporter.log("<p>" + "logging with username: " +sh0.getCell(j-1, i).getContents() +", password: " +sh0.getCell(j, i).getContents() +"-->" +error_msg.getText());
			}
	  
	  /*		//Checking for forgot password link
	  		List<WebElement> pass_links = driver.findElement(By.id("wrapper")).findElements(By.tagName(or.getProperty("pass_link_tagname")));
	  		Reporter.log("<p>" + "FORGOT PASSWORD LINK PRESENT");
	  		Reporter.log("<p>" + "Forgot password link is = " +pass_links.get(2).findElement(By.tagName(or.getProperty("tag"))).getAttribute("href"));
	  		  
	  		//clicking forgot password link
			driver.findElement(By.id(or.getProperty("errormsg_id"))).findElement(By.tagName(or.getProperty("tag"))).click();
			sleep(1);
			  
			//Checking for submit button in forgot password link
			if(driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).getAttribute("value").equals("Submit"))
				Reporter.log("<p>" + "Submit button is present in forgot password link");
			 else
				Assert.fail("Submit Button is not present in forgot password link");
			  
		/*
		 * Getting usernames from spreadsheet 2 and trying to retreive password
		*/
	/*      int rows1 = sh1.getRows();
		  int j=0;
		  for(int i=0;i<rows1;i++){
		  driver.get(config.getProperty("url") + "/login.jsp");
		  driver.findElement(By.id(or.getProperty("errormsg_id"))).findElement(By.tagName(or.getProperty("tag"))).click();
		  driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(sh1.getCell(j, i).getContents());
		  driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).click();
		  sleep(6);
		  List<WebElement> error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label"));
			  if(error_msg.get(3).getText().isEmpty())
				  Reporter.log("<p>" + error_msg.get(2).getText());
			  else
				  Reporter.log("<p>" + error_msg.get(3).getText());
		  						  }*/
		  
	  
		  //Getting Log in credentials from spreadsheet 3 and trying to log in 
		  driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(sh2.getCell(0, 0).getContents());
		  driver.findElement(By.id(or.getProperty("passwd"))).sendKeys(sh2.getCell(1, 0).getContents());
		  driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).click();
		  Reporter.log("<p>" + "Logging with username: " +sh2.getCell(0, 0).getContents()+"," +"password: " +sh2.getCell(1, 0).getContents());
		  sleep(4);
		  Reporter.log("<p>" + "logged in as " +driver.findElement(By.className(or.getProperty("loginusername_class"))).getText());
		  if(driver.getTitle().equals("::Lead CRM:: BDM Home Page"))
			  Reporter.log("<p>" + "LOGGED IN SUCCESSFULLY");
		  else
			  Reporter.log("<p>" + "Error in log in");
  }
  
		@BeforeMethod
		/* 
		 *  Opening Firefox browser and getting the Lead CRM home page
		 */
		public void beforeMethod() {
			help.browser();
			help.maxbrowser();
			driver.get(config.getProperty("url"));
			if(driver.getTitle().equals("::LEAD-CRM::Login Here"))
				Reporter.log("<p>" + "Lead CRM URL found");
			else
				Reporter.log("<p>" + "Error in opening Lead CRM URL");
									}
		
		@AfterMethod
		public void afterMethod(){
			driver.quit();
		}
}
