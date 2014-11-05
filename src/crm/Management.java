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
String randomLead;
String Leadno;

 @Test
  public void test57_TC001() throws Exception 
  {
	  //////////// logging into the site///////////////////////////
	  String user = driver.findElement(By.className(mgmt.getProperty("user_Classname"))).getText();
	  if(user != null)
	  {
	  help.sleep(2);
	  if(user.contains("Hi ! Management"))
	  {
		  Reporter.log("<p>" + "User Logged in as:" + user);
	  }
	  }else{
		  Assert.fail("logged in as other user");
	  }
	   help.sidetreemenuverify(4);
	  Reporter.log("<p>" + "===========================================================================");
  }

 @Test
  public void test57_TC002() throws Exception
  {
	  //////////////// Expanding and collapsing tree in the left pane of page ////////////
	  help.expand();
	  help.collapse();
	  Reporter.log("<p>" + "######Expansion and collapsing of tree menu done successfully######");
	  Reporter.log("<p>" + "===========================================================================");
  }
 
 @Test
  public void test58_TC001() throws Exception
  {
	  /////////////// All Proposals click and search box ///////////////////////////////
      help.expand();
	  help.sleep(2);
	  String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
	  if(str.equals("All Proposals"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
	  }else{
		  Assert.fail("All propoals cant be clickable");
	  }
	  help.sleep(2);
	  ///////////////////////// For drop down and pagination /////////////////////////
	  help.pageEntries();
	 // pagination();
	  //////////////////////// for sorting//////////////////
	  help.sorting();
	///////////////////////// lead status ////////////////////////
	  String page = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries before searching  : " + page);
	  search("prospect"); 
	  String pagecheck = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries after searching : " +pagecheck);
	  if(page.equals(pagecheck))
	  {
		  Reporter.log("<p>" + "entries doesnt change the status is prospect");
	  }else{
		  Assert.fail("entries are changing the status is not prospect in all leads");
	  }
	  Reporter.log("<p>" + "###### Done with dropdown validation, lead status validation of all proposals ######"); 
  }

 @Test
  public void test58_TC006() throws Exception
  {
	  help.expand();
	  String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
	  if(str.equals("All Proposals"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
	  }else{
		  Assert.fail("All propoals cant be clickable");
	  }
	  /////////////// searchbox validation and picking a lead///////////////////////
	  searchBox();
	  List<WebElement> ls =driver.findElement(By.cssSelector(mgmt.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
	  if(ls.size()>=0)
	  {
		  Reporter.log("<p>" + "No of columns in all proposal list page are :" + ls.size());
	  }else{
		  Assert.fail("there are no columns in all proposals list page");
	  }
	  ArrayList<String> ar = new ArrayList<String>();
	  if(ls.size()>=0)
	  {
	  for (int i=0;i<ls.size();i++)
	  {
		 String s1= ls.get(i).getText();
		 ar.add(s1);  
	  }
	  Reporter.log("<p>" + "Array before clicking on trackit button is: " + ar);
	  }else{
		  Assert.fail("No elements in array container");
	  }
	  // clicking track it
	  String str22 = driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).getText();
      if(str22.equals("Track It"))
      {
	  driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).click(); 
      }else{
    	  Assert.fail("Trackit cannot be clickable");
      }
      help.sleep(2);
	  List<WebElement> ls2 =driver.findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
	  Reporter.log("<p>" + "No of rows in track it lead details of all proposals " + ls2.size());
	  ArrayList<String> ar1 = new ArrayList<String>();
	  if(ls2.size()>=0)
	  {
	  for (int i=0;i<ls2.size();i++)
	  {
		 List<WebElement> s1= ls2.get(i).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		 if(s1.size()>=0)
		 {
		 for(int j=0;j<s1.size();j++)
		 {
	     // System.out.println(s1.get(j).getText());
	     String s2 = s1.get(j).getText();
		 ar1.add(s2); 
		 } 
	     }	
	  }
	  }else{
		  Assert.fail("there are no rows in trackit lead details");
	  }
	  // System.out.println(ar1);
	  Reporter.log("<p>" + "array size after trackit button is clicked:" + ar1.size());
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(0));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(1));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(19));
	  if(ar1.get(0).contains(ar.get(0)))
	  {
		 if(ar1.get(1).contains(ar.get(1)))
		 {
			 if(ar1.get(19).contains(ar.get(4)))
			 {	 	 
				 Reporter.log("<p>" + "Data is matching exactly in all proposals");
	         }
	     }
	  }else{
	 Assert.fail("Data doesnt match in all proposals");
	  }
	  ////////////////// to get the status///////////////////////////
	 String strt=  driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(1).getText();
	 if(strt != null) 
	 {
		 Reporter.log("<p>" + strt);
	 help.sleep(2);
	 }else{
		 Assert.fail();
	 }
	 String strtt = driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(2).getText();
	 Reporter.log("<p>" + strtt);	
	 Reporter.log("<p>" + "tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	 String s1 = driver.findElement(By.tagName("a")).getTagName();
	 if(s1.contentEquals("a"))
	 {
		 Reporter.log("<p>" + "File can be downloaded");
	 }else{
	 Assert.fail("File cannot be downloaded");
	 }
	 Reporter.log("<p>" + "######Done with validation of all proposals page######");
	 Reporter.log("<p>" + "==============================================================================");  
  }

 @Test
  public void test59_TC001() throws Exception
  {
	  help.expand();
       /////////////// All lost competition ///////////////////////////////
	  if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
	  }else{
		  Assert.fail("all lost comepetition button is not available");
	  }
	  help.sleep(1);
	   ///////////// drop down validation and pagination/////////
	  help.pageEntries();
	  /////////////////////// for sorting///////////////
	  help.sorting();
	  //////////////////// lead status validation ////////////////////////
	  String page = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries before searching  : " + page);
	  search("Lost Competition"); 
	  String pagecheck = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries after searching : " +pagecheck);
	  if(page.equals(pagecheck))
	  {
		  Reporter.log("<p>" + "entries doesnt change, the status is Lost Competition");
	  }else{
		  Assert.fail("entries are changing, the status is not Lost Competition");
	  }
	  Reporter.log("<p>" + "###### Done with dropdown validation, lead status validation of all lost competition ######"); 
 }
 
 @Test
 public void test59_TC006() throws Exception
 {
	  help.expand();
	  if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
	  }else{
		  Assert.fail("all lost comepetition button is not available");
	  }
	  help.sleep(1);   
	 /////////// search box validation
	  searchBox();
	    ////////////// validation and comapring track it options
	 List<WebElement> lsc =driver.findElement(By.cssSelector(mgmt.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
	 Reporter.log("<p>" + "No of columns in all lost competition page are: " + lsc.size());
	  ArrayList<String> arc = new ArrayList<String>();
	  if(lsc.size()>=0)
	  {
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  Reporter.log("<p>" + "Array before clicking on trackit button is: " + arc);
	  }else{
		  Assert.fail("There are no elements in the array of lost competition");
	  }
	                // clicking track it/////////////////////
	  String str22 = driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).getText();
      if(str22.equals("Track It"))
      {
	  driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).click(); 
      }else{
    	  Assert.fail("Trackit cannot be clickable");
      }
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
	  Reporter.log("<p>" + "No of rows in track it lead details of all lost competition " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ls2c.size()>=0)
	  {
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		 if(s1c.size()>=0)
		 {
		 for(int j=0;j<s1c.size();j++)
		 {
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
    	 }  
      }
	  }else{
		  Assert.fail("no elements in container of all lost competition");
	  }
	  Reporter.log("<p>" + "Array size after trackit button is clicked: " + ar1c.size());
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(0));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(1));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1)))
		 {
			 if(ar1c.get(19).contains(arc.get(4)))
			 {	 
				 Reporter.log("<p>" + "Data is matching exactly in all lost competition");
	         }
	     }
	  }else{
		  Reporter.log("<p>" + "Data doesnt match in all lost competition");
	  }
	    ///////////////// to get status
	  String strt=  driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(1).getText();
	  Reporter.log("<p>" + strt);
	  String strtt = driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(2).getText();
	  Reporter.log("<p>" + strtt);	
	  Reporter.log("<p>" + "tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	  String s1 = driver.findElement(By.tagName("a")).getTagName();
	  if(s1.contentEquals("a"))
	    {
		  Reporter.log("<p>" + "File can be downloaded");
		 }else{
		 Assert.fail("File cannot be downloaded");
		 }
	  Reporter.log("<p>" + "######Done with validation of all lost competation page######");
	  Reporter.log("<p>" + "==============================================================================");
  }

 @Test
  public void test60_TC001() throws Exception
  {
	  help.expand();
     	  /////////////// All customers //////////////////////////
	  if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
	  }else{
		  Assert.fail("all customers link cannot be clickable");
	  }
	  help.sleep(1);
	    //////////////////////// dropdown validation and pagination ////////////////////
	  help.pageEntries();
	  /////////////////////////////// for sorting ///////////////////////
	  help.sorting();
         ////////////////////lead status validation ////////////////////////
	  String page = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries before searching  : " + page);
	  search("Customer"); 
	  String pagecheck = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries after searching : " +pagecheck);
	  if(page.equals(pagecheck))
	    {
		  Reporter.log("<p>" + "entries doesnt change, the status is Customer");
		  }else{
			  Assert.fail("entries are changing, the status is not Customer");
		  }
	      
	  Reporter.log("<p>" + "###### done with the validation of search and dropdown of all customers ######"); 
  }
	 
 @Test
  public void test60_TC006() throws Exception
  {
	  help.expand();
	  help.sleep(2);
	  if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
	  {
	     driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
	   }else{
		  Assert.fail("all customers link cannot be clickable");
		  }
	  searchBox();
	  List<WebElement> lsc =driver.findElement(By.cssSelector(mgmt.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
	  Reporter.log("<p>" + "No of columns in all customers page are: " + lsc.size());
   	  ArrayList<String> arc = new ArrayList<String>();
   	  if(lsc.size()>=0)
   	  {
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  Reporter.log("<p>" + "Array before clicking on trackit button is: " + arc);
   	  }else{
   		  Assert.fail("There are no elements in the array of all customers");
   	  }
   	String str22 = driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).getText();
    if(str22.equals("Track It"))
    {
	  driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).click(); 
    }else{
  	  Assert.fail("Trackit cannot be clickable");
    }
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
	  Reporter.log("<p>" + "No of rows in track it lead details of all customers " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ar1c.size()>=0)
	  {
	  for (int i=0;i<ls2c.size();i++)
	  {
	    List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
	     if(s1c.size()>=0)
	     {
		 for(int j=0;j<s1c.size();j++)
		 {
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	     }
	   }
	  }else{
		  Assert.fail("no elements in the table of trackit of all customers");
	  }
	  Reporter.log("<p>" + "Array size after trackit button is clicked: " + ar1c.size());
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(0));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(1));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1)))
		 {
			 if(ar1c.get(19).contains(arc.get(4)))
			 {	 
				 Reporter.log("<p>" + "Data is matching exactly in all customers ");
	         }
	      }
	  }else{
		  Reporter.log("<p>" + "Data doesnt match in all customers");
	  }
	      ////////////// to get the status
	  String strt=  driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(1).getText();
	  Reporter.log("<p>" + strt);
	  String strtt = driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(2).getText();
	  Reporter.log("<p>" + strtt);	
	  Reporter.log("<p>" + "tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	  String s1 = driver.findElement(By.tagName("a")).getTagName();
	  if(s1.contentEquals("a"))
	  {
		 Reporter.log("<p>" + "File can be downloaded");
	   }else{
		 Assert.fail("File cannot be downloaded");
		 }
	  Reporter.log("<p>" + "######Done with validation of all customers page######");
	  Reporter.log("<p>" + "==============================================================================");
  }
 
 @Test
  public void test61_TC001() throws Exception
  {
	  help.expand();
        /////////////// All Quotes //////////////////////////
	  if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
	  {
	  driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
	  }else {
		  Assert.fail("all quotes link cannot be clickable");
	  }
	  help.sleep(1);
        /////////////// drop down validation and pagination/////////////
	  help.pageEntries();
	  ////////////////// for sorting/////////////////
	  help.sorting();
	  //////////////////////////// prospect type
	  String page = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries before searching  : " + page);
	  search("Quote"); 
	  String pagecheck = driver.findElement(By.id("example_info")).getText();
	  Reporter.log("<p>" + "entries after searching : " +pagecheck);
	  if(page.equals(pagecheck))
	  {
		Reporter.log("<p>" + "entries doesnt change, the prospect type is Quote");
	  }else{
			  Assert.fail("entries are changing, the prospect type is not Quote");
		  }
	      
      Reporter.log("<p>" + "###### done with the validation of search and dropdown of all quotes ######"); 
  }

 @Test
  public void test61_TC006() throws Exception
  {
	  help.expand();
      help.sleep(2);
      if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
      {
    	  driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
      }else {
    		  Assert.fail("all quotes link cannot be clickable");
      }
      help.sleep(2);
      searchBox();
      List<WebElement> lsc =driver.findElement(By.cssSelector(mgmt.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
      Reporter.log("<p>" + "No of columns in all quotes page are: " + lsc.size());
      ArrayList<String> arc = new ArrayList<String>();
      if(lsc.size()>=0)
      {
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  Reporter.log("<p>" + "Array before clicking on trackit button is: " + arc);
      }else{
      Assert.fail("there are no elements in the array before trackit is clicked");
      }
	  sleep(1);
	  String str22 = driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).getText();
	  if(str22.equals("Track It")){
	  driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).click(); 
	  }else{
	  	  Assert.fail("Trackit cannot be clickable");
	  }
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
	  Reporter.log("<p>" + "No of rows in track it lead details of all quotes " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  if(ls2c.size()>=0)
	  {
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		 if(s1c.size()>=0)
		 {
		 for(int j=0;j<s1c.size();j++)
		 {
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	     }
	  }
      }else {
	  Assert.fail("there are no elements in the track it table");
	  Reporter.log("<p>" + "Array size after trackit button is clicked: " + ar1c.size());
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(0));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(1));
	  Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1c.get(19));
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1))){
			 if(ar1c.get(19).contains(arc.get(4))){	 
				 Reporter.log("<p>" + "Data is matching exactly in all customers ");
	  }
	  }
	  }else
		  Reporter.log("<p>" + "Data doesnt match in all quotes");
      }
	  //////////// to get the status
	 String strt=  driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(1).getText();
	 Reporter.log("<p>" + strt);
	 String strtt = driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(2).getText();
	 Reporter.log("<p>" + strtt);	
	 Reporter.log("<p>" + "tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
	 String s1 = driver.findElement(By.tagName("a")).getTagName();
	 if(s1.contentEquals("a")){
		 Reporter.log("<p>" + "File can be downloaded");
	 }else
	 Assert.fail("File cannot be downloaded");
	 Reporter.log("<p>" + "######Done with validation of all quotes page######");
	 Reporter.log("<p>" + "==============================================================================");
  }
 
