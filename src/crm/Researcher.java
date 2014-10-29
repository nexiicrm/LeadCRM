package crm;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	public void  treeSize() {
		
		    List<WebElement> list1 = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
		 
			System.out.println("Number Elements in List1 : " + list1.size());
			
			//System.out.println(" ");
			
			//System.out.println("Elements in List1 are :");
			
			//System.out.println("================================================");
		    
			String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
			 
		    System.out.println("User Logged in as:" + user);
	}
		
	//===========Leads Upload=========//
	
 @Test
  public void f() throws Exception {
	  
	//========Login=======//
	  
	  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
			
		//===tree menu size dispayng======//
		treeSize();
		
		//======= Expanding tree menu========//
		
		help.expand();
		//=========clicking bulkuplods========//
		driver.findElement(By.id(or.getProperty("uploadBulk"))).click();
		
		//==== color matching===//
		
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.className(or.getProperty("Rtextbox")))).build().perform();
		String s1 = driver.findElement(By.className(or.getProperty("Rtextbox"))).getCssValue(or.getProperty("upload_css"));
		
		if (driver.findElement(By.className(or.getProperty("Rtextbox"))).getCssValue(or.getProperty("upload_css")).equals(s1)) 
		{
		//=====uploading files===//
		
		driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("C:\\Users\\Nexii\\Desktop\\Testresearc.xlsx");
		driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		Thread.sleep(5000);
		
		if(driver.findElement(By.id("result_msg_div")).getText().startsWith("Excel File Uploaded and Leads Saved Successfully....!"))
		{
			System.out.println(driver.findElement(By.id("result_msg_div")).getText());
		} else {
			//====calling helper====//
			help.screenshot("uploadleadsfailure");
			System.out.println("File Upload Failed Due to Can't open the specified file: '/usr/local/tomcat7/temp/poifiles/poi-ooxml-*******.tmp'");
		}
				}
		
		else {
			//====calling helper====//
			help.screenshot("upload leads failure");
			Assert.fail("Please select a file");
		}
		
		
  }  
//===========My Account=========//
  
  
  @Test
  public void f1() throws Exception {
	  
	  //===Researcher is 3rdsheet in excels=====//
	  int columns = sh3.getColumns();
		int rows = sh3.getRows();
		String data;
		int col;
	//Login
		help.login(config.getProperty("Researcherusername"), config.getProperty("Researcherpassword"));
		String old = config.getProperty("Researcherpassword");
		System.out.println("old password is:" + old);
	  //====size of tree menu===//
	    treeSize();
	    //======= Expanding tree menu========//
		help.expand();
		
	  //======Clicking searchleads in leadsearch menu====//
		
		driver.findElement(By.linkText("Change Password")).click();
		help.sleep(2);
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
			
			data = sh3.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("oldPassword")).sendKeys(sh3.getCell(col,row).getContents());
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
			data = sh3.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("newPassword")).sendKeys(sh3.getCell(col,row).getContents());
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
			data = sh3.getCell(col, row).getContents();
			System.out.println(data);
			System.out.println("*************************");
			driver.findElement(By.id("confirmPassword")).sendKeys(sh3.getCell(col,row).getContents());
			System.out.println("*************************");
			String s3 = driver.findElement(By.id("confirmPassword")).getAttribute("value");
			col++;
			Thread.sleep(3000);
			driver.findElement(By.id("change")).click();
			if(s1.length()==0||s2.length()==0||s3.length()==0) {
			//====calling helper====//
			//help.screenshot("Fields_Not_Empty");
			System.out.println("Error message");
			System.out.println("All Fields Must Not be Empty");
			System.out.println("Error message");
			Thread.sleep(1000);
		}
		
		if(s1.equalsIgnoreCase(old)) {
			
			if(s1.equalsIgnoreCase(old)&&s3.equalsIgnoreCase(s2))
			{
				//====calling helper====//
				//help.screenshot("old&newpasswords_Notsame)");
				System.out.println("Error message is:");
				System.out.println("Old and new password must not be same");
				Thread.sleep(1000);
			}
		
			else if(s2.equalsIgnoreCase(s3)) {
				//====calling helper====//
				System.out.println("Error message is:");
				System.out.println("New password and confirm password not matching");
			Thread.sleep(1000);
		}
		
		} else {
			//====calling helper====//
			//help.screenshot("Check_oldpassword");
			System.out.println("Error message is:");
			System.out.println("Check old password");
		}

		//====Clicking Change button====//
		
		driver.findElement(By.id("change")).click();
		help.sleep(5);
		help.screenshot("Changepassword");
		help.sleep(2);
		driver.findElement(By.id("oldPassword")).clear();
		driver.findElement(By.id("newPassword")).clear();
		driver.findElement(By.id("confirmPassword")).clear();
		
		help.sleep(5);
		}
		
				
  }
  
  
  //======Lead search=====//
  
  @Test
  public void f3() throws Exception {
	//Login
		
			help.login(config.getProperty("Researcherusername"), config.getProperty("Researcherpassword"));
			
			String old = config.getProperty("Researcherpassword");
			
			System.out.println("old password is:" + old);
			
			
			//===printing size of tree menu====//
		    treeSize();
		  
		    //====Expanding tree menu====//
		    help.expand();
		    
		   //===clicking search leads====// 
		    driver.findElement(By.id("serachLeads123")).click();
		    
		    //====Window handlers moving to next tab===// 
		    Set<String> Windowids = driver.getWindowHandles(); 
		       Iterator<String> iter = Windowids.iterator();
		       String MainWindow = iter.next();
		       //popup window
		       String TabbedWindow = iter.next();
		       driver.switchTo().window(TabbedWindow);
		       System.out.println(driver.getTitle());
		       
		       //=======Getting name of selecting fields in required field======//
		      System.out.println("In required field we have::"+driver.findElement(By.tagName("legend")).getText());
		       help.sleep(4);
		    List<WebElement> li = driver.findElement(By.id("ui-accordion-accordion-panel-0")).findElements(By.tagName("input"));   
		    //System.out.println(li.get(0).getText());
			help.sleep(4);
			System.out.println("Requiredfield is having these many check boxes :" +li.size());
			System.out.println("All check box names are displaying one by one :");
			System.out.println(driver.findElement(By.id("fields_to_get")).getText());
			
			//===============Selecting checkboxes  randomly===========//
			
			int i = li.size();
			//System.out.println(i);
			int j = help.random(i);
			System.out.println("Randomly selected checkbox no is : "+ j);
			System.out.println(li.get(j).getText());
			li.get(j).click();
			driver.findElement(By.id("registerbutton")).click();
			help.sleep(3);
			//======displaing matching data when chicking check boxes======//
			
			List<WebElement> li5 = driver.findElement(By.id("example")).findElements(By.tagName("tr"));
			
			System.out.println("This are matching Results of check boxes: " + li5.size());
			System.out.println(driver.findElement(By.id("example")).getText());
  }

  

/* 
 *  Opening Firefox browser and getting the Lead CRM home page
 */
  @BeforeMethod
public void beforeMethod() {
	help.browser();
	help.maxbrowser();
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
	  driver.close();
	  driver.quit();
  }

}
