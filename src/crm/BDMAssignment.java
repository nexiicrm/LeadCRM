package crm;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import testUtils.Helper;

public class BDMAssignment extends Helper{
  @Test
  public void test1_expand_collapse() {
	  
	  //Getting size of all the options in left pane
	  List<WebElement> leftpane_options = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
	  System.out.println("Number of options present in left pane is= " +leftpane_options.size());
	  
	  //Expansion of side tree menu
	  //Getting all super options and clicking it
	  for(int i=0;i<leftpane_options.size();i++){
		  System.out.println(leftpane_options.get(i).getText());
		  leftpane_options.get(i).findElement(By.tagName("span")).click();
		  //System.out.println(leftpane_options.get(i).findElement(By.tagName("a")).getText()); 
	  }
	  System.out.println("Expansion of side tree is functioning properly");
	  
	  //Getting all the sub options from super options
	  List<WebElement> inner_links = driver.findElement(By.id("tree_menu")).findElements(By.tagName("a"));
	  System.out.println("Total number of sub options in left pane is= " +inner_links.size());
	  for(int j=0;j<inner_links.size();j++){
		  System.out.println(inner_links.get(j).getText());
	  }
	  
	  //Collapse of side tree menu
	  List<WebElement> leftpane_sub_options = driver.findElement(By.id("tree_menu")).findElements(By.className("          open"));
	  for(int i=0;i<leftpane_sub_options.size();i++){
		  leftpane_sub_options.get(i).findElement(By.tagName("span")).click();  
	  }
	  System.out.println("Collapsion of side tree menu is functioning properly");
	  
  }
  
  @Test
  public void test2_assign_lead(){
	  //Expanding assignment and clicking on assign lead link 
	  List<WebElement> leftpane_option = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
	  leftpane_option.get(0).findElement(By.tagName("span")).click();
	  leftpane_option.get(0).findElement(By.id("assignlead")).click();
	  sleep(1);
	  
	  //Clicking on select service dropdown and getting all the options
	  sleep(5);
	  List<WebElement> service_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("service")).findElements(By.tagName("option"));
	  System.out.println("Total number of options under select service is= " +service_options.size());
	  Random service_rand = new Random();
	  int option = service_rand.nextInt(service_options.size());
	  if(option==0){
		  option = 1;
	  }
	  service_options.get(option).click();
	  sleep(1);
	  System.out.println("Randomly selected option from select service is = " +service_options.get(option).getText());
	  String rand_service = service_options.get(option).getText();
	  
	  //Verifying randomly selected option matches with name shown in service name column
	  List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  if(leads_info.size()<=1){
		  System.out.println("Service table is empty");
	  }else{
	  System.out.println(leads_info.get(0).findElements(By.tagName("td")).get(5).getText());
	  String lead_service = leads_info.get(0).findElements(By.tagName("td")).get(5).getText();
	  if(lead_service.equalsIgnoreCase(rand_service)){
		  System.out.println("Service table is displayed with service name selected");
	  }else{
		  System.out.println("Service table does not match with service name selected");
	  }
	 }
	  
	  //Clicking Assign button without selecting assign to whom option
	  driver.findElement(By.className("button")).click();
	  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	//Clicking Assign button without selecting any lead name
	  sleep(5);
	  List<WebElement> assign_to_options = driver.findElement(By.id("form_assign_lead")).findElement(By.name("assignto")).findElements(By.tagName("option"));
	  System.out.println("Getting assign to whom: " +assign_to_options.get(2).getText());
	  for(int k=0;k<assign_to_options.size();k++){
	  if(assign_to_options.get(k).getText().equals("Self")){
		  assign_to_options.get(k).click();
	  }
	  }
	  driver.findElement(By.className("button")).click();
	  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  
	  //Clicking Assign button by selecting any lead name
	  List<WebElement> leads_check = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	  if(leads_check.size()==0){
		  System.out.println("Service table is empty");
	  }else{
	  leads_check.get(0).findElement(By.id("checkbox")).click();
	  driver.findElement(By.className("button")).click();
	  System.out.println(driver.findElement(By.id("result_msg_div")).findElement(By.tagName("label")).getText());
	  }
  }
	
/*	@Test
	public void search_pagination_entries(){
		List<WebElement> leftpane_option = driver.findElement(By.id("tree_menu")).findElements(By.className("   close"));
		  leftpane_option.get(0).findElement(By.tagName("span")).click();
		  leftpane_option.get(0).findElement(By.id("assignlead")).click();
		  sleep(1);
		  new Select(driver.findElement(By.name("service"))).selectByVisibleText("QA");
		List<WebElement> entries_options = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
		System.out.println("Total number of options under entries is= " +entries_options.size());
		for(int i=0;i<entries_options.size();i++){
			entries_options.get(i).click();
			System.out.println("option selected is = " +entries_options.get(i).getAttribute("value"));
			sleep(1);
			System.out.println("correctly displaying leads= " +driver.findElement(By.id("example_info")).getText());
			
		}
		
		List<WebElement> leads_info = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		  if(leads_info.size()<=1){
			  System.out.println("Service table is empty");
		  }else{
		  System.out.println(leads_info.get(0).findElements(By.tagName("td")).get(1).getText());
		  }	
	}*/
  
  @BeforeTest
  public void before() throws Exception{
	  help.browser();
	  driver.get(config.getProperty("url"));
	  help.maxbrowser();
	//logging into BDM account and extracting credentials from spreadsheet 3
	  sh = w.getSheet(2);
	  help.login(sh.getCell(0, 0).getContents(),sh.getCell(1, 0).getContents());
  }
}
