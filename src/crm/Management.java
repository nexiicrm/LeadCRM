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
  public void Login() throws Exception 
  {
	  //////////// logging into the site///////////////////////////
	  driver.get(config.getProperty("url"));
	  help.login("ajaytesting4@gmail.com", "password");
	  
	  //////////////// Expanding tree menu of proposals ////////////
	  driver.findElement(By.cssSelector("span.symbol-close")).click();
	  
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
	 driver.findElement(By.className("analyse")).click();
	 help.sleep(2);
	 help.screenshot("all proposals");

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
	  
	  //////////////// Expanding tree menu of customers ////////////
	  driver.findElement(By.cssSelector("span.symbol-close")).click();
	  
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
	  help.sleep(2);
	  help.screenshot("all customers"); 
      ////////////////Expanding tree menu of Quotes ////////////
	  driver.findElement(By.cssSelector("span.symbol-close")).click();

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
     
     /////////////////// Expanding tree menu of Lead search /////////////////////////////
     driver.findElement(By.cssSelector("span.symbol-close")).click();
     
     ///////////////////////// Search leads //////////////////////////////////////////////
     driver.findElement(By.id("serachLeads123")).click();
     ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab.get(1));
	 List<WebElement> ls = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("input"));
	 System.out.println("Required field container size of lead search " + ls.size());
	 for (int j=0;j<ls.size();j++)
     {
   	  ls.get(j).click();
     }
	 help.sleep(2);
     help.screenshot("search leads");
     driver.findElement(By.id("ui-accordion-accordion-header-1")).click();
     help.sleep(2);
 	 help.screenshot("search");
     driver.close();
     ArrayList<String> newTab1 = new ArrayList<String>(driver.getWindowHandles());
	 driver.switchTo().window(newTab1.get(0));
	 
     ///////////////////////// Expanding Tree menu of My Account /////////////////////
     driver.findElement(By.cssSelector("span.last.symbol-close")).click();
     
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
