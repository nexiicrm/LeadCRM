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
	
	
	public boolean waitforElement(int timeout, By by)  //WAIT FOR ELEMENT	
	{	
		while(timeout > 0) {
				sleep(1);
				List <WebElement> list = driver.findElements(by);
				if(list.size()!=0) {
					return true;
				}
				timeout--;
		}
		System.out.println("Waiting timed out... Element not found." + by.toString());
		return false;	
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
		List<WebElement> men = driver.findElements(By.className(or.getProperty("Menu_class")));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
	  List<WebElement> exp = driver.findElement(By.className(or.getProperty("Menu_class"))).findElements(By.className(or.getProperty("Close_class")));
	  sleep(1);
	  for (int i = 0; i < exp.size(); i++)
	  { 
		exp.get(i).findElement(By.className(or.getProperty("symbolclose_class"))).click();
		sleep(1);
	  }
	}
	
	public void collapse()  // To collapse side tree menu
	{
		List<WebElement> men = driver.findElements(By.className(or.getProperty("Menu_class")));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
	  List<WebElement> coll = driver.findElement(By.className(or.getProperty("Menu_class"))).findElements(By.className(or.getProperty("Open_class")));
	  sleep(1);
	  for (int i = 0; i < coll.size(); i++)
	  {
		coll.get(i).findElement(By.className(or.getProperty("symbolopen_class"))).click();
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
		List<WebElement> men = driver.findElements(By.className(or.getProperty("Menu_class")));
		if (men.size()==0)
		{
			Assert.fail("Side tree menu not available");
		}
		List<WebElement> lis = driver.findElement(By.id(or.getProperty("treemenu_id"))).findElements(By.className(or.getProperty("Close_class")));
		  for (int i = 0; i < lis.size(); i++)
		  {
			if(lis.get(i).getText().equalsIgnoreCase(sh7.getCell(j, i+1).getContents()))
			{
				Reporter.log(lis.get(i).getText()+"="+sh7.getCell(j, i+1).getContents());
			}
			else
			{
				Assert.fail(lis.get(i).getText()+"!="+sh7.getCell(j, i+1).getContents());
			}
			
		  }
	}
	
	

	public void ascending(int l)    // Nested method of sorting method
	{
	List<String> ids= new ArrayList<String>();
	ArrayList<Integer> li= new ArrayList<Integer>();
	List<WebElement> tablerecords= driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	//List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(or.getProperty("tablecol_tagname")));
		
			for(int a=0;a<tablerecords.size();a++)
			{
				if(l==0)
				{
					String s=tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText() ;
					int z = Integer.parseInt(s);
					li.add(a, z);
				}
				else
				{
					ids.add(a, tablerecords.get(a).findElements(By.tagName(or.getProperty("tablecol_tagname"))).get(l).getText());
				}
			}
			help.sleep(1);
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
							Assert.fail("sorting ascending: is on column: "+(l+1)+":failed");
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
							Assert.fail("sorting ascending: is on column: "+(l+1)+":failed");
					}
				}
			}
	}


	public void descending(int l)   //Nested method of sorting method
	{
	List<String> ids= new ArrayList<String>();
	ArrayList<Integer> li= new ArrayList<Integer>();
	List<WebElement> tablerecords= driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
	//List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(or.getProperty("tablecol_tagname")));
		
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

	public void sorting()    // Helper method for sorting in tables
	{
	int n=1;
	
		if(driver.getCurrentUrl().contains("search/search_leads.jsp"))
		{
			n--;
		}
		List<WebElement> tablecolumns= driver.findElement(By.tagName(or.getProperty("table_head"))).findElements(By.tagName(or.getProperty("tablehead_tagname")));
		List<WebElement> tablerecords= driver.findElement(By.id(or.getProperty("table_id"))).findElement(By.tagName(or.getProperty("table_body"))).findElements(By.tagName(or.getProperty("tablerow_tagname")));
		List<WebElement> tablerecords2=tablerecords.get(0).findElements(By.tagName(or.getProperty("tablecol_tagname")));
		if(tablerecords2.size()>1)
		{
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
		}else
			Reporter.log("<p>"+"no data available in the table, to perform sorting");
	}

		
		
  
	public void searchLead()		//Helper method for search leads  
	{
		if(driver.findElement(By.id(or.getProperty("leadsearchlink_id"))).isDisplayed()) 
		{
		driver.findElement(By.id(or.getProperty("leadsearchlink_id"))).click();

		// Switching to Child Window
		String parentWindow = driver.getWindowHandle();
		for(String childWindow : driver.getWindowHandles()) 
		{
		driver.switchTo().window(childWindow);
		}

		help.sleep(4);
		// Selecting Required fields
		List <WebElement> requiredFields = driver.findElement(By.id(or.getProperty("requiredfields_id"))).findElements(By.tagName(or.getProperty("servicename_tag")));
		int a = help.random(requiredFields.size());
		String field = requiredFields.get(a).getText();
		Reporter.log("<p>" +"The reqiured field:" + field);
		requiredFields.get(a).findElement(By.tagName(or.getProperty("searchbox_tag"))).click();
		driver.findElement(By.cssSelector(or.getProperty("filteroption_css"))).click();


		// Selecting a Category of Filter Options
		List <WebElement> filterOptions = driver.findElement(By.id(or.getProperty("filteroptioncontainer_id"))).findElements(By.className(or.getProperty("filteroption_class")));
		Reporter.log("<p>" +"Size of Filter option categories:" + filterOptions.size());
		for(int i=0; i<filterOptions.size(); i++) 
		{
		String opt = filterOptions.get(i).findElement(By.tagName(or.getProperty("filteroption_tag"))).getText();
		Reporter.log("<p>" +"Filter Option Selected:" + opt);

		// Selecting an option in a category in Filter Options and clicking on search button
		List <WebElement> option = filterOptions.get(i).findElements(By.tagName(or.getProperty("servicename_tag")));
		Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());
		int c = help.random(option.size());
		Reporter.log("<p>" +"Option selected is:" + option.get(c).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
		option.get(c).findElement(By.tagName(or.getProperty("searchbox_tag"))).click();
		driver.findElement(By.id(or.getProperty("registerbutton_id"))).click();
		help.waitforElement(20, By.id("example"));

		// Printing the Table displayed with required fields
		Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
		option.get(c).findElement(By.tagName(or.getProperty("searchbox_tag"))).click();
		}

		// Closing the Child Window
		driver.close();

		// Switching to Parent Window
		driver.switchTo().window(parentWindow);
		} 
		else
		Assert.fail("No Link Found");
		}

	
	
	public void searchLeadPagination() throws Exception    // helper method for search leads pagination
	{
		if(driver.findElement(By.id(or.getProperty("leadsearchlink_id"))).isDisplayed()) 
		{
			driver.findElement(By.id(or.getProperty("leadsearchlink_id"))).click();

			// Switching to Child Window
			String parentWindow = driver.getWindowHandle();
			for(String childWindow : driver.getWindowHandles()) 
			{
				driver.switchTo().window(childWindow);
			}

			help.sleep(4);
			// Selecting Required fields
			List <WebElement> requiredFields = driver.findElement(By.id(or.getProperty("requiredfields_id"))).findElements(By.tagName(or.getProperty("servicename_tag")));
			List <String> fieldoptions = new ArrayList <String>();
			for(int i=1; i<requiredFields.size(); i++) 
			{
				fieldoptions.add(requiredFields.get(i).getText());
			}

			Reporter.log("<p>" +"The required field:" + requiredFields.get(0).getText());
			requiredFields.get(0).findElement(By.tagName(or.getProperty("searchbox_tag"))).click();
			driver.findElement(By.cssSelector(or.getProperty("filteroption_css"))).click();

			// Selecting a Category of Filter Options
			WebElement filterOption = driver.findElement(By.id(or.getProperty("filteroptioncontainer_id"))).findElements(By.className(or.getProperty("filteroption_class"))).get(5);

			String opt = filterOption.findElement(By.tagName(or.getProperty("filteroption_tag"))).getText();
			Reporter.log("<p>" +"Filter Option Selected:" + opt);
			// Selecting an option in a category in Filter Options and clicking on search button
			List <WebElement> option = filterOption.findElements(By.tagName(or.getProperty("servicename_tag")));
			Reporter.log("<p>" +"No.of options in " + opt + " List:" + option.size());

			Reporter.log("<p>" +"Option selected is:" + option.get(3).findElement(By.tagName(or.getProperty("resultmsg_tag"))).getText());
			option.get(3).findElement(By.tagName(or.getProperty("searchbox_tag"))).click();
			driver.findElement(By.id(or.getProperty("registerbutton_id"))).click();
			//help.sleep(5);
			help.waitforElement(20, By.id("example"));

			// Printing the Table displayed with required fields
			List <WebElement> fields = driver.findElement(By.tagName(or.getProperty("thead_tag"))).findElements(By.tagName(or.getProperty("th_tag")));
			List <String> fieldheads = new ArrayList <String>();
			for(int i=1; i<fields.size(); i++) 
			{
				fieldheads.add(fields.get(i).getText());
			}
			Reporter.log("<p>" + fieldheads);
			Reporter.log("<p>" + fieldoptions);

			if(fieldheads.equals(fieldoptions)) 
			{
				Reporter.log("<p>" +"The required fields selected is matched with the headers of the table.");
				Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());

				// Pagination
				//pagination();

				// No.of Entries per page
				//pageEntries();

				// Sorting
				sorting();

				// Search Box Validation
				driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(or.getProperty("searchbox_tag"))).sendKeys("Johnson");
				Reporter.log("<p>" +driver.findElement(By.id(or.getProperty("tablename_id"))).findElement(By.tagName(or.getProperty("leads_info_tag"))).getText());
				
			}
			else 
				Reporter.log("<p>" +"The required fields selected is not matched with the headers of the table.");

			// Switching to Parent Window
			driver.switchTo().window(parentWindow);
		} 
		else
			Assert.fail("No Link Found"); 
	}


  public void pageEntries() throws Exception   // Helper method to verify page entries in table
	{
	  //Verifying table availability
	  List<WebElement> ent1 = driver.findElements(By.name(or.getProperty("entries_example_length_name")));
	  if (ent1.size()==0) 
	  {
		Assert.fail("Table not available, call method after selecting any side tree menu");
	  }
	  
	  // Getting entries
	  List<WebElement> ent = driver.findElement(By.name(or.getProperty("entries_example_length_name"))).findElements(By.tagName(or.getProperty("option_tag")));
	  if (ent.size()==0) 
	  {
		Assert.fail("Entries not available");
	  }
	 
	  //Selecting entries one after another
	  for (int i = 0; i < ent.size(); i++)
	  {
		new Select(driver.findElement(By.name(or.getProperty("entries_example_length_name")))).selectByValue(ent.get(i).getText());
		  List<WebElement> tab = driver.findElement(By.id(or.getProperty("example_id"))).findElement(By.tagName(or.getProperty("tbody_name"))).findElements(By.tagName(or.getProperty("tr_tag")));
		 
		  int a = tab.size();
		  int b = Integer.parseInt(ent.get(i).getText());
		  
		  //Verifying entry and table size
		  if (a==b) 
		  { 
			Reporter.log("Table contents displaying as per Entries "+ent.get(i).getText()+"<p>");  
			pagination();
		  }
		  else
		  {
			  if (a<b & driver.findElement(By.className(or.getProperty("pagination_class"))).isDisplayed())
			  {
				  Reporter.log("Table contents displaying as per Entries "+ent.get(i).getText()+"<p>");
			  }
			  else
			  {
				  Reporter.log("Table contents NOT displaying as per Entries "+ent.get(i).getText()+"<p>");
			  }
		  }
		 sleep(1); 	  
	  } 
  }  
  
  public void pagination()  // Nested method page entries
  {
	  sleep(1);
	  //verifying for pagination to next page for all entries
	  WebElement pageNext= driver.findElement(By.id(or.getProperty("pagination_next")));
	  if(pageNext.isDisplayed()){
	  Reporter.log("<p>" + "pagination next button is present");
	  }else
	  {
	  Assert.fail("pagination button not present");
	  }
	  //clicking on the pagination next until it is disabled
	  while(!pageNext.getAttribute("class").equalsIgnoreCase("paginate_disabled_next"))
	  {
	  String s = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  pageNext.click();

	  String s1 = driver.findElement(By.cssSelector(or.getProperty("list_info"))).getText();
	  help.sleep(1);
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
	  help.sleep(1);
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
  
  
  
  public void changePassword(String email) throws Exception // Change password pass email id as argument
	{
		resultSet = statement.executeQuery("select password from crm_user where email_id='"+email+"' AND delete_status='no'"); 
		resultSet.next();
		String dat = resultSet.getString("password");
		//===Researcher is 3rdsheet in excels=====//
		int rows = sh3.getRows();
		String data;
		int col;
		//====getting current url====//
		String currenturl= driver.getCurrentUrl();
		//======Clicking changepassword in myaccount menu====//
		driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
		help.sleep(2);
		//==connecting database and retriveng password==//
		Reporter.log("<p>" + "old password is:" + dat);
		//=== clicking cancle button===//
		driver.findElement(By.id(or.getProperty("cancel1"))).sendKeys(Keys.ENTER);
		help.sleep(3);
		//===getting current url after cancle button====//
		String aftcancleurl = driver.getCurrentUrl();
		//comparing current url after cancle button// 
		if(aftcancleurl.equalsIgnoreCase(currenturl))
		{
		} else 
		{
			Assert.fail("Not successfully cancled change password page");
		}
		//======Clicking searchleads in leadsearch menu for change button====//
		driver.findElement(By.linkText(or.getProperty("changepassword"))).sendKeys(Keys.ENTER);
		sleep(1);
		//=== checking validations for change passwords===//
		for(int row = 1;row < rows;row++)
			 {
				col=0;
				if((driver.getCurrentUrl().equalsIgnoreCase("http://192.168.50.32:8080/leadcrm/login.jsp"))) 
				{
					Reporter.log("<p>" +"*************************");
				} 	else 
				{
					//=========checking oldpassword===//
					List<WebElement> li70 = driver.findElements(By.id(or.getProperty("oldpass")));
					if(li70.size()==0)
					{ 
						help.screenshot("oldpasswordcontainer");
						// Assert.fail("not a container");
					}
					data = sh3.getCell(col, row).getContents();
					driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(sh3.getCell(col,row).getContents());
					//==placing oldpassword in s1==//
					String s1 = driver.findElement(By.id(or.getProperty("oldpass"))).getAttribute("value");
					col++;
					sleep(1);
					//======new password=====//
					List<WebElement> newpass = driver.findElements(By.id(or.getProperty("newpass")));
					if(newpass.size()==0)
					{ 
						//====calling helper====//
						help.screenshot("newpassword container");
						Assert.fail("not a container");
					}
					else 
					{
						Reporter.log("<p>" +"New password container is avilable");
					}
					data = sh3.getCell(col, row).getContents();
			
					driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(sh3.getCell(col,row).getContents());
			
					//==placing new password in s2==//
					String s2 = driver.findElement(By.id(or.getProperty("newpass"))).getAttribute("value");
					col++;
					sleep(1);
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
					driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(sh3.getCell(col,row).getContents());
					//==Placing confirm password in s3==//
					String s3 = driver.findElement(By.id(or.getProperty("confirmpass"))).getAttribute("value");
					col++;
					Thread.sleep(2000);
					//====Clicking Change button====//
					driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
					//===This is for if my oldpassword and my currently changing password is same then handing that scenario===//
					if(s1.equalsIgnoreCase(dat)&&s2.equalsIgnoreCase(s1)&&s3.equalsIgnoreCase(s2)) 
					{
						String str ="000";
						help.sleep(2);
						driver.findElement(By.id(or.getProperty("oldpass"))).clear();
						driver.findElement(By.id(or.getProperty("newpass"))).clear();
						driver.findElement(By.id(or.getProperty("confirmpass"))).clear();
						driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat);
						driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(str);
						driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(str);
						//====Clicking Change button====//
						driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
						help.sleep(1);
					}
					if(driver.getCurrentUrl().equalsIgnoreCase("http://192.168.50.32:8080/leadcrm/login.jsp"))
					{
						//==setting old password==//
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						connection = DBConnection.getConnection();
						statement = connection.createStatement();
						resultSet = statement.executeQuery("select password from crm_user where email_id='"+email+"' AND delete_status='no'"); 
						resultSet.next();
			
						String dat1 = resultSet.getString("password");
						//Login
						driver.findElement(By.id(or.getProperty("username1"))).sendKeys(email);
						driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat1);
						driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();
						//====size of tree menu===//
			
						List<WebElement> list1 = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
						Reporter.log("<p>" + "Number Elements in List1 : " + list1.size());
			
						String user = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
						if(user.contains("Hi !"))
						//======= Expanding tree menu========//
						help.expand();
						//======Clicking changepassword in myaccount menu====//
						driver.findElement(By.linkText(or.getProperty("changepassword"))).click();
						help.sleep(5);
						//oldpassword//
						driver.findElement(By.id(or.getProperty("oldpass"))).sendKeys(dat1);
						//newpassword//
						driver.findElement(By.id(or.getProperty("newpass"))).sendKeys(dat);
					  //confirm password//
					  driver.findElement(By.id(or.getProperty("confirmpass"))).sendKeys(dat);
					  driver.findElement(By.id(or.getProperty("change1"))).sendKeys(Keys.ENTER);
					  sleep(3);
					  resultSet = statement.executeQuery("select password from crm_user where email_id='"+email+"' AND delete_status='no'"); 
					  resultSet.next();
					 // sleep(2);
					  String dat2 = resultSet.getString("password");
					  //Login
					  sleep(2);
					  driver.findElement(By.id(or.getProperty("username1"))).sendKeys(email);
					  driver.findElement(By.id(or.getProperty("password1"))).sendKeys(dat2);
					  driver.findElement(By.cssSelector(or.getProperty("loginbutton1"))).findElement(By.tagName("input")).submit();
					
					  //====size of tree menu===//
					  List<WebElement> lis = driver.findElement(By.id(or.getProperty("user_ids"))).findElements(By.tagName(or.getProperty("user_tagname")));
					
					  String use = driver.findElement(By.className(or.getProperty("user_Classname"))).getText();
					  if(use.contains("Hi !"))
					  Reporter.log("<p>" + "User password reset sucessfully");
					  driver.findElement(By.linkText("Logout")).click();
					}
					  // driver.findElement(By.linkText("Logout")).click()
				}
			}
	}


 
  
  public void searchtable() throws Exception  //Helper to perform search on your table
  {
	  List<WebElement> ent1 = driver.findElements(By.name(or.getProperty("entries_example_length_name")));
	  if (ent1.size()==0) 
	  {
	  Assert.fail("Table not available, call method after selecting any side tree menu");
	  }
	  sleep(3); 
	  String result = null;
	  WebElement w =driver.findElement(By.id(admin.getProperty("pagination_next")));
	  ArrayList<String> ar = new ArrayList<String>();
	  while(!w.getAttribute("class").equalsIgnoreCase(admin.getProperty("nextpage_disabled"))){
	  sleep(2);
	  List<WebElement> mylist = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
	  for(int i =0;i<mylist.size();i++){
	  ar.add(mylist.get(i).getText());
	  }
	  w.click();
	  }

	  List<WebElement> mylist1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
	  for(int i1 =0;i1<mylist1.size();i1++){
	  ar.add(mylist1.get(i1).getText());

	  }
	  int count2= 0;
	  int j = random(ar.size());
	  
	  List<WebElement> tlist = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
	  if(tlist.size()>1){
	  do{
		  driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(ar.get(j));
		  sleep(2);
		  result  = driver.findElement(By.cssSelector(admin.getProperty("list_info"))).getText();
	  List<WebElement> rowList = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableRowtag")));
	  for(int k = 0;k<rowList.size();k++){
	  int count = 0;
	  List<WebElement> dataList = rowList.get(k).findElements(By.tagName(admin.getProperty("tableData_tag")));
	  for(int p = 0;p<dataList.size();p++){
	  if(dataList.get(p).getText().contains(ar.get(j))){
	  count++;
	  }
	  }
	  if(count!=0){
	 // Reporter.log("<p>" +"No.of search results "+ count);
		  count2 = count2 + count;
	  }else{
	  Reporter.log("<p>" +"searching failed");
	  }
	  }
	 // Reporter.log("<p>" + "No. of results found"+ count2);
	  driver.findElement(By.id(admin.getProperty("pagination_next"))).click();
	  sleep(2);
	  }while(!driver.findElement(By.id(admin.getProperty("pagination_next"))).getAttribute("class").equals("paginate_disabled_next"));
	 // Reporter.log("<p>" +"Search results found with given search term");
	  String sresult[] = result.split(" ");
	  Reporter.log("<p>" +"No .of Search results with search key within table:: "+sresult[5]);
	  
	  //search with negative scenario
	  int row = 1;
	  int count1=0;
	  for(int q = 0;q<ar.size();q++){
	  count1=0;
	  if(sh6.getCell(5, row).getContents().equalsIgnoreCase(ar.get(q))){
	  count1++;
	  }
	  }
	  if(count1==0){
	  driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(admin.getProperty("SearchBox"))).clear();
	  sleep(3);
	  driver.findElement(By.id(or.getProperty("searchbox_id"))).findElement(By.tagName(admin.getProperty("SearchBox"))).sendKeys(sh6.getCell(12, row).getContents());
	  sleep(1);
	  List<WebElement> tlist1 = driver.findElement(By.tagName(admin.getProperty("table_tag"))).findElements(By.tagName(admin.getProperty("tableData_tag")));
	  //Reporter.log("<p>" +tlist1.size());
	  Reporter.log("<p>" +"==========when tried with negative scenario=========");
	  if(tlist1.size()==1){
	  Reporter.log("<p>" +"No results displayed with search key not in table");
	  }else{
	  Reporter.log("<p>" +"search box failed to show the results with the given search key");
	  }
	  }else{
	  Assert.fail("The string should mis-match with the list items when trying with negative scenario");
	  }

	  }else{
		  Reporter.log("No data present in the table to search");
	  }//finishing the search with the item in the displayed table

  }
	 } 	
