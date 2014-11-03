package src.crm;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
		help.login(sh2.getCell(0,0).getContents(), sh2.getCell(1,0).getContents());
		String user = driver.findElement(By.className(or.getProperty("username_class"))).getText();
		Reporter.log("<p>" +"User Logged in as:" + user);
	}

	 
	 
	 @AfterMethod
	 public void after() {
		 driver.close();
	 }
	 
	 
	 // Test Method for Follow up 4 for Cold storage phase. 
	 @Test
	 public void coldStorageFollowup() {
		 
		 Reporter.log("<p>" +"Testing follow up for Cold storage");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on All follow up Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).click();
			 help.sleep(2);
			 search("Introductory Mail");
			 WebElement followupLead = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
			 Reporter.log("<p>" +"Selected Lead is:" + followupLead.getText());
			 if (followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).isEnabled()) 
			 {
				 followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).click();
				 help.sleep(3);
				 Reporter.log("<p>" +driver.findElement(By.cssSelector(or.getProperty("windowTitle_css"))).getText());
				 
				 // Selecting follow up type and entering the follow up comment
				 new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Followup 4");
				 driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Follow up 4");
				 
				 // Selecting yesterday's date for follow up 4 to send the lead to cold storage and click Proceed button
				 Calendar cal = Calendar.getInstance();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 cal.add(Calendar.DATE, -1);
				 String date = dateFormat.format(cal.getTime());
				 Reporter.log("<p>" +"Yesterday's date was "+ date); 
				 driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(date);
				 driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				 help.sleep(5);
				 
				 // Verifying the Success message displayed on the page after following up
				 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
				 
				 // Closing the page
				 driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
			 } 
			 else 
				 Reporter.log("<p>" +"No leads present in the Table for Follow up 4.");
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	 

