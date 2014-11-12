package src.crm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	       
        	@Test
        	public void a_paginations() throws Exception { 
	        	help.expand();
	        	
	        	String user = driver.findElement(By.className(bdm.getProperty("username_class"))).getText();
	          	Reporter.log("<p>user " + user);
	          	if (user.contains("Hi ! BDM"))
	          		Reporter.log("<p>   Logged in as BDM user ");
	          	else
	          		Assert.fail("You have not logged in as BDM user.");
	          	
	          	//This tree checks for the tree links of BDE page
	      		sidetreemenuverify(2);
	        	
	      		// Assign Lead
	        	startup("assignlead", "Assign Leads");
	        	new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("SAAS");
	        	help.sleep(3);
	        	paginate(); 
	
	        	// Reasearch Phase
	        	startup("researchPhase","Lead Research");
	        	paginate();
	
	        	// Work on Lead
	        	startup("workPhase","Work on Lead");
	        	paginate();
	
	        	// Today's follow up
	        	startup("todayfollowups","Today Followups");
	        	paginate();
	
	        	//All Follow up
	        	startup("allfollowups","All Followups");
	        	paginate();
	
	        	// Lead Close
	        	startup("closedPhase","Closed Phase");
	        	paginate();
	  
		        Reporter.log("<p> ___________________________________________________________________"); 
	       }
      
        	/*Verifying options available under Select service and Assign to whom dropdown's
           	 * with the options present in database
            */
            @Test
            public void b_LC_TS_26_verifyingServiceNamesandAssign() { // ts26_tc001 (Hardik)
            	help.expand();
            	
            	//startup function checks that valid page is opened or not
        	    startup("assignlead","Assign Leads");
        	    
        	    //Two lists for adding all services and assign person
        	    ArrayList<String> sr1= new ArrayList<String>();
        	    ArrayList<String> sr2= new ArrayList<String>();
        	    
        	    try 
        	    {
        	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
                    connection = DBConnection.getConnection();
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("select * from crm_service");      
                    while (resultSet.next()) 
                    {
                        String str = resultSet.getString("service_name");
                        sr1.add(str);                 
                    }
                    resultSet1 = statement.executeQuery("select first_name, last_name from crm_user where (role_id=4 OR role_id=5) AND delete_status='no'");  
                    while (resultSet1.next()){ 
                        sr2.add(resultSet1.getString("first_name") +" " +resultSet1.getString("last_name"));
                      }            
        	    }
                catch (Exception e)
        	    { 
                    e.printStackTrace();
                }
        	    
        	    //Verifying options available under select service dropdown equals services in database
        	    List<WebElement> services = driver.findElement(By.name(bdm.getProperty("service_name"))).findElements(By.tagName(bdm.getProperty("service_tag")));
        	    List<String> service_list = new ArrayList<String>();
        	    Reporter.log("<p>" + "***************Checking options under select service*********************");
        	    
        	    //loop runs for total number of services present
        	    for(int i=0;i<services.size()-1;i++) 
        	    {
        		    service_list.add(services.get(i+1).getText());
        		    //checking if all services present in dropdown equals services in DB
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
        	    //loop runs for total number of assign person present
        	    for(int i=0;i<services_assign.size()-1;i++)
        	    {
        		    assign.add(services_assign.get(i+1).getText());
        		    //checking if all assign names present in dropdown equals names in DB
        		    if(sr2.get(i).contains(username[3]))
        			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
        		    else if(sr2.get(i).equals(assign.get(i)))
        			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is displayed");
        		    else
        			    Reporter.log("<p>" + assign.get(i) + "-->assign to person is not displayed");
        	    }
        	    Reporter.log("<p> ___________________________________________________________________"); 
            }
            	
        
      
            /*Checking whether selected service matches with the services shown in
             * service table
            */
    	    @Test
    	    public void c_LC_TS_26_matching_service_name() { // ts26_tc002 (Hardik)
    	        
    	    	//expanding and clicking on assign lead link	
    	    	help.expand();
            	
    	    	//startup function checks that valid page is opened or not
    	    	startup("assignlead","Assign Leads");
    	        
    	    	//Clicking on select service dropdown and getting all the options
    	        help.sleep(5);
    	        
    	        //Getting total number of services present
    	        List<WebElement> service_options = driver.findElement(By.id(bdm.getProperty("service_options_id"))).findElement(By.name(bdm.getProperty("service_options_name"))).findElements(By.tagName(bdm.getProperty("service_options_tag")));
    	        Reporter.log("<p>" + "Total number of options under select service is= " +service_options.size());
    	        int service_option = random(service_options.size()-4);
    	        
    	        if(service_option==0)
    	        	service_option++;
    	        service_options.get(service_option).click();
    	        help.sleep(1);
    	        
    	        //getting service names of all leads and adding into hashset
            	HashSet<String> service = new HashSet<String>();
    	        Reporter.log("<p>" + "Randomly selected option from select service is = " +service_options.get(service_option).getText());
    	        
    	        //Verifying randomly selected option matches with name shown in service name column
    	        List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    	        
    	        //If both next and previous buttons are disabled then pick services names whichever present
    	        if(driver.findElement(By.id("example_previous")).getAttribute("class").equals("paginate_disabled_previous") &&
    	        		driver.findElement(By.id("example_next")).getAttribute("class").equals("paginate_disabled_next"))
    	        {
    	        	for(int i=0;i<leads_info.size();i++)
    	        	{
    	        		String lead_service = leads_info.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
    	        		service.add(lead_service);
    	        	}
    	        }
    	        
    	        //If leads table is empty then will throw message
    	        if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table"))
    	        	Reporter.log("<p>" + "leads table is empty");
    	        else
    	        {
    	        	//If next button is enabled then paginate till last page and add all services into hashset
    	        	while(!(driver.findElement(By.id(bdm.getProperty("page_id"))).getAttribute("class")).equals("paginate_disabled_next"))
    	        	{
    	        		List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    	        		for(int i=0;i<leads_info1.size();i++){
    	        			String lead_service = leads_info1.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
    	        			service.add(lead_service);
    	        		}
    	        		
    	        		driver.findElement(By.id(bdm.getProperty("page_id"))).click();
    	        		sleep(2);
    	        	}
    	        	
    	        	//If hashest size is 1 then all leads are displayed with same service orelse not	
    	        	if(service.size()==1)
    	        		Reporter.log("<p>" + "leads are displayed with service name selected");
    	        	else
    	        		Reporter.log("<p>" + "leads are not displayed with service name selected");
    		  
    	        	Reporter.log("<p> ___________________________________________________________________"); 
    	        }
    	    } 
    	  
    	    
	  
    	    /*Selecting service from select service dropdown and ticking select all button
    	     * and verifying all leads are ticked or not
    	    */
    	    @Test
    	    public void  d_LC_TS_26_selectAllcheckbox(){ // ts26_tc003 (Hardik)
    	    	
    	    	//expanding and clicking on assign lead link 
    	    	help.expand();
            	
    	    	//startup function checks that valid page is opened or not
    	    	startup("assignlead","Assign Leads");
    	    	new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
    	    	help.sleep(3);
    	    	
    	    	//check select all button and verifying it
    	    	driver.findElement(By.id(bdm.getProperty("selectallbutton_id"))).click();
    	    	
    	    	List<WebElement> select_lead = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    	    	
    	    	//Checking that all leads displayed in table are ticked or not
    	    	for(int i=0;i<select_lead.size();i++)
    	    	{
    	    		boolean sel = select_lead.get(i).findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).isSelected();
        	    	if(sel==true)
        	    	Reporter.log("<p>" + "lead name: " +select_lead.get(i).findElements(By.tagName("td")).get(1).getText() +" is ticked");
        	    	else
        	    	Reporter.log("<p>" + "lead name not checked"); 
    	    	}
    	    	Reporter.log("<p> ___________________________________________________________________"); 
    	    }
    	  
    	    
    	    
    	    /*Assigning one lead to self(i.e BDM)module and verifying that lead is 
    	     *assigned or not
    	    */
    	    @Test
    	    public void  e_LC_TS_27_serviceoptionsvalidation_self(){ // ts27_tc001 (Hardik)
    	    	help.expand();
    	    	
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
    	    	
    	    	for(int k=0;k<assign_to_options.size();k++)
    	    	{
    	    		if(assign_to_options.get(k).getText().equals("Self"))
    	    		{
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
    	    	
    	    	if(leads_check1.size()==0)
    	    	{
    	    		Reporter.log("<p>" + "Service table is empty");
    	    	}
    	    	else
    	    	{
    	    		leads_check1.get(0).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
    	    		driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
    	    		help.sleep(4);
    	    		Reporter.log("<p>" + "Lead" +" " +research_lead +"::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
    	    	}
    	    	
    	    	//Checking that lead in research phase
    	    	research_verify("BDM's",research_lead); 
    	    }   
    	    
    	    
    	    
    	    /*Assigning two leads to self(i.e BDM) module and verifying that leads is present
    	     * in BDM account or not.
    	    */
    	    @Test
    	    public void f_LC_TS_27_assignmultipleLeads_BDM () throws Exception{  // ts27_tc002 (Hardik)
    	    	
    	    	//expanding and clicking on assign lead link 
    	    	help.expand();
    		    
    	    	startup("assignlead","Assign Leads");
    		    
    	    	new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
    		    new Select(driver.findElement(By.name("assignto"))).selectByVisibleText("Self");
    		    help.sleep(3);
    		    
    		    //Getting names of two leads
    		    String name = assignmultipleLeadsname(0);
    		    String name1 = assignmultipleLeadsname(1);
    		    
    		    List<WebElement> leads_check = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    		    
    		    if(leads_check.size()==0)
    		    {
    		    	Reporter.log("<p>" + "Service table is empty");
    		    }
    		    else
    		    {
    		    	leads_check.get(0).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
    		    	leads_check.get(1).findElement(By.id(bdm.getProperty("leadcheckbox_id"))).click();
    		    	driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
    		    	help.sleep(3);
    		    	Reporter.log("<p>" + "Lead" +" " +name +" and " +name1 +":: " +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
    		    	//Checking assigned lead name in research phase
    		    	research_verify("BDM",name);
    		    	help.sleep(3);
    		    	//Checking assigned lead name in research phase
    		    	research_verify("BDM",name1); 
    		    }
    	    }    
    	    
    	    
    	    
    	    /*Assigning single lead to BDE module and verifying that leads is present
    	     * in BDE account or not.
    	    */
    	    @Test
    		public void  g_LC_TS_28_assignto_bde() throws Exception{ // ts28_tc001 (Hardik)
    			
    			//expanding and clicking on assign lead link	
    		    help.expand();
    			
    		    startup("assignlead","Assign Leads");
    			
    		    new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
    			help.sleep(3);
    			
    			//Getting lead id,lead name and company
    			String research_lead = assignmultipleLeadsname(0);
    			
    			//Assigning lead to BDE by retrieving name from DB and checking that lead name in that module
    	  		List<String> sr1 = assignlead_otherSelf("one lead");
    	  		
    	  		//Login with that BDE credentials
    		    help.login(sr1.get(0),sr1.get(1));
    		    Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(bdm.getProperty("login_class"))).findElement(By.className(bdm.getProperty("username_class"))).getText());
    		    help.expand();
    	    	
    		    //Checking assigned lead name in research phase
    		    research_verify("BDE's",research_lead);     
    		}
    	  
    		
    		
    	    /*Assigning two leads to BDE module and verifying that leads is present
    	     * in BDE account or not.
    	    */  
    		@Test
    		public void h_LC_TS_28_assignmultipleLeads_BDE() throws Exception{ // ts28_tc002 (Hardik)
    			//expanding and clicking on assign lead link
    			help.expand();
    	    	
    			startup("assignlead","Assign Leads");
    			
    			new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
    			help.sleep(3);
    			
    			//Getting lead id,lead name and company for two leads
    			String name = assignmultipleLeadsname(0);
    			String name1 = assignmultipleLeadsname(1);
    			
    			//Assigning two leads to BDE by retrieving name from DB and checking that leads name in that module
    		  	List<String> sr1 = assignlead_otherSelf("multiple lead"); 
    	    	
    		  	help.login(sr1.get(0),sr1.get(1));
    			Reporter.log("<p>" + "logged in as: " +driver.findElement(By.className(bdm.getProperty("login_class"))).findElement(By.className(bdm.getProperty("username_class"))).getText());
    			help.expand();
    			
    			research_verify("BDE's",name);
    			help.sleep(3);
    			research_verify("BDE's",name1);		  
    		} 
    		
    		
               
    		/*Picking any username from the database and assigning multiple leads
    	     * to BDE and BDM modules. 
    	    */
            @Test
            public void i_LC_TS_28_assignmultipleleadsBDM_BDE(){ // ts28_tc003 (Hardik)
            	help.expand();
            	startup("assignlead","Assign Leads");
                
            	//Assigning multiple leads to self(BDM) and getting success message
                new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Web");
                help.sleep(5);
                
                new Select(driver.findElement(By.name("assignto"))).selectByVisibleText("Self");
                driver.findElement(By.id(bdm.getProperty("selectallbutton_id"))).click();
                help.sleep(3);
                
                driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
                help.sleep(2);
                Reporter.log("<p>" + "Assigning leads to Self");
                Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

                //Assigning multiple leads to BDE and getting success message
                new Select(driver.findElement(By.name(bdm.getProperty("service_name")))).selectByVisibleText("Cloud");
                help.sleep(3);
               
                new Select(driver.findElement(By.name("assignto"))).selectByVisibleText("sreekar jakkula");
                driver.findElement(By.id(bdm.getProperty("selectallbutton_id"))).click();
                help.sleep(3);
                driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
                help.sleep(2);
                Reporter.log("<p>" + "Assigning leads to BDM");
                Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
                
                //Picking any one BDM and BDE from DB and assigning multiple leads 
                assignleadsBDM_BDE("BDM");
                assignleadsBDM_BDE("BDE");

                Reporter.log("<p> ___________________________________________________________________"); 
            }   
            
            
    	    
            /*Checking research button is enabled for all leads in research
             * phase.
             */ 
    		@Test
    		public void j_LC_TS_29_researchbuttoncheck(){ // ts29_tc001 (Hardik)
    			//expanding and clicking on research on lead link
    			Reporter.log("<p>" + "*******************RESEARCH PHASE***************************");
    			help.expand();
      	  		startup("researchPhase","Lead Research");
      	  		//Checking whether research button is available for all leads in page
      	  		List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
      	  		if(leads_info.get(0).getText().equals("No data available in table"))
    				Reporter.log("<p>" + "Leads table in Lead Close is empty");
    			else {
      	  		int research=0;
      	  		for(int i=0; i<leads_info.size(); i++) 
      	  		{
      	  			if(leads_info.get(i).findElement(By.className(bdm.getProperty("researchbutton_class"))).isEnabled()) 
      	  			{
      	  				research++;
      	  			} 
      	  		}
      	  		if(research==leads_info.size())
      	  			Reporter.log("<p>" + "Research button is enabled for all leads."); 
    		
      	  		Reporter.log("<p> ___________________________________________________________________"); 
    			}
    		}
    		
    		
    		
    		/*Clicking on research button available in research phase and filling all
    		 * the details in the form.
    		*/
    		@Test(invocationCount=1)
    		public void k_LC_TS_29_researchformfill(){ // ts29_tc002 (Hardik)
    			help.expand();
      	  		
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
      	  		
      	  		//Selecting company fund status
      	  		new Select(driver.findElement(By.name(bdm.getProperty("conpanyfundstatus_name")))).selectByVisibleText("Listed");
      	  		Reporter.log("<p>" + "Clicking segregate button without filling company status and comment field");
      	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
      	  		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
      	  		
      	  		//Selecting company status
      	  		new Select(driver.findElement(By.name(bdm.getProperty("companystatus_name")))).selectByVisibleText("Sinking");
      	  		Reporter.log("<p>" + "Clicking segregate button without giving comment");
      	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
      	  		Reporter.log("<p>" + "Error-->" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

      	  		//Entering comment in research on lead form and segregating
      	  		driver.findElement(By.name(bdm.getProperty("researchcomment_name"))).sendKeys("research test");
      	  		driver.findElement(By.id(bdm.getProperty("segregatebutton_id"))).click();
      	  		help.sleep(3);
      	  		Reporter.log("<p>" + driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
      	  		driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
      	  		
      	  		//verifying that lead is not present in research phase
      	  		verifylead(idnamecmpny);
    		
      	  		//verifying that lead name is present in work phase
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
    		
      	  		//Getting the status and checking if it matches to Qualified or not
      	  		WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(4);
      	  		String status = abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(3).getText();
      	  		Reporter.log("<p>" + status);
      	  		
      	  		if(status.contains("Qualified"))
      	  			Reporter.log("<p>" + "Status is displayed correct");
      	  		else
      	  			Reporter.log("<p>" + "Status is incorrect");
      	  		Reporter.log("<p> ___________________________________________________________________"); 
    		}
    		
    		
    		
    		/*Clicking on work phase and checking whether track it and follow up buttons
    		 * are enabled for all leads.
    		*/
    		@Test
    		public void l_LC_TS_30_workPhasetrackit(){ // ts30_tc001 (Hardik)
    			Reporter.log("<p>" + "***********************WORK PHASE********************");
    			
    			//expanding and clicking on work phase link
    			help.expand();
    			startup("workPhase","Work on Lead");
    			
    			//Checking whether trackit and followup buttons are available for all leads in page
    			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			int trackit_followup=0;
    			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
    				Reporter.log("<p>" +"Leads table in work phase is empty");
    			}
    			else
    			{
    				for(int i=0; i<leads_info.size(); i++) 
    				{
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
    		
    		
    		
    		/*In work phase clicking on follow up and filling all details in
    		 * the form by selecting todays date.
    		*/
    		@Test(invocationCount=2)
    		public void  m_LC_TS_31_workphaseFollowupTodaysDate(){ // ts31_tc001 (Hardik)
    			help.expand();
    			
    			startup("workPhase","Work on Lead");
    	    	WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    			
    	    	//Checking if leads table is empty or not
    	    	if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
    				Reporter.log("<p>" + "Leads table in Workphase is empty");
    			}
    	    	else
    			{
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
    				driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("introductory mail test");
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
    		
    		
    		
			/*In work phase clicking on follow up and filling all details in
			 * the form by selecting future date.
			*/
			@Test(invocationCount=2)
			public void  n_LC_TS_30_workphaseFuturedate(){ // ts30_tc003 (Hardik)
				
				//Filling all the options by selecting tomorrows date and clicking on proceed button
    			help.expand();
    			startup("workPhase","Work on Lead"); 
    	  	  	String name = idnamecmpny(0);
    	  	  	
    	  	  	//Checking leads table is not empty
    			WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    			
    			if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No data available in table")){
    				Reporter.log("<p>" + "Leads table in Workphase is empty");
    			}
    			else
    			{
    				leads_info.findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(1).click();
    				help.sleep(2);
    				Reporter.log("<p>" + "child window title is: " +driver.findElement(By.cssSelector(bdm.getProperty("childwindowtitile_css"))).getText());
    				help.sleep(2);
    				
    				//Selecting followup type
    				new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Introductory Mail");
    				driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("introductory mail test");
    				driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).click();
    				
    				//Selecting future date and clicking on proceed
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
    				
    				//Checking that lead name is present in All followups
    				if(name1.equals(name))
    					Reporter.log("<p>" + "lead name is present in All followup");
    				else
    					Reporter.log("<p>" + "lead name not found in All followup");
    			
    			Reporter.log("<p> ___________________________________________________________________"); 
    			}
			}
    				
			
			
    		/*Confirming all leads with today's date in All followups is present
    		 * in today's follow up or not.	
    		*/
    		@Test
    		public void  o_LC_TS_32_confirmleadsofTodaysDate(){ // ts32_tc002 (Hardik)
    			
    			//Clicking todays followup link and getting all leads for todays date
    			help.expand();
    			startup("allfollowups","All Followups");
    			
    			//Getting all leads with todays date from all followups
    			List<String> leads = confirmleadstodaysAllfollowups();
    			
    			//Clicking todays Allfollowup link and getting all leads for todays date
    			startup("todayfollowups","Today Followups");		
    			
    			//Getting all leads with todays date from today's followups
    			List<String> leads1 = confirmleadstodaysAllfollowups();
    			
    			//Comparing leads in todays followup and all followups
    			if(leads.size()==leads1.size())
    			{
    				for(int j=0;j<leads.size();j++)
    				{
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
    			
    		
    		
    		/*Verifying track it and followup button is enabled for all leads present
    		 * under allfollowups phase.	
    		*/
    		@Test
    		public void  p_LC_TS_32_allfollowup(){ // ts32_tc003 (Hardik)
    			
    			//Checking trackit and followup button for all leads and printing work phase comments
    			Reporter.log("<p>" + "****************ALL FOLLOWUP*******************");
    			help.expand();
    			
    			startup("allfollowups","All Followups");
    			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
    			
    			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found")){
    				Reporter.log("<p>" + "Leads table in All Followups is empty");
    			}
    			else
    			{
    				int trackit_followup=0;
        			
        			//Checking track it and follow up button is enabled
        			for(int i=0; i<leads_info.size(); i++) 
        			{
        				if(leads_info.get(i).findElement(By.className(bdm.getProperty("trackitbutton_class"))).isEnabled() && leads_info.get(i).findElement(By.className(bdm.getProperty("followupbutton_class"))).isEnabled()) 
        				{
        					trackit_followup++; 
        				}
        			}
        			if(trackit_followup==leads_info.size())
        			Reporter.log("<p>" + "Trackit and Followup button is enabled for all leads.");
        			
        			//Printing work phase comments table
        			leads_info.get(0).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
        			help.sleep(1);
        			Reporter.log("<p>" + "----------------Work phase comments table------------------");
        			List<WebElement> workphase_table = driver.findElement(By.id(bdm.getProperty("table_ids"))).findElements(By.tagName(bdm.getProperty("table_tag")));
        			Reporter.log("<p>" + workphase_table.get(2).getText());
        		    Reporter.log("<p> ___________________________________________________________________"); 	
    			}
    		}
    		
    		
    		
    		/*Selecting Followup 4 from followup type dropdown and verifying the status of
    		 * that lead.
    		*/
    		@Test
    		public void q_LC_TS_32_allNextFollowup(){ // ts32_tc004 (Hardik)
    			help.expand();
    			startup("allfollowups","All Followups");	  	  	
    			
    			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
    			String name = idnamecmpny(0);
    			
    			WebElement leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    			
    			if(leads_info.findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found"))
    			{
    				Reporter.log("<p>" + "Leads table in All Followups is empty");
    			}
    			else
    			{
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
    	
    				//Selecting yesterdays date
    				driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).click();
    				help.sleep(1);
    				
    				Calendar cal = Calendar.getInstance();
    				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    				cal.add(Calendar.DATE, -1);
    				String date = dateFormat.format(cal.getTime());
    				Reporter.log("<p>" +"Yesterday's date was "+ date); 
    				driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(date);
    				
    				//Clicking proceed button
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
    		  
	  
		
    		/*Selecting propspect identity by selecting proposal from proposal type dropdown 
    		 * and filling all details in the form.	
    		*/
    		@Test
    		public void r_LC_TS_32_FollowupProspectIdentityProposal() throws Exception{ // ts32_tc007 (Hardik & Anuhya <merged>)
    			
    			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
    			Date date = new Date();
    			
    			//expanding and clicking todays followup link
    			help.expand();
    	  	  	startup("allfollowups","All Followups");
    			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
    			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found"))
    				Reporter.log("<p>" + "Leads table is empty");
    			else
    			{
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
    		    	  
    		         catch (Exception e)
    				 { 
    		             e.printStackTrace();
    		         }
    				
    				//Verifying options available under select service dropdown equals services in database
    		    	List<WebElement> mail_ids = driver.findElement(By.name("to")).findElements(By.tagName("option"));
    		    	List<String> service_list = new ArrayList<String>();
    		    	Reporter.log("<p>" + "***************Checking options under 'To' dropdown*********************");
    		    	
    		    	for(int i=0;i<mail_ids.size()-1;i++)
    		    	{
    		    	  service_list.add(mail_ids.get(i+1).getText());
    		    	  if(sr1.get(i).equals(service_list.get(i)))
    		    		  Reporter.log("<p>" + service_list.get(i) + "-->mail id of Architect for " +lead_service +" is displayed");
    		    	  else
    		    		  Reporter.log("<p>" + service_list.get(i) + "-->mail id of Architect for " +lead_service +" is not displayed");
    		    	}
    		    	
    				//Randomly selecting any lead	
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
    				help.sleep(9);
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
    				
    				 // Logging out of the BDM module 
    				 driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
    				 Reporter.log("<p>" + "Logged out of BDM Module");
    			  
    			}
    			Reporter.log("<p> ___________________________________________________________________"); 
    		
    		}
    		
		
    		
    		/*Selecting propspect identity by selecting Quote from proposal type dropdown 
    		 * and filling all details in the form.	
    		*/
    		@Test
    		public void s_LC_TS_32_FollowupProspectIdentityQuote() throws Exception{ // ts32_tc009 (Hardik & Anuhya <merged>)
    			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
    			Date date = new Date();
    			
    			//expanding and clicking todays followup link
    			help.expand();
    	  	  	startup("allfollowups","All Followups");
    			driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("introductory mail");
    			
    			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			if(leads_info.get(0).findElement(By.tagName(bdm.getProperty("servicename_tag"))).getText().equals("No matching records found"))
    				Reporter.log("<p>" + "Leads table is empty");
    			else
    			{
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
    	
    				//Closing window
    				driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
    	
    				driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
    				List<WebElement> leads_info1 = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    				
    				int r1 = help.random(leads_info1.size());
    				leads_info1.get(r1).findElements(By.tagName(bdm.getProperty("leadclick_tag"))).get(0).click();
    				help.sleep(1);
    				Reporter.log("<p>" + "----------------Work phase comments table------------------");
    				
    				List<WebElement> workphase_table = driver.findElement(By.id(bdm.getProperty("table_ids"))).findElements(By.tagName(bdm.getProperty("table_tag")));
    				Reporter.log("<p>" + workphase_table.get(2).getText());
    				
    				// Logging out of the BDM module 
    				driver.findElement(By.className(bdm.getProperty("logout_class"))).findElement(By.linkText(bdm.getProperty("logoutlink_linktext"))).click();
    				Reporter.log("<p>" + "Logged out of BDM Module");
    			}
    				Reporter.log("<p> ___________________________________________________________________"); 
    		}
		
		
    		
    		/*From Allfollowup phase searching for prospect identity,clicking on followup by
    		 * selecting proposal/Quote send from followup type and filling all details.
    		 */
    		@Test(invocationCount=1)
    		public void t_LC_TS_32_proposalQuoteSend(){ // ts32_tc011 (Hardik)
    			
    			SimpleDateFormat simple = new SimpleDateFormat("yyyy-M-dd");
    			Date date = new Date();
    	  	  	help.expand();
    	  	  	
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
    			else
    			{
    				driver.findElement(By.id(bdm.getProperty("allfollowupslink_id"))).click();
    				driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Prospect Identify");
    				help.sleep(2);
    				
    				//filling all details for proposal/quote form
    				WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    				String name = idnamecmpny(0);
    				abc.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(6).findElement(By.className(bdm.getProperty("followupbutton_class"))).click();
    				help.sleep(3);
    				
    				//Selecting followup type,comment,next followup date and proceed
    				new Select(driver.findElement(By.name(bdm.getProperty("followuptype_name")))).selectByVisibleText("Proposal/Quote Send");
    				help.sleep(1);
    				driver.findElement(By.name(bdm.getProperty("followupcomment_name"))).sendKeys("Proposal/Quote test comment");
    				driver.findElement(By.id(bdm.getProperty("nextfollowupdate_id"))).sendKeys(simple.format(date));
    				driver.findElement(By.id(bdm.getProperty("proceedbutton_id"))).click();
    				help.sleep(2);
    				Reporter.log("<p>" + name +" ::" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());
    				
    				//Closing window
    				driver.findElement(By.cssSelector(bdm.getProperty("windowclose_css"))).click();
    				driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys(name);
    				WebElement aaa = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElement(By.tagName(bdm.getProperty("leads_info_tagname")));
    				String status = aaa.findElements(By.tagName(bdm.getProperty("servicename_tag"))).get(5).getText();
    				
    				//Checking status
    				if(status.contains("Proposal/Quote Send"))
    					Reporter.log("<p>" + "Status is displayed correct");
    				else
    					Reporter.log("<p>" + "Status is incorrect");
    			}
    		
    			Reporter.log("<p> ___________________________________________________________________"); 
    		}
    		
    		
    		
    		/*From Allfollowup phase searching for prospect identity,clicking on followup by
    		 * selecting proposal/Quote accepted from followup type and filling all details.
    		*/
    		@Test(invocationCount=1)
    		public void u_LC_TS_32_proposalQuoteaccept(){ // ts32_tc012 (Hardik)
    		    help.expand();
    		    startup("allfollowups","All Followups");
    			
    		    driver.findElement(By.id(bdm.getProperty("searchbox_id"))).findElement(By.tagName(bdm.getProperty("searchbox_tag"))).sendKeys("Proposal/Quote Send");
    			WebElement abc = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElement(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			if(abc.getText().equals("No matching records found"))
    				Reporter.log("<p>" + "Leads table is empty");
    			else
    			{
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
    		
    		
    			
    		/*Verifying close buttons is enabled for all leads in lead close phase
    		 * 
    		*/
    		@Test
    		public void v_LC_TS_33_verifyleadclosebutton(){ // ts33_tc002 (Hardik)
    			help.expand();
    	  	  	startup("closedPhase","Closed Phase");
    			
    	  	  	//validating lead close button is enabled for all leads
    			List<WebElement> leads_info = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname")));
    			
    			if(leads_info.get(0).getText().equals("No data available in table"))
    				Reporter.log("<p>" + "Leads table in Lead Close is empty");
    			else
    			{
    				int close=0;
    				for(int i=0; i<leads_info.size(); i++) 
    				{
    					if(leads_info.get(i).findElement(By.className(bdm.getProperty("closebutton_class"))).isEnabled()) 
        					close++; 
    				}
    				if(close==leads_info.size())
    					Reporter.log("<p>" + "close button is enabled for all leads.");	
    				
    				//verifying lead status of every lead is same or not
    				verifyleadstatus("Prospect");
    			}
    			Reporter.log("<p> ___________________________________________________________________"); 
    		}
    		
    		
    		
    		/*From closed phase clicking on close button and selecting All customers from 
    		 * followup type and filling all details and verifying that lead in Management module.
    		*/
    		@Test
    		public void w_LC_TS_33_leadcloseCustomer() throws Exception{ // ts33_tc003 (Hardik)
    	  	  	help.expand();
    	  	  	startup("closedPhase","Closed Phase");
    			
    	  	  	WebElement w = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    			
    	  	  	if(w.getText().equals("No data available in table"))
    				Reporter.log("<p>" + "Leads table in Lead Close is empty");
    			
    	  	  	else
    	  	  	{
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
    		
    		
    		
    		/*From closed phase clicking on close button and selecting Lost competition from 
    		 * followup type and filling all details and verifying that lead in Management module.
    		*/
    		@Test
    		public void x_LC_TS_33_leadcloseLostCompetition() throws Exception{ // ts33_tc004 (Hardik)
    	  	  	
    			help.expand();
    	  	  	startup("closedPhase","Closed Phase");
    			
    	  	  	WebElement w = driver.findElement(By.tagName(bdm.getProperty("leads_info_tag"))).findElements(By.tagName(bdm.getProperty("leads_info_tagname"))).get(0);
    			
    	  	  	if(w.getText().equals("No data available in table"))
    				Reporter.log("<p>" + "Leads table in Lead Close is empty");
    			else
    			{
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
		
    	//------------------------STATIC METHODS---------------------------------------------	
    		
    		public void page_validate(String pagename){
    	    	//Verifying page is opened properly or not
    	    	if(driver.findElement(By.id(bdm.getProperty("pagevalidate_id"))).findElement(By.tagName(bdm.getProperty("pagevalidate_tag"))).getText().equals(pagename))
    	    		Reporter.log("<p>" + pagename +" Page is opened");
    	    	else
    	    		Assert.fail("Failure in page navigation");
    	    }
    			
    	    
    	    public void startup(String linkname,String page){
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
    	    	}
    	    	else
    	    	{
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
    	    
    	    public static void paginate() throws Exception {
    	    	//Page entries
    	    	help.pageEntries();
    	    	//Sorting
    	    	help.sorting();
    	    	// Search
    	    	help.searchtable();
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
    	    
    	    public void assignleadsBDM_BDE(String modulename){
    			try {  
    	            Class.forName("com.mysql.jdbc.Driver").newInstance();
    	            connection = DBConnection.getConnection();
    	            statement = connection.createStatement();
    	            resultSet = statement.executeQuery("select a.role_name, b.first_name, b.last_name, b.email_id, b.password from crm_role a, crm_user b where"
    	              + " 							a.role_id = b.role_id AND a.role_name = '" +modulename 
    	              + "'										AND delete_status = 'no' Limit 1;");      
    	            while (resultSet.next()) {	             
    	                String fnamelname = resultSet.getString("first_name") +" " +resultSet.getString("last_name");
    	                Reporter.log("Randomly picking any " +modulename + " from DB");
    	                new Select(driver.findElement(By.name("assignto"))).selectByVisibleText(fnamelname);
    	        		driver.findElement(By.id(bdm.getProperty("selectallbutton_id"))).click();
    	        		help.sleep(3);
    	    		    driver.findElement(By.className(bdm.getProperty("assignbutton_class"))).click();
    	    		    help.sleep(2);
    	    		    Reporter.log("<p>" + "Randomly picking " +modulename +" from Database and assigning");
    	        		Reporter.log("<p>" +driver.findElement(By.id(bdm.getProperty("resultmsg_id"))).findElement(By.tagName(bdm.getProperty("resultmsg_tag"))).getText());

    	              		}
    	    	  }
    	    	catch (Exception e){ 
    		           e.printStackTrace();
    		       }
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