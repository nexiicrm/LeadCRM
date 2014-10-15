package crm;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BDELeadEdits {
 
  WebDriver driver;
  @BeforeMethod
  public void beforeMethod() {
  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\Drivers\\chromedriver.exe");
  driver = new ChromeDriver();
  //driver =new FirefoxDriver();
  driver.get("http://192.168.50.32:8080/leadcrm/");
  driver.manage().window().maximize();
  }
  
  @Test
  public void f() throws InterruptedException {
	  //BDE LOGIN
	  driver.findElement(By.id("username")).sendKeys("sreekar.jakkula@nexiilabs.com");
	  driver.findElement(By.id("password")).sendKeys("password");
	  driver.findElement(By.cssSelector("p.login.button")).submit();
	  //PARENT LINKS
	  List<WebElement> tree=driver.findElement(By.id("tree_menu")).findElements(By.className("close"));
	  for(int j=0;j<tree.size();j++){
			if( tree.get(j).getText().equalsIgnoreCase("Lead Edit")){
			//lead edit clicking
			tree.get(j).findElements(By.tagName("span")).get(0).click();
			//getting lead edit sublinks 
			List<WebElement> container=tree.get(j).findElements(By.tagName("a"));
			System.out.println("==========================================================");
			System.out.println("No of lead edit sub links: "+(container.size()));
			//clicking editlead page
			container.get(0).click();
			System.out.println(container.get(0).getText()+" page clicked successfully");
			System.out.println("navigated to "+driver.findElement(By.tagName("h1")).getText()+"     successfully");
			//TRACKIT
			//List<WebElement> trackelement=driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			//trackelement.get(0).findElement(By.className("analyse")).click();
			//EDIT
			//List<WebElement> editelement=driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			//editelement.get(0).findElement(By.className("edit")).click();
			//driver.findElement(By.className("form_edit_lead")).findElement(By.id("firstname")).clear();
			//driver.findElement(By.id("firstname")).sendKeys("denni");
			/*driver.findElement(By.id("lastname")).clear();
			driver.findElement(By.id("lastname")).sendKeys("Mcdonal");
			driver.findElement(By.id("mobilenumber")).clear();
			driver.findElement(By.id("mobilenumber")).sendKeys("");
			driver.findElement(By.id("boardnumber")).clear();
			driver.findElement(By.id("boardnumber")).sendKeys("");
			driver.findElement(By.id("desknumber")).clear();
			driver.findElement(By.id("desknumber")).sendKeys("");
			List<WebElement> dropdown=driver.findElement(By.className("row1")).findElements(By.tagName("select"));
			System.out.println(dropdown.size());*/
			/*for(int w=0;w<dropdown.size();w++){
				if(dropdown.get(w).)
			}*/
			//SORTING
			List<WebElement> totalcolumns= driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
			System.out.println("totalcolumns="+(totalcolumns.size()));
				for(int l=0;l<totalcolumns.size();l++){
					if(totalcolumns.get(l).getAttribute("class").equalsIgnoreCase("sorting"))
						totalcolumns.get(l).click();
					if(totalcolumns.get(l).getAttribute("class").contains("sorting_asc"))
						System.out.println("sorting ascending: is performed on column "+totalcolumns.get(l).getText());
					else
						System.out.println("sorting descending: is performed on column "+totalcolumns.get(l).getText());
					totalcolumns.get(l).click();
					if(totalcolumns.get(l).getAttribute("class").contains("sorting_asc"))
						System.out.println("sorting ascending: is performed on column "+totalcolumns.get(l).getText());
					else
						System.out.println("sorting descending: is performed on column "+totalcolumns.get(l).getText());
					System.out.println("=============================================================");
				}
			//SEARCHBOX
			driver.findElement(By.className("dataTables_filter")).findElement(By.tagName("input")).sendKeys("d");
			String s=driver.findElement(By.className("dataTables_filter")).findElement(By.tagName("input")).getText();
			System.out.println(driver.findElement(By.className("dataTables_info")).getText());
			//validation
				if(driver.findElement(By.className("dataTables_info")).getText().contains("Showing 0 to 0 of 0")){
				System.out.println(driver.findElement(By.className("dataTables_empty")).getText());
				break;
				}else{
				List<WebElement> tablerows= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
					if(tablerows.size()!=0)
					System.out.println("id found ");
					else
					System.out.println("id not found");
			    System.out.println("No of records by searching  "+ s +"is :"+tablerows.size());
					for(int r=0;r<tablerows.size();r++){
						if(tablerows.get(r).getText().contains(s))
						System.out.println("pass");
						else
						System.out.println("fail");
					}
				}
			//SHOWDROPDOWN BOX
			List<WebElement>showlength=driver.findElement(By.className("dataTables_length")).findElements(By.tagName("option"));
			System.out.println("No of elements in show dropdown box:"+showlength.size());
			String s1=driver.findElement(By.className("dataTables_info")).getText();
			//String s2=s1.s
				for(int k=0;k<showlength.size();k++){
				showlength.get(k).click();
				Thread.sleep(2000);
				System.out.println("======================================================");
				//validation
					//if(showlength.get(k).getAttribute("selected").contains("selected"))
					System.out.println("selected item in show dropdown is:"+showlength.get(k).getAttribute("value"));
					//else
					//System.out.println("In show dropdown item not selected proprly");
				String a=showlength.get(k).getAttribute("value");
				int b=Integer.parseInt(a);
				List<WebElement> rows= driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				System.out.println("no of records displaying is:"+ rows.size());
				int c=rows.size();
				//PAGINATION
					for(int z=0;z<10;z++){
					System.out.println(driver.findElement(By.className("dataTables_info")).getText());
						if(driver.findElement(By.id("example_next")).getAttribute("class").contains("enabled")){
						driver.findElement(By.id("example_next")).click();
						}else{
						System.out.println(driver.findElement(By.id("example_next")).getAttribute("class"));
						break;
						}
					}
				System.out.println("=======================================================");
					for(int y=0;y<10;y++){
					System.out.println(driver.findElement(By.className("dataTables_info")).getText());
						if(driver.findElement(By.id("example_previous")).getAttribute("class").contains("enabled")){
						driver.findElement(By.id("example_previous")).click();
						}else{
						System.out.println(driver.findElement(By.id("example_previous")).getAttribute("class"));
						break;
						}
					}
					
				}
				
	  
			}
	  }
  }

  @AfterMethod
  public void afterMethod() {
  }

}
