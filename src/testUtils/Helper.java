package src.testUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.nexiilabs.dbcon.DBConnection;

import src.crm.BaseTest;

public class Helper extends BaseTest
{
	
	public void browser()	//To select browser
	{
		if (config.getProperty("BrowserType").equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
			
		}
		
		else if (config.getProperty("BrowserType").equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		else if (config.getProperty("BrowserType").equalsIgnoreCase("ie")) 
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else
		{
			Assert.fail("Select Browser Correctly choose either Firefox or Chrome or IE");
		}
	}
	
	public void maxbrowser()   //To maximize browser
	{
		driver.manage().window().maximize();
	}
	
	public void browsererror() 	//To handle browser errors
	{
		log.debug("Checking for browser error");
		String currenturl = driver.getTitle();
		if (currenturl.toLowerCase().contains("Problem loading page") || currenturl.toLowerCase().contains("is not available") || currenturl.toLowerCase().contains("cannot display the webpage"))
		  {
			  Assert.fail("Check your Internet Connectivity or URL specfied");
		  }
		  else
		  {
			  return;
		  }
	}
	
	public boolean screenshot(String filename) throws Exception  // To take screenshots
	  {
		  f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  FileUtils.copyFile(f, new File(System.getProperty("user.dir")+"\\src\\screenshots\\"+filename+".jpg"));
		  return false;
	  }
	
	public void sleep(int seconds) // To sleep
	{
		try
		{
			Thread.sleep(seconds*1000);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void login(String username, String password) throws Exception  // To Login 
	{
		sleep(2);
		driver.findElement(By.id(or.getProperty("username_id"))).sendKeys(username);
		sleep(1);
		driver.findElement(By.id(or.getProperty("password_id"))).sendKeys(password);
		sleep(1);
		driver.findElement(By.cssSelector(or.getProperty("loginbutton_css"))).findElement(By.tagName(or.getProperty("loginbutton_input_tagname"))).submit();
		sleep(1);	
	}
	
	
	public void expand()   // To expand side tree menu
	{
		List<WebElement> men = driver.findElements(By.className("menu"));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
	  List<WebElement> exp = driver.findElement(By.className("menu")).findElements(By.className("close"));
	  sleep(1);
	  for (int i = 0; i < exp.size(); i++)
	  { 
		exp.get(i).findElement(By.className("    symbol-close")).click();
		sleep(1);
	  }
	}
	
	public void collapse()  // To collapse side tree menu
	{
		List<WebElement> men = driver.findElements(By.className("menu"));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
	  List<WebElement> coll = driver.findElement(By.className("menu")).findElements(By.className("open"));
	  sleep(1);
	  for (int i = 0; i < coll.size(); i++)
	  {
		coll.get(i).findElement(By.className("     symbol-open")).click();
		sleep(1);
	  }   
    }
	
	public int random(int size) // To generate a random number
	{
		Random r = new Random();
		int n = r.nextInt(size);
		return n;
	}
	
	public void sidetreemenuverify(int j) // 0=Admin, 1=Researcher , 2=BDM , 3=BDE , 4=Management 
	{
		List<WebElement> men = driver.findElements(By.className("menu"));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
		List<WebElement> lis = driver.findElement(By.id("tree_menu")).findElements(By.className(" close"));
		  for (int i = 0; i < lis.size(); i++)
		  {
			if(lis.get(i).getText().equalsIgnoreCase(sh7.getCell(j, i+1).getContents()))
			{
				System.out.println(lis.get(i).getText()+"="+sh7.getCell(j, i+1).getContents());	
			}
			else
			{
				Assert.fail(lis.get(i).getText()+"!="+sh7.getCell(j, i+1).getContents());
			}
			
		  }
	}
	
	

	public void ascending(int l)
	{
	List<String> ids= new ArrayList<String>();
	ArrayList<Integer> li= new ArrayList<Integer>();
	List<WebElement> tablerecords= driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	if(tablerecords2.size()>1)
	{
	for(int a=0;a<tablerecords.size();a++){
	if(l==0){
	String s=tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText() ;
	int z = Integer.parseInt(s);
	
	li.add(a, z);
	}
	else{
	ids.add(a, tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText());
	}
	}
	help.sleep(1);
	//validation
	if(l==0){
	for(int i=0;i<li.size();i++){
	for(int j=i;j<li.size();j++){
	int val=(li.get(i)).compareTo(li.get(j));
	if(val<=0)
	continue;
	else
	Assert.fail("sorting ascending: is on column: "+(l+1)+":failed");
	}
	}
	}
	else{
	for(int i=0;i<ids.size();i++){
	for(int j=i;j<ids.size();j++){
	int val=(ids.get(i)).compareToIgnoreCase(ids.get(j));
	if(val<=0)
	continue;
	else
	Assert.fail("sorting ascending: is on column: "+(l+1)+":failed");
	}
	}
	}
	}
	}


	public void descending(int l)
	{
	List<String> ids= new ArrayList<String>();
	ArrayList<Integer> li= new ArrayList<Integer>();
	List<WebElement> tablerecords= driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(or.getProperty("tablecol_tagname")));
	if(tablerecords2.size()>1)
	{
	for(int a=0;a<tablerecords.size();a++){
	if(l==0){
	String s=tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText() ;
	int z = Integer.parseInt(s);
	li.add(a, z);
	}else{
	ids.add(a, tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText());
	}
	}
	help.sleep(1);
	//validation
	if(l==0){
	for(int i=0;i<li.size();i++){
	for(int j=i;j<li.size();j++){
	int val=(li.get(i)).compareTo(li.get(j));
	if(val>=0)
	continue;
	else
	Assert.fail("sorting descending: is on column: "+(l+1)+":failed");
	}
	}
	}
	else{
	for(int i=0;i<ids.size();i++){
	for(int j=i;j<ids.size();j++){
	int val=(ids.get(i)).compareToIgnoreCase(ids.get(j));
	if(val>=0)
	continue;
	else
	Assert.fail("sorting descending: is on column: "+(l+1)+":failed");
	}
	}
	}
	}
	}

	public void sorting()
	{
	int n=1;
	List<WebElement> tablecolumns= driver.findElement(By.tagName(or.getProperty("table_head"))).findElements(By.tagName(or.getProperty("tablehead_tagname")));
	if(driver.getCurrentUrl().contains("search/search_leads.jsp"))
	{
	n--;
	}
	Reporter.log("<p>" +"totalcolumns having sorting options="+(tablecolumns.size()));
	for(int l=0;l<(tablecolumns.size()-n);l++){
	tablecolumns.get(l).click();
	help.sleep(1);
	if(tablecolumns.get(l).getAttribute("class").contains(or.getProperty("ascending_class"))){
	Reporter.log("<p>" +"sorting ascending: on column: "+(l+1)+":"+tablecolumns.get(l).getText());
	ascending(l);
	}else{
	Reporter.log("<p>" +"sorting descending: on column: "+(l+1)+":"+tablecolumns.get(l).getText());
	descending(l);
	}
	tablecolumns.get(l).click();
	help.sleep(1);
	if(tablecolumns.get(l).getAttribute("class").contains(or.getProperty("ascending_class"))){
	Reporter.log("<p>" +"sorting ascending: on column: "+(l+1)+":"+tablecolumns.get(l).getText());
	ascending(l);
	}else{
	Reporter.log("<p>" +"sorting descending: on column: "+(l+1)+":"+tablecolumns.get(l).getText());
	descending(l);
	}
	}
	}

		
  public void searchLead()	// Method for Lead Search phase
	{
		if(driver.findElement(By.id("serachLeads123")).isDisplayed()) 
		{
			driver.findElement(By.id("serachLeads123")).click();

	// Switching to Child Window
	String parentWindow = driver.getWindowHandle();
		for(String childWindow : driver.getWindowHandles()) 
		{
			driver.switchTo().window(childWindow);
		}

	// Selecting Required fields
	List <WebElement> requiredFields = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("td"));
	int a = help.random(requiredFields.size());
	String field = requiredFields.get(a).getText();
	Reporter.log("<p>" +"The reqiured field:" + field);
	requiredFields.get(a).findElement(By.tagName("input")).click();
	driver.findElement(By.cssSelector("span.ui-accordion-header-icon.ui-icon.ui-icon-triangle-1-e")).click();


	// Selecting a Category of Filter Options
	List <WebElement> filterOptions = driver.findElement(By.id("ui-accordion-accordion-panel-1")).findElements(By.className("row1"));
	Reporter.log("<p>" +"Size of Filter option categories:" + filterOptions.size());
		for(int i=0; i<filterOptions.size(); i++) 
		{
			String opt = filterOptions.get(i).findElement(By.tagName("legend")).getText();
			Reporter.log("<p>" +"Filter Option Selected:" + opt);

	// Selecting an option in a category in Filter Options and clicking on search button
	List <WebElement> option = filterOptions.get(i).findElements(By.tagName("td"));
	Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());
	int c = help.random(option.size());
	Reporter.log("<p>" +"Option selected is:" + option.get(c).findElement(By.tagName("label")).getText());
	option.get(c).findElement(By.tagName("input")).click();
	driver.findElement(By.id("registerbutton")).click();
	help.sleep(5);

	// Printing the Table displayed with required fields
	Reporter.log("<p>" +driver.findElement(By.id("example")).findElement(By.tagName("tbody")).getText());
	//driver.navigate().refresh(); 
	option.get(c).findElement(By.tagName("input")).click();
	}

	// Closing the Child Window
	driver.close();

	// Switching to Parent Window
	driver.switchTo().window(parentWindow);
	} 
	else
	Assert.fail("No Link Found");
	}
	
	

