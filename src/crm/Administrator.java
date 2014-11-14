package src.crm;
import src.testUtils.Helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nexiilabs.dbcon.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jxl.Cell;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Administrator extends Helper{
	@BeforeMethod
		public void bf() {
			browser();
			
			driver.get(config.getProperty("url"));
			if(driver.getTitle().equals("::LEAD-CRM::Login Here"))
			{
				Reporter.log("<p>" + "Page loaded successfully");
			}
			else
			{
				Assert.fail("Page Loading Failed");
			}
			maxbrowser();
		}
	@AfterMethod
		public void af()
		{
			driver.close();
		}
	//login method
		public void mylogin() throws Exception{
			
			//login to the Admin role by calling Login method from helper
			
			login(config.getProperty("auname"), config.getProperty("apass"));
			WebElement loggedInName = driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name")));
			if(loggedInName.getText().contains("Hi ! Admin "))
			{
				//List for validating the elements in the side tree menu for the Admin role
				
				List<WebElement> rolesList = driver.findElement(By.className(admin.getProperty("sidetreemenu"))).findElements(By.tagName(admin.getProperty("listtag")));
				if((sh6.getCell(10, 1).getContents().equals(rolesList.get(0).getText()))&&(sh6.getCell(10, 2).getContents().equals(rolesList.get(5).getText())&&(sh6.getCell(10, 3).getContents().equals(rolesList.get(10).getText()))))
				{
					Reporter.log("<p>" + "Logged in Successfully with valid Admin role");
				}
			}
			else
			{
			Assert.fail("Login failed");
			}
		}
	
		//researcher method for path modification verification
		
		public void myResearcher() throws Exception {
		
			//picking the password of the any researcher from database randomly
			
			String smail = dbReaserchPass();
			String spass = dbConnection(smail);
			
			//calling login method from helper to login into the Researcher role
			
			help.login(smail, spass );
			help.expand();
			sleep(2);
			
			//clicking on the browse button to upload a file
			
			driver.findElement(By.id("bulkUpload")).click();
			sleep(7);
			if (driver.findElement(By.className(admin.getProperty("Rtextbox"))).isDisplayed())
			{	
				//selecting a file to be uploaded
				
				driver.findElement(By.className(admin.getProperty("Rtextbox"))).sendKeys(System.getProperty("user.dir")+"\\src\\testData\\dummyTestData.xlsx");
			}
			else
			{
				Assert.fail("Browse option not available");
			}

			if (driver.findElement(By.id(admin.getProperty("leadUpload"))).isDisplayed())
			{	
				//clicking on the upload button
				
				driver.findElement(By.id(admin.getProperty("leadUpload"))).click();
				sleep(7);
			}
			else
			{
				Assert.fail("leads_upload_button not presented");
			}

			List<WebElement> resultantmessage = driver.findElements(By.id(admin.getProperty("resultant_msg")));
			if(resultantmessage.size()!=0)
			{
				String s1= "Excel File Uploaded and Leads Saved Successfully....!";
				if (resultantmessage.get(0).getText().equalsIgnoreCase(s1))
				{
					Reporter.log("<p>" + "File uploaded successfully");
				}
				else
				{
					Reporter.log("<p>" + "Uploaded file is Invalid");
				}
			}
			else
			{
				Assert.fail("The element is not loaded properly");
			}
			sleep(2);
			driver.close();
		}
	
		public String dbConnection(String string) throws Exception, IllegalAccessException, ClassNotFoundException{
		       
			//Data Base connection Statements 
			
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        connection = DBConnection.getConnection();
	        statement = connection.createStatement();
	        
	        //Query for retrieving password
	        
	        resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND crm_user.delete_status='no'");
	        resultSet.next();
	        String str = resultSet.getString("password"); 
	        return str;
		}
		public String dbConnectionRole(String string) throws Exception, IllegalAccessException, ClassNotFoundException{
		  
		 //Data Base connection Statements 
			
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         connection = DBConnection.getConnection();
         statement = connection.createStatement();
         
         //Query for retrieving Role 
         
         resultSet = statement.executeQuery("select a.role_name from crm_role a, crm_user b where a.role_id = b.role_id AND email_id ='"+string+"' AND b.delete_status = 'no'");
         resultSet.next();
         String str = resultSet.getString("role_name"); 
         return str;
		}
		
		public String dbReaserchPass() throws Exception, IllegalAccessException, ClassNotFoundException{
			
		 //Data Base connection Statements  
         
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         connection = DBConnection.getConnection();
         statement = connection.createStatement();
                  
         //Query for retrieving  researcher's password
         
         resultSet = statement.executeQuery("select   b.email_id from crm_role a, crm_user b where a.role_id = b.role_id AND a.role_id = 6 AND b.delete_status='no' Group by a.role_name");
         resultSet.next();
         String str = resultSet.getString("email_id"); 
         return str;
		}
		
		////@Test
		
		//A small test method for retrieval password and roles etc.....it is just used while writing the code
		
	    public void test() throws IllegalAccessException, ClassNotFoundException, Exception{
			 String s= dbConnection("sreekar.jakkula@nexiilabs.com");
			 Reporter.log("<p>" + s);
			//  String s1 =  dbConnectionRole();
			//  Reporter.log("<p>" + s1);
			 String se = dbReaserchPass();
			 Reporter.log("<p>" +se);
			 
		  }
	    //=============Start of Non-Functional Test methods============================		  
	 
	   //@Test
	   
	   public void a_searchUsersOfUpdateUser() throws Exception{
		   
		  //====== Searching for a specific user ========
		   
		   mylogin();
		   expand();
		   
		   //clicking on update user link from the side tree menu
		   
		   WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		   if(wup.isDisplayed())
		   {
			   wup.click();
			   sleep(2);
		   }
		   else
		   {
			   Assert.fail("update button not present");
		   }
		   //searching for the specific user in update user page by calling the user defined method
		   
		    searchtable();
		    
		    Reporter.log("<p>" + " =====================f_searchUsers() success=================");
	   }   
	 
	   //@Test
	   
	   public void b_sortingOfUpdateUser () throws Exception{
		   
		  mylogin();
		  expand();
		  
		  //clicking on the update link from the side tree menu
		  
		  WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		  
		  if(wup.isDisplayed())
		  {
			  wup.click();
		      sleep(2);
		  }
		  else
		  {
			  Assert.fail("update button not present");
		  }
		  
		  //calling sorting method from helper
		  
		  sorting();
		
	   }
	 
	  //@Test
	    
	  public void c_pageNavigationOfUpdateUser() throws Exception{
	    	
          //==========calling helper methods ======== 
	    	
		    mylogin();
		    expand();
		    
		 //clicking on the update link from the side tree menu
		    
		 WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		 if(wup.isDisplayed())
		 {
			 wup.click();
		     sleep(2);
		 }
		 else
		 {
			 Assert.fail("update button not present");
		 }
		 //calling for the 'pagination' user defined method
		 
		 pageEntries();
         Reporter.log("<p>" + "=============h_pageNavigation() success==============");
	    
	   }
  
	 //@Test
	   
	 public void d_searchbuttonOfDeleteUser() throws Exception{
		
		//calling helper methods
		   
		mylogin();
		expand();
		
		//clicking on the delete link from the side tree menu
		
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		
		if(wd.isDisplayed())
		{
			wd.click();
			sleep(2);
		}
		else
		{
			Assert.fail("delete user button not present in tree menu");
		}
		
		//calling the search user method for searching a specific user 
		
		searchtable();
		Reporter.log("<p>" + "======== k_serachbutton() success===========");
	  }

     //@Test
	 public void e_sortOfDeleteUser() throws Exception{
    	 //calling helper methods
    	 
		mylogin();
		expand();
		
		//clicking on the delete user link from the side tree menu
		
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed())
		{
			wd.click();
			sleep(2);
		}
		else
		{
			Assert.fail("delete user button not present in tree menu");
		}
		
		//calling sorting method from helper
		
		sorting();
	}
	
	//@Test
	public void f_pagenavigationOfDeleteUser() throws Exception{
		
		//calling helper methods for login and expansion of side tree
		
		mylogin();
		expand();
		
		//clicking on the delete user link from the side tree menu
		
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed())
		{
			wd.click();
			sleep(2);
		}
		else
		{
			Assert.fail("delete user button not present in tree menu");
		}
		
		//calling the paginations user defined method
		
		pageEntries();
	}

	//@Test
	
	public void g_searchUserOfViewAllUsers() throws Exception{
		//calling helper methods for login and expansion of side tree
		
		mylogin();
		expand();
		
		//clicking on the 'ViewAllUsers' link from the side tree menu
		
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed())
		{
			wv.click();
			sleep(2);
		}
		else
		{
			Assert.fail("view all users button is not present");
		}
		
		//calling the helper method for search users
		
		searchtable();
	}	
	
	//@Test 
	
	public void g_sortedUserOfViewAllUsers() throws Exception{
		
		//calling helper methods for login 
		
		mylogin();
		expand();
		
		//clicking on 'ViewAllUsers' link from the side tree menu
		
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed())
		{
			wv.click();
			sleep(2);
		}
		else
		{
			Assert.fail("view all users button is not present");
		}
		
		//calling sorting method from helper
		
		sorting();
		
	  } 
	
	  //@Test
	   public void h_pageNavigationOfViewAllUsers() throws Exception{
		
			//calling helper methods for login and expansion of side tree menu
		
			mylogin();
			expand();
			
			//clicking on the 'VewAllUsers' link from the side tree menu
			
			WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
			
			if(wv.isDisplayed())
			{
				wv.click();
				sleep(2);
			}
			else
			{
				Assert.fail("view all users button is not present");
			}
			
			//calling the helper method for pagination
			
			pageEntries();
		}
	  
