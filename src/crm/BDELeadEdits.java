package src.crm;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import src.testUtils.Helper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nexiilabs.dbcon.DBConnection;


public class BDELeadEdits  extends Helper 
{
	public static Connection connection =null;
    public static Statement statement;
    public static ResultSet resultSet;
    
  @BeforeMethod
  public void beforemethod() throws Exception 
  {
	  help.browser();
	  help.maxbrowser();
	  driver.get(config.getProperty("url"));
	  help.sleep(1);
	  help.login(config.getProperty("bdename"),config.getProperty("bdepass"));
	  if(driver.findElement(By.className(bde.getProperty("login_class"))).getText().contains("Hi ! BDE")){
		  Reporter.log("<p>"+"logged successfully");
	  }else
		  Assert.fail("login not done");
	 
  }
  
  public void close(int y,String c,String data) throws Exception
  {
	 	driver.findElement(By.name(bde.getProperty("comment_id"))).sendKeys(sh4.getCell(0,6).getContents());
		driver.findElement(By.id(bde.getProperty("closeform_button"))).click();
		help.sleep(2);
		Reporter.log("<p>" +driver.findElement(By.className(bde.getProperty("success_message"))).getText());
		driver.findElement(By.className(bde.getProperty("editform_close")));
		help.sleep(2);
		 driver.findElement(By.className("user_logout")).click();
		help.sleep(3);
		//for getting mailid,password from database
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DBConnection.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select a.role_name, b.email_id, b.password "+ "from crm_role a, crm_user b where " 
			+ "a.role_id = b.role_id AND a.role_name = 'BDM' AND delete_status='no'Limit 1;"); 
			while (resultSet.next())
			{ 
				// String role = resultSet.getString("role_name");
				String email = resultSet.getString("email_id");
				String pass = resultSet.getString("password");
				help.login(email,pass);
			}
		}
		catch (Exception e){ 
            e.printStackTrace();
        }
		
		help.expand();
		if(driver.findElement(By.id(c)).isDisplayed())
		{
			  	  driver.findElement(By.id(c)).click();
			      ArrayList<Integer> li= new ArrayList<Integer>();
				  help.sleep(3);
				  //sending data into searchbox
				  WebElement search = driver.findElement(By.id(bde.getProperty("searchid"))).findElement(By.tagName(bde.getProperty("searchtag")));
				   if(search == null)
				    Assert.fail("The Search Text Box is not Present");
				   else
				    search.sendKeys(data);
				   //getting data from table
				  List<WebElement> tablerecords1= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
				  for(int b=0;b<tablerecords1.size();b++)
				  {
					  String s1=tablerecords1.get(b).findElements(By.tagName(bde.getProperty("tablecol_tagname"))).get(0).getText() ;
					 int z = Integer.parseInt(s1);
					 li.add(b, z);
				  }
				  if(li.contains(y))
				  Reporter.log("<p>" +"closed lead in BDE module is present in management module");
				  else
				  Assert.fail("closed lead in BDE module is not  present in management module");
		}
 }
  

	
@Test                                              // in manual test case LC_TS_48      closed phase
public void LC_TS_48_TC001() throws Exception 
{
	   help.expand();
	  help.sleep(5);
 if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
 {
	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  		help.sleep(3);
	  		help.sorting();
	  		help.pageEntries();
	  		searchtable();
 }else
     Assert.fail("closed phase  link not found successfully");
 driver.quit();
}
 
