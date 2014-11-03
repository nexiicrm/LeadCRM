package src.crm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nexiilabs.dbcon.DBConnection;

import src.testUtils.Helper;

public class BDM_hardik extends Helper{
	    public static Connection connection =null;
	    public static Statement statement;
	    public static ResultSet resultSet,resultSet1;
	    
	    
	    public void page_validate(String pagename){
	    	if(driver.findElement(By.id(or.getProperty("pagevalidate_id"))).findElement(By.tagName(or.getProperty("pagevalidate_tag"))).getText().equals(pagename))
	    		  Reporter.log("<p>" + pagename +" Page is opened");
	    }
	    
	    public void startup(String linkname,String page){
	    	expand();
			driver.findElement(By.id(linkname)).click();
			help.sleep(2);
	  	  	page_validate(page);
	    }
	   	
		public void research_verify(String module,String research_lead){
			driver.findElement(By.id(or.getProperty("researchphaselink_id"))).click();
			  driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(research_lead);
			  List<WebElement> research_bdm = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			  String name = research_bdm.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			  if(name.equals(research_lead))
				  Reporter.log("<p>" + "lead name found in " +module + " research module");
			  else
				  Reporter.log("<p>" + "lead name not found in "+module + " research module");
			  Reporter.log("<p>" + "*********************************************");
		}
	
		public void search(){
		//Validating Search box 
		List<WebElement> leads_info1 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  if(leads_info1.size()<=1){
			  Reporter.log("<p>" + "Service table is empty");
		  }else{
		  Reporter.log("<p>" + "Getting first lead name: " +leads_info1.get(0).findElements(By.tagName("td")).get(1).getText());
		  String Lead_name = leads_info1.get(0).findElements(By.tagName("td")).get(1).getText();
		  driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys(Lead_name);
		  help.sleep(1);
		  
		  List<WebElement> leads_info2 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  Reporter.log("<p>" + "Size of leads table after searching is= " +leads_info2.size());
		  Reporter.log("<p>" + "Getting lead name of searched item: " +leads_info2.get(0).findElements(By.tagName("td")).get(1).getText());
		  String search_lead = leads_info2.get(0).findElements(By.tagName("td")).get(1).getText();
		  if(search_lead.equals(Lead_name)){
			 Reporter.log("<p>" + "Search text box is functioning properly"); 
		  }else{
			  Reporter.log("<p>" + "Error in search text box");
		  }
		  }
		  Reporter.log("<p>" + "");
		}	
		
