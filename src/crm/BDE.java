package crm;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;
import crm.BDEMethods;

public class BDE extends Helper{
	BDEMethods b = new BDEMethods();
	List<String> lisub = new ArrayList<String>();
	
	public void ExpandCollapse(List<WebElement> tree)
	{
		for (int i=0 ; i< tree.size(); i++)
		{
			lisub.add(tree.get(i).getText());
			System.out.println("this upper" + lisub);
			System.out.println(tree.get(i).getText());
	   
			List<WebElement> sub = tree.get(i).findElements(By.tagName("span"));
			//sub.get(0).click();
			if(sub.get(0).getAttribute("class").contains("open"))
			{   
				List<WebElement> li2= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
				for (int j=0 ;j <li2.size();j++){
					System.out.println(li2.get(j).getText());
					lisub.add(li2.get(j).getText());
				}
			}	   
		}
	}
    public void ResearchOnCompany(List<WebElement> tree)
    {
    	List<WebElement> sub = tree.get(0).findElements(By.tagName("span"));
		   sub.get(0).click();
		   
		   if((sub.get(0).getAttribute("class").contains("open")))// && (tree.get(i).getText().equalsIgnoreCase("Research Phase")))
		   {   
			   System.out.println("this is Research Phase");
			   
			   List<WebElement> subtree = tree.get(0).findElements(By.tagName("a"));
			   subtree.get(0).click();
			   System.out.println(" Navigate to 'Research On Company' page  ");
			   help.sleep(2000);
			   WebElement cont= driver.findElement(By.id("container"));
			   if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Lead Research"))
			   {
				   System.out.println("The Lead Research page is navigated sucessfully.");
				   
				   List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				   System.out.println(table.size() + " is " + table);
				   WebElement res = table.get(0).findElement(By.className("segregate"));
				   res.click();
				   System.out.println("The Button clicked is:" +res.getText());
				   WebElement cont1= driver.findElement(By.id("dialog-form"));
				   System.out.println("cont1 size"+ cont1);
				   System.out.println(cont1.findElement(By.tagName("h1")).getText());
				   if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Research on Lead"))
				   {
					  List<WebElement> lab1 = driver.findElement(By.name("companyfundstatus")).findElements(By.tagName("option"));
					  lab1.get(0).click();
				   }
				   
				   
			   }
		   }
    
			   
    }
	
	

	@BeforeTest
	public void beforeMethod(){
		help.browser();
	}
	@Test
	public void BDEModule() throws Exception 
	{
		//h.browser();
		driver.get("http://192.168.50.32:8080/leadcrm");
		help.login("sreekar.jakkula@nexiilabs.com", "password");
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();	
		
		List<WebElement> tree = driver.findElement(By.id("tree_menu")).findElements(By.className("close"));
		/*b.listmethodassert(tree);
		ExpandCollapse(tree);
		List<String> lisub = new ArrayList<String>();*/
	    ResearchOnCompany(tree);
		
	
  }
}
