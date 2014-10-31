package crm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import testUtils.Helper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BDELeadEdits  extends Helper 
{
	
  @BeforeMethod
  public void beforemethod() throws Exception 
  {
	  help.browser();
	  help.maxbrowser();
	  driver.get(config.getProperty("url"));
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  //help.login("sreekar.jakkula@nexiilabs.com", "password");
	  help.login("srinivasa.sanchana@nexiilabs.com", "password");
	 /* if(driver.findElement(By.className("user_name")).getText().equalsIgnoreCase("Hi ! BDE sreekar")){
		  System.out.println("logged into BDE done successfully");
	  }
	  else{
		  System.out.println(driver.findElement(By.className("login button")).findElements(By.tagName("label")).get(0).getText());
		  Assert.fail("login not done");
	  }*/
  }
  
  public void Showdropdown()  
  {
	  	System.out.println("=================================================\n"+"SHOW DROPDOWN:");
		List<WebElement>showlength=driver.findElement(By.className("dataTables_length")).findElements(By.tagName("option"));
		System.out.println("No of elements in show dropdown box:"+showlength.size());
		for(int k=0;k<showlength.size();k++)
		{
			showlength.get(k).click();
			help.sleep(3);
			System.out.println("======================================================");
			//validation
			if(showlength.get(k).isSelected())
				System.out.println("selected item in show dropdown is:"+showlength.get(k).getAttribute("value"));
		
			String a=showlength.get(k).getAttribute("value");
			int b=Integer.parseInt(a);
			List<WebElement> tablerecords= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			System.out.println("no of records displaying is:"+ tablerecords.size());
			int c=tablerecords.size();
			System.out.println(driver.findElement(By.className("dataTables_info")).getText());
		    //validation
			if(b==c)
			{
				System.out.println("no of records displayed according to show dropdown selected item successfully");
			}else if(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled")){
				Assert.fail("show drop down failed");
			}else
				System.out.println("less no  of records are present in table than show dropdown selected item ");
			
			
		}
	 }
  
  public void Searchbox()
  {
		System.out.println("=================================================\n"+"SEARCH BOX");
		driver.findElement(By.className("dataTables_filter")).findElement(By.tagName("input")).sendKeys(or.getProperty("search"));
		String s=or.getProperty("search");
		if(driver.findElement(By.className("dataTables_info")).getText().contains("Showing 0 to 0 of 0"))
		{
		System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
		}
		else
		{
			List<WebElement> tablerecords= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			int o=random(tablerecords.size());
			//validation
			if(tablerecords.get(o).getText().contains(s))
			{
				System.out.println(tablerecords.get(o).getText());	
				System.out.println(" displayed record contains  search item:"+s+": successfully");
			}
		}
		System.out.println(driver.findElement(By.className("dataTables_info")).getText());
	}
  
  
  public void ascending(int l)
  {
	  List<String> ids= new ArrayList<String>();
	  ArrayList<Integer> li= new ArrayList<Integer>();
	  List<WebElement> tablerecords= driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  for(int a=0;a<tablerecords.size();a++)
	  {
		  if(l==0)
		  {
			  String s=tablerecords.get(a).findElements(By.tagName("td")).get(l).getText() ;
				 int z = Integer.parseInt(s);
				 li.add(a, z);
		  }
		  else
		  {
			  ids.add(a, tablerecords.get(a).findElements(By.tagName("td")).get(l).getText());
		  }
	  }
	  help.sleep(5);
	  //validation
	  if(l==0)
	  {
			 for(int i=0;i<li.size();i++)
			 {
				 for(int j=i;j<li.size();j++)
				 {
					 int val=(li.get(i)).compareTo(li.get(j));
					 if(val<=0)
					continue;
					 else
					Assert.fail("failed");
				 }
			 }
	  }
	  else
	  {
		  for(int i=0;i<ids.size();i++)
		  {
			  for(int j=i;j<ids.size();j++)
			  {
				  int val=(ids.get(i)).compareToIgnoreCase(ids.get(j));
				 if(val<=0)
				continue;
				 else
				Assert.fail("failed");
			  }
		  }
	  }
  }
  
  
  public void descending(int l)
  {
	  List<String> ids= new ArrayList<String>();
	  ArrayList<Integer> li= new ArrayList<Integer>();
	  List<WebElement> tablerecords= driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  for(int a=0;a<tablerecords.size();a++)
	  {
		  if(l==0)
		  {
			  String s=tablerecords.get(a).findElements(By.tagName("td")).get(l).getText() ;
			 int z = Integer.parseInt(s);
			 li.add(a, z);
		  }
		  else
		  {
			  ids.add(a, tablerecords.get(a).findElements(By.tagName("td")).get(l).getText());
		  }
	  }
	  help.sleep(5);
	  //validation
	 if(l==0)
	 {
		 for(int i=0;i<li.size();i++)
		 {
			 for(int j=i;j<li.size();j++)
			 {
				 int val=(li.get(i)).compareTo(li.get(j));
				 if(val>=0)
				continue;
				 else
				Assert.fail("failed");
			 }
		 }
	 }
	 else
	 {
		 for(int i=0;i<ids.size();i++)
		 {
			 for(int j=i;j<ids.size();j++)
			 {
				 int val=(ids.get(i)).compareToIgnoreCase(ids.get(j));
				 if(val>=0)
				continue;
				 else
				Assert.fail("failed");
			 }
		}
	 }
  }
  
  
  
  public void Sorting()
  {
	  	List<WebElement> tablecolumns= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
		System.out.println("totalcolumns having sorting options="+(tablecolumns.size()));
		for(int l=0;l<(tablecolumns.size()-1);l++)
		{
			tablecolumns.get(l).click();
			help.sleep(5);
			if(tablecolumns.get(l).getAttribute("class").contains("sorting_asc"))
			{
				System.out.println("sorting ascending: is performed on column: "+tablecolumns.get(l).getText());
				ascending(l);
			}
			else
			{
				System.out.println("sorting descending: is performed on column: "+tablecolumns.get(l).getText());
				descending(l);
			}
			tablecolumns.get(l).click();
			help.sleep(5);
			if(tablecolumns.get(l).getAttribute("class").contains("sorting_asc"))
			{
				System.out.println("sorting ascending: is performed on column: "+tablecolumns.get(l).getText());
				ascending(l);
			}
			else
			{
				System.out.println("sorting descending: is performed on column: "+tablecolumns.get(l).getText());
				descending(l);
			}
		}
  }
  
  public void next()
  {
	  for(int z=0;z<10;z++)
		{
			System.out.println(driver.findElement(By.className("dataTables_info")).getText());
			if(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled")){
			driver.findElement(By.id("example_next")).click();
			}else{
			System.out.println(driver.findElement(By.id("example_next")).getAttribute("class"));
			break;
			}
		}
  }
  public void previous()
  {
		for(int y=0;y<10;y++)
		{
			System.out.println(driver.findElement(By.className("dataTables_info")).getText());
			if(driver.findElement(By.id("example_previous")).getAttribute("class").contains("enabled")){
			driver.findElement(By.id("example_previous")).click();
			}else{
			System.out.println(driver.findElement(By.id("example_previous")).getAttribute("class"));
			break;
			}
		}
  }
 @Test
 public void asample()
 {
	  help.expand();
	  if(driver.findElement(By.id("allfollowups")).isDisplayed())
	  {
	  		driver.findElement(By.id("allfollowups")).click();
	  		help.sleep(3);
	  		//driver.findElement(By.name("service")).findElements(By.tagName("option")).get(1).click();
	  		//Showdropdown();
	  		Sorting();
	  		//next();
	  		//previous();
	  		//Searchbox();
	  }
	  collapse();
	  driver.close();
 }
 
 public void close(int y,String c) throws Exception
 {
	 	driver.findElement(By.name(or.getProperty("comment_id"))).sendKeys(or.getProperty("comment"));
		driver.findElement(By.id(or.getProperty("closeform_button"))).click();
		//driver.findElement(By.xpath("//div[5]/div/button")).click();
		System.out.println(driver.findElement(By.className(or.getProperty("success_message"))).getText());
		driver.findElement(By.className(or.getProperty("editform_close")));
		help.sleep(3);
		collapse();
		driver.close();
		help.browser();
		help.maxbrowser();
		driver.get(config.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		help.login(sh5.getCell(0,0).getContents(), sh5.getCell(1,0).getContents());
		help.expand();
		if(driver.findElement(By.id(c)).isDisplayed())
		{
			  driver.findElement(By.id(c)).click();
			  ArrayList<Integer> li= new ArrayList<Integer>();
				  help.sleep(3);
				  List<WebElement> tablerecords1= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				  System.out.println(tablerecords1.size());
				  for(int b=0;b<tablerecords1.size();b++)
				  {
					  String s1=tablerecords1.get(b).findElements(By.tagName("td")).get(0).getText() ;
					 int z = Integer.parseInt(s1);
					 li.add(b, z);
				  }
				  System.out.println(li);
				  if(li.contains(y))
				  {
					  System.out.println("pass");
				  }else
					  Assert.fail("not done");
		}
 }
 
// @Test
 public void Trackit1()
 {
	  
	  help.expand();
	  System.out.println("=================================================\n"+"TRACKIT");
	  if(driver.findElement(By.id(or.getProperty("leadedit_link"))).isDisplayed())
	  {
	  		driver.findElement(By.id(or.getProperty("leadedit_link"))).click();
	  		help.sleep(3);
	  		List<WebElement> trackelement=driver.findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	  		//Checking for the Track it button for each lead
	  		int trackit=0;
			for(int i=0; i<trackelement.size(); i++) 
			{
					if(trackelement.get(i).findElement(By.className(or.getProperty("tracit_button"))).isEnabled()) 
					trackit++;
			}
			if(trackit==trackelement.size())
			System.out.println("Trackit button is present for all leads.");
			//random selection of lead
			int p=random(trackelement.size());
	  		List<WebElement> ls =trackelement.get(p).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	  		ArrayList<String> ar = new ArrayList<String>();
	  		for (int i=0;i<ls.size();i++)
	  		{
	  			String s1= ls.get(i).getText();
	  			ar.add(s1);  
	  		}
	  		System.out.println("Array before clicking on trackit button is: " + ar);
	  		//clicking on trackit button
	  		trackelement.get(p).findElement(By.className(or.getProperty("tracit_button"))).click(); 
	  		help.sleep(2);
	  		List<WebElement> ls2 =driver.findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	  		ArrayList<String> ar1 = new ArrayList<String>();
	  		for (int i=0;i<ls2.size();i++)
	  		{
	  			List<WebElement> s1= ls2.get(i).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	  			for(int j=0;j<s1.size();j++){
	  				// System.out.println(s1.get(j).getText());
	  				String s2 = s1.get(j).getText();
	  				ar1.add(s2); 
	  			} 
	  		}	  
	  		// System.out.println(ar1);
	  		System.out.println("array size after trackit button is clicked:" + ar1.size());
	  		System.out.println("array element after trackit button is clicked: " + ar1.get(0));
	  		System.out.println("array element after trackit button is clicked: " + ar1.get(1));
	  		System.out.println("array element after trackit button is clicked: " + ar1.get(18));
	  		System.out.println("array element after trackit button is clicked: " + ar1.get(19));
	  		if(ar1.get(0).contains(ar.get(0)))
	  		{
	  			if(ar1.get(1).contains(ar.get(1)))
	  			{
	  				if(ar1.get(18).contains(ar.get(5)))
	  				{	 	 
	  					System.out.println("Data is matching exactly ");
	  				}
	  			}
	  		}else
	  			System.out.println("Data doesnt match ");
	  	}
	  	collapse();
	  	driver.close();
}

  
 // @Test
  public void Edit()
  {
	  help.expand();
	  System.out.println("=================================================\n"+"EDIT::");
	  if(driver.findElement(By.id(or.getProperty("leadedit_link"))).isDisplayed())
	  {
	  		driver.findElement(By.id(or.getProperty("leadedit_link"))).click();
			// Verifying no.of leads in the page
	  		help.sleep(5);
		  	List <WebElement> leads = driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
		  	help.sleep(5);
		  	System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
		  	// Checking for the buttons for each lead
		  	int edit=0;
		  	for(int i=0; i<leads.size(); i++) 
		  	{
				if(leads.get(i).findElement(By.className(or.getProperty("edit_button"))).isEnabled()) 
				{
			  		edit++;
				}	  
		  	}
		  	if(edit==leads.size())
			System.out.println("Edit buttons are enabled for all leads.");
		  	int columns=sh4.getColumns();
			int rows=sh4.getRows();
			String data;
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			int j=random(leads.size());
			List<WebElement> ls =leads.get(j).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	  		ArrayList<String> ar = new ArrayList<String>();
	  		for (int i=0;i<ls.size();i++)
	  		{
	  			String s1= ls.get(i).getText();
	  			ar.add(s1);  
	  		}
	  		System.out.println("lead before clicking on edit button is: " + ar);
			leads.get(j).findElement(By.className(or.getProperty("edit_button"))).click();
			System.out.println(driver.findElement(By.id(or.getProperty("dialog"))).findElement(By.tagName(or.getProperty("dialog_tagname"))).getText());
			for(int row=3;row<=3;row++)
			{
					int col=0;
					driver.findElement(By.id(or.getProperty("first_name"))).clear();
					driver.findElement(By.id(or.getProperty("first_name"))).sendKeys(sh4.getCell(col, row).getContents());
					driver.findElement(By.id(or.getProperty("last_name"))).clear();
					driver.findElement(By.id(or.getProperty("last_name"))).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id(or.getProperty("mobile_no"))).clear();
					driver.findElement(By.id(or.getProperty("mobile_no"))).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id(or.getProperty("board_no"))).clear();
					driver.findElement(By.id(or.getProperty("board_no"))).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id(or.getProperty("desk_no"))).clear();
					driver.findElement(By.id(or.getProperty("desk_no"))).sendKeys(sh4.getCell(++col, row).getContents());
			}
			List<WebElement> updatedropdowns=driver.findElement(By.className(or.getProperty("updatedropdown_class"))).findElements(By.tagName(or.getProperty("updatedropdown_tag")));
			for(int a=0;a<updatedropdowns.size();a++)
			{
					List<WebElement>container1=updatedropdowns.get(a).findElements(By.tagName(or.getProperty("dropdown_options")));
					int w=random(container1.size());
					container1.get(w).click();
					if(container1.get(w).isSelected())
					{
						System.out.println(w+":"+container1.get(w).getText()+" is selected ");
					}
			}
			driver.findElement(By.id(or.getProperty("editcommit_button"))).click();
			System.out.println(driver.findElement(By.id(or.getProperty("message_id"))).findElement(By.tagName(or.getProperty("message_tagname"))).getText());
			driver.findElement(By.className(or.getProperty("editform_close"))).click();
			//driver.findElement(By.cssSelector("span.ui-button-icon-primary.ui-icon.ui-icon-closethick")).click();
			help.sleep(5);
			List <WebElement> leads1 = driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	  		List<WebElement> ls1 =leads1.get(j).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	  		ArrayList<String> ar1 = new ArrayList<String>();
	  		for (int i=0;i<ls1.size();i++)
	  		{
	  			String s1= ls1.get(i).getText();
	  			ar1.add(s1);  
	  		}
	  		System.out.println("lead after clicking on edit button is: " + ar1);
	  		if(ar1.equals(ar))
	  		{
	  			System.out.println("no modifications done");
	  		}
	  		else
	  		{
	  			for(int k=0;k<ar1.size();k++)
	  			{
	  				if(ar.get(k).equals(ar1.get(k)))
	  				continue;
	  				else
	  				System.out.println(ar.get(k)+":changed to:"+ar1.get(k));
	  			}
	  		}
		}
	  	collapse();
	  	driver.close();
  }
  

 
 // @Test
  public void Closedphase() throws Exception 
  {
	  help.expand();
	  help.sleep(5);
	  if(driver.findElement(By.id(or.getProperty("closedphase_link"))).isDisplayed())
	  {
	  		driver.findElement(By.id(or.getProperty("closedphase_link"))).click();
	  		help.sleep(3);
	  		List<WebElement> tablerecords= driver.findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	  		//Checking for the close button for each lead
			int count=0;
			for(int i=0; i<tablerecords.size(); i++) 
			{
				if(tablerecords.get(i).findElement(By.className(or.getProperty("close_button"))).isEnabled()) 
				count++;
			}
			if(count==tablerecords.size())
			System.out.println("close button is enabled for all leads.");
			int o=random(tablerecords.size());
			String s=tablerecords.get(o).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(0).getText();
			int y=Integer.parseInt(s);
			System.out.println("closed lead id:"+y);
			tablerecords.get(o).findElement(By.className(or.getProperty("close_button"))).click();
			System.out.println(driver.findElement(By.id(or.getProperty("dialog"))).findElement(By.tagName(or.getProperty("dialog_tagname"))).getText()+":is opened");
			List<WebElement> leadstatus=driver.findElement(By.name(or.getProperty("leadstatus_id"))).findElements(By.tagName(or.getProperty("dropdown_options")));
			System.out.println("leadstatus dropdown size:"+leadstatus.size());
			int a=random(leadstatus.size());
			System.out.println(a);
			if(a==0)
			a++;
			leadstatus.get(a).click();
			if(leadstatus.get(a).getText().equalsIgnoreCase(or.getProperty("leadstatus_item1")))
			{
				String c="customersList";
				driver.findElement(By.id(or.getProperty("project_id"))).sendKeys(or.getProperty("projectdeatailes"));
				close(y,c);
				//System.out.println("done");
						  
			}
			if(leadstatus.get(a).getText().equalsIgnoreCase(or.getProperty("leadstatus_item2")))
			{
				String c="lostCompetitionList";
				close(y,c);
				//System.out.println("done");
			}
	  	}
	  help.sleep(3);
	  	collapse();
	  	driver.close();
  }
  
  @Test
  public void leadsearch() throws Exception 
  {
	  help.expand();
	  if(driver.findElement(By.id("serachLeads123")).isDisplayed())
	  {
	  		driver.findElement(By.id("serachLeads123")).click();
			// Switching to Child Window
			 String parentWindow = driver.getWindowHandle();
			 for(String childWindow : driver.getWindowHandles()) 
			 {
				 driver.switchTo().window(childWindow);
			 }
			 help.sleep(3);
			System.out.println(driver.getTitle());
			System.out.println(" navigated to:"+driver.findElement(By.tagName("h1")).getText()+":successfully");
			List<WebElement> reqfields = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("input"));
			System.out.println("Total No of required fields:"+reqfields.size());
			for(int c=0;c<reqfields.size();c++)
			System.out.println((c+1)+":"+ driver.findElement(By.id("fields_to_get")).findElements(By.tagName("label")).get(c).getText());
			//selecting required fields
			int a=random(reqfields.size());
			reqfields.get(a).click();
			System.out.println(driver.findElement(By.id("fields_to_get")).findElements(By.tagName("label")).get(a).getText()+":clicked");
			driver.findElement(By.id("ui-accordion-accordion-header-1")).click();
			List<WebElement> filteroptions=driver.findElement(By.id("ui-accordion-accordion-panel-1")).findElements(By.tagName("fieldset"));
			System.out.println("Total no of filter options:"+filteroptions.size());
			//print filter options
			for(int d=0;d<filteroptions.size();d++)
			System.out.println("Filter options:"+filteroptions.get(d).findElement(By.tagName("legend")).getText());
			//selecting randomly one  from filter options
			int e=random(filteroptions.size());
			System.out.println(filteroptions.get(e).findElement(By.tagName("legend")).getText()+":selected");
			List<WebElement> options=filteroptions.get(e).findElements(By.tagName("input"));
			System.out.println("No of options:"+options.size());
			//selecting randomly one from options
			int f=random(options.size());
			options.get(f).click();
			//validation
			if(options.get(f).isSelected())
			System.out.println(filteroptions.get(e).findElements(By.tagName("label")).get(f).getText()+":is selected");
			else
			System.out.println("filter options not selected properly");
			driver.findElement(By.id("registerbutton")).click();
			help.sleep(5);
			// Printing the Table displayed with required fields
			 System.out.println(driver.findElement(By.id("example")).findElement(By.tagName("tbody")).getText());
			 Sorting();
			// Switching to Parent Window
			 driver.switchTo().window(parentWindow);
	 }
	  collapse();
	  driver.close();
  }
  
//  @Test
public void changePassword()
{
	  help.expand();
	  System.out.println("======================\n"+" My Account");
	  if(driver.findElement(By.linkText("Change Password")).isDisplayed())
	  {
					driver.findElement(By.linkText("Change Password")).click(); 
					driver.findElement(By.id("oldPassword")).sendKeys("venky");
					driver.findElement(By.id("newPassword")).sendKeys("venkatesh");
					driver.findElement(By.id("confirmPassword")).sendKeys("venkatesh");
					driver.findElement(By.id("change")).sendKeys(Keys.ENTER);
					System.out.println(driver.findElement(By.xpath(".//*[@id='changePassword']/form/label[1]")).getText());
	  }
	  else 
		Assert.fail("No Link Found");
	  System.out.println("___________________________________________________________________________________");
			
	  
} 
 
 

	 

  @AfterMethod
  public void afterMethod() 
  {
	  
  }

}

