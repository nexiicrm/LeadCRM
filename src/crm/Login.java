package src.crm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.nexiilabs.dbcon.DBConnection;

import src.testUtils.Helper;


public class Login extends Helper{
	    public static Connection connection =null;
	    public static Statement statement;
	    public static ResultSet resultSet,resultSet1;
	
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
			* Getting username and password from TestData.xls sheet no 0 and trying to log in
			*/
		    Reporter.log("<p>" + "Trying to login with different username and passwords from excel sheets");
		    int rows = sh0.getRows();
		    //Keeping column constant and incrementing rows and retrieving all credentials from sheet 0. 
			for(int i=0;i<rows;i++){
				int j=0;
				driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(sh0.getCell(j, i).getContents());
				j++;
				driver.findElement(By.id(or.getProperty("passwd"))).sendKeys(sh0.getCell(j, i).getContents());
				driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).submit();
				help.sleep(2);
				
				/*Checking whether title page contains login.If logged in it will
				 *get text of login module else it will get error message.
				*/
				if(!(driver.getTitle().contains("Login"))){
					Reporter.log("<p>" + "logged in as " +driver.findElement(By.className(or.getProperty("loginusername_class"))).getText());
					driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.tagName(or.getProperty("logout_tag"))).click();

				}else{
					WebElement error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label")).get(2);
					Reporter.log("<p>" + "logging with username: " +sh0.getCell(j-1, i).getContents() +", password: " +sh0.getCell(j, i).getContents() +"-->" +error_msg.getText());
				}
			}
			
			
	 		//Checking for forgot password link
	  		List<WebElement> pass_links = driver.findElement(By.id("wrapper")).findElements(By.tagName(or.getProperty("pass_link_tagname")));
	  		Reporter.log("<p>" + "FORGOT PASSWORD LINK PRESENT");
	  		Reporter.log("<p>" + "Forgot password link is = " +pass_links.get(2).findElement(By.tagName(or.getProperty("tag"))).getAttribute("href"));
	  		  
	  		//clicking forgot password link
			driver.findElement(By.id(or.getProperty("errormsg_id"))).findElement(By.tagName(or.getProperty("tag"))).click();
			help.sleep(2);
			  
			//Checking for submit button in forgot password link
			if(driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).getAttribute("value").equals("Submit"))
				Reporter.log("<p>" + "Submit button is present in forgot password link");
			 else
				Assert.fail("Submit Button is not present in forgot password link");
			  
		  /*
		   * Getting username's from TestData sheet no 1 and trying to retrieve passwords
		   */
	       int rows1 = sh1.getRows();
		   int j=0;
		   for(int i=0;i<rows1;i++)
		   {
			   driver.get(config.getProperty("url") + "/login.jsp");
			   driver.findElement(By.id(or.getProperty("errormsg_id"))).findElement(By.tagName(or.getProperty("tag"))).click();
			   driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(sh1.getCell(j, i).getContents());
			   Reporter.log("<p>" + "Entering username as " +sh1.getCell(j, i).getContents() + " in forgot password link");
			   driver.findElement(By.cssSelector(or.getProperty("login_button_css"))).findElement(By.tagName(or.getProperty("button_tagname"))).submit();
			   sleep(6);
			   
			   List<WebElement> error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label"));
			   //Getting error message if username is incorrect
			   if(error_msg.get(3).getText().isEmpty())
				  Reporter.log("<p>" + error_msg.get(2).getText());
			  else
				  Reporter.log("<p>" + error_msg.get(3).getText());
		  	}
	  
		  /*Getting Log in credentials like roles in roleslist,emails in emaillist and 
		   * passwords in passlists for all modules from database and logging in
		   */
			ArrayList<String> roleslist = new ArrayList<String>();
			ArrayList<String> emailslist = new ArrayList<String>();
			ArrayList<String> passlist = new ArrayList<String>();
	    	  try {  
	    		  
	              Class.forName("com.mysql.jdbc.Driver").newInstance();
	              connection = DBConnection.getConnection();
	              statement = connection.createStatement();
	              resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password from crm_role a, crm_user b where a.role_id = b.role_id AND b.delete_status='no' Group by a.role_name;");      
	              while (resultSet.next())
	              {
	                  String role = resultSet.getString("role_name");
	                  String email = resultSet.getString("email_id");
	                  String pass = resultSet.getString("password");
	                  if(!(role.contains("Architect") || role.contains("Manager")))
	                  {
	                	  roleslist.add(role);      
	                	  emailslist.add(email);
	                	  passlist.add(pass);
	                  }
	              }            
	           }
	    	  
	         catch (Exception e){ 
	             e.printStackTrace();
	         }
	    	  //Loop executes for all different modules and trying to log in
	    	for(int i=0;i<roleslist.size();i++)
	    	{
	    		driver.findElement(By.id(or.getProperty("user_name"))).sendKeys(emailslist.get(i));
	    		driver.findElement(By.id(or.getProperty("passwd"))).sendKeys(passlist.get(i));
	    		driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).submit();
	    		Reporter.log("<p>" + "Logging with username: " +emailslist.get(i)+"," +"password: " +passlist.get(i));
	    		if(driver.getTitle().contains(roleslist.get(i)) || driver.getTitle().contains(roleslist.get(i).substring(0,4)))
	    		{
	    			sleep(4);
	    			Reporter.log("<p>" + "logged in as " +driver.findElement(By.className(or.getProperty("loginusername_class"))).getText());
	    			//Verifying whether log in with particular module from DB is same after logging 
	    			String a = driver.findElement(By.className(or.getProperty("loginusername_class"))).getText();
	    			String a1[] = a.split(" ");
	    			if(roleslist.get(i).contains(a1[2]))
	    			{
	    				Reporter.log("<p>" + "Logged in successfully with " +roleslist.get(i) + " module");
	    				driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.tagName(or.getProperty("logout_tag"))).click();
	    				Reporter.log("<p>" + "------------------------------------------------------------------------");
	    			}
	    			else
	    				Reporter.log("<p>" + "login error with " +roleslist.get(i) + " module");
	    			sleep(4);
	    		}
	    		else
	    			Reporter.log("<p>" +driver.findElement(By.id("login")).findElements(By.tagName("label")).get(2).getText());
			    Reporter.log("<p>" + "------------------------------------------------------------------------");
	    	}
		}
		
		@BeforeMethod
		/* 
		 *  Opening browser,getting URL from config file and getting title of page
		 */
		public void beforeMethod()
		{
			help.browser();
			help.maxbrowser();
			driver.get(config.getProperty("url"));
			if(driver.getTitle().equals("::LEAD-CRM::Login Here"))
				Reporter.log("<p>" + "Lead CRM URL found");
			else
				Reporter.log("<p>" + "Error in opening Lead CRM URL");
		}
		
		@AfterMethod
		public void afterMethod()
		{
			driver.quit();
		}
}