		public void pagination(){
			//Validating pagination
			Reporter.log("<p>" + "Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
			String next = driver.findElement(By.id("example_next")).getAttribute("class");
			if(next.contains("enabled")){
				Reporter.log("<p>" + "Clicking next button");
				driver.findElement(By.id("example_next")).click();
				Reporter.log("<p>" + "Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
				Reporter.log("<p>" + "next Pagination is functioning properly");
			}else
				Reporter.log("<p>" + "next button is not enabled");
			help.sleep(1);
			
			String previous = driver.findElement(By.id("example_previous")).getAttribute("class");
			if(previous.contains("enabled")){ 
			Reporter.log("<p>" + "Clicking previous button");
			driver.findElement(By.id("example_previous")).click();
			Reporter.log("<p>" + "Number of entries per page: " +driver.findElement(By.id("example_info")).getText());
			Reporter.log("<p>" + "previous Pagination is functioning properly");
			}else
				Reporter.log("<p>" + "previous button is not enabled");
			Reporter.log("<p>" + "");
		}
		
		//Validating sorting technique
		public void sorting(String name,int a) {
			List<WebElement> sort = driver.findElement(By.tagName("tr")).findElements(By.tagName("th"));
			  List<WebElement> leads_info3 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  String lead_name_asc = leads_info3.get(0).findElements(By.tagName("td")).get(a).getText();
			  Reporter.log("<p>" + name +" in ascending: " +lead_name_asc);
			  sort.get(a).click();
			  List<WebElement> leads_info4 = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			  String lead_name_des = leads_info4.get(0).findElements(By.tagName("td")).get(a).getText();
			  Reporter.log("<p>" + name +" in descending: " +lead_name_des);
			  if(a==0){
				  if(Integer.parseInt(lead_name_asc) <  Integer.parseInt(lead_name_des)){
					  Reporter.log("<p>" + "sorting is functioning properly in leads id column");
				  }else{
					  Reporter.log("<p>" + "Lead id Sorting error");
				  }
				  Reporter.log("<p>" + "****************************");
			  }else{
			  int result= lead_name_asc.compareTo(lead_name_des);
			  if(result<0){
				 Reporter.log("<p>" + name +"s" +" are displayed in sorting manner");
			  }else
				  Reporter.log("<p>" + name+ " sorting error");
			  Reporter.log("<p>" + "********************************");
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
			Reporter.log("<p>" + "Total number of options under entries is= " +entries_options.size());
			for(int i=0;i<entries_options.size();i++){
				entries_options.get(i).click();
				Reporter.log("<p>" + "option selected is = " +entries_options.get(i).getAttribute("value"));
				help.sleep(1);
				Reporter.log("<p>" + "correctly displaying leads table: " +driver.findElement(By.id("example_info")).getText());
				
			}
			Reporter.log("<p>" + "Entries dropdown is functioning properly");
			Reporter.log("<p>" + "");
			
		}	

      @Test
      public void a_expand_collapse() {
	  //Getting size of all the options in left pane
	  List<WebElement> leftpane_options = driver.findElement(By.id(or.getProperty("leftpane_id"))).findElements(By.className(or.getProperty("leftpane_class")));
	  Reporter.log("<p>" + "Number of options present in left pane is= " +leftpane_options.size());
	  
	  //Expansion of side tree menu
	  //Getting all super options and clicking it
	  for(int i=0;i<leftpane_options.size();i++){
		  Reporter.log("<p>" + leftpane_options.get(i).getText());
		  leftpane_options.get(i).findElement(By.tagName(or.getProperty("leftpane_tag"))).click(); 
	  }
	  Reporter.log("<p>" + "Expansion of side tree is functioning properly");
	  
	  Reporter.log("<p>" + "***********************************************");
	  //Getting all the sub options from super options
	  List<WebElement> inner_links = driver.findElement(By.id(or.getProperty("leftpane_id"))).findElements(By.tagName(or.getProperty("innerlink_tag")));
	  Reporter.log("<p>" + "Total number of sub options in left pane is= " +inner_links.size());
	  for(int j=0;j<inner_links.size();j++){
		  Reporter.log("<p>" + inner_links.get(j).getText());
	  }
	  
	  //Collapse of side tree menu
	  List<WebElement> leftpane_sub_options = driver.findElement(By.id(or.getProperty("leftpane_id"))).findElements(By.className(or.getProperty("leftpane_suboption_class")));
	  for(int i=0;i<leftpane_sub_options.size();i++){
		  help.sleep(1);
		  leftpane_sub_options.get(i).findElement(By.tagName(or.getProperty("leftpane_tag"))).click();  
	  }
	  Reporter.log("<p>" + "Collapsing of side tree menu is functioning properly");   
  
	  Reporter.log("<p>" + "***************************************************");
      }
      
      
      @Test
      public void b_verifyingServiceNamesandAssign(){ 
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
    	  List<WebElement> services = driver.findElement(By.name(or.getProperty("service_name"))).findElements(By.tagName(or.getProperty("service_tag")));
    	  List<String> service_list = new ArrayList<String>();
    	  Reporter.log("<p>" + "***************Checking options under select service*********************");
    	  for(int i=0;i<services.size()-1;i++){
    		  service_list.add(services.get(i+1).getText());
    		  if(sr1.get(i).equals(service_list.get(i)))
    			  Reporter.log("<p>" + service_list.get(i) + "-->service is displayed");
    		  else
    			  Reporter.log("<p>" + service_list.get(i) + "-->service is not displayed");
    	  }
    	  
    	  //Verifying options available under assign to whom equals options in database
    	  Reporter.log("<p>" + "***************Checking options under Assign to Whom*********************");
    	  List<String> assign = new ArrayList<String>();
    	  String username[]=driver.findElement(By.className(or.getProperty("username_class"))).getText().split(" ");
    	  List<WebElement> services_assign = driver.findElement(By.name(or.getProperty("services_assign_name"))).findElements(By.tagName(or.getProperty("services_assign_tag")));
    	  for(int i=0;i<services_assign.size()-1;i++){
    		  assign.add(services_assign.get(i+1).getText());
    		  if(sr2.get(i).contains(username[3])){
    			  Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
    		  }
    		  else if(sr2.get(i).equals(assign.get(i)))
    			  Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
    		  else
    			  Reporter.log("<p>" + assign.get(i) + "-->assign to person is not displayed");
    	  }  
      }
      
	  @Test
	  public void c_matching_service_name(){ 
	  //expanding and clicking on assign lead link	  
	  startup("assignlead","Assign Leads");

	  //Clicking on select service dropdown and getting all the options
	  help.sleep(5);
	  List<WebElement> service_options = driver.findElement(By.id(or.getProperty("service_options_id"))).findElement(By.name(or.getProperty("service_options_name"))).findElements(By.tagName(or.getProperty("service_options_tag")));
	  Reporter.log("<p>" + "Total number of options under select service is= " +service_options.size());
	  int option = random(service_options.size()-3);
	  service_options.get(option).click();
	  help.sleep(1);
	  Reporter.log("<p>" + "Randomly selected option from select service is = " +service_options.get(option).getText());
	  String rand_service = service_options.get(option).getText();
	  
	  //Verifying randomly selected option matches with name shown in service name column
	  List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
	  if(leads_info.size()<1){
		  Reporter.log("<p>" + "Service table is empty");
	  }else{
	  Reporter.log("<p>" + "fetching service name from table: " +leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(5).getText());
	  String lead_service = leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(5).getText();
	  if(lead_service.equalsIgnoreCase(rand_service))
		  Reporter.log("<p>" + "Service table is displayed with service name selected");
	  else
		  Reporter.log("<p>" + "Service table does not match with service name selected");
	 }
	  Reporter.log("<p>" + "*************************************************");
	  } 
	  
	  @Test
	  public void d_selectAllcheckbox(){
	  //expanding and clicking on assign lead link	  
	  startup("assignlead","Assign Leads");
	  new Select(driver.findElement(By.name(or.getProperty("service_name")))).selectByVisibleText("Web");
	  help.sleep(1);
	  //check select all button and verifying it
	  driver.findElement(By.id(or.getProperty("selectallbutton_id"))).click();
	  List<WebElement> select_lead = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
	  for(int i=0;i<select_lead.size();i++){
		  boolean sel = select_lead.get(i).findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.id(or.getProperty("leadcheckbox_id"))).isSelected();
			  if(sel==true)
				  Reporter.log("<p>" + "lead name: " +select_lead.get(i).findElements(By.tagName("td")).get(1).getText() +" is ticked");
			  else
				  Reporter.log("<p>" + "lead name not checked");  
	  		}
	  Reporter.log("<p>" + "**************************************");
	  }
	  
	  @Test
	  public void e_serviceoptionsvalidation_self(){
    	  startup("assignlead","Assign Leads");
		  new Select(driver.findElement(By.name(or.getProperty("service_name")))).selectByVisibleText("Web");
		  help.sleep(1);
	  
	  //Clicking Assign button without selecting assign to whom option
	  Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ASSIGN TO WHOM OPTION");
	  driver.findElement(By.className(or.getProperty("assignbutton_class"))).click();
	  Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
	  
	//Clicking Assign button without selecting any lead name
	  Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ANY LEAD");
	  help.sleep(3);
	  List<WebElement> assign_to_options = driver.findElement(By.id(or.getProperty("assignoption_id"))).findElement(By.name(or.getProperty("services_assign_name"))).findElements(By.tagName(or.getProperty("services_assign_tag")));
	  Reporter.log("<p>" + "Getting assign to whom: " +assign_to_options.get(2).getText());
	  for(int k=0;k<assign_to_options.size();k++){
	  if(assign_to_options.get(k).getText().equals("Self")){
		  assign_to_options.get(k).click();
	  }
	  }
	  driver.findElement(By.className(or.getProperty("assignbutton_class"))).click();
	  Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
	  
	  //Clicking Assign button by selecting any lead name
	  Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON BY SELECTING ANY LEAD");
	  List<WebElement> leads_check1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
	  String research_lead = leads_check1.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
	  Reporter.log("<p>" + "lead name selected is: " +leads_check1.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText());
	  if(leads_check1.size()==0){
		  Reporter.log("<p>" + "Service table is empty");
	  }else{
	  leads_check1.get(0).findElement(By.id(or.getProperty("leadcheckbox_id"))).click();
	  driver.findElement(By.className(or.getProperty("assignbutton_class"))).click();
	  Reporter.log("<p>" + "Lead" +" " +research_lead +"::" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
	  }
	  research_verify("BDM's",research_lead); 
	  }
	  
	  	@Test
		  public void f_selecttowhomassign_notself() throws Exception{
		  //expanding and clicking on assign lead link	  
    	  startup("assignlead","Assign Leads");
		  new Select(driver.findElement(By.name(or.getProperty("service_name")))).selectByVisibleText("Web");
		  help.sleep(1);
		  
		  List<WebElement> assign_to_options = driver.findElement(By.id(or.getProperty("assignoption_id"))).findElement(By.name(or.getProperty("services_assign_name"))).findElements(By.tagName(or.getProperty("services_assign_tag")));
		  Reporter.log("<p>" + "Getting assign to whom: " +assign_to_options.get(5).getText());
		  for(int k=0;k<assign_to_options.size();k++){
		  if(assign_to_options.get(k).getText().equals("Sreekar Jakkula"))
			  assign_to_options.get(k).click();
		  }
		 help.sleep(2);
		  //Clicking Assign button by selecting any lead name
		  List<WebElement> leads_check = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
		  String research_lead = leads_check.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
		  if(leads_check.size()==0)
			  Reporter.log("<p>" + "Service table is empty");
		  else{
		  leads_check.get(0).findElement(By.id(or.getProperty("leadcheckbox_id"))).click();
		  driver.findElement(By.className(or.getProperty("assignbutton_class"))).click();
		  help.sleep(3);
		  Reporter.log("<p>" + "Lead" +" " +research_lead +"::" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
		  	}
		  
		  //Validating that lead by login to BDE's account
		  driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.tagName(or.getProperty("logout_tag"))).click();
		  help.login(sh2.getCell(0, 1).getContents(),sh2.getCell(1, 1).getContents());
		  Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(or.getProperty("login_class"))).findElement(By.className(or.getProperty("username_class"))).getText());
		  expand();
		  research_verify("BDE's",research_lead);     
		  }
	  
	  
	//  @Test
	  public void f_assignleadPagination(){
		startup("assignlead","Assign Leads");
		new Select(driver.findElement(By.name(or.getProperty("service_options_name")))).selectByVisibleText("Web");
	  	Reporter.log("<p>" + "************VALIDATING PAGINATION***************");
	  	pagination();
	  	Reporter.log("<p>" + "************VALIDATING ENTRIES BOX***************");
	  	entries();
	  	Reporter.log("<p>" + "************VALIDATING SORTING *****************");
	  	//sort_name("lead id",0);
	  	//sort_name("lead name",1);
	  	//sort_name("lead email",2);
	  	//sort_name("lead company",3);
	  	//sort_name("lead domain",4);
	  	Reporter.log("<p>" + "************VALIDATING SEARCH TEXT BOX *****************");
		search();
		  	  
	  }
	  
		@Test
		public void g_researchbuttoncheck(){
		//expanding and clicking on research on lead link
		Reporter.log("<p>" + "*******************RESEARCH PHASE***************************");
  	  	startup("researchPhase","Lead Research");
		//Checking whether research button is available for all leads in page
		List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
		int research=0;
		for(int i=0; i<leads_info.size(); i++) {
		if(leads_info.get(i).findElement(By.className(or.getProperty("researchbutton_class"))).isEnabled()) {
		research++;
			} 
		}
		if(research==leads_info.size())
		Reporter.log("<p>" + "Research button is enabled for all leads."); 
		
		Reporter.log("<p>" + "********************************************");
		}
		
		@Test
		public void h_researchformfill(){
  	  	startup("researchPhase","Lead Research");
		List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
		
		//Clicking on research button and filling all details in research on lead window
		Reporter.log("<p>" + "Clicking research for lead: " +leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText());
		String name = leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
		leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("researchbutton_class"))).click();
		help.sleep(3);
		Reporter.log("<p>" + "Child window is opened");
		Reporter.log("<p>" + "Child window title is: " +driver.findElement(By.className(or.getProperty("windowtitle_class"))).getText());
		Reporter.log("<p>" + "Clicking segregate button without filling all details");
		driver.findElement(By.id(or.getProperty("segregatebutton_id"))).click();
		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
		new Select(driver.findElement(By.name(or.getProperty("conpanyfundstatus_name")))).selectByVisibleText("Listed");
		Reporter.log("<p>" + "Clicking segregate button without filling company status and comment field");
		driver.findElement(By.id(or.getProperty("segregatebutton_id"))).click();
		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
		new Select(driver.findElement(By.name(or.getProperty("companystatus_name")))).selectByVisibleText("Sinking");
		Reporter.log("<p>" + "Clicking segregate button without giving comment");
		driver.findElement(By.id(or.getProperty("segregatebutton_id"))).click();
		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());

		//Entering comment in research on lead form and segregating
		driver.findElement(By.name(or.getProperty("researchcomment_name"))).sendKeys("research test");
		driver.findElement(By.id(or.getProperty("segregatebutton_id"))).click();
		Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
		driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
	
		//verifying that lead in work phase
		driver.findElement(By.id(or.getProperty("workphaselink_id"))).click();
		driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
		List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
		leads_info1.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(0).click();
		help.sleep(2);
		//printing research phase table
		Reporter.log("<p>" + "----------------Research phase comments table------------------");
		WebElement research_table = driver.findElement(By.id(or.getProperty("table_id"))).findElements(By.tagName(or.getProperty("table_tag"))).get(1);
		Reporter.log("<p>" + research_table.getText());
		Reporter.log("<p>" + "---------------------------------------------------------------");
		
		WebElement abc = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(4);
		String status = abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(3).getText();
		Reporter.log("<p>" + status);
		if(status.contains("Qualified"))
			Reporter.log("<p>" + "Status is displayed correct");
		else
			Reporter.log("<p>" + "Status is incorrect");
		Reporter.log("<p>" + "********************************************************");
		}
		
	//	@Test
		public void hh_researchPagination(){
  	  	startup("researchPhase","Lead Research");
		//Validating pagination,entries textbox,sorting technique and search text box
		Reporter.log("<p>" + "************VALIDATING PAGINATION***************");
		pagination();
		Reporter.log("<p>" + "************VALIDATING ENTRIES BOX***************");
		entries();
		Reporter.log("<p>" + "************VALIDATING SORTING *****************");
		//sort_name("lead id",0);
		//sort_name("lead name",1);
		//sort_name("lead email",2);
		//sort_name("lead company",3);
		//sort_name("lead domain",4);
		Reporter.log("<p>" + "************VALIDATING SEARCH TEXT BOX *****************");
		search();
  } 
		
		
		@Test
		public void i_workPhasetrackit(){
			Reporter.log("<p>" + "***********************WORK PHASE********************");
			//expanding and clicking on work phase link
	  	  	startup("workPhase","Work on Lead");
			//Checking whether trackit and followup buttons are available for all leads in page
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			int trackit_followup=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(or.getProperty("trackitbutton_class"))).isEnabled() && leads_info.get(i).findElement(By.className(or.getProperty("followupbutton_class"))).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			Reporter.log("<p>" + "Trackit and Followup button is enabled for all leads.");
		
		}
  
		@Test
		public void j_workphaseFollowupTodaysDate(){
	  	  	startup("workPhase","Work on Lead");
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info.size()<=1)
				Reporter.log("<p>" + "Leads table is empty");
			else{
			String name = leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			leads_info.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			
			//clicking proceed button without filling all options
			driver.findElement(By.id(or.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			
			//Selecting followup type and clicking proceed button
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.id(or.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			
			//Selecting followup type,entering followup comment and clicking proceed button
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("intoductory mail test");
			driver.findElement(By.id(or.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			
			//Selecting todays date and clicking on proceed button
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).click();
			help.sleep(1);
			driver.findElement(By.id(or.getProperty("datetoday_id"))).findElement(By.cssSelector(or.getProperty("datetoday_css"))).click();
			driver.findElement(By.id(or.getProperty("assignbutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
			help.sleep(2);
		
			Reporter.log("<p>" + "********************TODAY'S FOLLOWUP****************************");
			//verifying that lead in today's followup phase
			driver.findElement(By.id(or.getProperty("todayfollowupslink_id"))).click();
			help.sleep(2);
	  	  	page_validate("Today Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info1.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText().equals(name))
				Reporter.log("<p>" + "lead name is present in todays followup");
			else
				Reporter.log("<p>" + "lead name not found in todays followup");
			}
		}
		
			@Test
			public void k_workphaseFuturedate(){
			//Filling all the options by selecting tomorrows date and clicking on proceed button
	  	  	startup("workPhase","Work on Lead");
			//Selecting future date and clicking on proceed 
			List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info1.size()<=1)
				Reporter.log("<p>" + "Leads table is empty");
			else{
			String name = leads_info1.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			leads_info1.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("introductory mail test");
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).click();
			
			//Selecting future date
			List<WebElement> dates = driver.findElement(By.id(or.getProperty("futuredate_id"))).findElements(By.className(or.getProperty("futuredate_class")));
			Date date = new Date();
			dates.get(date.getDate()).click();
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();

			//verifying that lead in All followup phase
			driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).click();
			help.sleep(2);
	  	  	page_validate("All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
			List<WebElement> leads_info2 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info2.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText().equals(name))
				Reporter.log("<p>" + "lead name is present in All followup");
			else
				Reporter.log("<p>" + "lead name not found in All followup");
			}
		}
			
			
			
		@Test
		public void l_allfollowup(){
			//Checking trackit and followup button for all leads and printing work phase comments
			Reporter.log("<p>" + "****************ALL FOLLOWUP*******************");
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			int trackit_followup=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(or.getProperty("trackitbutton_class"))).isEnabled() && leads_info.get(i).findElement(By.className(or.getProperty("followupbutton_class"))).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			Reporter.log("<p>" + "Trackit and Followup button is enabled for all leads.");
			
			leads_info.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(or.getProperty("table_id"))).findElements(By.tagName(or.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
			Reporter.log("<p>" + "------------------------------------------------");
			
		   }
		
		@Test
			public void m_allNextFollowup(){
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info.size()<=1)
				Reporter.log("<p>" + "Leads table is empty");
			else{
			String lead_name = leads_info.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			leads_info.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//selecting followup 4 from followup type dropdown and clicking proceed button
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Followup 4");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());

			//Entering comment and clicking
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("followup 4");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());

			//Selecting todays date
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).click();
			help.sleep(1);
			driver.findElement(By.id(or.getProperty("datetoday_id"))).findElement(By.cssSelector(or.getProperty("datetoday_css"))).click();
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();

			//Verifying followup changed to followup 4
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info1.get(0).findElements(By.tagName(or.getProperty("servicename_tag"))).get(5).getText().equals("Followup 4"))
				Reporter.log("<p>" + "Followup status changed to follow up 4");
		}
			}
			
		
		@Test
		public void n_FollowupProspectIdentityProposal(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info.get(0).findElement(By.tagName(or.getProperty("servicename_tag"))).getText().equals("No matching records found"))
				Reporter.log("<p>" + "Leads table is empty");
			else{
			int r = random(leads_info.size());
			String lead_service = leads_info.get(r).findElements(By.tagName(or.getProperty("servicename_tag"))).get(3).getText();
			String lead_name = leads_info.get(r).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			Reporter.log("<p>" + lead_name);
			//clicking followup button for lead and getting title of child window
			leads_info.get(r).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
			help.sleep(1);
			new Select(driver.findElement(By.name(or.getProperty("prospecttype_name")))).selectByVisibleText("Proposal");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			help.sleep(2);
			//selecting fix on date
			driver.findElement(By.id(or.getProperty("fixon_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//selecting followup email ids
			//retreiving all architect mail ids for particular service from database
			List<String> sr1 = new ArrayList<String>();
			try {
	              
	              Class.forName("com.mysql.jdbc.Driver").newInstance();
	              connection = DBConnection.getConnection();
	              statement = connection.createStatement();
	              resultSet = statement.executeQuery("select  c.email_id "
	                      + "from crm_service a, crm_role b, crm_user c "
	                      + "where a.service_id = c.service_id AND b.role_id = c.role_id AND "
	                      + "b.role_id = 2 AND a.service_name = '" + lead_service + "';");      
	              while (resultSet.next()) {
	             
	                  String str = resultSet.getString("email_id");
	                  sr1.add(str);                 
	             }        
	           }
	    	  
	         catch (Exception e){ 
	             e.printStackTrace();
	         }
			
			//Verifying options available under select service dropdown equals services in database
	    	  List<WebElement> mail_ids = driver.findElement(By.name("to")).findElements(By.tagName("option"));
	    	  List<String> service_list = new ArrayList<String>();
	    	  Reporter.log("<p>" + "***************Checking options under 'To' dropdown*********************");
	    	  for(int i=0;i<mail_ids.size()-1;i++){
	    		  service_list.add(mail_ids.get(i+1).getText());
	    		  if(sr1.get(i).equals(service_list.get(i)))
	    			  Reporter.log("<p>" + service_list.get(i) + "-->mail id of Architect for " +lead_service +" is displayed");
	    		  else
	    			  Reporter.log("<p>" + service_list.get(i) + "-->mail id of Architect for " +lead_service +" is not displayed");
	    	  }
				
			int rand = random(service_list.size());
	    	Reporter.log("<p>" + "Randomly service selected is " +lead_service + " and randomly selecting email id is " +service_list.get(rand));
			new Select(driver.findElement(By.name(or.getProperty("emailto_name")))).selectByVisibleText(service_list.get(rand));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//inputing subject
			driver.findElement(By.name(or.getProperty("subject_name"))).sendKeys("proposal test");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//inputing message
			driver.findElement(By.name(or.getProperty("message_name"))).sendKeys("proposal test message");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//inputing followup comment
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("proposal test comment");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//selecting next followup date
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(5);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			leads_info1.get(0).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(or.getProperty("table_id"))).findElements(By.tagName(or.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
			Reporter.log("<p>" + "-----------------------------------------------------------");
			//Verifying current status of that lead
			WebElement abc = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(4);
			String status = abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(3).getText();
			Reporter.log("<p>" + status);
			if(status.contains("Prospect"))
				Reporter.log("<p>" + status + " is displayed correct");
			else
				Reporter.log("<p>" + "Status displayed is incorrect");
			Reporter.log("<p>" + "********************************************************");
			}
		}
		
		@Test
		public void o_FollowupProspectIdentityQuote(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			if(leads_info.get(0).findElement(By.tagName(or.getProperty("servicename_tag"))).getText().equals("No matching records found"))
				Reporter.log("<p>" + "leads table is empty");
			else{
			int r = help.random(leads_info.size());	
			String lead_name = leads_info.get(r).findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			//clicking followup button for lead and getting title of child window
			leads_info.get(r).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
			help.sleep(1);
			new Select(driver.findElement(By.name(or.getProperty("prospecttype_name")))).selectByVisibleText("Quote");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			help.sleep(2);
			//selecting fix on date
			driver.findElement(By.id(or.getProperty("fixon_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//inputing followup comment
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Quote test comment");
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//selecting next followup date
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(4);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());

			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();

			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			leads_info1.get(r).findElements(By.tagName(or.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(or.getProperty("table_id"))).findElements(By.tagName(or.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
			Reporter.log("<p>" + "-----------------------------------------------------------");
			}
		}
		
		
		@Test
		public void p_proposalQuoteSend(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("Prospect Identify");
			help.sleep(2);
			
			WebElement track = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
			track.findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("trackitbutton_class"))).click();
			help.sleep(3);
			WebElement proposal_quote_detail = driver.findElements(By.tagName(or.getProperty("leads_info_tag"))).get(3).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(1);
			String upload_file = proposal_quote_detail.findElements(By.tagName(or.getProperty("servicename_tag"))).get(2).findElement(By.tagName(or.getProperty("leadclick_tag"))).getText();
			
			//Checking if proposal/Quote is uploaded or not
			if(upload_file.equals("null"))	
				Reporter.log("<p>" + "First upload Proposal/Quote");
			else{
			driver.findElement(By.id(or.getProperty("allfollowupslink_id"))).click();
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("Prospect Identify");
			help.sleep(2);
			//filling all details for proposal/quote form
			WebElement abc = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
			String name = abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("followupbutton_class"))).click();
			help.sleep(3);
			new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Proposal/Quote Send");
			help.sleep(1);
			driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Proposal/Quote test comment");
			driver.findElement(By.id(or.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
			WebElement aaa = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
			String status = aaa.findElements(By.tagName(or.getProperty("servicename_tag"))).get(5).getText();
			if(status.contains("Proposal/Quote Send"))
				Reporter.log("<p>" + "Status is displayed correct");
			else
				Reporter.log("<p>" + "Status is incorrect");
			}
		
			Reporter.log("<p>" + "********************************************************");
		}
		
			@Test
			public void q_proposalQuoteaccept(){
		  	  	startup("allfollowups","All Followups");
				driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("Proposal/Quote Send");
				WebElement abc = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
				if(abc.getText().equals("No matching records found"))
					Reporter.log("Leads table is empty");
				else{
				String name = abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
				abc.findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("followupbutton_class"))).click();
				help.sleep(3);
				//clicking proceed button without filling any option
				driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				help.sleep(1);
				Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
				//entering followuptype option and clicking proceed
				new Select(driver.findElement(By.name(or.getProperty("followuptype_name")))).selectByVisibleText("Proposal/Quote Accepted");
				help.sleep(1);
				driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				help.sleep(1);
				Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
				//entering comment and proceed 
				driver.findElement(By.name(or.getProperty("followupcomment_name"))).sendKeys("Proposal/Quote accepted comment");
				driver.findElement(By.id(or.getProperty("proceedbutton_id"))).click();
				help.sleep(2);
				Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
				driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();				
				
				//verification of lead in closed phase
				driver.findElement(By.id(or.getProperty("closedphaselink_id"))).click();
				driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
				WebElement leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname"))).get(0);
				if(leads_info.findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText().equals(name))
					Reporter.log("<p>" + "lead name is present in lead close");
				else
					Reporter.log("<p>" + "lead name not found in lead close");
				Reporter.log("<p>" + "********************************************************");
				}
			}
		
		@Test
		public void r_verifyleadclosebutton(){
	  	  	startup("closedPhase","Closed Phase");
			//validating lead close button is enabled for all leads
			List<WebElement> leads_info = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElements(By.tagName(or.getProperty("leads_info_tagname")));
			int close=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(or.getProperty("closebutton_class"))).isEnabled()) {
			close++; 
				}
			}
			if(close==leads_info.size())
			Reporter.log("<p>" + "close button is enabled for all leads.");	
			Reporter.log("<p>" + "***********************************************************"); 

		}
		
	//	@Test
		public void t_leadcloseCustomer() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
			String name = w.findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			w.findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("closebutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			//clicking proceed button without filling all details 
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//selecting lead status and proceed
			new Select(driver.findElement(By.name(or.getProperty("leadstatus_name")))).selectByVisibleText("Customer");
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//Entering project name and proceed
			driver.findElement(By.id(or.getProperty("projectname_id"))).sendKeys("Selenium Automation");
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//Entering comment and proceed
			driver.findElement(By.name(or.getProperty("closedcomment_name"))).sendKeys("lead customer");
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//closing child window
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.tagName(or.getProperty("logout_tag"))).click();
			help.login(sh5.getCell(0, 0).getContents(),sh5.getCell(1, 0).getContents());
			Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(or.getProperty("login_class"))).findElement(By.className(or.getProperty("username_class"))).getText());
			expand();
			driver.findElement(By.id(or.getProperty("customersList_id"))).click();
			help.sleep(2);
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
			  
			WebElement wb = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
			String status = wb.findElements(By.tagName(or.getProperty("servicename_tag"))).get(4).getText();
			if(status.equals("Customer"))
				Reporter.log("<p>" + "Lead is displayed in All customers in Management module with proper lead status ");
			else
				Reporter.log("<p>" + "Lead is not displayed in All customers in Management module");
			 Reporter.log("***********************************************************"); 
		}
		
	//	@Test
		public void z_leadcloseLostCompetition() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
			String name = w.findElements(By.tagName(or.getProperty("servicename_tag"))).get(1).getText();
			w.findElements(By.tagName(or.getProperty("servicename_tag"))).get(6).findElement(By.className(or.getProperty("closebutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(or.getProperty("childwindowtitile_css"))).getText());
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//Selecting lost competition option from lead status dropdown and proceed
			new Select(driver.findElement(By.name(or.getProperty("leadstatus_name")))).selectByVisibleText("Lost Competition");
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//Entering comment and proceed
			driver.findElement(By.name(or.getProperty("closedcomment_name"))).sendKeys("lead lost competition");
			driver.findElement(By.id(or.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + driver.findElement(By.id(or.getProperty("resultmsg_id"))).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			//child window close
			driver.findElement(By.cssSelector(or.getProperty("windowclose_css"))).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			driver.findElement(By.className(or.getProperty("logout_class"))).findElement(By.tagName(or.getProperty("logout_tag"))).click();
			help.login(sh5.getCell(0, 0).getContents(),sh5.getCell(1, 0).getContents());
			Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(or.getProperty("login_class"))).findElement(By.className(or.getProperty("username_class"))).getText());
			expand();
			driver.findElement(By.id(or.getProperty("lostcompetitionlink_id"))).click();
			help.sleep(2);
			driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys(name);
			  
			WebElement wb = driver.findElement(By.tagName(or.getProperty("leads_info_tag"))).findElement(By.tagName(or.getProperty("leads_info_tagname")));
			String status = wb.findElements(By.tagName(or.getProperty("servicename_tag"))).get(4).getText();
			if(status.equals("Lost Competition"))
				Reporter.log("<p>" + "Lead is displayed in All Lost Competition in Management module with proper lead status ");
			else
				Reporter.log("<p>" + "Lead is not displayed in All Lost Competition in Management module");
			  
		}
		
		
  @BeforeMethod
  public void before() throws Exception{
	  help.browser();
	  help.browsererror();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	  help.login(sh2.getCell(0, 0).getContents(),sh2.getCell(1, 0).getContents());
  }
  
  @AfterMethod
  public void after(){
	  driver.quit();
  }
}
