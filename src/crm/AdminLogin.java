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
		WebElement w = driver.findElement(By.className("user_name"));
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
		 WebElement w= driver.findElement(By.id("example_next"));
	     while(!w.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
	    	   String s = driver.findElement(By.cssSelector("div.dataTables_info")).getText();
	    	   System.out.println(s);
	          w.click();
	         
	         String s1 = driver.findElement(By.id("example_info")).getText();
	        if(!s1.equalsIgnoreCase(s)){
	    	   System.out.println("navigating to next page");
	        }else{
	    	   Assert.fail("page navigation failed to next page");
	        }
	        sleep(1);
	        //verification of navigation to the previous pge for all entries 
	      if(w.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")) {
	    	WebElement w1 =  driver.findElement(By.id("example_previous"));
	        while(!w1.getAttribute("class").equalsIgnoreCase("paginate_disabled_previous")){
	       String s2 = driver.findElement(By.id("example_info")).getText();
	       w1.click();
	       String s3 = driver.findElement(By.id("example_info")).getText();
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
		   List<WebElement> lw = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
		   
		   System.out.println(lw.size());
		   for(int i = 0;i<lw.size();i++){
			   lw.get(i).click();
			   sleep(1);
			   int x = Integer.parseInt(lw.get(i).getText());
			   List<WebElement> tr =driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			   if(x==(tr.size()))
			   {
				   System.out.println("Value chosen from drop down:"+x);
				   System.out.println("users displayed in list:"+tr.size());
				 
			   }
			   else{
				   Assert.fail("The drop down menu is not working properly");
			   }
			  
			 sleep(3);
		   }
	}
	
	public void searchUser(){
		 sleep(1);
		   driver.findElement(By.tagName("input")).sendKeys("Ajay"); 
		   sleep(2);
		   List<WebElement> h = driver.findElement(By.tagName("tbody")).findElements(By.tagName("td"));
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
	public void sortAscend(){
		 List<WebElement> tbody = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		   System.out.println(tbody.size());
		   ArrayList<Integer> li= new ArrayList<Integer>();
		   for(int m = 0;m<tbody.size();m++){
			   List<WebElement> trr = tbody.get(m).findElements(By.tagName("td"));
			   System.out.println(trr.get(0).getText());
			   String s= trr.get(0).getText();
			 int y = Integer.parseInt(s);
			  li.add(y);
			 }
		 System.out.println(li.size());
		 WebElement w = driver.findElement(By.className("sorting_asc"));
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
	}
	public void sortDescend(){
		 driver.findElement(By.className("sorting_asc")).click();
		 List<WebElement> tbody1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		   System.out.println(tbody1.size());
		   ArrayList<Integer> li1= new ArrayList<Integer>();
		   for(int m1 = 0;m1<tbody1.size();m1++){
			   List<WebElement> trr1 = tbody1.get(m1).findElements(By.tagName("td"));
			   System.out.println(trr1.get(0).getText());
			   String s1= trr1.get(0).getText();
			 int y1 = Integer.parseInt(s1);
			  li1.add(y1);
			 }
		 WebElement w1 = driver.findElement(By.className("sorting_desc"));
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
		 }
	
	}
	
//@Test
  public void a_createbutton()throws Exception  {
	   //======= Login verification =========
	 		mylogin();
	 		
	   //==========Side Tree Expansion==========
	 	expand();
	 	List<WebElement> li=driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	 	System.out.println(li.size());
	 	for(int i =0;i<li.size();i++){
	 		System.out.println(li.get(i).getText());
	 	}
	 	System.out.println("The tree expanded successfully");
	 	collapse();
	 	
	 	
	  
	  //======Clicking on Create button=======
	  
	   driver.findElement(By.id("createUser")).click();
	   sleep(2);
	   System.out.println("Navigated to create page successfully");
	   System.out.println("===========================================");
 }
 
 
// @Test
  public void b_dropDownVerification() throws Exception{
	  //======'Manager' Drop down button presence and its working=====
	 	 mylogin();
	 	 expand();
	 	 driver.findElement(By.id("createUser")).click();
	     sleep(2);
	     List<WebElement> M = driver.findElement(By.name("manager")).findElements(By.tagName("option"));
		 System.out.println("drop down size"+M.size());
	     int count = 0;
	    for(int i=0;i<M.size();i++){
		  M.get(i).click(); 
		  count++;
	 }
	    if(count==M.size()){
	    	System.out.println("Manager drop down successfully choosing all values for "+count+" times");
	    }else{
	    	Assert.fail("manager drop down mossing some values");
	    }
	 //======'Role' Drop down button presence and its working=====
	  List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
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
	    //======Service dropdown presence and working =======
	    R.get(2).click();
		List<WebElement>li = driver.findElement(By.name("service")).findElements(By.tagName("option"));
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
	 System.out.println("=========================================================");
 }
 
 
 //	@Test
 	public void c_ButtonVerification() throws Exception{
  		mylogin();
  		expand();
  		driver.findElement(By.id("createUser")).click();
  		sleep(1);
  		
 		//======checking of '+' button working ========
  		List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
 	    R.get(2).click();
	  driver.findElement(By.id("Service")).click();  
	  sleep(2);
	  WebElement w = driver.findElement(By.cssSelector("span.ui-dialog-title"));
	  System.out.println(w.getText());
	  if(w.getText().equals("Create Service")){
		  System.out.println("page navigated to createService window successfully");
	  }
	  driver.findElement(By.className("medium")).sendKeys("database admin");
	  sleep(1);
	  driver.findElement(By.id("createbutton")).click(); 
	  sleep(2);
	  Actions ac = new Actions(driver);
	  ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
	  driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  System.out.println("c_ButtonVerification() success");
 	 }
 	
	// 	@Test
	 public void d_newUserCreation() throws Exception{
	   //========= Creation of New User ======
		 mylogin();
		 expand();
		 driver.findElement(By.id("createUser")).click();
	    int columns = sh6.getColumns();
		int rows = sh6.getRows();
		int col;
		for(int row=1;row<rows;row++){
			 col=0;
			 sleep(1);
			 driver.findElement(By.name("firstname")).sendKeys(sh6.getCell(col, row).getContents());
			 sleep(1);
	  
			 driver.findElement(By.name("lastname")).sendKeys(sh6.getCell(++col, row).getContents());
			 sleep(1);
	   
			 driver.findElement(By.name("email")).sendKeys(sh6.getCell(++col, row).getContents());
			 sleep(1);
			 driver.findElement(By.name("empid")).sendKeys(sh6.getCell(++col, row).getContents());
			 sleep(1);
			 List<WebElement> M = driver.findElement(By.name("manager")).findElements(By.tagName("option"));
			 int a =random(M.size());
			 if (M.get(a).getText().contentEquals("--- SELECT ---")){
				 a++;  
			 }
			 M.get(a).click();
			 sleep(1);
			 System.out.println(M.get(a).getText());
			 List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
			 int b =random(R.size());
			 if (R.get(b).getText().contentEquals("--- SELECT ---")){
				 b++;  
			 }
			 R.get(b).click();
			 System.out.println(R.get(b).getText());
			 if(R.get(b).getText().equals("Architect")){
				 List<WebElement> ls= driver.findElement(By.name("service")).findElements(By.tagName("option"));   	       
				 System.out.println(ls.size());
				 int c = random(ls.size());
				 ls.get(c).click();
			 }
	      driver.findElement(By.id("registerbutton")).submit(); 
	      sleep(1);
	      if(driver.findElement(By.id("result_msg_div")).getText().equals("Successfully Created...Password Sent to Your email id")){
	    	 
	    	  System.out.println("user created successfully");
	      }else
	      {
	    	  Assert.fail("user already exist ");
	      }
		  driver.findElement(By.name("firstname")).clear();
		  driver.findElement(By.name("lastname")).clear();
		  driver.findElement(By.name("email")).clear();
		  driver.findElement(By.name("empid")).clear();
		  sleep(1);
		}
		System.out.println("=================d_newUserCreation() success==========================");
 }
	 
	 
// @Test
  public void e_showEntriesDropDown() throws Exception   {
		   
	   //======Clicking on Update button======= 
	 	mylogin();
	 	expand();
	    driver.findElement(By.id("updateUser")).click();
	    //verifying the show entries drop down in update page by calling method
	  	showEntries();
	   System.out.println("==============e_showEntriesDropDown() success=======================");
 }  
 // @Test
	 public void f_searchUsers() throws Exception{
		  //====== Searching for a specific user ======== 
		   mylogin();
		   expand();
		   driver.findElement(By.id("updateUser")).click();
		   //searching for the specific user in update user page
		  searchUser();
		   System.out.println(" =====================f_searchUsers() success=================");
	 }   
	 
		
//	@Test
	public void g_updatingUser() throws Exception{
		mylogin();
		expand();
		 driver.findElement(By.id("updateUser")).click();
		sleep(1);
		 driver.findElement(By.tagName("input")).sendKeys("tulasi"); 
		 sleep(2);
	   driver.findElement(By.id("118")).click();
	   sleep(4);
	   List<WebElement> man=driver.findElement(By.name("manager")).findElements(By.tagName("option"));
	   int rnm = random(man.size());
	   man.get(rnm).click();
	     
	   driver.findElement(By.id("updatebutton")).submit();
	   sleep(2);
	   WebElement we = driver.findElement(By.className("success_msg"));
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
//	 @Test
	 public void h_sorting () throws Exception{
		 mylogin();
		 expand();
		 driver.findElement(By.id("updateUser")).click();
		 sleep(3);
		 sortAscend(); 
		 sortDescend();
	 }
	 
	
//	 @Test
	 public void i_pageNavigation() throws Exception{
     //==========Page Navigation ======== 
		 mylogin();
		 expand();
		 driver.findElement(By.id("updateUser")).click();
		pagination();
        System.out.println("h_pageNavigation() success");
	 }
  
 
	 
 //  @Test
	public void j_showentries() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
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
		WebElement w = driver.findElement(By.className("success_msg"));
		if(w.getText().equals("Successfully Deleted...!")){
			System.out.println("configuration successfully deleted");
		}
	}
	
		
}
 

  	
    
		  
	 
  