//@Test                                                     // in manual test case LC_TS_48     closed phase
public void LC_TS_48_TC002() throws Exception
{
	  help.expand();
	  help.sleep(1);
  if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
  {
	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  		help.sleep(1);
	  		List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		
	  		if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		if(tablerecords2.size()>1)
	  		{
	  			//Checking for the close button for each lead
	  			int check=0;
	  			do
	  			{
	  				if(check!=0)
	  				driver.findElement(By.id("example_next")).click();
	  				List<WebElement> tablerecords3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  				int count=0;
	  				for(int i=0; i<tablerecords3.size(); i++) 
	  				{
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
	  
driver.quit();
}


// @Test                                                         // in manual test case LC_TS_48     closed phase
public void LC_TS_48_TC003() throws Exception 
{
	 
	  help.expand();
	  help.sleep(1);
 if(driver.findElement(By.id(bde.getProperty("closedphase_link"))).isDisplayed())
 {
	  		driver.findElement(By.id(bde.getProperty("closedphase_link"))).click();
	  		help.sleep(3);
	  		List<WebElement> tablerecords= driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		String g=driver.findElement(By.className("dataTables_info")).getText();
	  		if(tablerecords.size()==0)
	  			Assert.fail("tablebody id not found");
	  		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  if(tablerecords2.size()>1)
	  {
		//Checking for the close button for each lead
			int count=0;
			for(int i=0; i<tablerecords.size(); i++) 
			{
				if(tablerecords.get(i).findElement(By.className(bde.getProperty("close_button"))).isEnabled()) 
					count++;
				else
				Assert.fail("close button not found");
			}
			if(count==tablerecords.size())
				Reporter.log("<p>" +"close button is enabled for all leads.");
			//random selection of close button
			int o=random(tablerecords.size());
			//getting  details of random lead
			List<WebElement>leaddata=tablerecords.get(o).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
			String s=leaddata.get(0).getText();
			 String data=  leaddata.get(0).getText() + " " + leaddata.get(1).getText() + " " +leaddata.get(2).getText();
			//converting string to integer
			int y=Integer.parseInt(s);
			Reporter.log("<p>" +"closed lead id:"+y);
			//clicking on close button
			tablerecords.get(o).findElement(By.className(bde.getProperty("close_button"))).click();
			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText()+":is opened");
			List<WebElement> leadstatus=driver.findElement(By.name(bde.getProperty("leadstatus_id"))).findElements(By.tagName(bde.getProperty("dropdown_options")));
			if(leadstatus.size()==0)
				Assert.fail("lead status id not found in close form");
			int a=random(leadstatus.size());
			if(a==0)
			a++;
			leadstatus.get(a).click();
			if(leadstatus.get(a).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item1"))){
				String c="customersList";
				driver.findElement(By.id(bde.getProperty("project_id"))).sendKeys(sh4.getCell(0,5).getContents());
				close(y,c,data);
			}
			if(leadstatus.get(a).getText().equalsIgnoreCase(bde.getProperty("leadstatus_item2"))){
				String c="lostCompetitionList";
				close(y,c,data);
			}
	  }else
	   Reporter.log("<p>"+"no data available");
	
	}else
  Assert.fail("closed phase  link not found successfully");
	  
	  	driver.quit();
 }
 
// @Test                                                   // in manual test case LC_TS_49 & 50 &51         leadsearch
 public void LC_TS_49_TC001() throws Exception 
 {
	  help.expand();
	  help.sleep(1);
    searchLead();
    driver.quit();
 }
 
 
 
// @Test                                                   // in manual test case LC_TS_52                  leadedits(trackit)
public void LC_TS_50_TC001() throws Exception
{
	   help.expand();
	  Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
 if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
 {
	  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(3);
	  		help.sorting();
	  		help.pageEntries();
	  		searchtable();
 }else
	   Assert.fail("lead edit link not found successfully");
 driver.quit();
}
 
//@Test                                                      // in manual test case LC_TS_52           leadedits(trackit)     
public void LC_TS_50_TC002() throws Exception
{
   help.expand();
  Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
  if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
  {
  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
  		help.sleep(3);
  		List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
  		if(trackelement.size()==0)
  			Assert.fail("table body tagname not found successfully in trackit");
  		List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
  		if( trackelement2.size()>1)
  		{
  			//Checking for the Track it button for each lead
  			int check=0;
  			do
  			{
  				if(check!=0)
  				driver.findElement(By.id("example_next")).click();
  				List<WebElement> trackelement3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
  				int trackit=0;
  				for(int i=0; i<trackelement3.size(); i++) {
				if(trackelement3.get(i).findElement(By.className(bde.getProperty("tracit_button"))).isEnabled()) 
				trackit++;
				else
			  	Assert.fail("trackit button is not found successfully");
  				}
				if(trackit==trackelement3.size())
  				Reporter.log("<p>"+"Trackit button is present for all leads."); 
				check++;
  			}while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
  			
  		}else
	  	 Reporter.log("<p>"+"no data available");
	}else
     Assert.fail("lead edit link not found successfully");
  driver.quit();
}


 
//  @Test                                                          // in manual test case LC_TS_52                leadedits(trackit)
 public void LC_TS_50_TC003() throws Exception
 {
	   help.expand();
	  Reporter.log("<p>"+"==============\n"+"TRACKIT"+"===================");;
  if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
  {
	  		driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(3);
	  		List<WebElement> trackelement=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
	  		if(trackelement.size()==0)
	  			Assert.fail("table body tagname not found successfully in trackit");
	  		List<WebElement> trackelement2=trackelement.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	 if( trackelement2.size()>1)
	 {
		//Checking for the Track it button for each lead
			int trackit=0;
			for(int i=0; i<trackelement.size(); i++) {
				if(trackelement.get(i).findElement(By.className(bde.getProperty("tracit_button"))).isEnabled()) 
				trackit++;
				else
			  	Assert.fail("trackit button is not found successfully");
			}
			if(trackit==trackelement.size())
				Reporter.log("<p>"+"Trackit button is present for all leads."); 
		   //random selection of track it button
			int p=random(trackelement.size());
			//getting details of random lead before clicking track it button
	  		List<WebElement> ls =trackelement.get(p).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  		if(ls.size()==0)
	  			Assert.fail("tablecol tagname not found successfully in trackit");
	  		ArrayList<String> ar = new ArrayList<String>();
	  		for (int i=0;i<ls.size();i++){
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
	  		for (int i=0;i<ls2.size();i++){
	  			List<WebElement> s1= ls2.get(i).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  			for(int j=0;j<s1.size();j++){
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
	  		if(ar1.get(0).contains(ar.get(0))){
	  			if(ar1.get(1).contains(ar.get(1))){
	  				if(ar1.get(18).contains(ar.get(5))){	 	 
	  					Reporter.log("<p>" +"Data is matching exactly ");
	  				}
	  			}
	  		}else
	  		 Reporter.log("<p>" +"Data doesnt match ");
	 }else
	  Reporter.log("<p>" +"no data available ");
  }else
   Assert.fail("lead edit link not found successfully");
	  
	  	driver.quit();
}

  
 // @Test                                                              // in manual test case LC_TS_52               leadedits(EDIT)
  public void LC_TS_51_TC001() throws Exception
  {
	  help.expand();
	  Reporter.log("<p>" +"=====\n"+"EDIT::"+"======");
	if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	{
			driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(2);
	  		List <WebElement> leads = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
		  	if(leads.size()==0)
	  			Assert.fail("table body tagname not found successfully in edit");
		  	help.sleep(2);
		  	List <WebElement> leads2=leads.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  if(leads2.size()>1)
	  {
		  	// Checking for the buttons for each lead
		  	int check=0;
		  	do
		  	{
		  		if(check!=0)
		  			driver.findElement(By.id("example_next")).click();
		  		List<WebElement> leads3=driver.findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
		  		int edit=0;
		  		for(int i=0; i<leads3.size(); i++)
		  		{
		  			if(leads3.get(i).findElement(By.className(bde.getProperty("edit_button"))).isEnabled()) 
		  				edit++;
		  			else
		  				Assert.fail("edit button is not found successfully");
		  		}
		  		check++;
				if(edit==leads3.size())
					Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
		  	}while(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled"));
	  }else
		   Reporter.log("<p>"+"no data available");
	}else
		   Assert.fail("lead edit link not found successfully");
	driver.quit();
  }
  
//  @Test                                                               // in manual test case LC_TS_52                leadedits(EDIT)
  public void LC_TS_51_TC002() throws Exception
  {
	  help.expand();
	  Reporter.log("<p>" +"=====\n"+"EDIT::"+"======");
	if(driver.findElement(By.id(bde.getProperty("leadedit_link"))).isDisplayed())
	{
			driver.findElement(By.id(bde.getProperty("leadedit_link"))).click();
	  		help.sleep(2);
	  		List <WebElement> leads = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
		  	if(leads.size()==0)
	  			Assert.fail("table body tagname not found successfully in edit");
		  	help.sleep(2);
		  	List <WebElement> leads2=leads.get(0).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
	  if(leads2.size()>1)
	  {
			// Checking for the buttons for each lead
		  	int edit=0;
		  	for(int i=0; i<leads.size(); i++) {
				if(leads.get(i).findElement(By.className(bde.getProperty("edit_button"))).isEnabled()) 
				edit++;
				else
				 Assert.fail("edit button is not found successfully");
			}
		  	if(edit==leads.size())
			Reporter.log("<p>" +"Edit buttons are enabled for all leads.");
		  	Reporter.log("<p>" +"No. of leads in the Lead Edit Table:" + leads.size());
		  	int columns=sh4.getColumns();
			int rows=sh4.getRows();
			String data;
			help.sleep(2);
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//random selection of edit button
			int j=random(leads.size());
			//getting details of random lead before clicking edit button
			List<WebElement> ls =leads.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
			if(ls.size()==0)
				Assert.fail("tablecol tagname not found successfully in edit");
	  		ArrayList<String> ar = new ArrayList<String>();
	  		for (int i=0;i<ls.size();i++){
	  			String s1= ls.get(i).getText();
	  			ar.add(s1);  
	  		}
	  		Reporter.log("<p>" +"lead before clicking on edit button is: " + ar);
			leads.get(j).findElement(By.className(bde.getProperty("edit_button"))).click();
			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("dialog"))).findElement(By.tagName(bde.getProperty("dialog_tagname"))).getText());
			for(int row=2;row<=2;row++){
					int col=0;
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
			List<WebElement> updatedropdowns=driver.findElement(By.className(bde.getProperty("updatedropdown_class"))).findElements(By.tagName(bde.getProperty("updatedropdown_tag")));
			if(updatedropdowns.size()==0)
	  			Assert.fail(" updatedropdown class name not found successfully in edit");
			for(int a=0;a<updatedropdowns.size();a++){
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
			Reporter.log("<p>" +driver.findElement(By.id(bde.getProperty("message_id"))).findElement(By.tagName(bde.getProperty("message_tagname"))).getText());
			driver.findElement(By.className(bde.getProperty("editform_close"))).click();
			help.sleep(5);
			//getting details of lead after  editing the lead
			List <WebElement> leads1 = driver.findElement(By.id(bde.getProperty("table_id"))).findElement(By.tagName(bde.getProperty("table_body"))).findElements(By.tagName(bde.getProperty("tablerow_tagname")));
			if(leads1.size()==0)
				Assert.fail("table id not found in edit");
			help.sleep(5);
			
	  		List<WebElement> ls1 =leads1.get(j).findElements(By.tagName(bde.getProperty("tablecol_tagname")));
			
	  		ArrayList<String> ar1 = new ArrayList<String>();
	  		for (int i=0;i<ls1.size();i++){
	  			String s1= ls1.get(i).getText();
	  			ar1.add(s1);  
	  		}
	  		Reporter.log("<p>" +"lead details after  editing   : " + ar1);
	  		//validation
	  		if(ar1.equals(ar)){
	  			Reporter.log("<p>" +"no modifications done");
	  		}
	  		else{
	  			for(int k=0;k<ar1.size();k++){
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
	  driver.quit();
  }
  
 // @Test                                                               // in manual test case LC_TS_53 & 54                myaccount
  public void LC_TS_52_TC002() throws Exception
  {
	  help.expand();
	  String email1=config.getProperty("bdename");
	  help.sleep(2);
	  changePassword(email1);
	  driver.quit();
  }
  
  
  @AfterMethod
  public void afterMethod() 
  {
	  
  }

}

