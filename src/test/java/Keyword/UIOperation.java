package Keyword;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UIOperation {
	WebDriver driver;
	Logger log;
	ExtentTest test;	
	String tcname;
	private enum UIO { CLICK,SETTEXT,GOTOURL,GETTEXT,VERIFY,className,cssSelector,id,name,xpath; }
    public UIOperation(WebDriver driver,String testcaseName,ExtentTest test) {
    	tcname=testcaseName;
        this.driver = driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        log=Logger.getLogger("UIOperation");
        PropertyConfigurator.configure("D:\\PERSONAL-DOC\\Selenium-Trials\\MavenKeywordFramework\\log4j.properties");
        log.info("Testcase "+testcaseName+" is started to execute..");
        log.info("Browser Opened");
        this.test=test;
    }
    
    public void perform(String keyword,String locatorType,String locatorValue,String value) throws Exception {
       
    	System.out.println(locatorType+"\t\t"+locatorValue);
    	
    	String keyword1=keyword;
    	UIO ui=UIO.valueOf(keyword1);
        switch (ui) {
        case CLICK:
            //Perform click
            driver.findElement(this.getElementLocator(locatorType, locatorValue.trim().toString())).click();
            log.info("Clicked : "+locatorValue.trim());
            test.log(LogStatus.INFO,"Clicked : "+locatorValue.trim());
            break;
        case SETTEXT:
            //Set text on control
            driver.findElement(this.getElementLocator(locatorType, locatorValue)).sendKeys(value);
            log.info("Entered Text in Locator "+locatorValue.trim());
            test.log(LogStatus.INFO,"Entered Text in Locator "+locatorValue.trim());
            break;
        case GOTOURL:
            //Get url of application
            driver.navigate().to(value.trim());
            log.info("Navigated to url "+value.toString());
            test.log(LogStatus.INFO,"Navigated to url "+value.toString());
            break;
        case GETTEXT:
            //Get text of an element
            driver.findElement(this.getElementLocator(locatorType, locatorValue.trim().toString())).getText();
            break;
       /* case "QUIT":
        	driver.quit();
        	log.info("Browser Closed");
        	test.log(LogStatus.INFO,"Browser Closed");
        	break;*/
        case VERIFY:
        	String actual=driver.findElement(By.xpath(locatorValue)).getText();
        	System.out.println(keyword+"\t\t"+value);
        	String expected=value.trim();
        	log.info("Verifying Text : "+value.trim());
            test.log(LogStatus.INFO,"Verifying Text : "+value.trim());
        	Assert.assertEquals(actual, expected);
        	log.info("Verified Text : "+value.trim());
            test.log(LogStatus.INFO,"Verified Text : "+value.trim());
        	break;
        default:
            break;
        }
        
    	
    }
    
    public String getScreenshotPath() throws IOException
    {
    	 String spath=GetScreenShot.capture(driver, tcname);
    	 return spath;
    }
	public void closeDriver()
	{
		log.info("Browser Closed");
    	test.log(LogStatus.INFO,"Browser Closed");
		driver.quit();
	}
	public By getElementLocator(String locatorType,String locatorValue)
	{
		String ltype1=locatorType;
    	UIO ui=UIO.valueOf(ltype1);
		switch(ui)
		{
		case className:
			 return By.className(locatorValue);
		case cssSelector:
			 return By.cssSelector(locatorValue);
		case id:
			 return By.id(locatorValue);
		case name:
			 return By.name(locatorValue);
		case xpath:
			 return By.xpath(locatorValue);
		default:
			 return By.id(locatorValue);
	   }
	}


}
