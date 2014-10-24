package crm;
import testUtils.Helper;

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
	public void mylogin() throws Exception{
		login(config.getProperty("auname"), config.getProperty("apass"));
		WebElement w = driver.findElement(By.className(or.getProperty("LoggedIn_nuser_name")));
		if(w.getText().equals("Hi ! Admin srini")){
			System.out.println("Logged in Successfully");
		}else
		{
			Assert.fail("Login failed");
		}
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
			   else{
				   Assert.fail("The drop down menu is not working properly");
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
 
 
// 	@Test
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
 	
//	 	@Test
	 public void d_newUserCreation() throws Exception{
	   //========= Creation of New User ======
		 mylogin();
		 expand();
		 
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
		for(int row=1;row<rows;row++){
			 col=0;
			 sleep(1);
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
	      sleep(1);
	      }else{
	    	  Assert.fail("Create button not present");
	      }
	      //checking for the resultant message of created user
	      if(driver.findElement(By.id(or.getProperty("resultant_msg"))).getText().equals("Successfully Created...Password Sent to Your email id")){
	    	 
	    	  System.out.println("user created successfully");
	      }else
	      {
	    	  System.out.println("user already exist....try with other details  ");
	      }
		  wu.clear();
		  wl.clear();
		  we.clear();
		  wei.clear();
		  sleep(1);
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
//  @Test
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
			ws.sendKeys("Ajay"); 
			sleep(2);
		}else{
			Assert.fail("update button nont present");
		}
		//clicking on the user to be updated 
		List<WebElement> liA = driver.findElement(By.tagName(or.getProperty("table_tag"))).findElements(By.tagName(or.getProperty("Anchor_tag")));
		if(liA.size()!=0){
			liA.get(0).click();
		    sleep(3);
		}else{
			Assert.fail("failed to load the users with the given serach key");
		}
	   sleep(4);
	   //performing modification to the existing data
	   List<WebElement> man=driver.findElement(By.name(or.getProperty("manager_dd"))).findElements(By.tagName(or.getProperty("option_tag")));
	   if(man.size()!=0){
	   int rnm = random(man.size());
	   man.get(rnm).click();
	   }else{
		   Assert.fail("manager drop down not present ");
	   }
	   //clicking on update button
	   WebElement wb = driver.findElement(By.id(or.getProperty("reupdate_button")));
	   if(wb.isDisplayed()){
		   wb.submit();
		   sleep(2);
	   }else{
		   Assert.fail("update user button note present");
	   }
	   //verifying for resultant message
	   WebElement we = driver.findElement(By.id(or.getProperty("resultant_msg")));
	   System.out.println(we.getText());
	   if(we.getText().equalsIgnoreCase("Successfully Updated...")){
		   System.out.println("user updated successfully");
	   }else{
		   Assert.fail("user updation failed");
	   }
	   
	   sleep(2);
	   driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
	   System.out.println("===========g_updatingUser() is successs==============");
	 }
  // @Test
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
  
 
	 
  // @Test
	public void j_showentries() throws Exception{
		mylogin();
		expand();
		//clicking on delete user button from the tree menu
		WebElement wd = driver.findElement(By.id(or.getProperty("deleteUser_Button")));
		if(wd.isDisplayed()){
			wd.click();
			sleep(2);
		}
		//calling the show Entries user defined method
		showEntries();
		   System.out.println("j_showentries() success");
	}			
//	@Test
	public void k_searchbutton() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		searchUser();
		   System.out.println(" k_serachbutton() success");
	}
	
	 
