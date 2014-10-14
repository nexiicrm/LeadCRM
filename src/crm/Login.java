package crm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


public class Login {
	//Instantiating WebDriver
	WebDriver driver;
  @Test
  public Login() throws IOException, Exception{
	  /*
	   * Checking login button is present in home page
	   */
	  if(driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).getAttribute("value").equals("Login")){
		  System.out.println("Login button is available in Lead CRM home page");
	  }else{
		  Assert.fail("Login Button is not present");
	  }
	
	  System.out.println("\n");
	  /*
	   * Getting username and password from spreadsheet 1 and trying to log in
	   */
	  System.out.println("Trying to login with different username and passwords from excel sheets");
	  File fp = new File(System.getProperty("user.dir") +"\\src\\LoginCredentials.xls");
	  Workbook wb = Workbook.getWorkbook(fp);
	  Sheet sh = wb.getSheet(0);
	  int rows = sh.getRows();
	  for(int i=0;i<rows;i++){
		  	int j=0;
		  			
			  driver.findElement(By.id("username")).sendKeys(sh.getCell(j, i).getContents());
			  j++;
			  driver.findElement(By.id("password")).sendKeys(sh.getCell(j, i).getContents());
			  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
			  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
			  Thread.sleep(2000);
			  List<WebElement> error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label"));
			  System.out.println("logging with username: " +sh.getCell(j-1, i).getContents() +", "+"password: " +sh.getCell(j, i).getContents() +":::" +error_msg.get(2).getText());
	  						}
	  
	  		  //Checking for forgot password link
	  		  List<WebElement> pass_links = driver.findElement(By.id("login")).findElements(By.tagName("p"));
	  		  System.out.println("FORGOT PASSWORD LINK PRESENT");
	  		  System.out.println("Forgot password link is = " +pass_links.get(2).findElement(By.tagName("a")).getAttribute("href"));
	  		  
	  		  //clicking forgot password link
			  driver.findElement(By.id("wrapper")).findElement(By.tagName("a")).click();
			  
			  //Checking for submit button in forgot password link
			  if(driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).getAttribute("value").equals("Submit")){
				  System.out.println("Submit button is present in forgot password link");
			  }else{
				  Assert.fail("Submit Button is not present in forgot password link");
			  }
			  
				 /*
		  		 * Getting usernames from spreadsheet 2 and trying to retreive password
		  		 */
			  Sheet sh1 = wb.getSheet(1);
			  int rows1 = sh1.getRows();
			  int j=0;
	  for(int i=0;i<rows1;i++){
		  driver.get("http://192.168.50.32:8080/leadcrm/login.jsp");
		  driver.findElement(By.id("wrapper")).findElement(By.tagName("a")).click();
		  driver.findElement(By.id("username")).sendKeys(sh1.getCell(j, i).getContents());
		  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
		  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
		  Thread.sleep(6000);
		  List<WebElement> error_msg = driver.findElement(By.id("wrapper")).findElements(By.tagName("label"));
			  if(error_msg.get(3).getText().isEmpty()){
				  System.out.print(error_msg.get(2).getText());
			  }else{
				  System.out.println(error_msg.get(3).getText());
			  }
			  
		  System.out.println(error_msg.get(3).getText());
		  
	  }
	  
	  //Getting Log in credentials from spreadsheet 3 and trying to log in 
	  Sheet sh2 = wb.getSheet(2);
	  driver.findElement(By.id("username")).sendKeys(sh2.getCell(0, 0).getContents());
	  driver.findElement(By.id("password")).sendKeys(sh2.getCell(1, 0).getContents());
	  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
	  driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).click();
	  Thread.sleep(2000);
	  if(driver.getTitle().equals("::NVT:: BDM Home Page")){
		  System.out.println("Logging with username: " +sh2.getCell(0, 0).getContents()+"," +"password: " +sh2.getCell(1, 0).getContents());
		  System.out.println("LOGGED IN SUCCESSFULLY");
	  }else{
		  System.out.println("Error in log in");
	  }
	  
  }
  @BeforeMethod
  
  /* 
   *  Opening Firefox browser and getting the Lead CRM home page
   */
  public void beforeMethod() {
	  driver = new FirefoxDriver();
	 driver.get("http://192.168.50.32:8080/leadcrm/login.jsp");
	 if(driver.getTitle().equals("::LEAD-CRM::Login Here")){
		 System.out.println("Lead CRM URL found");
	 }else{
		 System.out.println("Error in opening Lead CRM URL");
	 }
  }

}
