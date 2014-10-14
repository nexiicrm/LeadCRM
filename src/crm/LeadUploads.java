package crm;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import testUtils.Helper;

public class LeadUploads extends Helper{
		
	//===========Leads Upload=========//
  @Test
  public void f() throws Exception {
	  
	//========Login=======//
		driver.findElement(By.id("username")).sendKeys(config.getProperty("Researcherusername"));
		
		driver.findElement(By.id("password")).sendKeys(config.getProperty("Researcherpassword"));
		
		driver.findElement(By.cssSelector("p.login.button")).submit();
		//====================//
	  
	  List<WebElement> list1 = driver.findElement(By.id("tree_menu")).findElements(By.tagName("li"));
	  
		System.out.println("Number Elements in List1 : " + list1.size());
		
		System.out.println(" ");
		
		System.out.println("Elements in List1 are :");
		
		System.out.println("================================================");
		
		list1.get(0).click();
		
		//======= selecting Leads Upload========//
		
		System.out.println("Selecting Leads upload");
		
		List<WebElement> li1 = driver.findElement(By.id("tree_menu")).findElements(By.className(" symbol-close"));
		
		System.out.println(li1.size());
		
		System.out.println(li1.get(0).getText());
		
		li1.get(0).click();
		
		List<WebElement> li2 = driver.findElement(By.className("  open")).findElements(By.tagName("a"));
		
		System.out.println("Lead uploads having :"+li2.size()+":sub element");
		
		//======Clicking Lead upload sub links====//
		
		System.out.println("Clicking Leads upload SubLink");
		
		System.out.println("sub element name:"+li2.get(0).getText());
		
		li2.get(0).click();
		
		//==== color matching===//
		
		//System.out.println(driver.findElement(By.className("medium")).getCssValue("background-color"));
		
		Actions a = new Actions(driver);
		
		a.moveToElement(driver.findElement(By.className("medium"))).build().perform();
		
		//System.out.println(driver.findElement(By.className("medium")).getCssValue("background-color"));
		
		//driver.findElement(By.id("leads_upload_button")).click();
		
		//System.out.println(driver.findElement(By.className("medium")).getCssValue("background-color"));
		
		String s1 = driver.findElement(By.className("medium")).getCssValue("background-color");
		
		//System.out.println(s1);
		
		if (driver.findElement(By.className("medium")).getCssValue("background-color").equals(s1)) 
			
			{
			
		//=====uploading files===//
		
		 
		driver.findElement(By.className("medium")).sendKeys("C:\\Users\\Nexii\\Desktop\\Testresearc.xlsx");
				
		driver.findElement(By.id("leads_upload_button")).click();
		
		Thread.sleep(20000);
		
		System.out.println("test");
		
				
		if(driver.findElement(By.id("result_msg_div")).getText().startsWith("Excel File Uploaded and Leads Saved Successfully....!"))
		{
			
			System.out.println(driver.findElement(By.id("result_msg_div")).getText());
			
		} else {
			
			System.out.println("File Upload Failed Due to Can't open the specified file: '/usr/local/tomcat7/temp/poifiles/poi-ooxml-*******.tmp'");
		
		}
		
		System.out.println("tesdt");
		
		}
		
		else {
			Assert.fail("Please select a file");
		}
		
	
	
  }

  

/* 
 *  Opening Firefox browser and getting the Lead CRM home page
 */
  @BeforeMethod
public void beforeMethod() {
	help.browser();
	driver.get(config.getProperty("url"));
	 if(driver.getTitle().equals("::LEAD-CRM::Login Here")){
		 System.out.println("Lead CRM URL found");
	 }else{
		 System.out.println("Error in opening Lead CRM URL");
	 }
}

  @AfterMethod
  public void afterMethod() {
	 // driver.quit();
  }

}
