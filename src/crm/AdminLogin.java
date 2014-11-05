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

public class AdminLogin extends Helper{
	 public static Connection connection =null;
	    public static Statement statement;
	    public static ResultSet resultSet;
	@BeforeMethod
	public void bf() {
		 browser();
		 driver.get("http://192.168.50.32:8080/leadcrm/login.jsp");
		 if(driver.getTitle().equals("::LEAD-CRM::Login Here")){
			 Reporter.log("<p>" + "Page loaded successfully");
		 }else{
			 Assert.fail("Page Loading Failed");
		 }
		 maxbrowser();
		 
	}
	@AfterMethod
	public void af(){
		driver.close();
	}
	//login method
	public void mylogin() throws Exception{
		login(config.getProperty("auname"), config.getProperty("apass"));
		WebElement loggedInName = driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name")));
		if(loggedInName.getText().contains("Hi ! Admin ")){
			List<WebElement> rolesList = driver.findElement(By.className(admin.getProperty("sidetreemenu"))).findElements(By.tagName(admin.getProperty("listtag")));
			//Reporter.log("<p>" + li.size());
			if((sh6.getCell(10, 1).getContents().equals(rolesList.get(0).getText()))&&(sh6.getCell(10, 2).getContents().equals(rolesList.get(5).getText())&&(sh6.getCell(10, 3).getContents().equals(rolesList.get(10).getText())))){
				Reporter.log("<p>" + rolesList.get(0).getText());
				Reporter.log("<p>" + rolesList.get(5).getText());
				Reporter.log("<p>" + rolesList.get(10).getText());
				Reporter.log("<p>" + "Logged in Successfully with valid role");
			}
			
			
		}else
		{
			Assert.fail("Login failed");
		}
	}
	
	//researcher method for path modification verification
	public void myResearcher() throws Exception 
	{
	help.browser();
	driver.get(config.getProperty("url"));
	help.maxbrowser();
	help.browsererror();
	
	help.login("pavan.nanigans@gmail.com", "password" );
	help.expand();
	sleep(2);
	//if (driver.findElement(By.id(admin.getProperty("uploadBulk"))).isDisplayed())
//	{
	driver.findElement(By.id("bulkUpload")).click();
	//}
//	else
	//{
	//Assert.fail("Bulk upload option not available");
	//}
	 sleep(7);
	if (driver.findElement(By.className(admin.getProperty("Rtextbox"))).isDisplayed())
	{
		driver.findElement(By.className(admin.getProperty("Rtextbox"))).sendKeys("D:\\LEADCRM\\Researcher Test Data1.xls");
	}
	else
	{
		Assert.fail("Browse option not available");
	}

	if (driver.findElement(By.id(admin.getProperty("leadUpload"))).isDisplayed())
		
	{
		driver.findElement(By.id(admin.getProperty("leadUpload"))).click();
		sleep(7);
	}
	else
	{
		Assert.fail("leads_upload_button not presented");
	}

	List<WebElement> resultantmessage = driver.findElements(By.id(admin.getProperty("resultant_msg")));
	if(resultantmessage.size()!=0){
		String s1= "Excel File Uploaded and Leads Saved Successfully....!";
		if (resultantmessage.get(0).getText().equalsIgnoreCase(s1))
		{
			Reporter.log("<p>" + "File uploaded successfully");
		}
		else
		{
			Reporter.log("<p>" + "Uploaded file is Invalid");
		}
	}else{
		Assert.fail("The element is not loaded properly");
	}
	sleep(2);
	driver.close();
	
	}
	
	 public String dbConnection(String string) throws Exception, IllegalAccessException, ClassNotFoundException{
		  
              
	             Class.forName("com.mysql.jdbc.Driver").newInstance();
	             connection = DBConnection.getConnection();
	             statement = connection.createStatement();
	             resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'");
	             resultSet.next();
	             String str = resultSet.getString("password"); 
	           return str;
	}
	 public String dbConnectionRole() throws Exception, IllegalAccessException, ClassNotFoundException{
		  
         
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         connection = DBConnection.getConnection();
         statement = connection.createStatement();
         resultSet = statement.executeQuery("select a.role_name from crm_role a, crm_user b where a.role_id = b.role_id AND email_id = 'tulasirammail@gmail.com' AND delete_status = 'no'");
         
         resultSet.next();
         String str = resultSet.getString("role_name"); 
         return str;
}
	
	public void pagination(){
		 sleep(2);
		 //verifying for pagination to next page for all entries
		 WebElement pageNext= driver.findElement(By.id(admin.getProperty("pagination_next")));
		 if(pageNext.isDisplayed()){
			 Reporter.log("<p>" + "pagination next button is present");
		 }else
		 {
			 Assert.fail("pagination button not present");
		 }
		 //clicking on the pagination next until it is disabled
	     while(!pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
	    	   String s = driver.findElement(By.cssSelector(admin.getProperty("list_info"))).getText();
	    	   Reporter.log("<p>" + s);
	    	   pageNext.click();
	         
	          String s1 = driver.findElement(By.cssSelector(admin.getProperty("list_info"))).getText();
	          if(!s1.equalsIgnoreCase(s)){
	        	 Reporter.log("<p>" + "navigating to next page");
	          }else{
	    	     Assert.fail("page navigation failed to next page");
	         }
	         sleep(1);
	         //verification of navigation to the previous page for all entries 
	         if(pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")) {
	    	     WebElement pagePrevious =  driver.findElement(By.id(admin.getProperty("pagiantion_prev")));
	    	 if(pagePrevious.isDisplayed()){
	    		Reporter.log("<p>" + "pagination previous button present");
	    	 }else{
	    		Assert.fail("pagination previous button not present");
	      }
	    	 //clicking on the pagination to previous page until it is disabled
	        while(!pagePrevious.getAttribute("class").equalsIgnoreCase("paginate_disabled_previous")){
	        	String s2 = driver.findElement(By.cssSelector(admin.getProperty("list_info"))).getText();
	        	pagePrevious.click();
	        	String s3 = driver.findElement(By.cssSelector(admin.getProperty("list_info"))).getText();
	        	Reporter.log("<p>" + s2);
	        	if(!s2.equalsIgnoreCase(s3)){
	        		Reporter.log("<p>" + "navigated to previous page");
	        	}else{
	        		Assert.fail("page navigation failed to previous page");
	        	}
	        }
	        //To break the loop once the circle finished
		      if(!pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
		    	  break;
		      }
	        }
	      
	   }
	      }
		
