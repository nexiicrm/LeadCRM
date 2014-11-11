package src.crm;

import java.util.ArrayList;
import java.util.List;
import src.testUtils.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import com.nexiilabs.dbcon.DBConnection;


public class BDELeadEdits  extends Helper 
{
  @BeforeMethod
  public void beforemethod() throws Exception 
  {
	  //to open the browser
	  help.browser();
	  //to maximise the browser
	  help.maxbrowser();
	  //for getting leadcrm url page
	  driver.get(config.getProperty("url"));
	  help.sleep(1);
	  //login into bde module
	  help.login(config.getProperty("bdename"),config.getProperty("bdepass"));
	  if(driver.findElement(By.className(bde.getProperty("login_class"))).getText().contains("Hi ! BDE")){
		  Reporter.log("<p>"+"logged successfully");
	  }else
		  Assert.fail("login not done");
	 
  }
  
  public void close(int y,String c,String data) throws Exception
  {
	  
	 	//for getting mailid,password from database
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DBConnection.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select a.role_name, b.email_id, b.password "+ "from crm_role a, crm_user b where " 
			+ "a.role_id = b.role_id AND a.role_name = 'Management' AND delete_status='no'Limit 1;"); 
			while (resultSet.next())
			{ 
				String email = resultSet.getString("email_id");
				String pass = resultSet.getString("password");
				//login into management module
				help.login(email,pass);
			}
		}
		catch (Exception e){ 
            e.printStackTrace();
        }
		
		help.expand();
		help.sleep(5);
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
  
  

@Test                                                     // in manual test case LC_TS_48     closed phase
public void LC_TS_48_TC001() throws Exception
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
	  				if(check!=0)
	  				//clicking on pagination next button	
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
public void LC_TS_48_TC002() throws Exception 
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
	  		String g=driver.findElement(By.className("dataTables_info")).getText();
	  		//validation 
	  		if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
	  		//getting no of columns of 1st lead
	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		if(tablerecords2.size()>1)
	  		{
	  			//getting show dropdown options
	  			List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
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
	  			   ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
	  			   //closing the child form
	  			   driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  				help.sleep(2);
	  				//clicking on logout button
	  				driver.findElement(By.className("user_logout")).click();
	  				//calling close method
	  				close(y,c,data);
	  			}
	   }else
	   Reporter.log("<p>"+"no data available");
	
	}else
	 Assert.fail("closed phase  link not found successfully");
}
@Test                                                         // in manual test case LC_TS_48     closed phase
public void LC_TS_48_TC003() throws Exception 
{
	 ///////////////////// closing the lead by selecting lost competition ////////////////////////
	  	help.expand();
	  	help.sleep(1);
	  	if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
	  	{
	        //clicking on closed phase link
	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  		help.sleep(3);
	  		//getting table records
	  		List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		String g=driver.findElement(By.className("dataTables_info")).getText();
	  		//validation
	  		if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
	  		//getting no of columns of 1st lead
	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		if(tablerecords2.size()>1)
	  		{
	  			//getting show dropdown entries
	  			List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
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
		  			   ac.moveToElement(driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close"))).build().perform();
		  			   //closing the child form
		  			   driver.findElement(By.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close")).click();
	  				help.sleep(2);
	  				//clicking on logout
	  			    driver.findElement(By.className("user_logout")).click();
	  			    //calling close method
	  				close(y,c,data);
	  			}
	  }else
	   Reporter.log("<p>"+"no data available");
	
	}else
	 Assert.fail("closed phase  link not found successfully");
}

 
@Test                                                   // in manual test case LC_TS_49 & 50 &51         leadsearch
public void LC_TS_49_TC001() throws Exception 
 {
	  help.expand();
	  help.sleep(1);
	  //calling search lead from helper
	  searchLead();
 }
 
 
 

 
@Test                                                      // in manual test case LC_TS_52           leadedits     
public void LC_TS_50_TC001() throws Exception
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
  			List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
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
public void LC_TS_50_TC002() throws Exception
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
	  			List<WebElement> show=driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
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
public void LC_TS_51_TC001() throws Exception
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
		  		Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText());
		  		for(int row=2;row<=2;row++)
		  		{
					int col=0;
					help.sleep(1);
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
public void LC_TS_52_TC001() throws Exception
  {
	  help.expand();
	  String email1=config.getProperty("bdename");
	  help.sleep(2);
	  //calling change password from helper
	  changePassword(email1);
	 
  }
  
  
  @AfterMethod
  public void afterMethod() 
  {
	  driver.quit();
  }

}