  public void searchLeadPagination() throws Exception		// Method for Paginations in Search Lead phase 
	{
	if(driver.findElement(By.id("serachLeads123")).isDisplayed()) 
	{
		driver.findElement(By.id("serachLeads123")).click();

	// Switching to Child Window
	String parentWindow = driver.getWindowHandle();
		for(String childWindow : driver.getWindowHandles()) 
		{
			driver.switchTo().window(childWindow);
		}

	// Selecting Required fields
	List <WebElement> requiredFields = driver.findElement(By.id("fields_to_get")).findElements(By.tagName("td"));
	List <String> fieldoptions = new ArrayList <String>();
		for(int i=1; i<requiredFields.size(); i++) 
		{
			fieldoptions.add(requiredFields.get(i).getText());
		}

	Reporter.log("<p>" +"The required field:" + requiredFields.get(0).getText());
	requiredFields.get(0).findElement(By.tagName("input")).click();
	driver.findElement(By.cssSelector("span.ui-accordion-header-icon.ui-icon.ui-icon-triangle-1-e")).click();

	// Selecting a Category of Filter Options
	WebElement filterOption = driver.findElement(By.id("ui-accordion-accordion-panel-1")).findElements(By.className("row1")).get(5);

	String opt = filterOption.findElement(By.tagName("legend")).getText();
	Reporter.log("<p>" +"Filter Option Selected:" + opt);
	// Selecting an option in a category in Filter Options and clicking on search button
	List <WebElement> option = filterOption.findElements(By.tagName("td"));
	Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());

	Reporter.log("<p>" +"Option selected is:" + option.get(3).findElement(By.tagName("label")).getText());
	option.get(3).findElement(By.tagName("input")).click();
	driver.findElement(By.id("registerbutton")).click();
	help.sleep(5);

	// Printing the Table displayed with required fields
	List <WebElement> fields = driver.findElement(By.tagName("thead")).findElements(By.tagName("th"));
	List <String> fieldheads = new ArrayList <String>();
	for(int i=1; i<fields.size(); i++) 
	{
	fieldheads.add(fields.get(i).getText());
	}
	System.out.println(fieldheads);
	System.out.println(fieldoptions);

	if(fieldheads.equals(fieldoptions)) 
	{
	Reporter.log("<p>" +"The required fields selected is matched with the headers of the table.");
	Reporter.log("<p>" +driver.findElement(By.id("example")).findElement(By.tagName("tbody")).getText());

	// Pagination
	pagination();

	// No.of Entries per page
	pageEntries();

	// Sorting
	help.sorting();

	// Search Box Validation
	driver.findElement(By.id("example_filter")).findElement(By.tagName("input")).sendKeys("Johnson");
	Reporter.log("<p>" +driver.findElement(By.id("example")).findElement(By.tagName("tbody")).getText());
	}
	else 
	Reporter.log("<p>" +"The required fields selected is not matched with the headers of the table.");

	// Closing the Child Window
	//driver.close();

	// Switching to Parent Window
	driver.switchTo().window(parentWindow);
	} 
	else
	Assert.fail("No Link Found"); 
	}
  
  public void pageEntries() throws Exception
  {
	  //Verifying table availability
	  List<WebElement> ent1 = driver.findElements(By.name("example_length"));
	  if (ent1.size()==0) 
	  {
		Assert.fail("Table not available, call method after selecting any side tree menu");
	  }
	  
	  // Getting entries
	  List<WebElement> ent = driver.findElement(By.name("example_length")).findElements(By.tagName("option"));
	  if (ent.size()==0) 
	  {
		Assert.fail("Entries not available");
	  }
	 
	  //Selecting entries one after another
	  for (int i = 0; i < ent.size(); i++)
	  {
		new Select(driver.findElement(By.name("example_length"))).selectByValue(ent.get(i).getText());
		  List<WebElement> tab = driver.findElement(By.id("example")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		 
		  int a = tab.size();
		  int b = Integer.parseInt(ent.get(i).getText());
		  
		  //Verifying entry and table size
		  if (a==b) 
		  { 
			Reporter.log("Table contents displaying as per Entries "+ent.get(i).getText()+"<p>");  
			//System.out.println("Table contents displaying as per Entries "+ent.get(i).getText());
			pagination();
		  }
		  else
		  {
			  if (a<b & driver.findElement(By.className("paginate_disabled_next")).isDisplayed())
			  {
				  Reporter.log("Table contents displaying as per Entries "+ent.get(i).getText()+"<p>");
				  //System.out.println("Table contents displaying as per Entries "+ent.get(i).getText());
			  }
			  else
			  {
				  Reporter.log("Table contents NOT displaying as per Entries "+ent.get(i).getText()+"<p>");
				  //System.out.println("Table contents NOT displaying as per Entries "+ent.get(i).getText());
			  }
		  }
		 sleep(1); 	  
	  } 
  }  
  
  public void pagination()
  {
	  sleep(2);
	  //verifying for pagination to next page for all entries
	  WebElement pageNext= driver.findElement(By.id(or.getProperty("pagination_next")));
	  if(pageNext.isDisplayed()){
	  Reporter.log("<p>" + "pagination next button is present");
	  }else
	  {
	  Assert.fail("pagination button not present");
	  }
	  //clicking on the pagination next until it is disabled
	  while(!pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
	  String s = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  Reporter.log("<p>" + s);
	  pageNext.click();

	  String s1 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  if(!s1.equalsIgnoreCase(s)){
	  Reporter.log("<p>" + "navigating to next page");
	  }else{
	  Assert.fail("page navigation failed to next page");
	  }
	  sleep(1);
	  //verification of navigation to the previous page for all entries 
	  if(pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")) {
	  WebElement pagePrevious = driver.findElement(By.id(or.getProperty("pagiantion_prev")));
	  if(pagePrevious.isDisplayed()){
	  Reporter.log("<p>" + "pagination previous button present");
	  }else{
	  Assert.fail("pagination previous button not present");
	  }
	  //clicking on the pagination to previous page until it is disabled
	  while(!pagePrevious.getAttribute("class").equalsIgnoreCase("paginate_disabled_previous")){
	  String s2 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  pagePrevious.click();
	  String s3 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  Reporter.log("<p>" + s2);
	  if(!s2.equalsIgnoreCase(s3)){
	  Reporter.log("<p>" + "navigated to previous page");
	  }else{
	  Assert.fail("page navigation failed to previous page");
	  }
	  }
	  //To break the loop once the circle finished
	  if(!pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next")){
	  break;
	  }
	  }

	  }
	  }
  
  public void changePassword(String string) throws Exception, IllegalAccessException, ClassNotFoundException{
	  System.out.println("===========My Account=========");
	  Class.forName("com.mysql.jdbc.Driver").newInstance();
	  connection = DBConnection.getConnection();
	  statement = connection.createStatement();
	 // System.out.println("hurray connected");
	  resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'"); 
	  resultSet.next();
	  //String str = resultSet.getString("password");
	  //return str;
	  String dat = resultSet.getString("password");

	  //===Researcher is 3rdsheet in excels=====//
	  int columns = sh3.getColumns();
	  int rows = sh3.getRows();
	  String data;
	  int col;
	  //====getting current url====//
	  String currenturl= driver.getCurrentUrl();

	  //======Clicking changepassword in myaccount menu====//
	  driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
	  help.sleep(2);

	  //==connecting database and retriveng password==//
	  ////// String dat = myAccount("pavan.nanigans@gmail.com");

	  // System.out.println(dat);
	  Reporter.log("<p>" + "old password is:" + dat);

	  //=== clicking cancle button===//
	  driver.findElement(By.id(or.getProperty("cancel1"))).sendKeys(Keys.ENTER);
	  help.sleep(3);

	  //===getting current url after cancle button====//
	  String aftcancleurl = driver.getCurrentUrl();

	  //comparing current url after cancle button// 
	  if(aftcancleurl.equalsIgnoreCase(currenturl)){
	  Reporter.log("<p>" +"Successfully canceled the change password page");
	  } else {
	  Reporter.log("<p>" +"Not successfully canceled change password page");
	  }
	  //======Clicking searchleads in leadsearch menu for change button====//
	  driver.findElement(By.linkText(or.getProperty("changepassword"))).sendKeys(Keys.ENTER);
	  help.sleep(2);
	  //=== checking validations for change passwords===//
	  for(int row = 1;row < rows;row++)
	  {
	  col=0;
	  //=========checking oldpassword===//
	  List<WebElement> li70 = driver.findElements(By.id(or.getProperty("oldpass")));
	  if(li70.size()==0)
	  { 
	  help.screenshot("oldpasswordcontainer");
	  Assert.fail("not a container");
	  }
	  else{
	  //System.out.println("Old password container is avilable");
	  Reporter.log("<p>" +"Old password container is avilable");
	  }

	  data = sh3.getCell(col, row).getContents();
	  Reporter.log("<p>" +data);
	  //System.out.println(data);
	  //System.out.println("*************************");
	  Reporter.log("<p>" +"*************************");
	  driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(sh3.getCell(col,row).getContents());
	  Reporter.log("<p>" +"*************************");
	  //==placing oldpassword in s1==//
	  String s1 = driver.findElement(By.id(or.getProperty("oldpass"))).getAttribute("value");
	  col++;
	  Thread.sleep(2000);
	  //======new password=====//
	  List<WebElement> newpass = driver.findElements(By.id(or.getProperty("newpass")));
	  if(newpass.size()==0)
	  { 
	  //====calling helper====//
	  help.screenshot("newpassword container");
	  Assert.fail("not a container");
	  }
	  else {

	  //System.out.println("New password container is available");
	  Reporter.log("<p>" +"New password container is avilable");
	  }
	  data = sh3.getCell(col, row).getContents();
	  System.out.println(data);
	  Reporter.log("<p>" +data);
	  Reporter.log("<p>" +"*************************");
	  driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(sh3.getCell(col,row).getContents());
	  Reporter.log("<p>" +"*************************");
	  //==placing new password in s2==//
	  String s2 = driver.findElement(By.id(or.getProperty("newpass"))).getAttribute("value");
	  col++;
	  Thread.sleep(2000);

	  List<WebElement> confirmpass = driver.findElements(By.id(or.getProperty("confirmpass")));
	  if(confirmpass.size()==0)
	  {
	  //====calling helper====//
	  help.screenshot("Configpassword container");
	  Assert.fail("not a container");
	  }
	  else {
	  Reporter.log("<p>" +"Confirm password container is avilable");
	  }
	  data = sh3.getCell(col, row).getContents();
	  //System.out.println(data);
	  Reporter.log("<p>" +data);
	  Reporter.log("<p>" +"*************************");
	  driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(sh3.getCell(col,row).getContents());
	  Reporter.log("<p>" +"*************************");
	  //==Placing confirm password in s3==//
	  String s3 = driver.findElement(By.id(or.getProperty("confirmpass"))).getAttribute("value");
	  col++;
	  Thread.sleep(2000);

	  //====Clicking Change button====//
	  driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
	  //===This is for if my oldpassword and my currently changing password is same then handing that scenario===//
	  
	  System.out.println(dat+":::"+s1+":::"+s2+"::"+s3);
	  
	  if(s1.equalsIgnoreCase(dat)&&s2.equalsIgnoreCase(s1)&&s3.equalsIgnoreCase(s2)) {
	  String str ="000";
	  driver.findElement(By.id(or.getProperty("oldpass"))).clear();
	  driver.findElement(By.id(or.getProperty("newpass"))).clear();
	  driver.findElement(By.id(or.getProperty("confirmpass"))).clear();

	  driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat);
	  driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(str);
	  driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(str);
	  //====Clicking Change button====//
	  driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
	  help.sleep(2);

	  }
	  //System.out.println("dddddddddddddddd");
	  if(driver.getCurrentUrl().equalsIgnoreCase("http://192.168.50.32:8080/leadcrm/login.jsp")) {

	  		
	  //==setting old password==//

	  //String dat1 = dbConnection("pavan.nanigans@gmail.com");
	  Class.forName("com.mysql.jdbc.Driver").newInstance();
	  connection = DBConnection.getConnection();
	  statement = connection.createStatement();
	  System.out.println("hurray connected");
	  resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'"); 
	  resultSet.next();
	  //String str = resultSet.getString("password");
	  //return str;
	  String dat1 = resultSet.getString("password");
	  // System.out.println("changed password"+dat1);
	  Reporter.log("<p>" +"changed password"+dat1);


	  //Login
	  driver.findElement(By.id(or.getProperty("username1"))).sendKeys(string);
	  driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat1);
	  driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();

	  //==placing change password==//
	  //System.out.println("old password is:" + dat);
	  Reporter.log("<p>" +"old password is:" + dat);

	  //====size of tree menu===//
	  //treeSize();
	  List<WebElement> list1 = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
	  Reporter.log("<p>" + "Number Elements in List1 : " + list1.size());
	  //System.out.println("Number Elements in List1 : " + list1.size());
	  String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
	  if(user.contains("Hi ! Researcher"))
	  Reporter.log("<p>" + "User Logged in as:" + "Hi ! Researcher");

	  //======= Expanding tree menu========//
	  help.expand();

	  //====getting current url====//
	  String currenturl1= driver.getCurrentUrl();

	  //======Clicking changepassword in myaccount menu====//
	  driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
	  help.sleep(2);

	  //oldpassword//
	  driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat1);
	  //String one = driver.findElement(By.id("confirmPassword")).getAttribute("value");

	  //newpassword//
	  driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(dat);
	  //String two = driver.findElement(By.id("confirmPassword")).getAttribute("value");

	  //confirm password//
	  driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(dat);

	  //String three = driver.findElement(By.id("confirmPassword")).getAttribute("value");
	  driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);

	  /////////////
	  //String dat2 = dbConnection("pavan.nanigans@gmail.com");
	  Class.forName("com.mysql.jdbc.Driver").newInstance();
	  connection = DBConnection.getConnection();
	  statement = connection.createStatement();
	  System.out.println("hurray connected");
	  resultSet = statement.executeQuery("select password from crm_user where email_id='"+string+"' AND delete_status='no'"); 
	  resultSet.next();
	  //String str = resultSet.getString("password");
	  //return str;
	  String dat2 = resultSet.getString("password");
	  // System.out.println("changed password"+dat1);
	  Reporter.log("<p>" +"changed password"+dat1);

	  System.out.println("changed password"+dat2);
	  //Login
	  driver.findElement(By.id(or.getProperty("username1"))).sendKeys(string);
	  driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat2);
	  driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();
	  //==placing change password==//
	  //System.out.println("old password is:" + dat2);
	  Reporter.log("<p>" +"old password is:" + dat2);

	  //====size of tree menu===//
	  List<WebElement> lis = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
	  Reporter.log("<p>" + "Number Elements in List1 : " + lis.size());
	  //System.out.println("Number Elements in List1 : " + list1.size());
	  String use = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
	  if(use.contains("Hi ! Researcher"))
	  Reporter.log("<p>" + "User Logged in as:" + "Hi ! Researcher");
	  driver.findElement(By.linkText("Logout")).click();
	  }
	  }
	  }

} 	

