package crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import testUtils.Helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
  
  public void Sorting()
  {
	  	System.out.println("=================================================\n"+"SORTING::");
	  	List<WebElement> tablecolumns= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
		System.out.println("totalcolumns having sorting options="+(tablecolumns.size()));
		for(int l=0;l<tablecolumns.size();l++)
		{
			if(tablecolumns.get(l).getAttribute("class").equalsIgnoreCase("sorting"))
				tablecolumns.get(l).click();
			if(tablecolumns.get(l).getAttribute("class").contains("sorting_asc"))
				System.out.println("sorting ascending: is performed on column: "+tablecolumns.get(l).getText());
			else
			System.out.println("sorting descending: is performed on column: "+tablecolumns.get(l).getText());
			
			
			
			
			tablecolumns.get(l).click();
			if(tablecolumns.get(l).getAttribute("class").contains("sorting_asc"))
				System.out.println("sorting ascending: is performed on column: "+tablecolumns.get(l).getText());
			else
				System.out.println("sorting descending: is performed on column: "+tablecolumns.get(l).getText());
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
				//SEARCHBOX
				Searchbox();
				//SHOW DROPDOWN
				Showdropdown();
				//SORTING
				Sorting();
				//PAGINATION
				Pagination();	
			}
	  }
	  collapse();
	  driver.close();
  }
  
  @Test
  public void Edit()
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
				System.out.println(container.get(0).getText()+":clicked and navigated to:"+driver.findElement(By.tagName("h1")).getText()+":successfully");
	  			System.out.println("=================================================\n"+"EDIT::");
				//EDIT
				int columns=sh4.getColumns();
				int rows=sh4.getRows();
				String data;
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.linkText("Edit")).click();
				System.out.println(driver.findElement(By.id("dialog-form")).findElement(By.tagName("h1")).getText());
				for(int row=2;row<=2;row++)
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
				if(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getAttribute("class").contentEquals("error_msg")){
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
				}else{
				System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
				}
				driver.findElement(By.className("ui-button-text")).click();
			}
	  }
	  collapse();
	  driver.close();
  }
  @Test
  public void Trackit()
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
				System.out.println(container.get(0).getText()+":clicked and navigated to:"+driver.findElement(By.tagName("h1")).getText()+":successfully");
				System.out.println("=================================================\n"+"TRACKIT");
				//TRACKIT
				List<WebElement> trackelement=driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				int p=random(trackelement.size());
				String s2=trackelement.get(p).findElements(By.tagName("td")).get(1).getText();
				System.out.println(s2);
				trackelement.get(p).findElement(By.className("analyse")).click();
				List<WebElement> trackelement1=driver.findElement(By.id("body_result")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td"));
				System.out.println((trackelement1.get(1).getText()));
				String s3=trackelement1.get(1).getText();
				container.get(0).click();
				if(s3.contains(s2))
				{
					System.out.println("deatails of tracked record displayed successfully ");
				}
			}
	  }
	  collapse();
	  driver.close();
  }
 
/*  @Test
  public void Closedphase() throws Exception 
  {
	  List<WebElement> tree=driver.findElement(By.id("tree_menu")).findElements(By.className("close")); 
	  for(int j=0;j<tree.size();j++)
	  {
		  //To enter into closedphase tree
			if( tree.get(j).getText().equalsIgnoreCase("Closed Phase"))
			{
				tree.get(j).findElements(By.tagName("span")).get(0).click(); 
				List<WebElement> container=tree.get(j).findElements(By.tagName("a"));
				System.out.println("===================================\n" +"No of"+ tree.get(j).getText()+ "sub links: "+(container.size()));
				container.get(0).click();
				System.out.println(container.get(0).getText()+":clicked and navigated to:"+driver.findElement(By.tagName("h1")).getText()+":successfully");
				
				//SEARCHBOX
				//Searchbox();
				
				//SHOW DROPDOWN
				//Showdropdown();
				
				//SORTING
				//Sorting();
				
				//PAGINATION
				//Pagination();
				
				//CLOSE
				driver.findElement(By.className("close")).click();
				System.out.println(driver.findElement(By.id("dialog-form")).findElement(By.tagName("h1")).getText()+":is opened");
				List<WebElement> leadstatus=driver.findElement(By.name("leadstatus")).findElements(By.tagName("option"));
				System.out.println("leadstatus dropdown size:"leadstatus.size());
				
				
			}
	  }
  }*/
  

  @AfterMethod
  public void afterMethod() 
  {
	  
  }

}

