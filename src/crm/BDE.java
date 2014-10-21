import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

import testUtils.Helper;

public class BDE extends Helper{
	String[] expected = {"Research Phase", "Work Phase", "Closed Phase", "Cold Storage", "Lead Search", "Lead Edit", "My Account", 
			"Research On Company", "Work on Lead", "Today's FollowUp", "All FollowUps", "Lead Close", "Cold Storage", "Search Leads", "Edit Leads", "Change Password"};

	List<String> lisub = new ArrayList<String>();
	String workLead;
	String Leadno;
	Date date;
	Date later;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
	Calendar cal;
	
	public void submitMessage(WebElement sb, String msg)
	{
	 sb.submit();
	  List<WebElement> ermg =driver.findElement(By.id("result_msg_div")).findElements(By.tagName("label"));
	  if(ermg.get(0).getText().equalsIgnoreCase(msg))
		  System.out.println("Error message is displayed as expected");
	}
	public void searchLead(String Leadno)
	{
		WebElement search = driver.findElement(By.id("example_filter")).findElement(By.tagName("input"));
		search.sendKeys(Leadno);
		List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		System.out.println("Should display only one item,The table size is :::: " + table.size());
		String Leadres = table.get(0).findElement(By.className("sorting_1")).getText();
		
	    if(Leadres.contentEquals(Leadno)){
	    	System.out.println("The Lead is present");       
	    }else
	    	System.out.println("Lead not present");
	}
	
