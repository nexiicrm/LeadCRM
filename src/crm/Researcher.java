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

public class Researcher extends Helper{
		
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
			
			//====calling helper====//
			help.screenshot("uploadleadsfailure");
			
			System.out.println("File Upload Failed Due to Can't open the specified file: '/usr/local/tomcat7/temp/poifiles/poi-ooxml-*******.tmp'");
		
		}
		
		System.out.println("tesdt");
		
		}
		
		else {
			//====calling helper====//
			help.screenshot("upload leads failure");
			Assert.fail("Please select a file");
		}
		
	driver.close();
	driver.quit();
	
  }
  
//===========My Account=========//
  @Test
  public void f1() throws Exception {
	  
	  //===Researcher is 3rdsheet in excels=====//
	  
	  int columns = sh.getColumns();
		int rows = sh.getRows();
		String data;
		int col;
	  
	//Login
		
		help.login(config.getProperty("Researcherusername"), config.getProperty("Researcherpassword"));
		String old = config.getProperty("Researcherpassword");
		System.out.println(old);
		
		
		//======//
		
		
	  
	  List<WebElement> list1 = driver.findElement(By.id("tree_menu")).findElements(By.tagName("li"));
	  
		System.out.println("Number Elements in List1 : " + list1.size());
		
		System.out.println(" ");
		
		System.out.println("Elements in List1 are :");
		
		System.out.println("================================================");
		
		list1.get(0).click();
		
		//======= selecting MyAccount========//
		
		List<WebElement> li1 = driver.findElement(By.id("tree_menu")).findElements(By.className(" symbol-close"));
		
		System.out.println(li1.size());
		
		System.out.println(li1.get(2).getText());
		
		li1.get(2).click();
		
		List<WebElement> li2 = driver.findElement(By.className("  open")).findElements(By.tagName("a"));
		
		System.out.println("Lead uploads having :"+li2.size()+":sub element");
		
		//======Clicking Lead upload sub links====//
		
		System.out.println("sub element name:"+li2.get(0).getText());
		
		li2.get(0).click();
		
		//===  checking validations for change passwords===//
		
		for(int row = 1;row < rows;row++)
		{
			col=0;
			
			//=========checking oldpassword===//
			List<WebElement> li70 = driver.findElements(By.id("oldPassword"));

			if(li70.size()==0)
			{	
				help.screenshot("oldpasswordcontainer");
				Assert.fail("not a container");
			}
			else{
				System.out.println("Old password container is avilable");
			}
			data = sh.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("oldPassword")).sendKeys(sh.getCell(col,row).getContents());
			System.out.println("*************************");
			String s1 = driver.findElement(By.id("oldPassword")).getAttribute("value");
			col++;
			Thread.sleep(3000);
			//======new password=====//
			List<WebElement> newpass = driver.findElements(By.id("newPassword"));

			if(newpass.size()==0)
			{	
				//====calling helper====//
				help.screenshot("newpassword container");
				Assert.fail("not a container");
			}
			else{
				System.out.println("New password container is avilable");
			}
			data = sh.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("newPassword")).sendKeys(sh.getCell(col,row).getContents());
			System.out.println("*************************");
			String s2 = driver.findElement(By.id("newPassword")).getAttribute("value");
			col++;
			Thread.sleep(3000);
		
			List<WebElement> confirmpass = driver.findElements(By.id("confirmPassword"));

			if(confirmpass.size()==0)
			{
				//====calling helper====//
				help.screenshot("Configpassword container");
				Assert.fail("not a container");
			}
			else{
				System.out.println("Confirm password container is avilable");
			}
			data = sh.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("confirmPassword")).sendKeys(sh.getCell(col,row).getContents());
			System.out.println("*************************");
			String s3 = driver.findElement(By.id("confirmPassword")).getAttribute("value");
			col++;
			Thread.sleep(3000);
			
			//====Clicking Change button====//
			
			driver.findElement(By.id("change")).click();
			
								
		if(s1.length()==0||s2.length()==0||s3.length()==0) {
			//====calling helper====//
			help.screenshot("Fields_Not_Empty");
			System.out.println("Error message");
			System.out.println("All Fields Must Not be Empty");
			System.out.println("Error message");
			Thread.sleep(1000);
			
		}
		
		if(s1.equalsIgnoreCase(old)) {
			
			if(s1.equalsIgnoreCase(old)&&s3.equalsIgnoreCase(s2))
			{
				//====calling helper====//
				help.screenshot("old&newpasswords_Notsame)");
				System.out.println("Error message is:");
				System.out.println("Old and new password must not be same");
				Thread.sleep(1000);
			}
		
			else if(s2.equalsIgnoreCase(s3)) {
				//====calling helper====//
				help.screenshot("Mismatch_old&newpassword");
				
				System.out.println("Error message is:");
				System.out.println("New password and confirm password not matching");
			Thread.sleep(1000);
		}
		
		} else {
			//====calling helper====//
			help.screenshot("Check_oldpassword");
			System.out.println("Error message is:");
			System.out.println("Check old password");
		}
		
		}
		//System.out.println("successfully change ur password");
		driver.close();
		driver.quit();
		
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