/*	 // Test method for Cold Storage
	 @Test
	 public void coldStorage() {
		
		 Reporter.log("<p>" +"Test for Cold storage");
		 
		 // Expands the side tree menu and clicking on Cold Storage Link
		 help.expand();
		 
		 // Clicking on Cold Storage Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).click();
			 help.sleep(2);
			 
			 // Verifying whether the required page is loaded or not
			 Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			 help.sleep(2);
			 if(driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText().equals("Showing 0 to 0 of 0 entries"))
				  Reporter.log("<p>" +driver.findElement(By.className(or.getProperty("emptytable_class"))).getText());
			 
		     else {
		    	// Verifying no.of leads in the page
				List <WebElement> leads = driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
				Reporter.log("<p>" +"No. of leads in the Table:" + leads.size());
				int confirm=0;
				for(int i=0; i<leads.size(); i++) {
					if(leads.get(i).findElement(By.className(or.getProperty("trackitbutton_class"))).isEnabled()) 
					{
					  confirm++;
					}	  
				}
				if(confirm==leads.size())
				    Reporter.log("<p>" +"Confirm button is enabled for all " + confirm + " leads.");
				
				// Clicking on Confirm button of a lead
				int opt = help.random(leads.size());
				Reporter.log("<p>" +"The ID of lead selected to confirm: " + leads.get(opt).findElement(By.tagName(or.getProperty("servicename_tag"))).getText());
				leads.get(opt).findElement(By.className(or.getProperty("trackitbutton_class"))).click();
				help.sleep(4);
				Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.className(or.getProperty("successmsg_class"))).getText());
		       } 
		     } 
		 else 
		    	 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	 
	 
	 // Test method for Proposal follow up
	 @Test
	 public void proposalFollowup() throws Exception {
		
		 Reporter.log("<p>" +"Testing follow up for Proposal Upload");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on All follow ups link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).click();
			 help.sleep(2);
			 search("Introductory Mail");
			 WebElement followupLead = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
			 
			 String uploadleadId = followupLead.findElement(By.className(or.getProperty("leadidintable_class"))).getText();
			 Reporter.log("<p>" +"The ID of Lead selected to Upload:" + uploadleadId);
			 
			 if (followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).isEnabled()) 
			 {
				 followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).click();
				 help.sleep(3);
				 Reporter.log("<p>" +driver.findElement(By.cssSelector(or.getProperty("windowTitle_css"))).getText());
				
				 // Selecting followup type 
				 new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
				 new Select(driver.findElement(By.name(or.getProperty("prospecttype_name")))).selectByVisibleText("Proposal");
				 
				 // Selecting a Fixon date
				 Date date = new Date();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 String dateString = dateFormat.format(date);
				 driver.findElement(By.id(or.getProperty("fixon_id"))).sendKeys(dateString);
				 
				 // Selecting an email id and filling subject, message, followup comment and next followup date
				 List <WebElement> toList = driver.findElement(By.name(or.getProperty("emailto_name"))).findElements(By.tagName(or.getProperty("service_options_tag")));
				 toList.get(help.random(toList.size())).click();
				 driver.findElement(By.name(or.getProperty("subject_name"))).sendKeys("Prospect Identify");
				 driver.findElement(By.name(or.getProperty("message_name"))).sendKeys("Prospect Identify for Proposal Upload");
				 driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Prospect Identify for Proposal Upload");
				 driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(dateString);
				 driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				 help.sleep(5);
				 
				 // Verifying the Success message displayed on the page after uploading the Proposal
				 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
				 
				 // Closing the Proposal Upload page
				 driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
				 
				// Logging out of the BDM module 
				 driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.linkText(or.getProperty("logoutlink_linktext"))).click();
				 Reporter.log("<p>" + "Logged out of BDM Module");
			  
				 String link = "proposal";
			  
				 // Logging into Researcher module and check for leads uploaded with quote
				 management(link, uploadleadId);
			 } 
			 else 
				 Reporter.log("<p>" +"No leads present in the Table for Prospect Identify.");
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	} 
	
	 
	 // Test method for Proposal upload
	 @Test
	 public void proposalUpload() {
		 
		 Reporter.log("<p>" +"Test for Proposal Upload");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on the Proposal Upload Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("proposalupload_id"))).getText() + "' Link" );
	     if(driver.findElement(By.id(or.getProperty("proposalupload_id"))).isDisplayed()) 
	     {
			  driver.findElement(By.id(or.getProperty("proposalupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  if(driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText().equals("Showing 0 to 0 of 0 entries"))
				  Reporter.log("<p>" +driver.findElement(By.className(or.getProperty("emptytable_class"))).getText());
			  else 
			  {
				   uploadButton();
				  
				  // Entering details to the fields in Proposal Upload page
				  driver.findElement(By.name(or.getProperty("proposalname_name"))).sendKeys("Proposal Name");
				  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(or.getProperty("proposaldescription_name"))).sendKeys("Proposal Description");
				  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(or.getProperty("proposaluploadbutton_name"))).sendKeys("E:\\abc1.txt");
				  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  help.sleep(4);
				  // Verifying the Success message displayed on the page after uploading the Proposal
				  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
				  
				  // Closing the Proposal Upload page
				  driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
				  
			  }
		 } 
		 else
			  Assert.fail("No Link Found");
	     Reporter.log("<p>___________________________________________________________________________________");
	 } 
	 
	 
	 
	 // Test method for Quote follow up
	 @Test
	 public void quoteFollowup() throws Exception {
			 Reporter.log("<p>" +"Testing follow up for Quote Upload");
			 
			 // Expands the side tree menu
			 help.expand();
			 
			 // Clicking on All follow ups Link
			 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).getText() + "' Link" );
			 if(driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).isDisplayed()) 
			 {
				 driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).click();
				 help.sleep(2);
				 
				 // Search for Leads with Introductory mail as Last Follow up type
				 search("Introductory Mail");
				 WebElement followupLead = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
				 
				 String uploadleadId = followupLead.findElement(By.className(or.getProperty("leadidintable_class"))).getText();
				 Reporter.log("<p>" +"The ID of Lead selected to Upload:" + uploadleadId);
				 if (followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).isEnabled()) 
				 {
					 // Entering details for Quote upload
					 followupLead.findElement(By.className(or.getProperty("followupbutton_class"))).click();
					 help.sleep(5);
					 Reporter.log("<p>" +driver.findElement(By.cssSelector(or.getProperty("windowTitle_css"))).getText());
					 new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
					 new Select(driver.findElement(By.name(or.getProperty("prospecttype_name")))).selectByVisibleText("Quote");
					 Date date = new Date();
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					 String dateString = dateFormat.format(date);
					 driver.findElement(By.id(or.getProperty("fixon_id"))).sendKeys(dateString);
					 driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Prospect Identify for Quote Upload");
					 driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(dateString);
					 driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
					 help.sleep(5);
					 
					 // Verifying the Success message displayed on the page after uploading the Proposal
					 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
					 
					 // Closing the Quote Upload page
					 driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
					 
					 // Logging out of the BDM module 
					 driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.linkText(or.getProperty("logoutlink_linktext"))).click();
					 Reporter.log("<p>" + "Logged out of BDM Module");
				  
					 String link = "quote";
				  
					 // Logging into Researcher module and check for leads uploaded with quote
					 management(link, uploadleadId);
				 } 
				 else
					 Reporter.log("<p>" +"No leads present in the Table for Prospect Identify."); 
			 } 
			 else
				 Assert.fail("No Link Found"); 
			 Reporter.log("<p>___________________________________________________________________________________");
		 } 
	 
	 
	 
 	 // Test method for Quote upload
	 @Test
	 public void quoteUpload() {
		 
		 Reporter.log("<p>" +"Test for Quote Upload");
		 
		 // Expands Side tree menu
		 help.expand();
		 
		 // Clicking on Quote Uploads Link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("quoteupload_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("quoteupload_id"))).isDisplayed()) 
		 {
			  driver.findElement(By.id(or.getProperty("quoteupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  if(driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText().equals("Showing 0 to 0 of 0 entries"))
				 Reporter.log("<p>" + driver.findElement(By.className(or.getProperty("emptytable_class"))).getText());
			  else {
				  // Verifying no.of leads in the page and getting the lead ID of the uploaded lead
				  uploadButton();
				  
				  // Entering details to the fields in Quote Upload page
				  driver.findElement(By.name(or.getProperty("quotename_name"))).sendKeys("Quote Name");
				  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(or.getProperty("quotedescription_name"))).sendKeys("Quote Description");
				  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  driver.findElement(By.name(or.getProperty("quoteuploadbutton_name"))).sendKeys("E:\\abc2.txt");
				//  driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				  help.sleep(5);
				  
				  // Verifying the Success message displayed on the page after uploading the Quote
				  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
				  
				  // Closing the Quote Upload page
				  driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
				  
				  
				  
			  }
		 } 
		 else
			  Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 } 
	 
	 
	 
	 // Test method for Lead Edit Track it button
	 @Test 
	 public void testLeadTrackButton() {
		 
		 Reporter.log("<p>" +"Testing Track It button in Lead Edit Phase");
		 
		 // Expands the side tree menu
		 help.expand();
		 
		 // Clicking on Edit Leads link
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("editleads_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("editleads_id"))).isDisplayed()) 
		 {
			 driver.findElement(By.id(or.getProperty("editleads_id"))).click();
			 help.sleep(3);
			 List <WebElement> leads1 = driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			 Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads1.size());
			 
			  // Checking for the Track it button for each lead
			  int trackit=0;
			  for(int i=0; i<leads1.size(); i++) {
				if(leads1.get(i).findElement(By.className(or.getProperty("trackitbutton_class"))).isEnabled()) {
				  
				  trackit++;
				}	  
			  }
			  if(trackit==leads1.size())
				  Reporter.log("<p>" +"Trackit button is enabled for all leads.");
			  
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
	 public void testLeadEditButton() {
		 
		 Reporter.log("<p>" +"Testing Edit button in Lead Edit Phase and its Functionality");
		 
		  // Expands the side tree menu
		  help.expand();
		  
		  // Clicking on Edit Leads link
		  Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("editleads_id"))).getText() + "' Link" );
		  driver.findElement(By.id(or.getProperty("editleads_id"))).click();
		  
		  // Verifying whether the required page is loaded or not
		  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
		  driver.findElement(By.name(or.getProperty("tablelength_name"))).findElements(By.tagName(or.getProperty("service_options_tag"))).get(3).click();
		  
		  // Verifying no.of leads in the page
		  List <WebElement> leads = driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
		  help.sleep(3);
		  Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads.size());
		  
		  // Checking for the buttons for each lead
		  int edit=0;
		  for(int i=0; i<leads.size(); i++) 
		  {
			if(leads.get(i).findElement(By.className(or.getProperty("editbutton_class"))).isEnabled()) 
			{
			  edit++;
			}	  
		  }
		  if(edit==leads.size())
			  Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
		  
		  // Tracking the lead and getting the details into the List
		  int opt1 = help.random(leads.size());
		  List <String> detail =  trackitButton(opt1);
		  
		  String lead = detail.get(0);
		  List <String> dbDetail = leadEditdb(lead);
		  
		  //Editing the Leads
		  driver.findElement(By.id(or.getProperty("editleads_id"))).click();
		  driver.findElement(By.name("example_length")).findElements(By.tagName(or.getProperty("service_options_tag"))).get(3).click();
		  help.sleep(5);
		  
		  // Selecting a lead and Clicking on Edit button
		  driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(opt1).findElement(By.className("edit")).click();
		  help.sleep(5);
		  Reporter.log("<p>" +driver.findElement(By.cssSelector(or.getProperty("windowTitle_css"))).getText());
		  
		  
		  
		  if((detail.get(1).equals("Jennifer")) || detail.get(1).equals("Bret"))
		  {
			  // Editing the Fields
			  driver.findElement(By.id(or.getProperty("firstname_id"))).clear();
			  driver.findElement(By.id(or.getProperty("firstname_id"))).sendKeys("John");
			  driver.findElement(By.id(or.getProperty("lastname_id"))).clear();
			  driver.findElement(By.id(or.getProperty("lastname_id"))).sendKeys("Andrew");
			  driver.findElement(By.id(or.getProperty("mobilenumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("mobilenumber_id"))).sendKeys("3-(486)235-8432");
			  driver.findElement(By.id(or.getProperty("boardnumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("boardnumber_id"))).sendKeys("8-(104)838-3404");
			  new Select(driver.findElement(By.name(or.getProperty("service_options_name")))).selectByVisibleText("SAAS");
			  new Select(driver.findElement(By.name(or.getProperty("domainoption_name")))).selectByVisibleText("Robotics");
			  driver.findElement(By.id(or.getProperty("desknumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("desknumber_id"))).sendKeys("3-(618)434-8752"); 
		  }
		  else
		  {
			  // Editing the Fields
			  driver.findElement(By.id(or.getProperty("firstname_id"))).clear();
			  driver.findElement(By.id(or.getProperty("firstname_id"))).sendKeys("Bret");
			  driver.findElement(By.id(or.getProperty("lastname_id"))).clear();
			  driver.findElement(By.id(or.getProperty("lastname_id"))).sendKeys("Lee");
			  driver.findElement(By.id(or.getProperty("mobilenumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("mobilenumber_id"))).sendKeys("3-(486)235-8432");
			  driver.findElement(By.id(or.getProperty("boardnumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("boardnumber_id"))).sendKeys("8-(104)838-3404");
			  new Select(driver.findElement(By.name(or.getProperty("service_options_name")))).selectByVisibleText("QA");
			  new Select(driver.findElement(By.name(or.getProperty("domainoption_name")))).selectByVisibleText("Finance");
			  driver.findElement(By.id(or.getProperty("desknumber_id"))).clear();
			  driver.findElement(By.id(or.getProperty("desknumber_id"))).sendKeys("3-(618)434-8752"); 
		  }
		  
		  // Clicking on Edit button to proceed
		  driver.findElement(By.id(or.getProperty("editbutton_id"))).click();
		  help.sleep(3);
		  
		  // Getting the success message
		  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).getText());
		  driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
		  
		  // Tracking the edited lead
		  driver.findElement(By.name("example_length")).findElements(By.tagName(or.getProperty("service_tag"))).get(3).click();
		  help.sleep(5);
		  
		  // Tracking the lead and getting the details into the List
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
	 public void leadSearch() {
		
		 Reporter.log("<p>" +"Test for Lead Search Phase");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id("serachLeads123")).getText() + "' Link" );
		 
		 Reporter.log("<p>___________________________________________________________________________________");
	 } 	
	
	 
	 
	 // Test Method for Lead Search Paginations
	 @Test
	 public void leadSearchPagination() {
		 Reporter.log("<p>" +"Test for Lead Search Phase Paginations");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id("serachLeads123")).getText() + "' Link" );
		 
		 searchLeadPagination();
		 
		 Reporter.log("<p>___________________________________________________________________________________");
		 
	 }
	 
/*	 @Test
	 public void changePassword() {
		 
		 Reporter.log("<p>" +"Test for My Account Phase");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.linkText("Change Password")).getText() + "' Link" );
		 if(driver.findElement(By.linkText("Change Password")).isDisplayed()) {
			 driver.findElement(By.linkText("Change Password")).click(); 
			 driver.findElement(By.id("oldPassword")).sendKeys("abcdef");
			 driver.findElement(By.id("newPassword")).sendKeys("abcdef");
			 driver.findElement(By.id("confirmPassword")).sendKeys("abcdef");
			 driver.findElement(By.id("change")).sendKeys(Keys.ENTER);
			  Reporter.log("<p>" +driver.findElement(By.xpath(".//*[@id='changePassword']/form/label[1]")).getText());
		 }
		 else 
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 } */
	 
	 
	 
