package crm;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testUtils.Helper;


public class Management extends Helper {
	
	
  @Test
  public void test1() throws Exception 
  {
	  //////////// logging into the site///////////////////////////
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  System.out.println("######logged in successfully into management role######");
	  System.out.println("===========================================================================");
  }
  
  @Test
  public void test2() throws Exception
  {
	  //////////////// Expanding and collapsing tree in the left pane of page ////////////
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
	  help.collapse();
	  System.out.println("######Expansion and collapsing of tree menu done successfully######");
	  System.out.println("===========================================================================");
  }
  
  @Test
  public void test3() throws Exception
  {
	  /////////////// All Proposals click and search box ///////////////////////////////
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
	  help.sleep(2);
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
      driver.findElement(By.tagName(or.getProperty("allproposals_tagName"))).click();
	  driver.findElement(By.tagName(or.getProperty("allproposals_tagName"))).sendKeys("sreekar");
  
  
	  ///////////////////////// For drop down /////////////////////////
	  List<WebElement> ele = driver.findElement(By.name(or.getProperty("allproposals_name"))).findElements(By.tagName(or.getProperty("allproposalsdrop_tagName")));
	  System.out.println("size of all proposals dropdown container: " + ele.size());
	  for (int i=0;i<ele.size();i++)
	  {
		  ele.get(i).click();
		  help.sleep(1);
	  }
	  System.out.println("###### Done with search validation and dropdown validation ######");
	  System.out.println("============================================================================");
  }
 
  @Test
  public void test4() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
	  driver.findElement(By.id(or.getProperty("allproposals_id"))).click();
	  help.sleep(2);
	  List<WebElement> ls =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow_tagName")));
	  System.out.println("No of rows " + ls.size());
	  ArrayList<String> ar = new ArrayList<String>();
	  for (int i=0;i<ls.size();i++)
	  {
		 List<WebElement> lss = ls.get(i).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
		 System.out.println("No of columns " + lss.size());
		 String s1= lss.get(0).getText();
		 ar.add(s1);	
		 String s2= lss.get(1).getText();
		 ar.add(s2);
	  }  
	  System.out.println(ar);
	  driver.findElement(By.className(or.getProperty("allproposals_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2 =driver.findElement(By.tagName(or.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(or.getProperty("allproposalsrow_tagName")));
	  System.out.println("No of rows in track it lead details " + ls2.size());
	  ArrayList<String> ar1 = new ArrayList<String>();
	  List<WebElement> lss2 = ls2.get(0).findElements(By.tagName(or.getProperty("allproposalscol_tagName")));
	  System.out.println("No of columns in track it lead details " + lss2.size());
	  String s11= lss2.get(0).getText();
	  ar1.add(s11);	
	  String s21= lss2.get(1).getText();
	  ar1.add(s21);
	  System.out.println(ar1);
	  if(ar1.get(0).contains(ar.get(0)))
	  {
		 if(ar1.get(1).contains(ar.get(1))){
		 System.out.println("Data is matching exactly in all proposals");
	  }
	  }else
	  System.out.println("Data doesnt match in all proposals");
	  System.out.println("######Done with validation of all proposals page######");
	  System.out.println("==============================================================================");
	 
  }
    
  @Test
  public void test5() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
     /////////////// All lost competition ///////////////////////////////
	  driver.findElement(By.id(or.getProperty("alllost_id"))).click();
	  driver.findElement(By.tagName(or.getProperty("alllost_tagName"))).click();
	  driver.findElement(By.tagName(or.getProperty("alllost_tagName"))).sendKeys("sreekar");
	  List<WebElement> ele1 = driver.findElement(By.name(or.getProperty("alllost_name"))).findElements(By.tagName("alllostdrop_tagName"));
	  System.out.println("size of all lost competition dropdown container: " + ele1.size());
	  for (int j=0;j<ele1.size();j++)
	  {
		  ele1.get(j).click();
		  help.sleep(1);
		  }
      //driver.findElement(By.className("analyse")).click();
	  System.out.println("######Done with validation of all lost competation page######");
	  System.out.println("==============================================================================");
	  
  }
  
  @Test
  public void test6() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
	  /////////////// All customers //////////////////////////
	  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
	  driver.findElement(By.tagName(or.getProperty("allcustomers_tagName"))).click();
	  driver.findElement(By.tagName(or.getProperty("allcustomers_tagName"))).sendKeys("shiva");
	  List<WebElement> ele2 = driver.findElement(By.name(or.getProperty("allcustomers_name"))).findElements(By.tagName(or.getProperty("allcustomersdrop_tagName")));
	  System.out.println("size of all customers dropdown container: " + ele2.size());
	  for (int j=0;j<ele2.size();j++)
	  {
		  ele2.get(j).click();
		  help.sleep(1);
		  }
      System.out.println("###### done with the validation of search and dropdown of all customers ######"); 
      System.out.println("==================================================================================");
      
  }
	 
