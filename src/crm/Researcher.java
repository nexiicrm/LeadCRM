package src.crm;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.nexiilabs.dbcon.DBConnection;
import com.sun.jna.platform.win32.Netapi32Util.User;

import src.testUtils.Helper;

public class Researcher extends Helper{
	public static Connection connection = null;
	public static Statement statement;
	public static ResultSet resultSet;
	
	
	public String dbConnection(String string) throws Exception, IllegalAccessException, ClassNotFoundException{
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		 connection = DBConnection.getConnection();
		 statement = connection.createStatement();
		 System.out.println("hurray connected");
		 resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'"); 
		 resultSet.next();
		 String str = resultSet.getString("password");
		 return str;
		 }
	public void  treeSize() {
		    List<WebElement> list1 = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
		    Reporter.log("<p>" + "Number Elements in List1 : " + list1.size());
			//System.out.println("Number Elements in List1 : " + list1.size());
			String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
			if(user.contains("Hi ! Researcher"))
			Reporter.log("<p>" + "User Logged in as:" + "Hi ! Researcher");
		   // System.out.println("User Logged in as:" + user);
	}
	//==wait for element==//
	public boolean waitForElement(int timeout,By by) {
		while(timeout>0) {
			sleep(1);
		
		List<WebElement> list = driver.findElements(by);
		if(list.size()!=0) {
			return true;
		}
		timeout--;
		}
		//System.out.println("Waiting timed out element not found"+by.toString());
		Reporter.log("<p>" + "Waiting timed out element not found"+by.toString());
		return false;
		
	}
	
	//===invalid xlsx msgs for Lead upload===//
	
	public void action()
	{
		//=====uploading files===//
		//System.out.println(driver.findElement(By.id("result_msg_div")).getText());
		Reporter.log("<p>" +driver.findElement(By.id("result_msg_div")).getText());
	}
		
	//===========Leads Upload=========//
	
  @Test
  public void lead() throws Exception {
	 
	// System.out.println("===========Leads Upload=========");
	 Reporter.log("<p>" +"===========Leads Upload========="); 
	//========Login=======//
	  
	  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
			
		//===tree menu size dispaying======//
		treeSize();
		
		//======= Expanding tree menu========//
		
		help.expand();
		 sleep(2);
		//=========clicking bulkuplods========//
		 driver.findElement(By.id("bulkUpload")).click();
		 //==clicking directly  leaduplodbutton==//
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 sleep(3);
		//==clicking invalid  xlsx sheet==//
		 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("C:\\Users\\Nexii\\git\\LeadCRM\\src\\testData\\invalidxlsx1.xlsx");
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 waitForElement(60,By.className("error_msg"));
		 sleep(3);
		//==calling action method==//
		 action();
		 sleep(3);
		//==clicking invalid  xlsx sheet==//
		 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("C:\\Users\\Nexii\\git\\LeadCRM\\src\\testData\\invalidxlxs2.xlsx");
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 waitForElement(60,By.className("error_msg"));
		//==calling action method==//
		 action();
		 sleep(3);
		//==clicking invalid  xlsx sheet==//
		 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("C:\\Users\\Nexii\\git\\LeadCRM\\src\\testData\\invalidtextfile.txt");
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 waitForElement(60,By.className("error_msg"));
		 sleep(3);
		 //================tree collapsing===========//
		 System.out.println("Tree collapsing");
		 help.collapse();
		 
  }  
  
//===========Leads Upload valid data=========//
	
  @Test
  public void lead1() throws Exception {
	  
	   
	 
	  Reporter.log("<p>" +"===========Leads Upload1========="); 
	  
	//========Login=======//
	  
	  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
			
		//===tree menu size dispaying======//
		treeSize();
		
		//======= Expanding tree menu========//
		
		help.expand();
		 sleep(2);
		//=========clicking bulkuplods========//
		 driver.findElement(By.id("bulkUpload")).click();
		//==clicking valid  xlsx sheet==//
		 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("C:\\Users\\Nexii\\git\\LeadCRM\\src\\testData\\validxlxs.xlsx");
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 waitForElement(60,By.className("error_msg"));
		//==calling action method==//
		 action();
		 sleep(3);
		
  }  
 //====checking in bdm====// 
 // @Test
  public void check() throws Exception, IOException {

		 
		 //==============checking in bdm role================//
		
		 sh8 = w.getSheet(8); // data sheet
		
			int rows = sh8.getRows();
			System.out.println(rows);
			String data;
			
			for(int row = 1;row < rows;row++)
			{
				int col=3,col1=13;
				//using key-value pair
				HashMap<String,String> hm=new HashMap<String,String>();
				hm.put(sh8.getCell(col,row).getContents(),sh8.getCell(col1,row).getContents());
				
				Set<String> keys=hm.keySet();
				for(String key:keys){
					//System.out.println("value of"+key+"is:"+""+hm.get(key));
					Reporter.log("<p>" + "value of"+key+"is:"+"::"+hm.get(key));
			}
				
			
			}		
	  
  }
//===========My Account=========//
 
