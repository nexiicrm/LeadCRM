package crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDE extends Helper{
	String[] expected = {"Research Phase", "Work Phase", "Closed Phase", "Cold Storage", "Lead Search", "Lead Edit", "My Account", 
			"Research On Company", "Work on Lead", "Today's FollowUp", "All FollowUps", "Lead Close", "Cold Storage", "Search Leads", "Edit Leads", "Change Password"};

	List<String> lisub = new ArrayList<String>();
	String randomLead;
	String Leadno;
	Date date;
	Date later;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
	Calendar cal;
	
	
	  
	public void navigatePage(String subLink, String pageName)
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
		for (int i=0; i<subtree.size(); i++)
		{
			if((subtree.get(i).getText().equalsIgnoreCase(subLink)))
			{   	   
			   subtree.get(i).click();
			   System.out.println(" Navigate to 'Work on Lead' page  ");
			   sleep(2);		  
			   WebElement cont= driver.findElement(By.id("container"));
			   if (cont.findElement(By.tagName("h1")).getText().equalsIgnoreCase(pageName))
				   System.out.println("You have Sucessfully navigated to " + subLink );
			   else
				   Assert.fail("The page is not navigated to "+ pageName);
				   
			}
		}
	}
	
	public void particularLeadSelection(String searchText)
	{
		 searchLead(searchText);
		 sleep(3);
		 List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		 System.out.println(table.size() + " is " + table);
		 if (table.size() == 0)
			 Assert.fail("Work Phase table field is Empty...");
		
		 WebElement res = table.get(random(table.size())).findElement(By.className("work"));
		 Leadno = res.getAttribute("id");
		 System.out.println("WorkLead is" + Leadno);
		 res.click();
		 sleep(1);
		 WebElement cont1= driver.findElement(By.id("dialog-form"));
		 System.out.println(cont1.findElement(By.tagName("h1")).getText());
		 
	}
	
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
	
	
	public void randomLeadSelection()
	{
		List<WebElement> table = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		 System.out.println(table.size() + " is " + table);
		 if (table.size() == 0)
			 Assert.fail("Work Phase table field is Empty...");
        
		 WebElement res = table.get(random(table.size())).findElement(By.className("work"));	
		 randomLead = res.getAttribute("id");
		 System.out.println("WorkLead is" + randomLead);
		 res.click();
		 
		 
		 System.out.println("######### Navigated to  Work on Lead Form ##########");   
		 WebElement cont1= driver.findElement(By.id("dialog-form"));
		 System.out.println(cont1.findElement(By.tagName("h1")).getText());
	}
	
	public void fillingForm(String s1, String s2, String s3)
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
		//driver.close();
	}
	
    //@Test
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
	    			Assert.fail("Failed on search of link of tree :" +expected[k]);
		}
	}
	
	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
	//@Test
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
				   
				   //This Block checks for 'Research on Lead' Form functionality.
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
		navigatePage("Work on Lead", "Work on Lead");
		randomLeadSelection();
	    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
		{	
	    	sleep(2);
	    	fillingForm("Introductory Mail","selection today date",simple.format(date));
	    	WebElement close =  driver.findElement(By.tagName("button"));
	    	close.click();	 
		}
	    else
	    {
		  Assert.fail("The navigation to work on Lead Form is failed");
	    }
	    navigatePage("Today's FollowUp", "Today Followups");
		sleep(2);
		searchLead(randomLead);
	}
	
	  @Test
	  public void workPhaseForLaterDate()
	  {   
	    cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 2);
	    later = cal.getTime();
	
		navigatePage("Work on Lead", "Work on Lead");
		randomLeadSelection();
	    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
	    {
	    	sleep(2);
	    	fillingForm("Introductory Mail","selection later date",simple.format(later));
	    	WebElement close =  driver.findElement(By.tagName("button"));
	    	close.click();	
	    }
	    else
	    {
	    	Assert.fail("This form 'Work on Lead' navigation is unsuccessfull.");
	    }
	    navigatePage("All FollowUps", "All FollowUps");
		sleep(2);
		searchLead(randomLead);
	}
	
     
     @Test
     public void todaysFollowup4()
	 {	
    	 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("Today's FollowUp", "Today Followups");
    	 particularLeadSelection("Introductory Mail");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 fillingForm("Followup 4","selection today date",simple.format(later));
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
			 
		 }else
			 Assert.fail("Navitage to todays followup form is failed.");
		 
		 navigatePage("Cold Storage", "Cold Storage");
		 sleep(2);
		 searchLead(Leadno);
	 }
	
	

	 @Test
	 public void todaysFollowupProposal()
	 {
		 date = new Date();
	     navigatePage("Today's FollowUp", "Today Followups");
	     particularLeadSelection("Introductory Mail");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {   
			 WebElement seg = driver.findElement(By.id("button"));
			 
			 new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("prospect Identify");
			 
			 new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
			 sleep(1);
			 	  	  					 
			 WebElement fixdate = driver.findElement(By.id("fixon"));
			 fixdate.sendKeys(simple.format(date));
			 sleep(1);
			 
			 
			 new Select(driver.findElement(By.name("to"))).selectByVisibleText("lakshmi.sure@nexiilabs.com"); 
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();	
			 sleep(1);
			 
			 WebElement sub = driver.findElement(By.name("subject"));
			 sub.sendKeys("Sending Prospect Proposal");
			 sleep(1);
			 
			 submitMessage(seg,"Please select Followup Type");
			 WebElement msg = driver.findElement(By.name("followupcomment"));
			 msg.sendKeys("This is proposal message field");
			 sleep(1);
			 
			 submitMessage(seg,"Please select Followup Type");
			 WebElement cmt = driver.findElement(By.name("followupcomment"));
			 cmt.sendKeys("This is proposal comment field");
			 sleep(1);
			  
			 submitMessage(seg,"Please Enter next Follow up date");			  	  					 
			 WebElement folldate = driver.findElement(By.id("nextfollowupdate"));
			 folldate.sendKeys(simple.format(date));
			 sleep(1);		  
			 folldate.click();
			 submitMessage(seg,"Successfully Updated."); 
			 
			 
		 }					
	 }
	  
	 public void AllFollowups()
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUp", "All Followups");
    	 particularLeadSelection("Introductory Mail");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 fillingForm("Followup 4","selection today date",simple.format(later));
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
			 
		 }else
			 Assert.fail("Navitage to todays followup form is failed.");
		 
		 navigatePage("Cold Storage", "Cold Storage");
		 sleep(2);
		 searchLead(Leadno);
	 }
	 
	
	 
					 
 }