  @Test
  public void test7() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
	  help.sleep(2);
	  driver.findElement(By.id(or.getProperty("allcustomers_id"))).click();
	  driver.findElement(By.tagName(or.getProperty("allcustomers_tagName"))).sendKeys("shiva");
	  List<WebElement> lsc =driver.findElement(By.tagName(or.getProperty("allcustomersbody_tagName"))).findElements(By.tagName(or.getProperty("allcustomersrow_tagName")));
	  System.out.println("No of rows " + lsc.size());
	  ArrayList<String> arc = new ArrayList<String>();
	  for (int i=0;i<lsc.size();i++)
	  {
		 List<WebElement> lssc = lsc.get(i).findElements(By.tagName(or.getProperty("allcustomerscol_tagName")));
		 System.out.println("No of columns " + lssc.size());
		 String s1c= lssc.get(0).getText();
		 arc.add(s1c);	
		 String s2c= lssc.get(1).getText();
		 arc.add(s2c);
	  }  
	  System.out.println(arc);
	  driver.findElement(By.className(or.getProperty("allcustomers_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2c =driver.findElement(By.tagName(or.getProperty("allcustomersbody_tagName"))).findElements(By.tagName(or.getProperty("allcustomersrow_tagName")));
	  System.out.println("No of rows in track it lead details " + ls2c.size());
	  ArrayList<String> ar1c = new ArrayList<String>();
	  List<WebElement> lss2c = ls2c.get(0).findElements(By.tagName(or.getProperty("allcustomerscol_tagName")));
	  System.out.println("No of columns in track it lead details " + lss2c.size());
	  String s11c= lss2c.get(0).getText();
	  ar1c.add(s11c);	
	  String s21c= lss2c.get(1).getText();
	  ar1c.add(s21c);
	  System.out.println(ar1c);
	  if(ar1c.get(0).contains(arc.get(0)))
	  {
		 if(ar1c.get(1).contains(arc.get(1))){
		 System.out.println("Data is matching exactly in all customers ");
	  }
	  }else
	  System.out.println("Data doesnt match in all customers");
	  System.out.println("######Done with validation of all customers page######");
	  System.out.println("==============================================================================");
	  
	 
  }
  
  @Test
  public void test8() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
      /////////////// All Quotes //////////////////////////
	  driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
      driver.findElement(By.tagName(or.getProperty("allquotes_tagName"))).click();
      driver.findElement(By.tagName(or.getProperty("allquotes_tagName"))).sendKeys("Karen");
      List<WebElement> ele3 = driver.findElement(By.name(or.getProperty("allquotes_name"))).findElements(By.tagName(or.getProperty("allquotesdrop_tagName")));
      System.out.println("size of all quotes dropdown container: " + ele3.size());
      for (int j=0;j<ele3.size();j++)
      {
    	  ele3.get(j).click();
    	  help.sleep(1);
      }
      System.out.println("###### done with the validation of search and dropdown of all quotes ######"); 
      System.out.println("==================================================================================");
  }
  @Test
  public void test9() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
      help.sleep(2);
      driver.findElement(By.id(or.getProperty("allquotes_id"))).click();
      driver.findElement(By.tagName(or.getProperty("allquotes_tagName"))).sendKeys("Karen");
	  List<WebElement> lscq =driver.findElement(By.tagName(or.getProperty("allquotesbody_tagName"))).findElements(By.tagName(or.getProperty("allquotesrow_tagName")));
	  System.out.println("No of rows " + lscq.size());
	  ArrayList<String> arcq = new ArrayList<String>();
	  for (int i=0;i<lscq.size();i++)
	  {
		 List<WebElement> lsscq = lscq.get(i).findElements(By.tagName(or.getProperty("allquotescol_tagName")));
		 System.out.println("No of columns " + lsscq.size());
		 String s1cq= lsscq.get(0).getText();
		 arcq.add(s1cq);	
		 String s2cq= lsscq.get(1).getText();
		 arcq.add(s2cq);
	  }  
	  System.out.println(arcq);
	  driver.findElement(By.className(or.getProperty("allquotes_className"))).click();
	  help.sleep(2);
	  List<WebElement> ls2cq =driver.findElement(By.tagName(or.getProperty("allquotesbody_tagName"))).findElements(By.tagName(or.getProperty("allquotesrow_tagName")));
	  System.out.println("No of rows in track it lead details " + ls2cq.size());
	  ArrayList<String> ar1cq = new ArrayList<String>();
	  List<WebElement> lss2cq = ls2cq.get(0).findElements(By.tagName(or.getProperty("allquotescol_tagName")));
	  System.out.println("No of columns in track it lead details " + lss2cq.size());
	  String s11cq= lss2cq.get(0).getText();
	  ar1cq.add(s11cq);	
	  String s21cq= lss2cq.get(1).getText();
	  ar1cq.add(s21cq);
	  System.out.println(ar1cq);
	  if(ar1cq.get(0).contains(arcq.get(0)))
	  {
		 if(ar1cq.get(1).contains(arcq.get(1))){
		 System.out.println("Data is matching exactly in all quotes ");
	  }
	  }else
	  System.out.println("Data doesnt match in all quotes");
     help.sleep(2);
     System.out.println("######Done with validation of all quotes page######");
	  System.out.println("==============================================================================");
  }
  
