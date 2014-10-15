package crm;

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
  
  @Test
  public void Leadedit() throws Exception 
  {
	  List<WebElement> tree=driver.findElement(By.id("tree_menu")).findElements(By.className("close"));
	  //To enter into Leadedit tree 
	  for(int j=0;j<tree.size();j++)
	  {
			if( tree.get(j).getText().equalsIgnoreCase("Lead Edit"))
			{
			tree.get(j).findElements(By.tagName("span")).get(0).click(); 
			List<WebElement> container=tree.get(j).findElements(By.tagName("a"));
			System.out.println("===================================\n" +"No of"+ tree.get(j).getText()+ "sub links: "+(container.size()));
			container.get(0).click();
			System.out.println(container.get(0).getText()+"  clicked and navigated to  "+driver.findElement(By.tagName("h1")).getText()+"     successfully");
			
				System.out.println("=================================================\n"+"SORTING::");
				//SORTING
				List<WebElement> totalcolumns= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
				System.out.println("totalcolumns having sorting options="+(totalcolumns.size()));
				for(int l=0;l<totalcolumns.size();l++)
				{
					if(totalcolumns.get(l).getAttribute("class").equalsIgnoreCase("sorting"))
						totalcolumns.get(l).click();
					if(totalcolumns.get(l).getAttribute("class").contains("sorting_asc"))
						System.out.println("sorting ascending: is performed on column: "+totalcolumns.get(l).getText());
					else
						System.out.println("sorting descending: is performed on column: "+totalcolumns.get(l).getText());
					totalcolumns.get(l).click();
					if(totalcolumns.get(l).getAttribute("class").contains("sorting_asc"))
					System.out.println("sorting ascending: is performed on column: "+totalcolumns.get(l).getText());
					else
						System.out.println("sorting descending: is performed on column: "+totalcolumns.get(l).getText());
				}
				
				System.out.println("=================================================\n"+"SHOW DROPDOWN:");
				//SHOWDROPDOWN BOX
				List<WebElement>showlength=driver.findElement(By.className("dataTables_length")).findElements(By.tagName("option"));
				System.out.println("No of elements in show dropdown box:"+showlength.size());
				String s1=driver.findElement(By.className("dataTables_info")).getText();
				for(int k=0;k<showlength.size();k++)
				{
				showlength.get(k).click();
				Thread.sleep(2000);
				System.out.println("======================================================");
				System.out.println("selected item in show dropdown is:"+showlength.get(k).getAttribute("value"));
				String a=showlength.get(k).getAttribute("value");
				int b=Integer.parseInt(a);
				List<WebElement> rows= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				System.out.println("no of records displaying is:"+ rows.size());
				int c=rows.size();
				System.out.println(driver.findElement(By.className("dataTables_info")).getText());
					if(b>=c){
					System.out.println("no of records displayed according to show dropdown selected item");
					}
				}
				
				System.out.println("=================================================\n"+"SEARCH BOX");
				//SEARCHBOX
				driver.findElement(By.className("dataTables_filter")).findElement(By.tagName("input")).sendKeys("d");
				String s=driver.findElement(By.className("dataTables_filter")).findElement(By.tagName("input")).getText();
				//validation
				if(driver.findElement(By.className("dataTables_info")).getText().contains("Showing 0 to 0 of 0"))
				{
				System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
				break;
				}else{
				List<WebElement> tablerows= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					if(tablerows.size()!=0)
					System.out.println("tbody id  for serachbox is found ");
					System.out.println("No of records by searching  "+ s +"is :"+tablerows.size());
					if(tablerows.get(random(tablerows.size())).getText().contains(s))
					System.out.println("records displayed according to search item successfully");
				}
				System.out.println(driver.findElement(By.className("dataTables_info")).getText());
				
				
			/*		//PAGINATION
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
					}*/
					
				
				
				System.out.println("=================================================\n"+"EDIT::");
				//EDIT
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.linkText("Edit")).click();
				driver.findElement(By.id("firstname")).clear();
				driver.findElement(By.id("firstname")).sendKeys("denni");
				driver.findElement(By.id("lastname")).clear();
				driver.findElement(By.id("lastname")).sendKeys("Mcdonal");
				driver.findElement(By.id("mobilenumber")).clear();
				driver.findElement(By.id("mobilenumber")).sendKeys("4-(841)774-5254");
				driver.findElement(By.id("boardnumber")).clear();
				driver.findElement(By.id("boardnumber")).sendKeys("2-3-4-5");
				driver.findElement(By.id("desknumber")).clear();
				driver.findElement(By.id("desknumber")).sendKeys("1-(611)153-3511");
				List<WebElement> dropdown=driver.findElement(By.className("row1")).findElements(By.tagName("select"));
				for(int a=0;a<dropdown.size();a++)
				{
					List<WebElement>container1=dropdown.get(a).findElements(By.tagName("option"));
					Random r= new Random();
					int w=r.nextInt(container1.size());
					container1.get(w).click();
					if(container1.get(w).getAttribute("selected").equalsIgnoreCase("selected"))
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
				
				System.out.println("=================================================\n"+"TRACKIT");
				//TRACKIT
				List<WebElement> trackelement=driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				String s2=trackelement.get(0).findElements(By.tagName("td")).get(1).getText();
				System.out.println(s2);
				trackelement.get(0).findElement(By.className("analyse")).click();
				List<WebElement> trackelement1=driver.findElement(By.id("body_result")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td"));
				System.out.println((trackelement1.get(1).getText()));
				String s3=trackelement1.get(1).getText();
				container.get(0).click();
				if(s3.contains(s2)){
					System.out.println("deatails of tracked record displayed successfully ");
				}
			}
	  }
  }
 

  

  @AfterMethod
  public void afterMethod() {
  }

}

