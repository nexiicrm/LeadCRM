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
	  help.login("sreekar.jakkula@nexiilabs.com", "password");
	  if(driver.findElement(By.className("user_name")).getText().equalsIgnoreCase("Hi ! BDE sreekar")){
		  System.out.println("logged into BDE done successfully");
	  }
	  else{
		  System.out.println(driver.findElement(By.className("login button")).findElements(By.tagName("label")).get(0).getText());
		  Assert.fail("login not done");
	  }
  }
  
  public void Showdropdown() throws InterruptedException
  {
	  	System.out.println("=================================================\n"+"SHOW DROPDOWN:");
		List<WebElement>showlength=driver.findElement(By.className("dataTables_length")).findElements(By.tagName("option"));
		System.out.println("No of elements in show dropdown box:"+showlength.size());
		for(int k=0;k<showlength.size();k++)
		{
			showlength.get(k).click();
			Thread.sleep(2000);
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
			if(b>=c)
			{
				System.out.println("no of records displayed according to show dropdown selected item successfully");
			}
			
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
	  List<WebElement> tablerecords= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  for(int a=0;a<tablerecords.size();a++)
		ids.add(a, tablerecords.get(a).findElements(By.tagName("td")).get(l).getText());
	  //validation
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
  public void descending(int l)
  {
	  List<String> ids= new ArrayList<String>();
	  List<WebElement> tablerecords= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  for(int a=0;a<tablerecords.size();a++)
		  ids.add(a, tablerecords.get(a).findElements(By.tagName("td")).get(l).getText());
	  //validation
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
  
  public void Sorting()
  {
	  	List<WebElement> tablecolumns= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
		System.out.println("totalcolumns having sorting options="+(tablecolumns.size()));
		for(int l=0;l<tablecolumns.size();l++)
		{
			tablecolumns.get(l).click();
			help.sleep(3);
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
			help.sleep(3);
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
  
  public void Pagination()
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
		System.out.println("=======================================================");
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
  public void Leadedit() throws Exception 
  {
	  List<WebElement> tree=driver.findElement(By.id("tree_menu")).findElements(By.className("close")); 
	  for(int j=0;j<tree.size();j++)
	  {
		  //To enter into Leadedit tree
			if( tree.get(j).getText().equalsIgnoreCase("Lead Edit"))
			{
				tree.get(j).findElements(By.tagName("span")).get(0).click(); 
				List<WebElement> container=tree.get(j).findElements(By.tagName("a"));
				//System.out.println("===================================\n" +"No of"+ tree.get(j).getText()+ "sub links: "+(container.size()));
				container.get(0).click();
				System.out.println(container.get(0).getText()+":clicked and navigated to:"+driver.findElement(By.tagName("h1")).getText()+"     successfully");
				
				//SHOW DROPDOWN
				//Showdropdown();
				//SEARCHBOX
				//Searchbox();
				//SORTING
				Sorting();
				//PAGINATION
				//Pagination();	
			}
	  }
	  collapse();
	  driver.close();
  }
  
  @Test
  public void Edit()
  {
	  help.expand();
	  System.out.println("=================================================\n"+"EDIT::");
	  if(driver.findElement(By.id("editLeads")).isDisplayed())
	  {
	  		driver.findElement(By.id("editLeads")).click();
			// Verifying no.of leads in the page
		  	List <WebElement> leads = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  	help.sleep(3);
		  	System.out.println("No. of leads in the Lead Edit Table:" + leads.size());
		  	// Checking for the buttons for each lead
		  	int edit=0;
		  	for(int i=0; i<leads.size(); i++) 
		  	{
				if(leads.get(i).findElement(By.className("edit")).isEnabled()) 
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
			driver.findElement(By.linkText("Edit")).click();
			System.out.println(driver.findElement(By.id("dialog-form")).findElement(By.tagName("h1")).getText());
			for(int row=1;row<=1;row++)
			{
					int col=0;
					driver.findElement(By.id("firstname")).clear();
					driver.findElement(By.id("firstname")).sendKeys(sh4.getCell(col, row).getContents());
					driver.findElement(By.id("lastname")).clear();
					driver.findElement(By.id("lastname")).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id("mobilenumber")).clear();
					driver.findElement(By.id("mobilenumber")).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id("boardnumber")).clear();
					driver.findElement(By.id("boardnumber")).sendKeys(sh4.getCell(++col, row).getContents());
					driver.findElement(By.id("desknumber")).clear();
					driver.findElement(By.id("desknumber")).sendKeys(sh4.getCell(++col, row).getContents());
			}
			List<WebElement> updatedropdowns=driver.findElement(By.className("row1")).findElements(By.tagName("select"));
			for(int a=0;a<updatedropdowns.size();a++)
			{
					List<WebElement>container1=updatedropdowns.get(a).findElements(By.tagName("option"));
					int w=random(container1.size());
					container1.get(w).click();
					if(container1.get(w).isSelected())
					{
						System.out.println(w+":"+container1.get(w).getText()+" is selected ");
					}
			}
			driver.findElement(By.id("editbutton")).click();
			if(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getAttribute("class").contentEquals("error_msg"))
			{
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			}
			else
			{
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
			}
				driver.findElement(By.className("ui-button-text")).click();
		}
	  	collapse();
	  	driver.close();
  }
  
  @Test
  public void Trackit()
  {
	  
	  help.expand();
	  System.out.println("=================================================\n"+"TRACKIT");
	  if(driver.findElement(By.id("editLeads")).isDisplayed())
	  {
	  		driver.findElement(By.id("editLeads")).click();
	  		List<WebElement> trackelement=driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  		//Checking for the Track it button for each lead
	  		int trackit=0;
			for(int i=0; i<trackelement.size(); i++) 
			{
					if(trackelement.get(i).findElement(By.className("analyse")).isEnabled()) 
					trackit++;
			}
			if(trackit==trackelement.size())
			System.out.println("Trackit button is enabled for all leads.");
			int p=random(trackelement.size());
			String s2=trackelement.get(p).findElements(By.tagName("td")).get(1).getText();
			System.out.println(s2);
			trackelement.get(p).findElement(By.className("analyse")).click();
			List<WebElement> trackelement1=driver.findElement(By.id("body_result")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td"));
			System.out.println((trackelement1.get(1).getText()));
			String s3=trackelement1.get(1).getText();
			driver.findElement(By.id("editLeads")).click();
			if(s3.contains(s2))
			System.out.println("deatails of tracked record displayed successfully ");
		}
	  	collapse();
	  	driver.close();
  }
 
  @Test
  public void Closedphase() throws Exception 
  {
	  help.expand();
	  if(driver.findElement(By.id("closedPhase")).isDisplayed())
	  {
	  		driver.findElement(By.id("closedPhase")).click();
	  		List<WebElement> tablerecords= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  		//Checking for the close button for each lead
			int count=0;
			for(int i=0; i<tablerecords.size(); i++) 
			{
				if(tablerecords.get(i).findElement(By.className("close")).isEnabled()) 
						  count++;
			}
			if(count==tablerecords.size())
			System.out.println("close button is enabled for all leads.");
			int o=random(tablerecords.size());
			tablerecords.get(o).findElement(By.className("close")).click();
			System.out.println(driver.findElement(By.id("dialog-form")).findElement(By.tagName("h1")).getText()+":is opened");
			List<WebElement> leadstatus=driver.findElement(By.name("leadstatus")).findElements(By.tagName("option"));
			System.out.println("leadstatus dropdown size:"+leadstatus.size());
			int a=random(leadstatus.size());
			System.out.println(a);
			if(leadstatus.get(a).getText().contains("SELECT"))
			a++;
			leadstatus.get(a).click();
			if(leadstatus.get(a).getText().equalsIgnoreCase("Customer"))
						{
							WebElement s=	driver.findElement(By.id("projectid")).findElement(By.tagName("label"));
							if(s.getAttribute("display").contains("block"))
							{
								System.out.println(s.getText()+":textbox is available");
								driver.findElement(By.id("project")).sendKeys(or.getProperty("projectdeatailes"));
							}
							else
							{
								Assert.fail("project textbox is not available");
							}
						}
						driver.findElement(By.name("comment")).sendKeys(or.getProperty("comment"));
						driver.findElement(By.id("closedphasebutton")).click();
						System.out.println(driver.findElement(By.className("error_msg")).getText());
						driver.findElement(By.className("ui-button-text")).click();
				
		}
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
			// Switching to Parent Window
			 driver.switchTo().window(parentWindow);
	 }
	  collapse();
	  driver.close();
  }
  
  @Test
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

