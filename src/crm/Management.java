package src.crm;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import src.testUtils.Helper;

public class Management extends Helper {
String randomLead;

	@Test
	public void a_TS_57_TC001login() throws Exception 
	{
		//	logging into the site
		String user = driver.findElement(By.className(mgmt.getProperty("user_Classname"))).getText();
		if(user != null)
		{
			help.sleep(2);
			if(user.contains("Hi ! Management"))
			{
				Reporter.log("<p>" + "User Logged in as:" + user);
			}
		}
		else
		{
			Assert.fail("logged in as other user");
		}
		
		//	Side tree menu validation
		help.sidetreemenuverify(4);
	}

	@Test
	public void b_TS_57_TC002expand() throws Exception
	{
		//	Expanding and collapsing tree in the left pane of page
		help.expand();
		help.collapse();
		Reporter.log("<p>" + "######Expansion and collapsing of tree menu done successfully######");
	}
 
	@Test
	public void c_TS_58_TC001PSearch() throws Exception
	{
		help.expand();
		help.sleep(2);
		
		//	All Proposals validation and clicking on the link 
		String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
		if(str.equals("All Proposals"))
		{
			driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
		}
		else
		{
			Assert.fail("All propoals cant be clickable");
		}
		help.sleep(2);
		
		//	search entries of all proposals
		help.searchtable();
	}
 
	@Test
	public void d_TS_58_TC004PDrop() throws Exception
	{
		help.expand();
		help.sleep(2);
		
		//	Clicking on all proposals 
		String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
		if(str.equals("All Proposals"))
		{
			driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
		}
		else
		{
			Assert.fail("All propoals cant be clickable");
		}
		help.sleep(2);
		
		//	drop down and pagination of all proposals
		help.pageEntries();
	}
	
	@Test
	public void e_TS_58_TC005PSortStatus() throws Exception
	{
		help.expand();
		help.sleep(2);
		
		//	clicking on all proposals
		String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
		if(str.equals("All Proposals"))
		{
			driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
		}
		else
		{
			Assert.fail("All propoals cant be clickable");
		}
		help.sleep(2);
		
		//	sorting of all proposals
		help.sorting();
		
		//	lead status of all proposals
		String page = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries before searching  : " + page);
		search("prospect"); 
		help.sleep(3);
		String pagecheck = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries after searching : " +pagecheck);
		if(page.equals(pagecheck))
		{
			Reporter.log("<p>" + "entries doesnt change the status is prospect");
		}
		else
		{
			Assert.fail("entries are changing the status is not prospect in all leads");
		}
	}

	@Test
	public void f_TS_58_TC006PTrackit() throws Exception
	{
		help.expand();
		
		// Clicking on all proposals 
		String str = driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).getText();
		if(str.equals("All Proposals"))
		{
			driver.findElement(By.id(mgmt.getProperty("allproposals_id"))).click();
		}
		else
		{
			Assert.fail("All propoals cant be clickable");
		}
		
