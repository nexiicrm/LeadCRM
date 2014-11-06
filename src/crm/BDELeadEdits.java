package src.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import src.testUtils.Helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nexiilabs.dbcon.DBConnection;


public class BDELeadEdits extends Helper{
	
	List<String> lisub = new ArrayList<String>();
	String randomLead;
	String Leadno;
	Date date;
	Date later;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
	Calendar cal;
	public static Connection connection =null;
    public static Statement statement;
    public static ResultSet resultSet;
   
    //Take Two Arguments 'subLink' to navigate also 'container name' of page after it navigates. 
	//Navigate to any subLink of BDM or BDE and checks the given pageName to verify the navigation is accurate.
	public void navigatePage(String subLink, String pageName)
	{
		expand();
		List<WebElement> subtree= driver.findElement(By.id(bde.getProperty("Treeid"))).findElements(By.tagName(bde.getProperty("subtreetag")));
		for (int i=0; i<subtree.size(); i++)
		{
			if((subtree.get(i).getText().equalsIgnoreCase(subLink)))
			{   	   
			   subtree.get(i).click();
			   Reporter.log("<p>   ###### Navigate to the page ::: "+ subLink + "########## \n ");
			   sleep(2);		  
			   WebElement cont= driver.findElement(By.id(bde.getProperty("pagename")));
			   if (cont.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase(pageName))
				   Reporter.log("<p> You have Sucessfully navigated to " + subLink );
			   else
				   Reporter.log("<p> The page is not navigated to "+ pageName);		   
			}
		}
	}

	// This method takes parameter as string. Pass this string in to search box.
	public void searchLead(String Leadno)
	{
		WebElement search = driver.findElement(By.id(bde.getProperty("searchid"))).findElement(By.tagName(bde.getProperty("searchtag")));
		if(search == null)
			Assert.fail("The Search Text Box is not Present");
		else
			search.sendKeys(Leadno);
	}
	
	// This method selects a random lead from table.
	public void LeadSelection(String txt, String button)
	{
		if (txt.contentEquals("random"))
		{
			List<WebElement> table = driver.findElement(By.id(bde.getProperty("tableId"))).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
			if(tableSize().contains("dataTables_empty"))
				Assert.fail("The Table size is Empty to pick a random Element");
			else
			{
				WebElement res = table.get(random(table.size()));
				List<WebElement> tdlis = res.findElements(By.tagName("td"));
				Reporter.log("<p> " +tdlis.size());
				randomLead = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
				Reporter.log("<p>  The particular Lead is : " + randomLead);
				searchLead(randomLead);
				driver.findElement(By.className(button)).click();
				sleep(1); 
				Reporter.log("<p>  ######### You have Navigated to Form ##########"); 
			}
		}
		else
		{
			searchLead(txt);
			List<WebElement> table1 = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
			if(tableSize().contains("dataTables_empty"))
				Assert.fail(" The table size is empty ");
			else
			{
				WebElement tr= table1.get(random(table1.size()));
				List<WebElement> tdlis1 = tr.findElements(By.tagName("td"));
				Leadno = tdlis1.get(0).getText() + " " + tdlis1.get(1).getText() + " " +tdlis1.get(2).getText();
				Reporter.log("<p> The particular Lead is : " + Leadno);
				tdlis1.get(6).findElement(By.className(button)).click();
			}
		}
	}
	
	// This method takes two parameters WebElement of button , Message to be displayed as string.
	// This will click the button and checks for the error message displayed on form raise a assert if not displayed.
	public void submitMessage(WebElement sb, String msg)
	{
		  sb.submit();
		  
		  List<WebElement> ermg =driver.findElement(By.id(bde.getProperty("resultid"))).findElements(By.tagName(bde.getProperty("resulttag")));
		  sleep(2);
		  if(ermg.get(0).getText().contentEquals(msg));
			  Reporter.log("<p> The message :" + msg +" is displayed.....");  
		  if(ermg.get(0).getText().contains("Proposal Request Failed to send through mail"))
			  Reporter.log("<p> Mail is not sent because of some reasons.");
		  if(ermg.get(0).getText().contains("Please Upload the Proposal or Quote First"))
			  Assert.fail("Can't close the Lead with out uploading Quote or Proposal.");  
		  else
			  Reporter.log("<p> The message : " + msg +". is not displayed and could see error :" + ermg.get(0).getText());  	  
	}
		
	//This method is Work Phase Form Which takes 3 parameters FollowUp Type, Comment, Date and fill form fields
	public void fillingForm(String s1, String s2, String s3)
	{
		  WebElement seg = driver.findElement(By.id(bde.getProperty("formButton")));
		  submitMessage(seg, "Please Select FollowUp Type....");
		  new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText(s1);
		  sleep(1);
	 				 
		  submitMessage(seg,"Please Leave A Comment....");
		  WebElement cmt = driver.findElement(By.name(bde.getProperty("Comment")));
		  cmt.sendKeys(s2);
		  sleep(1);
		  
		  submitMessage(seg,"Please Enter Next FollowUp Date....");			  	  					 
		  WebElement folldate = driver.findElement(By.id(bde.getProperty("Date")));
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
		List<WebElement> table = driver.findElement(By.id(bde.getProperty("tableId"))).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		List<WebElement> td = table.get(0).findElements(By.tagName("td"));
        //Reporter.log("<p> "td.get(0).getAttribute(bde.getProperty("class")));
	    return td.get(0).getAttribute(bde.getProperty("class"));	 	    
	}
   