 @Test
  public void f1() throws Exception {
	  
	  System.out.println("===========My Account=========");
	  
	  //===Researcher is 3rdsheet in excels=====//
	  int columns = sh3.getColumns();
		int rows = sh3.getRows();
		String data;
		int col;
	//Login
		help.login(config.getProperty("Researcherusername"), config.getProperty("Researcherpassword"));
		//==placing original password in old==//
		String old = config.getProperty("Researcherpassword");
		 Reporter.log("<p>" + "old password is:" + old);
		//System.out.println("old password is:" + old);
		
	  //====size of tree menu===//
	    treeSize();
	    
	    //======= Expanding tree menu========//
		help.expand();
		
		//====getting current url====//
		String currenturl= driver.getCurrentUrl();
		
	  //======Clicking changepassword in myaccount menu====//
		driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
		help.sleep(2);
		
		//==connecting database and retriveng password==//
		 String dat = dbConnection("pavan.nanigans@gmail.com");
		// System.out.println(dat);
		 Reporter.log("<p>" + "old password is:" + dat);
		 
		//=== clicking cancle button===//
		 driver.findElement(By.id(or.getProperty("cancel1"))).sendKeys(Keys.ENTER);
		help.sleep(3);
		
		//===getting current url after cancle button====//
		String aftcancleurl = driver.getCurrentUrl();
		
		//comparing current url after cancle button//				
		if(aftcancleurl.equalsIgnoreCase(currenturl)){
			Reporter.log("<p>" +"Successfully canceled the change password page");
		} else {
			Reporter.log("<p>" +"Not successfully canceled change password page");
		}
		//======Clicking searchleads in leadsearch menu for change button====//
		driver.findElement(By.linkText(or.getProperty("changepassword"))).sendKeys(Keys.ENTER);
		help.sleep(2);
		//===  checking validations for change passwords===//
		for(int row = 1;row < rows;row++)
		{
			col=0;
			//=========checking oldpassword===//
			List<WebElement> li70 = driver.findElements(By.id(or.getProperty("oldpass")));

			if(li70.size()==0)
			{	
				help.screenshot("oldpasswordcontainer");
				Assert.fail("not a container");
			}
			else
			{
				System.out.println("Old password container is avilable");
			}
			
			data = sh3.getCell(col, row).getContents();
			Reporter.log("<p>" +data);
			//System.out.println(data);
			//System.out.println("*************************");
			Reporter.log("<p>" +"*************************");
			driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(sh3.getCell(col,row).getContents());
			Reporter.log("<p>" +"*************************");
			//==placing oldpassword in s1==//
			String s1 = driver.findElement(By.id(or.getProperty("oldpass"))).getAttribute("value");
			col++;
			Thread.sleep(3000);
			//======new password=====//
			List<WebElement> newpass = driver.findElements(By.id(or.getProperty("newpass")));

			if(newpass.size()==0)
			{	
				//====calling helper====//
				help.screenshot("newpassword container");
				Assert.fail("not a container");
			}
			else {
		
				//System.out.println("New password container is available");
				Reporter.log("<p>" +"New password container is avilable");
			}
			data = sh3.getCell(col, row).getContents();
			System.out.println(data);
			Reporter.log("<p>" +data);
			Reporter.log("<p>" +"*************************");
			driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(sh3.getCell(col,row).getContents());
			Reporter.log("<p>" +"*************************");
			//==placing new password in s2==//
			String s2 = driver.findElement(By.id(or.getProperty("newpass"))).getAttribute("value");
			col++;
			Thread.sleep(3000);
		
			List<WebElement> confirmpass = driver.findElements(By.id(or.getProperty("confirmpass")));

			if(confirmpass.size()==0)
			{
				//====calling helper====//
				help.screenshot("Configpassword container");
				Assert.fail("not a container");
			}
			else {
				System.out.println("Confirm password container is avilable");
			}
			data = sh3.getCell(col, row).getContents();
			//System.out.println(data);
			Reporter.log("<p>" +data);
			Reporter.log("<p>" +"*************************");
			driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(sh3.getCell(col,row).getContents());
			Reporter.log("<p>" +"*************************");
			//==Placing confirm password in s3==//
			String s3 = driver.findElement(By.id(or.getProperty("confirmpass"))).getAttribute("value");
			col++;
			Thread.sleep(3000);
			
			//====Clicking Change button====//
			driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
			//===This is for if my oldpassword and my currently changing password is same then handing that scenario===//
			if(s1.equalsIgnoreCase(dat)&&s2.equalsIgnoreCase(s1)&&s3.equalsIgnoreCase(s2)) {
				String str ="000";
				driver.findElement(By.id(or.getProperty("oldpass"))).clear();
				driver.findElement(By.id(or.getProperty("newpass"))).clear();
				driver.findElement(By.id(or.getProperty("confirmpass"))).clear();
				
				driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat);
				driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(str);
				driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(str);
				//====Clicking Change button====//
				driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
				help.sleep(2);
				
			}
			if(driver.getCurrentUrl().equalsIgnoreCase("http://192.168.50.32:8080/leadcrm/login.jsp")) {
				
				//==setting old password==//
				
			       String dat1 = dbConnection("pavan.nanigans@gmail.com");
			      // System.out.println("changed password"+dat1);
			       Reporter.log("<p>" +"changed password"+dat1);
			       
			     
				//Login
				driver.findElement(By.id(or.getProperty("username1"))).sendKeys(config.getProperty("Researcherusername"));
				driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat1);
				driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();
				
				//==placing change password==//
				//System.out.println("old password is:" + dat);
				 Reporter.log("<p>" +"old password is:" + dat);
				
			  //====size of tree menu===//
			    treeSize();
			    
			    //======= Expanding tree menu========//
				help.expand();
				
				//====getting current url====//
				String currenturl1= driver.getCurrentUrl();
				
			  //======Clicking changepassword in myaccount menu====//
				driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
				help.sleep(2);
				
				//oldpassword//
				driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat1);
				//String one = driver.findElement(By.id("confirmPassword")).getAttribute("value");
				
