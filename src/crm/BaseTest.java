package src.crm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import com.nexiilabs.dbcon.DBConnection;

import src.testUtils.Helper;

public class BaseTest 
{
  public static WebDriver driver = null;
  public static Properties config, or, admin, researcher, bdm, bde, mgmt  = null;
  public static FileInputStream fis1, fis2, fis3, fis4, fis5, fis6, fis7, fis8, fis9 = null;
  public static Workbook w = null;
  public static Sheet sh0, sh1, sh2, sh3, sh4, sh5, sh6, sh7,sh8 = null;
  public static Logger log = null;
  public static Helper help = null;
  public static File f = null;
  public static Connection connection = null;
  public static Statement statement;
  public static ResultSet resultSet;
  
  @BeforeSuite
  public void beforesuite() throws Exception
  {
	  help = new Helper();
	  
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
	  sh7 = w.getSheet(7); // Side tree menu
	  sh8 = w.getSheet(8);
	  
	  admin = new Properties();
	  fis4 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\Admin.properties");
	  admin.load(fis4);
	  
	  researcher = new Properties();
	  fis5 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\Researcher.properties");
	  researcher.load(fis5);
	  
	  bdm = new Properties();
	  fis6 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\BDM.properties");
	  bdm.load(fis6);
	  
	  bde = new Properties();
	  fis7 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\BDE.properties");
	  bde.load(fis7);
	  
	  mgmt = new Properties();
	  fis8 = new FileInputStream(System.getProperty("user.dir")+"\\src\\config\\Management.properties");
	  mgmt.load(fis8);
	  
	  Class.forName("com.mysql.jdbc.Driver").newInstance();
	  connection = DBConnection.getConnection();
	  statement = connection.createStatement();
	   
  }
  
 }
