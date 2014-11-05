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
			    	Reporter.log("<p> These is Data Mismatch in ...");
		    else
		    	Reporter.log("<p> There is mismatch Lead name is different");
	    else
	    	Reporter.log("<p> There is mismatch in LeadID ");
	}
	 
	 //This method takes two parameters as strings. It will take the BDM page Proposal or Quote link, container name.
	 //It will login to BDM page click particular lead from proposal or Quote page.
	 public void proposalQuotePage(String Linkname, String Containername) throws Exception 
	 {
		 List<String> sr1 = new ArrayList<String>();
		 try
	     {  
			 	
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            connection = DBConnection.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password "
	                    + "from crm_role a, crm_user b where "
	                    + "a.role_id = b.role_id AND delete_status='no';");      
	            while (resultSet.next())
	            {
	           
	                String role = resultSet.getString("role_name");
	                String email = resultSet.getString("email_id");
	                String pass = resultSet.getString("password");
	                if(email.contains("srinivas") && role.contains("BDM"))
	                {
	                    sr1.add(email);
	                    sr1.add(pass);
	                    //return sr1;
	                }
	           }
	     }
	     catch (Exception e){ 
		             e.printStackTrace();
		 }
		 browser();
		 maxbrowser();
		 driver.get(config.getProperty("url"));
		 help.login(sr1.get(0),sr1.get(1));
		 navigatePage(Linkname, Containername);
		 searchLead(Leadno);
		 WebElement table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElement(By.tagName(bde.getProperty("tableTr")));
		 table.findElements(By.tagName(bde.getProperty("tagTd"))).get(5).click();
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
		 
		 WebElement upload = driver.findElement(By.id(bde.getProperty("formButton")));
		 upload.click();	
		 
		 WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
		 close.click();
		 driver.close();
	 }
	 
	@BeforeMethod
	public void beforeMethod() throws Exception{
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		login(config.getProperty("bdename"), config.getProperty("bdepass"));
		browsererror();		
	}
	
	@AfterMethod
	public void afterMethod() throws Exception{
		//driver.close();
		driver.manage().deleteAllCookies();
	}
	
    //@Test
	public void ExpandCollapse()
	{	
		//This List tree contains all Main Links of BDE Module , adds these in to "lisub" List.
		List<WebElement> tree = driver.findElement(By.id(bde.getProperty("Treeid"))).findElements(By.className(bde.getProperty("close")));
		sleep(1);
		Reporter.log("<p>  ++++++++ Adding Links of tree in to array lisub ++++++++++");
		for (int i = 0; i < tree.size(); i++)
			lisub.add(tree.get(i).getText());	
		expand();
		
		//This List tree contains all Main subLinks of BDE Module, adds these in to "lisub" List.
		Reporter.log("<p>  ++++++++ Adding Links of tree in to array lisub ++++++++++");
		List<WebElement> subtree= driver.findElement(By.id(bde.getProperty("Treeid"))).findElements(By.tagName(bde.getProperty("subtreetag")));
		for (int i = 0; i < subtree.size(); i++)
			lisub.add(subtree.get(i).getText());
		collapse();	
		
		//This loop checks for all Expected Links, subLinks of BDE Module
		Reporter.log("<p> +++++++++ This List of Links in tree and sub tree ++++++\n" + lisub);
		for ( int k=0 ; k< expected.length; k++)
	    {
	    	String list = lisub.get(k);
	    		if (list.equals(expected[k]))
	    			Reporter.log("<p> Passed on search of link of tree :" +expected[k]);
	    		else 
	    			Assert.fail("Failed on search of link of tree :" +expected[k]);
		}
	}
	
	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
    //@Test(invocationCount = 1)
	public void aresearchOnCompany()
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
	
		 trackIT();
		 List<WebElement> tb = driver.findElements(By.tagName("table"));
		 List<WebElement> lb2 = tb.get(1).findElement(By.tagName("tbody")).findElements(By.tagName("label"));
		 if(lb2.get(2).getText().contains("Research Phase completed as per the schedule"))
			Reporter.log("<p> Trackit comments for Research Phase is successfull.");
		 else
			Reporter.log(" Track it comments for Research phase not Present");
	}	
	
	//@Test
    public void bworkPhaseForTodaysDate()
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
		List<WebElement> tb = driver.findElements(By.tagName("table"));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName("tbody")).findElements(By.tagName("label"));
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
	
	  //@Test
	  public void cworkPhaseForLaterDate()
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
		List<WebElement> tb = driver.findElements(By.tagName("table"));
		List<WebElement> lb2 = tb.get(1).findElement(By.tagName("tbody")).findElements(By.tagName("label"));
		
		if(lb2.get(3).getText().contentEquals("Selection of later date"))
			Reporter.log("<p> Track it comments for Work Phase later date is Present");
		else
			Reporter.log("<p> Track it comments for Work Phase later date not found");
	}
     
     //@Test
     public void dtodaysFollowup4()
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
     
	 //@Test
	 public void etodaysFollowupProposal() throws Exception
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
	 
     //@Test
	 public void fAllFollowups4()
	 {
		 date = new Date();	
    	 cal = Calendar.getInstance();
    	 cal.add(Calendar.DAY_OF_YEAR, -1);
 	     later = cal.getTime();
 	     
    	 navigatePage("All FollowUps", "All Followups");
    	 LeadSelection("Introductory Mail" , "work");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Followup on Lead"))
		 {
			 fillingForm("Followup 4","selection today date",simple.format(later));		 
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
	 
     //@Test
	 public void gAllFollowupsQuoteUpload() throws Exception
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
			 cmt.sendKeys("This is followup comment of proposal");
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
			 Assert.fail("Navigation to page All Followup failed");
		 
		 proposalQuotePage("Quote Upload", "Leads for Quote Upload");
		 if (driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Quote Upload"))
			 ProposalQuoteForm("quotename","quotedescription","quote");  
		 else 
			Assert.fail("Not navigated.");
		
		 
		 //This block check for the lead present in All FollowUp also verify the Track It comments
		 navigatePage("All FollowUp", "All Followups");
		 searchLead(Leadno);
		 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
		 trackIT(); 		 
		 
		 
	 }
  
	 //@Test
     public void iAllFollowupclose()
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
    	 navigatePage("Lead Close", "Lead Close");
    	 searchLead(Leadno);
    	 if (tableSize().contains("dataTables_empty"))	
			 Assert.fail("Expected Lead is not present in All FollowUp");
		 else 
			 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 	
    	 
     }
	 
	 @Test
	 public void jcoldStorage() 
	 {
		 
		/* navigatePage("Cold Storage", "Cold Storage");
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
			  Assert.fail("Expected Lead is still present in All FollowUp after Confirmation.");*/
		 
		 //Search Closed Lead in DB from Search Leads
		 navigatePage("Search Leads", "Search Criteria");
		 //driver.findElement(By.name("rqfieldsCheckAll")).click();
		 sleep(5);
		 WebElement reqfields = driver.findElement(By.id(bdm.getProperty("requiredfields_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0);
	     reqfields.findElement(By.tagName(bdm.getProperty("searchbox_tag"))).click();
	      
	      driver.findElement(By.cssSelector(bdm.getProperty("filteroption_css"))).click();
	      
	      // Selecting Cold storage in filter options
	      WebElement status = driver.findElement(By.id(bdm.getProperty("statusid_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4);
	      status.findElement(By.tagName(bdm.getProperty("searchbox_tag"))).click();
	      
	      // Clicking on search button
	      driver.findElement(By.id(bdm.getProperty("registerbutton_id"))).click();
		 
		 
		 
		/* List<WebElement> li = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("input"));
		 li.get(0).click();
		 sleep(3);
		 driver.findElement(By.id("ui-accordion-accordion-header-1")).findElement(By.tagName("span")).click();
		 driver.findElement(By.id("status_ids")).findElement(By.name("statusesCheckAll")).click();
		 driver.findElement(By.id("registerbutton")).click();*/
	 }
	 
	 //@Test
	 public void kconfirmLeadsOfTodaysDate()
	 {
		 date = new Date();	
		 List<String> l1 = new ArrayList<String>();
		 List<String> l2 = new ArrayList<String>();
		 
		 navigatePage("All FollowUps", "All Followups");
		 searchLead(simple.format(date));
		 List<WebElement> All = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		 
		 for(int i = 0; i < All.size(); i++)
			  l1.add(All.get(i).findElement(By.className(bde.getProperty("sortclass"))).getText());
		 
		 navigatePage("Today's FollowUp", "Today Followups");
		 searchLead(simple.format(date));
		 List<WebElement> Today = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
		 
		 for(int j = 0; j < All.size(); j++)
			  l2.add(Today.get(j).findElement(By.className(bde.getProperty("sortclass"))).getText());
		 
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
	 

 }