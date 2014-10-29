package crm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nexiilabs.dbcon.DBConnection;

import testUtils.Helper;

public class BDM_hardik extends Helper{
	    public static Connection connection =null;
	    public static Statement statement;
	    public static ResultSet resultSet,resultSet1;
	    
	    
	    public void page_validate(String pagename){
	    	if(driver.findElement(By.id("body_result")).findElement(By.tagName("h1")).getText().equals(pagename))
	    		  System.out.println(pagename +" Page is opened");
	    }
	    
	    public void startup(String linkname,String page){
	    	expand();
			driver.findElement(By.id(linkname)).click();
			sleep(2);
	  	  	page_validate(page);
	    }
	   	
		public void research_verify(String module,String research_lead){
			driver.findElement(By.id("researchPhase")).click();
			  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(research_lead);
			  List<WebElement> research_bdm = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  String name = research_bdm.get(0).findElements(By.tagName("td")).get(1).getText();
			  if(name.equals(research_lead))
				  System.out.println("lead name found in " +module + " research module");
			  else
				  System.out.println("lead name not found in "+module + " research module");
			  System.out.println("*********************************************");
		}
	
		public void search(){
		//Validating Search box 
		List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  if(leads_info1.size()<=1){
			  System.out.println("Service table is empty");
		  }else{
		  System.out.println("Getting first lead name: " +leads_info1.get(0).findElements(By.tagName("td")).get(1).getText());
		  String Lead_name = leads_info1.get(0).findElements(By.tagName("td")).get(1).getText();
		  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(Lead_name);
		  sleep(1);
		  
		  List<WebElement> leads_info2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  System.out.println("Size of leads table after searching is= " +leads_info2.size());
		  System.out.println("Getting lead name of searched item: " +leads_info2.get(0).findElements(By.tagName("td")).get(1).getText());
		  String search_lead = leads_info2.get(0).findElements(By.tagName("td")).get(1).getText();
		  if(search_lead.equals(Lead_name)){
			 System.out.println("Search text box is functioning properly"); 
		  }else{
			  System.out.println("Error in search text box");
		  }
		  }
		  System.out.println("");
		}	
		