	//This method verifies the track it functionality...
	public void trackIT()
	{   
		List<String> Lead = new ArrayList<String>();
		List<String> track  = new ArrayList<String>();
		
		List<WebElement> res = driver.findElements(By.tagName(bde.getProperty("tagTd")));
	    // This For loop add the lead id, name, company, service, Mobile, Lead Status in to ArrayList 'Lead'
		for ( int k = 0 ; k< 6 ; k++ )
		{
			Lead.add(res.get(0).getText());
			Reporter.log("<p> The track index ::" + k + "contains ::" +res.get(k).getText() );
		}
			
		driver.findElement(By.linkText("Track It")).click();
		sleep(2);	
		List<WebElement> tracks = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
			
		// This For loop add the all the contents of track it form in to Array List 'track
		for (int i = 0 ; i< tracks.size() ; i++ )
		{
			 List<WebElement> s1= tracks.get(i).findElements(By.tagName(bde.getProperty("tagTd")));
			 for(int j=0;j<s1.size();j++){
				 String s2 = s1.get(j).getText();
			     track.add(s2); 
			 }
		} 
		Reporter.log("<p> Size is " + track.size() + " 1) " + track.get(0) + " 2) " + track.get(1) + " 19) " + track.get(19) );
		    
	    if(track.get(0).contains(Lead.get(0)))
		    if(track.get(1).contains(Lead.get(1)))   
			    if(track.get(19).contains(Lead.get(5)))
			         Reporter.log("<p> Data is exactly matching in track it page....");   
	    else
	    	Reporter.log("<p> There is mismatch in Data");
	}
	 
	 //This method takes two parameters as strings. It will take the BDM page Proposal or Quote link, container name.
	 //It will login to BDM page click particular lead from proposal or Quote page.
	 public void proposalQuotePage(String Linkname, String Containername) throws Exception 
	 { 
		 try
	     {   	
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            connection = DBConnection.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password "+ "from crm_role a, crm_user b where " 
	                                             + "a.role_id = b.role_id AND a.role_name = 'BDM' AND delete_status='no'Limit 1;");      
	            while (resultSet.next())
	            {        
	               // String role = resultSet.getString("role_name");
	                String email = resultSet.getString("email_id");
	                String pass = resultSet.getString("password");
	                
	                browser();
	    			maxbrowser();
	    			driver.get(config.getProperty("url"));
	       		    help.login(email,pass);   
	       		    
	       		    String user = driver.findElement(By.className("user_name")).getText();
	       		    System.out.println("user " + user);
	       		    if (user.contains("Hi ! BDM"))
	       		    	Reporter.log("<p>  ++++++++ Logged in as BDE user ++++++++++");
	       		    else
	       		    	Assert.fail("You have not logged in as BDE user.");
	       		    
	       		    navigatePage(Linkname, Containername);
	       		    searchLead(Leadno);
	       		    WebElement table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElement(By.tagName(bde.getProperty("tableTr")));
	       		    table.findElements(By.tagName(bde.getProperty("tagTd"))).get(5).findElement(By.className("upload")).click();
	       		    sleep(1);  
	           }
	     }
	     catch (Exception e){ 
		      e.printStackTrace();
		 }	
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
		 
		 WebElement upload = driver.findElement(By.id(bde.getProperty("formButton")));
		 upload.click();	
		 
