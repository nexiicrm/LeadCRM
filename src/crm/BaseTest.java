package crm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import testUtils.Helper;

public class BaseTest 
{
  public static WebDriver driver = null;
  public static Properties config,or = null;
  public static FileInputStream fis1,fis2,fis3 = null;
  public static Workbook w = null;
  public static Sheet sh0, sh1, sh2, sh3, sh4, sh5, sh6 = null;
  public static Logger log = null;
  public static Helper help = null;
  public static File f = null;
  
  @BeforeSuite
  public void beforesuite() throws Exception
  {
	  log = Logger.getLogger("devpinoyLogger");
	  
	  log.debug("Loading Configuration files");
	  config = new Properties();
	  fis1 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\configuration.properties");
	  config.load(fis1);
	  
	  log.debug("Loading Object Repository files");
	  or = new Properties();
	  fis2 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\objectrepository.properties");
	  or.load(fis2);
	  
	  log.debug("Loading Test data");
	  fis3 = new FileInputStream(System.getProperty("user.dir")+"\\src\\testData\\TestData.xls");
	  w = Workbook.getWorkbook(fis3);
	  sh0 = w.getSheet(0); // Login sheet
	  sh1 = w.getSheet(1); // Change_password sheet
	  sh2 = w.getSheet(2); // BDM Sheet
	  sh3 = w.getSheet(3); // Researcher sheet
	  sh4 = w.getSheet(4); // BDE sheet
	  sh5 = w.getSheet(5); // Management sheet
	  sh6 = w.getSheet(6); // Admin sheet
	  
	  help = new Helper();
	   
  }
  
}