		public void pagination(){
			//Validating pagination
			System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
			String next = driver.findElement(By.id("example_next")).getAttribute("class");
			if(next.contains("enabled")){
				System.out.println("Clicking next button");
				driver.findElement(By.id("example_next")).click();
				System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
				System.out.println("next Pagination is functioning properly");
			}else
				System.out.println("next button is not enabled");
			sleep(1);
			
			String previous = driver.findElement(By.id("example_previous")).getAttribute("class");
			if(previous.contains("enabled")){ 
			System.out.println("Clicking previous button");
			driver.findElement(By.id("example_previous")).click();
			System.out.println("Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
			System.out.println("previous Pagination is functioning properly");
			}else
				System.out.println("previous button is not enabled");
			System.out.println("");
		}
		
		//Validating sorting technique
		public void sorting(String name,int a) {
			List<WebElement> sort = driver.findElement(By.tagName("tr")).findElements(By.tagName("th"));
			  List<WebElement> leads_info3 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  String lead_name_asc = leads_info3.get(0).findElements(By.tagName("td")).get(a).getText();
			  System.out.println(name +" in ascending: " +lead_name_asc);
			  sort.get(a).click();
			  List<WebElement> leads_info4 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  String lead_name_des = leads_info4.get(0).findElements(By.tagName("td")).get(a).getText();
			  System.out.println(name +" in descending: " +lead_name_des);
			  if(a==0){
				  if(Integer.parseInt(lead_name_asc) <  Integer.parseInt(lead_name_des)){
					  System.out.println("sorting is functioning properly in leads id column");
				  }else{
					  System.out.println("Lead id Sorting error");
				  }
				  System.out.println("****************************");
			  }else{
			  int result= lead_name_asc.compareTo(lead_name_des);
			  if(result<0){
				 System.out.println(name +"s" +" are displayed in sorting manner");
			  }else
				  System.out.println(name+ " sorting error");
			  System.out.println("********************************");
			       }
		}
	
		public void sort_name(String name,int a){
			List<WebElement> sort = driver.findElement(By.tagName("tr")).findElements(By.tagName("th"));
				if(a==0) {
					sorting(name,a);
				    } else {
					sort.get(a).click();
					sorting(name,a);
				    		}
			  }
			
		public void entries(){
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
			System.out.println("");
			
		}	

  //    @Test
      public void a_expand_collapse() {
	  //Getting size of all the options in left pane
	  List<WebElement> leftpane_options = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
	  System.out.println("Number of options present in left pane is= " +leftpane_options.size());
	  
	  //Expansion of side tree menu
	  //Getting all super options and clicking it
	  for(int i=0;i<leftpane_options.size();i++){
		  System.out.println(leftpane_options.get(i).getText());
		  leftpane_options.get(i).findElement(By.tagName("span")).click(); 
	  }
	  System.out.println("Expansion of side tree is functioning properly");
	  
	  System.out.println("***********************************************");
	  //Getting all the sub options from super options
	  List<WebElement> inner_links = driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	  System.out.println("Total number of sub options in left pane is= " +inner_links.size());
	  for(int j=0;j<inner_links.size();j++){
		  System.out.println(inner_links.get(j).getText());
	  }
	  
	  //Collapse of side tree menu
	  List<WebElement> leftpane_sub_options = driver.findElement(By.id("tree_menu")).findElements(By.className("          open"));
	  for(int i=0;i<leftpane_sub_options.size();i++){
		  sleep(1);
		  leftpane_sub_options.get(i).findElement(By.tagName("span")).click();  
	  }
	  System.out.println("Collapsing of side tree menu is functioning properly");   
  
	  System.out.println("***************************************************");
      }
      
      
    //  @Test
      public void aa_verifyingServiceNamesandAssign(){ 
    	  startup("assignlead","Assign Leads");
    	  ArrayList<String> sr1= new ArrayList<String>();
    	  ArrayList<String> sr2= new ArrayList<String>();
    	  try {
              
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              connection = DBConnection.getConnection();
              statement = connection.createStatement();
              resultSet = statement.executeQuery("select * from crm_service");      
              while (resultSet.next()) {
             
                  String str = resultSet.getString("service_name");
                  sr1.add(str);                 
             }
              resultSet1 = statement.executeQuery("select first_name, last_name from crm_user where (role_id=4 OR role_id=5) AND delete_status='no'");  
              while (resultSet1.next()){ 
                  sr2.add(resultSet1.getString("first_name") +" " +resultSet1.getString("last_name"));
              }            
           }
    	  
         catch (Exception e){ 
             e.printStackTrace();
         }
    	//Verifying options available under select service dropdown equals services in database
    	  List<WebElement> services = driver.findElement(By.name("service")).findElements(By.tagName("option"));
    	  List<String> service_list = new ArrayList<String>();
    	  System.out.println("***************Checking options under select service*********************");
    	  for(int i=0;i<services.size()-1;i++){
    		  service_list.add(services.get(i+1).getText());
    		  if(sr1.get(i).equals(service_list.get(i)))
    			  System.out.println(service_list.get(i) + "-->service is displayed");
    		  else
    			  System.out.println(service_list.get(i) + "-->service is not displayed");
    	  }
    	  
    	  //Verifying options available under assign to whom equals options in database
    	  System.out.println("***************Checking options under Assign to Whom*********************");
    	  List<String> assign = new ArrayList<String>();
    	  String username[]=driver.findElement(By.className("user_name")).getText().split(" ");
    	  List<WebElement> services_assign = driver.findElement(By.name("assignto")).findElements(By.tagName("option"));
    	  for(int i=0;i<services_assign.size()-1;i++){
    		  assign.add(services_assign.get(i+1).getText());
    		  if(sr2.get(i).contains(username[3])){
    			  System.out.println(assign.get(i) + "-->assign to person is displayed");
    		  }
    		  else if(sr2.get(i).equals(assign.get(i)))
    			  System.out.println(assign.get(i) + "-->assign to person is displayed");
    		  else
    			  System.out.println(assign.get(i) + "-->assign to person is not displayed");
    	  }  
      }
      
	//  @Test
	  public void b_matching_service_name(){ 
	  //expanding and clicking on assign lead link	  
	  startup("assignlead","Assign Leads");

	  //Clicking on select service dropdown and getting all the options
	  sleep(5);
	  List<WebElement> service_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("service")).findElements(By.tagName("option"));
	  System.out.println("Total number of options under select service is= " +service_options.size());
	  int option = random(service_options.size()-3);
	  service_options.get(option).click();
	  sleep(1);
	  System.out.println("Randomly selected option from select service is = " +service_options.get(option).getText());
	  String rand_service = service_options.get(option).getText();
	  
	  //Verifying randomly selected option matches with name shown in service name column
	  List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  if(leads_info.size()<1){
		  System.out.println("Service table is empty");
	  }else{
	  System.out.println("fetching service name from table: " +leads_info.get(0).findElements(By.tagName("td")).get(5).getText());
	  String lead_service = leads_info.get(0).findElements(By.tagName("td")).get(5).getText();
	  if(lead_service.equalsIgnoreCase(rand_service))
		  System.out.println("Service table is displayed with service name selected");
	  else
		  System.out.println("Service table does not match with service name selected");
	 }
	  System.out.println("*************************************************");
	  } 
	  
