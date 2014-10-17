package crm;

import java.util.List;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDM extends Helper {
	
	 @BeforeMethod
	 public void before() throws Exception {
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		browsererror();
	
		//Login for BDM module
		help.login(sh2.getCell(0,0).getContents(), sh2.getCell(1,0).getContents());
		String user = driver.findElement(By.className("user_name")).getText();
		System.out.println("User Logged in as:" + user);
		
	 }
	 
	 
/*	 //Test method for Proposal follow up
	 @Test
	 public void proposalFollowup() {
		 help.expand();
		 System.out.println("Click on the '" + driver.findElement(By.id("allfollowups")).getText() + "' Link" );
		 driver.findElement(By.id("allfollowups")).click();
		 help.sleep(2);
		 search("Introductory Mail");
		 List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		 if(leads.size()==0)
			 System.out.println("No leads present to Prospect Identify for Proposals.");
		 else { 
			 leads.get(0).findElement(By.className("work")).click();
			 
		 }
		 
	 }*/
	
	 
	 //Test method for Proposal upload
	 @Test
	 public void proposalUpload() {
		  
		  help.expand();
		  System.out.println("Click on the '" + driver.findElement(By.id("proposalupload")).getText() + "' Link" );
		  driver.findElement(By.id("proposalupload")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  help.sleep(4);
		  
		  if(driver.findElement(By.id("example_info")).getText().equals("Showing 0 to 0 of 0 entries"))
			  System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
		  else {
			   uploadButton();
			  //Entering details to the fields in Quote Upload page
			  driver.findElement(By.name("proposalname")).sendKeys("Proposal Name");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("proposaldescription")).sendKeys("Proposal Description");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("proposal")).sendKeys("E:\\abc1.txt");
			  driver.findElement(By.id("button")).click();
			  help.sleep(4);
			  //Verifying the Success message displayed on the page after uploading the Proposal
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.className("success_msg")).getText());
			  
			  //Closing the Proposal Upload page
			  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			  help.collapse();  
		  }//End of else condition
	 } 
	 
	 @Test
	 public void quoteUpload() {

		  help.expand();
		  System.out.println("Click on the '" + driver.findElement(By.id("quoteupload")).getText() + "' Link" );
		  driver.findElement(By.id("quoteupload")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  sleep(4);
		  
		  if(driver.findElement(By.id("example_info")).getText().equals("Showing 0 to 0 of 0 entries"))
			 System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
		  else {
			  //Verifying no.of leads in the page
			  uploadButton();
			  //Entering details to the fields in Quote Upload page
			  driver.findElement(By.name("quotename")).sendKeys("Quote Name");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("quotedescription")).sendKeys("Quote Description");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("quote")).sendKeys("E:\\abc2.txt");
			  driver.findElement(By.id("button")).click();
			  help.sleep(5);
			  //Verifying the Success message displayed on the page after uploading the Proposal
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.className("success_msg")).getText());
			  
			  //Closing the Quote Upload page
			  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			  help.collapse();
		  }
		 
	 } 
	 
	 
	 @Test 
	 public void testLeadTrackButton() {
		 help.expand();
		 //Clicking on Edit Leads link
		 System.out.println("Click on the '" + driver.findElement(By.id("editLeads")).getText() + "' Link" );
		 driver.findElement(By.id("editLeads")).click();
		 help.sleep(3);
		 List <WebElement> leads1 = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		 System.out.println("No. of leads in the Lead Edit Table:" + leads1.size());
		 
		  //Checking for the Track it button for each lead
		  int trackit=0;
		  for(int i=0; i<leads1.size(); i++) {
			if(leads1.get(i).findElement(By.className("analyse")).isEnabled()) {
			  
			  trackit++;
			}	  
		  }
		  if(trackit==leads1.size())
			  System.out.println("Trackit button is enabled for all leads.");
		  
		  trackitButton(help.random(leads1.size()));
	 } 
	 
	 @Test
	 public void testLeadEditButton() {
		  
		  help.expand();
		  //Clicking on Edit Leads link
		  System.out.println("Click on the '" + driver.findElement(By.id("editLeads")).getText() + "' Link" );
		  driver.findElement(By.id("editLeads")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  pagination();
		  //pageEntries();
		  driver.findElement(By.name("example_length")).findElements(By.tagName("option")).get(3).click();
		  
		  
		  //Verifying no.of leads in the page
		  List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  help.sleep(3);
		  System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
		  
		  //Checking for the buttons for each lead
		  int edit=0;
		  for(int i=0; i<leads.size(); i++) {
			if(leads.get(i).findElement(By.className("edit")).isEnabled()) {
			  edit++;
			}	  
		  }
		  if(edit==leads.size())
			  System.out.println("Edit buttons are enabled for all leads.");
		  
		  //Validating the Track it button
		  int opt1 = help.random(leads.size());
		  String detail =  trackitButton(opt1);
		  
		  //Editing the Leads
		  driver.findElement(By.id("editLeads")).click();
		  driver.findElement(By.name("example_length")).findElements(By.tagName("option")).get(3).click();
		  help.sleep(5);
		  driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(opt1).findElement(By.className("edit")).click();
		  help.sleep(5);
		  System.out.println(driver.findElement(By.cssSelector("span.ui-dialog-title")).getText());
		  driver.findElement(By.id("firstname")).clear();
		  driver.findElement(By.id("firstname")).sendKeys("Jennifer");
		  driver.findElement(By.id("lastname")).clear();
		  driver.findElement(By.id("lastname")).sendKeys("Strauss");
		  driver.findElement(By.id("mobilenumber")).clear();
		  driver.findElement(By.id("mobilenumber")).sendKeys("3-(486)235-8432");
		  driver.findElement(By.id("boardnumber")).clear();
		  driver.findElement(By.id("boardnumber")).sendKeys("8-(104)838-3404");
		  new Select(driver.findElement(By.name("service"))).selectByVisibleText("SAAS");
		  new Select(driver.findElement(By.name("domain"))).selectByVisibleText("Robotics");
		  driver.findElement(By.id("desknumber")).clear();
		  driver.findElement(By.id("desknumber")).sendKeys("3-(618)434-8752");
		  driver.findElement(By.id("editbutton")).click();
		  help.sleep(3);
		  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.className("success_msg")).getText());
		  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
		  
		  //Tracking the edited lead and compare for modifications
		  driver.findElement(By.name("example_length")).findElements(By.tagName("option")).get(3).click();
		  help.sleep(5);
		  String detail1 = trackitButton(opt1);
		  if(detail.equals(detail1))
			  System.out.println("The Lead is not edited");
		  else {
			  System.out.println("The lead details are modified.");
		  }  
	 } 
	 
	 
	// Static Methods 
		
		 //Method for validating Upload button
		 public static void uploadButton() {
			  //Verifying no.of leads in the page
			  List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  System.out.println("No. of leads in the Table:" + leads.size());
			  int upload=0;
			  for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className("upload")).isEnabled()) {
				  upload++;
				}	  
			  }
			  if(upload==leads.size())
				  System.out.println("Upload button is enabled for all " + upload + " leads.");
			  //Clicking on Upload button of a Lead opens a Quote Upload Page
			  int opt = help.random(leads.size());
			  leads.get(opt).findElement(By.className("upload")).click();
			  help.sleep(5);
			  System.out.println(driver.findElement(By.cssSelector("span.ui-dialog-title")).getText());
		 }
		 
		 
		 //No. of Entries per page
		 public  void pageEntries() {
			  //Selecting no.of entries for the table
			  driver.findElement(By.id("example_length")).click();
			  List <WebElement> entries = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
			  help.sleep(4000);
			  System.out.println(entries.size());
			  int opt = help.random(entries.size());
			  entries.get(opt).click();
			  System.out.println("No.of Entries selected for the page:" + entries.get(opt).getText());
		 }
		 
		 //Method for Pagination(Previous and Next buttons)
		 public static void pagination() {
			  System.out.println("Verifying Next & Previous Buttons:");
			  System.out.println(driver.findElement(By.id("example_info")).getText());
			  help.sleep(2);
			  driver.findElement(By.id("example_next")).click();
			  System.out.println("Clicked on Next button");
			  System.out.println(driver.findElement(By.id("example_info")).getText());
			  help.sleep(2);
			  driver.findElement(By.id("example_previous")).click();
			  System.out.println("Clicked on Previous button");
			  System.out.println(driver.findElement(By.id("example_info")).getText());
		 }

		 
		 //Method for validating Track It button
		 public static String trackitButton(int opt1) {
			  List <WebElement> leads1 = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  leads1.get(opt1).findElement(By.className("analyse")).click();
			  help.sleep(3);
			  //Printing the details of the table
			  String details = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody")).getText();
			  System.out.println("____________________________________________________");
			  System.out.println(details);
			  System.out.println("____________________________________________________");
			  return details; 
		 }
		 
		 
		 //Method for validating Search box
		 public static void search(String keyword) {
			 driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(keyword);
			 
		 }

}