@Test
  public void test62_TC001() throws Exception
  {
	help.expand();
	help.searchLead();
	//help.searchLeadPagination();
	 Reporter.log("<p>" + "######Done with validation of testleads page######");
	 Reporter.log("<p>" + "==============================================================================");
  } 	

// @Test
  public void test65_TC001() throws Exception
  {
	 help.expand();
        /////////////////////////// change password ////////////////////////////////////
   
	 Reporter.log("<p>" + "######Done with validation of change password page######");
	 Reporter.log("<p>" + "==============================================================================");
  }
	 
     	 //Method for validating Search box
 public static void search(String keyword) 
  {
    driver.findElement(By.id(mgmt.getProperty("searching_id"))).findElement(By.tagName(mgmt.getProperty("allproposals_tagName"))).sendKeys(keyword);
  }
 public void searchBox()
 {	
	 List<WebElement> table = driver.findElement(By.id(mgmt.getProperty("search_idd"))).findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
	 if(table.size()>0)
	 {	
	 WebElement res = table.get(random(table.size()));
	 List<WebElement> tdlis = res.findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		    if(tdlis.size()>0)
		    {
		    	randomLead = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
		    	Reporter.log("<p>" + "The particular Lead is : " + randomLead);
		    	search(randomLead);
		    	help.sleep(2);
		    }else{
		    	Assert.fail("there are no elements to pick lead id and name");
	 		}
	 	}else{
	 			Assert.fail("There is no data present in the table");
	 	}
 }
 
 // pagination