	//  @Test
	  public void c_selectAllcheckbox(){
	  //expanding and clicking on assign lead link	  
	  startup("assignlead","Assign Leads");
	  new Select(driver.findElement(By.name("service"))).selectByVisibleText("Web");
	  sleep(1);
	  //check select all button and verifying it
	  driver.findElement(By.id("selectall")).click();
	  List<WebElement> select_lead = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  for(int i=0;i<select_lead.size();i++){
		  boolean sel = select_lead.get(i).findElements(By.tagName("td")).get(6).findElement(By.id("checkbox")).isSelected();
			  if(sel==true)
				  System.out.println("lead name: " +select_lead.get(i).findElements(By.tagName("td")).get(1).getText() +" is ticked");
			  else
				  System.out.println("lead name not checked");  
	  		}
	  System.out.println("**************************************");
	  }
	  
	//  @Test
	  public void d_serviceoptionsvalidation_self(){
    	  startup("assignlead","Assign Leads");
		  new Select(driver.findElement(By.name("service"))).selectByVisibleText("Web");
		  sleep(1);
	  
	  //Clicking Assign button without selecting assign to whom option
	  System.out.println("CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ASSIGN TO WHOM OPTION");
	  driver.findElement(By.className("button")).click();
	  System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	//Clicking Assign button without selecting any lead name
	  System.out.println("CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ANY LEAD");
	  sleep(3);
	  List<WebElement> assign_to_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("assignto")).findElements(By.tagName("option"));
	  System.out.println("Getting assign to whom: " +assign_to_options.get(2).getText());
	  for(int k=0;k<assign_to_options.size();k++){
	  if(assign_to_options.get(k).getText().equals("Self")){
		  assign_to_options.get(k).click();
	  }
	  }
	  driver.findElement(By.className("button")).click();
	  System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	  //Clicking Assign button by selecting any lead name
	  System.out.println("CLICKING ON ASSIGN BUTTON BY SELECTING ANY LEAD");
	  List<WebElement> leads_check1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  String research_lead = leads_check1.get(0).findElements(By.tagName("td")).get(1).getText();
	  System.out.println("lead name selected is: " +leads_check1.get(0).findElements(By.tagName("td")).get(1).getText());
	  if(leads_check1.size()==0){
		  System.out.println("Service table is empty");
	  }else{
	  leads_check1.get(0).findElement(By.id("checkbox")).click();
	  driver.findElement(By.className("button")).click();
	  System.out.println("Lead" +" " +research_lead +"::" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  }
	  research_verify("BDM's",research_lead); 
	  }
	  
