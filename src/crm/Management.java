package src.crm;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import src.testUtils.Helper;

public class Management extends Helper {
	
 @Test
  public void test1() throws Exception 
  {
	  //////////// logging into the site///////////////////////////
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
	  if(user != null){
	  help.sleep(2);
	  if(user.equals("Hi ! Management basani")){
	  System.out.println("User Logged in as:" + user);
	  }
	  }else
		  Assert.fail("logged in as other user");
	  List <WebElement> we  = driver.findElements(By.tagName(or.getProperty("user_tagname")));
	  if(we.size()>=0){
	  ArrayList <String> str = new ArrayList <String>();
	  for(int i=0;i<we.size();i++){  
		  str.add(we.get(i).getText());
	  }
	  System.out.println("Elements in side tree menu are: " + str);
	  }else
		  Assert.fail("Side tree menu is not available");
	  System.out.println("===========================================================================");
  }

 @Test
  public void test2() throws Exception
  {
	  //////////////// Expanding and collapsing tree in the left pane of page ////////////
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
	  help.collapse();
	  System.out.println("######Expansion and collapsing of tree menu done successfully######");
	  System.out.println("===========================================================================");
  }
 
 @Test
  public void test3() throws Exception
  {
	  /////////////// All Proposals click and search box ///////////////////////////////
   	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
      help.expand();
	  help.sleep(2);
	  String str = driver.findElement(By.id(or.getProperty("allproposals_id"))).getText();
	  if(str.equals("All Proposals")){
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
	  }else
		  Assert.fail("All propoals cant be clickable");
	  help.sleep(1);
	  pagination();
	  help.sleep(2);
	  
	  ///////////////////////// For drop down /////////////////////////
	  List<WebElement> ele = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  if(ele.size()>=0){
	  System.out.println("size of all proposals dropdown container: " + ele.size()); 
	  for (int i=0;i<ele.size();i++)
	  {
		  ele.get(i).click();
		  help.sleep(1);  
	  }
	  }else
		 Assert.fail("Dropdown container doesnt have any elements");
	  //////////////////////// for search box 
	  search("Aaron");
	  System.out.println("###### Done with search validation and dropdown validation of all proposals ######"); 
  }
 
