package crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testUtils.Helper;


public class Management extends Helper {
	
 @Test
  public void test1() throws Exception 
  {
	  //////////// logging into the site///////////////////////////
	  help.login(sh5.getCell(0,0).getContents(), sh5.getCell(1,0).getContents());
	  String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
	  System.out.println("User Logged in as:" + user);
	  List <WebElement> we  = driver.findElements(By.tagName(or.getProperty("user_tagname")));
	  ArrayList <String> str = new ArrayList <String>();
	  for(int i=0;i<we.size();i++){  
		  str.add(we.get(i).getText());
		  //System.out.println(str.get(i));
	  }
	  System.out.println(str);
	/*  if(sh5.getCell(1,1).getContents().contains(str.get(0)))
	  {
		 if(sh5.getCell(1,2).getContents().contains(str.get(1)))
		 {
			 if(sh5.getCell(1,3).getContents().contains(str.get(2)))
			 {	 
				 if(sh5.getCell(1,4).getContents().contains(str.get(3)))
				 {
					 if(sh5.getCell(1,5).getContents().contains(str.get(4))){
				 System.out.println("######logged in successfully into management role######");
	        }
	    }
	 }
	 }
	 }else
	  Assert.fail("Logged in as other user except management");
	  */
	  System.out.println("===========================================================================");
  }

 @Test
  public void test2() throws Exception
  {
	  //////////////// Expanding and collapsing tree in the left pane of page ////////////
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
	  help.collapse();
	  System.out.println("######Expansion and collapsing of tree menu done successfully######");
	  System.out.println("===========================================================================");
  }

 @Test
  public void test3() throws Exception
  {
	  /////////////// All Proposals click and search box ///////////////////////////////
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
	  help.sleep(2);
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
	  help.sleep(1);
	  pagination();
	  
	  ///////////////////////// For drop down /////////////////////////
	  List<WebElement> ele = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all proposals dropdown container: " + ele.size()); 
	  for (int i=0;i<ele.size();i++)
	  {
		  ele.get(i).click();
		  help.sleep(1);  
	  }
	  
	  //////////////////////// for search box 
	  search("sreekar");
	  System.out.println("###### Done with search validation and dropdown validation of all proposals ######"); 
  }
 
 @Test
  public void test4() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
	  search("sreekar");
	  help.sleep(2);
	  List<WebElement> ls =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all proposal list page are :" + ls.size());
	  ArrayList<String> ar = new ArrayList<String>();
	  for (int i=0;i<ls.size();i++)
	  {
		 String s1= ls.get(i).getText();
		 ar.add(s1);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + ar);
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click(); // clicking track it
	  help.sleep(2);
	  List<WebElement> ls2 =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all proposals " + ls2.size());
	  ArrayList<String> ar1 = new ArrayList<String>();
	  for (int i=0;i<ls2.size();i++)
	  {
		 List<WebElement> s1= ls2.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 for(int j=0;j<s1.size();j++){
	     // System.out.println(s1.get(j).getText());
	     String s2 = s1.get(j).getText();
		 ar1.add(s2); 
		 } 
	  }	  
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
	 System.out.println("Data doesnt match in all proposals");
	  ////////////////// to get the status
	 String strt=  driver.findElements(By.tagName(or.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(or.getProperty("allproposalscol_tagName"))).get(1).getText();
	 System.out.println(strt);
	 System.out.println("######Done with validation of all proposals page######");
	 System.out.println("==============================================================================");
	  
  }
  
