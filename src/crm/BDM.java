package src.crm;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import com.nexiilabs.dbcon.DBConnection;

import src.testUtils.Helper;



public class BDM extends Helper {

	public static Connection connection =null;
	public static Statement statement;
	public static ResultSet rs;
	
	

	@BeforeMethod
	 public void before() throws Exception {
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		browsererror();
	
		// Login for BDM module
		help.login(config.getProperty("bdmuser"), config.getProperty("bdmpwd"));
		String user = driver.findElement(By.className(bdm.getProperty("username_class"))).getText();
		Reporter.log("<p>" +"User Logged in as:" + user);
	}

	 
	 
	 @AfterMethod
	 public void after() {
		 driver.close();
	 }
	 
	 
	 // Test Method for Follow up 4 for Cold storage phase. 
	 @Test
	 public void a_coldStorageFollowup() {
		 
		 Reporter.log("<p>" +"Testing follow up for Cold storage");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on All follow up Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).isDisplayed()) 
		 {
			 // Clicking on all follow ups link
			 driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
			 help.sleep(2);
			 Reporter.log("<p>" + "Searching leads with status as Introductory Mail.");
			 
			 // Searching with Introductory mail
			 search("Introductory Mail");
			 if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"));
				
		     else 
		     { 
		    	 // Selceting a lead from lead table
		    	 WebElement followupLead = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
				 Reporter.log("<p>" +"Selected Lead is:" + followupLead.getText());
				 if (followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) 
				 {
					 // Clicking on the follow up button
					 followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
					 help.sleep(3);
					 Reporter.log("<p>" + driver.findElement(By.cssSelector(bdm.getProperty("windowTitle_css"))).getText());
					 
					 // Selecting follow up type and entering the follow up comment
					 new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Followup 4");
					 driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Follow up 4");
					 
					 // Selecting yesterday's date for follow up 4 to send the lead to cold storage and click Proceed button
					 Calendar cal = Calendar.getInstance();
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					 cal.add(Calendar.DATE, -1);
					 String date = dateFormat.format(cal.getTime());
					 
					 Reporter.log("<p>" +"Yesterday's date was "+ date); 
					 driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(date);
					 
					 // Clicking on Proceed button
					 driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
					 help.sleep(5);
					 
					 // Verifying the Success message displayed on the page after following up
					 Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
					 
					 // Closing the page
					 driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
				 } 
				 else 
					 Reporter.log("<p>" +"No leads present in the Table for Follow up 4.");
		     } 
		 }
		 else
			 // If no link is found to click, a user assertion is thrown
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }   
	 
	 

	 // Test method for Cold Storage
	 @Test
	 public void b_coldStorage() {
		
		 Reporter.log("<p>" +"Test for Cold storage");
		 
		 // Expands the side tree menu and clicking on Cold Storage Link
		 help.expand();
		 
		 // Clicking on Cold Storage Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).click();
			 help.sleep(2);
			 
			 // Verifying whether the required page is loaded or not
			 Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			 help.sleep(4);
			 if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"))
				  Reporter.log("<p>" +driver.findElement(By.className(bdm.getProperty("emptytable_class"))).getText());
			 
		     else {
		    	
		    	 // Checking whether the leads' status is Follow up 4 or not
		    	 String leadno = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				 driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Follow up 4");
				 String leadnos = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				 if(leadno.contains(leadnos)) 
				 {
					  Reporter.log("<p>" + "Lead Status of every lead is Follow up 4");
				 }
				 else
				 {
					  Reporter.log("<p>" + "Lead Status of every lead is not Follow up 4");
				 }
		    	 
		    	// Verifying no.of leads in the page
				List <WebElement> leads = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
				Reporter.log("<p>" +"No. of leads in the Table:" + leads.size());
				int confirm=0;
				for(int i=0; i<leads.size(); i++) {
					if(leads.get(i).findElement(By.className(bdm.getProperty("trackitbutton_class"))).isEnabled()) 
					{
					  confirm++;
					}	  
				}
				if(confirm==leads.size())
				    Reporter.log("<p>" +"Confirm button is enabled for all " + confirm + " leads.");
				
				// Clicking on Confirm button of a lead
				int opt = help.random(leads.size());
				
				String leadid = leads.get(opt).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText();
				String leadname = leads.get(opt).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText();
				String company = leads.get(opt).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();
				String lead = leadid + " " + leadname + " " + company;
				Reporter.log("<p>" +"The details of lead selected to confirm: " + lead);
				
				// Clicking on confirm button
				leads.get(opt).findElement(By.className(bdm.getProperty("trackitbutton_class"))).click();
				help.sleep(4);
				Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.className(bdm.getProperty("successmsg_class"))).getText());
				
				// Search for the confirmed lead and check whether the lead is present in the cold storage or not
				Reporter.log("<p>" + "Searching the lead in cold storage phase which is confirmed.");
				search(lead);
				help.sleep(2);
				
				// Check whether the lead is present in the database or in Lead Search cold storage
				if(driver.findElement(By.id(bdm.getProperty("leadsearchlink_id"))).isDisplayed()) 
				 {
					 driver.findElement(By.id(bdm.getProperty("leadsearchlink_id"))).click();
					 
					 // Switching to Child Window
					 String parentWindow = driver.getWindowHandle();
					 for(String childWindow : driver.getWindowHandles()) 
					 {
						 driver.switchTo().window(childWindow);
					 }
					 
					 
					 // Selecting required fields
					 Reporter.log("<p>" + "Clicking on the Lead Search and verifying the confirmed lead of cold storage.");
					 WebElement reqfields = driver.findElement(By.id(bdm.getProperty("requiredfields_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0);
					 reqfields.findElement(By.tagName(bdm.getProperty("searchbox_tag"))).click();
					 
					 driver.findElement(By.cssSelector(bdm.getProperty("filteroption_css"))).click();
					 
					 // Selecting Cold storage in filter options
					 WebElement status = driver.findElement(By.id(bdm.getProperty("statusid_id"))).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4);
					 status.findElement(By.tagName(bdm.getProperty("searchbox_tag"))).click();
					 
					 // Clicking on search button
					 driver.findElement(By.id(bdm.getProperty("registerbutton_id"))).click();
					 help.sleep(4);
					 
					 String[] lname = lead.split(" ");
					 
					 Reporter.log("<p>" + "Searching the lead in Lead Search which is confirmed in cold storage phase .");
					 
					 // Searching with lead name and company name
					 search(lname[1] + " " + lname[2]);
					 
					 // Closing the Child Window
					 driver.close();
					 
					 // Switching to Parent Window
					 driver.switchTo().window(parentWindow);
				 }
				 else 
			    	 Assert.fail("No Link Found");
		       } 
		     } 
		 else 
		    	 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	
	 
	 // Test method for Proposal follow up
	 @Test
	 public void c_proposalFollowup() throws Exception {
		
		 Reporter.log("<p>" +"Testing follow up for Proposal Upload");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on All follow ups link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
			 help.sleep(2);
			 Reporter.log("<p>" + "Searching leads with status as Introductory Mail.");
			 
			 // Searching with Introductory mail
			 search("Introductory Mail");
			 help.sleep(3);
			 if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"));
				
		     else 
		     {
		    	 
		    	 // Selecting a lead
		    	 WebElement followupLead = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
				 
				 String uploadleadId = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText();
				 String leadname = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText();
				 String company = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();
				 String lead = uploadleadId + " " + leadname + " " + company;
				 Reporter.log("<p>" +"The details of lead selected to confirm: " + lead);
				 
				 Reporter.log("<p>" +"The ID of Lead selected to Follow up:" + uploadleadId);
				 
				 if (followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) 
				 {
					 // Clicking on follow up button
					 followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
					 help.sleep(3);
					 Reporter.log("<p>" +driver.findElement(By.cssSelector(bdm.getProperty("windowTitle_css"))).getText());
					
					 // Selecting followup type 
					 new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
					 new Select(driver.findElement(By.name(bdm.getProperty("prospecttype_name")))).selectByVisibleText("Proposal");
					 
					 // Selecting a Fixon date
					 Date date = new Date();
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					 String dateString = dateFormat.format(date);
					 driver.findElement(By.id(bdm.getProperty("fixon_id"))).sendKeys(dateString);
					 
					 // Selecting an email id and filling subject, message, followup comment and next followup date
					 List <WebElement> toList = driver.findElement(By.name(bdm.getProperty("emailto_name"))).findElements(By.tagName(bdm.getProperty("service_options_tag")));
					 toList.get(help.random(toList.size())).click();
					 driver.findElement(By.name(bdm.getProperty("subject_name"))).sendKeys("Prospect Identify");
					 driver.findElement(By.name(bdm.getProperty("message_name"))).sendKeys("Prospect Identify for Proposal Upload");
					 driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Prospect Identify for Proposal Upload");
					 driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(dateString);
					 
					 // Clicking on Proceed button
					 driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
					 help.sleep(5);
					 
					 // Verifying the Success message displayed on the page after uploading the Proposal
					 Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
					 
					 // Closing the Proposal Upload page
					 driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
					 
					// Logging out of the BDM module 
					 driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
					 Reporter.log("<p>" + "Logged out of BDM Module");
				  
					 String link = "proposal";
				  
					 // Logging into Researcher module and check for leads uploaded with quote
					 management(link, lead);
				 } 
				 else 
					 Reporter.log("<p>" +"No leads present in the Table for Prospect Identify.");
			 } 
		 }
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	} 
	
	 
	 // Test method for Proposal upload
	 @Test
	 public void d_proposalUpload() {
		 
		 Reporter.log("<p>" +"Test for Proposal Upload");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on the Proposal Upload Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).getText() + "' Link" );
	     
		 // Checks whether the Proposal upload link is displayed or not
		 if(driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).isDisplayed()) 
	     {
			 // Proposal upload link is Clicked in Proposal Uploads phase
			 driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Verifying whether leads are present in the Proposal Uploads page.
			  // If leads are not present it enters if condition
			  if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"))
				  Reporter.log("<p>" +driver.findElement(By.className(bdm.getProperty("emptytable_class"))).getText());
			  
			  // If leads are present it enters else condition
			  else 
			  {
				   // Verifying whether every lead in the page are having the status as "Prospect"
				   String leadno = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				   driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Prospect");
				   String leadnos = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				   
				   // Verifying the no.of leads present before searching with "Prospect" is equal to no.of leads after search
				   if(leadno.contains(leadnos)) 
				   {
					   Reporter.log("<p>" + "Lead Status of every lead is Prospect");
				   }
				   else
				   {
					   Reporter.log("<p>" + "Lead Status of every lead is not Prospect");
				   }
				   
				   // Verifying no.of leads in the page and getting the lead ID of the uploaded lead
				   String lead = uploadButton();
				  
				  // Entering details to the fields in Proposal Upload page
				  driver.findElement(By.name(bdm.getProperty("proposalname_name"))).sendKeys("Proposal Name");
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(bdm.getProperty("proposaldescription_name"))).sendKeys("Proposal Description");
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  String file = System.getProperty("user.dir") + "\\src\\testData\\Proposal.pdf";
				  driver.findElement(By.name(bdm.getProperty("proposaluploadbutton_name"))).sendKeys(file);
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  help.sleep(4);
				  // Verifying the Success message displayed on the page after uploading the Proposal
				  Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
				  
				  // Closing the Proposal Upload page
				  driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
				  
				  Reporter.log("<p>" + "Searching the proposal uploaded lead in Proposal upload page.");
				  
				  // Searching the lead whether it is present in the proposal upload page or not
				  search(lead);
				  
				  // Logging out of the BDM module 
				  driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
				  Reporter.log("<p>" + "Logged out of BDM Module");
				  
				  // Spliting lead details in a string to id, name, company
				  String[] leadiddetails = lead.split(" ");
				  String leadid = leadiddetails[0];
				  
				  // Logging into BDM or BDE module to validate whether the lead is present in all followups or not
				  assignedLead( leadid, lead, file);
				  
			  }
		 } 
		 else
			  // If the link proposalupload is not displayed, then it throws a user exception
			  Assert.fail("No Link Found");
	     Reporter.log("<p>___________________________________________________________________________________");
	 } 
	 
	 
	 
	 // Test method for Quote follow up
	 @Test
	 public void e_quoteFollowup() throws Exception {
			 Reporter.log("<p>" +"Testing follow up for Quote Upload");
			 
			 // Expands the side tree menu
			 help.expand();
			 
			 // Clicking on All follow ups Link
			 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).getText() + "' Link" );
			 if(driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).isDisplayed()) 
			 {
				 driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
				 help.sleep(2);
				 
				 // Search for Leads with Introductory mail as Last Follow up type
				 Reporter.log("<p>" + "Searching leads with status as Introductory Mail.");
				 search("Introductory Mail");
				 help.sleep(3);
				 if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"));
					
			     else 
			     {
			    	 // Selecting a lead for follow up
			    	 WebElement followupLead = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
					 
					 String uploadleadId = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText();
					 String leadname = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText();
					 String company = followupLead.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();
					 String lead = uploadleadId + " " + leadname + " " + company;
					 Reporter.log("<p>" +"The details of lead selected to confirm: " + lead);
					 
					 Reporter.log("<p>" +"The ID of Lead selected to Follow up:" + uploadleadId);
					 
					 // Clicking on follow up button
					 if (followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) 
					 {
						 
						 followupLead.findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
						 help.sleep(5);
						 
						// Entering details for Quote upload request
						 Reporter.log("<p>" +driver.findElement(By.cssSelector(bdm.getProperty("windowTitle_css"))).getText());
						 new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
						 new Select(driver.findElement(By.name(bdm.getProperty("prospecttype_name")))).selectByVisibleText("Quote");
						 Date date = new Date();
						 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						 String dateString = dateFormat.format(date);
						 driver.findElement(By.id(bdm.getProperty("fixon_id"))).sendKeys(dateString);
						 driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Prospect Identify for Quote Upload");
						 driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(dateString);
						 
						 // Clicking on Proceed button
						 driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
						 help.sleep(5);
						 
						 // Verifying the Success message displayed on the page after uploading the Proposal
						 Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
						 
						 // Closing the Quote Upload page
						 driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
						 
						 // Logging out of the BDM module 
						 driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
						 Reporter.log("<p>" + "Logged out of BDM Module");
					  
						 String link = "quote";
					  
						 // Logging into Researcher module and check for leads uploaded with quote
						 management(link, lead);
					 } 
					 else
						 Reporter.log("<p>" +"No leads present in the Table for Prospect Identify."); 
				 } 
			 }	 
			 else
				 Assert.fail("No Link Found"); 
			 Reporter.log("<p>___________________________________________________________________________________");
		 } 
	 
	 
	 
 	 // Test method for Quote upload
	 @Test
	 public void f_quoteUpload() {
		 
		 Reporter.log("<p>" +"Test for Quote Upload");
		 
		 // Expands Side tree menu
		 help.expand();
		 
		 // Clicking on Quote Uploads Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).getText() + "' Link" );
		 
		 // Checks whether the Quote upload is displayed or not
		 if(driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).isDisplayed()) 
		 {
			  // Quote upload link is Clicked in Quote Uploads phase
			  driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Verifying whether leads are present in the Quote Uploads page.
			  // If leads are not present it enters if condition
			  if(driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText().contains("Showing 0 to 0 of 0 entries"))
				 Reporter.log("<p>" + driver.findElement(By.className(bdm.getProperty("emptytable_class"))).getText());
			  
			  // If leads are present it enters else condition
			  else {
				  
				   // Verifying whether every lead in the page are having the status as "Prospect"
				   String leadno = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				   driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Prospect");
				   String leadnos = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
				   
				   // Verifying the no.of leads present before searching with "Prospect" is equal to no.of leads after search
				   if(leadno.contains(leadnos)) 
				   {
					   Reporter.log("<p>" + "Lead Status of every lead is Prospect");
				   }
				   else
				   {
					   Reporter.log("<p>" + "Lead Status of every lead is not Prospect");
				   }
			  
				  // Verifying no.of leads in the page and getting the lead ID of the uploaded lead
				  String lead = uploadButton();
				  
				  // Entering details to the fields in Quote Upload page
				  driver.findElement(By.name(bdm.getProperty("quotename_name"))).sendKeys("Quote Name");
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(bdm.getProperty("quotedescription_name"))).sendKeys("Quote Description");
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  String file = System.getProperty("user.dir") + "\\src\\testData\\Quote.doc";
				  driver.findElement(By.name(bdm.getProperty("quoteuploadbutton_name"))).sendKeys(file);
				  driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
				  help.sleep(5);
				  
				  // Verifying the Success message displayed on the page after uploading the Quote
				  Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
				  
				  // Closing the Quote Upload page
				  driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
				  
				  Reporter.log("<p>" + "Searching the quote uploaded lead in Quote upload page.");
				  
				  // Searching the lead whether it is present in the quote upload page or not
				  search(lead);
				  
				  // Logging out of the BDM module 
				  driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
				  Reporter.log("<p>" + "Logged out of BDM Module");
				  
				  // Splitting lead details in a string to id, name, company
				  String[] leadiddetails = lead.split(" ");
				  String leadid = leadiddetails[0];
				  
				  // Logging into BDM or BDE module to validate whether the lead is present in all followups or not
				  assignedLead( leadid, lead, file);  
			  }
		 } 
		 else
			  // If the link quoteupload is not displayed, then it throws a user exception
			  Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 } 
	 

	 
	 
	 // Test method for Lead Edit Track it button
	 @Test 
	 public void g_testLeadTrackButton() {
		 
		 Reporter.log("<p>" +"Testing Track It button in Lead Edit Phase");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on Edit Leads link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("editleads_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("editleads_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(bdm.getProperty("editleads_id"))).click();
			 help.sleep(3);
			 List <WebElement> leads1 = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			 Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads1.size());
			 
			  // Checking for the Track it button for each lead
			  int trackit=0;
			  for(int i=0; i<leads1.size(); i++) {
				if(leads1.get(i).findElement(By.className(bdm.getProperty("trackitbutton_class"))).isEnabled()) {
				  
				  trackit++;
				}	  
			  }
			  if(trackit==leads1.size())
				  Reporter.log("<p>" +"Trackit button is enabled for all leads.");
			  
			  Reporter.log("<p>" +"Details of the lead are: ");
			  // Getting the details of the lead to a List and Printing the details
			  List <String> details = trackitButton(help.random(leads1.size()));
			  for(int i=0; i<details.size(); i++)
			  Reporter.log("<p>" +details.get(i));
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }  
	 
	 
	 
	 // Test method for "Lead Edit" Edit button functionality
	 @Test
	 public void h_testLeadEditButton() {
		 
		 Reporter.log("<p>" +"Testing Edit button in Lead Edit Phase and its Functionality");
		 
		  // Expands the side tree menu
		  help.expand();
		  
		  // Clicking on Edit Leads link
		  Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("editleads_id"))).getText() + "' Link" );
		  driver.findElement(By.id(bdm.getProperty("editleads_id"))).click();
		  
		  // Verifying whether the required page is loaded or not
		  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
		  driver.findElement(By.name(bdm.getProperty("tablelength_name"))).findElements(By.tagName(bdm.getProperty("service_options_tag"))).get(3).click();
		  
		  // Verifying no.of leads in the page
		  List <WebElement> leads = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
		  help.sleep(3);
		  Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads.size());
		  
		  // Checking for the buttons for each lead
		  int edit=0;
		  for(int i=0; i<leads.size(); i++) 
		  {
			if(leads.get(i).findElement(By.className(bdm.getProperty("editbutton_class"))).isEnabled()) 
			{
			  edit++;
			}	  
		  }
		  if(edit==leads.size())
			  Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
		  
		  // Tracking the lead and getting the details into the List
		  int opt1 = help.random(leads.size());
		  List <String> detail =  trackitButton(opt1);
		  
		  // 
		  String lead = detail.get(0);
		  List <String> dbDetail = leadEditdb(lead);
		  
		  //Editing the Leads
		  driver.findElement(By.id(bdm.getProperty("editleads_id"))).click();
		  driver.findElement(By.name(bdm.getProperty("tablelength_name"))).findElements(By.tagName(bdm.getProperty("service_options_tag"))).get(3).click();
		  help.sleep(5);
		  
		  // Selecting a lead and Clicking on Edit button
		  driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(opt1).findElement(By.className("edit")).click();
		  help.sleep(5);
		  Reporter.log("<p>" +driver.findElement(By.cssSelector(bdm.getProperty("windowTitle_css"))).getText());
		  
		  // Conditions for the selection of the Lead edit set to be edited
		  if((detail.get(1).equals("Jennifer")) || detail.get(1).equals("Bret"))
		  {
			  // Editing the Fields
			  editLead("John", "Andrew", "SAAS", "Robotics");
			  
		  }
		  else if (detail.get(1).equals("John"))
		  {
			  // Editing the Fields
			  editLead("Bret", "Lee", "QA", "Finance");
		  }
		  else 
		  {
			// Editing the Fields
			  editLead("Katrina", "Hayden", "Mobile", "Banking");
		  }
		  
		  
		  
		  // Clicking on Edit button to proceed
		  driver.findElement(By.id(bdm.getProperty("editbutton_id"))).click();
		  help.sleep(3);
		  
		  // Getting the success message
		  Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).getText());
		  driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
		  
		  // Tracking the edited lead
		  driver.findElement(By.name(bdm.getProperty("tablelength_name"))).findElements(By.tagName(bdm.getProperty("service_tag"))).get(3).click();
		  help.sleep(5);
		  
		  // Tracking the lead and getting the details into the List from Track it and Database
		  List <String> detail1 = trackitButton(opt1);
		  
		  List <String> dbDetail1 = leadEditdb(lead);
		  
		  Reporter.log("<p>" + detail);
		  Reporter.log("<p>" + detail1);
		  
		  // Comparing the details in 2 lists before and after editing the lead and printing the edited fields
		  if((detail.equals(detail1)) && dbDetail.equals(dbDetail1))
		  {
			  Reporter.log("<p>" +"Lead is not edited or modified.");
		  } 
		  else 
		  {
			  Reporter.log("<p>" +"The modified fields before and after editing the lead are:");
			  for(int i=0; i<detail.size();i++)
			  {
				  for(int j=0; j<detail1.size(); j++)
				  {
					  if((!detail.get(i).equals(detail1.get(j))) && (i==j))
						  Reporter.log("<p>" +detail.get(i) + ", " + detail1.get(j));  
				  }
			  }
		  }
		  Reporter.log("<p>___________________________________________________________________________________");
	 } 
	 
	 
	 // Test method for Search Leads
	 @Test
	 public void i_leadSearch() {
		
		 Reporter.log("<p>" +"Test for Lead Search Phase");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("leadsearchlink_id"))).getText() + "' Link" );
		
		 // Calling search lead method from helper
		 searchLead();
		 
		 Reporter.log("<p>___________________________________________________________________________________");
	 } 	

	 
	 
	 // ------Paginations for all Pages-------
	 
	 // Test Method for Lead Search Paginations
	 @Test
	 public void j_leadSearchPagination() throws Exception {
		 Reporter.log("<p>" +"Test for Lead Search Phase Paginations");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("leadsearchlink_id"))).getText() + "' Link" );
		 
		 help.searchLeadPagination();
		 
		 Reporter.log("<p>___________________________________________________________________________________");
		 
	 }
	 
	 
	 
		// Pagination for Cold Storage page
		@Test
		public void k_paginationColdStorage() throws Exception {
			
			 Reporter.log("<p>" +"Test for Pagination in Cold Storage page");
			 
			 // Expands the side tree menu
			 help.expand();
			 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).getText() + "' Link" );
			 if(driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).isDisplayed()) 
			 {
				  driver.findElement(By.id(bdm.getProperty("coldstorageLink_id"))).click();
				  
				  // Verifying whether the required page is loaded or not
				  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
				  help.sleep(4);
				  
				  // Pagination
				  //help.pagination();
				  
				  // Page Entries
				  help.pageEntries();
				  
				  // Sorting
				  help.sorting();
				  
				  // Search 
				  help.searchtable();
				      
			 } 
			 else 
				 Assert.fail("No Link Found");
			 Reporter.log("<p>___________________________________________________________________________________");
		}
		
		
		
	 
	 // Pagination for Proposal Uploads Page
	 @Test
	 public void l_paginationproposal() throws Exception {
		 
		 Reporter.log("<p>" +"Test for Pagination in proposal upload page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).isDisplayed()) {
			  driver.findElement(By.id(bdm.getProperty("proposalupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  //help.pagination();
			  
			  // Page Entries
			  help.pageEntries();
			  
			  // Sorting
			  help.sorting();
			  
			  // Search 
			  help.searchtable();
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	 
	 // Pagination for Quote Uploads page
	 @Test
	 public void m_paginationQuote() throws Exception {
		 
		 Reporter.log("<p>" +"Test for Pagination in quote upload page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).isDisplayed()) {
			  driver.findElement(By.id(bdm.getProperty("quoteupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  //help.pagination();
			  
			  // Page Entries
			  help.pageEntries();
			  
			  // Sorting
			  help.sorting();
			  
			  // Search 
			  help.searchtable();   
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	
	
	
	// Pagination for Lead Edit page
	@Test
	public void n_paginationleadEdit() throws Exception {
		 
		 Reporter.log("<p>" +"Test for Pagination in Lead Edit page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("editleads_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(bdm.getProperty("editleads_id"))).isDisplayed()) 
		 {
			  driver.findElement(By.id(bdm.getProperty("editleads_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  //help.pagination();
			  
			  // Page Entries
			  help.pageEntries();
			  
			  // Sorting
			  help.sorting();
			  
			  // Search 
			  help.searchtable();   
		 } 
		 else 
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	} 
	
	
	// Change Password
	@Test
	 public void changePassword() throws Exception {
		 	  // Expanding the side tree menu
		      help.expand();
		      // Calling changePassword helper method and passing the username to the method
		      help.changePassword(config.getProperty("bdmuser"));
	 }

	 
	 // --------------------------------- Static Methods ---------------------------------------------------

	
		
		 // Method for validating Upload button
		 public static String uploadButton() {
			  // Verifying no.of leads in the page
			  List <WebElement> leads = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			  Reporter.log("<p>" +"No. of leads in the Table:" + leads.size());
			  int upload=0;
			  for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className(bdm.getProperty("uploadbutton_class"))).isEnabled()) {
				  upload++;
				}	  
			  }
			  if(upload==leads.size())
				  Reporter.log("<p>" +"Upload button is enabled for all " + upload + " leads.");
			  // Clicking on Upload button of a Lead opens a Quote Upload Page
			  int opt = help.random(leads.size());
			  String uploadleadId = leads.get(opt).findElement(By.className(bdm.getProperty("leadidintable_class"))).getText();
			  String name = leads.get(opt).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText();
			  String company = leads.get(opt).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();
			  Reporter.log("<p>" +"The ID of Lead selected to Upload:" + uploadleadId);
			  Reporter.log(name + company);
			  String lead = uploadleadId + " " + name + " " + company;
			  leads.get(opt).findElement(By.className(bdm.getProperty("uploadbutton_class"))).click();
			  help.sleep(5);
			  String title = driver.findElement(By.cssSelector(bdm.getProperty("windowTitle_css"))).getText();
			  Reporter.log("<p>" +"The lead window opened for upload:" + title);
			  if(title.contains(uploadleadId))
				  Reporter.log("<p>" +"Lead selected to upload is matched with the Lead opened.");
			  else 
				  Reporter.log("<p>" + "Lead Mismatched.");
			  
			  return lead;
			  
		 }
		 
		 

		 
		 // Method for validating Track It button
		 public static List<String> trackitButton(int opt1) 
		 {
			  List <WebElement> leads1 = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			  leads1.get(opt1).findElement(By.className(bdm.getProperty("trackitbutton_class"))).click();
			  help.sleep(3);
			  
			  // Getting the details of the lead in the table to a List
			  List <WebElement> list = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("servicename_tag")));
			  List <String> detailsList = new ArrayList <String>() ;
			  for(int i=0; i<list.size(); i++)
				  detailsList.add(list.get(i).findElements(By.tagName(bdm.getProperty("resultmsg_tag"))).get(1).getText());
			  
			  // Return the String List
			  return detailsList; 
		 }
		 
		 
		 // Method for validating Search box. Searches with keyword and prints the table with all search results
		 public static void search(String keyword) {
			 driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(keyword);
			 help.sleep(4);
			 Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).getText());
		 }

		 
		 
		 // Method for Logging into the Management Module and verify the lead with quote or proposal upload
		 public static void management(String link, String leadId) throws Exception {
			 
			 Reporter.log("<p>" + "Logging in to Management Module");
			 
			 try {
				 Class.forName("com.mysql.jdbc.Driver").newInstance();
	             connection = DBConnection.getConnection();
	             statement = connection.createStatement();
	             rs = statement.executeQuery("select  a.role_name, b.email_id, b.password "
		              		+ "from crm_role a, crm_user b where "
		              		+ "a.role_id = b.role_id AND delete_status='no' AND role_name='Management' Limit 1;");
	             
	             
	             
	             while (rs.next()) {
	                
	                 String role = rs.getString("role_name");
	                 String email = rs.getString("email_id");
	                 String password = rs.getString("password");
	                 System.out.println(email +password);
	                 if (role.contains("Management"))
	                	 help.login(email, password);
	             } 
		        } 
		        catch (Exception e) 
		        {
		            e.printStackTrace();
		        }
			 //help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
			 help.expand();
			 
			 // Clicking on either All Quotes or All Proposals for searching the Lead
			 if(link.contains("quote")) 
			 {
				 driver.findElement(By.id(bdm.getProperty("allquotes_id"))).click();
				 Reporter.log("<p>" + "Clicking on All Quotes and searching for the quote requested lead with lead id:" + leadId);
				 search(leadId);
			 }
			 else 
			 {
				 driver.findElement(By.id(bdm.getProperty("allproposals_id"))).click();
				 Reporter.log("<p>" + "Clicking on All Proposals and searching for the proposal requested lead id:" + leadId);
				 search(leadId);
			 }
			 // Logging out of the Management module 
			 driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
			 Reporter.log("<p>" + "Logged out of Management Module");
		 }
		 
		 
		 
		 // Method for retieving the details in the database for Lead Edit
		 public static List<String> leadEditdb(String id) 
		 {
			 List <String> dbList = new ArrayList<String> ();
			 try {
	             Class.forName("com.mysql.jdbc.Driver").newInstance();
	             connection = DBConnection.getConnection();
	             statement = connection.createStatement();
	             rs = statement.executeQuery("select b.first_name, b.last_name, b.mobile_number, b.board_number, "
	             		+ "b.desk_number, a.domain_name, c.service_name from crm_domain a, "
	             		+ "crm_lead b, crm_service c where a.domain_id = b.domain_id AND "
	             		+ "c.service_id=b.service_id AND  b.lead_id='" + id + "';");      
	            
	             while (rs.next()) {
	            	 String fname = rs.getString("first_name");
	                 String lname = rs.getString("last_name");
	                 String name = fname + " " + lname;
	                 String mobile = rs.getString("mobile_number");
	                 String boardnum = rs.getString("board_number");
	                 String desknum = rs.getString("desk_number");
	                 String domain = rs.getString("domain_name");
	                 String service = rs.getString("service_name");
	                 
	                 dbList.add(name);
	                 dbList.add(mobile);
	                 dbList.add(boardnum);
	                 dbList.add(desknum);
	                 dbList.add(domain);
	                 dbList.add(service);
	             } 
			 } 
		        catch (Exception e) 
		        {
		            e.printStackTrace();
		        }   
			return dbList;
		 }
		 
	
		 
		 // Method for Validating the quote and proposal upload lead is present
		 // in all followups of the assigned user module or not
		 public static void assignedLead(String leadid, String lead, String file) {
			 
			 try {
                 
	             Class.forName("com.mysql.jdbc.Driver").newInstance();
	             connection = DBConnection.getConnection();
	             statement = connection.createStatement();
	             
	             rs = statement.executeQuery("select  a.lead_id, a.assigned_to_user_id, b.email_id, b.password "
	              		+ "from crm_lead a, crm_user b where "
	              		+ "a.lead_id = '" + leadid + "' AND a.assigned_to_user_id = b.user_id;");
	             
	             while (rs.next()) 
	             {
	                
	                 //String role = rs.getString("role_name");
	                 String email = rs.getString("email_id");
	                 String password = rs.getString("password");
	                 Reporter.log("<p>" + "Logging into " + email + " account.");
	                 help.login(email, password);
	                 help.expand();
	                 
	                 // Clicking on All follow ups Link
	    			 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).getText() + "' Link" );
	    			 if(driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).isDisplayed()) 
	    			 {
	    				 driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
	    				 help.sleep(2);
	    				 
	    				 // Search for Leads with Introductory mail as Last Follow up type
	    				 search(lead);
	    				 //WebElement abc = driver.findElement(By.id(bdm.getProperty("tablename_id"))).findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    				  driver.findElement(By.className(bdm.getProperty("trackitbutton_class"))).click();
	    				  help.sleep(3);
	    				  
	    				  // Getting the details of the lead in the table to a List
	    				  WebElement details = driver.findElements(By.tagName(bdm.getProperty("leads_info_tag"))).get(3).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(1);
	    				  Reporter.log("<p>" + details.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText());
	    				  Reporter.log("<p>" + "The attribute of href is: " + details.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).findElement(By.tagName(bdm.getProperty("anchor_tag"))).getAttribute("href"));
	    				  String download = details.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).findElement(By.tagName(bdm.getProperty("anchor_tag"))).getText();
	    				  
	    				  Reporter.log("<P>" + "The file uploaded is: " + file);
	    				  Reporter.log("<P>" + "The file displayed in the track it of lead is:" + download);
	    				  
	    				  if(file.contains(download)) {
	    					  Reporter.log("<p>" + "The file uploaded is verified."  );
	    				  }
	    				  else
	    				  {
	    					  Reporter.log("<p>" + "The wrong file is uploaded."  );
	    				  }
	    			 }
	    			 else
	    			 {
	    				 Assert.fail("No Link Found");
	    			 }	 
	                 
	             } 
	             
		     } 
		     catch (Exception e) 
		     {
		          e.printStackTrace();
		     }
			 
		 }
		 
		 
		 
     // Static method for lead edit sets
	 public static void editLead(String fname, String lname, String service, String domain) {
		  driver.findElement(By.id(bdm.getProperty("firstname_id"))).clear();
		  driver.findElement(By.id(bdm.getProperty("firstname_id"))).sendKeys(fname);
		  driver.findElement(By.id(bdm.getProperty("lastname_id"))).clear();
		  driver.findElement(By.id(bdm.getProperty("lastname_id"))).sendKeys(lname);
		  driver.findElement(By.id(bdm.getProperty("mobilenumber_id"))).clear();
		  driver.findElement(By.id(bdm.getProperty("mobilenumber_id"))).sendKeys("3-(486)235-8432");
		  driver.findElement(By.id(bdm.getProperty("boardnumber_id"))).clear();
		  driver.findElement(By.id(bdm.getProperty("boardnumber_id"))).sendKeys("8-(104)838-3404");
		  new Select(driver.findElement(By.name(bdm.getProperty("service_options_name")))).selectByVisibleText(service);
		  new Select(driver.findElement(By.name(bdm.getProperty("domainoption_name")))).selectByVisibleText(domain);
		  driver.findElement(By.id(bdm.getProperty("desknumber_id"))).clear();
		  driver.findElement(By.id(bdm.getProperty("desknumber_id"))).sendKeys("3-(618)434-8752"); 
	 }
		 
}