 @Test
  public void test4() throws Exception
  {
   	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
	  String str = driver.findElement(By.id(or.getProperty("allproposals_id"))).getText();
	  if(str.equals("All Proposals")){
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
	  }else
		  Assert.fail("All propoals cant be clickable");
	  search("Aaron");
	  help.sleep(2);
	  List<WebElement> ls =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  if(ls.size()>=0){
	  System.out.println("No of columns in all proposal list page are :" + ls.size());
	  }else
		  Assert.fail("there are no columns in all proposals list page");
	  ArrayList<String> ar = new ArrayList<String>();
	  if(ls.size()>=0){
	  for (int i=0;i<ls.size();i++)
	  {
		 String s1= ls.get(i).getText();
		 ar.add(s1);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + ar);
	  }else
		  Assert.fail("No elements in array container");
	  // clicking track it
	  String str22 = driver.findElement(By.className(or.getProperty("allproposals_className"))).getText();
      if(str22.equals("Track It")){
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click(); 
      }else
    	  Assert.fail("Trackit cannot be clickable");
      help.sleep(2);
	  List<WebElement> ls2 =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all proposals " + ls2.size());
	  ArrayList<String> ar1 = new ArrayList<String>();
	  if(ls2.size()>=0){
	  for (int i=0;i<ls2.size();i++)
	  {
		 List<WebElement> s1= ls2.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 if(s1.size()>=0){
		 for(int j=0;j<s1.size();j++){
	     // System.out.println(s1.get(j).getText());
	     String s2 = s1.get(j).getText();
		 ar1.add(s2); 
		 } 
	  }	
	  }
	  }else
		  Assert.fail("there are no rows in trackit lead details");
	  // System.out.println(ar1);
	  System.out.println("array size after trackit button is clicked:" + ar1.size());
	  System.out.println("array element after trackit button is clicked: " + ar1.get(0));
	  System.out.println("array element after trackit button is clicked: " + ar1.get(1));
	  System.out.println("array element after trackit button is clicked: " + ar1.get(19));
	  if(ar1.get(0).contains(ar.get(0)))
	  {
		 if(ar1.get(1).contains(ar.get(1)))
		 {
			 if(ar1.get(19).contains(ar.get(4)))
			 {	 	 
		       System.out.println("Data is matching exactly in all proposals");
	        }
	    }
	 }else
	 Assert.fail("Data doesnt match in all proposals");
	  ////////////////// to get the status
	 String strt=  driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(1).getText();
	 if(strt != null){
	 System.out.println(strt);
	 help.sleep(2);
	 }else
		 Assert.fail();
	 String strtt = driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(2).getText();
	 System.out.println(strtt);	
	 System.out.println("tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	 String s1 = driver.findElement(By.tagName("a")).getTagName();
	 if(s1.contentEquals("a")){
		 System.out.println("File can be downloaded");
	 }else
	 Assert.fail("File cannot be downloaded");
	 System.out.println("######Done with validation of all proposals page######");
	 System.out.println("==============================================================================");
	  
  }
 
 @Test
  public void test5() throws Exception
  {
 	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
       /////////////// All lost competition ///////////////////////////////
	  if(driver.findElement(By.id(or.getProperty("alllost_id"))).getText().equals("All Lost Competition")){
	  driver.findElement(By.id(or.getProperty("alllost_id"))).click();
	  }else
		  Assert.fail("all lost comepetition button is not available");
	  help.sleep(1);
	  pagination();
	   ///////////// drop down validation
	  List<WebElement> ele = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all lost competition dropdown container: " + ele.size());
	  if(ele.size()>=0){
	  for (int j=0;j<ele.size();j++)
	  {
		  ele.get(j).click();
		  help.sleep(1);
	  }
	  }else
		  Assert.fail("no elements found in  dropdown container");
	    /////////// search box validation
	  search("medina");
	    ////////////// validation and comapring track it options
	 List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all lost competition page are: " + lsc.size());
	  ArrayList<String> arc = new ArrayList<String>();
	  if(lsc.size()>=0){
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
	  }else
		  Assert.fail("There are no elements in the array of lost competition");
	                // clicking track it
	  String str22 = driver.findElement(By.className(or.getProperty("allproposals_className"))).getText();
      if(str22.equals("Track It")){
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click(); 
      }else
    	  Assert.fail("Trackit cannot be clickable");
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all lost competition " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ls2c.size()>=0){
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 if(s1c.size()>=0){
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }}
	  }else
		  Assert.fail("no elements in container of all lost competition");
	  System.out.println("Array size after trackit button is clicked: " + ar1c.size());
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(0));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(1));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1))){
			 if(ar1c.get(19).contains(arc.get(4))){	 
		 System.out.println("Data is matching exactly in all lost competition");
	  }
	  }
	  }else
	  System.out.println("Data doesnt match in all lost competition");
	    ///////////////// to get status
	  String strt=  driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(1).getText();
	  System.out.println(strt);
	  String strtt = driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(2).getText();
	  System.out.println(strtt);	
	  System.out.println("tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	  String s1 = driver.findElement(By.tagName("a")).getTagName();
	  if(s1.contentEquals("a")){
			 System.out.println("File can be downloaded");
		 }else
		 Assert.fail("File cannot be downloaded");
	  System.out.println("######Done with validation of all lost competation page######");
	  System.out.println("==============================================================================");
  }

 @Test
  public void test6() throws Exception
  {
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
     	  /////////////// All customers //////////////////////////
	  if(driver.findElement(By.id(or.getProperty("allcustomers_id"))).getText().equals("All Customers")){
	  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
	  }else
		  Assert.fail("all customers link cannot be clickable");
	  help.sleep(1);
	  pagination();
	    //////////////////////// dropdown validation
	  List<WebElement> ele2 = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all customers dropdown container: " + ele2.size());
	  if(ele2.size()>=0){
	  for (int j=0;j<ele2.size();j++)
	  {
		  ele2.get(j).click();
		  help.sleep(1);
		  }
	  }else
		  Assert.fail("dropdown container donot hold any elements");
	      /////////// serach fields validation
	  search("sara");
      System.out.println("###### done with the validation of search and dropdown of all customers ######"); 
  }
	 
 @Test
  public void test7() throws Exception
  {
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
	  help.sleep(2);
	  if(driver.findElement(By.id(or.getProperty("allcustomers_id"))).getText().equals("All Customers")){
		  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
		  }else
			  Assert.fail("all customers link cannot be clickable");
	  search("sara");
	  List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all customers page are: " + lsc.size());
   	  ArrayList<String> arc = new ArrayList<String>();
   	  if(lsc.size()>=0){
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
   	  }else
   		  Assert.fail("There are no elements in the array of all customers");
   	String str22 = driver.findElement(By.className(or.getProperty("allproposals_className"))).getText();
    if(str22.equals("Track It")){
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click(); 
    }else
  	  Assert.fail("Trackit cannot be clickable");
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all customers " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ar1c.size()>=0){
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		if(s1c.size()>=0){
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }}
	  }else
		  Assert.fail("no elements in the table of trackit of all customers");
	  System.out.println("Array size after trackit button is clicked: " + ar1c.size());
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(0));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(1));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1))){
			 if(ar1c.get(19).contains(arc.get(4))){	 
		 System.out.println("Data is matching exactly in all customers ");
	  }
	  }
	  }else
	  System.out.println("Data doesnt match in all customers");
	      ////////////// to get the status
	  String strt=  driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(1).getText();
	  System.out.println(strt);
	  String strtt = driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(2).getText();
	  System.out.println(strtt);	
	  System.out.println("tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	  String s1 = driver.findElement(By.tagName("a")).getTagName();
	  if(s1.contentEquals("a")){
			 System.out.println("File can be downloaded");
		 }else
		 Assert.fail("File cannot be downloaded");
	  System.out.println("######Done with validation of all customers page######");
	  System.out.println("==============================================================================");
  }
  
 @Test
  public void test8() throws Exception
  {
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
        /////////////// All Quotes //////////////////////////
	  if(driver.findElement(By.id(or.getProperty("allquotes_id"))).getText().equals("All Quotes")){
	  driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
	  }else 
		  Assert.fail("all quotes link cannot be clickable");
	  help.sleep(1);
	  pagination();
        /////////////// drop down validation
      List<WebElement> ele3 = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
      System.out.println("size of all quotes dropdown container: " + ele3.size());
      if(ele3.size()>=0){
      for (int j=0;j<ele3.size();j++)
      {
    	  ele3.get(j).click();
    	  help.sleep(1);
      }
      }else
    	  Assert.fail("there are no elements in dropdown container");
        ///////////// search fields validation 
      search("karen");
      System.out.println("###### done with the validation of search and dropdown of all quotes ######"); 
  }
 
 @Test
  public void test9() throws Exception
  {
	  help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	  help.expand();
      help.sleep(2);
      if(driver.findElement(By.id(or.getProperty("allquotes_id"))).getText().equals("All Quotes")){
    	  driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
    	  }else 
    		  Assert.fail("all quotes link cannot be clickable");
      search("karen");
      List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all quotes page are: " + lsc.size());
      ArrayList<String> arc = new ArrayList<String>();
      if(lsc.size()>=0){
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
      }else
      Assert.fail("there are no elements in the array before trackit is clicked");
	  sleep(1);
		String str22 = driver.findElement(By.className(or.getProperty("allproposals_className"))).getText();
	    if(str22.equals("Track It")){
		  driver.findElement(By.className(or.getProperty("allproposals_className"))).click(); 
	    }else
	  	  Assert.fail("Trackit cannot be clickable");
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all quotes " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ls2c.size()>=0){
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 if(s1c.size()>=0){
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }}
      }else 
	  Assert.fail("there are no elements in the track it table");
	  System.out.println("Array size after trackit button is clicked: " + ar1c.size());
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(0));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(1));
	  System.out.println("array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1))){
			 if(ar1c.get(19).contains(arc.get(4))){	 
		 System.out.println("Data is matching exactly in all customers ");
	  }
	  }
	  }else
	 System.out.println("Data doesnt match in all quotes");
	  //////////// to get the status
	 String strt=  driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(1).getText();
	 System.out.println(strt);
	 String strtt = driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(2).getText();
	 System.out.println(strtt);	
	 System.out.println("tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	 String s1 = driver.findElement(By.tagName("a")).getTagName();
	 if(s1.contentEquals("a")){
		 System.out.println("File can be downloaded");
	 }else
	 Assert.fail("File cannot be downloaded");
     System.out.println("######Done with validation of all quotes page######");
	 System.out.println("==============================================================================");
  }
 
