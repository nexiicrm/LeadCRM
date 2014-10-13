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
  public static Sheet sh = null;
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
	  sh = w.getSheet(0);
	  
	  help = new Helper();
	   
  }
  
}