/*	 // ------Paginations for all Pages-------
	 
	 // Pagination for Proposal Uploads Page
	 @Test
	 public void proposalPagination() {
		 
		 Reporter.log("<p>" +"Test for Pagination in proposal upload page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("proposalupload_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("proposalupload_id"))).isDisplayed()) {
			  driver.findElement(By.id(or.getProperty("proposalupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  pagination();
			  
			  // Page Entries
			  pageEntries();
			  
			  // Search 
			  Reporter.log("<p>" +"The Lead searched for:");
			  search("Henry");	  
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	 
	 // Pagination for Quote Uploads page
	 @Test
	 public void quotePagination() {
		 
		 Reporter.log("<p>" +"Test for Pagination in quote upload page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("quoteupload_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("quoteupload_id"))).isDisplayed()) {
			  driver.findElement(By.id(or.getProperty("quoteupload_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  pagination();
			  
			  // Page Entries
			  pageEntries();
			  
			  // Search 
			  Reporter.log("<p>" +"The Lead searched for:");
			  search("Henry");	  
		 } 
		 else
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	 }
	 
	 
	 
	// Pagination for Cold Storage page
	@Test
	public void coldStoragePagination() {
		
		 Reporter.log("<p>" +"Test for Pagination in Cold Storage page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).isDisplayed()) 
		 {
			  driver.findElement(By.id(or.getProperty("coldstorageLink_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  pagination();
			  
			  // Page Entries
			  pageEntries();
			  
			  // Search 
			  Reporter.log("<p>" +"The Lead searched for:");
			  search("Henry");	  
		 } 
		 else 
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	}
	
	
	
	// Pagination for Lead Edit page
	@Test
	public void leadEditPagination() {
		 
		 Reporter.log("<p>" +"Test for Pagination in Lead Edit page");
		 
		 // Expands the side tree menu
		 help.expand();
		 Reporter.log("<p>" +"Click on the '" + driver.findElement(By.id(or.getProperty("editleads_id"))).getText() + "' Link" );
		 if(driver.findElement(By.id(or.getProperty("editleads_id"))).isDisplayed()) 
		 {
			  driver.findElement(By.id(or.getProperty("editleads_id"))).click();
			  
			  // Verifying whether the required page is loaded or not
			  Reporter.log("<p>" +"Page loaded is:" + driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText());
			  help.sleep(4);
			  
			  // Pagination
			  pagination();
			  
			  // Page Entries
			  pageEntries();
			  
			  // Search 
			  Reporter.log("<p>" +"The Lead searched for:");
			  search("Henry");	  
		 } 
		 else 
			 Assert.fail("No Link Found");
		 Reporter.log("<p>___________________________________________________________________________________");
	} 
	
	*/

	 
	 // -------- Static Methods ---------

	
		
		 // Method for validating Upload button
		 public static void uploadButton() {
			  // Verifying no.of leads in the page
			  List <WebElement> leads = driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			  Reporter.log("<p>" +"No. of leads in the Table:" + leads.size());
			  int upload=0;
			  for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className("upload")).isEnabled()) {
				  upload++;
				}	  
			  }
			  if(upload==leads.size())
				  Reporter.log("<p>" +"Upload button is enabled for all " + upload + " leads.");
			  // Clicking on Upload button of a Lead opens a Quote Upload Page
			  int opt = help.random(leads.size());
			  String uploadleadId = leads.get(opt).findElement(By.className(or.getProperty("leadidintable_class"))).getText();
			  String name = leads.get(opt).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			  
			  Reporter.log("<p>" +"The ID of Lead selected to Upload:" + uploadleadId);
			  Reporter.log(name);
			  leads.get(opt).findElement(By.className("upload")).click();
			  help.sleep(5);
			  String title = driver.findElement(By.cssSelector(or.getProperty("windowTitle_css"))).getText();
			  Reporter.log("<p>" +"The lead window opened for upload:" + title);
			  if(title.contains(uploadleadId))
				  Reporter.log("<p>" +"Lead selected to upload is matched with the Lead opened.");
			  else 
				  Reporter.log("<p>" + "Lead Mismatched.");
			  
			  
		 }
		 
		 
		 // No. of Entries per page
		 public static void pageEntries() {
			  // Selecting no.of entries for the table
			  driver.findElement(By.id("example_length")).click();
			  List <WebElement> entries = driver.findElement(By.id("example_length")).findElements(By.tagName(or.getProperty("service_tag")));
			  help.sleep(4);
			  int opt = help.random(entries.size());
			  entries.get(opt).click();
			  Reporter.log("<p>" +"No.of Entries selected for the page:" + entries.get(opt).getText());
		 }
		 
		 // Method for Pagination(Previous and Next buttons)
		 public static void pagination() {
			  Reporter.log("<p>" +"Verifying Pagination Next & Previous Buttons:");
			  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText());
			  help.sleep(2);
			  driver.findElement(By.id("example_next")).click();
			  Reporter.log("<p>" +"Clicked on Next button");
			  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText());
			  help.sleep(2);
			  driver.findElement(By.id("example_previous")).click();
			  Reporter.log("<p>" +"Clicked on Previous button");
			  Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tableinfo_id"))).getText());
		 }

		 
		 // Method for validating Track It button
		 public static List<String> trackitButton(int opt1) 
		 {
			  List <WebElement> leads1 = driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			  leads1.get(opt1).findElement(By.className(or.getProperty("trackitbutton_class"))).click();
			  help.sleep(3);
			  
			  // Getting the details of the lead in the table to a List
			  List <WebElement> list = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("servicename_tag")));
			  List <String> detailsList = new ArrayList <String>() ;
			  for(int i=0; i<list.size(); i++)
				  detailsList.add(list.get(i).findElements(By.tagName("label")).get(1).getText());
			  
			  // Return the String List
			  return detailsList; 
		 }
		 
		 
		 // Method for validating Search box. Searches with keyword and prints the table with all search results
		 public static void search(String keyword) {
			 driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(keyword);
			 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
		 }

		 
		 
		 // Method for Logging into the Management Module and verify the lead with quote or proposal upload
		 public static void management(String link, String leadId) throws Exception {
			 
			 Reporter.log("<p>" + "Logging in to Management Module");
			 help.login(sh5.getCell(0,0).getContents(),sh5.getCell(1,0).getContents());
			 help.expand();
			 
			 // Clicking on either All Quotes or All Proposals for searching the Lead
			 if(link.contains("quote")) 
			 {
				 driver.findElement(By.id("quotesList")).click();
				 Reporter.log("<p>" + "Clicking on All Quotes and searching for the quote requested lead with lead id:" + leadId);
				 search(leadId);
			 }
			 else 
			 {
				 driver.findElement(By.id("proposalsList")).click();
				 Reporter.log("<p>" + "Clicking on All Proposals and searching for the proposal requested lead id:" + leadId);
				 search(leadId);
			 }
			 // Logging out of the Management module 
			 driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.linkText(or.getProperty("logoutlink_linktext"))).click();
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
		 
		 
		 // Method for Paginations in Search Lead phase
		 
		 public void searchLeadPagination() {
			 
			 
			 if(driver.findElement(By.id("serachLeads123")).isDisplayed()) 
			 {
				 driver.findElement(By.id("serachLeads123")).click();
				 
				 // Switching to Child Window
				 String parentWindow = driver.getWindowHandle();
				 for(String childWindow : driver.getWindowHandles()) 
				 {
					 driver.switchTo().window(childWindow);
				 }
				 
				 // Selecting Required fields
				 List <WebElement> requiredFields = driver.findElement(By.id("fields_to_get")).findElements(By.tagName(or.getProperty("servicename_tag")));
				 List <String> fieldoptions = new ArrayList <String>();
				 for(int i=1; i<requiredFields.size(); i++) 
				 {
					 fieldoptions.add(requiredFields.get(i).getText());
				 }
				 
				 Reporter.log("<p>" +"The required field:" + requiredFields.get(0).getText());
				 requiredFields.get(0).findElement(By.tagName("input")).click();
				 driver.findElement(By.cssSelector("span.ui-accordion-header-icon.ui-icon.ui-icon-triangle-1-e")).click();
				 
				 // Selecting a Category of Filter Options
				 WebElement filterOption = driver.findElement(By.id("ui-accordion-accordion-panel-1")).findElements(By.className("row1")).get(5);
				 
				 String opt = filterOption.findElement(By.tagName("legend")).getText();
				 Reporter.log("<p>" +"Filter Option Selected:" + opt);
				 // Selecting an option in a category in Filter Options and clicking on search button
				 List <WebElement> option = filterOption.findElements(By.tagName(or.getProperty("servicename_tag")));
				 Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());
				 
				 Reporter.log("<p>" +"Option selected is:" + option.get(3).findElement(By.tagName("label")).getText());
				 option.get(3).findElement(By.tagName("input")).click();
				 driver.findElement(By.id("registerbutton")).click();
				 help.sleep(5);
				 
				 // Printing the Table displayed with required fields
				 List <WebElement> fields = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				 List <String> fieldheads = new ArrayList <String>();
				 for(int i=1; i<fields.size(); i++) 
				 {
					 fieldheads.add(fields.get(i).getText());
				 }
				 Reporter.log("<p>" + fieldheads);
				 Reporter.log("<p>" + fieldoptions);
				 
				 if(fieldheads.equals(fieldoptions)) 
				 {
					 Reporter.log("<p>" +"The required fields selected is matched with the headers of the table.");
					 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
					 
					 // Pagination
					 pagination();
					 
					 // No.of Entries per page
					 pageEntries();
					 
					 // Sorting
					// help.sorting();
					 
					 // Search Box Validation
					 driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Johnson");
					 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
				 }
				 else 
					 Reporter.log("<p>" +"The required fields selected is not matched with the headers of the table.");
				 			 
				 // Closing the Child Window
				 //driver.close();
				 
				 // Switching to Parent Window
				 driver.switchTo().window(parentWindow);
			 } 
			 else
				 Assert.fail("No Link Found"); 
		 }
		 
		 
		 
		 // Method for Lead Search phase
		 public void searchLead() {
			 if(driver.findElement(By.id("serachLeads123")).isDisplayed()) 
			 {
				 driver.findElement(By.id("serachLeads123")).click();
				 
				 // Switching to Child Window
				 String parentWindow = driver.getWindowHandle();
				 for(String childWindow : driver.getWindowHandles()) 
				 {
					 driver.switchTo().window(childWindow);
				 }
				 
				 // Selecting Required fields
				 List <WebElement> requiredFields = driver.findElement(By.id("fields_to_get")).findElements(By.tagName(or.getProperty("servicename_tag")));
				 int a = help.random(requiredFields.size());
				 String field = requiredFields.get(a).getText();
				 Reporter.log("<p>" +"The reqiured field:" + field);
				 requiredFields.get(a).findElement(By.tagName("input")).click();
				 driver.findElement(By.cssSelector("span.ui-accordion-header-icon.ui-icon.ui-icon-triangle-1-e")).click();
				 
				 
				 // Selecting a Category of Filter Options
				 List <WebElement> filterOptions = driver.findElement(By.id("ui-accordion-accordion-panel-1")).findElements(By.className("row1"));
				 Reporter.log("<p>" +"Size of Filter option categories:" + filterOptions.size());
				 for(int i=0; i<filterOptions.size(); i++) 
				 {
					 String opt = filterOptions.get(i).findElement(By.tagName("legend")).getText();
					 Reporter.log("<p>" +"Filter Option Selected:" + opt);
					
					 // Selecting an option in a category in Filter Options and clicking on search button
					 List <WebElement> option = filterOptions.get(i).findElements(By.tagName(or.getProperty("servicename_tag")));
					 Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());
					 int c = help.random(option.size());
					 Reporter.log("<p>" +"Option selected is:" + option.get(c).findElement(By.tagName("label")).getText());
					 option.get(c).findElement(By.tagName("input")).click();
					 driver.findElement(By.id("registerbutton")).click();
					 help.sleep(5);
					 
					 // Printing the Table displayed with required fields
					 Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
					 //driver.navigate().refresh(); 
					 option.get(c).findElement(By.tagName("input")).click();
				 }
				 
				 // Closing the Child Window
				 driver.close();
				 
				 // Switching to Parent Window
				 driver.switchTo().window(parentWindow);
			 } 
			 else
				 Assert.fail("No Link Found");
		 }
}