//================End Of Non-Functional Test Methods=======================================	
		  
//===============Functional Test Methods ==================================================
	  
	   //@Test
    public void i_LC_TS_5_sideTreeExpansionAndCollapse()throws Exception  {
    	
	   //======= Login verification =========
	 		mylogin();
	 		
	   //==========Side Tree Expansion==========
	 	expand();
	 		 	 
	  //======Clicking on Create button=======
	 	
	  help.collapse();
	  
	  Reporter.log("<p>" + "Side Tree Menu expansion and collpase checked Successfully");
    }
 
 
     //@Test
    public void j_LC_TS_6_1_dropDownVerification() throws Exception{
    	
	  //======Verification of 'Manager' Drop down button presence and its working=====
    	
	 	 mylogin();
	 	 expand();
	 	 
	 	 //clicking on the create link of users from the side tree menu
	 	 
	 	 WebElement createButton = driver.findElement(By.id(admin.getProperty("create_button")));
	 	 if(createButton.isDisplayed())
	 	 {
	 		createButton.click();
	 		sleep(2);
	 	 }
	 	 else
	 	 {
	 		 Assert.fail("Create button not present");
	 	 }
	 	 
	 	 //pikcing the values of the Manager drop down into list
	 	 
	     List<WebElement> ManagerDropdown = driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
	     if(ManagerDropdown.size()!=0)
	     {
	    	 Reporter.log("<p>" + "Manager drop down size   "+ManagerDropdown.size());
	    	 int count = 0;
	    	 
	    	 // clicking on the each element in the drop down using for loop
	    	 
	    	 for(int i=0;i<ManagerDropdown.size();i++)
	    	 {
	    		 ManagerDropdown.get(i).click(); 
	    		 count++;
	    	 }
	    	 
	    	 //Verifying the size of the count variable with size of the list
	    	 
	    	 if(count==ManagerDropdown.size())
	    	 {
	    		 Reporter.log("<p>" + "Manager drop down successfully choosing all values for "+count+" times");
	    	 }
	    	 else
	    	 {
	    		 Assert.fail("manager drop down missing some values");
	    	 }
	      }
	      else
	      {
	    	  Assert.fail(" manager drop down does not present");
	      }
	     
	   //======'Role' Drop down button presence and its working=====
	  
	  // picking the values of the role drop down int olist
	  List<WebElement> Role = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
	  if(Role.size()!=0)
	  {
		  Reporter.log("<p>" + "Role drop down size  "+Role.size());
	      int cnt=0;
	      
	      //clicking on the each value of the role drop down using for loop
	      
	      for(int j=0;j<Role.size();j++)
	      {
	    	 Role.get(j).click();
	    	 cnt++;
	      }	
	      
	      //verifying the counter variable with the list of the size
	      
	      if(cnt==Role.size())
	      {
	    	   Reporter.log("<p>" + "All elements are clicked in role  dropdown for "+cnt+" times");
	      }
	     else
	     {
	    	  Assert.fail("failed in choosing all the values from role drop down");
	     }
	  }
	  else
	  {
		    Assert.fail("Role drop down is not present");
	  }
	    
	   //======Service drop down presence and working =======
	  
	   //clicking on the 'Architecture' role for the verification of the 'service' drop down
	  
	  	Role.get(2).click();
	  	
	  	//picking all the values of 'service' drop down into list
	  	
		List<WebElement>serviceList = driver.findElement(By.name(admin.getProperty("service_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
		
		if(serviceList.size()!=0)
		{
			Reporter.log("<p>" + " Service dropdown size  :"+serviceList.size());
			int cont=0;
			
			//clicking on the each value of the 'service' list
			
			for(int i= 0;i<serviceList.size();i++)
			{
				serviceList.get(i).click();
				cont++;
		    }
			
			//comparing the counter variable with the list size
			
			if(cont==serviceList.size())
			{
				Reporter.log("<p>" + "All values are clicked in service drop down for " + cont+" times");
			}
			else
			{
			Assert.fail("failed in choosing all values from service drop down");
			}
		}
		else
		{
			Assert.fail("service drop down not present");
		}
	 
		Reporter.log("<p>" + "=========================================================");
    }
 
  //@Test
    
 	public void k_LC_TS_6_2_ButtonVerification() throws Exception{
 		
 		//calling helper methods for login and exapansion of side tree menu
 		
  		 mylogin();
  		 expand();
  		 
  		 //clicking on create link of users under side tree menu
  		 
  		 WebElement createButton = driver.findElement(By.id(admin.getProperty("create_button")));
	 	 if(createButton.isDisplayed())
	 	 {
	 	   	 createButton.click();
	 		 sleep(2);
	 	 }
	 	 else
	 	 {
	 		 Assert.fail("Create button not present");
	 	 }
  		
 		
	 	//loading values of the role into a list 
	 	 
  		List<WebElement> Role = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
  		if(Role.size()!=0)
  		{
  			//clicking on the 'Architecture' role from the drop down values
  			
  			Role.get(2).click();
  			
  			//======checking of '+' button 
  			WebElement serviceButton = driver.findElement(By.id(admin.getProperty("service_button")));
  			if(serviceButton.isDisplayed())
  			{
  				serviceButton.click();  
  				sleep(2);
  			}
  			else
  			{
  				Reporter.log("<p>" + "service button not present");
  			}
  			
  			//verification of page navigation to the 'create service' page
  			
  			WebElement w = driver.findElement(By.cssSelector(admin.getProperty("createService_popup")));
  			if(w.getText().equals("Create Service"))
  			{
  				Reporter.log("<p>" + "page navigated to createService window and tried to choose one service successfully");
  			}
  			else
  			{
  				Assert.fail("page naviagation to the create service window  is failed");
  			}
  			
  			//Entering the value into the service text box
  			
  			WebElement wt = driver.findElement(By.className(admin.getProperty("text_service")));
	    
		    wt.sendKeys("database admin");
		    sleep(1);
		     
		     //clicking on the create button in the 'create service' page
		     
		     driver.findElement(By.id(admin.getProperty("create_service"))).click(); 
		     sleep(2);
		     
		     //closing the pop up window of 'service' with the help of 'Actions'
		     Actions ac = new Actions(driver);
		     ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
		     driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
		    
		     Reporter.log("<p>" + "c_ButtonVerification() success");
  		}
  		else
  		{
  		  	Assert.fail("role drop down not present");
  		}
 
 	}
 	
 	 	//@Test
	 public void l_LC_TS_6_3_newUserCreation() throws Exception{
	 
		 //Calling helper methods for login and tree expansion
		 
		 mylogin();
		 expand();
		
	 	//fetching the user details from the test data 
		 
		int rows = sh6.getRows();
		int col;
		String s1 = null;
		
		for(int row=1;row<rows;row++){
			
			 //clicking on the create link under users of side tree menu
			
			 WebElement wc = driver.findElement(By.id(admin.getProperty("create_button")));
		 	 if(wc.isDisplayed())
		 	 {
		 		 wc.click();
		 		 sleep(2);
		 	 }
		 	 else
		 	 {
		 		 Assert.fail("Create button not present in the side tree menu");
		 	 }
			 col=0;
			 sleep(1);
			 
			 //checking for the values in the test data
			 
			 if(!sh6.getCell(col, row).getContents().equals("")){
					 
				 //Assigning first name 
				 WebElement wu = driver.findElement(By.name(admin.getProperty("u_fname")));
				 if(wu.isEnabled())
				 {
					 wu.sendKeys(sh6.getCell(col, row).getContents());
					 sleep(1);
				 }
				 else
				 {
					 Assert.fail("firstname text box not present in the create user page");
				 }
				 
				 //Assigning last name
				 
				 WebElement wl = driver.findElement(By.name(admin.getProperty("u_lname")));
				 if(wl.isEnabled())
				 {
					 wl.sendKeys(sh6.getCell(++col, row).getContents());
					 sleep(1);
				 }
				 else
				 {
					 Assert.fail("last name text box not present in the create user page");
				 }
				 
				 //Assigning email
				 
				 WebElement we = driver.findElement(By.name(admin.getProperty("u_email")));
				 if(we.isEnabled())
				 {
					 we.sendKeys(sh6.getCell(++col, row).getContents());
					 sleep(1);
				 }
				 else
				 {
					 Assert.fail("email text box not present in the create user page");
				 }
				 
				 //Assigning empId
				 
				 WebElement wei = driver.findElement(By.name(admin.getProperty("u_empId")));
				 if(wei.isEnabled())
				 {
					 wei.sendKeys(sh6.getCell(++col, row).getContents());
					 sleep(1);
				 }
				 else
				 {
					 Assert.fail("empId text box not present in the create user page");
				 }
				 
				 //==========choosing the manager from the drop down Randomly================
				
				 //picking all manager drop down values into a list
				 
				 List<WebElement> M = driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
				 if(M.size()!=0)
				 {
					 //randomly picking one value from the manager drop down
					 
					 int a =random(M.size());
					 
					 //if the randomly chosen value is 'select' then choosing the other value
					 
					 if (M.get(a).getText().contentEquals("--- SELECT ---"))
					 {
						 a++;  
					 }
					 M.get(a).click();
					 sleep(1);
				 }
				 else
				 {
					 Assert.fail("Manager drop down not present in the create user page");
				 }
			
				 //picking all the role drop down values into one list
				 
				 List<WebElement> R = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
				 if(R.size()!=0)
				 {
					 int b =random(R.size());
				
					 if (R.get(b).getText().contentEquals("--- SELECT ---"))
					 {
						 b++;  
					 }
					 
					 //clicking one value from the list randomly
					 
					 s1 = R.get(b).getText();
					 R.get(b).click();

					 //if the selected role is 'Architect' then we need to give service also
					 
					 if(R.get(b).getText().equals("Architect"))
					 {
					
						 //selecting the service if the role is architect as this is an additional field for architects
					
						 List<WebElement> ls= driver.findElement(By.name(admin.getProperty("service_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
					
						 if(ls.size()!=0)
						 {
							 int c = random(ls.size());
							 ls.get(c).click();
						 }
						 else
						 {
							 Assert.fail("service drop down not present in the create user page");
						 }
					 }
				 }
				 else
				 {
					 Assert.fail("Role drop down not present in the create user page");
				 }
				 
				 //finally clicking on the create button
				 
				 WebElement ws = driver.findElement(By.id(admin.getProperty("createUser_button")));
				 if(ws.isDisplayed())
				 {
					 ws.submit(); 
					 sleep(10);
				 }
				 else
				 {
					 Assert.fail("Create button not present in the create user page ");
				 }
				 
				 //checking for the resultant message of created user
				 
				 if(driver.findElement(By.id(admin.getProperty("resultant_msg"))).getText().contains("Successfully Created"))
				 {
					 String s = dbConnection(sh6.getCell(2, row).getContents());
					 sleep(1);
					 String s2=s1;
					 
					 // trying to login with the credentials of the newly created user
					 
					 //This is to specify that the architect and the manager have no logins
					 
					 if(s2.equalsIgnoreCase("Manager")||s2.equalsIgnoreCase("Architect")||s2.equalsIgnoreCase("VP Sales"))
					 {
						 //loading of the login page
						 
						 driver.get(config.getProperty("url"));
						 sleep(2);
						 
						 //login with the created user's credentials
						 
						 login(sh6.getCell(2, row).getContents(),s);
						 sleep(4);
						 
						 //picking the error message displayed in the login page
						 
						 List<WebElement> lid = driver.findElement(By.id(admin.getProperty("login_page"))).findElements(By.tagName(admin.getProperty("labelview")));
						 
						 if(lid.get(2).getText().equals("Invalid User Login"))
						 {
							 Reporter.log("<p>" + "The roles manager and Architect and VP Sales have no login access");
						 }
						 
						 //This is for the login checking of the user created with the given credentials	
					 }
					 else
					 {
						 //login to the user created...in the case the user is other than 'Architect' and 'Manger'
						 
						 driver.get(config.getProperty("url"));
						 sleep(2);
						 
						 login(sh6.getCell(2, row).getContents(),s);
						 sleep(4);
						  
						 //checking the user name displayed at right corner of the page
						 
						 if(driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText().contains(s2))
						 {
							 Reporter.log("<p>" + "user created successfully");
						 }
	    	
						 driver.findElement(By.linkText("Logout")).click();
					 }
					 sleep(2);
					 
					 //To re-login to the Admin page after checking the login of the created user 
					 
					 mylogin();
					 expand();
					 driver.findElement(By.id(admin.getProperty("create_button")));
				 }
				 else
				 {
					 Reporter.log("<p>" + "user already exist....try with other credentials  ");
				 }
				 sleep(1);
			 }
			 else
			 {
				Reporter.log("<p>" + "no data present in test data to read");
				break;
			 }
		}
		Reporter.log("<p>" + "=================d_newUserCreation() success==========================");
	 }
	
	  // @Test
	  public void m_LC_TS_7_updatingUser() throws Exception{
		  
		  // calling login methods for login and side tree expansion
		  
		 mylogin();
		 expand();
		 
		 int col;
		 int rows = sh6.getRows();
		 String smail = null;
		// String sub;
			   
	     for(int row = 1;row<rows;row++)
	     {
	    	 col=2;
		     WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
			 if(wup.isDisplayed())
			 {
				 wup.click();
				 sleep(2);
			 }
			 else
			 {
				 Assert.fail("update button not present in the side tree menu");
			 }
			 
			 //checking for the data availability in the testData
			 
			 if(!sh6.getCell(col, row).getContents().equals(""))
			 {
				 //searching for user in the search box to update the user
				 
				driver.findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(sh6.getCell(col, row).getContents());
				sleep(2);
				
				//picking the table data into a list
				
				List<WebElement> pickEmail =  driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
				
				//checking for the data availability in the table 
				
				if(pickEmail.size()>1)
				{
					 smail = pickEmail.get(2).getText();
					 
					//clicking on update button of a user
					 
					driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElement(By.tagName(admin.getProperty("Anchor_tag"))).click();
					sleep(4);
			
				
				//performing modification to the existing data
				
				//picking the manager drop down values into list
				
				List<WebElement> man=driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
				if(man.size()!=0)
				{
					// picking one value randomly from the drop down to update the user
					
					int rnm = random(man.size());
					
					if(man.get(rnm).getText().contains("--- SELECT ---"))
					{
						rnm++;
					}
					
					//clicking on the randomly chosen value
					
					man.get(rnm).click();
					sleep(1);
				 }
				 else
				 {
					 Assert.fail("manager drop down not present in the update user pop up window");
				 }
				
				//retrieving the role of the user from DB by passing Email-Id
				
				String s1 = dbConnectionRole(smail);
				
				//updating the role of existing user
				sleep(2);
				
				//Loading the Role values into list for changing the existing role
				
				List<WebElement> R = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
				if(R.size()!=0)
				{
					//choosing one role randomly from the existing roles
					int b =random(R.size());
					
					if (R.get(b).getText().contentEquals("--- SELECT ---")||R.get(b).getText().contentEquals("Architect")||R.get(b).getText().contentEquals("Manager"))
					{
						b= b+3;  
					}
					
					//clicking on the randomly chosen value
					R.get(b).click();
					
					//clicking on update button
					
					WebElement wb = driver.findElement(By.id(admin.getProperty("reupdate_button")));
					if(wb.isDisplayed())
					{
						wb.submit();
						sleep(2);
					}
					else
					{
						Assert.fail("update user button not present in the update user pop up window");
					}
		
				}
				else
				{
					Assert.fail("role drop down in update user phase not loaded successfully");
				}
				
				//closing the update pop up window
				sleep(2);
				
				driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
				sleep(3);
				
				//Retrieving the Role of the updated user from the database to verify the updation of the user
				String s2 = dbConnectionRole(smail);
			
				//Retrieving the password of the updated user from the DB to verify the updation
				
				String Rolepass = dbConnection(smail);
				
				//Login to the updated user's account with those credentials
				
				driver.get(config.getProperty("url"));
				login(smail,Rolepass);
				sleep(5);
				
				//Verifying the role name whether changed successfully or not
				sleep(2);
				 if(s2.equalsIgnoreCase("Manager")||s2.equalsIgnoreCase("Architect"))
				 { 
					 List<WebElement> lid = driver.findElement(By.id(admin.getProperty("login_page"))).findElements(By.tagName(admin.getProperty("labelview")));
				 
					 if(lid.get(2).getText().equals("Invalid User Login"))
					 {
						 Reporter.log("<p>" + "The roles manager and Architect have no login access but the user is successfully updated");
					 }
				 }
				 //This is for the login checking of the user created with the given credentials	
			 
				 else
				 {
				
					 if((driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText().contains(s2))||driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText().contains("Admin"))
					 {
						 Reporter.log("<p>" + "User updated successfully");
					 }
					 else
					 {
						 Assert.fail("user updation failed");
					 }
				 }
				
				//Logging out from the user's account
				
				driver.findElement(By.linkText("Logout")).click();
			    	
			    sleep(2);
			    
			    //To re-login to the Admin page after checking the login of the created user 
			    
			    mylogin();
			    expand();
			}
			else
			{
				Reporter.log("<p>" +" No data present in the table to update");
			}
		  }
		  else
		  {
			 break;
		  }
	   }
		  
    }		    
	
		
	 
		
	//@Test
	public void n_LC_TS_8_deletinguser() throws Exception{
	
		//calling helper methods for login and side tree expansion
		mylogin();
		expand();
		
		int col;
		
		//Reading the no.of rows from the test data
		
		int rows= sh6.getRows();
		String s = null;
		String sdelete = null;
		for(int row = 1;row<rows;row++)
		{
			col =2;
			
			//clicking on delete user button
			WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
			if(wd.isDisplayed())
			{
				wd.click();
				sleep(2);
			}
			else
			{
				Assert.fail("delete user button not present in the side tree menu");
			}
			
			//Checking for the data availability in the test data
			
			if(!sh6.getCell(col, row).getContents().equals(""))
			{
				
				//Searching for a user to be deleted 
						
				driver.findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(sh6.getCell(col, row).getContents());
				
				//picking all the table data into one list
				
				List<WebElement> li = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElement(By.tagName(admin.getProperty("tableRowtag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
				
				//checking for the availability of table data
				if(li.size()>1)
				{
					//storing the mailId of user to be deleted in a string 
					
					s = li.get(2).getText();
					
					//Retrieving the Password of the deleted user from the database
					sdelete = dbConnection(s);
					
					//clicking on the delete link
					
					List<WebElement> li1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("Anchor_tag")));
					if(li1.size()!=0)
					{
					
						//clicking on the delete button of a specific user
						
						li1.get(0).click();
						sleep(3);
					}
					else
					{
						Assert.fail("Table data not properly loaded into the list");
					}
					sleep(2);
				
				
			   //trying to login with the credentials of the deleted user
				
			   driver.get(config.getProperty("url"));
			   sleep(2); 
			   
			   //Logging in with deleted user credentials
			   
			   login(s,sdelete);
			   sleep(3);
			   
			   //picking the error message from the login page
			   
			   List<WebElement> lid = driver.findElement(By.id(admin.getProperty("login_page"))).findElements(By.tagName(admin.getProperty("labelview")));
			   if(lid.size()!=0)
			   {
				  if(lid.get(2).getText().contains("Invalid User Login"))
				  {
			  			  
					  Reporter.log("<p>" + "user deleted successfully");
				  }
				  else
				  {
					  Assert.fail("user deletion failed");
				  }
			   }
			   
			   //Re-login to the Admin page
			   
			   mylogin();
			   expand();
			}
			else
			{
				Reporter.log("<p>"+"no user present with given search key to delete");
			}
			
		}  
		else
		{
			break;
		}
			
	   }
	}
	

	//@Test
	public void o_LC_TS_9_viewUsers() throws Exception{
		//calling helper methods for login and side tree expansion
		
		mylogin();
		expand();
		
		//clicking on the 'ViewAllUser' from the side tree menu
		
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed())
		{
			wv.click();
			sleep(2);
		}
		else
		{
			Assert.fail("view all users button is not present in the side tree menu");
		}
		//Taking all the table data into one list
		
		List<WebElement> tableData = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
		
		//checking for the availability of the table data
		
		if(tableData.size()>1)
		{
			//Checking for pagination button
			
			WebElement we =driver.findElement(By.id(admin.getProperty("pagination_next")));
			if(we.isDisplayed())
			{
				int i = 10;
				
				//clicking on the pagination till the end to load the no.of users into one list
				
				while(!we.getAttribute("class").equalsIgnoreCase(admin.getProperty("nextpage_disabled")))
				{
					//clicking on the pagination next button until it is disabled
					
					we.click();
					//loading all the table data into list
					
					List<WebElement> li = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
					if(li.size()!=0)
					{
						//Counting the total number of users
						
						i= i+li.size();
						sleep(2);
					}
					else
					{
						Assert.fail("Elementsof 'View All Users' are not properly loaded into the list");
					}
				}
				
				//Displaying the total number of users
				
				Reporter.log("<p>" + i + " no.of users are viewed successfully");
			}
			else
			{
				Assert.fail("pagiantionNext_button is not present");
			}
		}
		else
		{
			Assert.fail("no users present in the table to view");
		}
	}
	
	
	//@Test
	public void p_LC_TS_10_configCreation() throws Exception{
		
		//calling helper methods for login and side tree expansion
		
		mylogin();
		expand();
		
		//picking the values of the existing paths by clicking on 'ViewConfig' link 
		
		WebElement w1 = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w1.isDisplayed())
		{
			w1.click();
			sleep(1);
		}
		else
		{
			Assert.fail("view Configuration button is not present in the side tree menu");
		}
		
		//=======Assigning paths when there is no paths exist previously========
				
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().contains("Please Provide Configuration Details Before Updation...!"))
		{
			//clicking on the 'create config' button
			
			driver.findElement(By.id("createConfiguration")).click();
			sleep(2);
			
			//template path assigning
			
			WebElement wt1 = driver.findElement(By.name(admin.getProperty("templatePath")));
			wt1.sendKeys(sh6.getCell(6, 1).getContents());
			sleep(1);
			
			//proposal path assigning
			
			WebElement wp1 = driver.findElement(By.name(admin.getProperty("proposalPath")));
			wp1.sendKeys(sh6.getCell(6, 2).getContents());
			sleep(1);
			
			//Assigning quote path
			
			WebElement wq1 = driver.findElement(By.name(admin.getProperty("quotePath")));
			wq1.sendKeys(sh6.getCell(6, 3).getContents());
			sleep(1);
			
			//Assigning profileImagePath
			
			WebElement wpi1 = driver.findElement(By.name(admin.getProperty("profileImagePath")));
			wpi1.sendKeys(sh6.getCell(6, 4).getContents());
			sleep(1);
			
			//clicking on create button of configuration
			
			WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
			if(wr.isDisplayed())
			{
				wr.click();
				sleep(2);
			}
			else
			{
				Assert.fail("create button not present in the side tree menu");
			}
			
			WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
			//picking the resultant message for validation
			if(w.isDisplayed())
			{
				if(w.getText().contains("Configurations Successfully Inserted ...!"))
				{
					sleep(2);
					
					//calling Researcher method to verify file upload status after changing the path
					
					myResearcher();
					
					Reporter.log("<p>" + " creation of the configuration successfully done  when no configuration exist previously");
				}
				else
				{
					Reporter.log("<p>" + "configuration already exists");
				}
			}
			else
			{
				Assert.fail("No resultant message is displayed in the create config page ");
			}
		}
		else
		{
			//Assigning new paths in case of previous paths existence
			
			ArrayList<String> as = new ArrayList<String>();
			//picking the existing paths into one list
			
			List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
			for(int i = 0;i<lip.size();i++)
			{
				if(i%2!=0)
				{
					String s = lip.get(i).getText();
					as.add(s);
				}
			}
			
			//deleting existing paths in order to create new paths as we cannot create new paths in the existence of the old paths
			sleep(2);
			
			driver.findElement(By.id(admin.getProperty("deleteConfig"))).click();
			sleep(2);
			
			driver.findElement(By.id(admin.getProperty("createUser_button"))).click();
			sleep(2);
			
			//clicking on createConfig link from the side tree menu
			
			driver.findElement(By.id("createConfiguration")).click();
			sleep(2);
			
			//Assigning the template path by reading the data from the test data
			
			WebElement wt = driver.findElement(By.name(admin.getProperty("templatePath")));
			if(!as.get(0).equals(sh6.getCell(6, 1).getContents())){
		
				wt.sendKeys(sh6.getCell(6, 1).getContents());
			
			}
			else
			{
				wt.sendKeys(sh6.getCell(8, 1).getContents());
			}
			sleep(1);
		
			//proposal path uploading from the test data
			
			WebElement wp = driver.findElement(By.name(admin.getProperty("proposalPath")));
			if(!as.get(1).equals(sh6.getCell(6, 2).getContents())){
				wp.sendKeys(sh6.getCell(6, 2).getContents());
			}
			else
			{
				wp.sendKeys(sh6.getCell(8, 2).getContents());
			}
			sleep(1);
			
		
			//quote path upload	
			WebElement wq = driver.findElement(By.name(admin.getProperty("quotePath")));
			if(!as.get(2).equals(sh6.getCell(6, 3).getContents())){
				wq.sendKeys(sh6.getCell(6, 3).getContents());
			}
			else
			{
				wq.sendKeys(sh6.getCell(8, 3).getContents());
			}
			sleep(1);
		
			//profile image path upload
			
			WebElement wpi = driver.findElement(By.name(admin.getProperty("profileImagePath")));
			if(!as.get(3).equals(sh6.getCell(6, 4).getContents())){
				wpi.sendKeys(sh6.getCell(6, 4).getContents());
			}
			else
			{
				wpi.sendKeys(sh6.getCell(8, 4).getContents());
			}
			sleep(1);
			
			//clicking on the create button 
			
			WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
			if(wr.isDisplayed())
			{
				wr.click();
				sleep(2);
			}
			else
			{
				Assert.fail("create button not present in the side tree menu");
			}
			
			//verifying with the resultant message after creating paths
			
			WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
			
			if(w.isDisplayed())
			{
				if(w.getText().contains("Configurations Successfully Inserted ...!"))
				{
					sleep(2);
					
					//calling researcher method for verification of the created configuration
					driver.findElement(By.linkText("Logout")).click();
					sleep(2);
					myResearcher();
				
					Reporter.log("<p>" + "creation of the configuration successfully done by arasing the existing paths");
				}
				else
				{
					Reporter.log("<p>" + "configuration already exists");
				}
			}
			else
			{
				Assert.fail("no resultant message is displayed in the create config page");
			}
		 }
		
	  }
	
//@Test
	public void q_LC_TS_11_updateConfig() throws Exception{
		//calling helper methods for login and side tree expansion
		
		mylogin();
		expand();
		
		//taking the values of the existing paths
		
		WebElement w1 = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w1.isDisplayed())
		{
			w1.click();
			sleep(1);
		}
		else
		{
			Assert.fail("view Configuration button is not present");
		}
		
		//checking whether there is any existing paths or not by reading the error message 
		
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!"))
		{
			Reporter.log("<p>" +"No configuration exists previously to update");
		}
		else
		{
			
		//taking the paths into one list if they exist previously
		//	
		ArrayList<String> as = new ArrayList<String>();
		
		List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
		for(int i = 0;i<lip.size();i++)
		{
			if(i%2!=0)
			{
				//Adding the existing paths into one array list
				
				String s = lip.get(i).getText();
				as.add(s);
			}
		}
		//comparing with the old paths with the paths supposed to assigned and modifying with them
		
		//clicking on updateConfig button
		
		driver.findElement(By.id(admin.getProperty("updateConfig"))).click();
		sleep(2);
		
		//template path modification
		
		WebElement wt = driver.findElement(By.name(admin.getProperty("templatePath")));
		if(!as.get(0).equals(sh6.getCell(6, 1).getContents()))
		{
			wt.clear();
			sleep(1);
			wt.sendKeys(sh6.getCell(6, 1).getContents());
			
		}else{
			wt.clear();
			sleep(1);
			wt.sendKeys(sh6.getCell(8, 1).getContents());
		}
		sleep(1);
		
		//proposal path modification
		
		WebElement wp = driver.findElement(By.name(admin.getProperty("proposalPath")));
		if(!as.get(1).equals(sh6.getCell(6, 2).getContents()))
		{
			wp.clear();
			sleep(1);
			wp.sendKeys(sh6.getCell(6, 2).getContents());
			
		}
		else
		{
			wp.clear();
			sleep(1);
			wp.sendKeys(sh6.getCell(8, 2).getContents());
		}
		sleep(1);
		
		//quote path modification	
		
		WebElement wq = driver.findElement(By.name(admin.getProperty("quotePath")));
		if(!as.get(2).equals(sh6.getCell(6, 3).getContents()))
		{
			wq.clear();
			sleep(1);
			wq.sendKeys(sh6.getCell(6, 3).getContents());
		}
		else
		{
			wq.clear();
			sleep(1);
			wq.sendKeys(sh6.getCell(8, 3).getContents());
		}
		sleep(1);
		
		//profile image path modification
		
		WebElement wpi = driver.findElement(By.name(admin.getProperty("profileImagePath")));
		
		if(!as.get(3).equals(sh6.getCell(6, 4).getContents()))
		{
			wpi.clear();
			sleep(1);
			wpi.sendKeys(sh6.getCell(6, 4).getContents());
		}else{
			wpi.clear();
			sleep(1);
			wpi.sendKeys(sh6.getCell(8, 4).getContents());
		}
		sleep(1);
		WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
		if(wr.isDisplayed())
		{
			wr.click();
			sleep(2);
		}else{
			Assert.fail("create button not present");
		}
		WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
		if(w.isDisplayed())
		{
			if(w.getText().contains("Configurations Successfully Updated ...!"))
			{
				sleep(2);
				//calling researcher method for path verification
				
				 driver.findElement(By.linkText("Logout")).click();
				
				myResearcher();
				
				Reporter.log("<p>" + "configuration updated successfully");
			}
			else
			{
				Reporter.log("<p>" + "configuration updation failed");
			}
		}
		else
		{
			Assert.fail("No resultant message is displayed");
		}
	  }
	}


	//@Test
	public void r_LC_TS_13_viewConfig() throws Exception{
		//calling helper methods for login and side tree expansion
		
		mylogin();
		expand();
		
		//clicking on the 'view config' link in the side tree menu
		
		WebElement w = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w.isDisplayed())
		{
			w.click();
			sleep(1);
		}
		else
		{
			Assert.fail("view Configuration button is not present");
		}
		
		//checking whether any paths exist previously or not to view by reading the error message
		
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!"))
		{
			Reporter.log("<p>" +"There is no configuration exists previously to view");
		}
		else
		{
			//picking the existing paths into list
			
			List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
			if(lip.size()!=0)
			{
				int col = 6;
				int row = 1;
				
				for(int i = 0;i<lip.size();i++)
				{
					if(i%2!=0)
					{
						Reporter.log("<p>" + lip.get(i).getText());
					
						//comparing the existing paths with the server paths in the test to verify whether the existing paths are server paths or not
						
						if(lip.get(i).getText().equalsIgnoreCase(sh6.getCell(col, row).getContents()))
						{
							Reporter.log("<p>" + "The existing path is server path");
						
						}
						else
						{
							Reporter.log("<p>" +"The existing paths are not the server paths, Any uploads can't be performed");
						}
						row++;
					}
				}
			}
		}
		
	}
	//@Test
	public void s_LC_TS_12_deleteConfig() throws Exception{
		
		//calling helper methods for login and side tree expansion
		mylogin();
		expand();
		//clicking on the delete link from side tree menu
		
		WebElement w = driver.findElement(By.id(admin.getProperty("deleteConfig")));
		if(w.isDisplayed())
		{
			w.click();
			sleep(2);
		}
		else
		{
			Assert.fail("delete configuration button not present");
		}
		
		//checking for the paths whether they are present or not for deletion by reading error message
		
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!"))
		{
			Reporter.log("<p>" +"There is no configuration exists previously to delete");
		}
		else
		{
			//clicking on the delete button
			
			WebElement w2 = driver.findElement(By.id(admin.getProperty("createUser_button")));
				if(w2.isDisplayed())
				{
					w2.click();
					sleep(2);
				}
				else
				{
					Assert.fail("delete button not present");
				}
				//reading the resultant message
				
				WebElement w1 = driver.findElement(By.id(admin.getProperty("resultant_msg")));
				if(w1.isDisplayed())
				{
					if(w1.getText().equals("Successfully Deleted...!"))
					{
						//calling researcher method for deleted path verification
						driver.findElement(By.linkText("Logout")).click();
						myResearcher();
						
						Reporter.log("<p>" + "configuration successfully deleted");
					}
					else
					{
						Assert.fail("configuration deletion failed");
					}
				}
				else
				{
					Assert.fail("resultant message is not displayed");
				}
				sleep(2);
				
				//Re-login into the Admin module to re-assign the paths
				browser();
				driver.get(config.getProperty("url"));
				help.maxbrowser();
				
				mylogin();
				expand();
				sleep(2);
				
				//recreation of paths as they are deleted previously############################################################
				
				driver.findElement(By.id("createConfiguration")).click();
				sleep(2);
					
				//template path assigning by reading from the test data
				
				//Assigning template path
				
				WebElement wt1 = driver.findElement(By.name(admin.getProperty("templatePath")));
				wt1.sendKeys(sh6.getCell(6, 1).getContents());
				sleep(1);
				
				//Assigning propsal path
				
				WebElement wp1 = driver.findElement(By.name(admin.getProperty("proposalPath")));
				wp1.sendKeys(sh6.getCell(6, 2).getContents());
				sleep(1);
				
				//Assigning quote path
				
				WebElement wq1 = driver.findElement(By.name(admin.getProperty("quotePath")));
				wq1.sendKeys(sh6.getCell(6, 3).getContents());
				sleep(1);
				
				//Assigning profile image path
				
				WebElement wpi1 = driver.findElement(By.name(admin.getProperty("profileImagePath")));
				wpi1.sendKeys(sh6.getCell(6, 4).getContents());
				sleep(1);
				
				//finally clicking on the create button
				
				WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
				if(wr.isDisplayed())
				{
					wr.click();
					sleep(2);
				}
				else
				{
					Assert.fail("create button not present");
				}
			}	
		
		}
	
	  //@Test
   	  public void t_LC_TS_14_15_changePassword() throws Exception{
		
		//calling helper methods for login and side tree expansion
		
		mylogin();
		expand();
		sleep(4);
		
		//calling the changePassword method 
		
		help.changePassword("srini.sanchana@gmail.com");
		
	}
	//==============End of Functional Test Methods ===================================
}	
	
	 
	


 

  	
    
		  
	 
  