//	@Test
	public void l_deletinguser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		sleep(2);
		driver.findElement(By.tagName("input")).sendKeys("ajay");
		List<WebElement> li = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td"));
		String s = li.get(2).getText();
		System.out.println(s);
		ArrayList<String> a = new ArrayList<String>();
		List<WebElement> li1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("a"));
		li1.get(0).click();
		sleep(3);
		List<WebElement> li4 = driver.findElement(By.id("example_length")).findElements(By.tagName("option"));
		li4.get(3).click();
		sleep(3);
		List<WebElement> li2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(int i=0;i<li2.size();i++){
			 List<WebElement> trr = li2.get(i).findElements(By.tagName("td"));
			   System.out.println(trr.get(2).getText());
			   String s1= trr.get(2).getText();
			   a.add(s1);
		}
		System.out.println(a.size());
		int count = 0;
		for(int j=0;j<a.size();j++){
			if(s.equals(a.get(j))){
				count++;
			}
		}
		if(count==0){
			System.out.println("user deleted successfully");
		}
		else{
			Assert.fail("user deletion failed");
		}
	}  
	
//	@Test
	public void m_sort() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		sleep(2);
		sortAscend();
		sortDescend();
		
	}
	
//	@Test
	public void n_pagenavigation() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		pagination();
	}
	
//	@Test
	public void o_showEntries() throws Exception{
		//=======verification of showentries dropdown ========
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		showEntries();
	}  
//	@Test
	public void p_searchUser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		searchUser();
	}	
//	@Test 
	public void q_sortedUser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		sortAscend();
		sleep(1);
		sortDescend();
	}
//	@Test
	public void r_viewUsers() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		WebElement we =driver.findElement(By.id("example_next"));
		int i = 10;
		while(!we.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
			we.click();
			List<WebElement> li = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			i= i+li.size();
			sleep(2);
		}
		System.out.println(i + " no.of users are viewed successfully");
	}
//	@Test
	public void s_pageNavigation() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		pagination();
	}
	
//	@Test
	public void t_configCreation() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("createConfiguration")).click();
		sleep(2);
		driver.findElement(By.name("templateUploadPath")).sendKeys("/usr/local/tomcat7/webapps/leadcrmtemplates");
		driver.findElement(By.name("proposalUploadPath")).sendKeys("/usr/local/tomcat7/webapps/leadcrmproposals");
		driver.findElement(By.name("quoteUploadPath")).sendKeys("/usr/local/tomcat7/webapps/leadcrmquotes");
		driver.findElement(By.name("profileImageUploadPath")).sendKeys("/usr/local/tomcat7/webapps/leadcrmprofileimages");
		driver.findElement(By.id("registerbutton")).click();
		sleep(2);
		WebElement w = driver.findElement(By.id("result_msg_div"));
		if(w.getText().contains("Configurations Successfully Inserted ...!")){
			System.out.println("configuration creation success");
		}else{
			System.out.println("configuration already exists");
		}
		
	}
//	@Test
	public void u_updateConfig() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("updateConfiguration")).click();
		sleep(2);
		driver.findElement(By.name("quoteUploadPath")).sendKeys("C:/configuration/quotes");
		driver.findElement(By.id("registerbutton")).click();
		sleep(2);
		WebElement w = driver.findElement(By.className("success_msg"));
		if(w.getText().contains("Configurations Successfully Updated ...!")){
			System.out.println("successfully updated");
		}
	}
//	@Test
	public void v_viewConfig() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("viewConfiguration")).click();
		sleep(1);
		help.screenshot("viewconfig");
	}
//	@Test
	public void w_deleteConfig() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteConfiguration")).click();
		sleep(2);
		driver.findElement(By.id("registerbutton")).click();
		sleep(2);
		WebElement w = driver.findElement(By.id("result_msg_div"));
		if(w.getText().equals("Successfully Deleted...!")){
			System.out.println("configuration successfully deleted");
		}
	}
	
//	@Test
	public void y_Researcher() throws Exception 
	{
	help.browser();
	driver.get(config.getProperty("url"));
	help.maxbrowser();
	help.browsererror();
	help.login("ajay.kovuri@nexiilabs.com", "password");
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
	driver.findElement(By.className("medium")).sendKeys("D:\\LEADCRM\\Researcher Test Data1.xls");
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
		Assert.fail("leads_upload_button not presented");
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

		
}
 

  	
    
		  
	 
  