@Test
  public void testLeads() throws Exception
  {
	help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	 
  } 	

 @Test
  public void testPassword() throws Exception
  {
	 help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	 help.expand();
        /////////////////////////// change password ////////////////////////////////////
     driver.findElement(By.xpath(or.getProperty("change_xpath"))).click();
     driver.findElement(By.id(or.getProperty("changeold_id"))).sendKeys(sh5.getCell(1,0).getContents());
     driver.findElement(By.id(or.getProperty("changenew_id"))).sendKeys(sh5.getCell(0,4).getContents());
     driver.findElement(By.id(or.getProperty("changeconfirm_id"))).sendKeys(sh5.getCell(0,4).getContents());
     help.sleep(2);
  // driver.findElement(By.id(or.getProperty("change_id"))).submit();
     System.out.println("######Done with validation of change password page######");
	 System.out.println("==============================================================================");
  }
  
     // verifing is element present to click or not
 
          // pagination
 public static void pagination() 
 {
	 System.out.println("###### Verifying Next & Previous Buttons ######");
	 WebElement w= driver.findElement(By.id(or.getProperty("pagination_next")));
	 if(w.isDisplayed()){
		 System.out.println("pagination next button is present");
	 }else
		 Assert.fail("pagination button not present");
	 String s1 =driver.findElement(By.id(or.getProperty("page1_id"))).getText();
	 System.out.println(s1);
	 while(!w.getAttribute("class").contains("disabled")){
	 help.sleep(2);
	 String str = driver.findElement(By.id(or.getProperty("page2_id"))).getAttribute("class");
	 
	 if(str.contains("enabled")){
	 w.click();
	 System.out.println("Clicked on Next button");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
	 help.sleep(1);
	 }else
		 System.out.println("Next button cant be clicked");
	 }
	 WebElement w1 =  driver.findElement(By.id(or.getProperty("pagiantion_prev")));
	 if(w1.isDisplayed()){
		System.out.println("pagination previous button present");
	 }else
		Assert.fail("pagination previous button not present");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
	 while(!w1.getAttribute("class").contains("disabled")){
	 String str2 = driver.findElement(By.id(or.getProperty("page3_id"))).getAttribute("class");
	 
	 if(str2.contains("enabled")){
	 w1.click();
	 System.out.println("Clicked on Previous button");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
	 }else
		 System.out.println("previous button cant be clicked");
	 }
 }
  
          //No. of Entries per page
 public static void pageEntries()
 {
		//Selecting no.of entries for the table
   driver.findElement(By.id(or.getProperty("allproposals_name"))).click();
   List <WebElement> entries = driver.findElement(By.id(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
   help.sleep(4);
   int opt = help.random(entries.size());
   entries.get(opt).click();
   System.out.println("No.of Entries selected for the page:" + entries.get(opt).getText());
 } 
	 
     	 //Method for validating Search box
 public static void search(String keyword) 
  {
    driver.findElement(By.id(or.getProperty("searching_id"))).findElement(By.tagName(or.getProperty("allproposals_tagName"))).sendKeys(keyword);
  }

 @BeforeMethod
  public void beforeMethod() throws Exception 
 {
    help.browser();
	help.maxbrowser();
	driver.get(config.getProperty("url"));	  
    help.browsererror();  
  }

 @AfterMethod
 public void afterMethod() 
 {
	driver.quit();
  }
 }