	//  	@Test
		  public void e_selecttowhomassign_notself() throws Exception{
		  //expanding and clicking on assign lead link	  
    	  startup("assignlead","Assign Leads");
		  new Select(driver.findElement(By.name("service"))).selectByVisibleText("Web");
		  sleep(1);
		  
		  List<WebElement> assign_to_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("assignto")).findElements(By.tagName("option"));
		  System.out.println("Getting assign to whom: " +assign_to_options.get(5).getText());
		  for(int k=0;k<assign_to_options.size();k++){
		  if(assign_to_options.get(k).getText().equals("Sreekar Jakkula"))
			  assign_to_options.get(k).click();
		  }
		 sleep(2);
		  //Clicking Assign button by selecting any lead name
		  List<WebElement> leads_check = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  String research_lead = leads_check.get(0).findElements(By.tagName("td")).get(1).getText();
		  if(leads_check.size()==0)
			  System.out.println("Service table is empty");
		  else{
		  leads_check.get(0).findElement(By.id("checkbox")).click();
		  driver.findElement(By.className("button")).click();
		  sleep(3);
		  System.out.println("Lead" +" " +research_lead +"::" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
		  	}
		  
		  //Validating that lead by login to BDE's account
		  driver.findElement(By.className("user_logout")).findElement(By.tagName("a")).click();
		  help.login(sh2.getCell(0, 1).getContents(),sh2.getCell(1, 1).getContents());
		  System.out.println("logged in as: " +driver.findElement(By.className("content")).findElement(By.className("user_name")).getText());
		  expand();
		  research_verify("BDE's",research_lead);     
		  }
	  
	  
	//  @Test
	  public void f_assignleadPagination(){
		startup("assignlead","Assign Leads");
		new Select(driver.findElement(By.name("service"))).selectByVisibleText("Web");
	  	System.out.println("************VALIDATING PAGINATION***************");
	  	pagination();
	  	System.out.println("************VALIDATING ENTRIES BOX***************");
	  	entries();
	  	System.out.println("************VALIDATING SORTING *****************");
	  	//sort_name("lead id",0);
	  	//sort_name("lead name",1);
	  	//sort_name("lead email",2);
	  	//sort_name("lead company",3);
	  	//sort_name("lead domain",4);
	  	System.out.println("************VALIDATING SEARCH TEXT BOX *****************");
		search();
		  	  
	  }
	  
	//	@Test
		public void g_researchbuttoncheck(){
		//expanding and clicking on research on lead link
		System.out.println("*******************RESEARCH PHASE***************************");
  	  	startup("researchPhase","Lead Research");
		//Checking whether research button is available for all leads in page
		List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		int research=0;
		for(int i=0; i<leads_info.size(); i++) {
		if(leads_info.get(i).findElement(By.className("segregate")).isEnabled()) {
		research++;
			} 
		}
		if(research==leads_info.size())
		System.out.println("Research button is enabled for all leads."); 
		
		System.out.println("********************************************");
		}
		
