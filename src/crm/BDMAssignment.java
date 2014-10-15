package crm;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDMAssignment extends Helper{
  @Test
  public void test1_assignLead() {
	  
	  //Getting size of all the options in left pane
	  List<WebElement> leftpane_options = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
	  System.out.println("Number of options present in left pane is= " +leftpane_options.size());
	  
	  //Expansion of side tree menu
	  //Getting all super options and clicking it
	  for(int i=0;i<leftpane_options.size();i++){
		  System.out.println(leftpane_options.get(i).getText());
		  leftpane_options.get(i).findElement(By.tagName("span")).click();
		  //System.out.println(leftpane_options.get(i).findElement(By.tagName("a")).getText()); 
	  }
	  System.out.println("Expansion of side tree is functioning properly");
	  
	  //Getting all the sub options from super options
	  List<WebElement> inner_links = driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	  System.out.println("Total number of sub options in left pane is= " +inner_links.size());
	  for(int j=0;j<inner_links.size();j++){
		  System.out.println(inner_links.get(j).getText());
	  }
	  
	  //Collapse of side tree menu
	  List<WebElement> leftpane_sub_options = driver.findElement(By.id("tree_menu")).findElements(By.className("          open"));
	  for(int i=0;i<leftpane_sub_options.size();i++){
		  leftpane_sub_options.get(i).findElement(By.tagName("span")).click();  
	  }
	  System.out.println("Collapsing of side tree menu is functioning properly");
	  
  
	  //Expanding assignment and clicking on assign lead link 
	  List<WebElement> leftpane_option = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
	  leftpane_option.get(0).findElement(By.tagName("span")).click();
	  leftpane_option.get(0).findElement(By.id("assignlead")).click();
	  sleep(1);
	  
	  //Clicking on select service dropdown and getting all the options
	  sleep(5);
	  List<WebElement> service_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("service")).findElements(By.tagName("option"));
	  System.out.println("Total number of options under select service is= " +service_options.size());
	  Random service_rand = new Random();
	  int option = service_rand.nextInt(service_options.size()-2);
	  if(option==0){
		  option = 1;
	  }
	  service_options.get(option).click();
	  sleep(1);
	  System.out.println("Randomly selected option from select service is = " +service_options.get(option).getText());
	  String rand_service = service_options.get(option).getText();
	  
	  //Verifying randomly selected option matches with name shown in service name column
	  List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  if(leads_info.size()<=1){
		  System.out.println("Service table is empty");
	  }else{
	  System.out.println("fetching service name from table: " +leads_info.get(0).findElements(By.tagName("td")).get(5).getText());
	  String lead_service = leads_info.get(0).findElements(By.tagName("td")).get(5).getText();
	  if(lead_service.equalsIgnoreCase(rand_service)){
		  System.out.println("Service table is displayed with service name selected");
	  }else{
		  System.out.println("Service table does not match with service name selected");
	  }
	 }
	  
	  //Clicking Assign button without selecting assign to whom option
	  driver.findElement(By.className("button")).click();
	  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	//Clicking Assign button without selecting any lead name
	  sleep(5);
	  List<WebElement> assign_to_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("assignto")).findElements(By.tagName("option"));
	  System.out.println("Getting assign to whom: " +assign_to_options.get(2).getText());
	  for(int k=0;k<assign_to_options.size();k++){
	  if(assign_to_options.get(k).getText().equals("Self")){
		  assign_to_options.get(k).click();
	  }
	  }
	  driver.findElement(By.className("button")).click();
	  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	  //Clicking Assign button by selecting any lead name
	  List<WebElement> leads_check = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  System.out.println("First Lead name= " +leads_check.get(0).findElements(By.tagName("td")).get(1).getText());
	  String research_lead = leads_check.get(0).findElements(By.tagName("td")).get(1).getText();
	  if(leads_check.size()==0){
		  System.out.println("Service table is empty");
	  }else{
	  leads_check.get(0).findElement(By.id("checkbox")).click();
	  driver.findElement(By.className("button")).click();
	  System.out.println("Lead" +" " +research_lead +"::" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  }
  
		//Validating pagination
		System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());  
		System.out.println("Clicking next button");
		driver.findElement(By.id("example_next")).click();
		System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
		System.out.println("Clicking previous button");
		driver.findElement(By.id("example_previous")).click();
		System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
		System.out.println("Pagination is functioning properly");
		System.out.println("***************************************");
		
		//Validating entries box
		List<WebElement> entries_options = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
		System.out.println("Total number of options under entries is= " +entries_options.size());
		for(int i=0;i<entries_options.size();i++){
			entries_options.get(i).click();
			System.out.println("option selected is = " +entries_options.get(i).getAttribute("value"));
			sleep(1);
			System.out.println("correctly displaying leads table: " +driver.findElement(By.id("example_info")).getText());
			
		}
		System.out.println("Entries dropdown is functioning properly");
		System.out.println("****************************************");
		
		  
		  //Sorting lead id column
		  List<WebElement> leads_info2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_id_asc = leads_info2.get(0).findElements(By.tagName("td")).get(0).getText();
		  System.out.println("first lead id in ascending order is= " +lead_id_asc);
		  List<WebElement> sort = driver.findElement(By.tagName("tr")).findElements(By.tagName("th"));
		  System.out.println("Clicking Lead id column");
		  sort.get(0).click();
		  List<WebElement> leads_info3 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_id_des = leads_info3.get(0).findElements(By.tagName("td")).get(0).getText();
		  System.out.println("first lead id in descending order is= "+lead_id_des);
		  if(Integer.parseInt(lead_id_asc) <  Integer.parseInt(lead_id_des)){
			  System.out.println("sorting is functioning properly in leads id column");
		  }else{
			  System.out.println("Lead id Sorting error");
		  }
		  System.out.println("********************************");
		  
		  //Sorting lead name column
		  sort.get(1).click();
		  List<WebElement> leads_info4 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_name_asc = leads_info4.get(0).findElements(By.tagName("td")).get(1).getText();
		  System.out.println("lead name in ascending: " +lead_name_asc);
		  sort.get(1).click();
		  List<WebElement> leads_info5 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_name_des = leads_info5.get(0).findElements(By.tagName("td")).get(1).getText();
		  System.out.println("lead name in descending: " +lead_name_des);
		  int result= lead_name_asc.compareTo(lead_name_des);
		  if(result<0){
			 System.out.println("Lead names are displayed in sorting manner");
		  }else{
			  System.out.println("Leads name sorting error");
		  }
		  System.out.println("********************************");
		  
		  //Sorting lead email column
		  sort.get(2).click();
		  List<WebElement> leads_info6 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_email_asc = leads_info6.get(0).findElements(By.tagName("td")).get(2).getText();
		  System.out.println("lead email in ascending: " +lead_email_asc);
		  sort.get(2).click();
		  List<WebElement> leads_info7 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_email_des = leads_info7.get(0).findElements(By.tagName("td")).get(2).getText();
		  System.out.println("lead email in descending: " +lead_email_des);
		  int result1= lead_name_asc.compareTo(lead_email_des);
		  if(result1<0){
			 System.out.println("Lead emails are displayed in sorting manner");
		  }else{
			  System.out.println("Leads emails sorting error");
		  }
		  System.out.println("***********************************");
		  
		//Sorting lead company column
		  sort.get(3).click();
		  List<WebElement> leads_info8 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_company_asc = leads_info8.get(0).findElements(By.tagName("td")).get(3).getText();
		  System.out.println("lead company in ascending: " +lead_company_asc);
		  sort.get(3).click();
		  List<WebElement> leads_info9 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_company_des = leads_info9.get(0).findElements(By.tagName("td")).get(3).getText();
		  System.out.println("lead company in descending: " +lead_company_des);
		  int result2= lead_name_asc.compareTo(lead_company_des);
		  if(result2<0){
			 System.out.println("Lead company are displayed in sorting manner");
		  }else{
			  System.out.println("Leads company sorting error");
		  }
		  System.out.println("***********************************");
		  
		//Sorting domain column
		  sort.get(4).click();
		  List<WebElement> leads_info10 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_domain_asc = leads_info10.get(0).findElements(By.tagName("td")).get(4).getText();
		  System.out.println("lead domain in ascending: " +lead_domain_asc);
		  sort.get(4).click();
		  List<WebElement> leads_info11 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String lead_domain_des = leads_info11.get(0).findElements(By.tagName("td")).get(4).getText();
		  System.out.println("lead domain in descending: " +lead_domain_des);
		  int result3= lead_name_asc.compareTo(lead_domain_des);
		  if(result3<0){
			 System.out.println("Lead domains are displayed in sorting manner");
		  }else{
			  System.out.println("Leads domain sorting error");
		  }
		  
		  //clicking service name and select all columns
		  sort.get(5).click();
		  sort.get(5).click();
		  sort.get(6).click();
		  sort.get(6).click();
		  
		  
		//Validating Search box 
			List<WebElement> leads_info12 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  if(leads_info12.size()<=1){
				  System.out.println("Service table is empty");
			  }else{
			  System.out.println("Getting first lead name: " +leads_info12.get(0).findElements(By.tagName("td")).get(1).getText());
			  String Lead_name = leads_info12.get(0).findElements(By.tagName("td")).get(1).getText();
			  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(Lead_name);
			  sleep(1);
			  
			  List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  System.out.println("Size of leads table after searching is= " +leads_info1.size());
			  System.out.println("Getting lead name of searched item: " +leads_info1.get(0).findElements(By.tagName("td")).get(1).getText());
			  String search_lead = leads_info1.get(0).findElements(By.tagName("td")).get(1).getText();
			  if(search_lead.equals(Lead_name)){
				 System.out.println("Search text box is functioning properly"); 
			  }else{
				  System.out.println("Error in search text box");
			  }
			  }
			  
			  //closing assign lead link
			  driver.findElement(By.id("tree_menu")).findElement(By.className(" symbol-open")).click(); 
			  System.out.println("*******************RESEARCH PHASE***************************");
			  
	  		  //Clicking research on company link
			  List<WebElement> leftpane_option1 = driver.findElement(By.id("tree_menu")).findElements(By.className("    close"));
			  leftpane_option1.get(1).findElement(By.tagName("span")).click();
			  leftpane_option1.get(1).findElement(By.id("researchPhase")).click();
			  sleep(1);
			  
			  //clicking research button and segregating without selecting company fund status and company status
			  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(research_lead);
			  List<WebElement> leads_info13 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  leads_info13.get(0).findElements(By.tagName("td")).get(6).click();
			  System.out.println("Child window is opened");
			  sleep(5);
			  System.out.println("Child window title is: " +driver.findElement(By.className("ui-dialog-title")).getText());
			  driver.findElement(By.id("segregatebutton")).click();
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			  new Select(driver.findElement(By.name("companyfundstatus"))).selectByVisibleText("Listed");
			  driver.findElement(By.id("segregatebutton")).click();
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			  new Select(driver.findElement(By.name("companystatus"))).selectByVisibleText("Sinking");
			  driver.findElement(By.id("segregatebutton")).click();
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			  //Entering comment in research on lead form and segregating
			  driver.findElement(By.name("researchcomment")).sendKeys("research test");
			  driver.findElement(By.id("segregatebutton")).click();
			  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			  driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			  driver.findElement(By.id("tree_menu")).findElement(By.className("   symbol-open")).click();
			 
			  
			//Validating pagination
				System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());  
				System.out.println("Clicking next button");
				driver.findElement(By.id("example_next")).click();
				System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
				System.out.println("Clicking previous button");
				driver.findElement(By.id("example_previous")).click();
				System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
				System.out.println("Pagination is functioning properly");
				System.out.println("***************************************");  
			  
			//Validating entries box
				List<WebElement> entries_options1 = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
				System.out.println("Total number of options under entries is= " +entries_options1.size());
				for(int i=0;i<entries_options1.size();i++){
					entries_options1.get(i).click();
					System.out.println("option selected is = " +entries_options1.get(i).getAttribute("value"));
					sleep(1);
					System.out.println("correctly displaying leads table: " +driver.findElement(By.id("example_info")).getText());
					
				}
				System.out.println("Entries dropdown is functioning properly");
				System.out.println("****************************************");
			  
				//Validating Search box 
				List<WebElement> leads_info14 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				  if(leads_info14.size()<=1){
					  System.out.println("Service table is empty");
				  }else{
				  System.out.println("Getting first lead name: " +leads_info14.get(0).findElements(By.tagName("td")).get(1).getText());
				  String Lead_name = leads_info14.get(0).findElements(By.tagName("td")).get(1).getText();
				  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(Lead_name);
				  sleep(1);
				  
				  List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				  System.out.println("Size of leads table after searching is= " +leads_info1.size());
				  System.out.println("Getting lead name of searched item: " +leads_info1.get(0).findElements(By.tagName("td")).get(1).getText());
				  String search_lead = leads_info1.get(0).findElements(By.tagName("td")).get(1).getText();
				  if(search_lead.equals(Lead_name)){
					 System.out.println("Search text box is functioning properly"); 
				  }else{
					  System.out.println("Error in search text box");
				  }
				  }	
				
  }	  		  
  
  @BeforeTest
  public void before() throws Exception{
	  help.browser();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	//logging into BDM account and extracting credentials from spreadsheet 3
	  //sh2 = w.getSheet(2);
	  help.login(sh2.getCell(0, 0).getContents(),sh2.getCell(1, 0).getContents());
  }
}
