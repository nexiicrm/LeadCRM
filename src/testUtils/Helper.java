package src.testUtils;

import java.util.List;
import java.util.Random;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import src.crm.BaseTest;

public class Helper extends BaseTest
{
	public void browser()	//To select browser
	{
		if (config.getProperty("BrowserType").equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
			
		}
		
		else if (config.getProperty("BrowserType").equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		else if (config.getProperty("BrowserType").equalsIgnoreCase("ie")) 
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else
		{
			Assert.fail("Select Browser Correctly choose either Firefox or Chrome or IE");
		}
	}
	
	public void maxbrowser()   //To maximize browser
	{
		driver.manage().window().maximize();
	}
	
	public void browsererror() 	//To handle browser errors
	{
		log.debug("Checking for browser error");
		String currenturl = driver.getTitle();
		if (currenturl.toLowerCase().contains("Problem loading page") || currenturl.toLowerCase().contains("is not available") || currenturl.toLowerCase().contains("cannot display the webpage"))
		  {
			  Assert.fail("Check your Internet Connectivity or URL specfied");
		  }
		  else
		  {
			  return;
		  }
	}
	
	public boolean screenshot(String filename) throws Exception  // To take screenshots
	  {
		  f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  FileUtils.copyFile(f, new File(System.getProperty("user.dir")+"\\src\\screenshots\\"+filename+".jpg"));
		  return false;
	  }
	
	public void sleep(int seconds) // To sleep
	{
		try
		{
			Thread.sleep(seconds*1000);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void login(String username, String password) throws Exception  // To Login 
	{
		sleep(2);
		driver.findElement(By.id("username")).sendKeys(username);
		sleep(1);
		driver.findElement(By.id("password")).sendKeys(password);
		sleep(1);
		driver.findElement(By.cssSelector("p.login.button")).findElement(By.tagName("input")).submit();
		sleep(1);	
	}
	
	public void expand()   // To expand side tree menu
	{
	  List<WebElement> exp = driver.findElement(By.className("menu")).findElements(By.className("close"));
	  sleep(1);
	  for (int i = 0; i < exp.size(); i++)
	  { 
		exp.get(i).findElement(By.className("    symbol-close")).click();
		sleep(1);
	  }
	}
	
	public void collapse()  // To collapse side tree menu
	{
	  List<WebElement> coll = driver.findElement(By.className("menu")).findElements(By.className("open"));
	  sleep(1);
	  for (int i = 0; i < coll.size(); i++)
	  {
		coll.get(i).findElement(By.className("     symbol-open")).click();
		sleep(1);
	  }   
    }
	
	public int random(int size)
	{
		Random r = new Random();
		int n = r.nextInt(size);
		return n;
	}
	

}
