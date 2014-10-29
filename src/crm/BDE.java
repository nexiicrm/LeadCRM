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
	String[] expected = {"Research Phase" , "Work Phase", "Closed Phase", "Cold Storage", "Lead Search", "Lead Edit", "My Account", 
			"Research On Company", "Work on Lead", "Today's FollowUp", "All FollowUps", "Lead Close", "Cold Storage", "Search Leads", "Edit Leads", "Change Password"};
    
	List<String> lisub = new ArrayList<String>();
	String randomLead;
	String Leadno;
	Date date;
	Date later;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
	Calendar cal;
	
	//Take Two Arguments 'subLink' to navigate also 'container name' of page after it navigates. 
	//Navigate to any subLink of BDM or BDE and checks the given pageName to verify the navigation is accurate.
	public void navigatePage(String subLink, String pageName)
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id(or.getProperty("Treeid"))).findElements(By.tagName(or.getProperty("subtreetag")));
		for (int i=0; i<subtree.size(); i++)
		{
			if((subtree.get(i).getText().equalsIgnoreCase(subLink)))
			{   	   
			   subtree.get(i).click();
			   System.out.println(" ###### Navigate to the page ::: "+ subLink + "########## \n ");
			   sleep(2);		  
			   WebElement cont= driver.findElement(By.id(or.getProperty("pagename")));
			   if (cont.findElement(By.tagName(or.getProperty("pagetag"))).getText().equalsIgnoreCase(pageName))
				   System.out.println("You have Sucessfully navigated to " + subLink );
			   else
				   System.out.println("The page is not navigated to "+ pageName);		   
			}
		}
	}
	
	//This method takes argument as search Text. IT will search for relevant text and click the lead.
	public void particularLeadSelection(String searchText)
	{
		 searchLead(searchText);
		 sleep(3);
		 List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		 System.out.println(table.size() + " is " + table);
		 if (table.size() == 0) 
			 Assert.fail("Work Phase table field is Empty...");
		 else
		 {
			 WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("leadProceed")));
			 Leadno = res.getAttribute("id");
			 System.out.println("WorkLead is" + Leadno);
			 sleep(4);
			 res.click();
			 sleep(1);
		 }
	}
	
	// This method takes two parameters WebElement of button , Message to be displayed as string.
	// This will click the button and checks for the error message displayed on form raise a assert if not displayed.
	public void submitMessage(WebElement sb, String msg)
	{
	  sb.submit();
	  List<WebElement> ermg =driver.findElement(By.id(or.getProperty("resultid"))).findElements(By.tagName(or.getProperty("resulttag")));
	  if(ermg.get(0).getText().contains(msg)) //equalsIgnoreCase(msg))
		  System.out.println("The Error message :" + msg +" is displayed.....");
	  else
		  Assert.fail("The Error message : "+ msg +" is not displayed......");
	}
	
	// This method takes parameter as string. Pass this string in to search box.
	public void searchLead(String Leadno)
	{
		WebElement search = driver.findElement(By.id(or.getProperty("searchid"))).findElement(By.tagName(or.getProperty("searchtag")));
		search.sendKeys(Leadno);
	}
	
	// This method selects a random lead from table.
	public void randomLeadSelection()
	{
		List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		System.out.println("Selecting random element from table of size" + " is " + table.size());
		if (table.size() == 0)
			Assert.fail("The size table field is Empty... ");
		else
		{
			WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("leadProceed")));	
			randomLead = res.getAttribute(or.getProperty("leadId"));
			System.out.println("WorkLead is" + randomLead);
			sleep(4);
			res.click();	 
			System.out.println("######### You have Navigated to Form ##########");   	
		}
	}
	
	//This method is Work Phase Form Which takes 3 parameters FollowUp Type, Comment, Date and fill form fields
	public void fillingForm(String s1, String s2, String s3)
	{
		  WebElement seg = driver.findElement(By.id(or.getProperty("formButton")));
		  submitMessage(seg, "Please Select FollowUp Type....");
		  new Select(driver.findElement(By.name(or.getProperty("Type")))).selectByVisibleText(s1);
		  sleep(1);
	 				 
		  submitMessage(seg,"Please Leave A Comment....");
		  WebElement cmt = driver.findElement(By.name(or.getProperty("Comment")));
		  cmt.sendKeys(s2);
		  sleep(1);
		  
		  submitMessage(seg,"Please Enter Next FollowUp date....");			  	  					 
		  WebElement folldate = driver.findElement(By.id(or.getProperty("date")));
		  folldate.sendKeys(s3);
		  sleep(1);
		  folldate.click();
	
		  submitMessage(seg,"Successfully Updated..."); 	 
	}
	
	//Return the size of the table....
	public String tableSize()
	{
		List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		System.out.println("The table size is: "+ table.size() + " id is " + table.get(0).getAttribute(or.getProperty("leadId")));
		List<WebElement> td = table.get(0).findElements(By.tagName("td"));
        System.out.println(td.get(0).getAttribute("class"));
	    return td.get(0).getAttribute("class");	 	    
	}
   
	//This method verifies the track it functionality...
	public void trackIT()
	{   
		List<String> Lead = new ArrayList<String>();
		List<String> track  = new ArrayList<String>();
		
		List<WebElement> res = driver.findElements(By.tagName(or.getProperty("tagTd")));
			
	        // This For loop add the lead id, name, company, service, Mobile, Lead Status in to ArrayList 'Lead'
			for (int k = 0 ; k< res.size(); k++)
			{
				Lead.add(res.get(0).getText());
				System.out.println("The track index ::" + k + "contains ::" +res.get(k).getText() );
			}
			
			driver.findElement(By.linkText("Track It")).click();
			sleep(2);	
			List<WebElement> tracks = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
			
			// This For loop add the all the contents of track it form in to Array List 'track
			for (int i=0;i< tracks.size();i++)
			{
			   List<WebElement> s1= tracks.get(i).findElements(By.tagName(or.getProperty("tagTd")));
			   for(int j=0;j<s1.size();j++){
			      String s2 = s1.get(j).getText();
			      track.add(s2); 
			   }
			} 
			System.out.println("Size is " + track.size() + "1: " + track.get(0) + "2: " + track.get(1) + "19: " + track.get(19) );
		    
			 if(track.get(0).contains(Lead.get(0)))
			   if(track.get(1).contains(Lead.get(1)))   
			    if(track.get(19).contains(Lead.get(19)))
			         System.out.println("Data is exactly matching in track it page.");   
		     else
		    	 System.out.println("These is Data Mismatch.............");
		}
	
	 public void proposalQuotePage(String s1, String s2) throws Exception 
	 {
		 login("srinivasa.sanchana@nexiilabs.com", "password");
		 navigatePage(s1, s2);
		 searchLead(Leadno);
		// WebElement table = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
		 WebElement table = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElement(By.tagName(or.getProperty("tableTr")));
		 table.findElements(By.tagName("td")).get(5).click();
		 sleep(1);
	 }    
 
	 public void ProposalQuoteForm(String s1, String s2, String s3)
	 {
		 WebElement name = driver.findElement(By.name(s1));
		 name.sendKeys("Comments of closed phase.. ");
		 sleep(1);
		 
		 WebElement des = driver.findElement(By.name(s2));
		 des.sendKeys("Comments of closed phase.. ");
		 sleep(1);
		 
		 WebElement browse = driver.findElement(By.name(s3));
		 String path  =  System.getProperty("user.dir") + "\\src\\configFiles\\configuration.properties";
		 browse.sendKeys(path);
		 
		 WebElement upload = driver.findElement(By.id("button"));
		 upload.click();	 
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
	
    // @Test
	public void ExpandCollapse()
	{	
		//This List tree contains all Main Links of BDE Module , adds these in to "lisub" List.
		List<WebElement> tree = driver.findElement(By.id(or.getProperty("Treeid"))).findElements(By.className(or.getProperty("close")));
		sleep(1);
		System.out.println(" ++++++++ Adding Links of tree in to array lisub ++++++++++");
		for (int i = 0; i < tree.size(); i++)
			lisub.add(tree.get(i).getText());	
		expand();
		
		//This List tree contains all Main subLinks of BDE Module, adds these in to "lisub" List.
		System.out.println(" ++++++++ Adding Links of tree in to array lisub ++++++++++");
		List<WebElement> subtree= driver.findElement(By.id(or.getProperty("Treeid"))).findElements(By.tagName(or.getProperty("subtreetag")));
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
		navigatePage("Research On Company", "Lead Research");
	    
		List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		System.out.println("Randomly selecting one lead from table of size ::" + table.size());
			   
		WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("segButton")));
		
		String researchLead =res.getAttribute(or.getProperty("leadId"));
	    System.out.println("Lead no is " + researchLead);
	    res.click();
		sleep(2);
		
		System.out.println("######### Navigated to  ResearchOnCompany Form ##########\n");   
	    if (driver.findElement(By.tagName(or.getProperty("pagetag"))).getText().equalsIgnoreCase("Research on Lead"))
	    {
			WebElement seg = driver.findElement(By.id("segregatebutton"));
		    submitMessage(seg, "Please Select Company Fund Status");
		    new Select(driver.findElement(By.name("companyfundstatus"))).selectByVisibleText("Seed Funded");
		    sleep(1);
					  
			submitMessage(seg , "Please Select Company Status");
			new Select(driver.findElement(By.name("companystatus"))).selectByVisibleText("Stable Growth");
            sleep(1);
            
			submitMessage(seg , "Please Leave a comment");
			WebElement cmt = driver.findElement(By.name("researchcomment"));
			cmt.sendKeys("This is completed as per the schedule.");
			sleep(1);
					 
			submitMessage(seg , "Segregated Successfully and Lead Moved to Work Phase");			  
			WebElement close =  driver.findElement(By.tagName(or.getProperty("formButton")));
			close.click();
			
		 }else
			 Assert.fail("Navigation of Reasearch on Lead page Failed");
	     
	     //Check for Lead not Present in Research Phase
	     navigatePage("Research On Company", "Lead Research");
	     searchLead(researchLead);
	     if (tableSize().contains("dataTables_empty"))
	    	 System.out.println("The research Lead : " + researchLead +" is not present in Research Phase.");
		 else 
			 Assert.fail("The Lead " + researchLead + "is still present in research phase");
		 
	     //Check for Lead Presence in Work on Lead Phase.
		 navigatePage("Work on Lead", "Work on Lead");
		 searchLead(researchLead);
		 if (tableSize().contains("dataTables_empty"))
			 Assert.fail("Expected Lead is not present in Work Phase");
		 else 
			 System.out.println("The research Lead : " + researchLead +" is sucessfully navigated to Work Phase");
		
		 trackIT();
	}	
	
	//@Test
    public void workPhaseForTodaysDate()
	{
		date = new Date();	
		navigatePage("Work on Lead", "Work on Lead");
		randomLeadSelection();
	    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Work on Lead"))
		{	
	    	sleep(2);
	    	fillingForm("Introductory Mail","selection of today date",simple.format(date));
	    	WebElement close =  driver.findElement(By.tagName("button"));
	    	close.click();	 
		}
	    else
		  Assert.fail("The navigation to work on Lead Form is failed");
	    
	    //This Block checks for Lead worked is not present in work on Lead Phase.
	    navigatePage("Work on Lead", "Work on Lead");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))
	    	System.out.println("The Work Lead :" + randomLead +" is not present in Work Phase");
		 else 
			Assert.fail("Expected " + randomLead + " Work on Lead is still in Work phase.");
	    
	    // This Block Search for the lead present in Todays FollowUp. 
	    navigatePage("Today's FollowUp", "Today Followups");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty")) 	
			 Assert.fail("Expected Lead is not present in Todays Follow up");
		 else 
			 System.out.println("The research Lead : " + randomLead +" is sucessfully navigated to Today's Follow up");
		sleep(2);
		trackIT();
		
		// This Block Search for the lead present in All FollowUp.
		navigatePage("All FollowUps", "All Followups");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 System.out.println("The research Lead : " + randomLead + " is also available in All FollowUps");
		sleep(2);
		trackIT();
		
	}
	
	  //@Test
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
	    	Assert.fail("This form 'Work on Lead' navigation is unsuccessfull.");
	    
	    //This Block checks for Lead worked is not present in work on Lead Phase.
	    navigatePage("Work on Lead", "Work on Lead");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))
	    	 System.out.println("The research Lead : " + randomLead +" is not present in Research Phase.");
		 else 
			 Assert.fail("Expected Lead is not present in Work Phase");
	    
	    // This Block Search for the lead present in All FollowUps.
	    navigatePage("All FollowUps", "All FollowUps");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 System.out.println("The research Lead : " + randomLead + " is also available in All FollowUps");
		trackIT();
	}
     
     //@Test
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
		 
		 // Check for Lead  present in Todays FollowUp. 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))
			 Assert.fail("Lead :" +Leadno + "is not present in All Followup to cold storage");
		 else 
			 System.out.println("The research Lead : " + Leadno +" is present in Todays FollowUp.");
	     
		 // Check for Lead presence at cold Storage.
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))    	
			 Assert.fail("Expected Lead" + Leadno +" is not present in All Follow up");
		 else 
			 System.out.println("The research Lead : " + Leadno + " is also available in All FollowUps");
		 
	 }
     
	 @Test
	 public void todaysFollowupProposal() throws Exception
	 {
		 date = new Date();
	     navigatePage("Today's FollowUp", "Today Followups");
	     particularLeadSelection("Introductory Mail");
	     sleep(2);
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {   
			 WebElement seg = driver.findElement(By.className("button"));
			 
			 submitMessage(seg, "Please Select FollowUp Type....");
			 new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
			 sleep(1);
			 
			 submitMessage(seg, "Please Select Prospect Type....");
			 new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
			 sleep(1);
		
			 
			 submitMessage(seg, "Please Enter Fixed Date....");
			 WebElement fixdate = driver.findElement(By.id("fixon"));
			 fixdate.sendKeys(simple.format(date));
			 sleep(1);
			 
			 submitMessage(seg, "Please Select Mail Id of Architect....");
			 
			 WebElement to = driver.findElement(By.name("to"));
			 to.click();
			 List<WebElement> option =to.findElements(By.tagName("option"));
			 option.get(1).click();
			 sleep(1);
			 
			 submitMessage(seg, "Please Enter Subject");
			 WebElement sub = driver.findElement(By.name("subject"));
			 sub.sendKeys("Sending Prospect Proposal");
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Message");
			 WebElement msg = driver.findElement(By.name("message"));
			 msg.sendKeys("This is proposal message field");
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Comment");
			 WebElement cmt = driver.findElement(By.name("followupcomment"));
			 cmt.sendKeys("This is followup comment of proposal");
			 sleep(1);
			  
			
			 submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
			 WebElement folldate = driver.findElement(By.id("nextfollowupdate"));
			 folldate.sendKeys(simple.format(date));
			 sleep(1);	
			 	 
			 submitMessage(seg,"Proposal Request Send Successfully"); 
	
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
		 }	
		 else
		     	Assert.fail("Failed to navigate to todays Followup form");
		 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 tableSize(); 
		 
		 browser();
		 maxbrowser();
		 driver.get(config.getProperty("url"));
		 proposalQuotePage("Proposal Upload", "Leads for Proposal Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Proposal Upload"))
		 {
		   ProposalQuoteForm("proposalname","proposaldescription","proposal");
		 } 
	 }
	 
    // @Test
	 public void AllFollowups()
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUps", "All Followups");
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
	 
     //@Test
	 public void AllFollowupsQuoteUpload() throws Exception
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, 2);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUps", "All Followups");
    	 particularLeadSelection("Introductory Mail");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 WebElement seg = driver.findElement(By.id("button"));
			 
			 submitMessage(seg, "Please Select FollowUp Type");
			 new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
			 sleep(1);
			 
			 submitMessage(seg, "Please Select FollowUp Type");
			 new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Quote");
			 sleep(1);
			 
			 submitMessage(seg, "Please Enter Fixed Date");
			 WebElement fixdate = driver.findElement(By.id("fixon"));
			 fixdate.sendKeys(simple.format(date));
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Comment");
			 WebElement cmt = driver.findElement(By.name("followupcomment"));
			 cmt.sendKeys("This is followup comment of proposal");
			 sleep(1);		  
			
			 submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
			 WebElement folldate = driver.findElement(By.id("nextfollowupdate"));
			 folldate.sendKeys(simple.format(later));
			 sleep(1);	
			 	 
			 submitMessage(seg,"Quote Details Successfully Updated"); 		 
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
		 }
		 else
			 Assert.fail("Navigation to page All Followup failed");
		 
		 navigatePage("All FollowUp", "All Followups");
		 searchLead(Leadno);
		 		 
		 
		 proposalQuotePage("Quote Upload", "Leads for Quote Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Quote Upload"))
		 {
		   ProposalQuoteForm("quotename","quotedescription","quote");
		 }
		 
	 }
  
	// @Test
     public void AllFollowupclose()
     {
    	 navigatePage("All FollowUps", "All Followups");    
    	 particularLeadSelection("Prospect Identify");
    	
    	 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
    		 WebElement seg = driver.findElement(By.id("button"));
    		 submitMessage(seg, "Please Select Followup Type");
    		 new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Proposal/Quote Accepted");
    		 sleep(1);
   	 				 
    		 submitMessage(seg,"Please Leave A Comment.");
    		 WebElement cmt = driver.findElement(By.name("followupcomment"));
    		 cmt.sendKeys("Comments of closed phase.. ");
    		 sleep(1);
    	     
    		 submitMessage(seg, "Sucessfully Updated");
    		 
    		 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
    		 
		 } else
			 Assert.fail("Navigation to page All Followup failed");
    	 navigatePage("Lead Close", "Lead Close");
    	 searchLead(Leadno);
    	
    
     }
	 //@Test
	 public void closePhase() 
	 {
		 navigatePage("Lead Close", "Closed Phase");
		 
	 }
	 
	 
 }

