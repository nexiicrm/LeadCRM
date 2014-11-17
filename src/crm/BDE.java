package src.crm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
  			sleep(1);
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
        if(ermg.get(0).getText().equalsIgnoreCase(msg))
        	Reporter.log("<p> The message :" + msg +" is displayed....."); 
        if(ermg.get(0).getText().contains("Please Upload the Proposal or Quote First"))
        	Reporter.log("Can't close the Lead with out uploading Quote or Proposal.");  
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
  	                   + "from crm_role a, crm_user b where "  + "a.role_id = b.role_id AND b.delete_status='no';");      
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
  	    		       help.sleep(5);
  	       		       searchLead(Leadno);
  	       		       if (tableSizeCheck().contains("dataTables_empty"))    	
  	       		    	   Assert.fail("Expected Lead of QUOTE or PROPOSAL " + Leadno +" is not present in Management "+ Linkname);
  	       		       else 
  	       		    	   Reporter.log("<p> The Expected Lead  : " + Leadno + " is also available in BDM "+ Linkname);
  	       		    
  	       		       WebElement table = driver.findElement(By.tagName(bde.getProperty("tableBody"))).findElement(By.tagName(bde.getProperty("tableTr")));
  	       		       table.findElements(By.tagName(bde.getProperty("tagTd"))).get(5).findElement(By.className("upload")).click();
  	       		       sleep(1);  
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
  		 sleep(1);
  		 
  		 driver.findElement(By.className("user_logout")).click();
  		 sleep(2);
  		 
  	 }
  	 
  	 public void close(int y,String c,String data) throws Exception
	 {
  		 help.sleep(3);
		 //for getting mailid,password from database
		 try
		 { 
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
			 connection = DBConnection.getConnection();
			 statement = connection.createStatement();
			 resultSet = statement.executeQuery("select a.role_name, b.email_id, b.password "+ "from crm_role a, crm_user b where " 
					 + "a.role_id = b.role_id AND a.role_name = 'Management' AND b.delete_status='no'Limit 1;"); 
			 while (resultSet.next())
			 { 
				 String email = resultSet.getString("email_id");
				 String pass = resultSet.getString("password");
				 //login into management module
				 help.login(email,pass);
			 }
		 }
		 catch (Exception e)
		 { 
            e.printStackTrace();
		 }
		 help.sleep(1);
		 help.expand();
		 help.sleep(1);
		 
		 if(driver.findElement(By.id(c)).isDisplayed())
		 {
				  //clicking on customer or lost competition
			  	  driver.findElement(By.id(c)).click();
			  	  
			  	  //creating arraylist
			      ArrayList<Integer> li= new ArrayList<Integer>();
				  help.sleep(1);
				  
				  //sending data into searchbox
				  WebElement search = driver.findElement(By.id(bde.getProperty("searchid"))).findElement(By.tagName(bde.getProperty("searchtag")));
				  if(search == null)
				    Assert.fail("The Search Text Box is not Present");
				  else
				    search.sendKeys(data);
				  
				  //getting data from table through searchbox data
				  List<WebElement> tablerecords1= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
				  for(int b=0;b<tablerecords1.size();b++)
				  {
					  String s1=tablerecords1.get(b).findElements(By.tagName(bde.getProperty("tablecol_tagname"))).get(0).getText() ;
					  int z = Integer.parseInt(s1);
					  li.add(b, z);
				  }
				  
				  //validation
				  if(li.contains(y))
				  Reporter.log("<p>" +"closed lead in BDE module is present in management module");
				  else
				  Assert.fail("closed lead in BDE module is not  present in management module");
		 	}
	 }

    @Test //UI Functionality of the BDE Module.
  	public void a_LC_TS_43_ExpandCollapse() throws Exception
  	{	
    	help.expand();
  		List<WebElement> tree=driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
  		
  		for(int i=0;i<(tree.size()-1);i++)
  		{
  			if(tree.get(i).getText().contains("Search Leads"))
  			{
  				continue;
  			}
  			tree.get(i).click();
  			help.sleep(1);
  			help.sorting();
  			help.sleep(1);
  			help.pageEntries();
  			help.searchtable();
  		}
          
  	}
  	
  
  	// This researchOnCompany test, checks the functionality of Research On Company Page, 'Lead Research' Form.
    @Test(invocationCount = 5)
  	public void b_LC_TS_44_researchOnCompany() throws Exception
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
     }
    }
  	
    //This test method checks functionality of Work phase by giving today's date
  	@Test(invocationCount = 2)
    public void c_LC_TS_45_workPhaseForTodaysDate() throws Exception
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
    @Test(invocationCount = 3)
    public void d_LC_TS_45_workPhaseForLaterDate()
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
          sleep(3);
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
     public void e_LC_TS_46_todaysFollowup4()
  	 {	
      	 date = new Date();	
      	 cal = Calendar.getInstance();
      	 cal.add(Calendar.DAY_OF_YEAR, -1);
   	     later = cal.getTime();
   	     
      	 navigatePage("Today's FollowUp", "Today Followups");
      	 if(LeadSelection("Introductory Mail", "work") == true)
      	 {
      		 sleep(3);
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
  	public void f_LC_TS_46_todaysFollowupProposal() throws Exception
  	{
  		 date = new Date();
  	     navigatePage("Today's FollowUp", "Today Followups");	     
  	     sleep(1);
  	     
  	     //Click on one random lead of status Introductory mail and fills the form by giving prospectType as 'Proposal'.
  		 if (LeadSelection("Introductory Mail" , "work") == true)
  		 {		
  			     help.sleep(1);
  				 if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  		 		 {   
  			
  			 		WebElement seg = driver.findElement(By.className(bde.getProperty("formButton")));
  			 		sleep(1);
  			 
  			 		submitMessage(seg, "Please Select FollowUp Type....");
  			 		new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
  			 		sleep(1);
  			 
  			 		submitMessage(seg, "Please Select Prospect Type....");
  			 		sleep(1);
  			 		new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Proposal");
  			 		sleep(1);
  		
  			 
  			 		submitMessage(seg, "Please Enter Fixed Date....");
  			 		sleep(1);
  			 		WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
  			 		fixdate.sendKeys(simple.format(date));
  			 		sleep(2);
  			 
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
  			 		sleep(1);
  	         
  			 		WebElement close =  driver.findElement(By.tagName(bde.getProperty("formButton")));
  			 		close.click();
  			
  		 		 }	
  		 		 else
  		 			 Assert.fail("Failed to navigate to todays Followup form");
  		 
  		 
  				 driver.findElement(By.className("user_logout")).click();
  				 sleep(1);
  		 
  				 proposalQuotePage("Proposal Upload", "Leads for Proposal Upload");
  				 sleep(1);
  				 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Proposal Upload"))
  				 {
  					 ProposalQuoteForm("proposalname","proposaldescription","proposal");
  				 } 
  		 
  				 //This block Checks for Lead Presence in Todays FollowUp and also proposal comment.
  		 
  				 driver.get(config.getProperty("url"));
  				 login(config.getProperty("bdename"),config.getProperty("bdepass")); 
  				 sleep(1);
  		 
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
 	
	@Test // This checks the To Field check of prospect Identify phase.
  	public void g_LC_TS_47_UIToFieldCheck()
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
  			 sleep(1);
  		 
  			 if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
  			 {   
  		     	new Select(driver.findElement(By.name(bde.getProperty("Type")))).selectByVisibleText("Prospect Identify");
  		    	sleep(1);
  		    	
  		    	new Select(driver.findElement(By.name(bde.getProperty("prospectType")))).selectByVisibleText("Proposal");
  			    sleep(1);
  	
  			    WebElement fixdate = driver.findElement(By.id(bde.getProperty("fixon")));
  			    fixdate.sendKeys(simple.format(date));
  			    sleep(2);
  		  
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
  	 
  	
      // This test method checks functionality of All FollowUp phase prospect Identify of Quote
  	@Test
    public void h_LC_TS_47_AllFollowupsQuoteUpload() throws Exception
    {
     date = new Date(); 
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 2);
         later = cal.getTime();
         
        navigatePage("All FollowUps", "All Followups");
        
     if(LeadSelection("Introductory Mail" , "work") == true )
     {
        help.sleep(1);
       if(driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().equalsIgnoreCase("Followup on Lead"))
       {
        sleep(1);
        WebElement seg = driver.findElement(By.id(bde.getProperty("formButton")));
      
        sleep(1);
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
        sleep(1);
      
        driver.findElement(By.tagName("button")).click();
       }
       else
        Assert.fail("Navigation to page All Followup failed");
     
     
       driver.findElement(By.className("user_logout")).click();
       sleep(1);
     
       proposalQuotePage("Quote Upload", "Leads for Quote Upload");
       if (driver.findElement(By.tagName(bde.getProperty("pagetag"))).getText().contains("Quote Upload"))
        ProposalQuoteForm("quotename","quotedescription","quote");  
       else 
        Assert.fail("Not navigated.");
     
     
       //This block check for the lead present in All FollowUp also verify the Track It comments
       driver.get(config.getProperty("url"));
       login(config.getProperty("bdename"),config.getProperty("bdepass"));
       browsererror();
       sleep(2);
       navigatePage("All FollowUps", "All Followups");
       sleep(1);
       searchLead(Leadno);
       if (tableSizeCheck().contains("dataTables_empty")) 
        Assert.fail("Expected Lead is not present in All FollowUp");
       else 
        Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
       
      
       trackIT(); 
       
       
     }
    }
    
  	@Test
    public void i_LC_TS_47_AllFollowupclose() throws Exception
    {
     navigatePage("All FollowUps", "All Followups");    
     sleep(4);
   
     if (LeadSelection("Prospect Identify" , "work") == true) 
     {
    	 help.sleep(1);
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
        
        seg.click();
        List<WebElement> ermg =driver.findElement(By.id(bde.getProperty("resultid"))).findElements(By.tagName(bde.getProperty("resulttag")));
        sleep(4);
           
        Reporter.log("The Message after closing Lead" +ermg.get(0).getText());
        if((ermg.get(0).getText()).contains("Successfully Updated..."))
        {
        	
         sleep(3);
         WebElement close =  driver.findElement(By.tagName("button"));
         close.click();
            
         //This block checks for Lead is moved for All FollowUpclose phase to Lead Close phase. 
         navigatePage("Lead Close", "Closed Phase");
         searchLead(Leadno);
         if (tableSizeCheck().contains("dataTables_empty")) 
          Assert.fail("Expected Lead is not present in All FollowUp");
         else 
          Reporter.log("<p> The All Followup Lead is : " + Leadno + " is available in All FollowUp.");
        }
       
        
       }  
     } 
   }
  
      // This test method checks functionality Todays FollowUp. Current date leads present or not
  	 @Test
  	 public void j_LC_TS_47_confirmLeadsOfTodaysDate()
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
  	
  
  	
  	 // This test method checks functionality of All FollowUp phase followUp 4
     @Test
 	 public void k_LC_TS_47_AllFollowups4() throws Exception
 	 {
 		 date = new Date();	
     	 cal = Calendar.getInstance();
     	 cal.add(Calendar.DAY_OF_YEAR, -1);
  	     later = cal.getTime();
  	     
     	 navigatePage("All FollowUps", "All Followups"); 
     	 sleep(1);
 		 if (LeadSelection("Introductory Mail" , "work") == true) 
 		 {
 			 	 sleep(1);
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
 	 
  	   
     @Test
     public void l_LC_TS_47_coldStorage() 
  	 { 
      	 //This block checks for lead moved from closed phase to BD
  		 navigatePage("Cold Storage", "Cold Storage");
  		 //Lead Selection is predefined method randomly selects one lead and click the button the lead.
  	     if (LeadSelection("random" , "analyse") == false)
  	     {
  	    	Reporter.log("<p>" + " Table is empty");
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
  				 sleep(2); 
  			 
  				 // Switching to Child Window
  				 String parentWindow = driver.getWindowHandle();
  				 for(String childWindow : driver.getWindowHandles()) 
  				 {
  					 driver.switchTo().window(childWindow);
  				 }
  				 
  				 sleep(1);
  				 Reporter.log("<p>" + "Clicking on the Lead Search and verifying the confirmed lead of cold storage.");
  				 WebElement reqfields = driver.findElement(By.id(bde.getProperty("requiredfields_id"))).findElements(By.tagName(bde.getProperty("servicename_tag"))).get(0);
  				 reqfields.findElement(By.tagName(bde.getProperty("searchtag"))).click();
  				 sleep(1);	 
  				 driver.findElement(By.cssSelector(bde.getProperty("filteroption_css"))).click();
  				 sleep(1);	
  			 
  				 // Selecting Cold storage in filter options
  				 WebElement status = driver.findElement(By.id(bde.getProperty("statusid_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4);
  				 status.findElement(By.tagName(bde.getProperty("searchtag"))).click();
  						 
  				 // Clicking on search button
  		         sleep(1);
  		         driver.findElement(By.id(bde.getProperty("registerbutton_id"))).click();
  		         sleep(1);
  						 
  		         String[] search = randomLead.split(" ");
  		         Reporter.log("<p>" + "Searching the lead in Lead Search which is confirmed in cold storage phase .");
  		         searchLead(search[1] + " " + search[2]);
  					
  		         if (tableSizeCheck().contains("dataTables_empty"))	
  		        	 Assert.fail("Expected Lead is not present in Cold Storage.");
  		         else 
  		        	 Reporter.log("<p> The All Followup Lead is : " + randomLead + " is available in search leads."); 	
  					
  		         //Closing the Child Window
  		         driver.close();
  					 
  		         //Switching to Parent Window
  		         driver.switchTo().window(parentWindow);
  			 }
  	     }
  	     
  	 } 
     
     @Test                                                     // in manual test case LC_TS_48     closed phase
	 public void m_LC_TS_48_closedPhasebutton() throws Exception
	 {
		 //////////// checking for the close button for each lead /////////////
		 help.expand();
		 help.sleep(1);
	  
		 if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
		 {
			 //clicking on closed phase link
			 driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
			 help.sleep(1);
	  		
			 // getting table records
			 List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
			 
			 //validation 
			 if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
			 
			 //for getting no of  columns of 1st lead
			 List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
			 if(tablerecords2.size()>1)
			 {
				 //getting show dropdown options
				 List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
				 
				 //clicking on lastentry(100) of show dropdown
				 show.get(show.size()-1).click();
				 
				 //Checking for the close button for each lead
				 int check=0;
				 
				 do
				 {
					 //clicking on pagination next button
					 if(check!=0)
						 driver.findElement(By.id("example_next")).click();
					 
					 //getting table records
					 List<WebElement> tablerecords3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
					 int count=0;
					 for(int i=0; i<tablerecords3.size(); i++) 
					 {
						 //Checking for the close button 
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
	 }
	 @Test                                                         // in manual test case LC_TS_48     closed phase
	 public void n_LC_TS_48_closedCustomer() throws Exception 
	 {
		 ///////////////// closing the lead by selecting customerslist /////////////////////
		 help.expand();
	  	 help.sleep(1);
	  	 if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	  	 {
	  		 //clicking on closed phase link
	  		 driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  		 help.sleep(3);
	  		 
	  		 //getting table records
	  		 List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		
	  		 //validation 
	  		 if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
	  		 
	  		 //getting no of columns of 1st lead
	  		 List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		 if(tablerecords2.size()>1)
	  		 {
	  			//getting show dropdown options
	  			List<WebElement> show=driver.findElement(By.name(bde.getProperty("showbox_name"))).findElements(By.tagName(bde.getProperty("showbox_tagname")));
	  			
	  			//click on lastentry(100) of the show dropdown
	  			show.get(show.size()-1).click();
	  			
	  			//random selection of close button
	  			int o=random(tablerecords.size());
	  			
	  			//getting  details of random lead
	  			List<WebElement>leaddata=tablerecords.get(o).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  			
	  			//getting random lead id no
	  			String s=leaddata.get(0).getText();
	  			
	  			//getting 3field deatails of random lead
	  			String data=  leaddata.get(0).getText() + " " + leaddata.get(1).getText() + " " +leaddata.get(2).getText();
	  			
	  			//converting lead id string to integer
	  			int y=Integer.parseInt(s);
	  			Reporter.log("<p>" +"closed lead id:"+y);
	  			
	  			//clicking on close button
	  			tablerecords.get(o).findElement(By.className(bde.getProperty("close_button"))).click();
	  			//Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText()+":is opened");
	  			help.sleep(1);
	  			
	  			//getting leadstatus dropdown contents
	  			List<WebElement> leadstatus=driver.findElement(By.name(bde.getProperty("leadstatus_id"))).findElements(By.tagName(bde.getProperty("dropdown_options")));
	  			
	  			//validation
	  			if(leadstatus.size()==0)
				Assert.fail("lead status id not found in close form");
	  			
	  			//clicking on customerlist( leadstatus dropdown 1st item)
	  			leadstatus.get(1).click();
	  			if(leadstatus.get(1).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item1")))
	  			{
	  				String c="customersList";
	  				
	  				//entering data into textboxes
	  				driver.findElement(By.id(bde.getProperty("project_id"))).sendKeys(sh4.getCell(0,5).getContents());
	  				driver.findElement(By.name(bde.getProperty("comment_id"))).sendKeys(sh4.getCell(0,6).getContents());
	  			 	help.sleep(1);
	  			 	
	  			 	//clicking on close button
	  				driver.findElement(By.id(bde.getProperty("closeform_button"))).click();
	  				help.sleep(2);
	  				//Reporter.log("<p>" +driver.findElement(By.className(bde.getProperty("success_message"))).getText());
	  				help.sleep(5);
	  				Actions ac = new Actions(driver);
	  				ac.moveToElement(driver.findElement(By.cssSelector(bde.getProperty("closeform_cssselector")))).build().perform();
	  				
	  				//closing the child form
	  				driver.findElement(By.cssSelector(bde.getProperty("closeform_cssselector"))).click();
	  				help.sleep(2);
	  				
	  				//clicking on logout button
	  				driver.findElement(By.className(bde.getProperty("logout_classname"))).click();
	  				
	  				//calling close method
	  				help.sleep(1);
	  				close(y,c,data);
	  			}
	  		 }else
	  			 Reporter.log("<p>"+"no data available");
	  	 }else
	  		 Assert.fail("closed phase  link not found successfully");
	 }
	 @Test                                                         // in manual test case LC_TS_48     closed phase
	 public void o_LC_TS_48_closedCompetetion() throws Exception 
	 {
		 ///////////////////// closing the lead by selecting lost competition ////////////////////////
	  		help.expand();
	  		help.sleep(1);
	  		if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	  		{
	  			//clicking on closed phase link
	  			driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  			help.sleep(1);
	  			
	  			//getting table records
	  			List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  			
	  			//validation
	  			if(tablerecords.size()==0)
	  				Assert.fail("tablebody id not found");
	  			
	  			//getting no of columns of 1st lead
	  			List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  			
	  			if(tablerecords2.size()>1)
	  			{
	  				//getting show dropdown entries
	  				List<WebElement> show=driver.findElement(By.name(bde.getProperty("showbox_name"))).findElements(By.tagName(bde.getProperty("showbox_tagname")));
	  				
	  				//clicking on last entry(100) of the show dropdown
	  				show.get(show.size()-1).click();
	  				
	  				//random selection of close button
	  				int o=random(tablerecords.size());
	  				
	  				//getting  details of random lead
	  				List<WebElement>leaddata=tablerecords.get(o).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  				
	  				//getting lead id of random lead
	  				String s=leaddata.get(0).getText();
	  				
	  				//getting 3fields data of the random lead
	  				String data=  leaddata.get(0).getText() + " " + leaddata.get(1).getText() + " " +leaddata.get(2).getText();
	  				
	  				//converting leadid string to integer
	  				int y=Integer.parseInt(s);
	  				Reporter.log("<p>" +"closed lead id:"+y);
	  				
	  				//clicking on close button
	  				tablerecords.get(o).findElement(By.className(bde.getProperty("close_button"))).click();
	  				//Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText()+":is opened");
	  				help.sleep(5);
	  				
	  				//getting leadstatus dropdown contents
	  				List<WebElement> leadstatus=driver.findElement(By.name(bde.getProperty("leadstatus_id"))).findElements(By.tagName(bde.getProperty("dropdown_options")));
	  				
	  				//validation
	  				if(leadstatus.size()==0)
	  					Assert.fail("lead status id not found in close form");
	  				
	  				//clicking on lost competition(lead status dropdown 2nd content)
	  				leadstatus.get(2).click();
	  				
	  				if(leadstatus.get(2).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item2")))
	  				{
	  					String c="lostCompetitionList";
	  					
	  					//entering data into textboxes
	  					driver.findElement(By.name(bde.getProperty("comment_id"))).sendKeys(sh4.getCell(0,6).getContents());
	  					help.sleep(1);
	  					
	  					//clicking on close button
	  					driver.findElement(By.id(bde.getProperty("closeform_button"))).click();
	  					help.sleep(2);
	  					//Reporter.log("<p>" +driver.findElement(By.className(bde.getProperty("success_message"))).getText());
	  					help.sleep(1);
	  					Actions ac = new Actions(driver);
	  					ac.moveToElement(driver.findElement(By.cssSelector(bde.getProperty("closeform_cssselector")))).build().perform();
	  					
	  					//closing the child form
	  					driver.findElement(By.cssSelector(bde.getProperty("closeform_cssselector"))).click();
	  					help.sleep(2);
	  					
	  					//clicking on logout
	  					driver.findElement(By.className(bde.getProperty("logout_classname"))).click();
	  					
	  					//calling close method
	  					close(y,c,data);
	  				}
	  			}else
	  				Reporter.log("<p>"+"no data available");
	  		}else
	  			Assert.fail("closed phase  link not found successfully");
	 }

	
 
	 @Test                                                   // in manual test case LC_TS_49 & 50 &51         leadsearch
	 public void p_LC_TS_49_searchLead() throws Exception 
	 {
		 help.expand();
		 help.sleep(1);
		 //calling search lead from helper
		 searchLead();
	 }
 
	 @Test                                                      // in manual test case LC_TS_52           leadedits     
	 public void q_LC_TS_50_leadEditbutton() throws Exception
	 {
		 /////////////////////checking for the trackit and edit button for each lead /////////////////////////////
		 help.expand();
		 
		 if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
		 {
			 //clicking on leadedits link
			 driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
			 help.sleep(1);
			 
			 //getting table records
			 List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
			 
			 //validation
			 if(trackelement.size()==0)
				 Assert.fail("table body tagname not found successfully in trackit");
			 
			 //getting no of columns 1st lead
			 List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
			 if( trackelement2.size()>1)
			 {
				 //getting show dropdown entries
				 List<WebElement> show=driver.findElement(By.name(bde.getProperty("showbox_name"))).findElements(By.tagName(bde.getProperty("showbox_tagname")));
				 
				 //clicking on lastentry(100) of the show dropdown
				 show.get(show.size()-1).click();
				 
				 //Checking for the Track it & edit button for each lead
				 int check=0;
				 
				 do
				 {
					 if(check!=0)
						 driver.findElement(By.id("example_next")).click();
					 List<WebElement> trackelement3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
					 int trackit=0;
					 int edit=0;
					 for(int i=0; i<trackelement3.size(); i++) 
					 {
						 //Checking for the Track it  button for  lead
						 if(trackelement3.get(i).findElement(By.className(bde.getProperty("tracit_button"))).isEnabled()) 
							 trackit++;
						 else
							 Assert.fail("trackit button is not found successfully");
						 
						 //Checking for the  edit button for  lead
						 if(trackelement3.get(i).findElement(By.className(bde.getProperty("edit_button"))).isEnabled()) 
							 edit++;
						 else
							 Assert.fail("edit button is not found successfully");
					 }
					 if(trackit==trackelement3.size())
						 Reporter.log("<p>"+"Trackit button is present for all leads.");
					 if(edit==trackelement3.size())
						 Reporter.log("<p>"+"edit button is present for all leads."); 
					 check++;
				 }while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
  			
			 }else
				 Reporter.log("<p>"+"no data available");
		 }else
			 Assert.fail("lead edit link not found successfully");
	 }
	 @Test                                                          // in manual test case LC_TS_52                leadedits(trackit)
	 public void r_LC_TS_50_leadEditTrackIt() throws Exception
	 {
		 ////////////////////////// TRACK IT  ///////////////////////////
		 help.expand();
		 if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
		 {
		   	//clicking on leadedits link
	  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(3);
	  		
	  		//getting the table records
	  		List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		
	  		//validation
	  		if(trackelement.size()==0)
	  			Assert.fail("table body tagname not found successfully in trackit");
	  		
	  		//getting no of columns of 1st lead
	  		List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		
	  		if( trackelement2.size()>1)
	  		{
	  			//getting show dropdown entries
	  			List<WebElement> show=driver.findElement(By.name(bde.getProperty("showbox_name"))).findElements(By.tagName(bde.getProperty("showbox_tagname")));
	  			
	  			//clicking on lastentry(100) of show dropdown
	  			show.get(show.size()-1).click();
	  			
	  			//random selection of track it button
	  			int p=random(trackelement.size());
	  			
	  			//getting details of random lead before clicking track it button
	  			List<WebElement> ls =trackelement.get(p).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  			
	  			//validation
	  			if(ls.size()==0)
	  			Assert.fail("tablecol tagname not found successfully in trackit");
	  			
	  			//creating arraylist
	  			ArrayList<String> ar = new ArrayList<String>();
	  			
	  			for (int i=0;i<ls.size();i++)
	  			{
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
	  			
	  			for (int i=0;i<ls2.size();i++)
	  			{
	  				List<WebElement> s1= ls2.get(i).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  				for(int j=0;j<s1.size();j++)
	  				{
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
	  			if(ar1.get(0).contains(ar.get(0)))
	  			{
	  				if(ar1.get(1).contains(ar.get(1)))
	  				{
	  					if(ar1.get(18).contains(ar.get(5)))
	  					{	 	 
	  						Reporter.log("<p>" +"Data is matching exactly ");
	  					}
	  				}
	  			}else
	  				Reporter.log("<p>" +"Data doesnt match ");
	  		}else
	  			Reporter.log("<p>" +"no data available ");
		 }else
			 Assert.fail("lead edit link not found successfully");
	 }

	 @Test                                                               // in manual test case LC_TS_52                leadedits(EDIT)
	 public void s_LC_TS_51_LeadEdit() throws Exception
	 {
		 ////////////////////////// EDIT ////////////////////////////////////////
		 help.expand();
		 if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
		 {
		  	//clicking on leadedits link
			driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(2);
	  		
	  		//getting table leads
	  		List <WebElement> leads = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		
	  		//validation
		  	if(leads.size()==0)
	  			Assert.fail("table body tagname not found successfully in edit");
		  	help.sleep(2);
		  	
		  	//getting columns of 1st lead
		  	List <WebElement> leads2=leads.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
		  	
		  	if(leads2.size()>1)
		  	{
		  		//getting show dropdown entries
		  		List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
		  		
		  		//clicking on lastentry(100) of show dropdown
	  			show.get(show.size()-1).click();
		  		int columns=sh4.getColumns();
		  		int rows=sh4.getRows();
		  		String data;
		  		help.sleep(2);
		  		
		  		//random selection of edit button
		  		int j=random(leads.size());
		  		
		  		//getting details of random lead before clicking edit button
		  		List<WebElement> ls =leads.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
		  		
		  		//validation
		  		if(ls.size()==0)
				Assert.fail("tablecol tagname not found successfully in edit");
		  		ArrayList<String> ar = new ArrayList<String>();
		  		
		  		for (int i=0;i<ls.size();i++)
		  		{
		  			String s1= ls.get(i).getText();
		  			ar.add(s1);  
		  		}
		  		
		  		//printing the lead details before clicking on edit button
		  		Reporter.log("<p>" +"lead before clicking on edit button is: " + ar);
		  		
		  		//clicking on edit button
		  		leads.get(j).findElement(By.className(bde.getProperty("edit_button"))).click();
		  		help.sleep(1);
		  		Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText());
		  		
		  		for(int row=2;row<=2;row++)
		  		{
					int col=0;
					help.sleep(2);
					//entering data into textboxes
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
		  		
		  		//getting update dropdowns contents
		  		List<WebElement> updatedropdowns=driver.findElement(By.className(bde.getProperty("updatedropdown_class"))).findElements(By.tagName(bde.getProperty("updatedropdown_tag")));
		  		
		  		//validation
		  		if(updatedropdowns.size()==0)
	  			Assert.fail(" updatedropdown class name not found successfully in edit");
		  		
		  		for(int a=0;a<updatedropdowns.size();a++)
		  		{
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
		  		help.sleep(1);
		  		Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("message_id"))).findElement(By.tagName(bde.getProperty("message_tagname"))).getText());
		  		help.sleep(1);
		  		
		  		//closing the edit form
		  		driver.findElement(By.className(bde.getProperty("editform_close"))).click();
		  		help.sleep(1);
		  		
		  		//getting details of lead after  editing the lead
		  		List <WebElement> leads1 = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
		  		
		  		//validation
		  		if(leads1.size()==0)
				Assert.fail("table id not found in edit");
		  		help.sleep(1);
		  		List<WebElement> ls1 =leads1.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
		  		ArrayList<String> ar1 = new ArrayList<String>();
		  		
		  		for (int i=0;i<ls1.size();i++)
		  		{
		  			String s1= ls1.get(i).getText();
		  			ar1.add(s1);  
		  		}
		  		
		  		//printing the leaddetails after editing
		  		Reporter.log("<p>" +"lead details after  editing   : " + ar1);
		  		
		  		//validation
		  		if(ar1.equals(ar))
		  		{
		  			Reporter.log("<p>" +"no modifications done");
		  		}
		  		else
		  		{
		  			for(int k=0;k<ar1.size();k++)
		  			{
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
	 }
	

  
	 @Test                                                               // in manual test case LC_TS_53 & 54                myaccount
	 public void t_LC_TS_52_changePassword() throws Exception
	 {
		 help.expand();
		 String email1=config.getProperty("bdename");
		 help.sleep(2);
		 //calling change password from helper
		 changePassword(email1);
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
 
 
 