  @Test
  public void testLeads() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
     ///////////////////////// Search leads //////////////////////////////////////////////
     driver.findElement(By.id(or.getProperty("allleads_id"))).click();
     ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab.get(1));
	 List<WebElement> ls1 = driver.findElement(By.id(or.getProperty("allleadstab_id"))).findElements(By.tagName(or.getProperty("allleads_tagName")));
	 System.out.println("Required field container size of lead search " + ls1.size());
	 for (int j=0;j<ls1.size();j++)
     {
   	  ls1.get(j).click();
     }
	 help.sleep(2);
     driver.findElement(By.id(or.getProperty("allleadsnext_id"))).click();
     help.sleep(2);
     driver.close();
     ArrayList<String> newTab1 = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab1.get(0));
	 System.out.println("######Done with validation of search leads page######");
	  System.out.println("==============================================================================");
  }
  
  @Test
  public void testPassword() throws Exception
  {
	  help.login("basanirakeshreddy000@gmail.com", "password");
	  help.expand();
    /////////////////////////// change password ////////////////////////////////////
	 
     driver.findElement(By.xpath(or.getProperty("change_xpath"))).click();
     driver.findElement(By.id(or.getProperty("changeold_id"))).sendKeys("password");
     driver.findElement(By.id(or.getProperty("changenew_id"))).sendKeys("rakesh");
     driver.findElement(By.id(or.getProperty("changeconfirm_id"))).sendKeys("rakesh");
     help.sleep(2);
   // driver.findElement(By.id(or.getProperty("change_id"))).submit();

     System.out.println("######Done with validation of change password page######");
	  System.out.println("==============================================================================");
  }

  
  @BeforeMethod
  public void beforeClass() throws Exception {
	  help.browser();
	  help.maxbrowser();
	  driver.get(config.getProperty("url"));	  
	
  }

 @AfterMethod
  public void afterClass() {
	driver.quit();
  }

 }
