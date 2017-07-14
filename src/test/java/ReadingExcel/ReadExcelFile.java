	package ReadingExcel;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;
	import com.relevantcodes.extentreports.ExtentTest;
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.firefox.FirefoxDriver;

	import Keyword.GetScreenShot;
	import Keyword.UIOperation;

	public class ReadExcelFile {
		
		Workbook wb = null;
		Sheet sheet=null;
		List<Object> rtc=new ArrayList<Object>();
		Object[][] tcAndModes;
		UIOperation operation=null;
		
		public void runTestcases(String fpath, String fileName) throws IOException
		{
			
		    File file =	new File(fpath+fileName);
			FileInputStream inputStream = new FileInputStream(file);
			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			if(fileExtensionName.equals(".xlsx"))
			{
		       wb = new XSSFWorkbook(inputStream);
			}
			else if(fileExtensionName.equals(".xls"))
			{
				wb = new HSSFWorkbook(inputStream);
			}
			
		    sheet = wb.getSheet("Testcases");
			int rowcount=sheet.getLastRowNum()-sheet.getFirstRowNum();
			System.out.println("Total No of TestCases       : "+rowcount);
			tcAndModes=new Object[rowcount][2];
			for(int i=0;i<rowcount;i++)
			{
			     Row row=sheet.getRow(i+1);
			     String mode= row.getCell(2).toString();
			     tcAndModes[i][0]=row.getCell(1).toString();
			     tcAndModes[i][1]=row.getCell(2).toString();
			     if(mode.equalsIgnoreCase("Yes"))
			     {
			    	 rtc.add(row.getCell(1).toString());
			     }
			 }
			
		}
		
		public List<Object> getTestcaseNames()
		{
			return rtc;
		}
		
		public Object[][] getTestcaseAndModes()
		{
			return tcAndModes;
		}
		public void printRunTestCases()
		{
			int tccount=rtc.size();
			System.out.println("No of test cases should run : "+tccount+"\n");
			for(int i=0;i<tccount;i++)
			{
				System.out.println((i+1)+"."+rtc.get(i));
			}
		}
		
		public void close()
		{
			operation.closeDriver();
		}
		
		public String getPath() throws IOException
		{
			return operation.getScreenshotPath();
		}
		public void executeTest(String testcaseName,ExtentTest test) throws Exception
		{
			
			        sheet = wb.getSheet(testcaseName.trim());   
					int rowCount = sheet.getPhysicalNumberOfRows();
					System.out.println(rowCount);
					//if(rowCount==6)
						//rowCount++;
					    for (int i = 0; i < rowCount; i++) 
					    {
					        Row row = sheet.getRow(i);
					        if(row.getCell(0).toString().startsWith("TC_"))
					        {
					        	
					                operation.perform(
					                		row.getCell(1).toString(), 
					                		row.getCell(2).toString(),
					                        row.getCell(3).toString(), 
					                        row.getCell(4).toString()
					                        );
					          }
					        else{
					                System.out.println("New Testcase->"+testcaseName +" Started");
					                operation= new UIOperation(new FirefoxDriver(),testcaseName,test);
					             }
					      }
	    }
		
		public void executeDTTest(String testcaseName)
		{
			
		}
		public int getRowCount(String sheetName)
		{
			int row=wb.getSheet(sheetName).getLastRowNum();
			row=row+1;
			return row;
			
		}
		
		public String getData(int sheetNumber,int row,int column)
		{
			sheet=wb.getSheetAt(sheetNumber);
			String data=sheet.getRow(row).getCell(column).getStringCellValue();
			
			return data;
		}
		

	}


