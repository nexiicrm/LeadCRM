package src.crm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
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
	    	//Verifying page is opened properly or not
	    	if(driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText().equals(pagename))
	    		  Reporter.log("<p>" + pagename +" Page is opened");
	    }
	    
	    public void startup(String linkname,String page){
	    	expand();
			driver.findElement(By.id(linkname)).click();
			help.sleep(2);
	  	  	page_validate(page);
	    }
	    
	    public String idnamecmpny(int r){
	    	//Getting lead id,lead name and company
	    	String idnamecmpny1=null;
	    	List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    	if((leads_info.get(r).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table") ||
	    	   (leads_info.get(r).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found")))){
				Reporter.log("<p>" + "Leads table is empty");	
	    	}else{
	    	idnamecmpny1 = leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText() + " "
						+leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText() + " "
						+leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();			  	
	    }
	    	return idnamecmpny1;		
	    }
	    
	    public void verifyleadstatus(String status){
	    	//Verifying lead status for all leads is same or not
	    	String leadno = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
	        driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(status);
	        String leadnos = driver.findElement(By.className(bdm.getProperty("tableinfo_class"))).getText();
	        if(leadno.contains(leadnos)) 
	         Reporter.log("<p>" + "Lead Status of every lead is " +status);
	        else
	         Reporter.log("<p>" + "Lead Status of every lead is not " +status);
	    }
	    
	    
	    public String assignmultipleLeadsname(int r){
	    	//Getting lead id,name and company for assign module
	    	List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    	String idnamecmpny = leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText() + " "
					+leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText() + " "
					+leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(3).getText();
	    	return idnamecmpny;
	    }
	    
	    
	    public List<String> confirmleadstodaysAllfollowups(){
	       //Checking whether leads with todays date in All followups is same in todays followup
	    	Date date = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
	    	driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(simple.format(date));
			Reporter.log(driver.findElement(By.id(bdm.getProperty("pageentriesmessage_id"))).getText());
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).
												findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			List<String> leads = new ArrayList<String>();
			for(int i=0;i<leads_info.size();i++){
				String name = idnamecmpny(i);
				leads.add(name);
			}
			return leads;
	    }
	    
	    public List<String> assignlead_otherSelf(String leadassign){
	    	//Assigning single lead or multiple lead to BDE module	  
			help.sleep(1);	  
			List<WebElement> assign_to_options = driver.findElement(By.id(bdm.getProperty("assignoption_id"))).findElement(By.name(bdm.getProperty("services_assign_name"))).findElements(By.tagName(bdm.getProperty("services_assign_tag")));
			ArrayList<String> sr2= new ArrayList<String>();
			String fnamelname=null;
			try {  
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            connection = DBConnection.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select a.role_name, b.first_name, b.last_name, b.email_id, b.password from crm_role a, crm_user b where"
	              + " 							a.role_id = b.role_id AND a.role_name = 'BDE' "
	              + "										AND delete_status = 'no' Limit 1;");      
	            while (resultSet.next()) {	             
	                fnamelname = resultSet.getString("first_name") +" " +resultSet.getString("last_name") ;
	                String email = resultSet.getString("email_id");
	                String pass = resultSet.getString("password");
	                sr2.add(email);
	                sr2.add(pass);
	              		}
	    	  }
	    	catch (Exception e){ 
		           e.printStackTrace();
		       }
			Reporter.log("<p>" + "Getting assign to whom: " +fnamelname);
			for(int k=0;k<assign_to_options.size();k++){
			if(assign_to_options.get(k).getText().equals(fnamelname))
				assign_to_options.get(k).click();
			  }
			help.sleep(2);
			//Clicking Assign button by selecting any lead name
			List<WebElement> leads_check = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			String name = assignmultipleLeadsname(0);
			String name1 = assignmultipleLeadsname(1);
			if(leads_check.size()==0){
				Reporter.log("<p>" + "Service table is empty");
				return null;         }
			else{
				if(leadassign.equals("one lead")){
					leads_check.get(0).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
				    driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
				    help.sleep(3);
				    Reporter.log("<p>" + "Lead" +" " +name +"::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
				  }
				else if(leadassign.equals("multiple lead")){
					leads_check.get(0).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
					leads_check.get(1).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
				    driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
			        help.sleep(3);
			        Reporter.log("<p>" + "Leads " +name +" and " +name1 +"::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
				  }
			driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.tagName(bdm.getProperty("logout_tag"))).click();
	   	  	return sr2;
		  } 
	    }	 
	    
	    public void verifylead(String name){
	    	//Checking lead name in current page
	    	driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
	    	help.sleep(2);
			List<WebElement> lead_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			if(lead_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found") ||
				lead_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table"))
				Reporter.log("<p>" + "lead name is not found in current page");
		
			help.sleep(4);
	    }
	    
	    public void managementmodule(String name,String finalstatus,String link){
	    	//Verifying lead name by log in to Management module
	    	try {  
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            connection = DBConnection.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("select  a.role_name, b.email_id, b.password "
	                      + "from crm_role a, crm_user b where "
	                      + "a.role_id = b.role_id AND a.role_name = 'Management' AND delete_status='no'Limit 1;");      
	            while (resultSet.next()) {
	                String email = resultSet.getString("email_id");
	                String pass = resultSet.getString("password");
	                help.login(email, pass);
	              	}
	        Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(bdm.getProperty("login_class"))).findElement(By.className(bdm.getProperty("username_class"))).getText());
	        expand();
	  		driver.findElement(By.id(bdm.getProperty(link))).click();
	  		help.sleep(2);
	  		driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
	  			  
	  		WebElement wb = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElement(By.tagName(bdm.getProperty("leads_info_tagname")));
	  		String status = wb.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(4).getText();
	  		if(finalstatus.contains(status))
	  			Reporter.log("<p>" + "Lead is displayed in "+finalstatus +" in Management module with proper lead status ");
	  		else
	  			Reporter.log("<p>" + "Lead is not displayed in " +finalstatus + " in Management module");
	  		Reporter.log("<p> ___________________________________________________________________");       
	           }
	    	  
	       catch (Exception e){ 
	           e.printStackTrace();
	         }
	    }
	    
	   	
		public void research_verify(String module,String research_lead){
			//Checking lead name in Research module
			driver.findElement(By.id(bdm.getProperty("researchphaselink_id"))).click();
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(research_lead);
			List<WebElement> research_bdm = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    	if(research_bdm.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found")){
	    		 Reporter.log("<p>" + " lead table size is empty");
	    		 Reporter.log("<p>" + " lead name not found");
	    	 }
	    	 else{   
			 String name = idnamecmpny(0);
	    	 if(name.equals(research_lead))
				  Reporter.log("<p>" + "lead " +name  +" found in " +module + " research module");
			 else
				  Reporter.log("<p>" + "lead " +name  +" not found in "+module + " research module");
	  		 Reporter.log("<p> ___________________________________________________________________"); 
	    	 }
		}
		
        @Test
        public void a_expand_collapse() {
	        //Getting size of all super links available in left pane
	        List<WebElement> leftpane_options = driver.findElement(By.id(bdm.getProperty("leftpane_id"))).findElements(By.className(bdm.getProperty("leftpane_class")));
	        Reporter.log("<p>" + "Total number of options available in left pane is " +leftpane_options.size());
	  
	        //Expansion of side tree menu
	        //Getting all super options and clicking it
	        for(int i=0;i<leftpane_options.size();i++){
		        Reporter.log("<p>" + leftpane_options.get(i).getText());
		        leftpane_options.get(i).findElement(By.tagName(bdm.getProperty("leftpane_tag"))).click(); 
	        }
	        Reporter.log("<p>" + "Expansion of side tree is functioning properly");
	  
	        Reporter.log("<p> ___________________________________________________________________"); 
	        //Getting all the sub options from super options
	        List<WebElement> inner_links = driver.findElement(By.id(bdm.getProperty("leftpane_id"))).findElements(By.tagName(bdm.getProperty("innerlink_tag")));
	        Reporter.log("<p>" + "Total number of sub options available in left pane is " +inner_links.size());
	        for(int j=0;j<inner_links.size();j++){
		        Reporter.log("<p>" + inner_links.get(j).getText());
	        }
	  
	        //Collapse of side tree menu
	        List<WebElement> leftpane_sub_options = driver.findElement(By.id(bdm.getProperty("leftpane_id"))).findElements(By.className(bdm.getProperty("leftpane_suboption_class")));
	        for(int i=0;i<leftpane_sub_options.size();i++){
		        help.sleep(1);
		        leftpane_sub_options.get(i).findElement(By.tagName(bdm.getProperty("leftpane_tag"))).click();  
	        }
	        Reporter.log("<p>" + "Collapsing of side tree menu is functioning properly");   
  
	        Reporter.log("<p> ___________________________________________________________________"); 
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
    	    List<WebElement> services = driver.findElement(By.name(bdm.getProperty("service_name"))).findElements(By.tagName(bdm.getProperty("service_tag")));
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
    	    String username[]=driver.findElement(By.className(bdm.getProperty("username_class"))).getText().split(" ");
    	    List<WebElement> services_assign = driver.findElement(By.name(bdm.getProperty("services_assign_name"))).findElements(By.tagName(bdm.getProperty("services_assign_tag")));
    	    for(int i=0;i<services_assign.size()-1;i++){
    		    assign.add(services_assign.get(i+1).getText());
    		    if(sr2.get(i).contains(username[3]))
    			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
    		    else if(sr2.get(i).equals(assign.get(i)))
    			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
    		    else
    			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is not displayed");
    	       }
    	    Reporter.log("<p> ___________________________________________________________________"); 
            }
      
	    @Test
	    public void c_matching_service_name(){ 
	        //expanding and clicking on assign lead link	  
	        startup("assignlead","Assign Leads");
	        //Clicking on select service dropdown and getting all the options
	        help.sleep(5);
	        List<WebElement> service_options = driver.findElement(By.id(bdm.getProperty("service_options_id"))).findElement(By.name(bdm.getProperty("service_options_name"))).findElements(By.tagName(bdm.getProperty("service_options_tag")));
	        Reporter.log("<p>" + "Total number of options under select service is= " +service_options.size());
	        int option = random(service_options.size()-4);
	        if(option==0)
	        	option++;
	        service_options.get(option).click();
	        help.sleep(1);
        	HashSet<String> service = new HashSet<String>();
	        Reporter.log("<p>" + "Randomly selected option from select service is = " +service_options.get(option).getText());
	        //Verifying randomly selected option matches with name shown in service name column
	        List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	        if(driver.findElement(By.id("example_previous")).getAttribute("class").equals("paginate_disabled_previous") &&
	        		driver.findElement(By.id("example_next")).getAttribute("class").equals("paginate_disabled_next")){
	        	for(int i=0;i<leads_info.size();i++){
	        		String lead_service = leads_info.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
	        		service.add(lead_service);
	        		}
	        }
	        if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table"))
	        	Reporter.log("<p>" + "leads table is empty");
	        else{
	        	while(!(driver.findElement(By.id(bdm.getProperty("page_id"))).getAttribute("class")).equals("paginate_disabled_next")){
	        		List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	        		for(int i=0;i<leads_info1.size();i++){
	        			String lead_service = leads_info1.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
	        			service.add(lead_service);
	        	}
			driver.findElement(By.id(bdm.getProperty("page_id"))).click();
			sleep(2);
		    }
		    if(service.size()==1)
			    Reporter.log("<p>" + "leads are displayed with service name selected");
		    else
			    Reporter.log("<p>" + "leads are not displayed with service name selected");
		  
		    Reporter.log("<p> ___________________________________________________________________"); 
	  	         }
	       }
	  
	    @Test
	    public void d_selectAllcheckbox(){
	    	//expanding and clicking on assign lead link	  
	    	startup("assignlead","Assign Leads");
	    	new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
	    	help.sleep(1);
	    	//check select all button and verifying it
	    	driver.findElement(By.id(bdm.getProperty("selectallbutton_id"))).click();
	    	List<WebElement> select_lead = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    	for(int i=0;i<select_lead.size();i++){
	    		boolean sel = select_lead.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).isSelected();
	    		if(sel==true)
	    			Reporter.log("<p>" + "lead name: " +select_lead.get(i).findElements(By.tagName("td")).get(1).getText() +" is ticked");
	    		else
	    			Reporter.log("<p>" + "lead name not checked");  
	  			}
	    	Reporter.log("<p> ___________________________________________________________________"); 
	    	}
	  
	    @Test
	    public void e_serviceoptionsvalidation_self(){
	    	startup("assignlead","Assign Leads");
	    	new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
	    	help.sleep(1); 
	    	//Clicking Assign button without selecting assign to whom option
	    	Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ASSIGN TO WHOM OPTION");
	    	driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
	    	Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
	  
	    	//Clicking Assign button without selecting any lead name
	    	Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON WITHOUT SELECTING ANY LEAD");
	    	help.sleep(3);
	    	List<WebElement> assign_to_options = driver.findElement(By.id(bdm.getProperty("assignoption_id"))).findElement(By.name(bdm.getProperty("services_assign_name"))).findElements(By.tagName(bdm.getProperty("services_assign_tag")));
	    	Reporter.log("<p>" + "Getting assign to whom: " +assign_to_options.get(2).getText());
	    	for(int k=0;k<assign_to_options.size();k++){
	    		if(assign_to_options.get(k).getText().equals("Self")){
	    			assign_to_options.get(k).click();
	    		}
	    	}
	    	driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
	    	Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
	  
	    	//Clicking Assign button by selecting any lead name
	    	Reporter.log("<p>" + "CLICKING ON ASSIGN BUTTON BY SELECTING ANY LEAD");
	    	List<WebElement> leads_check1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
	    	String research_lead = assignmultipleLeadsname(0);
	    	Reporter.log("<p>" + "lead name selected is: " +research_lead);
	    	if(leads_check1.size()==0){
	    		Reporter.log("<p>" + "Service table is empty");
	    	}else{
	    		leads_check1.get(0).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
	    		driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
	    		help.sleep(4);
	    		Reporter.log("<p>" + "Lead" +" " +research_lead +"::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
	    	}
	    	research_verify("BDM's",research_lead); 
	    }
	  
	    @Test
		public void f_selecttowhomassign_notself() throws Exception{
			//expanding and clicking on assign lead link	
			startup("assignlead","Assign Leads");
			new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
			help.sleep(3);
			String research_lead = assignmultipleLeadsname(0);
	  		List<String> sr1 = assignlead_otherSelf("one lead");
		    help.login(sr1.get(0),sr1.get(1));
		    Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(bdm.getProperty("login_class"))).findElement(By.className(bdm.getProperty("username_class"))).getText());
		    expand();
		    research_verify("BDE's",research_lead);     
		    }
	  
		  
		@Test
		public void g_assignmultipleLeads_BDE() throws Exception{
			//expanding and clicking on assign lead link	  
	    	startup("assignlead","Assign Leads");
			new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
			help.sleep(3);
			String name = assignmultipleLeadsname(0);
			String name1 = assignmultipleLeadsname(1);
		  	List<String> sr1 = assignlead_otherSelf("multiple lead"); 
	    	help.login(sr1.get(0),sr1.get(1));
			Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(bdm.getProperty("login_class"))).findElement(By.className(bdm.getProperty("username_class"))).getText());
			help.expand();
			research_verify("BDE's",name);
			help.sleep(3);
			research_verify("BDE's",name1);		  
		}
		  
	  
	    @Test
	    public void h_assignleadPagination() throws Exception{
	    	startup("assignlead","Assign Leads");
	    	//Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________"); 
	  }
	  
		@Test
		public void i_researchbuttoncheck(){
			//expanding and clicking on research on lead link
			Reporter.log("<p>" + "*******************RESEARCH PHASE***************************");
  	  		startup("researchPhase","Lead Research");
  	  		//Checking whether research button is available for all leads in page
  	  		List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
  	  		int research=0;
  	  		for(int i=0; i<leads_info.size(); i++) {
  	  			if(leads_info.get(i).findElement(By.className(bdm.getProperty("researchbutton_class"))).isEnabled()) {
  	  				research++;
  	  			} 
  	  		}
  	  		if(research==leads_info.size())
  	  			Reporter.log("<p>" + "Research button is enabled for all leads."); 
		
  	  		Reporter.log("<p> ___________________________________________________________________"); 
		}
		
		@Test
		public void j_researchformfill(){
  	  		startup("researchPhase","Lead Research");
  	  		List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
		
  	  		//Clicking on research button and filling all details in research on lead window
  	  		Reporter.log("<p>" + "Clicking research for lead: " +leads_info.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText());
  	  		String idnamecmpny = leads_info.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(0).getText() + " "
  	  				+leads_info.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText() + " "
  	  				+leads_info.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).getText();
  	  		leads_info.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("researchbutton_class"))).click();
  	  		help.sleep(3);
  	  		Reporter.log("<p>" + "Child window is opened");
  	  		Reporter.log("<p>" + "Child window title is: " +driver.findElement(By.className(bdm.getProperty("windowtitle_class"))).getText());
  	  		Reporter.log("<p>" + "Clicking segregate button without filling all details");
  	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
  	  		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
  	  		new Select(driver.findElement(By.name(bdm.getProperty("conpanyfundstatus_name")))).selectByVisibleText("Listed");
  	  		Reporter.log("<p>" + "Clicking segregate button without filling company status and comment field");
  	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
  	  		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
  	  		new Select(driver.findElement(By.name(bdm.getProperty("companystatus_name")))).selectByVisibleText("Sinking");
  	  		Reporter.log("<p>" + "Clicking segregate button without giving comment");
  	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
  	  		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

  	  		//Entering comment in research on lead form and segregating
  	  		driver.findElement(By.name(bdm.getProperty("researchcomment_name"))).sendKeys("research test");
  	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
  	  		Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
  	  		driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
  	  		//verifying that lead is not present in research phase
  	  		verifylead(idnamecmpny);
		
  	  		//verifying that lead in work phase
  	  		driver.findElement(By.id(bdm.getProperty("workphaselink_id"))).click();
  	  		driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(idnamecmpny);
  	  		List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
  	  		leads_info1.get(0).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
  	  		help.sleep(2);
  	  		//printing research phase table
  	  		Reporter.log("<p>" + "----------------Research phase comments table------------------");
  	  		WebElement research_table = driver.findElement(By.id("body_result")).findElements(By.tagName("table")).get(1);
  	  		Reporter.log("<p>" + research_table.getText());
  	  		Reporter.log("<p>" + "---------------------------------------------------------------");
		
  	  		WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(4);
  	  		String status = abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(3).getText();
  	  		Reporter.log("<p>" + status);
  	  		if(status.contains("Qualified"))
  	  			Reporter.log("<p>" + "Status is displayed correct");
  	  		else
  	  			Reporter.log("<p>" + "Status is incorrect");
  	  		Reporter.log("<p> ___________________________________________________________________"); 
		}
		
		@Test
		public void k_researchPagination() throws Exception{
  	  		startup("researchPhase","Lead Research");
  	  	    //Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________");
  } 
		
		
		@Test
		public void l_workPhasetrackit(){
			Reporter.log("<p>" + "***********************WORK PHASE********************");
			//expanding and clicking on work phase link
	  	  	startup("workPhase","Work on Lead");
			//Checking whether trackit and followup buttons are available for all leads in page
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			int trackit_followup=0;
			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
				Reporter.log("<p>" +"Leads table in work phase is empty");
			}else{
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(bdm.getProperty("trackitbutton_class"))).isEnabled() && leads_info.get(i).findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			Reporter.log("<p>" + "Trackit and Followup button is enabled for all leads.");
			//verifying lead status of each lead is qualified or not
			verifyleadstatus("Qualified");
			
			Reporter.log("<p> ___________________________________________________________________"); 
			}
		}
		
		@Test
		public void m_workphasePagination() throws Exception{
		  	startup("workPhase","Work on Lead");
		    //Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________");
		} 
  
		@Test
		public void n_workphaseFollowupTodaysDate(){
	  	  	startup("workPhase","Work on Lead");
	    	WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
				Reporter.log("<p>" + "Leads table in Workphase is empty");
			}else{
	    	String name = idnamecmpny(0);
	  	    leads_info.findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			
			//clicking proceed button without filling all options
			driver.findElement(By.id(bdm.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			
			//Selecting followup type and clicking proceed button
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.id(bdm.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			
			//Selecting followup type,entering followup comment and clicking proceed button
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("intoductory mail test");
			driver.findElement(By.id(bdm.getProperty("assignbutton_class"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			
			//Selecting todays date and clicking on proceed button
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).click();
			help.sleep(1);
			driver.findElement(By.id(bdm.getProperty("datetoday_id"))).findElement(By.cssSelector(bdm.getProperty("datetoday_css"))).click();
			driver.findElement(By.id(bdm.getProperty("assignbutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
			help.sleep(2);
			
			//verifying that lead is not present in work phase
			verifylead(name);
			
			Reporter.log("<p>" + "********************TODAY'S FOLLOWUP****************************");
			//verifying that lead in today's followup phase
			driver.findElement(By.id(bdm.getProperty("todayfollowupslink_id"))).click();
			help.sleep(2);
	  	  	page_validate("Today Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			//Picking lead id,lead name and company after searching in todays followups
			String name1 = idnamecmpny(0);
			if(name.equals(name1))
				Reporter.log("<p>" + "lead name is present in todays followup");
			else
				Reporter.log("<p>" + "lead name not found in todays followup");
			
		    Reporter.log("<p> ___________________________________________________________________"); 

			}
		}
		
		@Test
		public void o_todaysfollowupPagination() throws Exception{
	  	  	startup("todayfollowups","Today Followups");
	  	    //Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________");

  }
		
			@Test
			public void p_workphaseFuturedate(){
			//Filling all the options by selecting tomorrows date and clicking on proceed button
	  	  	startup("workPhase","Work on Lead");
			//Selecting future date and clicking on proceed 
	  	  	String name = idnamecmpny(0);
			WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
				Reporter.log("<p>" + "Leads table in Workphase is empty");
			}else{
			leads_info.findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Introductory Mail");
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("introductory mail test");
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).click();
			
			//Selecting future date
			List<WebElement> dates = driver.findElement(By.id(bdm.getProperty("futuredate_id"))).findElements(By.className(bdm.getProperty("futuredate_class")));
			Date date = new Date();
			dates.get(date.getDate()).click();
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();

			//verifying that lead is not present in work phase
			verifylead(name);
			
			//verifying that lead in All followup phase
			driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
			help.sleep(2);
	  	  	page_validate("All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			//Picking lead id,lead name and company after searching in all followups
			String name1 = idnamecmpny(0);
			if(name1.equals(name))
				Reporter.log("<p>" + "lead name is present in All followup");
			else
				Reporter.log("<p>" + "lead name not found in All followup");
			
		    Reporter.log("<p> ___________________________________________________________________"); 
			}
		}
				
		@Test
		public void q_allfollowupPagination() throws Exception{
			startup("allfollowups","All Followups");
			//Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________");
		}	
			
		@Test
		public void r_confirmleadsofTodaysDate(){
			//Clicking todays followup link and getting all leads for todays date
			startup("allfollowups","All Followups");
			List<String> leads = confirmleadstodaysAllfollowups();
			//Clicking todays Allfollowup link and getting all leads for todays date
			startup("todayfollowups","Today Followups");		
			List<String> leads1 = confirmleadstodaysAllfollowups();
			//Comparing leads in todays followup and all followups
			if(leads.size()==leads1.size()){
			for(int j=0;j<leads.size();j++){
				if(leads.get(j).equals(leads1.get(j)))
					Reporter.log("<p>" +leads.get(j) + " is found in todays followups ");
				else
					Reporter.log("<p>" +leads.get(j) + " not found in todays followups");
				}
			}
			else
				Assert.fail("leads size in todays followup and all followup are not equal");
			
			Reporter.log("<p> ___________________________________________________________________"); 
		}
			
			
		@Test
		public void s_allfollowup(){
			//Checking trackit and followup button for all leads and printing work phase comments
			Reporter.log("<p>" + "****************ALL FOLLOWUP*******************");
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found")){
				Reporter.log("<p>" + "Leads table in All Followups is empty");
			}else{
			int trackit_followup=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(bdm.getProperty("trackitbutton_class"))).isEnabled() && leads_info.get(i).findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) {
			trackit_followup++; 
				}
			}
			if(trackit_followup==leads_info.size())
			Reporter.log("<p>" + "Trackit and Followup button is enabled for all leads.");
			
			leads_info.get(0).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(bdm.getProperty("table_ids"))).findElements(By.tagName(bdm.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
		    Reporter.log("<p> ___________________________________________________________________"); 	
		   }
		}
		
		@Test
		public void t_allNextFollowup(){
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			String name = idnamecmpny(0);
			WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found")){
				Reporter.log("<p>" + "Leads table in All Followups is empty");
			}else{
			leads_info.findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//selecting followup 4 from followup type dropdown and clicking proceed button
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Followup 4");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

			//Entering comment and clicking
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("followup 4");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

			//Selecting todays date
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).click();
			help.sleep(1);
			driver.findElement(By.id(bdm.getProperty("datetoday_id"))).findElement(By.cssSelector(bdm.getProperty("datetoday_css"))).click();
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();

			//Verifying followup changed to followup 4
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			if(leads_info1.get(0).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText().equals("Followup 4"))
				Reporter.log("<p>" + "Followup status changed to follow up 4");
			
		    Reporter.log("<p> ___________________________________________________________________"); 	
			}
		}
			
		@Test
		public void u_FollowupProspectIdentityProposal(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found"))
				Reporter.log("<p>" + "Leads table is empty");
			else{
			int r = random(leads_info.size());
			String lead_service = leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(3).getText();
			String lead_name = leads_info.get(r).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(1).getText();
			Reporter.log("<p>" + lead_name);
			//clicking followup button for lead and getting title of child window
			leads_info.get(r).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
			help.sleep(1);
			new Select(driver.findElement(By.name(bdm.getProperty("prospecttype_name")))).selectByVisibleText("Proposal");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			help.sleep(2);
			//selecting fix on date
			driver.findElement(By.id(bdm.getProperty("fixon_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//selecting followup email ids
			//retrieving all architect mail ids for particular service from database
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
			new Select(driver.findElement(By.name(bdm.getProperty("emailto_name")))).selectByVisibleText(service_list.get(rand));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//inputing subject
			driver.findElement(By.name(bdm.getProperty("subject_name"))).sendKeys("proposal test");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//inputing message
			driver.findElement(By.name(bdm.getProperty("message_name"))).sendKeys("proposal test message");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//inputing followup comment
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("proposal test comment");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//selecting next followup date
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(5);
			Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(lead_name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			leads_info1.get(0).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(bdm.getProperty("table_ids"))).findElements(By.tagName(bdm.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
			Reporter.log("<p>" + "-----------------------------------------------------------");
			//Verifying current status of that lead
			WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(4);
			String status = abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(3).getText();
			Reporter.log("<p>" + status);
			if(status.contains("Prospect"))
				Reporter.log("<p>" + "status is displayed correct");
			else
				Reporter.log("<p>" + "Status displayed is incorrect");
			}
	      Reporter.log("<p> ___________________________________________________________________"); 
		
		}
		
		@Test
		public void v_FollowupProspectIdentityQuote(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
			//expanding and clicking todays followup link
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			int r = help.random(leads_info.size());
			//randomly getting lead id,name and company from table
			String name = idnamecmpny(r);
			//clicking followup button for lead and getting title of child window
			leads_info.get(r).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			help.sleep(2);
			//Selecting an option from followup type and prospect type dropdown and clicking proceed
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Prospect Identify");
			help.sleep(1);
			new Select(driver.findElement(By.name(bdm.getProperty("prospecttype_name")))).selectByVisibleText("Quote");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			help.sleep(2);
			//selecting fix on date
			driver.findElement(By.id(bdm.getProperty("fixon_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//inputing followup comment
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Quote test comment");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//selecting next followup date
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(4);
			Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();

			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			int r1 = help.random(leads_info1.size());
			leads_info1.get(r1).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
			help.sleep(1);
			Reporter.log("<p>" + "----------------Work phase comments table------------------");
			List<WebElement> workphase_table = driver.findElement(By.id(bdm.getProperty("table_ids"))).findElements(By.tagName(bdm.getProperty("table_tag")));
			Reporter.log("<p>" + workphase_table.get(2).getText());
			
			Reporter.log("<p> ___________________________________________________________________"); 
			}
		
		
		
		@Test
		public void w_proposalQuoteSend(){
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
			Date date = new Date();
	  	  	startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Prospect Identify");
			help.sleep(2);
			
			WebElement track = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			track.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("trackitbutton_class"))).click();
			help.sleep(3);
			WebElement proposal_quote_detail = driver.findElements(By.tagName(bdm.getProperty("leads_info_tag"))).get(3).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(1);
			String upload_file = proposal_quote_detail.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(2).findElement(By.tagName(bdm.getProperty("leadclick_tag"))).getText();
			
			//Checking if proposal/Quote is uploaded or not
			if(upload_file.equals("null"))	
				Reporter.log("<p>" + "First upload Proposal/Quote");
			else{
			driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Prospect Identify");
			help.sleep(2);
			//filling all details for proposal/quote form
			WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			String name = idnamecmpny(0);
			abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
			help.sleep(3);
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Proposal/Quote Send");
			help.sleep(1);
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Proposal/Quote test comment");
			driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" + name +" ::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			WebElement aaa = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElement(By.tagName(bdm.getProperty("leads_info_tagname")));
			String status = aaa.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
			if(status.contains("Proposal/Quote Send"))
				Reporter.log("<p>" + "Status is displayed correct");
			else
				Reporter.log("<p>" + "Status is incorrect");
			}
		
			Reporter.log("<p> ___________________________________________________________________"); 
		}
		
		@Test
		public void x_proposalQuoteaccept(){
		    startup("allfollowups","All Followups");
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Proposal/Quote Send");
			WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElement(By.tagName(bdm.getProperty("leads_info_tagname")));
			if(abc.getText().equals("No matching records found"))
				Reporter.log("<p>" + "Leads table is empty");
			else{
			String name = idnamecmpny(0);
			abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
			help.sleep(3);
			//clicking proceed button without filling any option
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(1);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//entering followuptype option and clicking proceed
			new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Proposal/Quote Accepted");
			help.sleep(1);
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(1);
			Reporter.log("<p>" + "Error--> " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//entering comment and proceed 
			driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Proposal/Quote accepted comment");
			driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
			help.sleep(2);
			Reporter.log("<p>" +name +" ::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();				
				
			//verifying that lead is not present in All followups phase
			verifylead(name);
				
			//verification of lead in closed phase
			driver.findElement(By.id(bdm.getProperty("closedphaselink_id"))).click();
			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
			String name1 = idnamecmpny(0);
			if(name1.equals(name))
				Reporter.log("<p>" + "lead name found in lead close");
			else
				Reporter.log("<p>" + "lead name not found in lead close");
			}
			Reporter.log("<p> ___________________________________________________________________"); 

		}
			
		@Test
		public void y_closeleadPagination() throws Exception{
		  	startup("closedPhase","Closed Phase");
		  	//Validating pagination,entries textbox,sorting technique and search
		  	Reporter.log("<p>" + "************VALIDATING PAGINATION AND ENTRIES BOX***************" + "<p>");
		   	help.pageEntries();
		  	Reporter.log("<p>" + "************VALIDATING SORTING *****************" + "<p>");
	  	  	help.sorting();
	  	    Reporter.log("<p>" + "************VALIDATING PAGE SEARCH *****************" + "<p>");
	  	  	help.searchtable(); 
	  	  	Reporter.log("<p> ___________________________________________________________________"); 
	  }	
		
		@Test
		public void z_verifyleadclosebutton(){
	  	  	startup("closedPhase","Closed Phase");
			//validating lead close button is enabled for all leads
			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
			int close=0;
			for(int i=0; i<leads_info.size(); i++) {
			if(leads_info.get(i).findElement(By.className(bdm.getProperty("closebutton_class"))).isEnabled()) {
			close++; 
				}
			}
			if(close==leads_info.size())
			Reporter.log("<p>" + "close button is enabled for all leads.");	
			//verifying lead status of every lead is same or not
			verifyleadstatus("Prospect");
			Reporter.log("<p> ___________________________________________________________________"); 
		}
		
		@Test
		public void zz_leadcloseCustomer() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			if(w.getText().equals("No data available in table"))
				Reporter.log("<p>" + "Leads table in Lead Close is empty");
			else{
			String name = idnamecmpny(0);
			w.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("closebutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			//clicking proceed button without filling all details 
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//selecting lead status and proceed
			new Select(driver.findElement(By.name(bdm.getProperty("leadstatus_name")))).selectByVisibleText("Customer");
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//Entering project name and proceed
			driver.findElement(By.id(bdm.getProperty("projectname_id"))).sendKeys("Selenium Automation");
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//Entering comment and proceed
			driver.findElement(By.name(bdm.getProperty("closedcomment_name"))).sendKeys("lead customer");
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" +name +" ::" +driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			//closing child window
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.tagName(bdm.getProperty("logout_tag"))).click();
			managementmodule(name,"All Customer","customersList_id");
			Reporter.log("<p> ___________________________________________________________________"); 
			}
		}
		
		@Test
		public void zzz_leadcloseLostCompetition() throws Exception{
	  	  	startup("closedPhase","Closed Phase");
			WebElement w = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
			if(w.getText().equals("No data available in table"))
				Reporter.log("<p>" + "Leads table in Lead Close is empty");
			else{
			String name = idnamecmpny(0);
			w.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("closebutton_class"))).click();
			help.sleep(2);
			Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//Selecting lost competition option from lead status dropdown and proceed
			new Select(driver.findElement(By.name(bdm.getProperty("leadstatus_name")))).selectByVisibleText("Lost Competition");
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//Entering comment and proceed
			driver.findElement(By.name(bdm.getProperty("closedcomment_name"))).sendKeys("lead lost competition");
			driver.findElement(By.id(bdm.getProperty("closephasebutton_id"))).click();
			Reporter.log("<p>" +name +" ::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
			//child window close
			driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();				

			//Validating that lead by login to Management's account.Also verifying lead status
			driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.tagName(bdm.getProperty("logout_tag"))).click();
			managementmodule(name,"All Lost Competition","lostcompetitionlink_id");
			Reporter.log("<p> ___________________________________________________________________"); 
			}
		}
		
  @BeforeMethod
  public void before() throws Exception{
	  help.browser();
	  help.browsererror();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	  help.login(config.getProperty("bdmuser"),config.getProperty("bdmpwd"));
	 
  }
  
  @AfterMethod
  public void after(){
	  driver.quit();
  }
  
}