	//	@Test
		public void gg_researchformfill(){
  	  	startup("researchPhase","Lead Research");
		List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		
		//Clicking on research button and filling all details in research on lead window
		System.out.println("Clicking research for lead: " +leads_info.get(0).findElements(By.tagName("td")).get(1).getText());
		String name = leads_info.get(0).findElements(By.tagName("td")).get(1).getText();
		leads_info.get(0).findElements(By.tagName("td")).get(6).findElement(By.className("segregate")).click();
		sleep(3);
		System.out.println("Child window is opened");
		System.out.println("Child window title is: " +driver.findElement(By.className("ui-dialog-title")).getText());
		System.out.println("Clicking segregate button without filling all details");
		driver.findElement(By.id("segregatebutton")).click();
		System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
		new Select(driver.findElement(By.name("companyfundstatus"))).selectByVisibleText("Listed");
		System.out.println("Clicking segregate button without filling company status and comment field");
		driver.findElement(By.id("segregatebutton")).click();
		System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
		new Select(driver.findElement(By.name("companystatus"))).selectByVisibleText("Sinking");
		System.out.println("Clicking segregate button without giving comment");
		driver.findElement(By.id("segregatebutton")).click();
		System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

		//Entering comment in research on lead form and segregating
		driver.findElement(By.name("researchcomment")).sendKeys("research test");
		driver.findElement(By.id("segregatebutton")).click();
		System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
		driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
	
		//verifying that lead in work phase
		driver.findElement(By.id("workPhase")).click();
		driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
		List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		leads_info1.get(0).findElements(By.tagName("a")).get(0).click();
		sleep(1);
		//printing research phase table
		System.out.println("----------------Research phase comments table------------------");
		WebElement research_table = driver.findElement(By.id("body_result")).findElements(By.tagName("table")).get(1);
		System.out.println(research_table.getText());
		System.out.println("---------------------------------------------------------------");
		
		WebElement abc = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(4);
		String status = abc.findElements(By.tagName("td")).get(3).getText();
		System.out.println(status);
		if(status.contains("Qualified"))
			System.out.println("Status is displayed correct");
		else
			System.out.println("Status is incorrect");
		System.out.println("********************************************************");
		}
		
