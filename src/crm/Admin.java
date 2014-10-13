package crm;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import testUtils.Helper;

public class Admin extends Helper
{
  @Test
  public void f() 
  {
	  log.debug("test");
	  driver = new FirefoxDriver();
	  driver.get(config.getProperty("url"));
  }
}
