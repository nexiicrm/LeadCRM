package src.crm;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
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
import org.openqa.selenium.support.ui.Select;
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
		Reporter.log("<p>" +driver.findElement(By.id("result_msg_div")).getText());
	}
		
	// @Test
	  public void f() throws Exception {
		 
		//========Login=======//
		  
		  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
				
			//===tree menu size dispaying======//
			treeSize();
			
			//======= Expanding tree menu========//
			
			help.expand();
			 sleep(2);
			 help.collapse();
		 
	 }
//===========Leads Upload invalid data=========//
	
  @Test
  public void f1() throws Exception {
	 
	// System.out.println("===========Leads Upload=========");
	 Reporter.log("<p>" +"===========Leads Upload========="); 
	//========Login to researcher module=======//
	 help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
			
	//===tree menu size dispaying======//
	 treeSize();
	//======= Expanding tree menu========//
	 help.expand();
	 sleep(2);
	//=========clicking bulkuplods========//
	 driver.findElement(By.id("bulkUpload")).click();
	//==clicking directly  leaduplodbutton==//
	// driver.findElement(By.id(or.getProperty("leadUpload"))).click();
	 sleep(3);
	//==clicking invalid  xlsx sheet==//
	 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys(System.getProperty("user.dir")+"\\src\\testData\\invalidxlsx1.xlsx");
	 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
	 waitForElement(60,By.className("error_msg"));
	 sleep(3);
	//==calling action method==//
	 action();
	 sleep(3);
	//==clicking invalid  xlsx sheet==//
	 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys(System.getProperty("user.dir")+"\\src\\testData\\invalidxlxs2.xlsx");
	 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
	 waitForElement(60,By.className("error_msg"));
	//==calling action method==//
	 action();
	 sleep(3);
	//==clicking invalid  xlsx sheet==//
	 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys(System.getProperty("user.dir")+"\\src\\testData\\invalidtextfile.txt");
	 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
	 waitForElement(60,By.className("error_msg"));
	 sleep(3);
		 //================tree collapsing===========//
		
	 Reporter.log("<p>" +"Tree collapsing"); 
		 
	 help.collapse();
		 
  }  
  
