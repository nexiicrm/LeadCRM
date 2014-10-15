package crm;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDM extends Helper {
	
	
	@BeforeTest
	public void before() throws Exception {
		browser();
		maxbrowser();
		driver.get(config.getProperty("url"));
		browsererror();
		sh = w.getSheet(2);
		//Login for BDM module
		login(sh.getCell(0,0).getContents(), sh.getCell(1,0).getContents());
		String user = driver.findElement(By.className("user_name")).getText();
		System.out.println("User Logged in as:" + user);
		
	}
	  //Test method for Proposal upload
	 @Test
	  public void proposal() throws InterruptedException {
		  
		  //Verifying the no.of options in the left pane and Expanding the Proposal Uploads option
		  List <WebElement> options = driver.findElement(By.id("tree_menu")).findElements(By.className(" close"));
		  System.out.println("The left pane has '" + options.size() + "' Options");
		  System.out.println("The option selected is:" + options.get(4).getText());
		  options.get(4).findElement(By.tagName("span")).click();
		  System.out.println("Click on the '" + driver.findElement(By.id("proposalupload")).getText() + "' Link" );
		  driver.findElement(By.id("proposalupload")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  
		  if(driver.findElement(By.id("example_info")).getText().equals("Showing 0 to 0 of 0 entries"))
			  System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
		  
		  else {
			  //Verifying no.of leads in the page
			  List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
			  
			  //Checking for the Upload button for each lead
			  int upload=0;
			  for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className("upload")).isEnabled()) {
				  upload++;
				}	  
			  }
			  if(upload==leads.size())
				  System.out.println("Upload button is enabled for all " + upload + " leads.");
		  
			  //Clicking on Upload button of a Lead opens a Quote Upload Page
			  Random r = new Random();
			  int opt = r.nextInt(leads.size());
			  leads.get(opt).findElement(By.className("upload")).click();
			  sleep(5);
			  System.out.println(driver.findElement(By.cssSelector("span.ui-dialog-title")).getText());
			  
			  //Entering details to the fields in Quote Upload page
			  driver.findElement(By.name("proposalname")).sendKeys("Proposal Name");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("proposaldescription")).sendKeys("Proposal Description");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("proposal")).sendKeys("E:\\abc1.txt");
			  //driver.findElement(By.id("button")).click();
			  Thread.sleep(4000);
			  //Verifying the Success message displayed on the page after uploading the Proposal
			  //System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.className("success_msg")).getText());
			  
			  //Closing the Proposal Upload page
			  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			  
			  //Collapsing Proposal Uploads option
			  driver.findElement(By.id("tree_menu")).findElement(By.className(" open")).findElement(By.className("  symbol-open")).click();
			  
			  
		  }//End of else condition
	  }
	 
	 @Test
	 public void quote() throws InterruptedException {
		  
		  //Verifying the no.of options in the left pane and Expanding the Quote Uploads option
		  List <WebElement> option = driver.findElement(By.id("tree_menu")).findElements(By.className(" close"));
		  System.out.println("The left pane has '" + option.size() + "' Options");
		  System.out.println("The option selected is:" + option.get(5).getText());
		  option.get(5).findElement(By.tagName("span")).click();
		  System.out.println("Click on the '" + driver.findElement(By.id("quoteupload")).getText() + "' Link" );
		  driver.findElement(By.id("quoteupload")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  
		  if(driver.findElement(By.id("example_info")).getText().equals("Showing 0 to 0 of 0 entries"))
			  System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
		  
		  else {
			  //Verifying no.of leads in the page
			  List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
			  
			  //Checking for the upload button for each lead
			  int upload=0;
			  for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className("upload")).isEnabled()) {
				  upload++;
				}	  
			  }
			  if(upload==leads.size()) 
				  System.out.println("Upload button is enabled for all " + upload + " leads.");
		  
			  //Clicking on Upload button of a Lead opens a Quote Upload Page
			  Random r = new Random();
			  int opt = r.nextInt(leads.size());
			  leads.get(opt).findElement(By.className("upload")).click();
			  sleep(5);
			  System.out.println(driver.findElement(By.cssSelector("span.ui-dialog-title")).getText());
			  
			  //Entering details to the fields in Quote Upload page
			  driver.findElement(By.name("quotename")).sendKeys("Quote Name");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("quotedescription")).sendKeys("Quote Description");
			  driver.findElement(By.id("button")).click();
			  driver.findElement(By.name("quote")).sendKeys("E:\\abc2.txt");
			  
			  //driver.findElement(By.id("button")).click();
			  Thread.sleep(4000);
			  //Verifying the Success message displayed on the page after uploading the Proposal
			  //System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.className("success_msg")).getText());
			  
			  //Closing the Quote Upload page
			  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			  
			  sleep(3);
			//Collapsing Quote Uploads option
			  driver.findElement(By.id("tree_menu")).findElement(By.className(" open")).findElement(By.className("  symbol-open")).click();
		  }
		 
	  }
	 
	 
	 @Test
	  public void testleadEdit() throws InterruptedException {
		  
		  //Verifying the no.of options in the left pane and Expanding the Lead Edit option
		  List <WebElement> options = driver.findElement(By.id("tree_menu")).findElements(By.className(" close"));
		  System.out.println("The left pane has '" + options.size() + "' Options");		  
		  System.out.println("The option selected is:" + options.get(7).getText());
		  options.get(7).findElement(By.tagName("span")).click();
		  
		  //Clicking on Edit Leads link
		  System.out.println("Click on the '" + driver.findElement(By.id("editLeads")).getText() + "' Link" );
		  driver.findElement(By.id("editLeads")).click();
		  
		  //Verifying whether the required page is loaded or not
		  System.out.println("Page loaded is:" + driver.findElement(By.id("container")).findElement(By.tagName("h1")).getText());
		  
		  //Selecting no.of entries for the table
		  driver.findElement(By.id("example_length")).click();
		  List <WebElement> entries = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
		  Thread.sleep(4000);
		  System.out.println(entries.size());
		   Random r = new Random();
		  int opt = r.nextInt(entries.size());
		  entries.get(opt).click();
		  System.out.println("No.of Entries selected for the page:" + entries.get(opt).getText());
		  
		  //Verifying no.of leads in the page
		  List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
		 
		  //Checking for the Track it and Edit buttons for each lead
		  int edit=0, trackit=0;
		  for(int i=0; i<leads.size(); i++) {
			if((leads.get(i).findElement(By.className("analyse")).isEnabled()) && (leads.get(i).findElement(By.className("edit")).isEnabled())) {
			  edit++;
			  trackit++;
			}	  
		  }
		  if((edit==leads.size()) && (trackit==leads.size()))
			  System.out.println("Trackit and Edit buttons are enabled for all leads.");
		  
		  //Validating the Track it button
		  Random r1 = new Random();
		  int opt1 = r1.nextInt(leads.size());
		  leads.get(3).findElement(By.className("analyse")).click();
		  
		  
		  
		  //Printing the details of the table
		  String details = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody")).getText();
		  System.out.println(details);
		  
		  driver.findElement(By.id("editLeads")).click();
		  driver.findElement(By.name("example_length")).findElements(By.tagName("option")).get(3).click();
		  sleep(5);
		  driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(opt1).findElement(By.className("edit")).click();
		  sleep(5);
		  System.out.println(driver.findElement(By.cssSelector("span.ui-dialog-title")).getText());
		  
		  
	  
	  
	  
	  }

}
