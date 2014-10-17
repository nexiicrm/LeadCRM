package crm;



import testUtils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	//WebDriver driver ;
	@BeforeMethod
	public void bf() {
		 browser();
		 driver.get("http://192.168.50.32:8080/leadcrm/login.jsp");
		 maxbrowser();
		 
	}
	public void mylogin() throws Exception{
		login(config.getProperty("auname"), config.getProperty("apass"));	
	}
	@AfterMethod
	public void af(){
		driver.close();
	}
	
	
 @Test
  public void a_createbutton()throws Exception  {
	   /*======= Login verification =========*/
	 		mylogin();
	 		WebElement w =	driver.findElement(By.className("user_name"));
	 		if(w.getText().contains("Hi ! Admin srini")){
	 			System.out.println("Loggedin Successfully");
	 		}
	   /*==========Side Tree Expansion==========*/
	 	expand();
	 	List<WebElement> li=driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	 	System.out.println(li.size());
	 	for(int i =0;i<li.size();i++){
	 		System.out.println(li.get(i).getText());
	 	}
	 	System.out.println("The tree expanded successfully");
	 	
	 	
	  
	  /*======Clicking on Create button=======*/
	  
	   driver.findElement(By.id("createUser")).click();
	  sleep(2);
	  System.out.println("a_createbutton successful");
 }
 
 
 @Test
  public void b_dropDownVerification() throws Exception{
	  /*======'Manager' Drop down button presence and its working=====*/
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
	 /*======'Role' Drop down button presence and its working=====*/
	 List<WebElement> R = driver.findElement(By.name("role")).findElements(By.tagName("option"));
	    System.out.println(R.size());
	    for(int j=0;j<R.size();j++){
		  R.get(j).click();
	  }	
	    /*======Service dropdown presence and working =======*/
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
  		
 		/*======checking of '+' button working ========*/
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
	   /*========= Creation of New User ======*/
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
		   
	   /*======Clicking on Update button======= */
	 	mylogin();
	 	expand();
	    driver.findElement(By.id("updateUser")).click();
	   
	   /*====== Verifying the Show Entries Drop down ========  */ 
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
		  
		 sleep(3);
	   }
	   System.out.println("e_showEntriesDropDown() success");
 }
 
 
 
	 @Test
	 public void f_searchUsers() throws Exception{
		  /*====== Searching for a specific user ========*/  
		   mylogin();
		   expand();
		   driver.findElement(By.id("updateUser")).click();
		   sleep(1);
		   driver.findElement(By.tagName("input")).sendKeys("tulasi"); 
		   sleep(2);
		   List<WebElement> h = driver.findElement(By.tagName("tbody")).findElements(By.tagName("td"));
		   System.out.println(h.size());
		   int cnt = 0;
		   for(int x =0;x<h.size();x++){
			   System.out.println(h.get(x).getText());
			   if(h.get(x).getText().contains("tulasi")){
				   cnt++;
			   }
		  }
		   sleep(2);
		   System.out.println("serch success ");
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
	   //Actions ac1 = new Actions(driver);
      // ac1.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
       //driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	   System.out.println("g_updatingUser() is successs");
	 }
	
	
	 @Test
	 public void h_pageNavigation() throws Exception{
     /*==========Page Navigation ======== */
		 mylogin();
		 expand();
		 driver.findElement(By.id("updateUser")).click();
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
       
       System.out.println("h_pageNavigation() success");
  
  
 }
 
}
 

  	
    
		  
	 
  

