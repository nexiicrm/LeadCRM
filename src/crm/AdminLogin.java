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
			 System.out.println("Page loaded successfully");
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
		WebElement w = driver.findElement(By.className(or.getProperty("LoggedIn_nuser_name")));
		if(w.getText().contains("Hi ! Admin ")){
			List<WebElement> li = driver.findElement(By.className("menu")).findElements(By.tagName("li"));
			System.out.println(li.size());
			if((sh6.getCell(2, 15).getContents().equals(li.get(0).getText()))&&(sh6.getCell(2, 16).getContents().equals(li.get(5).getText())&&(sh6.getCell(2, 17).getContents().equals(li.get(10).getText())))){
				System.out.println(li.get(0).getText());
				System.out.println(li.get(5).getText());
				System.out.println(li.get(10).getText());
				System.out.println("Logged in Successfully with valid role");
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
	//if (driver.findElement(By.id(or.getProperty("uploadBulk"))).isDisplayed())
//	{
	driver.findElement(By.id("bulkUpload")).click();
	//}
//	else
	//{
	//Assert.fail("Bulk upload option not available");
	//}
	 sleep(7);
	if (driver.findElement(By.className(or.getProperty("Rtextbox"))).isDisplayed())
	{
		driver.findElement(By.className(or.getProperty("Rtextbox"))).sendKeys("D:\\LEADCRM\\Researcher Test Data1.xls");
	}
	else
	{
		Assert.fail("Browse option not available");
	}

	if (driver.findElement(By.id(or.getProperty("leadUpload"))).isDisplayed())
		
	{
		driver.findElement(By.id(or.getProperty("leadUpload"))).click();
		sleep(7);
	}
	else
	{
		Assert.fail("leads_upload_button not presented");
	}

	List<WebElement> lis = driver.findElements(By.id(or.getProperty("resultant_msg")));
	if(lis.size()!=0){
		String s1= "Excel File Uploaded and Leads Saved Successfully....!";
		if (lis.get(0).getText().equalsIgnoreCase(s1))
		{
			System.out.println("File uploaded successfully");
		}
		else
		{
			System.out.println(("Uploaded file is Invalid"));
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
	             System.out.println("hurray connected");
	             resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'");
	             resultSet.next();
	             String str = resultSet.getString("password"); 
	           return str;
	}
	 public String dbConnectionRole() throws Exception, IllegalAccessException, ClassNotFoundException{
		  
         
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         connection = DBConnection.getConnection();
         statement = connection.createStatement();
         System.out.println("hurray connected");
         resultSet = statement.executeQuery("select a.role_name from crm_role a, crm_user b where a.role_id = b.role_id AND email_id = 'tulasirammail@gmail.com' AND delete_status = 'no'");
         resultSet.next();
         String str = resultSet.getString("role_name"); 
         return str;
}
	
	public void pagination(){
		 sleep(2);
		 //verifying for pagination to next page for all entries
		 WebElement w= driver.findElement(By.id(or.getProperty("pagination_next")));
		 if(w.isDisplayed()){
			 System.out.println("pagination next button is present");
		 }else
		 {
			 Assert.fail("pagination button not present");
		 }
	     while(!w.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
	    	   String s = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	    	   System.out.println(s);
	           w.click();
	         
	          String s1 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	         if(!s1.equalsIgnoreCase(s)){
	        	 System.out.println("navigating to next page");
	          }else{
	    	     Assert.fail("page navigation failed to next page");
	         }
	         sleep(1);
	         //verification of navigation to the previous pge for all entries 
	         if(w.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")) {
	    	     WebElement w1 =  driver.findElement(By.id(or.getProperty("pagiantion_prev")));
	    	 if(w1.isDisplayed()){
	    		System.out.println("pagination previous button present");
	    	 }else{
	    		Assert.fail("pagination previous button not present");
	      }
	        while(!w1.getAttribute("class").equalsIgnoreCase("paginate_disabled_previous")){
	        	String s2 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	        	w1.click();
	        	String s3 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	        	System.out.println(s2);
	        	if(!s2.equalsIgnoreCase(s3)){
	        		System.out.println("navigated to previous page");
	        	}else{
	        		Assert.fail("page navigation failed to previous page");
	        	}
	        }
	        //To break the loop once the circle finished
		      if(!w.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
		    	  break;
		      }
	        }
	      
	   }
	      }
		
	public void showEntries(){
		 //====== Verifying the Show Entries Drop down ========  
		   List<WebElement> lw = driver.findElement(By.name(or.getProperty("show_dropDown"))).findElements(By.tagName(or.getProperty("option_tag")));
		   if(lw.size()!=0){
			   System.out.println(lw.size());
			   for(int i = 0;i<lw.size();i++){
				   lw.get(i).click();
				   sleep(1);
				   int x = Integer.parseInt(lw.get(i).getText());
				   List<WebElement> tr =driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("tableRowtag")));
				   if(x==(tr.size()))
				   {
					   System.out.println("Value chosen from drop down:"+x);
					   System.out.println("users displayed in list:"+tr.size());
				   }
				   else {
					   if(tr.size()<x){
						  WebElement wl =  driver.findElement(By.id(or.getProperty("pagination_next")));
						  if(wl.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
							  System.out.println("The list of items are "+tr.size() +" which are less than the "+ x +" entries chosen from the drop down");
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
		   WebElement w =driver.findElement(By.tagName(or.getProperty("SearchBox")));
		   if(w.isEnabled()){
			   w.sendKeys("Ajay"); 
			   sleep(2);
			   List<WebElement> h = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("tableData_tag")));
			   System.out.println("total matches found:"+h.size());
			   int cnt = 0;
			   for(int x =0;x<h.size();x++){
			  
				   if(h.get(x).getText().contains("Ajay")){
					   System.out.println(h.get(x).getText());
					   cnt++;
			   }
		  }
		  sleep(1);
		   if(cnt!=0){
			   System.out.println("search success ");
		   }else{
			   Assert.fail("Search not working properly");
		   }
		   }
		   else{
			   Assert.fail("serach box not accepting values");
		   }
	}
	public void sortAscend(){
		 List<WebElement> tbody = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("tableRowtag")));
		 if(tbody.size()!=0){
		   System.out.println(tbody.size());
		   ArrayList<Integer> li= new ArrayList<Integer>();
		   for(int m = 0;m<tbody.size();m++){
			   List<WebElement> trr = tbody.get(m).findElements(By.tagName(or.getProperty("tableData_tag")));
			   System.out.println(trr.get(0).getText());
			   String s= trr.get(0).getText();
			   int y = Integer.parseInt(s);
			   li.add(y);
			 }
		 WebElement w = driver.findElement(By.className(or.getProperty("SortAsc_button")));
		 if(w.isDisplayed()){
			 if(w.getAttribute("class").equals("sorting_asc")){
				 System.out.println("elements in ascending order");
				 for(int j= 0;j<(li.size()-1);j++){
					 if(li.get(j)<li.get(j+1)){
						 System.out.println(li.get(j)+"\n");
					 }
					 else{
						 System.out.println("elements are not in sorted order");
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
		WebElement w =  driver.findElement(By.className(or.getProperty("SortAsc_button")));
		if(w.isDisplayed()){
			w.click();
		}
		else{
			Assert.fail("sort Ascend button not present");
		}
		 List<WebElement> tbody1 = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("tableRowtag")));
		 if(tbody1.size()!=0){
		   System.out.println(tbody1.size());
		   ArrayList<Integer> li1= new ArrayList<Integer>();
		   for(int m1 = 0;m1<tbody1.size();m1++){
			   List<WebElement> trr1 = tbody1.get(m1).findElements(By.tagName(or.getProperty("tableData_tag")));
			   if(trr1.size()!=0){
				   System.out.println(trr1.get(0).getText());
				   String s1= trr1.get(0).getText();
				   int y1 = Integer.parseInt(s1);
				   li1.add(y1);
			   }else{
				   Assert.fail("table data list not loaded properly");
			   }
			 }
		 WebElement w1 = driver.findElement(By.className(or.getProperty("SortDsc_button")));
		 if(w1.isDisplayed()){
			 if(w1.getAttribute("class").equals("sorting_desc")){
				 System.out.println("elements in descending  order");
				 for(int k= 0;k<(li1.size()-1);k++){
					 if(li1.get(k)>li1.get(k+1)){
						 System.out.println(li1.get(k)+"\n");
					 }
					 else{
						 System.out.println("elements are not in sorted order");
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
	 // @Test
	  public void test() throws IllegalAccessException, ClassNotFoundException, Exception{
		  String s= dbConnection("rakeshbasani.qa@gmail.com");
		  System.out.println(s);
		//  String s1 =  dbConnectionRole();
		//  System.out.println(s1);
		
		 
	  }
	
 //@Test
  public void a_createbutton()throws Exception  {
	   //======= Login verification =========
	 		mylogin();
	 		
	   //==========Side Tree Expansion==========
	 	expand();
	 	List<WebElement> li=driver.findElement(By.id(or.getProperty("Sidetree_menu"))).findElements(By.tagName(or.getProperty("Anchor_tag")));
	 	if(li.size()!=0){
	 		System.out.println(li.size());
	 		for(int i =0;i<li.size();i++){
	 			System.out.println(li.get(i).getText());
	 		}
	 		System.out.println("The tree expanded successfully");
	 	}else{
	 		Assert.fail("Elememts of side tree not loaded properly");
	 	}
	 	
	 	
	  
	  //======Clicking on Create button=======
	  
	   WebElement wc = driver.findElement(By.id(or.getProperty("create_button")));
	   if(wc.isDisplayed()){
		   wc.click();
		   sleep(2);
		   System.out.println("Navigated to create page successfully");
	   }
	   else{
		   Assert.fail("Create button not present");
	   }
	   System.out.println("===========================================");
 }
 
 
// @Test
  public void b_dropDownVerification() throws Exception{
	  //======'Manager' Drop down button presence and its working=====
	 	 mylogin();
	 	 expand();
	 	 WebElement wc = driver.findElement(By.id(or.getProperty("create_button")));
	 	 if(wc.isDisplayed()){
	 		 wc.click();
	 		 sleep(2);
	 	 }else{
	 		 Assert.fail("Create button not present");
	 	 }
	     List<WebElement> M = driver.findElement(By.name(or.getProperty("manager_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
	     if(M.size()!=0){
	    	 System.out.println("drop down size"+M.size());
	    	 int count = 0;
	    	 for(int i=0;i<M.size();i++){
	    		 M.get(i).click(); 
	    		 count++;
	    	 }
	    if(count==M.size()){
	    	System.out.println("Manager drop down successfully choosing all values for "+count+" times");
	    }else{
	    	Assert.fail("manager drop down missing some values");
	    }
	     }else{
	    	 Assert.fail(" manager drop down is not presented");
	     }
	 //======'Role' Drop down button presence and its working=====
	  List<WebElement> R = driver.findElement(By.name(or.getProperty("role_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
	  if(R.size()!=0){
	     System.out.println("drop down size"+R.size());
	     int cnt=0;
	     for(int j=0;j<R.size();j++){
	    	 R.get(j).click();
	    	 cnt++;
	  }	
	     if(cnt==R.size()){
	    	 System.out.println("All elements are clicked in role  dropdown for "+cnt+" times");
	     }
	     else{
	    	 Assert.fail("failed in choosing all the values from role drop down");
	     }
	  }else{
		  Assert.fail("Role drop down is not present");
	  }
	    //======Service dropdown presence and working =======
	    R.get(2).click();
		List<WebElement>li = driver.findElement(By.name(or.getProperty("service_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
		if(li.size()!=0){
			System.out.println("dropdown size"+li.size());
			int cont=0;
			for(int i= 0;i<li.size();i++){
				li.get(i).click();
				sleep(1);
				cont++;
		}
		if(cont==li.size()){
			System.out.println("All values are clicked in service drop down for " + cont+" times");
		}else{
			Assert.fail("failed in choosing all values from service drop down");
		}
		}else{
			Assert.fail("service drop down not present");
		}
	 System.out.println("=========================================================");
 }
 
 
 
 //	@Test
 	public void c_ButtonVerification() throws Exception{
  		 mylogin();
  		 expand();
  		 WebElement wc = driver.findElement(By.id(or.getProperty("create_button")));
	 	 if(wc.isDisplayed()){
	 		 wc.click();
	 		 sleep(2);
	 	 }else{
	 		 Assert.fail("Create button not present");
	 	 }
  		
 		//======checking of '+' button working ========
  		List<WebElement> R = driver.findElement(By.name(or.getProperty("role_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
  		if(R.size()!=0){
  			R.get(2).click();
  			WebElement ws = driver.findElement(By.id(or.getProperty("service_button")));
  			if(ws.isDisplayed()){
  				ws.click();  
  				sleep(2);}
  			else{
		  System.out.println("service button not present");
	  }
	  WebElement w = driver.findElement(By.cssSelector(or.getProperty("createService_popup")));
	  System.out.println(w.getText());
	  if(w.getText().equals("Create Service")){
		  System.out.println("page navigated to createService window successfully");
	  }else{
		  Assert.fail("page naviagation to the create service is failed");
	  }
	  WebElement wt = driver.findElement(By.className(or.getProperty("text_service")));
	  if(wt.isEnabled()){
		  wt.sendKeys("database admin");
		  sleep(1);
	  }else{
		  Assert.fail("text box is not enabled");
	  }
	  driver.findElement(By.id(or.getProperty("create_service"))).click(); 
	  sleep(2);
	  Actions ac = new Actions(driver);
	  ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
	  driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  System.out.println("c_ButtonVerification() success");
  		}else{
  			Assert.fail("role drop down not present");
  		}
 
 	}
 	
//	@Test
	 public void d_newUserCreation() throws Exception{
	   //========= Creation of New User ======
		 mylogin();
		 expand();
		 String str = null;
		 WebElement wc = driver.findElement(By.id(or.getProperty("create_button")));
	 	 if(wc.isDisplayed()){
	 		 wc.click();
	 		 sleep(2);
	 	 }else{
	 		 Assert.fail("Create button not present");
	 	 }
	 	//fetching the user details from the test data 
	    int columns = sh6.getColumns();
		int rows = sh6.getRows();
		int col;
		//String s1;
		for(int row=1;row<rows;row++){
			 col=0;
			 sleep(1);
			 if(!sh6.getCell(col, row).getContents().equals("")){
					 
			 //Assigning first name 
				 WebElement wu = driver.findElement(By.name(or.getProperty("u_fname")));
			 if(wu.isEnabled()){
				 wu.sendKeys(sh6.getCell(col, row).getContents());
				 sleep(1);
			 }else
			 {
				 Assert.fail("firstname text box not present");
			 }
			 //Assigning last name
			 WebElement wl = driver.findElement(By.name(or.getProperty("u_lname")));
			 if(wl.isEnabled()){
				 wl.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);
			 }else{
				 Assert.fail("last name text box not present");
			 }
			 //Assigning email
			 WebElement we = driver.findElement(By.name(or.getProperty("u_email")));
			 if(we.isEnabled()){
				 we.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);
			 }else{
				 Assert.fail("email text box not present");
			 }
			 //Assigning empId
			 WebElement wei = driver.findElement(By.name(or.getProperty("u_empId")));
			 if(wei.isEnabled()){
				 wei.sendKeys(sh6.getCell(++col, row).getContents());
				 sleep(1);}
			 else{
				 Assert.fail("empId text box not present");
			 }
			 //choosing the manager from the drop down
			 List<WebElement> M = driver.findElement(By.name(or.getProperty("manager_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
			 if(M.size()!=0){
				 int a =random(M.size());
				 if (M.get(a).getText().contentEquals("--- SELECT ---")){
					 a++;  
			 }
			 M.get(a).click();
			 sleep(1);
			 System.out.println(M.get(a).getText());
			 }else{
				 Assert.fail("Manager drop down not present");
			 }
			
			 //choosing the role from the role drop down
			 List<WebElement> R = driver.findElement(By.name(or.getProperty("role_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
			 if(R.size()!=0){
				 int b =random(R.size());
				 str = R.get(b).getText();
				 if (R.get(b).getText().contentEquals("--- SELECT ---")){
				 b++;  
			 }
			 R.get(b).click();
			 System.out.println(R.get(b).getText());
			 if(R.get(b).getText().equals("Architect")){
				 //selecting the service if the role is architect
				 List<WebElement> ls= driver.findElement(By.name(or.getProperty("service_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
				 if(ls.size()!=0){
					 System.out.println(ls.size());
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
	      WebElement ws = driver.findElement(By.id(or.getProperty("createUser_button")));
	      if(ws.isDisplayed()){
	    	  ws.submit(); 
	    	  sleep(10);
	      }else{
	    	  Assert.fail("Create button not present");
	      }
	      //checking for the resultant message of created user
	      if(driver.findElement(By.id(or.getProperty("resultant_msg"))).getText().equals("Successfully Created...Password Sent to Your email id")){
	    	String s = dbConnection(sh6.getCell(2, row).getContents());
	    	System.out.println(s);
	    	sleep(1);
	    	List<WebElement> R1 = driver.findElement(By.name(or.getProperty("role_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
	    	driver.get(config.getProperty("url"));
	    	sleep(2);
	    	login(sh6.getCell(2, row).getContents(),s);
	    	sleep(4);
	    	
	    	if(str.equals("Manager")||str.equals("Architect"))
	    	{
	    		if(driver.findElement(By.xpath(or.getProperty("invalid_user"))).equals("Invalid User Login ")){
	    			System.out.println("The roles manager and Architect have no login access");
	    		}
	    	}else{
	    		WebElement www = driver.findElement(By.className(or.getProperty("LoggedIn_nuser_name")));
	    		if(www.equals(driver.findElement(By.className("user_logout"))))
	    			{
	    				System.out.println("user created successfully");
	    			}
	    	
	    			//driver.findElement(By.className("user_logout")).click();
	    			driver.findElement(By.linkText("Logout")).click();
	    	}
	    	sleep(2);
	    	mylogin();
	    	expand();
	    	 driver.findElement(By.id(or.getProperty("create_button")));
	      }else
	      {
	    	  System.out.println("user already exist....try with other credentials  ");
	      }
		  wu.clear();
		  wl.clear();
		  we.clear();
		  wei.clear();
		  sleep(1);
			 }else{
				System.out.println("no data present in test data to read");
				break;
			 }
		}
		System.out.println("=================d_newUserCreation() success==========================");
 }
	 
	 
// @Test
  public void e_showEntriesDropDown() throws Exception   {
		   
	   //======Clicking on Update button======= 
	 	mylogin();
	 	expand();
	    WebElement wup = driver.findElement(By.id(or.getProperty("updateUser_button")));
	    if(wup.isDisplayed()){
	    	wup.click();
	    	sleep(2);
	    }
	    else{
	    	Assert.fail("update button not present");
	    }
	    //verifying the show entries drop down in update page by calling method
	  	showEntries();
	   System.out.println("==============e_showEntriesDropDown() success=======================");
 }  
 // @Test
	 public void f_searchUsers() throws Exception{
		  //====== Searching for a specific user ======== 
		   mylogin();
		   expand();
		   //clicking on update button
		   WebElement wup = driver.findElement(By.id(or.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		   //searching for the specific user in update user page
		  searchUser();
		   System.out.println(" =====================f_searchUsers() success=================");
	 }   
	 
		
//	@Test
	public void g_updatingUser() throws Exception{
		mylogin();
		expand();
		//clicking on the update button from the side tree menu
		 WebElement wup = driver.findElement(By.id(or.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		  //searching for a specific user whose details to be uploaded  
		 WebElement ws =  driver.findElement(By.tagName(or.getProperty("SearchBox")));
		 if(ws.isDisplayed()){
			ws.sendKeys("tulasirammail@gmail.com"); 
			sleep(2);
		}else{
			Assert.fail("Search box not present");
		}
		//clicking on update button of a user
		driver.findElement(By.tagName(or.getProperty("table_tag"))).findElement(By.tagName(or.getProperty("Anchor_tag"))).click();
	   sleep(4);
	   //performing modification to the existing data
	   List<WebElement> man=driver.findElement(By.name(or.getProperty("manager_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
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
		 System.out.println("role before updation   "+s1);
	   //updating the role of existing user
		 sleep(2);
	   List<WebElement> R = driver.findElement(By.name(or.getProperty("role_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
		 if(R.size()!=0){
		 int b =random(R.size());
		 
		 if (R.get(b).getText().contentEquals("--- SELECT ---")||R.get(b).getText().contentEquals("Architect")||R.get(b).getText().contentEquals("Manager")){
			 b= b+3;  
			 System.out.println("randomly picked role  "+R.get(b).getText());
		 }
		 R.get(b).click();
	   //clicking on update button
	   WebElement wb = driver.findElement(By.id(or.getProperty("reupdate_button")));
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
	   System.out.println("role after updation   "+s2);
	   String Rolepass = dbConnection("tulasirammail@gmail.com");
	   System.out.println(Rolepass);
	   driver.get(config.getProperty("url"));
	   login("tulasirammail@gmail.com",Rolepass);
	   sleep(5);
	   if(driver.findElement(By.className(or.getProperty("LoggedIn_nuser_name"))).getText().contains(s2)){
		   System.out.println("User updated successfully");
	   }else{
		   Assert.fail("user updation failed");
	   }
		 
		 
		 }
			
		
			
		 
	   System.out.println("===========g_updatingUser() is successs==============");
	 }
 //  @Test
	 public void h_sorting () throws Exception{
		 mylogin();
		 expand();
		 WebElement wup = driver.findElement(By.id(or.getProperty("updateUser_button")));
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
		 WebElement wup = driver.findElement(By.id(or.getProperty("updateUser_button")));
		    if(wup.isDisplayed()){
		    	wup.click();
		    	sleep(2);
		    }
		    else{
		    	Assert.fail("update button not present");
		    }
		    //calling for the 'pagination' user defined method
		pagination();
        System.out.println("=============h_pageNavigation() success==============");
	 }
  
 
	 
//   @Test
	public void j_showentries() throws Exception{
		mylogin();
		expand();
		//clicking on delete user button from the tree menu
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("showEntries drop down not present");
		}
		//calling the show Entries user defined method
		showEntries();
		System.out.println("==========j_showentries() success=============");
	}			
	//@Test
	public void k_searchbutton() throws Exception{
		mylogin();
		expand();
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//calling the search user method for searching a specific user 
		searchUser();
		   System.out.println("======== k_serachbutton() success===========");
	}
	
	 
//	@Test
	public void l_deletinguser() throws Exception{
		mylogin();
		expand();
		//clicking on delete user button
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}else{
			Assert.fail("delete user button not present in tree menu");
		}
		//entering a value in search box
		WebElement wi = driver.findElement(By.tagName(or.getProperty("SearchBox")));
		if(wi.isEnabled()){
			wi.sendKeys("ajay");
			sleep(3);
		}
		else{
			Assert.fail("search text box is not enabled");
		}
		//picking all the rows into one list 
		List<WebElement> li = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElement(By.tagName(or.getProperty("tableRowtag"))).findElements(By.tagName(or.getProperty("tableData_tag")));
		System.out.println(li.size());
		if(li.size()!=0){
			//storing the mailId of user to be deleted in a string 
			String s = li.get(2).getText();
			System.out.println(s);
			 String sdelete = dbConnection(s);
			List<WebElement> li1 = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("Anchor_tag")));
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
		   List<WebElement> lid = driver.findElement(By.id(or.getProperty("login_page"))).findElements(By.tagName(or.getProperty("labelview")));
		  if(lid.size()!=0){
			  if(lid.get(2).getText().contains("Invalid User Login")){
		  
			   System.out.println("user deleted successfully");
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
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
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
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
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
		WebElement wv =driver.findElement(By.id(or.getProperty("viewAllUsers")));
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
		WebElement wv =driver.findElement(By.id(or.getProperty("viewAllUsers")));
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
		WebElement wv =driver.findElement(By.id(or.getProperty("viewAllUsers")));
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
		WebElement wv =driver.findElement(By.id(or.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		WebElement we =driver.findElement(By.id(or.getProperty("pagination_next")));
		if(we.isDisplayed()){
		int i = 10;
		while(!we.getAttribute("class").equalsIgnoreCase(or.getProperty("nextpage_disabled"))){
			we.click();
			List<WebElement> li = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("tableRowtag")));
			if(li.size()!=0){
				i= i+li.size();
				sleep(2);
			}else{
				Assert.fail("list not properly loaded");
			}
		}
		System.out.println(i + " no.of users are viewed successfully");
		}else{
			Assert.fail("pagiantionNext_button is not present");
		}
	}
//	@Test
	public void s_pageNavigation() throws Exception{
		mylogin();
		expand();
		WebElement wv =driver.findElement(By.id(or.getProperty("viewAllUsers")));
		if(wv.isDisplayed()){
			wv.click();
			sleep(2);
		}else{
			Assert.fail("view all users button is not present");
		}
		pagination();
	}
	
	//@Test
	public void t_configCreation() throws Exception{
		mylogin();
		expand();
		//taking the values of the existing paths
		WebElement w1 = driver.findElement(By.id(or.getProperty("viewConfig")));
		if(w1.isDisplayed()){
			w1.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		ArrayList<String> as = new ArrayList<String>();
		List<WebElement> lip = driver.findElement(By.className(or.getProperty("pathclass"))).findElements(By.tagName(or.getProperty("labelview")));
		System.out.println(lip.size());
		for(int i = 0;i<lip.size();i++){
			if(i%2!=0){
				String s = lip.get(i).getText();
				as.add(s);
			}
		}
		//deleting existing paths
		sleep(2);
		driver.findElement(By.id(or.getProperty("deleteConfig"))).click();
		sleep(2);
		driver.findElement(By.id(or.getProperty("createUser_button"))).click();
		sleep(2);
		//clicking on createConfig button
		driver.findElement(By.id("createConfiguration")).click();
		sleep(2);
		//template path assigning
		WebElement wt = driver.findElement(By.name(or.getProperty("templatePath")));
		if(!as.get(0).equals(sh6.getCell(1, 9).getContents())){
		
			wt.sendKeys(sh6.getCell(1, 9).getContents());
			
		}else{
			wt.sendKeys(sh6.getCell(0, 15).getContents());
		}
		sleep(1);
		
		//proposal path uploading 
		WebElement wp = driver.findElement(By.name(or.getProperty("proposalPath")));
			if(!as.get(1).equals(sh6.getCell(1, 10).getContents())){
				wp.sendKeys(sh6.getCell(1, 10).getContents());
			}else{
				wp.sendKeys(sh6.getCell(0, 15).getContents());
			}
			sleep(1);
		//quote path upload	
		WebElement wq = driver.findElement(By.name(or.getProperty("quotePath")));
		if(!as.get(2).equals(sh6.getCell(1, 11).getContents())){
			wq.sendKeys(sh6.getCell(1, 11).getContents());
		}else{
			wq.sendKeys(sh6.getCell(0, 16).getContents());
		}
		sleep(1);
		
		//profile image path upload
		WebElement wpi = driver.findElement(By.name(or.getProperty("profileImagePath")));
		if(!as.get(3).equals(sh6.getCell(1, 12).getContents())){
			wpi.sendKeys(sh6.getCell(1, 12).getContents());
		}else{
			wpi.sendKeys(sh6.getCell(0, 17).getContents());
		}
		sleep(1);
		WebElement wr = driver.findElement(By.id(or.getProperty("createUser_button")));
		if(wr.isDisplayed()){
			wr.click();
			sleep(2);
		}else{
			Assert.fail("create button not present");
		}
		WebElement w = driver.findElement(By.id(or.getProperty("resultant_msg")));
		if(w.isDisplayed()){
			if(w.getText().contains("Configurations Successfully Inserted ...!")){
				sleep(2);
				myResearcher();
				
				System.out.println("configuration creation success");
			}else{
				System.out.println("configuration already exists");
			}
		}else{
			Assert.fail("no resultant message is displayed");
		}
		
	}
	
//	@Test
	public void t_updateConfig() throws Exception{
		mylogin();
		expand();
		//taking the values of the existing paths
		WebElement w1 = driver.findElement(By.id(or.getProperty("viewConfig")));
		if(w1.isDisplayed()){
			w1.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		ArrayList<String> as = new ArrayList<String>();
		List<WebElement> lip = driver.findElement(By.className(or.getProperty("pathclass"))).findElements(By.tagName(or.getProperty("labelview")));
		System.out.println(lip.size());
		for(int i = 0;i<lip.size();i++){
			if(i%2!=0){
				String s = lip.get(i).getText();
				as.add(s);
			}
		}
		
		//clicking on updateConfig button
		driver.findElement(By.id(or.getProperty("updateConfig"))).click();
		sleep(2);
		//template path modification
		WebElement wt = driver.findElement(By.name(or.getProperty("templatePath")));
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
		WebElement wp = driver.findElement(By.name(or.getProperty("proposalPath")));
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
		WebElement wq = driver.findElement(By.name(or.getProperty("quotePath")));
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
		WebElement wpi = driver.findElement(By.name(or.getProperty("profileImagePath")));
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
		WebElement wr = driver.findElement(By.id(or.getProperty("createUser_button")));
		if(wr.isDisplayed()){
			wr.click();
			sleep(2);
		}else{
			Assert.fail("create button not present");
		}
		WebElement w = driver.findElement(By.id(or.getProperty("resultant_msg")));
		if(w.isDisplayed()){
			if(w.getText().contains("Configurations Successfully Updated ...!")){
				sleep(2);
				myResearcher();
				
				System.out.println("configuration updated successfully");
			}else{
				System.out.println("configuration updation failed");
			}
		}else{
			Assert.fail("no resultant message is displayed");
		}
		
	}


//	@Test
	public void v_viewConfig() throws Exception{
		mylogin();
		expand();
		WebElement w = driver.findElement(By.id(or.getProperty("viewConfig")));
		if(w.isDisplayed()){
			w.click();
			sleep(1);
		}else{
			Assert.fail("view Configuration button is not present");
		}
		List<WebElement> lip = driver.findElement(By.className(or.getProperty("pathclass"))).findElements(By.tagName(or.getProperty("labelview")));
		System.out.println(lip.size());
		if(lip.size()!=0){
			int col = 1;
			int row = 9;
			for(int i = 0;i<lip.size();i++){
				if(i%2!=0){
					
					System.out.println(row);
					System.out.println(sh6.getCell(col, row).getContents());
					System.out.println(lip.get(i).getText());
					if(lip.get(i).getText().equalsIgnoreCase(sh6.getCell(col, row).getContents())){
						System.out.println("server path is verified");
						
					}
					else{
						Assert.fail("path not present");
					  }
					row++;
					}
				}
			}
		
		//help.screenshot("viewconfig");
	}
//	@Test
	public void w_deleteConfig() throws Exception{
		mylogin();
		expand();
		WebElement w = driver.findElement(By.id(or.getProperty("deleteConfig")));
		if(w.isDisplayed()){
			w.click();
			sleep(2);
		}else{
			Assert.fail("delete configuration button not present");
		}
		WebElement w2 = driver.findElement(By.id(or.getProperty("createUser_button")));
		if(w2.isDisplayed()){
			w2.click();
			sleep(2);
		}else{
			Assert.fail("delete button is not present");
		}
		WebElement w1 = driver.findElement(By.id(or.getProperty("resultant_msg")));
		if(w1.isDisplayed()){
			if(w1.getText().equals("Successfully Deleted...!")){
				myResearcher();
				System.out.println("configuration successfully deleted");
			}
			else{
				Assert.fail("configuration deletion failed");
			}
		}else{
			Assert.fail("resultant message is not displayed");
		}
	}
		
}
 

  	
    
		  
	 
  

