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
	
	List<String> lisub = new ArrayList<String>();
	String randomLead = null;
	String Leadno = null;
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
  			     
  			   WebElement cont= driver.findElement(By.id(bde.getProperty("pagename")));
  			   help.waitforElement(20 , By.id(bde.getProperty("pagename")) );	
  			   
  			   if (cont.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase(pageName))
  				   Reporter.log("<p> You have Sucessfully navigated to " + subLink );
  			   else
  				   Assert.fail("<p> The page is not navigated to "+ pageName);		   
  			}
  		}
  	}

  	// This method takes parameter as string. Pass this string in to search box.
  	public void searchLead(String Leadno)
  	{
  		waitforElement(20, By.id(bde.getProperty("searchid")) );
  		WebElement search = driver.findElement(By.id(bde.getProperty("searchid"))).findElement(By.tagName(bde.getProperty("searchtag")));
  		if(search == null)
  			Assert.fail("The Search Text Box is not Present");
  		else
  			search.sendKeys(Leadno);
  	}
  	
  	//This method takes 2 arguments Search test , Button name and return value 0 or 1
  	// This method selects a random lead from table and click on the button passed.
  	public boolean LeadSelection(String txt, String button)
  	{
  		// Directly selects on random lead used in Research and Work Phase.
  		if (txt.contentEquals("random"))
  		{
  			help.sleep(5);
  			List<WebElement> table = driver.findElement(By.id(bde.getProperty("tableId"))).findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
  			if(tableSizeCheck().contains("dataTables_empty"))
  			{
  				Reporter.log("The Table size is Empty to pick a random Element");
  				return false;
  			}
  			else
  			{
  				WebElement res = table.get(random(table.size()));
  				List<WebElement> tdlis = res.findElements(By.tagName("td"));
  				Reporter.log("<p> " +tdlis.size());
  				// Random Lead is public variable which stores the Lead ID + name + company name
  				randomLead = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
  				Reporter.log("<p>  The particular Lead is : " + randomLead);
  				searchLead(randomLead);
  				driver.findElement(By.className(button)).click();
  				sleep(1); 
  				Reporter.log("<p>  ######### You have Navigated to Form ##########"); 
  				return true;
  			}
  		}
  		else
  		{
  			// This method first search for required test and select one random lead used in today's and All FollowUp.
  			searchLead(txt);
  			List<WebElement> table1 = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
  			if(tableSizeCheck().contains("dataTables_empty"))
  			{
  				Reporter.log(" The table size is empty ");
  			    return false;
  			}
  			else
  			{
  				WebElement tr= table1.get(random(table1.size()));
  				List<WebElement> tdlis1 = tr.findElements(By.tagName("td"));
  			    // Leadno is public variable which stores the Lead ID + name + company name
  				Leadno = tdlis1.get(0).getText() + " " + tdlis1.get(1).getText() + " " +tdlis1.get(2).getText();
  				Reporter.log("<p> The particular Lead is : " + Leadno);
  				tdlis1.get(6).findElement(By.className(button)).click();
  				return true;
  			}
  		}
  		
  	}
  	
    // This method takes two parameters WebElement of button , Message to be displayed as string.
  	// This will click the button and checks for the error message displayed on form raise a assert if not displayed.
  	public void submitMessage(WebElement sb, String msg)
  	{
  		  sb.submit();  
  		  help.sleep(3);
  		  List<WebElement> ermg =driver.findElement(By.id(bde.getProperty("resultid"))).findElements(By.tagName(bde.getProperty("resulttag")));
  		  waitforElement(30, By.id(bde.getProperty("resultid")));
  		  if(ermg.get(0).getText().equalsIgnoreCase(msg));
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
  		  WebElement seg = driver.findElement(By.className(bde.getProperty("formButton")));
  		  
  		  submitMessage(seg, "Please Select FollowUp Type....");
  		  new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText(s1);
  		  waitforElement(30, By.name(bde.getProperty("Type")));
  	 				 
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
  		  WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
          close.click();
  	}
  	
  	//Return the class name of the table's Lead. 
  	public String tableSizeCheck()
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
  		 List<String> sr1 = new ArrayList<String>();
  		 try
  	     {   	
  			 Class.forName("com.mysql.jdbc.Driver").newInstance();
  	         connection = DBConnection.getConnection();
  	         statement = connection.createStatement();
  	         resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password "
  	                   + "from crm_role a, crm_user b where "  + "a.role_id = b.role_id AND delete_status='no';");      
  	         while (resultSet.next()) {
  	              
  	        	 String role = resultSet.getString("role_name");
  	             String email = resultSet.getString("email_id");
  	             String pass = resultSet.getString("password");
  	             if(email.contains("srinivas") && role.contains("BDM"))
  	             {
  	                   sr1.add(email);  sr1.add(pass);
  	    		       driver.get(config.getProperty("url"));
  	    		       help.login(sr1.get(0),sr1.get(1));   
  	       		 
  	    		       String user = driver.findElement(By.className("user_name")).getText();
  	    		       Reporter.log("<p>user " + user);
  	    		       if (user.contains("Hi ! BDM"))
  	       		    	   Reporter.log("<p>  ++++++++ Logged in as BDM user ++++++++++");
  	       		       else
  	       		    	   Assert.fail("You have not logged in as BDM user.");
  	       		    
  	    		       navigatePage(Linkname, Containername);
  	       		       searchLead(Leadno);
  	       		       if (tableSizeCheck().contains("dataTables_empty"))    	
  	       		    	   Assert.fail("Expected Lead of QUOTE or PROPOSAL " + Leadno +" is not present in Management "+ Linkname);
  	       		       else 
  	       		    	   Reporter.log("<p> The Expected Lead  : " + Leadno + " is also available in BDM "+ Linkname);
  	       		    
  	       		       WebElement table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElement(By.tagName(bde.getProperty("tableTr")));
  	       		       table.findElements(By.tagName(bde.getProperty("tagTd"))).get(5).findElement(By.className("upload")).click();
  	       		       sleep(4);  
  	             }
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
  		 name.sendKeys("BDE Lead");
  		 sleep(1);
  		 
  		 WebElement des = driver.findElement(By.name(s2));
  		 des.sendKeys("Uploading Quote or Proposal ");
  		 sleep(1);
  		 
  		 WebElement browse = driver.findElement(By.name(s3));
  		 String path  =  System.getProperty("user.dir") + "\\src\\testData\\invaliedtextfile.txt";
  		 browse.sendKeys(path);
  		 
  		 WebElement upload = driver.findElement(By.id(bde.getProperty("formButton")));
  		 upload.click();	
  		 
  		 WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
  		 close.click();
  		 sleep(4);
  		 
  		 driver.findElement(By.className("user_logout")).click();
  		 sleep(5);
  		 
  	 }

    ////@Test //UI Functionality of the BDE Module.
  	public void LC_TS_43_ExpandCollapse() throws Exception
  	{	
  		//This List tree contains all Main Links of BDE Module , adds these in to "lisub" List.
  	    
      	String user = driver.findElement(By.className("user_name")).getText();
      	Reporter.log("<p>user " + user);
      	if (user.contains("Hi ! BDE"))
      		Reporter.log("<p>  ++++++++ Logged in as BDE user ++++++++++");
      	else
      		Assert.fail("You have not logged in as BDE user.");
      	
      	//This tree checks for the tree links of BDE page
  		sidetreemenuverify(3);
        navigatePage("Research On Company", "Lead Research");
        help.pageEntries();
  		help.sorting();
  		help.searchtable();
          
        navigatePage("Work on Lead", "Work on Lead");
        help.pageEntries();
  		help.sorting();
  		help.searchtable();
         	
  		navigatePage("All FollowUps", "All Followups"); 
  		help.pageEntries();
  		help.sorting();
  	    help.searchtable();
  
  	   /* navigatePage("Lead Close", "Lead Close");
  	    help.pageEntries();
  		help.sorting();
  	    help.searchtable();
  	        
  	    navigatePage("Cold Storage", "Cold Storage");
  	    help.pageEntries();
  		help.sorting();
  	    help.searchtable();*/
  	        
          
  	}
  	
  
  	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
   ////@Test(invocationCount = 4)
  	public void LC_TS_44_researchOnCompany() throws Exception
  	{
  		navigatePage("Research On Company", "Lead Research");	
  		Reporter.log("<p> ######### Navigated to  ResearchOnCompany Form ##########\n");   
        
  		//Fills the research on Company Form.
  		if (LeadSelection("random", "segregate") == true)
  		{
  		   sleep(2);
  		   if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Research on Lead"))
  	  		{
  			    sleep(2);
  				WebElement seg = driver.findElement(By.id(bde.getProperty("segButton1")));
  				submitMessage(seg, "Please Select Company Fund Status");
  				new Select(driver.findElement(By.name(bde.getProperty("resFund")))).selectByVisibleText("Seed Funded");
  				sleep(2);
  					  
  				submitMessage(seg , "Please Select Company Status");
  				new Select(driver.findElement(By.name(bde.getProperty("resCompany")))).selectByVisibleText("Stable Growth");
              	sleep(2);
              
  				submitMessage(seg , "Please Leave a Comment");
  				WebElement cmt = driver.findElement(By.name(bde.getProperty("resComment")));
  				cmt.sendKeys("Research Phase completed as per the schedule.");
  				sleep(2);
  					 
  				submitMessage(seg , "Segregated Successfully and Lead Moved to Work Phase");			  
  				WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
  				close.click();		
  	  		}else
  			 	Assert.fail("Navigation of Reasearch on Lead page Failed");
  		}
  	     //Check for Lead not Present in Research Phase
  	     navigatePage("Research On Company", "Lead Research");
  	     searchLead(randomLead);
  	     if (tableSizeCheck().contains("dataTables_empty"))
  	    	 Reporter.log("<p> The research Lead : " + randomLead +" is not present in Research Phase.");
  		 else 
  			 Assert.fail("The Lead " + randomLead + "is still present in research phase");
  		 
  	     //Check for Lead Presence in Work on Lead Phase.
  		 navigatePage("Work on Lead", "Work on Lead");
  		 searchLead(randomLead);
  		 if (tableSizeCheck().contains("dataTables_empty"))
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
  	
    //This test method checks functionality of Work phase by giving today's date
  	////@Test(invocationCount = 1)
    public void LC_TS_45_1_workPhaseForTodaysDate() throws Exception
  	{
  		date = new Date();	
  		navigatePage("Work on Lead", "Work on Lead");

  	    if(LeadSelection("random", "work") == true)
  	    {
  	    		if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Work on Lead"))
  				{	
  	    			sleep(2);
  	    			fillingForm("Introductory Mail","Selection of today's date.",simple.format(date));	 
  				}
  	    		else
  	    			Reporter.log("<p> The navigation to work on Lead Form is failed <p> or Table size might be empty.");
  	    
  	    
  	    		//This Block checks for Lead worked is not present in work on Lead Phase.
  	    		navigatePage("Work on Lead", "Work on Lead");
  	    		searchLead(randomLead);
  	    		if (tableSizeCheck().contains("dataTables_empty"))
  	    			Reporter.log("<p> The Work Lead :" + randomLead +" is not present in Work Phase. ");
  	    		else 
  	    			Reporter.log("Expected " + randomLead + " Work on Lead is still in Work phase.");
  	    
  	    		// This Block Search for the lead present in Todays FollowUp. 
  	    		navigatePage("Today's FollowUp", "Today Followups");
  	    		searchLead(randomLead);
  	    		if (tableSizeCheck().contains("dataTables_empty")) 	
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
  	    		if (tableSizeCheck().contains("dataTables_empty"))	
  	    			Assert.fail("Expected Lead is not present in All Follow up");
  	    		else 
  	    			Reporter.log("<p> The research Lead : " + randomLead + " is also available in All FollowUps");
  	    		sleep(2);
  	    		trackIT();	
  	    }
  	}
  	
  	//This test method checks functionality of Work phase by giving later date
 	////@Test(invocationCount = 2)
  	public void LC_TS_45_2_workPhaseForLaterDate()
  	{   
  	    cal = Calendar.getInstance();
  		cal.add(Calendar.DAY_OF_YEAR, 2);
  	    later = cal.getTime();
  	
  		navigatePage("Work on Lead", "Work on Lead");
  		//This block takes one random lead from table and fill the work phase form by giving current date + 2.
  	    if (LeadSelection("random", "work") == true) 
  	    {
  	    	if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Work on Lead"))
  	    	{
  	    		sleep(2);
  	    		fillingForm("Introductory Mail","Selection of later date",simple.format(later));	
  	    	}
  	    	else
  	    		Assert.fail("This form 'Work on Lead' navigation is unsuccessfull .");
  	    
  	    
  	    	//This Block checks for Lead worked is not present in work on Lead Phase.
  	    	navigatePage("Work on Lead", "Work on Lead");
  	    	searchLead(randomLead);
  	    	if (tableSizeCheck().contains("dataTables_empty"))
  	    		Reporter.log("<p> The research Lead : " + randomLead +" is not present in Research Phase.");
  	    	else 
  	    		Assert.fail("Expected Lead is not present in Work Phase");
  	    
  	    	// This Block Search for the lead present in All FollowUps.
  	    	navigatePage("All FollowUps", "All FollowUps");
  	    	searchLead(randomLead);
  	    	if (tableSizeCheck().contains("dataTables_empty"))	
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
  	}
       
  	 // This test method checks functionality of todays phase followUp 4
    @Test
     public void LC_TS_46_1_todaysFollowup4()
  	 {	
      	 date = new Date();	
      	 cal = Calendar.getInstance();
      	 cal.add(Calendar.DAY_OF_YEAR, -1);
   	     later = cal.getTime();
   	     
      	 navigatePage("Today's FollowUp", "Today Followups");
      	 if(LeadSelection("Introductory Mail", "work") == true)
      	 {
      		 sleep(4);
      		 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
      		 {
      			 fillingForm("Followup 4","selection today date",simple.format(later));		 
      		 }else
      			 Assert.fail("Navitage to todays followup form is failed.");
      	 
      	 
      		 // Check for Lead  present in Todays FollowUp. 
      		 navigatePage("Today's FollowUp", "Today Followups");
      		 searchLead(Leadno);
      		 if (tableSizeCheck().contains("dataTables_empty"))
      			 Assert.fail("<p> The todays FollowUp Lead : " + Leadno +" is not present in todays Followup.");
      		 else 
      			 Reporter.log("Lead : " + Leadno + " is still present in All FollowUp");
  			
      		 // Check for Lead presence at Cold Storage.
      		 navigatePage("Cold Storage", "Cold Storage");
      		 searchLead(Leadno);
      		 if (tableSizeCheck().contains("dataTables_empty"))    	
      			 Assert.fail("Expected Lead" + Leadno +" is not present in All Follow up");
      		 else 
      			 Reporter.log("<p> The research Lead : " + Leadno + " is also available in All FollowUps");	
      	 }
  	 }
       
    //This test method checks functionality of todays phase prospect Identify.
 	@Test(invocationCount = 1)
  	public void LC_TS_46_2_todaysFollowupProposal() throws Exception
  	{
  		 date = new Date();
  	     navigatePage("Today's FollowUp", "Today Followups");	     
  	     sleep(4);
  	     
  	     //Click on one random lead of status Introductory mail and fills the form by giving prospectType as 'Proposal'.
  		 if (LeadSelection("Introductory Mail" , "work") == true)
  		 {		
  			     help.sleep(3);
  				 if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  		 		 {   
  			
  			 		WebElement seg = driver.findElement(By.className(bde.getProperty("formButton")));
  			 		sleep(2);
  			 
  			 		submitMessage(seg, "Please Select FollowUp Type....");
  			 		new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
  			 		sleep(2);
  			 
  			 		submitMessage(seg, "Please Select Prospect Type....");
  			 		sleep(2);
  			 		new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Proposal");
  			 		sleep(2);
  		
  			 
  			 		submitMessage(seg, "Please Enter Fixed Date....");
  			 		sleep(2);
  			 		WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
  			 		fixdate.sendKeys(simple.format(date));
  			 		sleep(4);
  			 
  			 		submitMessage(seg, "Please Select Mail Id of Architect....");
  			 		sleep(2);
  			 		WebElement to = driver.findElement(By.name(bde.getProperty("to")));
  			 		List<WebElement> option =to.findElements(By.tagName(bde.getProperty("option")));
  			 		option.get(1).click();
  			 		sleep(2);
  			 
  			 		submitMessage(seg, "Please Enter Subject");
  			 		WebElement sub = driver.findElement(By.name(bde.getProperty("subject")));
  			 		sub.sendKeys("Sending Prospect Proposal");
  			 		sleep(4);
  			 
  			 		submitMessage(seg,"Please Leave A Message");
  			 		WebElement msg = driver.findElement(By.name(bde.getProperty("message")));
  			 		msg.sendKeys("This is proposal message field");
  			 		sleep(4);
  			 
  			 		submitMessage(seg,"Please Leave A Comment");
  			 		WebElement cmt = driver.findElement(By.name(bde.getProperty("Comment")));
  			 		cmt.sendKeys("This is followup comment of proposal");
  			 		sleep(4);
  			  
  			
  			 		submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
  			 		WebElement folldate = driver.findElement(By.id(bde.getProperty("Date")));
  			 		folldate.sendKeys(simple.format(date));
  			 		sleep(2);	
  			 	 
  			 		submitMessage(seg,"Proposal Request Send Successfully"); 
  			 		sleep(2);
  	         
  			 		WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
  			 		close.click();
  			
  		 		 }	
  		 		 else
  		 			 Assert.fail("Failed to navigate to todays Followup form");
  		 
  		 
  				 driver.findElement(By.className("user_logout")).click();
  				 sleep(4);
  		 
  				 proposalQuotePage("Proposal Upload", "Leads for Proposal Upload");
  				 sleep(4);
  				 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Proposal Upload"))
  				 {
  					 ProposalQuoteForm("proposalname","proposaldescription","proposal");
  				 } 
  		 
  				 //This block Checks for Lead Presence in Todays FollowUp and also proposal comment.
  		 
  				 driver.get(config.getProperty("url"));
  				 login(config.getProperty("bdename"),config.getProperty("bdepass")); 
  				 sleep(4);
  		 
  				 navigatePage("Today's FollowUp", "Today Followups");
  				 searchLead(Leadno);
  				 if (tableSizeCheck().contains("dataTables_empty"))	
  					 Assert.fail("Expected Lead is not present in All Follow up");
  				 else 
  					 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
  				 trackIT();
  		 
  				 //This block checks whether Lead is Present in All FollowUp.
  		 
  				 navigatePage("All FollowUps", "All Followups");
  				 searchLead(Leadno);
  				 if (tableSizeCheck().contains("dataTables_empty"))	
  					 Assert.fail("Expected Lead is not present in All Follow up");
  				 else 
  					 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in today's FollowUps");
  				 trackIT(); 
  		 }
  	
  	 }
  	 
  	 // This test method checks functionality of All FollowUp phase followUp 4
     ////@Test
  	 public void LC_TS_47_TC005_AllFollowups4() throws Exception
  	 {
  		 date = new Date();	
      	 cal = Calendar.getInstance();
      	 cal.add(Calendar.DAY_OF_YEAR, -1);
   	     later = cal.getTime();
   	     
      	 navigatePage("All FollowUps", "All Followups"); 
      	 sleep(4);
  		 if (LeadSelection("Introductory Mail" , "work") == true) 
  		 {
  			 	 sleep(3);
  				 if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  		 		 {
  			 		sleep(2);
  			 		fillingForm("Followup 4","Sending Lead to Cold Phase",simple.format(later));		 
  		 		 }else
  		 			 Assert.fail("Navitage to todays followup form is failed."); 
  		 
  				 navigatePage("All FollowUps", "All Followups");
  				 if (tableSizeCheck().contains("dataTables_empty"))	
  					 Assert.fail("Expected Lead is not present in All FollowUp");
  				 else 
  					 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in AllFollowUP.");
  				 trackIT(); 
  		 
  				 navigatePage("Cold Storage", "Cold Storage");
  				 searchLead(Leadno); 
  				 if (tableSizeCheck().contains("dataTables_empty"))	
  					 Assert.fail("Expected Lead is not present in ColdStorage");
  				 else 
  					 Reporter.log("<p> The todays Followup Lead is : " + Leadno + " is available in Cold Storage.");
  		 }
  	 }
  	 
      // This test method checks functionality of All FollowUp phase prospect Identify of Quote
     ////@Test
  	 public void LC_TS_47_TC002_AllFollowupsQuoteUpload() throws Exception
  	 {
  		 date = new Date();	
      	 cal = Calendar.getInstance();
      	 cal.add(Calendar.DAY_OF_YEAR, 2);
   	     later = cal.getTime();
   	     
      	 navigatePage("All FollowUps", "All Followups");
      	 
  		 if(LeadSelection("Introductory Mail" , "work") == true )
  		 {
  				 if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  				 {
  					 sleep(2);
  					 WebElement seg = driver.findElement(By.id(bde.getProperty("formButton")));
  			 
  					 sleep(2);
  					 submitMessage(seg, "Please Select FollowUp Type");
  					 new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
  					 sleep(2);
  			 
  					 submitMessage(seg, "Please Select FollowUp Type");
  					 new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Quote");
  					 sleep(2);
  			 
  					 submitMessage(seg, "Please Enter Fixed Date");
  					 WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
  					 fixdate.sendKeys(simple.format(date));
  					 sleep(2);
  			 
  					 submitMessage(seg,"Please Leave A Comment");
  					 WebElement cmt = driver.findElement(By.name(bde.getProperty("Comment")));
  					 cmt.sendKeys("This is followup comment of Quote");
  					 sleep(2);		  
  			
  					 submitMessage(seg,"Please Enter Next FollowUp Date");			  	  					 
  					 WebElement folldate = driver.findElement(By.id(bde.getProperty("Date")));
  					 folldate.sendKeys(simple.format(later));
  					 sleep(2);	
  			 	 
  					 submitMessage(seg,"Quote Details Successfully Updated");  
  					 sleep(4);
  			 
  					 WebElement close =  driver.findElement(By.tagName("button"));
  					 close.click(); 
  			 
  				 }
  				 else
  					 Assert.fail("Navigation to page All Followup failed");
  		 
  		 
  				 driver.findElement(By.className("user_logout")).click();
  				 sleep(6);
  		 
  				 proposalQuotePage("Quote Upload", "Leads for Quote Upload");
  				 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Quote Upload"))
  					 ProposalQuoteForm("quotename","quotedescription","quote");  
  				 else 
  					 Assert.fail("Not navigated.");
  		 
  		 
  				 //This block check for the lead present in All FollowUp also verify the Track It comments
  				 driver.get(config.getProperty("url"));
  				 login(config.getProperty("bdename"),config.getProperty("bdepass"));
  				 browsererror();
  				 sleep(2);
  				 navigatePage("All FollowUp", "All Followups");
  				 sleep(2);
  				 searchLead(Leadno);
  				 if (tableSizeCheck().contains("dataTables_empty"))	
  					 Assert.fail("Expected Lead is not present in All FollowUp");
  				 else 
  					 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
  				 trackIT(); 
  				 
  		 }
  	 }
    
  	   @Test
       public void LC_TS_47_TC003_AllFollowupclose()
       {
      	 navigatePage("All FollowUps", "All Followups");    
      	 sleep(4);
      
      	 if (LeadSelection("Introductory Mail" , "work") == true) 
      	 {
      			 if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
      			 {
      				 sleep(4);
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
      			 if (tableSizeCheck().contains("dataTables_empty"))	
      				 Assert.fail("Expected Lead is not present in All FollowUp");
      			 else 
      				 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
      	 }
       }	 
  
      // This test method checks functionality Todays FollowUp. Current date leads present or not
  	 ////@Test
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
  			 
  			 if (tableSizeCheck().contains("dataTables_empty"))
  			 {
  				 navigatePage("Today's FollowUp", "Today Followups");
  			     searchLead(simple.format(date));
  			     if(tableSizeCheck().contains("dataTables_empty"))
  				    Reporter.log("There were no leads in All FollowUp table to verify this senario.");
  			     else
  			    	Assert.fail("There is some mis match in size of Todays Followup");
  			 }
  			 else
  			 {
  				 List<WebElement> tds = All.get(i).findElements(By.tagName(bde.getProperty("tagTd")));
  				 l1.add(tds.get(0).getText() + " " + tds.get(1).getText() + " " +tds.get(2).getText());
  			 }
  		 }   
  		 
  		 navigatePage("Today's FollowUp", "Today Followups");
  		 searchLead(simple.format(date));
  		 List<WebElement> Today = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
  		 
  		 for(int j = 0; j < All.size(); j++)
  		 {
  			 List<WebElement> tds1 = Today.get(j).findElements(By.tagName(bde.getProperty("tagTd")));
  			 if(tds1.get(j).getText().equals("No matching records found") || tds1.get(j).getText().equals("No data available in table")){
  				 Reporter.log("<p>" + "No matching records found");
  			 }else{
  		     l2.add(tds1.get(0).getText() + " " + tds1.get(1).getText() + " " +tds1.get(2).getText());
  		 
  		 Reporter.log("<p>  AllFollowUp table size:" + Today.size() + "Todays FollowUp table size:" + All.size());
  		 
  		 if(All.size() == Today.size())
  		 {
  			 for(int k = 0; k < All.size(); k++)
  				 if(l1.get(k).equals(l2.get(k)))
  					Reporter.log("<p> "+ l1.get(k) + "is found in todays Follow ups");
  				 else
  					Reporter.log("<p>" + l1.get(k) + "is not found in todays Follow ups");
  		 }
  			 }
  		 	}
  	 }
  	
  	////@Test // This checks the To Field check of prospect Identify phase.
  	public void LC_TS_47_TC001_UIToFieldCheck()
  	{
  		 date = new Date();
  	     navigatePage("Today's FollowUp", "Today Followups");
  	     searchLead("Introductory Mail");
  	     
  	     if (tableSizeCheck().contains("dataTables_empty"))	
  			 Reporter.log("The leads in All FollowUp is empty.");
  		 else 
  		 {
  			 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 	
  	     
  			 List<WebElement> table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElements(By.tagName(bde.getProperty("tableTr")));
  			 WebElement tr= table.get(random(table.size()));
  			 List<WebElement> tdlis1 = tr.findElements(By.tagName(bde.getProperty("tagTd")));
  			 String leadService= tdlis1.get(3).getText();
  			 tdlis1.get(6).findElement(By.className(bde.getProperty("leadProceed"))).click();
  			 sleep(4);
  		 
  			 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  			 {   
  		     	new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
  		    	sleep(2);
  		    	
  		    	new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Proposal");
  			    sleep(2);
  	
  			    WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
  			    fixdate.sendKeys(simple.format(date));
  			    sleep(4);
  		  
  			    List<WebElement> tolist =  driver.findElement(By.name(bde.getProperty("to"))).findElements(By.tagName(bde.getProperty("option")));
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
  	   
    ////@Test
     public void LC_TS_47_TC006_coldStorage() 
  	 { 
      	 //This block checks for lead moved from closed phase to BD
  		 navigatePage("Cold Storage", "Cold Storage");
  		 System.out.println("HII" + LeadSelection("random" , "analyse"));
  		 //Lead Selection is predefined method randomly selects one lead and click the button the lead.
  	     if (LeadSelection("random" , "analyse") == false)
  	     {
  			 System.out.println(" Table is empty");
  	     }
  	     else
  	     {
  			//This block checks for lead moved from closed phase to BD
			 navigatePage("Cold Storage", "Cold Storage");
			 searchLead(randomLead);
			 if (tableSizeCheck().contains("dataTables_empty"))	
				 Reporter.log("<p> The All Followup Lead is : " + randomLead + " is not available Cold Storage.");
			 else 
				 Assert.fail("Expected Lead is still present in Cold Storage after Confirmation.");
  			
  			 //This block checks for lead moved from All FollowUp.
  			 navigatePage("All FollowUps", "All Followups");
  			 searchLead(randomLead);
  			 if (tableSizeCheck().contains("dataTables_empty"))	
  				 Reporter.log("<p> The Cold Phase lead is : " + randomLead + " is not available in All FollowUp.");
  			 else 
  				 Assert.fail("Expected Lead is still present in All FollowUp after Confirmation.");
  			 
  			 //Search Closed Lead in DB from Search Leads
  			 // Check whether the lead is present in the database or in Lead Search cold storage
  			 if(driver.findElement(By.id(bde.getProperty("leadsearchlink_id"))).isDisplayed()) 
  			 {
  				 driver.findElement(By.id(bde.getProperty("leadsearchlink_id"))).click();
  				 sleep(4); 
  			 
  				 // Switching to Child Window
  				 String parentWindow = driver.getWindowHandle();
  				 for(String childWindow : driver.getWindowHandles()) 
  				 {
  					 driver.switchTo().window(childWindow);
  				 }
  				 
  				 sleep(2);
  				 Reporter.log("<p>" + "Clicking on the Lead Search and verifying the confirmed lead of cold storage.");
  				 WebElement reqfields = driver.findElement(By.id(bde.getProperty("requiredfields_id"))).findElements(By.tagName(bde.getProperty("servicename_tag"))).get(0);
  				 reqfields.findElement(By.tagName(bde.getProperty("searchtag"))).click();
  				 sleep(2);	 
  				 driver.findElement(By.cssSelector(bde.getProperty("filteroption_css"))).click();
  				 sleep(2);	
  			 
  				 // Selecting Cold storage in filter options
  				 WebElement status = driver.findElement(By.id(bde.getProperty("statusid_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4);
  				 status.findElement(By.tagName(bde.getProperty("searchtag"))).click();
  						 
  				 // Clicking on search button
  		         sleep(2);
  		         driver.findElement(By.id(bde.getProperty("registerbutton_id"))).click();
  		         sleep(4);
  						 
  		         String[] search = randomLead.split(" ");
  		         Reporter.log("<p>" + "Searching the lead in Lead Search which is confirmed in cold storage phase .");
  		         searchLead(search[1] + " " + search[2]);
  					
  		         if (tableSizeCheck().contains("dataTables_empty"))	
  		        	 Assert.fail("Expected Lead is not present in Cold Storage.");
  		         else 
  		        	 Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp."); 	
  					
  		         //Closing the Child Window
  		         driver.close();
  					 
  		         //Switching to Parent Window
  		         driver.switchTo().window(parentWindow);
  			 }
  	     }
  	     
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
  
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 