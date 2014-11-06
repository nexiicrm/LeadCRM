package src.crm;

import java.util.List;
import java.io.FileInputStream;

import jxl.Sheet;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.nexiilabs.dbcon.DBConnection;

import src.testUtils.Helper;


public class Smoke extends Helper
{
	
 //@Test
  public void aResearcher() throws Exception 
  {
	  help.browser();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	  help.browsererror();
	  help.login("pavan.nanigans@gmail.com", "password");
	  help.expand();
	  if (driver.findElement(By.id("bulkUpload")).isDisplayed())
	  {
		  driver.findElement(By.id("bulkUpload")).click();
	  }
	  else
	  {
		  Assert.fail("Bulk upload option not available");
	  }
	  Thread.sleep(2000);
	  
	  if (driver.findElement(By.className("medium")).isDisplayed())
	  {
		  driver.findElement(By.className("medium")).sendKeys("D:\\Lead CRM\\Researcher Test Data 3.xlsx");
	  }
	  else
	  {
		  Assert.fail("Browse option not available");
	  }
	  
	  if (driver.findElement(By.id("leads_upload_button")).isDisplayed())
	  {
		  driver.findElement(By.id("leads_upload_button")).click();
		  Thread.sleep(7000);
	  }
	  else
	  {
		  Assert.fail("Upload Button not available");
	  }
	  
	  List<WebElement> lis = driver.findElements(By.id("result_msg_div"));
	 
	  String s1= "Excel File Uploaded and Leads Saved Successfully....!";
	  if (lis.get(0).getText().equalsIgnoreCase(s1))
	  {
		System.out.println("File uploaded successfully");
	  }
	  else
	  {
		  Assert.fail("Uploaded file is Invalid");
	  }
	  if (driver.findElement(By.linkText("Logout")).isDisplayed()) 
	  {
		  driver.findElement(By.linkText("Logout")).click();
	  }
	  else
	  {
		  Assert.fail("Logout not available");
	  }
	  
	  driver.close();
  }
  
 //@Test
  public void bBDM() throws Exception
  {
	  help.browser();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	  help.browsererror();
	  help.login("srinivasa.sanchana@nexiilabs.com", "password");
	  help.expand();
	  
	  driver.findElement(By.id("assignlead")).click();
	  Thread.sleep(2000);
	  
	  new Select(driver.findElement(By.name("service"))).selectByVisibleText("QA");
	  Thread.sleep(3000);
	  
	  new Select(driver.findElement(By.name("assignto"))).selectByVisibleText("Self");
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.id("selectall")).click();
	  driver.findElement(By.id("submit")).findElement(By.className("button")).click();
	  Thread.sleep(3000);
	  
	  driver.findElement(By.id("researchPhase")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("segregate")).click();
	  Thread.sleep(4000);
	    
	  new Select(driver.findElement(By.name("companyfundstatus"))).selectByVisibleText("Listed");
	  new Select(driver.findElement(By.name("companystatus"))).selectByVisibleText("Heavy Growth");
	  driver.findElement(By.name("researchcomment")).sendKeys("Company has heavy growth in QA");
	  driver.findElement(By.id("segregatebutton")).click();
	  Thread.sleep(3000);
	  driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  
	  Thread.sleep(3000);
	  driver.findElement(By.id("workPhase")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("work")).click();
	  Thread.sleep(4000);
	  new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Introductory Mail");
	  driver.findElement(By.name("followupcomment")).sendKeys("Sent Introductory mail to lead");
	  driver.findElement(By.id("nextfollowupdate")).sendKeys("2014-10-15");
	  driver.findElement(By.id("nextfollowupdate")).sendKeys(Keys.ENTER);
	  Thread.sleep(3000);
	  driver.findElement(By.id("button")).click();
	  Thread.sleep(3000);
	  driver.navigate().refresh();
	 
	  Thread.sleep(3000);
	  driver.findElement(By.id("allfollowups")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("work")).click();
	  Thread.sleep(4000);
	  new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
	  Thread.sleep(2000);
	  
	  new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
	  Thread.sleep(2000);
	  driver.findElement(By.id("fixon")).sendKeys("2014-10-16");
	  Thread.sleep(2000);
	  new Select(driver.findElement(By.name("to"))).selectByVisibleText("ajay.kovuri8@nexiilabs.com");
	  driver.findElement(By.name("subject")).sendKeys("Proposal for zzzz company");
	  driver.findElement(By.name("message")).sendKeys("Prepare proposal for company and submit");
	  driver.findElement(By.name("followupcomment")).sendKeys("Proposal comments");
	  Thread.sleep(2000);
	  driver.findElement(By.id("nextfollowupdate")).sendKeys("2014-10-16");
	  driver.findElement(By.id("nextfollowupdate")).sendKeys(Keys.ENTER);
	  Thread.sleep(3000);
	  driver.findElement(By.id("button")).click();
	  Thread.sleep(3000);
	  driver.navigate().refresh();
	  
	 
	  Thread.sleep(3000);
	  driver.findElement(By.id("proposalupload")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("upload")).click();
	  Thread.sleep(4000);
	  driver.findElement(By.name("proposalname")).sendKeys("QA Proposal");
	  driver.findElement(By.name("proposaldescription")).sendKeys("This proposal contains........");
	  driver.findElement(By.name("proposal")).sendKeys("D:\\Lead CRM\\proposal test.txt");
	  Thread.sleep(3000);
	  driver.findElement(By.id("button")).click();
	  Thread.sleep(3000);
	  driver.navigate().refresh();
	  
	  
	  Thread.sleep(3000);
	  driver.findElement(By.id("allfollowups")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("work")).click();
	  Thread.sleep(4000);
	  new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Proposal/Quote Accepted");
	 
	  driver.findElement(By.name("followupcomment")).sendKeys("proposal accepted");
	  Thread.sleep(3000);
	  driver.findElement(By.id("button")).click();
	  Thread.sleep(3000);
	  driver.navigate().refresh();
	  
	  Thread.sleep(3000);
	  driver.findElement(By.id("closedPhase")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
	  Thread.sleep(3000);
	  driver.findElement(By.className("close")).click();
	  Thread.sleep(4000);
	  new Select(driver.findElement(By.name("leadstatus"))).selectByVisibleText("Customer");
	  driver.findElement(By.name("project")).sendKeys("QA Project zzzz");
	  driver.findElement(By.name("comment")).sendKeys("QA Project zzzz started");
	  Thread.sleep(3000);
	  driver.findElement(By.id("closedphasebutton")).click();
	  Thread.sleep(3000);
	  driver.navigate().refresh();
	  driver.findElement(By.linkText("Logout")).click();
	  driver.close();
  }
  
  //@Test
   public void cManagement() throws Exception
   {
	      help.browser();
	      driver.get(config.getProperty("url"));
		  help.maxbrowser();
		  help.browsererror();
		  help.login("basanirakeshreddy000@gmail.com", "password");
		  help.expand();
		  List<WebElement> lis = driver.findElements(By.id("customersList"));
		  System.out.println(lis.size());
		 if (driver.findElement(By.id("customersList")).isDisplayed()) 
		  {
			  driver.findElement(By.id("customersList")).click();
		  }
		  else
		  {
			  Assert.fail("All Customers Link not available");
		  }
		  
		  Thread.sleep(2000);
		  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Russells Bennetta");
		  driver.findElement(By.className("analyse")).click();
		  Thread.sleep(6000);
		  driver.close();	   
   }
  
     
 }
  
  