//===========Leads Upload valid data=========//
	
  @Test
  public void f2() throws Exception {
	  
	   
	 
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
		 driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys(System.getProperty("user.dir")+"\\src\\testData\\xlsxval.xlsx");
		 driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		 waitForElement(60,By.className("error_msg"));
		//==calling action method==//
		 action();
		// sleep(3);
		// sh8 = w.getSheet(8); // data sheet
		 log.debug("Loading Test data");
		 FileInputStream fp;
		 Workbook wb =null;
		  fp = new FileInputStream(System.getProperty("user.dir")+"\\src\\testData\\xlsfile.xls");
		  wb = Workbook.getWorkbook(fp);
		 Sheet sh99 = wb.getSheet(0); // Login sheet
			int rows = sh99.getRows();
			HashSet<String> a=new HashSet<String>();
			HashMap<String,String> hm=new HashMap<String,String>();
			List<String> newlist = null;
				 
			for(int row = 1;row < rows;row++)
			{
				int col=3,col1=13;
				
				//add elements to hash set
				a.add(sh99.getCell(col1,row).getContents());
				newlist=new ArrayList<String>(a);
				hm.put(sh99.getCell(col,row).getContents(),sh99.getCell(col1,row).getContents());
			}
			
			HashMap<String,String> lead= new HashMap<String,String>();
		   for(int i=0; i<newlist.size(); i++)
		   {
			   String service = newlist.get(i); 
			   ArrayList<String> keylist =  new ArrayList<String>();
			   keylist = getKeysByValue(hm, service);
				  
			   for(int j=0; j<3;j++)
			   {
				   lead.put(keylist.get(j), service);
			   }  	   
		   }
		   // logout of researcher
		   driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
  		  help.sleep(2);
		   
		   try {
            
	             Class.forName("com.mysql.jdbc.Driver").newInstance();
	             connection = DBConnection.getConnection();
	             statement = connection.createStatement();
			     resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password "
	                 + "from crm_role a, crm_user b where "
	                 + "a.role_id = b.role_id AND delete_status='no' AND role_name='BDM' Limit 2;");
	               
	               while (resultSet.next()) {
	            	   String role = resultSet.getString("role_name");
	                   String email = resultSet.getString("email_id");
	                   String password = resultSet.getString("password");
	                   // Login to BDM
	         		   help.login(email,password);
	         		   help.expand();
	         		   driver.findElement(By.id("assignlead")).click();
	         		   help.sleep(5);
	         		   for(int i=0;i<newlist.size();i++) {
	         			   
	         			   new Select(driver.findElement(By.name("service"))).selectByVisibleText(newlist.get(i));
	         			   
	         			   ArrayList<String> emailid =  getKeysByValue(lead,newlist.get(i));
	         			   for(int j=0;j<emailid.size();j++) {
	         			   driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(emailid.get(j));
	         			    help.sleep(4);
	         			    ////////////
	         			   List<WebElement> tlist1 = driver.findElement(By.tagName(researcher.getProperty("table_tag"))).findElements(By.tagName(researcher.getProperty("tableData_tag")));
	         			  //Reporter.log("<p>" +tlist1.size());
	         			   String str = null;
	         			   for(int k=0;k<tlist1.size();k++) {
	         				   str = tlist1.get(i).getText();
	         			   }
	         			  Reporter.log("<p>"+str);
	         			  //Reporter.log("<p>" +"==========when tried with negative scenario=========");
	         			  if(str.contains("No matching records found")){
	         			  Assert.fail("No matching records found");
	         			  } else{
	         			    Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).getText());
	         			    driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).clear();
	         			    help.sleep(1);
	         			   }
	         			   }   
	               } 
	         		  driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
	         		  help.sleep(2);
	        } 
		   }
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
		 
		// driver.findElement(By.linkText("Logout")).click();
		
  }  
 
  public static <T, E> ArrayList<String> getKeysByValue(HashMap<T, E> map, E value) {
    List<String> keys = new ArrayList<String>();
    for (Entry<T, E> entry : map.entrySet()) {
        if (value.equals(entry.getValue())) {
        	
            keys.add((String) entry.getKey());
        }
    }
    return (ArrayList<String>) keys;
}
  
  //==========calling search leads==========//
 // @Test
  public void f3() throws Exception {
	  
	  Reporter.log("<p>" + "=================search leads=================");
			
	  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
	  
	  Reporter.log("<p>" + "=================Expanding tree menu=================");
	  
	  help.expand();
	  
	  help.searchLead();
	  help.sleep(2);
				
	  
  }
  
  //====searchLeadPagenation=========//
  //@Test
  public void f4() throws Exception {
	  
	  Reporter.log("<p>" + "=================search leads=================");
			
	  help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
	  
	  Reporter.log("<p>" + "=================Expanding tree menu=================");
	  
	  help.expand();
	  
	  //help.searchLead();
	  
	  help.searchLeadPagination();
	  help.sleep(2);
				
	  
  }
  

  

// @Test
  public void f5() throws Exception {
	  
	 Reporter.log("<p>" +"======change password=====");
		//Login
			
				help.login(config.getProperty("Researcherusername"),config.getProperty("Researcherpassword"));
			//	String old = config.getProperty("apass");
				//System.out.println("old password is:" + old);
				
				//===printing size of tree menu====//
			    treeSize();
			    
			    //====Expanding tree menu====//
			    help.expand();
			    
			   changePassword("pavan.nanigans@gmail.com");
  }
  
 

/* 
 *  Opening Firefox browser and getting the Lead CRM home page
 */
  @BeforeMethod
  public void beforeMethod() throws Exception {
	help.browser();
	help.maxbrowser();
	driver.get(config.getProperty("url"));
	 if(driver.getTitle().equals("::LEAD-CRM::Login Here")){
		 Reporter.log("<p>"+"Lead CRM URL found");
	 }else{
		 Reporter.log("<p>"+"Error in opening Lead CRM URL");
	 }
	
}

  @AfterMethod
  public void afterMethod() {
	 // driver.quit();
	  driver.close();
	  //driver.quit();
  }

}
