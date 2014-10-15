package crm;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDE extends Helper{
	String[] expected = {"Research Phase", "Work ", "Closed Phase", "Cold Storage", "Lead Search", "Lead Edit", "My Account", 
			"Research On Company", "Work on Lead", "Today's FollowUp", "All FollowUps", "Lead Close", "Cold Storage", "Search Leads", "Edit Leads", "Change Password"};
			
	List<String> lisub = new ArrayList<String>();
	@BeforeTest
	public void beforeMethod() throws Exception{
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		login("sreekar.jakkula@nexiilabs.com", "password");
		browsererror();		
	}
	/*@Test
	public void ExpandCollapse()
	{	
		List<WebElement> tree = driver.findElement(By.id("tree_menu")).findElements(By.className("close"));
		sleep(1);
		for (int i = 0; i < tree.size(); i++)
			lisub.add(tree.get(i).getText());	
		expand();
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i = 0; i < subtree.size(); i++)
			lisub.add(subtree.get(i).getText());
		collapse();			
		System.out.println("+++++++++ This List of Links in tree and sub tree ++++++\n" + lisub);
	    for ( int k=0 ; k< expected.length; k++)
	    {
	    	String list = lisub.get(k);
	    	if (list.equals(expected[k]))
	    		System.out.println("Passed on search of link of tree :" +list);
	    	else
	    		System.out.println("Failed on search of links of tree :" +list);
		}
	    
	}*/
	@Test
	public void ResearchOnCompany()
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i=0; i<subtree.size(); i++)
			if((subtree.get(i).getText().equalsIgnoreCase("Research on company")))
			{   	   
			   subtree.get(i).click();
			   System.out.println(" Navigate to 'Research On Company' page  ");
			   sleep(2);
			   WebElement cont= driver.findElement(By.id("container"));
			   if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Lead Research"))
			   {
				   System.out.println("######### The Lead Research page is navigated sucessfully. ##########");
				   
				   List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				   System.out.println(table.size() + " is " + table);
				   WebElement res = table.get(0).findElement(By.className("segregate"));
				   res.click();
				   //System.out.println("The Button clicked is:" +res.getText());
				   WebElement cont1= driver.findElement(By.id("dialog-form"));
				   System.out.println(cont1.findElement(By.tagName("h1")).getText());
				   if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Research on Lead"))
				   {
					  WebElement seg = driver.findElement(By.id("segregatebutton"));
					  seg.submit();
					  List<WebElement> ermg =driver.findElement(By.id("result_msg_div")).findElements(By.tagName("label"));
					  if(ermg.get(0).getText().equalsIgnoreCase("Please Select Company Fund Status"))
						  System.out.println("Error message is displayed as expected");
				      
					  WebElement lab1 = driver.findElement(By.name("companyfundstatus"));
					  lab1.click();
					  sleep(1);
					  List<WebElement> l1opt= lab1.findElements(By.tagName("option"));
					  l1opt.get(random(l1opt.size())).click();
					  sleep(1);
					  
					  WebElement lab2 = driver.findElement(By.name("companystatus"));
					  lab2.click();
					  sleep(1);
					  List<WebElement> l2opt= lab1.findElements(By.tagName("option"));				  
					  l2opt.get(random(l2opt.size())).click();
					  sleep(1);
					  
					  WebElement cmt = driver.findElement(By.name("researchcomment"));
					  cmt.sendKeys("This is completed as per the schedule.");
					  
					  
					  
				   }
				   
				   
			   }
		   }
 
	}		   
    }
	
			   
    
	
	

	
	

