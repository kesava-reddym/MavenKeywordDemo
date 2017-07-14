package myPack;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ReadingExcel.ReadExcelFile;

public class ExecuteTestCases {
  
	private static int count;
	  Object[][] obj; 
	  ReadExcelFile ref;
	  ExtentReports extent;
	  ExtentTest test;
	  
	  @BeforeSuite
	  public void init() throws IOException
	  {
		    ref=new ReadExcelFile();
			ref.runTestcases("D:\\", "Testcases2.xlsx");
			ref.printRunTestCases();
			obj=ref.getTestcaseAndModes();
			count=-1;
			extent=new ExtentReports("./test-output/MyReport.html",true);
			extent.addSystemInfo("Testing Environment", "QA")
			.addSystemInfo("User Name", "Kesava Reddy M");
			extent.loadConfig(new File("./extent-config.xml"));
			
	  }
	  
	  public boolean getMode()
	  {
		  if(obj[count][1].toString().equalsIgnoreCase("No"))
			  return true;
		  else 
			  return false;
	  }
	  
	  @BeforeMethod
	  public void checkEnvironent()
	  {
		  count++;  
	  }
	  @Test(priority=0)
	  public void tc_001() throws Exception {
		  
		  if(getMode())
		  {
			  test=extent.startTest(obj[count][0].toString());
			  test.log(LogStatus.SKIP, "Testcase execution status is SKIP");
			  throw new SkipException("Skipped because runmode is NO");
		  }
		  else
		  {
		  test=extent.startTest(obj[count][0].toString());
		  Reporter.log(obj[count][0].toString());
		  ref.executeTest(obj[count][0].toString(), test);
		
		  }
		  
	  }
	  
	  @Test(priority=1)
	  public void tc_002() throws Exception {
		  
		  if(getMode())
		  {
			  test=extent.startTest(obj[count][0].toString());
			  test.log(LogStatus.SKIP, "Testcase execution status is SKIP");
			  throw new SkipException("Skipped because runmode is NO");
		  }
		  else
		  {
		  test=extent.startTest(obj[count][0].toString());
		  Reporter.log(obj[count][0].toString());
		  ref.executeTest(obj[count][0].toString(), test);
		  
		  }
	  }
	  
	  @Test(priority=2)
	  public void tc_003() throws Exception {
		  
		  if(getMode())
		  {
			  test=extent.startTest(obj[count][0].toString());
			  test.log(LogStatus.SKIP, "Testcase execution status is SKIP");
			  throw new SkipException("Skipped because runmode is NO");
		  }
		  else
		  {
		  test=extent.startTest(obj[count][0].toString());
		  Reporter.log(obj[count][0].toString());
		  ref.executeTest(obj[count][0].toString(), test);
		  
		  }
	  }
	 /* @Test(priority=3,dataProvider="getData")
	  public void tc_004(String username,String password)
	  {
		  if(getMode())
		  {
			  test=extent.startTest(obj[count][0].toString());
			  test.log(LogStatus.SKIP, "Testcase execution status is SKIP");
			  throw new SkipException("Skipped because runmode is NO");
		  }
		  else
		  {
		    test=extent.startTest(obj[count][0].toString());
		  }
	  }
	  
	  @DataProvider 
	  public Object[][] getData()
	  {
		  Object[][] data;
		  int rows=ref.getRowCount("DTGmail Login");
		  data=new Object[rows-1][2];
		  
		  for(int i=0;i<rows-1;i++)
		  {
			  data[i][0]=ref.getData(4, i+1, 0);
			  data[i][1]=ref.getData(4, i+1, 1);
			  
		  }
		  return data;
	  }
	  */
	  
	  @AfterMethod
	  public void tearDown(ITestResult result) throws IOException
	  {
		  if(ITestResult.SKIP==result.getStatus())
		  {
			  test.log(LogStatus.SKIP,obj[count][0].toString()+"\t"+result.getName());
			  test.log(LogStatus.SKIP, result.getThrowable());
			  
		  }
		  else if(ITestResult.FAILURE==result.getStatus())
		  {
			  test.log(LogStatus.FAIL,obj[count][0].toString()+"\t"+result.getName());
			  String screenShotPath =ref.getPath();
	          test.log(LogStatus.FAIL, "Snapshot below: "+test.addScreenCapture(screenShotPath));
			  test.log(LogStatus.FAIL, result.getThrowable());
			  
		  }
		  else
		  {
			  test.log(LogStatus.PASS,obj[count][0].toString()+"\t"+result.getName());
			  String screenShotPath =ref.getPath();
	          test.log(LogStatus.PASS, "Snapshot below: "+test.addScreenCapture(screenShotPath));
		  }
		  ref.close();
		  extent.endTest(test);
	  }
	  
	  @AfterSuite
	  public void endTest()
	  {
		  extent.flush();
		  extent.close();
		  WebDriver driver=new FirefoxDriver();
		  driver.manage().window().maximize();
		  driver.get(System.getProperty("user.dir")+"\\test-output\\MyReport.html");
	  }
  }