public  void pagination() 
{
Reporter.log("<p>" + "###### Verifying Next & Previous Buttons ######");
WebElement w= driver.findElement(By.id(or.getProperty("page2_id")));
if(w.isDisplayed()){
Reporter.log("<p>" + "pagination next button is present");
}else
Assert.fail("pagination button not present");
String s1 =driver.findElement(By.id(or.getProperty("page1_id"))).getText();
Reporter.log("<p>" + s1);
while(!w.getAttribute("class").contains("disabled")){
help.sleep(2);
String str = driver.findElement(By.id(or.getProperty("page2_id"))).getAttribute("class");

if(str.contains("enabled")){
w.click();
Reporter.log("<p>" + "Clicked on Next button");
Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("page1_id"))).getText());
help.sleep(1);
}else
Reporter.log("<p>" + "Next button cant be clicked");
}
WebElement w1 =  driver.findElement(By.id(or.getProperty("pagiantion_prev")));
if(w1.isDisplayed()){
Reporter.log("<p>" + "pagination previous button present");
}else
Assert.fail("pagination previous button not present");
Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("page1_id"))).getText());
while(!w1.getAttribute("class").contains("disabled")){
String str2 = driver.findElement(By.id(or.getProperty("page3_id"))).getAttribute("class");

if(str2.contains("enabled")){
w1.click();
Reporter.log("<p>" + "Clicked on Previous button");
Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("page1_id"))).getText());
}else
Reporter.log("<p>" + "previous button cant be clicked");
}
}

 @BeforeMethod
  public void beforeMethod() throws Exception 
 {
    help.browser();
	help.maxbrowser();
	driver.get(config.getProperty("url"));	  
    help.browsererror();  
    help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
  }

 @AfterMethod
 public void afterMethod() 
 {
	driver.quit();
  }
 }





