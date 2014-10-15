package crm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testUtils.Helper;


public class Management extends Helper {
	
  @Test
  public void Login() throws Exception 
  {
	 
	  //////////// logging into the site///////////////////////////

	  driver.get(config.getProperty("Murl"));
	  help.login("ajaytesting4@gmail.com", "password");
	  
	  //////////////// Expanding tree in the left pane of page ////////////
	  
	  help.expand();
	  
	  /////////////// All Proposals ///////////////////////////////
	 
	  driver.findElement(By.id("proposalsList")).click();
	  driver.findElement(By.tagName("input")).click();
	  driver.findElement(By.tagName("input")).sendKeys("sreekar");
	  List<WebElement> ele = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
	  System.out.println("size of all proposals dropdown container: " + ele.size());
	  for (int i=0;i<ele.size();i++)
	  {
		  ele.get(i).click();
		  help.sleep(1);
	  }
	  help.sleep(2);
	  List<WebElement> ls =driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  System.out.println("No of rows " + ls.size());
	  ArrayList<String> ar = new ArrayList<String>();
	  for (int i=0;i<ls.size();i++)
	  {
		 List<WebElement> lss = ls.get(i).findElements(By.tagName("td"));
		 System.out.println("No of columns " + lss.size());
		 String s1= lss.get(0).getText();
		 ar.add(s1);	
		 String s2= lss.get(1).getText();
		 ar.add(s2);
	  }  
	  System.out.println(ar);
	  driver.findElement(By.className("analyse")).click();
	  help.sleep(2);
	  List<WebElement> ls2 =driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  System.out.println("No of rows in track it lead details " + ls2.size());
	  ArrayList<String> ar1 = new ArrayList<String>();
	  List<WebElement> lss2 = ls2.get(0).findElements(By.tagName("td"));
	  System.out.println("No of columns in track it lead details " + lss2.size());
	  String s11= lss2.get(0).getText();
	  ar1.add(s11);	
	  String s21= lss2.get(1).getText();
	  ar1.add(s21);
	  System.out.println(ar1);
	  if(ar1.get(0).contains(ar.get(0)))
	  {
		 if(ar1.get(1).contains(ar.get(1))){
		 System.out.println("Data is matching exactly");
	  }
	  }else
	  System.out.println("Data doesnt match");
	 
	 
     
     
     
     /////////////// All lost competition ///////////////////////////////
	  driver.findElement(By.id("lostCompetitionList")).click();
	  driver.findElement(By.tagName("input")).click();
	  driver.findElement(By.tagName("input")).sendKeys("sreekar");
	  List<WebElement> ele1 = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
	  System.out.println("size of all lost competition dropdown container: " + ele1.size());
	  for (int j=0;j<ele1.size();j++)
	  {
		  ele1.get(j).click();
		  help.sleep(1);
		  }
      //driver.findElement(By.className("analyse")).click();
	  
	  /////////////// All customers //////////////////////////
	  driver.findElement(By.id("customersList")).click();
	  driver.findElement(By.tagName("input")).click();
	  driver.findElement(By.tagName("input")).sendKeys("shiva");
	  List<WebElement> ele2 = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
	  System.out.println("size of all customers dropdown container: " + ele2.size());
	  for (int j=0;j<ele2.size();j++)
	  {
		  ele2.get(j).click();
		  help.sleep(1);
		  }
	 
	  driver.findElement(By.className("analyse")).click();
	  help.screenshot("all proposals");
	  help.screenshot("all customers"); 
     
      /////////////// All Quotes //////////////////////////
	  driver.findElement(By.id("quotesList")).click();
      driver.findElement(By.tagName("input")).click();
      driver.findElement(By.tagName("input")).sendKeys("jesse");
      List<WebElement> ele3 = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
      System.out.println("size of all quotes dropdown container: " + ele3.size());
      for (int j=0;j<ele3.size();j++)
      {
    	  ele3.get(j).click();
    	  help.sleep(1);
      }
    // driver.findElement(By.className("analyse")).click();
     
     ///////////////////////// Search leads //////////////////////////////////////////////
     driver.findElement(By.id("serachLeads123")).click();
     ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab.get(1));
	 List<WebElement> ls1 = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("input"));
	 System.out.println("Required field container size of lead search " + ls1.size());
	 for (int j=0;j<ls1.size();j++)
     {
   	  ls1.get(j).click();
     }
	 help.sleep(2);
     help.screenshot("search leads");
     driver.findElement(By.id("ui-accordion-accordion-header-1")).click();
     help.sleep(2);
 	 help.screenshot("search");
     driver.close();
     ArrayList<String> newTab1 = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab1.get(0));
	
    /////////////////////////// change password ////////////////////////////////////
	 
     driver.findElement(By.xpath(".//*[@id='tree_menu']/li[5]/ul/li/a")).click();
     driver.findElement(By.id("oldPassword")).sendKeys("password");
     driver.findElement(By.id("newPassword")).sendKeys("rakesh");
     driver.findElement(By.id("confirmPassword")).sendKeys("rakesh");
     help.sleep(2);
 	 help.screenshot("change password");
   //  driver.findElement(By.id("change")).submit();


  }
  @BeforeMethod
  public void beforeClass() {
	  help.browser();
	  help.maxbrowser();
	
  }

 @AfterMethod
  public void afterClass() {
	
	 driver.quit();
  }

}