	public void showEntries(){
		 //====== Verifying the Show Entries Drop down ========  
		//picking all the drop down values into one list
		   List<WebElement> entriesDropdown = driver.findElement(By.name(admin.getProperty("show_dropDown"))).findElements(By.tagName(admin.getProperty("option_tag")));
		   if(entriesDropdown.size()!=0){
			   Reporter.log("<p>" + entriesDropdown.size());
			   for(int i = 0;i<entriesDropdown.size();i++){
				   //clicking on each element in the drop down
				   entriesDropdown.get(i).click();
				   sleep(1);
				   int x = Integer.parseInt(entriesDropdown.get(i).getText());
				   //taking the number of rows displayed into one list
				   List<WebElement> tableRows =driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
				  //comparing no.of rows with the selected values in the drop down
				   if(x==(tableRows.size()))
				   {
					   Reporter.log("<p>" + "Value chosen from drop down:"+x);
					   Reporter.log("<p>" + "users displayed in list:"+tableRows.size());
				   }
				   else {
					   //checking the case if the no.of entries are less than selected value
					   if(tableRows.size()<x){
						  WebElement wl =  driver.findElement(By.id(admin.getProperty("pagination_next")));
						  if(wl.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
							  Reporter.log("<p>" + "The list of items are "+tableRows.size() +" which are less than the "+ x +" entries chosen from the drop down");
						  }else{
							  Assert.fail("problem with loading the elements");
						  }
				   }
					  
			   }
			  
			 sleep(3);}
		   }
		   else{
			   Assert.fail("drop down not present");
		   }
		   
	}
	
