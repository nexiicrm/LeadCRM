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
		
		 if (tableSize().contains("dataTables_empty")) 
			 Assert.fail("Table field is Empty...");
		 else
		 {
			// WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("leadProceed")));
			 WebElement res = table.get(random(table.size()));
			 List<WebElement> tdlis = res.findElements(By.tagName("tr"));
			 Leadno = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
			 System.out.println("The particular Lead is : " + Leadno);
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
	  if(ermg.get(0).getText().contains(msg)) 
		  System.out.println("The message :" + msg +" is displayed.....");  
	  else if(ermg.get(0).getText().contains("Please Upload the Proposal or Quote First"))
		  Assert.fail("Can't close the Lead with out uploading Quote or Proposal.");  
	  else
		  Assert.fail("The message : " + msg +". is not displayed and could see error :" + ermg.get(0).getText());  	  
	}
	
	// This method takes parameter as string. Pass this string in to search box.
	public void searchLead(String Leadno)
	{
		WebElement search = driver.findElement(By.id(or.getProperty("searchid"))).findElement(By.tagName(or.getProperty("searchtag")));
		search.sendKeys(Leadno);
	}
	
	// This method selects a random lead from table.
	@Test
	public void randomLeadSelection()
	{
		navigatePage("Research On Company", "Lead Research");
		List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		System.out.println("Selecting random element from table of size");
		if (tableSize().contains("dataTables_empty"))
			Assert.fail("The size table field is Empty... ");
		else
		{
			//WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("leadProceed")));
			WebElement res = table.get(random(table.size()));
			 List<WebElement> tdlis = res.findElements(By.tagName("tr"));
			 randomLead = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
			 System.out.println("The particular Lead is : " + randomLead);
			 sleep(4);
			 res.click();
			 sleep(1);
			 
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
		  
		  submitMessage(seg,"Please Enter Next FollowUp Date....");			  	  					 
		  WebElement folldate = driver.findElement(By.id(or.getProperty("date")));
		  folldate.sendKeys(s3);
		  sleep(1);
		  folldate.click();
	
		  submitMessage(seg,"Successfully Updated...");
		  WebElement close =  driver.findElement(By.tagName("button"));
  		  close.click();
	}
	
	//Return the size of the table....
	public String tableSize()
	{
		List<WebElement> table = driver.findElement(By.id(or.getProperty("tableId"))).findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		List<WebElement> td = table.get(0).findElements(By.tagName("td"));
        //System.out.println(td.get(0).getAttribute(or.getProperty("class")));
	    return td.get(0).getAttribute(or.getProperty("class"));	 	    
	}
   
	//This method verifies the track it functionality...
	public void trackIT()
	{   
		List<String> Lead = new ArrayList<String>();
		List<String> track  = new ArrayList<String>();
		
		List<WebElement> res = driver.findElements(By.tagName(or.getProperty("tagTd")));
	    // This For loop add the lead id, name, company, service, Mobile, Lead Status in to ArrayList 'Lead'
		for ( int k = 0 ; k< 6 ; k++ )
		{
			Lead.add(res.get(0).getText());
			System.out.println("The track index ::" + k + "contains ::" +res.get(k).getText() );
		}
			
		driver.findElement(By.linkText("Track It")).click();
		sleep(2);	
		List<WebElement> tracks = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
			
		// This For loop add the all the contents of track it form in to Array List 'track
		for (int i = 0 ; i< tracks.size() ; i++ )
		{
			 List<WebElement> s1= tracks.get(i).findElements(By.tagName(or.getProperty("tagTd")));
			 for(int j=0;j<s1.size();j++){
				 String s2 = s1.get(j).getText();
			     track.add(s2); 
			 }
		} 
		System.out.println("Size is " + track.size() + " 1) " + track.get(0) + " 2) " + track.get(1) + " 19) " + track.get(19) );
		    
	   if(track.get(0).contains(Lead.get(0)))
		   if(track.get(1).contains(Lead.get(1)))   
			    if(track.get(19).contains(Lead.get(5)))
			         System.out.println("Data is exactly matching in track it page.");   
			    else
			    	System.out.println("These is Data Mismatch.............");
	}
	 
	 //This method takes two parameters as strings. It will take the BDM page Proposal or Quote link, container name.
	 //It will login to BDM page click particular lead from proposal or Quote page.
	 public void proposalQuotePage(String Linkname, String Containername) throws Exception 
	 {
		 browser();
		 maxbrowser();
		 driver.get(config.getProperty("url"));
		 login("srinivasa.sanchana@nexiilabs.com", "password");
		 navigatePage(Linkname, Containername);
		 searchLead(Leadno);
		 WebElement table = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElement(By.tagName(or.getProperty("tableTr")));
		 table.findElements(By.tagName(or.getProperty("tagTd"))).get(5).click();
		 sleep(1);
	 }    
     
	 //This method takes 3 parameters of proposal or Quote Form's names.
	 public void ProposalQuoteForm(String s1, String s2, String s3)
	 {
		 WebElement name = driver.findElement(By.name(s1));
		 name.sendKeys(" BDE Lead ");
		 sleep(1);
		 
		 WebElement des = driver.findElement(By.name(s2));
		 des.sendKeys("Uploading Quote or Proposal ");
		 sleep(1);
		 
		 WebElement browse = driver.findElement(By.name(s3));
		 String path  =  System.getProperty("user.dir") + "\\src\\configFiles\\configuration.properties";
		 browse.sendKeys(path);
		 
		 WebElement upload = driver.findElement(By.id(config.getProperty("formButton")));
		 upload.click();	
		 
		 WebElement close =  driver.findElement(By.tagName(config.getProperty("formButton")));
		 close.click();
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
	    System.out.println("Lead no is :" + researchLead);
	    res.click();
		sleep(2);
		
		System.out.println("######### Navigated to  ResearchOnCompany Form ##########\n");   
	    if (driver.findElement(By.tagName(or.getProperty("pagetag"))).getText().equalsIgnoreCase("Research on Lead"))
	    {
			WebElement seg = driver.findElement(By.id(or.getProperty("segButton1")));
		    submitMessage(seg, "Please Select Company Fund Status");
		    new Select(driver.findElement(By.name(or.getProperty("resFund")))).selectByVisibleText("Seed Funded");
		    sleep(1);
					  
			submitMessage(seg , "Please Select Company Status");
			new Select(driver.findElement(By.name(or.getProperty("resCompany")))).selectByVisibleText("Stable Growth");
            sleep(1);
            
			submitMessage(seg , "Please Leave a Comment");
			WebElement cmt = driver.findElement(By.name(or.getProperty("resComment")));
			cmt.sendKeys("Research Phase completed as per the schedule.");
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
		 List<WebElement> tb = driver.findElements(By.tagName("table"));
		 List<WebElement> lb2 = tb.get(1).findElement(By.tagName("tbody")).findElements(By.tagName("label"));
		 if(lb2.get(2).getText().contentEquals("Research Phase completed as per the schedule."))
			System.out.println("Trackit comments for Research Phase is successfull.");
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
	    	fillingForm("Introductory Mail","Selection of today's date.",simple.format(date));	 
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
		List<WebElement> tb = driver.findElements(By.tagName("table"));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName("tbody")).findElements(By.tagName("label"));
		if(lb2.get(3).getText().contentEquals("Selection of today's date."))
			System.out.println("Trackit comments for Work Phase is successfull.");
		
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
	    	fillingForm("Introductory Mail","Selection of later date",simple.format(later));	
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
		/*List<WebElement> tb = driver.findElements(By.tagName(config.getProperty("table")));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName(config.getProperty("tableBody"))).findElements(By.tagName("label"));
		if(lb2.get(3).getText().contentEquals("Selection of later date"))
			System.out.println("Trackit comments for Work Phase is successfull.");*/
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
				 
		 }else
			 Assert.fail("Navitage to todays followup form is failed.");
		 
		 // Check for Lead  present in Todays FollowUp. 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))
			 System.out.println("The todays FollowUp Lead : " + Leadno +" is not present in todays Followup.");
		 else 
			 Assert.fail("Lead : " + Leadno + " is still present in All Followup not moved to cold storage");
			
		 // Check for Lead presence at Cold Storage.
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))    	
			 Assert.fail("Expected Lead" + Leadno +" is not present in All Follow up");
		 else 
			 System.out.println("The research Lead : " + Leadno + " is also available in All FollowUps");
		 
	 }
     
	 //@Test
	 public void todaysFollowupProposal() throws Exception
	 {
		 date = new Date();
	     navigatePage("Today's FollowUp", "Today Followups");
	     particularLeadSelection("Introductory Mail");
	     sleep(2);
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {   
			 WebElement seg = driver.findElement(By.className(config.getProperty("formButton")));
			 
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
	
			 WebElement close =  driver.findElement(By.tagName(config.getProperty("formButton")));
			 close.click();
		 }	
		 else
		     	Assert.fail("Failed to navigate to todays Followup form");
		 
		 proposalQuotePage("Proposal Upload", "Leads for Proposal Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Proposal Upload"))
		 {
		   ProposalQuoteForm("proposalname","proposaldescription","proposal");
		 } 
		 
		 //This block Checks for Lead Presence in Todays FollowUp and also proposal comment.
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 System.out.println("The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
		 trackIT();
		 
		 //This block checks whether Lead is Present in All Followup.
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 System.out.println("The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
		 trackIT(); 
	
	 }
	 
     //@Test
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
		 }else
			 Assert.fail("Navitage to todays followup form is failed."); 
		 
		 navigatePage("All FollowUps", "All Followups");
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 System.out.println("The todays Followup Lead is : " + Leadno + " is available in AllFollowUP.");
		 trackIT(); 
		
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(Leadno); 
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in ColdStorage");
		 else 
			 System.out.println("The todays Followup Lead is : " + Leadno + " is available in Cold Storage.");	
		 
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
		 
		 proposalQuotePage("Quote Upload", "Leads for Quote Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Quote Upload"))
		 {
		   ProposalQuoteForm("quotename","quotedescription","quote");  
		 }
		 
		 //This block check for the lead present in All FollowUp also verify the Track It comments
		 navigatePage("All FollowUp", "All Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 System.out.println("The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
		 trackIT(); 		 
		 
		 
	 }
  
	 //@Test
     public void AllFollowupclose()
     {
    	 navigatePage("All FollowUps", "All Followups");    
    	 particularLeadSelection("Prospect Identify");
    	
    	 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
    		 WebElement seg = driver.findElement(By.id("button"));
    		 submitMessage(seg, "Please Select FollowUp Type");
    		 new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Proposal/Quote Accepted");
    		 sleep(1);
   	 				 
    		 submitMessage(seg,"Please Leave A Comment.");
    		 WebElement cmt = driver.findElement(By.name("followupcomment"));
    		 cmt.sendKeys("Comments of closed phase.. ");
    		 sleep(1);
    	     
    		 submitMessage(seg, "Sucessfully Updated..");
    		 sleep(3);
    		 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
    		 
		 } else
			 Assert.fail("Navigation to page All Followup failed");
    	 
    	 //This block checks for Lead is moved for All FollowUpclose phase to Lead Close phase. 
    	 navigatePage("Lead Close", "Lead Close");
    	 searchLead(Leadno);
    	 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 System.out.println("The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 		 	
     }
	 
	 //@Test
	 public void coldStorage() 
	 {
		 String coldLead = null;
		 navigatePage("Cold Storage", "Cold Storage");
		 List<WebElement> table = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		 if (tableSize().contains("dataTables_empty"))
			Assert.fail("There were no leads in cold Storage.. ");
		 else
		 {
			WebElement res = table.get(random(table.size())).findElement(By.className(or.getProperty("Button")));	
			coldLead = res.getAttribute(or.getProperty("leadId"));
			System.out.println("Cold Storage lead is : " + coldLead);
			res.click();	 	
		 }
		 
		 //This block checks for lead moved from closed phase to BD
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(coldLead);
		 if (tableSize().contains("dataTables_empty"))	
			 System.out.println("The All Followup Lead is : " + coldLead + " is not available Cold Storage.");
		 else 
			 Assert.fail("Expected Lead is still present in Cold Storage after Confirmation.");
		 
		//This block checks for lead moved from All FollowUp.
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(coldLead);
		 if (tableSize().contains("dataTables_empty"))	
			 System.out.println("The Cold Phase lead is : " + coldLead + " is not available in All FollowUp.");
		 else 
			  Assert.fail("Expected Lead is still present in All FollowUp after Confirmation.");

	 }
	 
	 //@Test
	 public void confirmLeadsOfTodaysDate()
	 {
		 date = new Date();	
		 List<String> l1 = new ArrayList<String>();
		 List<String> l2 = new ArrayList<String>();
		 
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(simple.format(date));
		 List<WebElement> All = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		 
		 for(int i = 0; i < All.size(); i++)
			  l1.add(All.get(i).findElement(By.className("sorting_1")).getText());
		 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(simple.format(date));
		 List<WebElement> Today = driver.findElement(By.tagName(or.getProperty("tableBody"))).findElements(By.tagName(or.getProperty("tableTr")));
		 
		 for(int j = 0; j < All.size(); j++)
			  l2.add(Today.get(j).findElement(By.className("sorting_1")).getText());
		 
		 System.out.println("AllFollowUp table size:" + Today.size() + "Todays FollowUp table size:" + All.size());
		 if(All.size() == Today.size())
		 {
			 
			 for(int k = 0; k < All.size(); k++)
				 System.out.println(l1 + "TodaysList" + l2);   
			     
		 }
	 }
 }