	  public void Sorting()
	  {
		  	List<WebElement> table= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
		  	List<WebElement> tablelis = driver.findElement(By.tagName("thead")).findElements(By.tagName("tr"));
			
			for(int i=0;i<table.size();i++)
			{
				table.get(i).click();
				//if (table.get(i).getAttribute("class")).equalsIgnoreCase("sorting_desc"))
				
				
				
				
			}
	  }
	  
	
	public void workPhasePage()
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i=0; i<subtree.size(); i++){
			if((subtree.get(i).getText().equalsIgnoreCase("Work on Lead")))
			{   	   
			   subtree.get(i).click();
			   System.out.println(" Navigate to 'Work on Lead' page  ");
			   sleep(2);		  
			   WebElement cont= driver.findElement(By.id("container"));
			   if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
			   {
				   System.out.println("######### Navigation to Work on Lead page. ##########"); 
				   List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				   System.out.println(table.size() + " is " + table);
				   if (table.size() == 0)
					   Assert.fail("Work Phase table field is Empty...");
				  
				   WebElement res = table.get(random(table.size())).findElement(By.className("work"));
				   workLead = res.getAttribute("id");
				   System.out.println("WorkLead is" + workLead);
				   res.click();
				   
				   System.out.println("######### Navigated to  Work on Lead Form ##########");   
				   WebElement cont1= driver.findElement(By.id("dialog-form"));
				   System.out.println(cont1.findElement(By.tagName("h1")).getText());
			   }
			}
		}
	}
	public void workPhaseForm(String s1, String s2, String s3)
	{
		 WebElement seg = driver.findElement(By.id("button"));
		  submitMessage(seg, "Please Select Followup Type");
		  new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText(s1);
		  sleep(1);
	 				 
		  submitMessage(seg,"Please select Followup Type");
		  WebElement cmt = driver.findElement(By.name("followupcomment"));
		  cmt.sendKeys(s2);
		  sleep(1);
		  
		  submitMessage(seg,"Please Enter next Follow up date");			  	  					 
		  WebElement folldate = driver.findElement(By.id("nextfollowupdate"));
		  folldate.sendKeys(s3);
		  sleep(1);
		  folldate.click();
	
		  submitMessage(seg,"Successfully Updated."); 	 
	}
	
	@BeforeMethod
	public void beforeMethod() throws Exception{
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		login("sreekar.jakkula@nexiilabs.com", "password");
		browsererror();		
	}
	
	@AfterMethod
	public void afterMethod() throws Exception{
		driver.close();
	}
	
	@Test
	public void ExpandCollapse()
	{	
		//This List tree contains all Main Links of BDE Module , adds these in to "lisub" List.
		List<WebElement> tree = driver.findElement(By.id("tree_menu")).findElements(By.className("close"));
		sleep(1);
		System.out.println(" ++++++++ Adding Links of tree in to array lisub ++++++++++");
		for (int i = 0; i < tree.size(); i++)
			lisub.add(tree.get(i).getText());	
		expand();
		
		//This List tree contains all Main subLinks of BDE Module, adds these in to "lisub" List.
		System.out.println(" ++++++++ Adding Links of tree in to array lisub ++++++++++");
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i = 0; i < subtree.size(); i++)
			lisub.add(subtree.get(i).getText());
		collapse();	
		
		//This loop checks for all Expected Links, subLinks of BDE Module
		System.out.println("+++++++++ This List of Links in tree and sub tree ++++++\n" + lisub);
		for ( int k=0 ; k< expected.length; k++)
	    {
	    	String list = lisub.get(k);
	    		if (list.equals(expected[k]))
	    			System.out.println("Passed on search of link of tree :" +expected[k]);
	    		else 
	    			System.out.println("Failed on search of link of tree :" +expected[k]);
		}
	}
	
	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
	@Test
	public void researchOnCompany()
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i=0; i<subtree.size(); i++)
		{
			if((subtree.get(i).getText().equalsIgnoreCase("Research On Company")))
			{   	   
			   subtree.get(i).click();
			   
			   System.out.println(" Navigate to 'Research On Company' page  ");     
			   WebElement cont= driver.findElement(By.id("container"));
			   if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Lead Research"))
			   {
				   System.out.println("######### Navigated to Lead Research page. ##########");   
				   List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				   System.out.println(table.size() + " is " + table);
				   
				   WebElement res = table.get(random(table.size())).findElement(By.className("segregate"));
				   Leadno =res.getAttribute("id");
				   System.out.println("Lead no is " + Leadno);
				   res.click();
				   
				   System.out.println("######### Navigated to  ResearchOnCompany Form ##########");   
				   WebElement cont1= driver.findElement(By.id("dialog-form"));
				   
				   // This Block checks for 'Research on Lead'Form. 
				   if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Research on Lead"))
				   {
					  WebElement seg = driver.findElement(By.id("segregatebutton"));
					  submitMessage(seg, "Please Select Company Fund Status");
					  new Select(driver.findElement(By.name("companyfundstatus"))).selectByVisibleText("Seed Funded");
					  sleep(1);
					  
					  submitMessage(seg,"Please Select Comapany Status");
					  new Select(driver.findElement(By.name("companystatus"))).selectByVisibleText("Stable Growth");

					  submitMessage(seg,"Please Leave a comment");
					  WebElement cmt = driver.findElement(By.name("researchcomment"));
					  cmt.sendKeys("This is completed as per the schedule.");
					  sleep(1);
					 
					  submitMessage(seg,"Segregated Successfully and Lead Moved to Work Phase");			  
					  WebElement close =  driver.findElement(By.tagName("button"));
					  close.click();
				   }	 
				
			   }	
			}
		}
	}
  
	@Test
    public void workPhaseForTodaysDate()
	{
		date = new Date();	
	    workPhasePage();
	    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
		{	
	    	sleep(2);
	    	workPhaseForm("Introductory Mail","selection today date",simple.format(date));
	    	WebElement close =  driver.findElement(By.tagName("button"));
	    	close.click();	 
		}
	}
	  @Test
	  public void workPhaseForLaterDate()
	  {   
	    cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 2);
	    later = cal.getTime();
	
		workPhasePage();
	    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
	    {
	    	sleep(2);
	    	workPhaseForm("Introductory Mail","selection later date",simple.format(later));
	    	WebElement close =  driver.findElement(By.tagName("button"));
	    	close.click();	
	    }
	}
	
     public void todaysFollowup(String s1, String s2)
	 {
    	 expand();		
		 List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		 for (int i=0; i<subtree.size(); i++){
		 if((subtree.get(i).getText().equalsIgnoreCase(s1)))
		 {   	   
			 subtree.get(i).click();
			 System.out.println(" Navigate to 'Work on Lead' page  ");
			 sleep(2);		  
			 WebElement cont= driver.findElement(By.id("container"));
			 if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase(s2))
			 {
				 System.out.println("######### Navigation to Work on Lead page. ##########"); 
				 searchLead("Introductory Mail");
				 sleep(2);
				 List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				 System.out.println(table.size() + " is " + table);
				 if (table.size() == 0)
					 Assert.fail("Work Phase table field is Empty...");
		   
				 WebElement res = table.get(random(table.size())).findElement(By.className("work"));	
				 res.click();
					   
				 System.out.println("######### Navigated to  Work on Lead Form ##########");   
				 WebElement cont1= driver.findElement(By.id("dialog-form"));
				 System.out.println(cont1.findElement(By.tagName("h1")).getText());
			 }
		}
	}
}

     @Test
     public void todaysFollowup4()
	 {	
    	 cal = Calendar.getInstance();
 		 //cal.add(Calendar.DAY_OF_YEAR, 2);
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
    	 todaysFollowup("Today's FollowUp", "Today Followups");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 workPhaseForm("Followup 4","selection today date",simple.format(later));
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();	
			 
			 //todaysFollowup("Cold Storage", "ColdStorage");
			 			 
		 }
	 }
		 
	// @Test
	 public void Proposal()
	 {
		 date = new Date();
	     todaysFollowup("Today's FollowUp", "Today Followups");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 sleep(2);
			 workPhaseForm("Prospect Identify","selection of today's date",simple.format(date));
			 
			 new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
			 sleep(1);
			 	  	  					 
			 WebElement fixdate = driver.findElement(By.id("fixon"));
			 fixdate.sendKeys(simple.format(date));
			 sleep(1);
			  
			 WebElement close =  driver.findElement(By.tagName("button"));
			 //close.click();	
		 }		
					
	 }
					 
 }