	//	@Test
		public void h_researchPagination(){
  	  	startup("researchPhase","Lead Research");
		//Validating pagination,entries textbox,sorting technique and search text box
		System.out.println("************VALIDATING PAGINATION***************");
		pagination();
		System.out.println("************VALIDATING ENTRIES BOX***************");
		entries();
		System.out.println("************VALIDATING SORTING *****************");
		//sort_name("lead id",0);
		//sort_name("lead name",1);
		//sort_name("lead email",2);
		//sort_name("lead company",3);
		//sort_name("lead domain",4);
		System.out.println("************VALIDATING SEARCH TEXT BOX *****************");
		search();
  } 
		
		
	//	@Test
		public void i_workPhasetrackit(){
			System.out.println("***********************WORK PHASE********************");
			//expanding and clicking on work phase link
	  	  	startup("workPhase","Work on Lead");
			//Checking whether trackit and followup buttons are available for all leads in page
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			int trackit_followup=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className("analyse")).isEnabled() && leads_info.get(i).findElement(By.className("work")).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			System.out.println("Trackit and Followup button is enabled for all leads.");
		
		}
  
	//	@Test
		public void j_workphaseFollowupTodaysDate(){
	  	  	startup("workPhase","Work on Lead");
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			String name = leads_info.get(0).findElements(By.tagName("td")).get(1).getText();
			leads_info.get(0).findElements(By.tagName("a")).get(1).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			
			//clicking proceed button without filling all options
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			//Selecting followup type and clicking proceed button
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			//Selecting followup type,entering followup comment and clicking proceed button
			driver.findElement(By.name("followupcomment")).sendKeys("intoductory mail test");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			//Selecting todays date and clicking on proceed button
			driver.findElement(By.id("nextfollowupdate")).click();
			sleep(1);
			driver.findElement(By.id("ui-datepicker-div")).findElement(By.cssSelector("td.ui-datepicker-days-cell-over.ui-datepicker-today")).click();
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			sleep(2);
		
			System.out.println("********************TODAY'S FOLLOWUP****************************");
			//verifying that lead in today's followup phase
			driver.findElement(By.id("todayfollowups")).click();
			sleep(2);
	  	  	page_validate("Today Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			if(leads_info1.get(0).findElements(By.tagName("td")).get(1).getText().equals(name))
				System.out.println("lead name is present in todays followup");
			else
				System.out.println("lead name not found in todays followup");
		}
		
		//	@Test
			public void k_workphaseFuturedate(){
			//Filling all the options by selecting tomorrows date and clicking on proceed button
	  	  	startup("workPhase","Work on Lead");
			//Selecting future date and clicking on proceed 
			List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			String name = leads_info1.get(0).findElements(By.tagName("td")).get(1).getText();
			leads_info1.get(0).findElements(By.tagName("a")).get(1).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			sleep(2);
			
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.name("followupcomment")).sendKeys("introductory mail test");
			driver.findElement(By.id("nextfollowupdate")).click();
			
			//Selecting future date
			List<WebElement> dates = driver.findElement(By.id("ui-datepicker-div")).findElements(By.className("ui-state-default"));
			Date date = new Date();
			dates.get(date.getDate()).click();
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();

			//verifying that lead in All followup phase
			driver.findElement(By.id("allfollowups")).click();
			sleep(2);
	  	  	page_validate("All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
			List<WebElement> leads_info2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			if(leads_info2.get(0).findElements(By.tagName("td")).get(1).getText().equals(name))
				System.out.println("lead name is present in All followup");
			else
				System.out.println("lead name not found in All followup");
		}
			
			
			
	//	@Test
		public void l_allfollowup(){
			//Checking trackit and followup button for all leads and printing work phase comments
			System.out.println("****************ALL FOLLOWUP*******************");
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			System.out.println(leads_info.size());
			int trackit_followup=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className("analyse")).isEnabled() && leads_info.get(i).findElement(By.className("work")).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			System.out.println("Trackit and Followup button is enabled for all leads.");
			
			leads_info.get(0).findElements(By.tagName("a")).get(0).click();
			sleep(1);
			System.out.println("----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id("body_result")).findElements(By.tagName("table"));
			System.out.println(workphase_table.get(2).getText());
			System.out.println("------------------------------------------------");
			
		   }
		
	//	@Test
			public void m_allNextFollowup(){
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			String lead_name = leads_info.get(0).findElements(By.tagName("td")).get(1).getText();
			System.out.println(lead_name);
			leads_info.get(0).findElements(By.tagName("a")).get(1).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			sleep(2);
			//selecting followup 4 from followup type dropdown and clicking proceed button
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Followup 4");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			//Entering comment and clicking
			driver.findElement(By.name("followupcomment")).sendKeys("followup 4");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			//Selecting todays date
			driver.findElement(By.id("nextfollowupdate")).click();
			sleep(1);
			driver.findElement(By.id("ui-datepicker-div")).findElement(By.cssSelector("td.ui-datepicker-days-cell-over.ui-datepicker-today")).click();
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();

			//Verifying followup changed to followup 4
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			if(leads_info1.get(0).findElements(By.tagName("td")).get(5).getText().equals("Followup 4"))
				System.out.println("Followup status changed to follow up 4");
		}
			
		
		//@Test
		public void n_FollowupProspectIdentityProposal(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			String lead_name = leads_info.get(0).findElements(By.tagName("td")).get(1).getText();
			System.out.println(lead_name);
			//clicking followup button for lead and getting title of child window
			leads_info.get(0).findElements(By.tagName("a")).get(1).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
			sleep(1);
			new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Proposal");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			sleep(2);
			//selecting fix on date
			driver.findElement(By.id("fixon")).sendKeys(simple.format(date));
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//selecting followup email ids
			new Select(driver.findElement(By.name("to"))).selectByVisibleText("ajay.kovuri8@nexiilabs.com");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//inputing subject
			driver.findElement(By.name("subject")).sendKeys("proposal test");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//inputing message
			driver.findElement(By.name("message")).sendKeys("proposal test message");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//inputing followup comment
			driver.findElement(By.name("followupcomment")).sendKeys("proposal test comment");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//selecting next followup date
			driver.findElement(By.id("nextfollowupdate")).sendKeys(simple.format(date));
			driver.findElement(By.id("button")).click();
			sleep(5);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			leads_info1.get(0).findElements(By.tagName("a")).get(0).click();
			sleep(1);
			System.out.println("----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id("body_result")).findElements(By.tagName("table"));
			System.out.println(workphase_table.get(2).getText());
			System.out.println("-----------------------------------------------------------");
			//Verifying current status of that lead
			WebElement abc = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(4);
			String status = abc.findElements(By.tagName("td")).get(3).getText();
			System.out.println(status);
			if(status.contains("Prospect"))
				System.out.println("Status is displayed correct");
			else
				System.out.println("Status is incorrect");
			System.out.println("********************************************************");
		}
		
	//	@Test
		public void o_FollowupProspectIdentityQuote(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			String lead_name = leads_info.get(0).findElements(By.tagName("td")).get(1).getText();
			//clicking followup button for lead and getting title of child window
			leads_info.get(0).findElements(By.tagName("a")).get(1).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Prospect Identify");
			sleep(1);
			new Select(driver.findElement(By.name("prospectType"))).selectByVisibleText("Quote");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			sleep(2);
			//selecting fix on date
			driver.findElement(By.id("fixon")).sendKeys(simple.format(date));
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//inputing followup comment
			driver.findElement(By.name("followupcomment")).sendKeys("Quote test comment");
			driver.findElement(By.id("button")).click();
			System.out.println("Error-->" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//selecting next followup date
			driver.findElement(By.id("nextfollowupdate")).sendKeys(simple.format(date));
			driver.findElement(By.id("button")).click();
			sleep(4);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();

			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			leads_info1.get(0).findElements(By.tagName("a")).get(0).click();
			sleep(1);
			System.out.println("----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id("body_result")).findElements(By.tagName("table"));
			System.out.println(workphase_table.get(2).getText());
			System.out.println("-----------------------------------------------------------");
		}
		
		
	//	@Test
		public void p_proposalQuoteSend(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Prospect Identify");
			sleep(2);
			
			WebElement track = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
			track.findElements(By.tagName("td")).get(6).findElement(By.className("analyse")).click();
			help.sleep(3);
			WebElement proposal_quote_detail = driver.findElements(By.tagName("tbody")).get(3).findElements(By.tagName("tr")).get(1);
			String upload_file = proposal_quote_detail.findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).getText();
			
			//Checking if proposal/Quote is uploaded or not
			if(upload_file.equals("null"))	
				System.out.println("First upload Proposal/Quote");
			else{
			driver.findElement(By.id("allfollowups")).click();
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Prospect Identify");
			sleep(2);
			
			WebElement abc = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
			String name = abc.findElements(By.tagName("td")).get(1).getText();
			abc.findElements(By.tagName("td")).get(6).findElement(By.className("work")).click();
			sleep(3);
			new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Proposal/Quote Send");
			sleep(1);
			driver.findElement(By.name("followupcomment")).sendKeys("Proposal/Quote test comment");
			driver.findElement(By.id("nextfollowupdate")).sendKeys(simple.format(date));
			driver.findElement(By.id("button")).click();
			sleep(2);
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
			WebElement aaa = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
			String status = aaa.findElements(By.tagName("td")).get(5).getText();
			if(status.contains("Proposal/Quote Send"))
				System.out.println("Status is displayed correct");
			else
				System.out.println("Status is incorrect");
			}
		
			System.out.println("********************************************************");
		}
		
	//	@Test
			public void q_proposalQuoteaccept(){
		  	  	startup("allfollowups","All Followups");
				driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Proposal/Quote Send");
				WebElement abc = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
				String name = abc.findElements(By.tagName("td")).get(1).getText();
				abc.findElements(By.tagName("td")).get(6).findElement(By.className("work")).click();
				sleep(3);
				//clicking proceed button without filling any option
				driver.findElement(By.id("button")).click();
				sleep(1);
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
				//entering followuptype option and clicking proceed
				new Select(driver.findElement(By.name("followuptype"))).selectByVisibleText("Proposal/Quote Accepted");
				sleep(1);
				driver.findElement(By.id("button")).click();
				sleep(1);
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
				//entering comment and proceed 
				driver.findElement(By.name("followupcomment")).sendKeys("Proposal/Quote accepted comment");
				driver.findElement(By.id("button")).click();
				sleep(2);
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
				driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();				
				
				//verification of lead in closed phase
				driver.findElement(By.id("closedPhase")).click();
				driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
				WebElement leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0);
				if(leads_info.findElements(By.tagName("td")).get(1).getText().equals(name))
					System.out.println("lead name is present in lead close");
				else
					System.out.println("lead name not found in lead close");
				System.out.println("********************************************************");
			}
		
	//	@Test
		public void r_verifyleadclosebutton(){
	  	  	startup("closedPhase","Closed Phase");
			//validating lead close button is enabled for all leads
			List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			int close=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className("close")).isEnabled()) {
			close++; 
				}
			}
			if(close==leads_info.size())
			System.out.println("close button is enabled for all leads.");	
			
		}
		
	//	@Test
		public void s_leadcloseCustomer() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
			String name = w.findElements(By.tagName("td")).get(1).getText();
			System.out.println(name);
			w.findElements(By.tagName("td")).get(6).findElement(By.className("close")).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			new Select(driver.findElement(By.name("leadstatus"))).selectByVisibleText("Customer");
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			driver.findElement(By.id("project")).sendKeys("Selenium Automation");
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			
			driver.findElement(By.name("comment")).sendKeys("lead customer");
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			  driver.findElement(By.className("user_logout")).findElement(By.tagName("a")).click();
			  help.login(sh5.getCell(0, 0).getContents(),sh5.getCell(1, 0).getContents());
			  System.out.println("logged in as: " +driver.findElement(By.className("content")).findElement(By.className("user_name")).getText());
			  expand();
			  driver.findElement(By.id("customersList")).click();
			  sleep(2);
			  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
			  
			  WebElement wb = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
			  String status = wb.findElements(By.tagName("td")).get(4).getText();
			  if(status.equals("Customer"))
				 System.out.println("Lead is displayed in All customers in Management module with proper lead status ");
			  else
				 System.out.println("Lead is not displayed in All customers in Management module");
			  
		}
		
	//	@Test
		public void t_leadcloseLostCompetition() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
			String name = w.findElements(By.tagName("td")).get(1).getText();
			System.out.println(name);
			w.findElements(By.tagName("td")).get(6).findElement(By.className("close")).click();
			sleep(2);
			System.out.println("child window title is: " +driver.findElement(By.cssSelector("div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")).getText());
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			new Select(driver.findElement(By.name("leadstatus"))).selectByVisibleText("Lost Competition");
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			
			driver.findElement(By.name("comment")).sendKeys("lead lost competition");
			driver.findElement(By.id("closedphasebutton")).click();
			System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());

			driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			  driver.findElement(By.className("user_logout")).findElement(By.tagName("a")).click();
			  help.login(sh5.getCell(0, 0).getContents(),sh5.getCell(1, 0).getContents());
			  System.out.println("logged in as: " +driver.findElement(By.className("content")).findElement(By.className("user_name")).getText());
			  expand();
			  driver.findElement(By.id("lostCompetitionList")).click();
			  sleep(2);
			  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(name);
			  
			  WebElement wb = driver.findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
			  String status = wb.findElements(By.tagName("td")).get(4).getText();
			  if(status.equals("Lost Competition"))
				 System.out.println("Lead is displayed in All Lost Competition in Management module with proper lead status ");
			  else
				 System.out.println("Lead is not displayed in All Lost Competition in Management module");
			  
		}
		
		
  @BeforeMethod
  public void before() throws Exception{
	  help.browser();
	  help.browsererror();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	  help.login(sh2.getCell(0, 0).getContents(),sh2.getCell(1, 0).getContents());
  }
  
  //@AfterMethod
  public void after(){
	  driver.quit();
  }
}