		trackIt();
		
	}

	@Test
	public void g_TS_59_TC001LSearch() throws Exception
	{
		help.expand();
		
		//	All lost competition validation and clicking it 
		if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
		{
			driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
		}
		else
		{
			Assert.fail("all lost comepetition button is not available");
		}
		help.sleep(1);
		
		//	Search entries of all lost competition
		help.searchtable();
	}
	@Test
	public void h_TS_59_TC004LDrop() throws Exception
	{
		help.expand();
		
		//	clicking on all lost competition link
		if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
		{
			driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
		}
		else
		{
			Assert.fail("all lost comepetition button is not available");
		}
		help.sleep(1);
		
		//	drop down validation and pagination of all lost competition
		help.pageEntries();
	}
	
	@Test
	public void i_TS_59_TC005LSortStatus() throws Exception
	{
		help.expand();
		
		//	Clicking on all lost competition
		if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
		{
			driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
		}
		else
		{
			Assert.fail("all lost comepetition button is not available");
		}
		help.sleep(1);
		
		//	Sorting of all lost competition page
		help.sorting();
		
		//	Lead status validation of all lost competition
		String page = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries before searching  : " + page);
		search("Lost Competition"); 
		help.sleep(3);
		String pagecheck = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries after searching : " +pagecheck);
		if(page.equals(pagecheck))
		{
			Reporter.log("<p>" + "entries doesnt change, the status is Lost Competition");
		}
		else
		{
			Assert.fail("entries are changing, the status is not Lost Competition");
		}
	}	
 
	@Test
	public void j_TS_59_TC006LTrackit() throws Exception
	{
		help.expand();
		
		//	Clicking on all lost competition link
		if(driver.findElement(By.id(mgmt.getProperty("alllost_id"))).getText().equals("All Lost Competition"))
		{
			driver.findElement(By.id(mgmt.getProperty("alllost_id"))).click();
		}
		else
		{
			Assert.fail("all lost comepetition button is not available");
		}
		help.sleep(1);   
		
		trackIt();
	}

	@Test
	public void k_TS_60_TC001CSearch() throws Exception
	{
		help.expand();
		
		//	Validation and clicking all customers link 
		if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
		{
			driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
		}
		else
		{
			Assert.fail("all customers link cannot be clickable");
		}
		help.sleep(1);
		
		//	Search entries of all customers
		help.searchtable(); 
	}
 
	@Test
	public void l_TS_60_TC004CDrop() throws Exception
	{
		help.expand();
		
		// clicking on all customers link
		if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
		{
			driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
		}
		else
		{
			Assert.fail("all customers link cannot be clickable");
		}
		help.sleep(1);
		
		//	Drop down validation and pagination of all customers
		help.pageEntries();
	}
 
	@Test
	public void m_TS_60_TC005CSortStatus() throws Exception
	{
		help.expand();
		
		//	Clicking on all customers link
		if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
		{
			driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
		}
		else
		{
			Assert.fail("all customers link cannot be clickable");
		}
		help.sleep(1);
		
		//	Sorting of all customers page
		help.sorting();
		
		//	lead status validation of all customers page
		String page = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries before searching  : " + page);
		search("Customer"); 
		help.sleep(3);
		String pagecheck = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries after searching : " +pagecheck);
		if(page.equals(pagecheck))
	    {
			Reporter.log("<p>" + "entries doesnt change, the status is Customer");
		}
		else
		{
			Assert.fail("entries are changing, the status is not Customer");
		}  
	}
	 
	@Test
	public void n_TS_60_TC006CTrackit() throws Exception
	{
		help.expand();
		help.sleep(2);
		
		//	Clicking on all customers link
		if(driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).getText().equals("All Customers"))
		{
			driver.findElement(By.id(mgmt.getProperty("allcustomers_id"))).click();
		}
		else
		{
			Assert.fail("all customers link cannot be clickable");
		}
		
		trackIt();
	}
 
	@Test
	public void o_TS_61_TC001QSearch() throws Exception
	{
		help.expand();
		
		//	Validation and clicking  all Quotes link
		if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
		{
			driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
		}
		else
		{
			Assert.fail("all quotes link cannot be clickable");
		}
		help.sleep(1);
		
		//	Search entries of all quotes page
		help.searchtable();
	}
 
	@Test
	public void p_TS_61_TC004QDrop() throws Exception
	{
		help.expand();
		
		//	Clicking on all Quotes link
		if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
		{
			driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
		}
		else
		{
			Assert.fail("all quotes link cannot be clickable");
		}
		help.sleep(1);
		
		//	Drop down validation and pagination of all quotes
		help.pageEntries();
	}
 
	@Test
	public void q_TS_61_TC005QSortStatus() throws Exception
	{
		help.expand();
		
		//	Clicking on All Quotes link 
		if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
		{
			driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
		}
		else
		{
			Assert.fail("all quotes link cannot be clickable");
		}
		help.sleep(1);
		
		//	Sorting of all quotes page
		help.sorting();
		
		//	 Prospect type validation of all quotes page
		String page = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries before searching  : " + page);
		search("Quote"); 
		help.sleep(3);
		String pagecheck = driver.findElement(By.id("example_info")).getText();
		Reporter.log("<p>" + "entries after searching : " +pagecheck);
		if(page.equals(pagecheck))
		{
			Reporter.log("<p>" + "entries doesnt change, the prospect type is Quote");
		}
		else
		{
			Assert.fail("entries are changing, the prospect type is not Quote");
		}    
	}

	@Test
	public void r_TS_61_TC006QTrackit() throws Exception
	{
		help.expand();
		help.sleep(2);
		
		// 	Clicking on all Quotes link
		if(driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).getText().equals("All Quotes"))
		{
			driver.findElement(By.id(mgmt.getProperty("allquotes_id"))).click();
		}
		else 
		{
			Assert.fail("all quotes link cannot be clickable");
		}
		
		trackIt();
				
	}
  
	@Test
	public void s_TS_62_TC001Leads() throws Exception
	{
		help.expand();
		
		// 	Search leads functionality from helper
		help.searchLead();
		Reporter.log("<p>" + "######Done with validation of Search leads page######");
	} 	


	
    //	Method for Search 
	public static void search(String keyword) 
	{
		driver.findElement(By.id(mgmt.getProperty("searching_id"))).findElement(By.tagName(mgmt.getProperty("allproposals_tagName"))).sendKeys(keyword);
	}
	
	//	Method for validating search box
	public void searchBox()
	{	
		List<WebElement> table = driver.findElement(By.id(mgmt.getProperty("search_idd"))).findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
		if(table.size()>1)
		{	
			WebElement res = table.get(random(table.size()));
			List<WebElement> tdlis = res.findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		    if(tdlis.size()>1)
		    {
		    	randomLead = tdlis.get(0).getText() + " " + tdlis.get(1).getText() + " " +tdlis.get(2).getText();
		    	Reporter.log("<p>" + "The particular Lead is : " + randomLead);
		    	search(randomLead);
		    	help.sleep(2);
		    }
		    else
		    {
		    	Assert.fail("there are no elements to pick lead id and name");
		    }
 		}
		else
		{
			Reporter.log("<p>" + "There is no data present in the table");
		}
	}
	
    // method for track it validation 
	public void trackIt()
	{
		help.sleep(2);
		
		searchBox();
		
		//	Data validation of all proposals container
		List<WebElement> ls =driver.findElement(By.cssSelector(mgmt.getProperty("allproposalsrow_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
		if(ls.size()>=0)
		{
			Reporter.log("<p>" + "No of columns in the table are :" + ls.size());
		}
		else
		{
			Assert.fail("there are no columns in the table ");
		}
		ArrayList<String> ar = new ArrayList<String>();
		if(ls.size()>=0)
		{
			for (int i=0;i<ls.size();i++)
			{
				String s1= ls.get(i).getText();
				ar.add(s1);  
			}
			Reporter.log("<p>" + "Array before clicking on trackit button is: " + ar);
		}
		else
		{
			Assert.fail("No elements in array container");
		}
		
		// clicking track it button 
		help.sleep(3);
		String str22 = driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).getText();
		if(str22 != null)
		{
			if(str22.equals("Track It"))
			{
				driver.findElement(By.className(mgmt.getProperty("allproposals_className"))).click(); 
			}
			else
			{
				Assert.fail("Trackit cannot be clickable");
			}
			help.sleep(2);
		}
		else
		{
			Assert.fail("Table size is zero");
		}
			
		
		//	Data validation of page after track it is clicked in all proposals
		List<WebElement> ls2 =driver.findElement(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName")));
		Reporter.log("<p>" + "No of rows in track it lead details of table " + ls2.size());
		ArrayList<String> ar1 = new ArrayList<String>();
		if(ls2.size()>=0)
		{
			for (int i=0;i<ls2.size();i++)
			{
				List<WebElement> s1= ls2.get(i).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName")));
				if(s1.size()>=0)
				{
					for(int j=0;j<s1.size();j++)
					{
						String s2 = s1.get(j).getText();
						ar1.add(s2); 
					} 
				}	
			}
		}
		else
		{
			Assert.fail("there are no rows in trackit lead details");
		}	
		Reporter.log("<p>" + "array size after trackit button is clicked:" + ar1.size());
		Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(0));
		Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(1));
		Reporter.log("<p>" + "array element after trackit button is clicked: " + ar1.get(19));
		if(ar1.get(0).contains(ar.get(0)))
		{
			if(ar1.get(1).contains(ar.get(1)))
			{
				if(ar1.get(19).contains(ar.get(4)))
				{	 	 
					Reporter.log("<p>" + "Data is matching exactly ");
				}
			}
		}	
		else
		{
			Assert.fail("Data doesnt match exactly ");
		}
		
		//	To get the status of all proposals after track it is clicked
		String strt=  driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(1).getText();
		if(strt != null) 
		{
			Reporter.log("<p>" + strt);
			help.sleep(2);
		}
		else
		{
			Assert.fail();
		}
		
		//	To check link is present to download a file in all proposals after track it is clicked
		String strtt = driver.findElements(By.tagName(mgmt.getProperty("allproposalsbody_tagName"))).get(3).findElements(By.tagName(mgmt.getProperty("allproposalsrow1_tagName"))).get(1).findElements(By.tagName(mgmt.getProperty("allproposalscol_tagName"))).get(2).getText();
		Reporter.log("<p>" + strtt);	
		Reporter.log("<p>" + "tag name for the file is: "+ driver.findElement(By.tagName("a")).getTagName());
		String s1 = driver.findElement(By.tagName("a")).getTagName();
		if(s1.contentEquals("a"))
		{
			Reporter.log("<p>" + "File can be downloaded");
		}
		else
		{
			Assert.fail("File cannot be downloaded");
		}
	}
	
	@BeforeMethod
	public void beforeMethod() throws Exception 
	{
		help.browser();
		help.maxbrowser();
		driver.get(config.getProperty("url"));	  
		help.browsererror();  
		help.login(config.getProperty("Muser"),config.getProperty("Mpass"));
	}	

	@AfterMethod
	public void afterMethod() 
	{
		driver.quit();
	}
 	}