 @Test
  public void test5() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
       /////////////// All lost competition ///////////////////////////////
	  driver.findElement(By.id(or.getProperty("alllost_id"))).click();
	  help.sleep(1);
	  pagination();
	   ///////////// drop down validation
	  List<WebElement> ele = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all lost competition dropdown container: " + ele.size());
	  for (int j=0;j<ele.size();j++)
	  {
		  ele.get(j).click();
		  help.sleep(1);
	  }
	    /////////// search box validation
	  driver.findElement(By.tagName(or.getProperty("allproposals_tagName"))).click();
	  search("denni");
	    ////////////// validation and comapring track it options
	  List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all lost competition page are: " + lsc.size());
	  ArrayList<String> arc = new ArrayList<String>();
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all lost competition " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }
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
	  System.out.println("######Done with validation of all lost competation page######");
	  System.out.println("==============================================================================");
  }
  
 @Test
  public void test6() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
     	  /////////////// All customers //////////////////////////
	  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
	  help.sleep(1);
	  pagination();
	    //////////////////////// dropdown validation
	  List<WebElement> ele2 = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all customers dropdown container: " + ele2.size());
	  for (int j=0;j<ele2.size();j++)
	  {
		  ele2.get(j).click();
		  help.sleep(1);
		  }
	      /////////// serach fields validation
	  search("shiva");
      System.out.println("###### done with the validation of search and dropdown of all customers ######"); 
  }
	 
 @Test
  public void test7() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
	  help.sleep(2);
	  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
	  search("shiva");
	  List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all customers page are: " + lsc.size());
   	  ArrayList<String> arc = new ArrayList<String>();
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all customers " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }
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
	  System.out.println("######Done with validation of all customers page######");
	  System.out.println("==============================================================================");
  }
  
 @Test
  public void test8() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
        /////////////// All Quotes //////////////////////////
	  driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
	  help.sleep(1);
	  pagination();
        /////////////// drop down validation
      List<WebElement> ele3 = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
      System.out.println("size of all quotes dropdown container: " + ele3.size());
      for (int j=0;j<ele3.size();j++)
      {
    	  ele3.get(j).click();
    	  help.sleep(1);
      }
        ///////////// search fields validation 
      search("karen");
      System.out.println("###### done with the validation of search and dropdown of all quotes ######"); 
  }
 @Test
  public void test9() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
      help.sleep(2);
      driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
      search("karen");
      List<WebElement> lsc =driver.findElement(By.cssSelector(or.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in all quotes page are: " + lsc.size());
      ArrayList<String> arc = new ArrayList<String>();
	  for (int i=0;i<lsc.size();i++)
	  {
		 String s1c= lsc.get(i).getText();
		 arc.add(s1c);  
	  }
	  System.out.println("Array before clicking on trackit button is: " + arc);
	  sleep(1);
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow1_tagName")));
	  System.out.println("No of rows in track it lead details of all quotes " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  for (int i=0;i<ls2c.size();i++)
	  {
		 List<WebElement> s1c= ls2c.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 for(int j=0;j<s1c.size();j++){
      // System.out.println(s1.get(j).getText());
	     String s2c = s1c.get(j).getText();
		 ar1c.add(s2c); 
		 } 
	  }
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
     System.out.println("######Done with validation of all quotes page######");
	 System.out.println("==============================================================================");
  }
 
@Test
  public void testLeads() throws Exception
  {
	  help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
	  help.expand();
        ///////////////////////// Search leads //////////////////////////////////////////////
	  if(driver.findElement(By.id(or.getProperty("allleads_id"))).isDisplayed()) {
      driver.findElement(By.id(or.getProperty("allleads_id"))).click();
	  String parentWindow = driver.getWindowHandle();
	  for(String childWindow : driver.getWindowHandles()) 
	  {
	  driver.switchTo().window(childWindow);
	  }
			 //Selecting Required fields
	 help.sleep(2);
	 List <WebElement> requiredFields = driver.findElement(By.id(or.getProperty("allleadstab_id"))).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	 int a = help.random(requiredFields.size());
	 System.out.println("The reqiured field : " + requiredFields.get(a).getText());
	 requiredFields.get(a).findElement(By.tagName(or.getProperty("allproposals_tagName"))).click();
	 driver.findElement(By.cssSelector(or.getProperty("search_css"))).click();
			 //Selecting a Category of Filter Options
	 List <WebElement> filterOptions = driver.findElement(By.id(or.getProperty("search_id"))).findElements(By.className(or.getProperty("search_class")));
	 System.out.println("Size of Filter option categories : " + filterOptions.size());
	 int b =help.random(filterOptions.size());
	 String opt = filterOptions.get(b).findElement(By.tagName(or.getProperty("search_tag"))).getText();
	 System.out.println("Filter Option Selected : " + opt);		
			 //Selecting an option in Filter Options 
	 List <WebElement> option = filterOptions.get(b).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	 System.out.println("No.of options in " + opt + " List : " + option.size());
	 int c = help.random(option.size());
	 System.out.println("Option selected is : " + option.get(c).findElement(By.tagName(or.getProperty("search_tagname"))).getText());
	 option.get(c).findElement(By.tagName(or.getProperty("allproposals_tagName"))).click();
	 driver.findElement(By.id(or.getProperty("search_id1"))).click();
	 help.sleep(5);
	    //Printing the Table displayed with required fields
	 pagination();
	 pageEntries();
	 search("Larry");
	 System.out.println(driver.findElement(By.id(or.getProperty("search_idd"))).findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).getText());
	 driver.close();
	 driver.switchTo().window(parentWindow);
	 } else {
		 Assert.fail("No Link Found");
		 }
	  System.out.println("###### done with the validation of search leads ######");
	  System.out.println("==============================================================================");
 }
 @Test
  public void testPassword() throws Exception
  {
	 help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
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
  
          // pagination
 public static void pagination() 
 {
	 System.out.println("###### Verifying Next & Previous Buttons ######");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
	 help.sleep(2);
	 driver.findElement(By.id(or.getProperty("page2_id"))).click();
	 System.out.println("Clicked on Next button");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
	 help.sleep(2);
	 driver.findElement(By.id(or.getProperty("page3_id"))).click();
	 System.out.println("Clicked on Previous button");
	 System.out.println(driver.findElement(By.id(or.getProperty("page1_id"))).getText());
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
 @AfterClass
  public void afterClass()
   {
    SimpleDateFormat simple = new SimpleDateFormat("E dd/MM/yyyy hh:mm:ss a"); // here E gives the day(mon, tue etc..)  and a gives am r pm
    Date date = new Date();
    System.out.println("The last run time of the instances " + simple.format(date).toString());
    System.out.println("===================================================================================");
   }
 }





