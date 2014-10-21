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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminLogin extends Helper{
	@BeforeMethod
	public void bf() {
		 browser();
		 driver.get("http://192.168.50.32:8080/leadcrm/login.jsp");
		 maxbrowser();
		 
	}
	@AfterMethod
	public void af(){
		driver.close();
	}
	public void mylogin() throws Exception{
		login(config.getProperty("auname"), config.getProperty("apass"));	
	}
	
	
	public void pagination(){
		 sleep(2);
		   String s = driver.findElement(By.cssSelector("div.dataTables_info")).getText();
		   System.out.println(s);
	       driver.findElement(By.id("example_next")).click();
	       String s1 = driver.findElement(By.id("example_info")).getText();
	       System.out.println(s1);
	       if(!s1.equalsIgnoreCase(s)){
	    	   System.out.println("The page is navigated to next page successfully");
	       }
	       driver.findElement(By.id("example_previous")).click();
	       String s2 = driver.findElement(By.id("example_info")).getText();
	       System.out.println(s2);
	       if(!s1.equalsIgnoreCase(s2)){
	    	   System.out.println("The page is navigated to previous page successfully");
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
				   System.out.println("The drop down menu is not working properly");
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
	
@Test
  public void a_createbutton()throws Exception  {
	   //======= Login verification =========
	 		mylogin();
	 		WebElement w =	driver.findElement(By.className("user_name"));
	 		if(w.getText().contains("Hi ! Admin srini")){
	 			System.out.println("Loggedin Successfully");
	 		}
	   //==========Side Tree Expansion==========
	 	expand();
	 	List<WebElement> li=driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	 	System.out.println(li.size());
	 	for(int i =0;i<li.size();i++){
	 		System.out.println(li.get(i).getText());
	 	}
	 	System.out.println("The tree expanded successfully");
	 	
	 	
	  
	  //======Clicking on Create button=======
	  
	   driver.findElement(By.id("createUser")).click();
	  sleep(2);
	  System.out.println("a_createbutton successful");
 }
 
 
 @Test
  public void b_dropDownVerification() throws Exception{
	  //======'Manager' Drop down button presence and its working=====
	 	mylogin();
	 	expand();
	 	driver.findElement(By.id("createUser")).click();
	     System.out.println("The drop down is present");
	     sleep(2);
	     List<WebElement> M = driver.findElement(By.name("manager")).findElements(By.tagName("option"));
		 System.out.println(M.size());
	  
	 for(int i=0;i<M.size();i++){
		 M.get(i).click(); 
	 }
	 //======'Role' Drop down button presence and its working=====
	 List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
	    System.out.println(R.size());
	    for(int j=0;j<R.size();j++){
		  R.get(j).click();
	  }	
	    //======Service dropdown presence and working =======
	    R.get(2).click();
		List<WebElement>li = driver.findElement(By.name("service")).findElements(By.tagName("option"));
		for(int i= 0;i<li.size();i++){
			li.get(i).click();
			sleep(1);
		}
	 System.out.println(" b_dropDownVerification successful");
 }
 
 
 	@Test
 	public void c_ButtonVerification() throws Exception{
  		mylogin();
  		expand();
  		driver.findElement(By.id("createUser")).click();
  		sleep(1);
  		
 		//======checking of '+' button working ========
  		List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
 	    R.get(2).click();
	  driver.findElement(By.id("Service")).click();  
	  sleep(1);
	  driver.findElement(By.className("medium")).sendKeys("database admin");
	  sleep(2);
	  driver.findElement(By.id("createbutton")).click(); 
	  sleep(2);
	  Actions ac = new Actions(driver);
	  ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
	  driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  System.out.println("c_ButtonVerification() success");
 	 }
	 

	 @Test
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
	      if(driver.findElement(By.className("error_msg")).getText().equals("User already exists...")){
	    	  System.out.println("user creation failed");
	      }else
	      {
	    	  System.out.println("user created successfully");
	      }
		  driver.findElement(By.name("firstname")).clear();
		  driver.findElement(By.name("lastname")).clear();
		  driver.findElement(By.name("email")).clear();
		  driver.findElement(By.name("empid")).clear();
		  sleep(1);
		}
		System.out.println("d_newUserCreation() sucess");
 }
	 
	 
 @Test
  public void e_showEntriesDropDown() throws Exception   {
		   
	   //======Clicking on Update button======= 
	 	mylogin();
	 	expand();
	    driver.findElement(By.id("updateUser")).click();
	  	showEntries();
	   System.out.println("e_showEntriesDropDown() success");
 }  
 
 
 
 @Test
	 public void f_searchUsers() throws Exception{
		  //====== Searching for a specific user ======== 
		   mylogin();
		   expand();
		   driver.findElement(By.id("updateUser")).click();
		  searchUser();
		   System.out.println(" f_searchUsers() success");
	 }   
	 
		
	@Test
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
	   System.out.println(man.size());
	   int rnm = random(man.size());
	   man.get(rnm).click();
	   
	   
	   driver.findElement(By.id("updatebutton")).submit();
	   sleep(2);
	   WebElement we = driver.findElement(By.className("success_msg"));
	   System.out.println(we.getText());
	   if(we.getText().equalsIgnoreCase("Successfully Updated...")){
		   System.out.println("user updated successfully");
	   }
	   sleep(2);
	   driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
	   System.out.println("g_updatingUser() is successs");
	 }
	 @Test
	 public void h_sorting () throws Exception{
		 mylogin();
		 expand();
		 driver.findElement(By.id("updateUser")).click();
		 sleep(3);
		 sortAscend(); 
		 sortDescend();
	 }
	 
	
	 @Test
	 public void i_pageNavigation() throws Exception{
     //==========Page Navigation ======== 
		 mylogin();
		 expand();
		 driver.findElement(By.id("updateUser")).click();
		pagination();
      
       System.out.println("h_pageNavigation() success");
	 }
  
 
	 
   @Test
	public void j_showentries() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		showEntries();
		   System.out.println("j_showentries() success");
	}			
	@Test
	public void k_serachbutton() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		searchUser();
		   System.out.println(" k_serachbutton() success");
	}
	
	 
	@Test
	public void l_deletinguser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		sleep(2);
		driver.findElement(By.tagName("input")).sendKeys("ajay");
		List<WebElement> li = driver.findElement(By.tagName("tbody")).findElements(By.tagName("a"));
		System.out.println(li.size());
		int i = random(li.size());
		sleep(1);
		li.get(i).click();
		System.out.println("user deleted successfully");
		
	}  
	
	@Test
	public void m_sort() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		sleep(2);
		sortAscend();
		sortDescend();
		
	}
	
	@Test
	public void n_pagenavigation() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("deleteUser")).click();
		pagination();
	}
	
	@Test
	public void o_showEntries() throws Exception{
		//=======verification of showentries dropdown ========
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		showEntries();
	}  
	@Test
	public void p_searchUser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		searchUser();
	}	
	@Test 
	public void q_sortedUser() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		sortAscend();
		sleep(1);
		sortDescend();
	}
	@Test
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
	@Test
	public void s_pageNavigation() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("listAllUsers")).click();
		sleep(2);
		pagination();
	}
	
	@Test
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
	@Test
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
	@Test
	public void v_viewConfig() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.id("viewConfiguration")).click();
		sleep(1);
		help.screenshot("viewconfig");
	}
	@Test
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
	
	@Test
	public void x_myaccount() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.linkText("Change Password")).click();
		sleep(2);
		if(driver.getCurrentUrl().equalsIgnoreCase("http://192.168.50.32:8080/leadcrm/changepassword.jsp")){
			System.out.println("The change password page is successfullyloaded");
		}
	}
	
	//@Test
	public void y_validChangePassword() throws Exception{
		mylogin();
		expand();
		driver.findElement(By.linkText("Change Password")).click();
		sleep(2);
		driver.findElement(By.id("oldPassword")).clear();
		driver.findElement(By.id("oldPassword")).sendKeys("abcd");
		driver.findElement(By.id("newPassword")).sendKeys("password");
		driver.findElement(By.id("confirmPassword")).sendKeys("password");
		driver.findElement(By.id("change")).click();
	}
}
 

  	
    
		  
	 
  