		 WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
		 close.click();
		 driver.close();
	 }
	
    @Test
	public void LC_TS_43_ExpandCollapse()
	{	
		//This List tree contains all Main Links of BDE Module , adds these in to "lisub" List.
	    
    	String user = driver.findElement(By.className("user_name")).getText();
    	System.out.println("user " + user);
    	if (user.contains("Hi ! BDE"))
    		Reporter.log("<p>  ++++++++ Logged in as BDE user ++++++++++");
    	else
    		Assert.fail("You have not logged in as BDE user.");
    	
    	//This tree checks for the tree links of BDE page
		sidetreemenuverify(3);
	}
	
	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
    @Test(invocationCount = 1)
	public void LC_TS_44_researchOnCompany()
	{
		navigatePage("Research On Company", "Lead Research");
		LeadSelection("random", "segregate");
		Reporter.log("<p> ######### Navigated to  ResearchOnCompany Form ##########\n");   
	    if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Research on Lead"))
	    {
			WebElement seg = driver.findElement(By.id(bde.getProperty("segButton1")));
		    submitMessage(seg, "Please Select Company Fund Status");
		    new Select(driver.findElement(By.name(bde.getProperty("resFund")))).selectByVisibleText("Seed Funded");
		    sleep(1);
					  
			submitMessage(seg , "Please Select Company Status");
			new Select(driver.findElement(By.name(bde.getProperty("resCompany")))).selectByVisibleText("Stable Growth");
            sleep(1);
            
			submitMessage(seg , "Please Leave a Comment");
			WebElement cmt = driver.findElement(By.name(bde.getProperty("resComment")));
			cmt.sendKeys("Research Phase completed as per the schedule.");
			sleep(1);
					 
			submitMessage(seg , "Segregated Successfully and Lead Moved to Work Phase");			  
			WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
			close.click();
			
		 }else
			 Assert.fail("Navigation of Reasearch on Lead page Failed");
	     
	     //Check for Lead not Present in Research Phase
	     navigatePage("Research On Company", "Lead Research");
	     searchLead(randomLead);
	     if (tableSize().contains("dataTables_empty"))
	    	 Reporter.log("<p> The research Lead : " + randomLead +" is not present in Research Phase.");
		 else 
			 Assert.fail("The Lead " + randomLead + "is still present in research phase");
		 
	     //Check for Lead Presence in Work on Lead Phase.
		 navigatePage("Work on Lead", "Work on Lead");
		 searchLead(randomLead);
		 if (tableSize().contains("dataTables_empty"))
			 Assert.fail("Expected Lead is not present in Work Phase");
		 else 
			 Reporter.log("<p> The research Lead : " + randomLead +" is sucessfully navigated to Work Phase");
		 
		 //This block checks the track It functionality 
		 trackIT();
		 List<WebElement> tb = driver.findElements(By.tagName(bde.getProperty("table")));
		 List<WebElement> lb2 = tb.get(1).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("resulttag")));
		 if(lb2.get(2).getText().contains("Research Phase completed as per the schedule"))
			Reporter.log("<p> Trackit comments for Research Phase is successfull.");
		 else
			Reporter.log("Track it comments for Research phase not Present");
	}	
	
	@Test(invocationCount = 1)
    public void LC_TS_45_1_workPhaseForTodaysDate()
	{
		date = new Date();	
		navigatePage("Work on Lead", "Work on Lead");
		LeadSelection("random", "work");
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
	    	Reporter.log("<p> The Work Lead :" + randomLead +" is not present in Work Phase");
		else 
			Reporter.log("Expected " + randomLead + " Work on Lead is still in Work phase.");
	    
	    // This Block Search for the lead present in Todays FollowUp. 
	    navigatePage("Today's FollowUp", "Today Followups");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty")) 	
			 Assert.fail("Expected Lead is not present in Todays Follow up");
		 else 
			 Reporter.log("<p> The research Lead : " + randomLead +" is sucessfully navigated to Today's Follow up");
		sleep(2);
		//Check for track functionality and Comments.
		trackIT();
		List<WebElement> tb = driver.findElements(By.tagName(bde.getProperty("table")));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("resulttag")));
		if(lb2.get(3).getText().contains("Selection of today's date"))
			Reporter.log("<p> Trackit comments for Work Phase is successfull.");
		else
			//Assert.fail("Track it comment for Work Phase not found..");
		
		// This Block Search for the lead present in All FollowUp.
		navigatePage("All FollowUps", "All Followups");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 Reporter.log("<p> The research Lead : " + randomLead + " is also available in All FollowUps");
		sleep(2);
		trackIT();		
	}
	
	  @Test(invocationCount = 2)
	  public void LC_TS_45_2_workPhaseForLaterDate()
	  {   
	    cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 2);
	    later = cal.getTime();
	
		navigatePage("Work on Lead", "Work on Lead");
		LeadSelection("random", "work");
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
	    	 Reporter.log("<p> The research Lead : " + randomLead +" is not present in Research Phase.");
		 else 
			 Assert.fail("Expected Lead is not present in Work Phase");
	    
	    // This Block Search for the lead present in All FollowUps.
	    navigatePage("All FollowUps", "All FollowUps");
	    searchLead(randomLead);
	    if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 Reporter.log("<p> The research Lead : " + randomLead + " is also available in All FollowUps");
		
	    //This block checks track it functionality and comments.
	    trackIT();
	    List<WebElement> tb = driver.findElements(By.tagName(bde.getProperty("table")));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("resulttag")));
		if(lb2.get(3).getText().contentEquals("Selection of later date"))
			Reporter.log("<p> Track it comments for Work Phase later date is Present");
		else
			Reporter.log("<p> Track it comments for Work Phase later date not found");
	}
     
     //@Test
     public void LC_TS_46_1_todaysFollowup4()
	 {	
    	 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("Today's FollowUp", "Today Followups");
    	 LeadSelection("Introductory Mail", "work");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 fillingForm("Followup 4","selection today date",simple.format(later));
				 
		 }else
			 Assert.fail("Navitage to todays followup form is failed.");
		 
		 // Check for Lead  present in Todays FollowUp. 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))
			 Assert.fail("<p> The todays FollowUp Lead : " + Leadno +" is not present in todays Followup.");
		 else 
			 Reporter.log("Lead : " + Leadno + " is still present in All FollowUp");
			
		 // Check for Lead presence at Cold Storage.
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))    	
			 Assert.fail("Expected Lead" + Leadno +" is not present in All Follow up");
		 else 
			 Reporter.log("<p> The research Lead : " + Leadno + " is also available in All FollowUps");	 
	 }
     
	 @Test(invocationCount = 1)
	 public void LC_TS_46_2_todaysFollowupProposal() throws Exception
	 {
		 date = new Date();
	     navigatePage("Today's FollowUp", "Today Followups");
	     LeadSelection("Introductory Mail" , "work");
	     sleep(2);
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {   
			 WebElement seg = driver.findElement(By.className(bde.getProperty("formButton")));
			 
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
			 
			 WebElement to = driver.findElement(By.name(bde.getProperty("to")));
			 to.click();
			 List<WebElement> option =to.findElements(By.tagName(bde.getProperty("option")));
			 option.get(1).click();
			 sleep(1);
			 
			 submitMessage(seg, "Please Enter Subject");
			 WebElement sub = driver.findElement(By.name(bde.getProperty("subject")));
			 sub.sendKeys("Sending Prospect Proposal");
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Message");
			 WebElement msg = driver.findElement(By.name(bde.getProperty("message")));
			 msg.sendKeys("This is proposal message field");
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Comment");
			 WebElement cmt = driver.findElement(By.name(bde.getProperty("Comment")));
			 cmt.sendKeys("This is followup comment of proposal");
			 sleep(1);
			  
			
			 submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
			 WebElement folldate = driver.findElement(By.id(bde.getProperty("Date")));
			 folldate.sendKeys(simple.format(date));
			 sleep(1);	
			 	 
			 submitMessage(seg,"Proposal Request Send Successfully"); 
	
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click();
			
		 }	
		 else
		 {
		     Assert.fail("Failed to navigate to todays Followup form");
		 }
		 
		 proposalQuotePage("Proposal Upload", "Leads for Proposal Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Proposal Upload"))
		 {
		   ProposalQuoteForm("proposalname","proposaldescription","proposal");
		 } 
		 
		 //This block Checks for Lead Presence in Todays FollowUp and also proposal comment.
		 
		 login(config.getProperty("bdename"),config.getProperty("bdepass"));
		 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
		 trackIT();
		 
		 //This block checks whether Lead is Present in All FollowUp.
		 
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All Follow up");
		 else 
			 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
		 trackIT(); 
	
	 }
	 
     @Test
	 public void LC_TS_47_TC001_AllFollowups4()
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUps", "All Followups");
    	 LeadSelection("Introductory Mail" , "work");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 fillingForm("Followup 4","Sending Lead to Cold Phase",simple.format(later));		 
		 }else
			 Assert.fail("Navitage to todays followup form is failed."); 
		 
		 navigatePage("All FollowUps", "All Followups");
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in AllFollowUP.");
		 trackIT(); 
		 
		 navigatePage("Cold Storage", "Cold Storage");
		 searchLead(Leadno); 
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in ColdStorage");
		 else 
			 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in Cold Storage.");		 
	 }
	 
     @Test
	 public void LC_TS_47_TC002_AllFollowupsQuoteUpload() throws Exception
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, 2);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUps", "All Followups");
    	 LeadSelection("Introductory Mail" , "work");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 WebElement seg = driver.findElement(By.id(bde.getProperty("formButton")));
			 
			 submitMessage(seg, "Please Select FollowUp Type");
			 new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
			 sleep(1);
			 
			 submitMessage(seg, "Please Select FollowUp Type");
			 new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Quote");
			 sleep(1);
			 
			 submitMessage(seg, "Please Enter Fixed Date");
			 WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
			 fixdate.sendKeys(simple.format(date));
			 sleep(1);
			 
			 submitMessage(seg,"Please Leave A Comment");
			 WebElement cmt = driver.findElement(By.name(bde.getProperty("Comment")));
			 cmt.sendKeys("This is followup comment of Quote");
			 sleep(1);		  
			
			 submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
			 WebElement folldate = driver.findElement(By.id(bde.getProperty("Date")));
			 folldate.sendKeys(simple.format(later));
			 sleep(1);	
			 	 
			 submitMessage(seg,"Quote Details Successfully Updated"); 		 
			 WebElement close =  driver.findElement(By.tagName("button"));
			 close.click(); 
			 
		 }
		 else
		 {
			 Assert.fail("Navigation to page All Followup failed");
		 }
		 
		 proposalQuotePage("Quote Upload", "Leads for Quote Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Quote Upload"))
			 ProposalQuoteForm("quotename","quotedescription","quote");  
		 else 
			Assert.fail("Not navigated.");
		 
		 
		 //This block check for the lead present in All FollowUp also verify the Track It comments
		 driver.get(config.getProperty("url"));
		 login(config.getProperty("bdename"),config.getProperty("bdepass"));
		 browsererror();
		 
		 navigatePage("All FollowUp", "All Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
		 trackIT(); 		  
	 }
  
	 @Test
     public void LC_TS_47_TC003_AllFollowupclose()
     {
    	 navigatePage("All FollowUps", "All Followups");    
    	 LeadSelection("Prospect Identify" , "work");
    	
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
    	 //navigatePage("Lead Close", "Lead Close");
    	 searchLead(Leadno);
    	 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 	   	 
     }
	 
	
	 @Test
	 public void LC_TS_47_TC004_confirmLeadsOfTodaysDate()
	 {
		 date = new Date();	
		 List<String> l1 = new ArrayList<String>();
		 List<String> l2 = new ArrayList<String>();
		 
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(simple.format(date));
		 List<WebElement> All = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		 
		 for(int i = 0; i < All.size(); i++)
		 {
			 // l1.add(All.get(i).findElement(By.className(bde.getProperty("sortclass"))).getText());
			 if (tableSize().contains("dataTables_empty"))
			 {
				 navigatePage("Today's FollowUp", "Today Followups");
			     searchLead(simple.format(date));
			     if(tableSize().contains("dataTables_empty"))
				    Assert.fail("There were no leads in All FollowUp table to verify this senario.");
			     else
			    	Assert.fail("There is some mis match in size of Todays Followup");
			 }
			 else
			 {
				 List<WebElement> tds = All.get(i).findElements(By.tagName("td"));
				 l1.add(tds.get(0).getText() + " " + tds.get(1).getText() + " " +tds.get(2).getText());
			 }
		 }   
		 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(simple.format(date));
		 List<WebElement> Today = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		 
		 for(int j = 0; j < All.size(); j++)
		 {
			  //l2.add(Today.get(j).findElement(By.className(bde.getProperty("sortclass"))).getText());
			 List<WebElement> tds1 = Today.get(j).findElements(By.tagName("td"));
		     l2.add(tds1.get(0).getText() + " " + tds1.get(1).getText() + " " +tds1.get(2).getText());
		 }
		 Reporter.log("<p>  AllFollowUp table size:" + Today.size() + "Todays FollowUp table size:" + All.size());
		 
		 if(All.size() == Today.size())
		 {
			 
			 for(int k = 0; k < All.size(); k++)
				 if(l1.get(k).equals(l2.get(k)))
					 System.out.println(l1.get(k) + "is found in todays Follow ups");
				 else
					 System.out.println(l1.get(k) + "is not found in todays Follow ups");
			     
		 }
	 }
	 
	//@Test
	public void LC_TS_47_TC005_UIToFieldCheck()
	{
		 date = new Date();
	     navigatePage("Today's FollowUp", "Today Followups");
	     searchLead("Introductory Mail");

	     if (tableSize().contains("dataTables_empty"))
			Assert.fail("Table is empty");
		 else
		 {
		    List<WebElement> table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		    WebElement tr= table.get(random(table.size()));
			List<WebElement> tdlis1 = tr.findElements(By.tagName("td"));
			String leadService= tdlis1.get(3).getText();
			tdlis1.get(6).findElement(By.className("work")).click();
		    if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		    {   
		     	new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
		    	sleep(2);
		    	
		    	new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
			    sleep(2);
	
			    WebElement fixdate = driver.findElement(By.id("fixon"));
			    fixdate.sendKeys(simple.format(date));
			    sleep(4);
		  
			    List<WebElement> tolist =  driver.findElement(By.name("to")).findElements(By.tagName("option"));
			    List<String> dbmail = new ArrayList<String>();
				try {
		              
		              Class.forName("com.mysql.jdbc.Driver").newInstance();
		              connection = DBConnection.getConnection();
		              statement = connection.createStatement();
		              
		              resultSet = statement.executeQuery("select  c.email_id " + "from crm_service a, crm_role b, crm_user c "
		                      + "where a.service_id = c.service_id AND b.role_id = c.role_id AND "
		                      + "b.role_id = 2 AND a.service_name = '" + leadService + "';");      
		              while(resultSet.next()) {
		                  String str = resultSet.getString("email_id");
		                  dbmail.add(str);                 
		             }        
		           }    	  
		         catch (Exception e){ 
		             e.printStackTrace();
		         }	
				
				 //Verifying options available under select service dropdown equals services in database 
		    	 List<String> list = new ArrayList<String>();
		    	 Reporter.log("<p>" + "***************Checking options under 'To' dropdown*********************");
		    	 for(int i=0;i<tolist.size()-1;i++)
		    	 {
		    		  list.add(tolist.get(i+1).getText());
		    		  if(dbmail.get(i).equals(list.get(i)))
		    			  Reporter.log("<p>" + list.get(i) + "-->mail id of Architect for " + dbmail +" is displayed");
		    		  else
		    			  Reporter.log("<p>" + list.get(i) + "-->mail id of Architect for " + dbmail +" is not displayed");
		    	 }
		  
		    }	
		
	   }
	}   
	     @Test
		 public void LC_TS_47_TC006_coldStorage() 
		 { 
			 navigatePage("Cold Storage", "Cold Storage");
			 LeadSelection("random" , "analyse");
			 
			 //This block checks for lead moved from closed phase to BD
			 navigatePage("Cold Storage", "Cold Storage");
			 searchLead(randomLead);
			 if (tableSize().contains("dataTables_empty"))	
				 Reporter.log("<p> The All Followup Lead is : " + randomLead + " is not available Cold Storage.");
			 else 
				 Assert.fail("Expected Lead is still present in Cold Storage after Confirmation.");
			 
			//This block checks for lead moved from All FollowUp.
			 navigatePage("All FollowUps", "All Followups");
			 searchLead(randomLead);
			 if (tableSize().contains("dataTables_empty"))	
				 Reporter.log("<p> The Cold Phase lead is : " + randomLead + " is not available in All FollowUp.");
			 else 
				  Assert.fail("Expected Lead is still present in All FollowUp after Confirmation.");
			 
			 //Search Closed Lead in DB from Search Leads
			 // Check whether the lead is present in the database or in Lead Search cold storage
			  if(driver.findElement(By.id(bde.getProperty("leadsearchlink_id"))).isDisplayed()) 
			 {
				 driver.findElement(By.id(bde.getProperty("leadsearchlink_id"))).click();
				 
				 // Switching to Child Window
				 String parentWindow = driver.getWindowHandle();
				 for(String childWindow : driver.getWindowHandles()) 
				 {
					 driver.switchTo().window(childWindow);
				 }
			
				Reporter.log("<p>" + "Clicking on the Lead Search and verifying the confirmed lead of cold storage.");
				WebElement reqfields = driver.findElement(By.id(bde.getProperty("requiredfields_id"))).findElements(By.tagName(bde.getProperty("servicename_tag"))).get(0);
				reqfields.findElement(By.tagName(bde.getProperty("searchbox_tag"))).click();
					 
			    driver.findElement(By.cssSelector(bde.getProperty("filteroption_css"))).click();
					 
			    // Selecting Cold storage in filter options
			    WebElement status = driver.findElement(By.id(bde.getProperty("statusid_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4);
			    status.findElement(By.tagName(bde.getProperty("searchbox_tag"))).click();
					 
			   // Clicking on search button
			    driver.findElement(By.id(bde.getProperty("registerbutton_id"))).click();
			    help.sleep(4);
					 
			    String[] search = randomLead.split(" ");
				 
				Reporter.log("<p>" + "Searching the lead in Lead Search which is confirmed in cold storage phase .");
				searchLead(search[1] + " " + search[2]);
				
				if (tableSize().contains("dataTables_empty"))	
					 Assert.fail("Expected Lead is not present in Cols Storage.");
			    else 
					 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 	
				
			    // Closing the Child Window
				driver.close();
				 
				// Switching to Parent Window
				 driver.switchTo().window(parentWindow);
			 }
		 }	
	     
	   ///Venkatesh Code /////////////  
	   ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	     public void close(int y,String c,String data) throws Exception
	     {
	   	 	driver.findElement(By.name(bde.getProperty("comment_id"))).sendKeys(sh4.getCell(0,6).getContents());
	   		driver.findElement(By.id(bde.getProperty("closeform_button"))).click();
	   		help.sleep(2);
	   		Reporter.log("<p>" +driver.findElement(By.className(bde.getProperty("success_message"))).getText());
	   		driver.findElement(By.className(bde.getProperty("editform_close")));
	   		help.sleep(2);
	   		 driver.findElement(By.className("user_logout")).click();
	   		help.sleep(3);
	   		//for getting mailid,password from database
	   		try
	   		{ 
	   			Class.forName("com.mysql.jdbc.Driver").newInstance();
	               connection = DBConnection.getConnection();
	   			statement = connection.createStatement();
	   			resultSet = statement.executeQuery("select a.role_name, b.email_id, b.password "+ "from crm_role a, crm_user b where " 
	   			+ "a.role_id = b.role_id AND a.role_name = 'BDM' AND delete_status='no'Limit 1;"); 
	   			while (resultSet.next())
	   			{ 
	   				// String role = resultSet.getString("role_name");
	   				String email = resultSet.getString("email_id");
	   				String pass = resultSet.getString("password");
	   				help.login(email,pass);
	   			}
	   		}
	   		catch (Exception e){ 
	               e.printStackTrace();
	           }
	   		
	   		help.expand();
	   		if(driver.findElement(By.id(c)).isDisplayed())
	   		{
	   			  	  driver.findElement(By.id(c)).click();
	   			      ArrayList<Integer> li= new ArrayList<Integer>();
	   				  help.sleep(3);
	   				  //sending data into searchbox
	   				  WebElement search = driver.findElement(By.id(bde.getProperty("searchid"))).findElement(By.tagName(bde.getProperty("searchtag")));
	   				   if(search == null)
	   				    Assert.fail("The Search Text Box is not Present");
	   				   else
	   				    search.sendKeys(data);
	   				   //getting data from table
	   				  List<WebElement> tablerecords1= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   				  for(int b=0;b<tablerecords1.size();b++)
	   				  {
	   					  String s1=tablerecords1.get(b).findElements(By.tagName(bde.getProperty("tablecol_tagname"))).get(0).getText() ;
	   					 int z = Integer.parseInt(s1);
	   					 li.add(b, z);
	   				  }
	   				  if(li.contains(y))
	   				  Reporter.log("<p>" +"closed lead in BDE module is present in management module");
	   				  else
	   				  Assert.fail("closed lead in BDE module is not  present in management module");
	   		}
	    }
	     

	   	
	   @Test                                              // in manual test case LC_TS_48      closed phase
	   public void LC_TS_48_TC001() throws Exception 
	   {
	   	   help.expand();
	   	  help.sleep(5);
	    if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	    {
	   	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	   	  		help.sleep(3);
	   	  		help.sorting();
	   	  		help.pageEntries();
	   	  		searchtable();
	    }else
	        Assert.fail("closed phase  link not found successfully");
	    //driver.quit();
	   }
	    
	   @Test                                                     // in manual test case LC_TS_48     closed phase
	   public void LC_TS_48_TC002() throws Exception
	   {
	   	  help.expand();
	   	  help.sleep(1);
	     if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	     {
	   	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	   	  		help.sleep(1);
	   	  		List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   	  		
	   	  		if(tablerecords.size()==0)
	   	  			Assert.fail("tablebody id not found");
	   	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  		if(tablerecords2.size()>1)
	   	  		{
	   	  			//Checking for the close button for each lead
	   	  			int check=0;
	   	  			do
	   	  			{
	   	  				if(check!=0)
	   	  				driver.findElement(By.id("example_next")).click();
	   	  				List<WebElement> tablerecords3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   	  				int count=0;
	   	  				for(int i=0; i<tablerecords3.size(); i++) 
	   	  				{
	   	  					if(tablerecords3.get(i).findElement(By.className(bde.getProperty("close_button"))).isEnabled()) 
	   	  					count++;
	   	  					else
	   	  						Assert.fail("close button not found");
	   	  				}
	   	  				check++;
	   	  				if(count==tablerecords3.size())
	   	  				Reporter.log("<p>" +"close button is enabled for all leads.");
	   	  			}while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
	   	  		}else
	   	  	      Reporter.log("<p>"+"no data available");
	   	 }else
	        Assert.fail("closed phase  link not found successfully");
	   	  
	   //driver.quit();
	   }


	    @Test                                                         // in manual test case LC_TS_48     closed phase
	   public void LC_TS_48_TC003() throws Exception 
	   {
	   	 
	   	  help.expand();
	   	  help.sleep(1);
	    if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	    {
	   	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	   	  		help.sleep(3);
	   	  		List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   	  		String g=driver.findElement(By.className("dataTables_info")).getText();
	   	  		if(tablerecords.size()==0)
	   	  			Assert.fail("tablebody id not found");
	   	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  if(tablerecords2.size()>1)
	   	  {
	   		//Checking for the close button for each lead
	   			int count=0;
	   			for(int i=0; i<tablerecords.size(); i++) 
	   			{
	   				if(tablerecords.get(i).findElement(By.className(bde.getProperty("close_button"))).isEnabled()) 
	   					count++;
	   				else
	   				Assert.fail("close button not found");
	   			}
	   			if(count==tablerecords.size())
	   				Reporter.log("<p>" +"close button is enabled for all leads.");
	   			//random selection of close button
	   			int o=random(tablerecords.size());
	   			//getting  details of random lead
	   			List<WebElement>leaddata=tablerecords.get(o).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   			String s=leaddata.get(0).getText();
	   			 String data=  leaddata.get(0).getText() + " " + leaddata.get(1).getText() + " " +leaddata.get(2).getText();
	   			//converting string to integer
	   			int y=Integer.parseInt(s);
	   			Reporter.log("<p>" +"closed lead id:"+y);
	   			//clicking on close button
	   			tablerecords.get(o).findElement(By.className(bde.getProperty("close_button"))).click();
	   			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText()+":is opened");
	   			List<WebElement> leadstatus=driver.findElement(By.name(bde.getProperty("leadstatus_id"))).findElements(By.tagName(bde.getProperty("dropdown_options")));
	   			if(leadstatus.size()==0)
	   				Assert.fail("lead status id not found in close form");
	   			int a=random(leadstatus.size());
	   			if(a==0)
	   			a++;
	   			leadstatus.get(a).click();
	   			if(leadstatus.get(a).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item1"))){
	   				String c="customersList";
	   				driver.findElement(By.id(bde.getProperty("project_id"))).sendKeys(sh4.getCell(0,5).getContents());
	   				close(y,c,data);
	   			}
	   			if(leadstatus.get(a).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item2"))){
	   				String c="lostCompetitionList";
	   				close(y,c,data);
	   			}
	   	  }else
	   	   Reporter.log("<p>"+"no data available");
	   	
	   	}else
	     Assert.fail("closed phase  link not found successfully");
	   	  
	   	  
	   	  //	driver.quit();
	    }
	    
	    @Test                                                   // in manual test case LC_TS_49 & 50 &51         leadsearch
	    public void LC_TS_49_TC001() throws Exception 
	    {
	   	  help.expand();
	   	  help.sleep(1);
	       searchLead();
	      // driver.quit();
	    }
	    
	    
	    
	    @Test                                                   // in manual test case LC_TS_52                  leadedits(trackit)
	   public void LC_TS_50_TC001() throws Exception
	   {
	   	   help.expand();
	   	  Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
	    if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	    {
	   	  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	   	  		help.sleep(3);
	   	  		help.sorting();
	   	  		help.pageEntries();
	   	  		searchtable();
	    }else
	   	   Assert.fail("lead edit link not found successfully");
	   // driver.quit();
	   }
	    
	   @Test                                                      // in manual test case LC_TS_52           leadedits(trackit)     
	   public void LC_TS_50_TC002() throws Exception
	   {
	      help.expand();
	     Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
	     if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	     {
	     		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	     		help.sleep(3);
	     		List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	     		if(trackelement.size()==0)
	     			Assert.fail("table body tagname not found successfully in trackit");
	     		List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	     		if( trackelement2.size()>1)
	     		{
	     			//Checking for the Track it button for each lead
	     			int check=0;
	     			do
	     			{
	     				if(check!=0)
	     				driver.findElement(By.id("example_next")).click();
	     				List<WebElement> trackelement3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	     				int trackit=0;
	     				for(int i=0; i<trackelement3.size(); i++) {
	   				if(trackelement3.get(i).findElement(By.className(bde.getProperty("tracit_button"))).isEnabled()) 
	   				trackit++;
	   				else
	   			  	Assert.fail("trackit button is not found successfully");
	     				}
	   				if(trackit==trackelement3.size())
	     				Reporter.log("<p>"+"Trackit button is present for all leads."); 
	   				check++;
	     			}while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
	     			
	     		}else
	   	  	 Reporter.log("<p>"+"no data available");
	   	}else
	        Assert.fail("lead edit link not found successfully");
	    // driver.quit();
	   }


	    
	     @Test                                                          // in manual test case LC_TS_52                leadedits(trackit)
	    public void LC_TS_50_TC003() throws Exception
	    {
	   	   help.expand();
	   	  Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
	     if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	     {
	   	  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	   	  		help.sleep(3);
	   	  		List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   	  		if(trackelement.size()==0)
	   	  			Assert.fail("table body tagname not found successfully in trackit");
	   	  		List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	 if( trackelement2.size()>1)
	   	 {
	   		//Checking for the Track it button for each lead
	   			int trackit=0;
	   			for(int i=0; i<trackelement.size(); i++) {
	   				if(trackelement.get(i).findElement(By.className(bde.getProperty("tracit_button"))).isEnabled()) 
	   				trackit++;
	   				else
	   			  	Assert.fail("trackit button is not found successfully");
	   			}
	   			if(trackit==trackelement.size())
	   				Reporter.log("<p>"+"Trackit button is present for all leads."); 
	   		   //random selection of track it button
	   			int p=random(trackelement.size());
	   			//getting details of random lead before clicking track it button
	   	  		List<WebElement> ls =trackelement.get(p).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  		if(ls.size()==0)
	   	  			Assert.fail("tablecol tagname not found successfully in trackit");
	   	  		ArrayList<String> ar = new ArrayList<String>();
	   	  		for (int i=0;i<ls.size();i++){
	   	  			String s1= ls.get(i).getText();
	   	  			ar.add(s1);  
	   	  		}
	   	  		Reporter.log("<p>"+"Array before clicking on trackit button is: " + ar);
	   	  		//clicking on trackit button
	   	  		trackelement.get(p).findElement(By.className(bde.getProperty("tracit_button"))).click();
	   	  		help.sleep(2);
	   	  		//getting details of lead after clicking trackit button
	   	  		List<WebElement> ls2 =driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   	  		if(ls2.size()==0)
	   	  			Assert.fail("tablebody tagname not found after clicking on track it button");
	   	  		ArrayList<String> ar1 = new ArrayList<String>();
	   	  		for (int i=0;i<ls2.size();i++){
	   	  			List<WebElement> s1= ls2.get(i).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  			for(int j=0;j<s1.size();j++){
	   	  				 //Reporter.log("<p>"+s1.get(j).getText());
	   	  				String s2 = s1.get(j).getText();
	   	  				ar1.add(s2); 
	   	  			} 
	   	  		}	  
	   	  		//Reporter.log("<p>"+ar1);
	   	  		Reporter.log("<p>" +"array size after trackit button is clicked:" + ar1.size());
	   	  		Reporter.log("<p>" +"array element after trackit button is clicked: " + ar1.get(0));
	   	  		Reporter.log("<p>" +"array element after trackit button is clicked: " + ar1.get(1));
	   	  		Reporter.log("<p>" +"array element after trackit button is clicked: " + ar1.get(18));
	   	  		Reporter.log("<p>" +"array element after trackit button is clicked: " + ar1.get(19));
	   	  		//validation
	   	  		if(ar1.get(0).contains(ar.get(0))){
	   	  			if(ar1.get(1).contains(ar.get(1))){
	   	  				if(ar1.get(18).contains(ar.get(5))){	 	 
	   	  					Reporter.log("<p>" +"Data is matching exactly ");
	   	  				}
	   	  			}
	   	  		}else
	   	  		 Reporter.log("<p>" +"Data doesnt match ");
	   	 }else
	   	  Reporter.log("<p>" +"no data available ");
	     }else
	      Assert.fail("lead edit link not found successfully");
	   	  
	   	  	//driver.quit();
	   }

	     
	     @Test                                                              // in manual test case LC_TS_52               leadedits(EDIT)
	     public void LC_TS_51_TC001() throws Exception
	     {
	   	  help.expand();
	   	  Reporter.log("<p>" +"=====\n"+"EDIT::"+"======");
	   	if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	   	{
	   			driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	   	  		help.sleep(2);
	   	  		List <WebElement> leads = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   		  	if(leads.size()==0)
	   	  			Assert.fail("table body tagname not found successfully in edit");
	   		  	help.sleep(2);
	   		  	List <WebElement> leads2=leads.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  if(leads2.size()>1)
	   	  {
	   		  	// Checking for the buttons for each lead
	   		  	int check=0;
	   		  	do
	   		  	{
	   		  		if(check!=0)
	   		  			driver.findElement(By.id("example_next")).click();
	   		  		List<WebElement> leads3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   		  		int edit=0;
	   		  		for(int i=0; i<leads3.size(); i++)
	   		  		{
	   		  			if(leads3.get(i).findElement(By.className(bde.getProperty("edit_button"))).isEnabled()) 
	   		  				edit++;
	   		  			else
	   		  				Assert.fail("edit button is not found successfully");
	   		  		}
	   		  		check++;
	   				if(edit==leads3.size())
	   					Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
	   		  	}while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
	   	  }else
	   		   Reporter.log("<p>"+"no data available");
	   	}else
	   		   Assert.fail("lead edit link not found successfully");
	   	//driver.quit();
	     }
	     
	     @Test                                                               // in manual test case LC_TS_52                leadedits(EDIT)
	     public void LC_TS_51_TC002() throws Exception
	     {
	   	  help.expand();
	   	  Reporter.log("<p>" +"=====\n"+"EDIT::"+"======");
	   	if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	   	{
	   			driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	   	  		help.sleep(2);
	   	  		List <WebElement> leads = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   		  	if(leads.size()==0)
	   	  			Assert.fail("table body tagname not found successfully in edit");
	   		  	help.sleep(2);
	   		  	List <WebElement> leads2=leads.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   	  if(leads2.size()>1)
	   	  {
	   			// Checking for the buttons for each lead
	   		  	int edit=0;
	   		  	for(int i=0; i<leads.size(); i++) {
	   				if(leads.get(i).findElement(By.className(bde.getProperty("edit_button"))).isEnabled()) 
	   				edit++;
	   				else
	   				 Assert.fail("edit button is not found successfully");
	   			}
	   		  	if(edit==leads.size())
	   			Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
	   		  	Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads.size());
	   		  	int columns=sh4.getColumns();
	   			int rows=sh4.getRows();
	   			String data;
	   			help.sleep(2);
	   			//random selection of edit button
	   			int j=random(leads.size());
	   			//getting details of random lead before clicking edit button
	   			List<WebElement> ls =leads.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   			if(ls.size()==0)
	   				Assert.fail("tablecol tagname not found successfully in edit");
	   	  		ArrayList<String> ar = new ArrayList<String>();
	   	  		for (int i=0;i<ls.size();i++){
	   	  			String s1= ls.get(i).getText();
	   	  			ar.add(s1);  
	   	  		}
	   	  		Reporter.log("<p>" +"lead before clicking on edit button is: " + ar);
	   			leads.get(j).findElement(By.className(bde.getProperty("edit_button"))).click();
	   			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText());
	   			for(int row=2;row<=2;row++){
	   					int col=0;
	   					driver.findElement(By.id(bde.getProperty("first_name"))).clear();
	   					driver.findElement(By.id(bde.getProperty("first_name"))).sendKeys(sh4.getCell(col, row).getContents());
	   					driver.findElement(By.id(bde.getProperty("last_name"))).clear();
	   					driver.findElement(By.id(bde.getProperty("last_name"))).sendKeys(sh4.getCell(++col, row).getContents());
	   					driver.findElement(By.id(bde.getProperty("mobile_no"))).clear();
	   					driver.findElement(By.id(bde.getProperty("mobile_no"))).sendKeys(sh4.getCell(++col, row).getContents());
	   					driver.findElement(By.id(bde.getProperty("board_no"))).clear();
	   					driver.findElement(By.id(bde.getProperty("board_no"))).sendKeys(sh4.getCell(++col, row).getContents());
	   					driver.findElement(By.id(bde.getProperty("desk_no"))).clear();
	   					driver.findElement(By.id(bde.getProperty("desk_no"))).sendKeys(sh4.getCell(++col, row).getContents());
	   			}
	   			List<WebElement> updatedropdowns=driver.findElement(By.className(bde.getProperty("updatedropdown_class"))).findElements(By.tagName(bde.getProperty("updatedropdown_tag")));
	   			if(updatedropdowns.size()==0)
	   	  			Assert.fail(" updatedropdown class name not found successfully in edit");
	   			for(int a=0;a<updatedropdowns.size();a++){
	   					List<WebElement>container1=updatedropdowns.get(a).findElements(By.tagName(bde.getProperty("dropdown_options")));
	   					if(container1.size()==0)
	   						Assert.fail("  updatedropdown tagname name not found successfully in edit");
	   					int w=random(container1.size());
	   					container1.get(w).click();
	   					if(container1.get(w).isSelected())
	   					{
	   						Reporter.log("<p>" +w+":"+container1.get(w).getText()+" is selected ");
	   					}
	   			}
	   			//clicking on edit button
	   			driver.findElement(By.id(bde.getProperty("editcommit_button"))).click();
	   			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("message_id"))).findElement(By.tagName(bde.getProperty("message_tagname"))).getText());
	   			driver.findElement(By.className(bde.getProperty("editform_close"))).click();
	   			help.sleep(5);
	   			//getting details of lead after  editing the lead
	   			List <WebElement> leads1 = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	   			if(leads1.size()==0)
	   				Assert.fail("table id not found in edit");
	   			help.sleep(5);
	   			
	   	  		List<WebElement> ls1 =leads1.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	   			
	   	  		ArrayList<String> ar1 = new ArrayList<String>();
	   	  		for (int i=0;i<ls1.size();i++){
	   	  			String s1= ls1.get(i).getText();
	   	  			ar1.add(s1);  
	   	  		}
	   	  		Reporter.log("<p>" +"lead details after  editing   : " + ar1);
	   	  		//validation
	   	  		if(ar1.equals(ar)){
	   	  			Reporter.log("<p>" +"no modifications done");
	   	  		}
	   	  		else{
	   	  			for(int k=0;k<ar1.size();k++){
	   	  				if(ar.get(k).equals(ar1.get(k)))
	   	  				continue;
	   	  				else
	   	  				Reporter.log("<p>" +ar.get(k)+":changed to:"+ar1.get(k));
	   	  			}
	   	  		}
	   	  }else
	   	   Reporter.log("<p>"+"no data available");
	     }else
	      Assert.fail("lead edit link not found successfully");
	   	 // driver.quit();
	     }
	     
	   //  @Test                                                               // in manual test case LC_TS_53 & 54                myaccount
	     public void LC_TS_52_TC002() throws Exception
	     {
	   	  String email=config.getProperty("bdename");
	   	  changePassword(email);
	   	 // driver.quit();
	     }
	     
	     
	     
	
	 @BeforeMethod
	 public void beforeMethod() throws Exception{
			browser();
			maxbrowser();
			driver.get(config.getProperty("url"));
			login(config.getProperty("bdename"),config.getProperty("bdepass"));
			browsererror();		
			
	 }
		
	@AfterMethod
	public void afterMethod() throws Exception{
			driver.close();
			driver.manage().deleteAllCookies();
	}
		

 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 