	public void searchUser(){
		   sleep(1);
		   WebElement searchbox =driver.findElement(By.tagName(admin.getProperty("SearchBox")));
		   if(searchbox.isEnabled()){
			   searchbox.sendKeys("Ajay"); 
			   sleep(2);
			   List<WebElement> tableData = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
			   Reporter.log("<p>" + "total matches found:"+tableData.size());
			   int count = 0;
			   for(int x =0;x<tableData.size();x++){
			  
				   if(tableData.get(x).getText().contains("Ajay")){
					   Reporter.log("<p>" + tableData.get(x).getText());
					   count++;
			   }
		  }
		  sleep(1);
		   if(count!=0){
			   Reporter.log("<p>" + "search success ");
		   }else{
			   Assert.fail("Search not working properly");
		   }
		   }
		   else{
			   Assert.fail("serach box not accepting values");
		   }
	}
	public void sortAscend(){
		 List<WebElement> tbody = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
		 if(tbody.size()!=0){
		   Reporter.log("<p>" + tbody.size());
		   ArrayList<Integer> li= new ArrayList<Integer>();
		   for(int m = 0;m<tbody.size();m++){
			   List<WebElement> trr = tbody.get(m).findElements(By.tagName(admin.getProperty("tableData_tag")));
			   Reporter.log("<p>" + trr.get(0).getText());
			   String s= trr.get(0).getText();
			   int y = Integer.parseInt(s);
			   li.add(y);
			 }
		 WebElement w = driver.findElement(By.className(admin.getProperty("SortAsc_button")));
		 if(w.isDisplayed()){
			 if(w.getAttribute("class").equals("sorting_asc")){
				 Reporter.log("<p>" + "elements in ascending order");
				 for(int j= 0;j<(li.size()-1);j++){
					 if(li.get(j)<li.get(j+1)){
						 Reporter.log("<p>" + li.get(j)+"\n");
					 }
					 else{
						 Reporter.log("<p>" + "elements are not in sorted order");
				 }
					 
			 }
		 }
		 }else{
			 Assert.fail("Sort button not working properly");
		 }
		 }else{
			 Assert.fail("Elemets not properly loaded into the list");
		 }
	}
	public void sortDescend(){
		WebElement w =  driver.findElement(By.className(admin.getProperty("SortAsc_button")));
		if(w.isDisplayed()){
			w.click();
		}
		else{
			Assert.fail("sort Ascend button not present");
		}
		 List<WebElement> tbody1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
		 if(tbody1.size()!=0){
		   Reporter.log("<p>" + tbody1.size());
		   ArrayList<Integer> li1= new ArrayList<Integer>();
		   for(int m1 = 0;m1<tbody1.size();m1++){
			   List<WebElement> trr1 = tbody1.get(m1).findElements(By.tagName(admin.getProperty("tableData_tag")));
			   if(trr1.size()!=0){
				   Reporter.log("<p>" + trr1.get(0).getText());
				   String s1= trr1.get(0).getText();
				   int y1 = Integer.parseInt(s1);
				   li1.add(y1);
			   }else{
				   Assert.fail("table data list not loaded properly");
			   }
			 }
		 WebElement w1 = driver.findElement(By.className(admin.getProperty("SortDsc_button")));
		 if(w1.isDisplayed()){
			 if(w1.getAttribute("class").equals("sorting_desc")){
				 Reporter.log("<p>" + "elements in descending  order");
				 for(int k= 0;k<(li1.size()-1);k++){
					 if(li1.get(k)>li1.get(k+1)){
						 Reporter.log("<p>" + li1.get(k)+"\n");
					 }
					 else{
						 Reporter.log("<p>" + "elements are not in sorted order");
				 }
					 
			 }
		 }else{
			 Assert.fail("Sort descend button not present");
		 }
		 }
			 else{
				 Assert.fail("list not loaded properly");
			 }
		 }
	
	}
	  @Test
	  public void test() throws IllegalAccessException, ClassNotFoundException, Exception{
		//  String s= dbConnection("santosh@gmail.com");
		//  Reporter.log("<p>" + s);
		  String s1 =  dbConnectionRole();
		  Reporter.log("<p>" + s1);
		
		 
	  }
	
// @Test
  public void a_createbutton()throws Exception  {
	   //======= Login verification =========
	 		mylogin();
	 		
	   //==========Side Tree Expansion==========
	 	expand();
	 	List<WebElement> sideTreeList=driver.findElement(By.id(admin.getProperty("Sidetree_menu"))).findElements(By.tagName(admin.getProperty("Anchor_tag")));
	 	if(sideTreeList.size()!=0){
	 		Reporter.log("<p>" + sideTreeList.size());
	 		for(int i =0;i<sideTreeList.size();i++){
	 			Reporter.log("<p>" + sideTreeList.get(i).getText());
	 		}
	 		Reporter.log("<p>" + "The tree expanded successfully");
	 	}else{
	 		Assert.fail("Elememts of side tree not loaded properly");
	 	}
	 	
	 	
	  
	  //======Clicking on Create button=======
	  
	   WebElement wc = driver.findElement(By.id(admin.getProperty("create_button")));
	   if(wc.isDisplayed()){
		   wc.click();
		   sleep(2);
		   Reporter.log("<p>" + "Navigated to create page successfully");
	   }
	   else{
		   Assert.fail("Create button not present");
	   }
	   Reporter.log("<p>" + "===========================================");
 }
 
 
 //@Test
  public void b_dropDownVerification() throws Exception{
	  //======'Manager' Drop down button presence and its working=====
	 	 mylogin();
	 	 expand();
	 	 WebElement createButton = driver.findElement(By.id(admin.getProperty("create_button")));
	 	 if(createButton.isDisplayed()){
	 		createButton.click();
	 		 sleep(2);
	 	 }else{
	 		 Assert.fail("Create button not present");
	 	 }
	     List<WebElement> ManagerDropdown = driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
	     if(ManagerDropdown.size()!=0){
	    	 Reporter.log("<p>" + "drop down size"+ManagerDropdown.size());
	    	 int count = 0;
	    	 for(int i=0;i<ManagerDropdown.size();i++){
	    		 ManagerDropdown.get(i).click(); 
	    		 count++;
	    	 }
	    if(count==ManagerDropdown.size()){
	    	Reporter.log("<p>" + "Manager drop down successfully choosing all values for "+count+" times");
	    }else{
	    	Assert.fail("manager drop down missing some values");
	    }
	     }else{
	    	 Assert.fail(" manager drop down is not presented");
	     }
	 //======'Role' Drop down button presence and its working=====
	  List<WebElement> Role = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
	  if(Role.size()!=0){
	     Reporter.log("<p>" + "drop down size"+Role.size());
	     int cnt=0;
	     for(int j=0;j<Role.size();j++){
	    	 Role.get(j).click();
	    	 cnt++;
	     }	
	     if(cnt==Role.size()){
	    	 Reporter.log("<p>" + "All elements are clicked in role  dropdown for "+cnt+" times");
	     }
	     else{
	    	 Assert.fail("failed in choosing all the values from role drop down");
	     }
	  }else{
		  Assert.fail("Role drop down is not present");
	  }
	    //======Service dropdown presence and working =======
	  	Role.get(2).click();
		List<WebElement>serviceList = driver.findElement(By.name(admin.getProperty("service_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
		if(serviceList.size()!=0){
			Reporter.log("<p>" + "dropdown size"+serviceList.size());
			int cont=0;
			for(int i= 0;i<serviceList.size();i++){
				serviceList.get(i).click();
				cont++;
		}
		if(cont==serviceList.size()){
			Reporter.log("<p>" + "All values are clicked in service drop down for " + cont+" times");
		}else{
			Assert.fail("failed in choosing all values from service drop down");
		}
		}else{
			Assert.fail("service drop down not present");
		}
	 Reporter.log("<p>" + "=========================================================");
 }
 
 
 
// 	@Test
 	public void c_ButtonVerification() throws Exception{
  		 mylogin();
  		 expand();
  		 //clicking on create button
  		 WebElement createButton = driver.findElement(By.id(admin.getProperty("create_button")));
	 	 if(createButton.isDisplayed()){
	 		createButton.click();
	 		 sleep(2);
	 	 }else{
	 		 Assert.fail("Create button not present");
	 	 }
  		
 		//======checking of '+' button working ========
  		List<WebElement> Role = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
  		if(Role.size()!=0){
  			Role.get(2).click();
  			WebElement serviceButton = driver.findElement(By.id(admin.getProperty("service_button")));
  			if(serviceButton.isDisplayed()){
  				serviceButton.click();  
  				sleep(2);
  			}else{
  				Reporter.log("<p>" + "service button not present");
  			}
  		//clicking on the  + button for providing additional service	
  		WebElement w = driver.findElement(By.cssSelector(admin.getProperty("createService_popup")));
	    Reporter.log("<p>" + w.getText());
	    if(w.getText().equals("Create Service")){
		    Reporter.log("<p>" + "page navigated to createService window successfully");
	    }else{
		    Assert.fail("page naviagation to the create service is failed");
	    }
	    WebElement wt = driver.findElement(By.className(admin.getProperty("text_service")));
	    if(wt.isEnabled()){
		    wt.sendKeys("database admin");
		    sleep(1);
	    }else{
		    Assert.fail("text box is not enabled");
	   }
	   driver.findElement(By.id(admin.getProperty("create_service"))).click(); 
	   sleep(2);
	   Actions ac = new Actions(driver);
	   ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
	   driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	   Reporter.log("<p>" + "c_ButtonVerification() success");
  		 }else{
  		  	Assert.fail("role drop down not present");
  		}
 
 	}
 	
	//@Test
	 public void d_newUserCreation() throws Exception{
	   //========= Creation of New User ======
		 mylogin();
		 expand();
		
	 	//fetching the user details from the test data 
	    int columns = sh6.getColumns();
		int rows = sh6.getRows();
		int col;
		 String s1 = null;
		//String s1;
		for(int row=1;row<rows;row++){
			 WebElement wc = driver.findElement(By.id(admin.getProperty("create_button")));
		 	 if(wc.isDisplayed()){
		 		 wc.click();
		 		 sleep(2);
		 	 }else{
		 		 Assert.fail("Create button not present");
		 	 }
			 col=0;
			 sleep(1);
			 if(!sh6.getCell(col, row).getContents().equals("")){
					 
			 //Assigning first name 
				 WebElement wu = driver.findElement(By.name(admin.getProperty("u_fname")));
			 if(wu.isEnabled()){
				 wu.sendKeys(sh6.getCell(col, row).getContents());
				 sleep(1);
			 }else
			 {
				 Assert.fail("firstname text box not present");
			 }
			 //Assigning last name
			 WebElement wl = driver.findElement(By.name(admin.getProperty("u_lname")));
			 if(wl.isEnabled()){
				 wl.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);
			 }else{
				 Assert.fail("last name text box not present");
			 }
			 //Assigning email
			 WebElement we = driver.findElement(By.name(admin.getProperty("u_email")));
			 if(we.isEnabled()){
				 we.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);
			 }else{
				 Assert.fail("email text box not present");
			 }
			 //Assigning empId
			 WebElement wei = driver.findElement(By.name(admin.getProperty("u_empId")));
			 if(wei.isEnabled()){
				 wei.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);}
			 else{
				 Assert.fail("empId text box not present");
			 }
			 //choosing the manager from the drop down
			 List<WebElement> M = driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
			 if(M.size()!=0){
				 int a =random(M.size());
				 if (M.get(a).getText().contentEquals("--- SELECT ---")){
					 a++;  
				 }
				 M.get(a).click();
				 sleep(1);
				 Reporter.log("<p>" + M.get(a).getText());
			 }else{
				 Assert.fail("Manager drop down not present");
			 }
			
			 //choosing the role from the role drop down
			 List<WebElement> R = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
			 if(R.size()!=0){
				 int b =random(R.size());
				
				 if (R.get(b).getText().contentEquals("--- SELECT ---")){
					 b++;  
					
				 }
				 s1 = R.get(b).getText();
				 R.get(b).click();
				 Reporter.log("<p>" + R.get(b).getText());
				 if(R.get(b).getText().equals("Architect")){
					 //selecting the service if the role is architect
					 List<WebElement> ls= driver.findElement(By.name(admin.getProperty("service_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
					 if(ls.size()!=0){
						 Reporter.log("<p>" + ls.size());
						 int c = random(ls.size());
						 ls.get(c).click();
					 }
					 else{
						 Assert.fail("service drop down not present");
					 }
				 }
			 }else{
				 Assert.fail("Role drop down not present");
			 }
			 //finally clicking on the create button
	       WebElement ws = driver.findElement(By.id(admin.getProperty("createUser_button")));
	       if(ws.isDisplayed()){
	    	   ws.submit(); 
	    	   sleep(10);
	      }else{
	    	  Assert.fail("Create button not present");
	      }
	      //checking for the resultant message of created user
	      if(driver.findElement(By.id(admin.getProperty("resultant_msg"))).getText().contains("Successfully Created")){
	    	String s = dbConnection(sh6.getCell(2, row).getContents());
	    	Reporter.log("<p>" + s);
	    	sleep(1);
	    	Reporter.log("<p>" + "role selected   "+s1);
	    	String s2=s1;
	    	Reporter.log("<p>" + "role assigned   "+s2);
	    	
	    	if(s2.equalsIgnoreCase("Manager")||s2.equalsIgnoreCase("Architect"))
	    	{
	    		driver.get(config.getProperty("url"));
		    	sleep(2);
		    	login(sh6.getCell(2, row).getContents(),s);
		    	sleep(4);
		    	List<WebElement> lid = driver.findElement(By.id(admin.getProperty("login_page"))).findElements(By.tagName(admin.getProperty("labelview")));
	    		Reporter.log("<p>" + lid.get(2).getText());
		    	if(lid.get(2).getText().equals("Invalid User Login")){
	    			Reporter.log("<p>" + "The roles manager and Architect have no login access");
	    		}
	    		
	    	}else{
	    		driver.get(config.getProperty("url"));
		    	sleep(2);
		    	login(sh6.getCell(2, row).getContents(),s);
		    	sleep(4);
		    	if(driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText().contains(s2)){
		    		Reporter.log("<p>" + driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText());
		    	
	    				Reporter.log("<p>" + "user created successfully");
	    			}
	    	
	    			//driver.findElement(By.className("user_logout")).click();
	    			driver.findElement(By.linkText("Logout")).click();
	    	}
	    	sleep(2);
	    	mylogin();
	    	expand();
	    	 driver.findElement(By.id(admin.getProperty("create_button")));
	      }else
	      {
	    	  Reporter.log("<p>" + "user already exist....try with other credentials  ");
	      }
		  sleep(1);
			 }else{
				Reporter.log("<p>" + "no data present in test data to read");
				break;
			 }
		}
		Reporter.log("<p>" + "=================d_newUserCreation() success==========================");
 }
	 
	 
 //@Test
  public void e_showEntriesDropDown() throws Exception   {
		   
	   //======Clicking on Update button======= 
	 	mylogin();
	 	expand();
	    WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
	    if(wup.isDisplayed()){
	    	wup.click();
	    	sleep(2);
	    }
	    else{
	    	Assert.fail("update button not present");
	    }
	    //verifying the show entries drop down in update page by calling method
	  	showEntries();
	   Reporter.log("<p>" + "==============e_showEntriesDropDown() success=======================");
 }  
 // @Test
	 public void f_searchUsers() throws Exception{
		  //====== Searching for a specific user ======== 
		   mylogin();
		   expand();
		   //clicking on update button
		   WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		   //searching for the specific user in update user page
		  searchUser();
		   Reporter.log("<p>" + " =====================f_searchUsers() success=================");
	 }   
	 
		
//	@Test
	public void g_updatingUser() throws Exception{
		mylogin();
		expand();
		//clicking on the update button from the side tree menu
		 WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		  //searching for a specific user whose details to be uploaded  
		 WebElement ws =  driver.findElement(By.tagName(admin.getProperty("SearchBox")));
		 if(ws.isDisplayed()){
			ws.sendKeys("tulasirammail@gmail.com"); 
			sleep(2);
		}else{
			Assert.fail("Search box not present");
		}
		//clicking on update button of a user
		driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElement(By.tagName(admin.getProperty("Anchor_tag"))).click();
	   sleep(4);
	   //performing modification to the existing data
	   List<WebElement> man=driver.findElement(By.name(admin.getProperty("manager_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
	   if(man.size()!=0){
	   int rnm = random(man.size());
	   if(man.get(rnm).getText().contains("--- SELECT ---")){
		   rnm++;
	   }
	   man.get(rnm).click();
	   sleep(1);
	   }else{
		   Assert.fail("manager drop down not present ");
	   }
	   	String s1 = dbConnectionRole();
		 Reporter.log("<p>" + "role before updation   "+s1);
	   //updating the role of existing user
		 sleep(2);
	   List<WebElement> R = driver.findElement(By.name(admin.getProperty("role_dd"))).findElements(By.tagName(admin.getProperty("option_tag")));
		 if(R.size()!=0){
		 int b =random(R.size());
		 
		 if (R.get(b).getText().contentEquals("--- SELECT ---")||R.get(b).getText().contentEquals("Architect")||R.get(b).getText().contentEquals("Manager")){
			 b= b+3;  
			 Reporter.log("<p>" + "randomly picked role  "+R.get(b).getText());
		 }
		 R.get(b).click();
	   //clicking on update button
	   WebElement wb = driver.findElement(By.id(admin.getProperty("reupdate_button")));
	   if(wb.isDisplayed()){
		   wb.submit();
		   sleep(2);
	   }else{
		   Assert.fail("update user button not present");
	   }
	
	 
	   //closing the update popup window
	   sleep(2);
	   driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
	   sleep(3);
	  	   String s2 = dbConnectionRole();
	   Reporter.log("<p>" + "role after updation   "+s2);
	   String Rolepass = dbConnection("tulasirammail@gmail.com");
	   Reporter.log("<p>" + Rolepass);
	   driver.get(config.getProperty("url"));
	   login("tulasirammail@gmail.com",Rolepass);
	   sleep(5);
	   if(driver.findElement(By.className(admin.getProperty("LoggedIn_nuser_name"))).getText().contains(s2)){
		   Reporter.log("<p>" + "User updated successfully");
	   }else{
		   Assert.fail("user updation failed");
	   }
		 
		 
		 }
		Reporter.log("<p>" + "===========g_updatingUser() is successs==============");
	 }
 //  @Test
	 public void h_sorting () throws Exception{
		 mylogin();
		 expand();
		 WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		 sortAscend(); 
		 sortDescend();
	 }
	 
	
//	 @Test
	 public void i_pageNavigation() throws Exception{
     //==========Page Navigation ======== 
		 mylogin();
		 expand();
		 //clicking on the update button from the side tree menu
		 WebElement wup = driver.findElement(By.id(admin.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		    //calling for the 'pagination' user defined method
		pagination();
        Reporter.log("<p>" + "=============h_pageNavigation() success==============");
	 }
  
 
	 
//   @Test
	public void j_showentries() throws Exception{
		mylogin();
		expand();
		//clicking on delete user button from the tree menu
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("showEntries drop down not present");
		}
		//calling the show Entries user defined method
		showEntries();
		Reporter.log("<p>" + "==========j_showentries() success=============");
	}			
	//@Test
	public void k_searchbutton() throws Exception{
		mylogin();
		expand();
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//calling the search user method for searching a specific user 
		searchUser();
		   Reporter.log("<p>" + "======== k_serachbutton() success===========");
	}
	
	 
//	@Test
	public void l_deletinguser() throws Exception{
		mylogin();
		expand();
		//clicking on delete user button
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//entering a value in search box
		WebElement wi = driver.findElement(By.tagName(admin.getProperty("SearchBox")));
		if(wi.isEnabled()){
			wi.sendKeys("ajay");
			sleep(3);
		}
		else{
			Assert.fail("search text box is not enabled");
		}
		//picking all the rows into one list 
		List<WebElement> li = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElement(By.tagName(admin.getProperty("tableRowtag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
		Reporter.log("<p>" + li.size());
		if(li.size()!=0){
			//storing the mailId of user to be deleted in a string 
			String s = li.get(2).getText();
			Reporter.log("<p>" + s);
			 String sdelete = dbConnection(s);
			List<WebElement> li1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("Anchor_tag")));
			if(li1.size()!=0){
				//clicking on the delete button of a specific user
				li1.get(0).click();
				sleep(3);
			}else{
				Assert.fail("list not loaded properly");
			}
			sleep(2);
		  
		   driver.get(config.getProperty("url"));
		   sleep(2);
		   login(s,sdelete);
		   sleep(3);
		   List<WebElement> lid = driver.findElement(By.id(admin.getProperty("login_page"))).findElements(By.tagName(admin.getProperty("labelview")));
		  if(lid.size()!=0){
			  if(lid.get(2).getText().contains("Invalid User Login")){
		  
			   Reporter.log("<p>" + "user deleted successfully");
		   }else{
			   Assert.fail("user deletion failed");
		   }
		}
		}
	}  
	
//	@Test
	public void m_sort() throws Exception{
		mylogin();
		expand();
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//calling sortAscend method 
		sortAscend();
		//calling sortDescend method 
		sortDescend();
		
	}
	
//	@Test
	public void n_pagenavigation() throws Exception{
		mylogin();
		expand();
		WebElement wd = driver.findElement(By.id(admin.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//calling the paginations user defined method
		pagination();
	}
	
//	@Test
	public void o_showEntries() throws Exception{
		//=======verification of show entries dropdown ========
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		//calling the show entries method 
		showEntries();
	}  
//	@Test
	public void p_searchUser() throws Exception{
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		searchUser();
	}	
//	@Test 
	public void q_sortedUser() throws Exception{
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		sortAscend();
		sleep(1);
		sortDescend();
	}
//	@Test
	public void r_viewUsers() throws Exception{
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		WebElement we =driver.findElement(By.id(admin.getProperty("pagination_next")));
		if(we.isDisplayed()){
		int i = 10;
		while(!we.getAttribute("class").equalsIgnoreCase(admin.getProperty("nextpage_disabled"))){
			we.click();
			List<WebElement> li = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
			if(li.size()!=0){
				i= i+li.size();
				sleep(2);
			}else{
				Assert.fail("list not properly loaded");
			}
		}
		Reporter.log("<p>" + i + " no.of users are viewed successfully");
		}else{
			Assert.fail("pagiantionNext_button is not present");
		}
	}
//	@Test
	public void s_pageNavigation() throws Exception{
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(admin.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		pagination();
	}
	
//	@Test
	public void t_configCreation() throws Exception{
		mylogin();
		expand();
		//taking the values of the existing paths
		WebElement w1 = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w1.isDisplayed()){
			w1.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		int cols = 6;
		int rows = 2;
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().contains("Please Provide Configuration Details Before Updation...!")){
			
			driver.findElement(By.id("createConfiguration")).click();
			sleep(2);
			//template path assigning
			WebElement wt1 = driver.findElement(By.name(admin.getProperty("templatePath")));
			wt1.sendKeys(sh6.getCell(6, 1).getContents());
			sleep(1);
			WebElement wp1 = driver.findElement(By.name(admin.getProperty("proposalPath")));
			wp1.sendKeys(sh6.getCell(6, 2).getContents());
			sleep(1);
			WebElement wq1 = driver.findElement(By.name(admin.getProperty("quotePath")));
			wq1.sendKeys(sh6.getCell(6, 3).getContents());
			sleep(1);
			WebElement wpi1 = driver.findElement(By.name(admin.getProperty("profileImagePath")));
			wpi1.sendKeys(sh6.getCell(6, 4).getContents());
			sleep(1);
			WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
			if(wr.isDisplayed()){
				wr.click();
				sleep(2);
			}else{
				Assert.fail("create button not present");
			}
			WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
			if(w.isDisplayed()){
				if(w.getText().contains("Configurations Successfully Inserted ...!")){
					sleep(2);
					myResearcher();
					
					Reporter.log("<p>" + "configuration creation success");
				}else{
					Reporter.log("<p>" + "configuration already exists");
				}
			}else{
				Assert.fail("no resultant message is displayed");
			}
		}else{
		ArrayList<String> as = new ArrayList<String>();
		List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
		Reporter.log("<p>" + lip.size());
		for(int i = 0;i<lip.size();i++){
			if(i%2!=0){
				String s = lip.get(i).getText();
				as.add(s);
			}
		}
		//deleting existing paths
		sleep(2);
		driver.findElement(By.id(admin.getProperty("deleteConfig"))).click();
		sleep(2);
		driver.findElement(By.id(admin.getProperty("createUser_button"))).click();
		sleep(2);
		//clicking on createConfig button
		driver.findElement(By.id("createConfiguration")).click();
		sleep(2);
		//template path assigning
		WebElement wt = driver.findElement(By.name(admin.getProperty("templatePath")));
		if(!as.get(0).equals(sh6.getCell(6, 1).getContents())){
		
			wt.sendKeys(sh6.getCell(6, 1).getContents());
			
		}else{
			wt.sendKeys(sh6.getCell(8, 1).getContents());
		}
		sleep(1);
		
		//proposal path uploading 
		WebElement wp = driver.findElement(By.name(admin.getProperty("proposalPath")));
			if(!as.get(1).equals(sh6.getCell(6, 2).getContents())){
				wp.sendKeys(sh6.getCell(6, 2).getContents());
			}else{
				wp.sendKeys(sh6.getCell(8, 2).getContents());
			}
			sleep(1);
		//quote path upload	
		WebElement wq = driver.findElement(By.name(admin.getProperty("quotePath")));
		if(!as.get(2).equals(sh6.getCell(6, 3).getContents())){
			wq.sendKeys(sh6.getCell(6, 3).getContents());
		}else{
			wq.sendKeys(sh6.getCell(8, 3).getContents());
		}
		sleep(1);
		
		//profile image path upload
		WebElement wpi = driver.findElement(By.name(admin.getProperty("profileImagePath")));
		if(!as.get(3).equals(sh6.getCell(6, 4).getContents())){
			wpi.sendKeys(sh6.getCell(6, 4).getContents());
		}else{
			wpi.sendKeys(sh6.getCell(8, 4).getContents());
		}
		sleep(1);
		WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
		if(wr.isDisplayed()){
			wr.click();
			sleep(2);
		}else{
			Assert.fail("create button not present");
		}
		WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
		if(w.isDisplayed()){
			if(w.getText().contains("Configurations Successfully Inserted ...!")){
				sleep(2);
				myResearcher();
				
				Reporter.log("<p>" + "configuration creation success");
			}else{
				Reporter.log("<p>" + "configuration already exists");
			}
		}else{
			Assert.fail("no resultant message is displayed");
		}
		}
		
	}
	
//	@Test
	public void t_updateConfig() throws Exception{
		mylogin();
		expand();
		//taking the values of the existing paths
		WebElement w1 = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w1.isDisplayed()){
			w1.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!")){
			System.out.println("There is no configuration exists previously to update");
		}else{
		ArrayList<String> as = new ArrayList<String>();
		List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
		Reporter.log("<p>" + lip.size());
		for(int i = 0;i<lip.size();i++){
			if(i%2!=0){
				String s = lip.get(i).getText();
				as.add(s);
			}
		}
		
		//clicking on updateConfig button
		driver.findElement(By.id(admin.getProperty("updateConfig"))).click();
		sleep(2);
		//template path modification
		WebElement wt = driver.findElement(By.name(admin.getProperty("templatePath")));
		if(!as.get(0).equals(sh6.getCell(1, 9).getContents())){
			wt.clear();
			sleep(1);
			wt.sendKeys(sh6.getCell(1, 9).getContents());
			
		}else{
			wt.clear();
			sleep(1);
			wt.sendKeys(sh6.getCell(0, 15).getContents());
		}
		sleep(1);
		
		//proposal path modification
		WebElement wp = driver.findElement(By.name(admin.getProperty("proposalPath")));
			if(!as.get(1).equals(sh6.getCell(1, 10).getContents())){
				wp.clear();
				sleep(1);
				wp.sendKeys(sh6.getCell(1, 10).getContents());
			}else{
				wp.clear();
				sleep(1);
				wp.sendKeys(sh6.getCell(0, 15).getContents());
			}
			sleep(1);
		//quote path modification	
		WebElement wq = driver.findElement(By.name(admin.getProperty("quotePath")));
		if(!as.get(2).equals(sh6.getCell(1, 11).getContents())){
			wq.clear();
			sleep(1);
			wq.sendKeys(sh6.getCell(1, 11).getContents());
		}else{
			wq.clear();
			sleep(1);
			wq.sendKeys(sh6.getCell(0, 16).getContents());
		}
		sleep(1);
		
		//profile image path modification
		WebElement wpi = driver.findElement(By.name(admin.getProperty("profileImagePath")));
		if(!as.get(3).equals(sh6.getCell(1, 12).getContents())){
			wpi.clear();
			sleep(1);
			wpi.sendKeys(sh6.getCell(1, 12).getContents());
		}else{
			wpi.clear();
			sleep(1);
			wpi.sendKeys(sh6.getCell(0, 17).getContents());
		}
		sleep(1);
		WebElement wr = driver.findElement(By.id(admin.getProperty("createUser_button")));
		if(wr.isDisplayed()){
			wr.click();
			sleep(2);
		}else{
			Assert.fail("create button not present");
		}
		WebElement w = driver.findElement(By.id(admin.getProperty("resultant_msg")));
		if(w.isDisplayed()){
			if(w.getText().contains("Configurations Successfully Updated ...!")){
				sleep(2);
				myResearcher();
				
				Reporter.log("<p>" + "configuration updated successfully");
			}else{
				Reporter.log("<p>" + "configuration updation failed");
			}
		}else{
			Assert.fail("no resultant message is displayed");
		}
		}
	}


	//@Test
	public void v_viewConfig() throws Exception{
		mylogin();
		expand();
		WebElement w = driver.findElement(By.id(admin.getProperty("viewConfig")));
		if(w.isDisplayed()){
			w.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!")){
			System.out.println("There is no configuration exists previously to view");
		}else{
		List<WebElement> lip = driver.findElement(By.className(admin.getProperty("pathclass"))).findElements(By.tagName(admin.getProperty("labelview")));
		Reporter.log("<p>" + lip.size());
		if(lip.size()!=0){
			int col = 1;
			int row = 9;
			for(int i = 0;i<lip.size();i++){
				if(i%2!=0){
					
					Reporter.log("<p>" + row);
					Reporter.log("<p>" + sh6.getCell(col, row).getContents());
					Reporter.log("<p>" + lip.get(i).getText());
					if(lip.get(i).getText().equalsIgnoreCase(sh6.getCell(col, row).getContents())){
						Reporter.log("<p>" + "server path is verified");
						
					}
					else{
						Assert.fail("path not present");
					  }
					row++;
					}
				}
			}
		}
		
		//help.screenshot("viewconfig");
	}
//	@Test
	public void w_deleteConfig() throws Exception{
		mylogin();
		expand();
		WebElement w = driver.findElement(By.id(admin.getProperty("deleteConfig")));
		if(w.isDisplayed()){
			w.click();
			sleep(2);
		}else{
			Assert.fail("delete configuration button not present");
		}
		if(driver.findElement(By.id(admin.getProperty("resultMessage"))).getText().equals("Please Provide Configuration Details Before Updation...!")){
			System.out.println("There is no configuration exists previously to delete");
		}else{
		WebElement w2 = driver.findElement(By.id(admin.getProperty("createUser_button")));
		if(w2.isDisplayed()){
			w2.click();
			sleep(2);
		}else{
			Assert.fail("delete button is not present");
		}
		WebElement w1 = driver.findElement(By.id(admin.getProperty("resultant_msg")));
		if(w1.isDisplayed()){
			if(w1.getText().equals("Successfully Deleted...!")){
				myResearcher();
				Reporter.log("<p>" + "configuration successfully deleted");
			}
			else{
				Assert.fail("configuration deletion failed");
			}
		}else{
			Assert.fail("resultant message is not displayed");
		}
		}
	}
//	@Test
	public void serchUser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id(admin.getProperty("viewAllUsers"))).click();
		sleep(2);
		//picking the values 
		WebElement w =driver.findElement(By.id(admin.getProperty("pagination_next")));
		ArrayList<String> ar = new ArrayList<String>();
		while(!w.getAttribute("class").equalsIgnoreCase(admin.getProperty("nextpage_disabled"))){
			sleep(2);
			List<WebElement> mylist = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
			System.out.println(mylist.size());
			for(int i =0;i<mylist.size();i++){
				ar.add(mylist.get(i).getText());
				}
			w.click();
		}
				
		List<WebElement> mylist1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
		System.out.println(mylist1.size());
		for(int i1 =0;i1<mylist1.size();i1++){
			ar.add(mylist1.get(i1).getText());
		
			}
		System.out.println(ar.size());
		int j = random(ar.size());
		System.out.println(ar.get(j));
		driver.findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(ar.get(j));
		sleep(1);
		List<WebElement> tlist = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
		System.out.println(tlist.size());
		if(tlist.size()>1){
			List<WebElement> rowList = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
			System.out.println(rowList.size());
			for(int k = 0;k<rowList.size();k++){
				int count = 0;
				List<WebElement> dataList = rowList.get(k).findElements(By.tagName(admin.getProperty("tableData_tag")));
				System.out.println("dataList Size"+ dataList.size());
				for(int p = 0;p<dataList.size();p++){
					if(ar.get(j).equals(dataList.get(p).getText())){
						System.out.println(dataList.get(p).getText());
						count++;
					}
				}
				if(count!=0){
					System.out.println("search item found  for "+count+ " times");
				}else{
					System.out.println("searching failed");
				}
			}
		}else{
			System.out.println("No matching records found");
		}//finishing the search with the item in the displayed table
		
		//search with negative scenario
		int row = 1;
		int count1=0;
		for(int q = 0;q<ar.size();q++){
			count1=0;
			if(sh6.getCell(5, row).getContents().equalsIgnoreCase(ar.get(q))){
				count1++;
			}
		//	System.out.println(count1);
		}
		//System.out.println(count1);
		if(count1==0){
			driver.findElement(By.tagName(admin.getProperty("SearchBox"))).clear();
			sleep(1);
			driver.findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(sh6.getCell(12, row).getContents());
			sleep(1);
			List<WebElement> tlist1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
			System.out.println(tlist1.size());
			if(tlist1.size()==1){
				System.out.println("no resultant users are displayed with the give nsearch key");
			}else{
				System.out.println("search box is failed to show the results with the given search key");
			}
		}
	}
}
 

  	
    
		  
	 
  