				//newpassword//
				driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(dat);
				//String two = driver.findElement(By.id("confirmPassword")).getAttribute("value");
				
				//confirm password//
				driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(dat);
				
				//String three = driver.findElement(By.id("confirmPassword")).getAttribute("value");
				driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
				
				/////////////
				 String dat2 = dbConnection("pavan.nanigans@gmail.com");
			       System.out.println("changed password"+dat2);
				//Login
				driver.findElement(By.id(or.getProperty("username1"))).sendKeys(config.getProperty("Researcherusername"));
				driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat2);
				driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();
				//==placing change password==//
				//System.out.println("old password is:" + dat2);
				 Reporter.log("<p>" +"old password is:" + dat2);
				
			  //====size of tree menu===//
			    treeSize();
			    
			    //======= Expanding tree menu========//
				help.expand();
				
				driver.findElement(By.linkText(or.getProperty("changepassword"))).sendKeys(Keys.ENTER);
				
		//System.out.println("Error message is:");
		List<WebElement> li6 = driver.findElement(By.id(or.getProperty("wrapper1"))).findElements(By.tagName("label"));
		//System.out.println(li6.get(3).getText());
		Reporter.log("<p>" +li6.get(3).getText());
		help.screenshot("Changepassword");
		help.sleep(2);
		}
			driver.findElement(By.id(or.getProperty("oldpass"))).clear();
			driver.findElement(By.id(or.getProperty("newpass"))).clear();
			driver.findElement(By.id(or.getProperty("confirmpass"))).clear();
			help.sleep(3);
		}
		}
  //======Lead search=====//
  
 // @Test
  public void f3() throws Exception {
	  
	  System.out.println("======Lead search=====");
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
			help.sleep(2);
  }
  
  
  //testing data

 @Test
  public void f4() throws Exception {
	  
	  System.out.println("======Lead search=====");
		//Login
			
				help.login(config.getProperty("auname"),config.getProperty("apass"));
				String old = config.getProperty("apass");
				System.out.println("old password is:" + old);
				
				//===printing size of tree menu====//
			    treeSize();
			    
			    //====Expanding tree menu====//
			    help.expand();
			    
			   //changePassword("srini.sanchana@gmail.com");
			   
	  
	  
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

 // @AfterMethod
  public void afterMethod() {
	 // driver.quit();
	  driver.close();
	  driver.quit();
  }

}
