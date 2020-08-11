package LibraryFiles;

import java.io.IOException;
import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import org.testng.internal.TestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ObjectRepository.CancelPage;
import ObjectRepository.HomePage;
import ObjectRepository.IDPPage;
import ObjectRepository.LoginPage;
import ObjectRepository.NonPickUpDate;
import ObjectRepository.ReportsPage;
import TestScripts.BaseSuiteFile;
import TestScripts.TC02_UploadCsvFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import junit.framework.Assert;

public class ReUsableLibrary {
	public static String propFilePath = System.getProperty("user.dir") + "\\GlobalSettings.properties";
	public static WebDriver driver;
	public static int colIndex = 0;
	public static String value;
	private static int waittime = 3000;
	private static int timecount = 0;
	private int maxtimecount = 90000;
	private int maxtimecounter = 120;
	public static ExtentReports extent;
	public static ExtentTest logger;
	private static SimpleDateFormat strScreenShotName = new SimpleDateFormat("MMddyy_HHmmss");
	protected SoftAssert sassert = new SoftAssert();
	public static String browser;
	public int noOfMissingFields;
	public String [] missingFieldsInRe= new String[8]; 
	public String caseIdCreated;
	
	public static void OpenWDInstance() throws IOException {
		try {
			browser = getElementFromPropFile("Browser");
			if (browser.equals("Firefox")) {
				String GeckoDriverPath = getElementFromPropFile("GeckoDriverPath");
				System.setProperty("webdriver.gecko.driver", GeckoDriverPath);
				driver = new FirefoxDriver();
			} else if (browser.equals("IE")) {
				String ieDriverPath = getElementFromPropFile("InternetExplorerDriverPath");
				System.setProperty("webdriver.ie.driver", ieDriverPath);
				driver = new InternetExplorerDriver();
			} else if (browser.equals("Chrome")) {
				String userDir=System.getProperty("user.dir");
				String downloadFilepath = userDir + "\\DownloadCases\\";
				System.out.println(downloadFilepath);
				createDir(downloadFilepath);
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			       chromePrefs.put("profile.default_content_settings.popups", 0);
			       chromePrefs.put("download.default_directory", downloadFilepath);
			       ChromeOptions options = new ChromeOptions();
			       options.setExperimentalOption("prefs", chromePrefs);
			       options.addArguments("--test-type");
			       options.addArguments("--disable-extensions"); //to disable browser extension popup
			       options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			       
				String chromeDriverPath =userDir+"//SupportingSoftware//chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				
				driver = new ChromeDriver(options);
			} else if (browser.equals("PhantomJS")) {
				String phantomJSexePath = getElementFromPropFile("PhantomJSPath");
				File file = new File(phantomJSexePath);
				System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
				try {
					driver = new PhantomJSDriver();
				} catch (UnreachableBrowserException e) {
					System.out.println(e);
					driver = new PhantomJSDriver();
				}
			}
			driver.manage().window().maximize();
			System.out.println("Created Driver Instance and Launched Browser: " + browser);
			extent.addSystemInfo("Browser", browser);
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed To Create Driver Instance " + browser + " " + e.toString());
			Assert.fail("Failed To Create Driver Instance " + browser + " " + e.toString());
		}
	}

	public static void launchApplication() {
		if (browser != null)
			logger.log(LogStatus.INFO, "Launched Browser " + browser + " Successfully ");
		String appURL;
		try {
			appURL = getElementFromPropFile("TeApplicationDt3Url");
			driver.get(appURL);
			customSleep();
			String body = driver.findElement(By.xpath("/html/body")).getText();
			Assert.assertFalse("Failed To Navigate To Application URL", body.contains("Reload") || body.contains("404"));
			logger.log(LogStatus.INFO, "Navigated to Application URL Successfully " + appURL);
		} catch (IOException e) {
			logger.log(LogStatus.FAIL, "Failed To Navigate To Application URL" + e.toString());
			Assert.fail("Failed To Navigate To Application URL" + e.toString());
		}
	}
	public void ExceptionRequestsClick(String caseID) {
		WebElement  created=null;
		try
		{
		  created = driver.findElement(By.xpath("//a[contains(text(),'"+caseID+"')]"));
		}
		catch(Exception e)
		{
		}
		if (created ==null)
		{
			driver.findElement(CancelPage.exceptionRequests).click();
		    
		}
	}
	public void ExceptionMessageCheckForIdpForDate(String file,String user, String application) {
		String fileName=file;
		String userName=user;
		String password="rules";
		String applicationName=application;
		TC02_UploadCsvFile oTC02_UploadCsvFile= new TC02_UploadCsvFile();
		try {
			oTC02_UploadCsvFile.UploadFile(fileName,userName,password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		String caseID=RetrieveCaseId(applicationName);
		this.caseIdCreated=caseID;
		custom3MSleep();
		custom2MSleep();
		custom3MSleep();
	    driver.navigate().refresh();
		System.out.println(caseID);
		logger.log(LogStatus.INFO, "Case ID created is"+caseID);
		//System.out.println(cellDataList);
		ExceptionRequestsClick(caseID);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+caseID+"')]")));
		WebElement createdCase= driver.findElement(By.xpath("//a[contains(text(),'"+caseID+"')]"));
		createdCase.click();
		logger.log(LogStatus.INFO, "Case created which moved to exception has been clicked");
		System.out.println("Case created which moved to exception has been clicked");
		custom6Sleep();
		List<WebElement> excepCaseStatus = null;
		try
		{
			  excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
		}
		catch(Exception e)
		{
		}
		if (excepCaseStatus ==null)
		{
			driver.findElement(CancelPage.caseSummary).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-attribute-name='Status']/div/span")));
			excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
			logger.log(LogStatus.INFO, "exception status has been checked for all the child cases");
		}
		for(WebElement e:excepCaseStatus) {
			Assert.assertEquals("Pending-Exception", e.getText().trim());
		}
		String[] excepMessageArray = new String[8];
		String[] excepMessageArrayExpected = new String[]{"IDP date should be within 2 and 90 calendar days","Requested IDP date is non pick-up date","Shipment date should always greater than current date","Requested IDP date is in NPU Calendar","You do not have enough capacity for this booking","Minimum check for current group failed","This Order has a Credit Block"};
		List<WebElement> exceptionMessage = (driver.findElements(CancelPage.cancelCaseExceptionMessageSymbol));
		Actions ac = new Actions(driver);
		int z=0;
		for(WebElement excepMsg:exceptionMessage) {
			 ac.moveToElement(excepMsg).build().perform();
			 custom6Sleep();
			wait1.until(ExpectedConditions.presenceOfElementLocated(CancelPage.cancelCaseExceptionMessage)) ;
			excepMessageArray[z]= driver.findElement(CancelPage.cancelCaseExceptionMessage).getText();
			System.out.println(excepMessageArray[z]);
			
			if(excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[0])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[1])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[2])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[3])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[4])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[5])||excepMessageArray[z].equalsIgnoreCase(excepMessageArrayExpected[6])) {
				
				 Assert.assertTrue("Exception message of expected and actual are same", true);
			}
			else
				Assert.fail("exception Messages are not equal");
			z++;
		}
	
	}
	public void ClickReports() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		wait.until(ExpectedConditions.presenceOfElementLocated(HomePage.reports));
		driver.findElement((HomePage.reports)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(ReportsPage.cancellationReports));
		driver.findElement((ReportsPage.cancellationReports)).click();
		custom6Sleep();
		Thread.yield();
		wait.until(ExpectedConditions.elementToBeClickable(ReportsPage.showAllCancellationReports));
		WebElement ele = driver.findElement((ReportsPage.showAllCancellationReports));
		
		executor.executeScript("arguments[0].click();", ele);
		custom6Sleep();
		Thread.yield();
		driver.switchTo().alert().accept();
		custom6Sleep();
	}
	public void ExceptionMessageCheckForCancel(String file,String user, String application) {
	String fileName=file;
	String userName=user;
	String password="rules";
	String applicationName=application;
	TC02_UploadCsvFile oTC02_UploadCsvFile= new TC02_UploadCsvFile();
	try {
		oTC02_UploadCsvFile.UploadFile(fileName,userName,password);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	logger.log(LogStatus.INFO, "CSV file with exception has been uploaded");
	try {
		System.out.println(fileName);
		ReadAndParseDataFromCsv(fileName);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		for(int p=0;p<missingFieldsInRe.length;p++) {
			System.out.println(missingFieldsInRe[p]);
		}
		
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		String caseID=RetrieveCaseId(applicationName);
		this.caseIdCreated=caseID;
		custom3MSleep();
		custom3MSleep();
		custom2MSleep();
	    driver.navigate().refresh();
		System.out.println(caseID);
		logger.log(LogStatus.INFO, "Case ID created is"+caseID);
		//System.out.println(cellDataList);
		ExceptionRequestsClick(caseID);
		
		logger.log(LogStatus.INFO, "Case created which moved to exception has been clicked");
		System.out.println("Case created which moved to exception has been clicked");
		logger.log(LogStatus.INFO, "Number of invalid cases in the uploaded sheet is");
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+caseID+"')]")));
		WebElement createdCase= driver.findElement(By.xpath("//a[contains(text(),'"+caseID+"')]"));
		createdCase.click();
		wait1.until(ExpectedConditions.presenceOfElementLocated((CancelPage.cancelCaseCreatedStatus)));
		String Status= (driver.findElement(CancelPage.cancelCaseCreatedStatus)).getText();
		logger.log(LogStatus.INFO, "Number of invalid cases in the uploaded sheet is"+noOfMissingFields);
		System.out.println(noOfMissingFields);
		logger.log(LogStatus.INFO, "Status of the invalid case is"+Status);
		System.out.println(Status);
		List<WebElement> excepCaseStatus = null;
		try
		{
			  excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
		}
		catch(Exception e)
		{
		}
		if (excepCaseStatus ==null)
		{
			driver.findElement(CancelPage.caseSummary).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-attribute-name='Status']/div/span")));
			excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
		}
		
			 List<WebElement> exceptionCaseIds = (driver.findElements(CancelPage.cancelCaseExceptionSubCasesId));
			 System.out.println("Number of exception case IDs created is"+exceptionCaseIds.size() );
			 logger.log(LogStatus.INFO, "Number of exception case IDs created is"+exceptionCaseIds.size());
			 for(WebElement excep:exceptionCaseIds) {
				 System.out.println("Exception Case ID created is"+excep.getText());
			 }
			 for(WebElement e:excepCaseStatus) {
					Assert.assertEquals("Pending-Exception", e.getText().trim());
				}
			 System.out.println(this.noOfMissingFields);
			if(!(exceptionCaseIds.size()==this.noOfMissingFields)) {
				Assert.fail("Number of exception cases in CSV is"+noOfMissingFields+"is not matching with the no of exception cases created"+exceptionCaseIds.size()+ "in exception screen");
			}
			else {
				String[] excepMessageArray = new String[8];
				List<WebElement> exceptionMessage = (driver.findElements(CancelPage.cancelCaseExceptionMessageSymbol));
				Actions ac = new Actions(driver);
				int z=0;
				for(WebElement excepMsg:exceptionMessage) {
					 ac.moveToElement(excepMsg).build().perform();
					 
					 custom6Sleep();
					wait1.until(ExpectedConditions.presenceOfElementLocated(CancelPage.cancelCaseExceptionMessage)) ;
					excepMessageArray[z]= driver.findElement(CancelPage.cancelCaseExceptionMessage).getText();
					System.out.println(excepMessageArray[z]);
					System.out.println(missingFieldsInRe[z]);
					z++;
			}
				excepMessageArray = Arrays.stream(excepMessageArray)
	                     .filter(s -> (s != null && s.length() > 0))
	                     .toArray(String[]::new); 
			
				missingFieldsInRe = Arrays.stream(missingFieldsInRe)
	                     .filter(s -> (s != null && s.length() > 0))
	                     .toArray(String[]::new);
				Arrays.sort(excepMessageArray);
				Arrays.sort(missingFieldsInRe);
				if(Arrays.equals(excepMessageArray, missingFieldsInRe)) {
					
					 Assert.assertTrue("Exception message of expected and actual are same", true);
				}
				else
					Assert.fail("exception Messages are not equal");
			
					
			}
			
}
	public  String addSpaceBeforeCapitalLetter(String sstr) 
    { 
        char[] str=sstr.toCharArray(); 
          int j=0;
        // Traverse the string 
        for (int i=0; i < str.length; i++) 
        { 
            // Convert to lowercase if its 
            // an uppercase character 
            if (str[i]>='A' && str[i]<='Z') 
            { 
              if(i!=0) {
            	String temp1= sstr.substring(i+j);
            	String temp2= sstr.substring(0, i+j);
            	sstr=temp2+" "+temp1;
            	j++;
              }                     
            } 
        } 
      System.out.println(sstr);
        return sstr;
    } 	

	public String RetrieveCaseId(String application) {
		String caseId;
		custom9Sleep();
		custom9Sleep();
		WebElement e=driver.findElement(By.xpath("//a[contains(text(),'TEE-')]"));
		caseId=e.getText();
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		if(application=="Cancel") {
			driver.findElement(CancelPage.cancelCasesButton).click();
			logger.log(LogStatus.INFO, "Cancel case has been clicked");
		}
		else 
			if(application=="IDP") {
			driver.findElement(IDPPage.idpCasesButton).click();
		}
		
		//driver.findElement(CancelPage.pendingRequests).click();
		
		logger.log(LogStatus.INFO, "Pending requests has been clicked");
		
		/*wait1.until(ExpectedConditions.presenceOfElementLocated(CancelPage.cancelCaseStatusList));
		List<WebElement> linkElements = (driver.findElements(CancelPage.cancelCaseStatusList));
		int len =linkElements.size();
		System.out.println(len);	
	    String[] linkTexts = new String[len];			
			//extract each of the element to check the pending status.		
	    int i=0;	
	    for (WebElement e : linkElements) {
				linkTexts[i]=e.getText();//Capturing the text from the matching CaseID
				i++;
			}
			System.out.println(Arrays.toString(linkTexts));
			List<String> caseIdlist = new ArrayList<String>();

		    for(String s : linkTexts) {
		       if(s != null && s.length() > 0) {
		    	   caseIdlist.add(s);//removing all the null values of the array
		       }
		    }
		    linkTexts = caseIdlist.toArray(new String[caseIdlist.size()]);
		    System.out.println(linkTexts.length);
		    caseId=linkTexts[0];//capturing the case id of the newly created case.
		    */
		
		System.out.println(caseId);	
			this.caseIdCreated=caseId;
			System.out.println(caseId);
			return caseId;
	}
	public static void customSleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void custom3Sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCaseId() {
		return this.caseIdCreated;
	}
	public static void custom3MSleep() {
		try {
			Thread.sleep(180000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void custom2MSleep() {
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void custom6Sleep() {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getElementFromPropFile(String property) throws IOException {
		FileInputStream file = new FileInputStream(propFilePath);
		Properties prop = new Properties();
		prop.load(file);
		return prop.getProperty(property);

	}

	public static void takeScreenShot(ITestResult result) throws IOException {
		String testCaseName = result.getName();
		File file = null;
		if (result.getStatus() == ITestResult.SUCCESS)
			file = new File(System.getProperty("user.dir") + "\\ScreenShots\\" + testCaseName + "\\Passed\\");
		if (result.getStatus() == ITestResult.FAILURE)
			file = new File(System.getProperty("user.dir") + "\\ScreenShots\\" + testCaseName + "\\Failed\\");
		if (result.getStatus() == ITestResult.SKIP)
			file = new File(System.getProperty("user.dir") + "\\ScreenShots\\" + testCaseName + "\\Skipped\\");
		if (!file.exists()) {
			System.out.println("File created " + file);
			file.mkdir();
		}
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String screenshotName = testCaseName + strScreenShotName.format(new Date()) + ".png";
			File targetFile = new File(file, screenshotName);
			FileUtils.copyFile(scrFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getData(String SheetName, String columnName, String tcName) throws IOException {
		String defDataSheet = getElementFromPropFile("DefaultDataSheet");
		String fileName = System.getProperty("user.dir") + "\\TestData\\" + defDataSheet + ".xlsx";
		String dataSheetName = SheetName;
		String colName = columnName;
		String rowName = tcName;

		List cellDataList = new ArrayList();
		FileInputStream fileInputStream = new FileInputStream(fileName);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workBook.getSheet(dataSheetName);
		Iterator rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			XSSFRow row = (XSSFRow) rowIterator.next();
			Iterator iterator = row.cellIterator();
			List cellTempList = new ArrayList();
			while (iterator.hasNext()) {
				XSSFCell cell = (XSSFCell) iterator.next();
				
				cellTempList.add(cell);
			}
			cellDataList.add(cellTempList);
		}
		// System.out.println(cellDataList);
		for (int i = 0; i < cellDataList.size(); i++) {
			int k = 0;
			List cellTempList = (List) cellDataList.get(i);
			int sizeOfList = cellTempList.size();
			do {
				if (cellTempList.get(k).toString().equals(colName)) {
					// System.out.println(cellTempList.get(k).toString());
					XSSFCell hssfCell = (XSSFCell) cellTempList.get(k);
					colIndex = hssfCell.getColumnIndex();
					// System.out.print(colIndex);
				} else {
					if (cellTempList.get(k).toString().equals(rowName)) {
						// System.out.println(cellTempList.get(k).toString());
						XSSFCell hssfCell = (XSSFCell) cellTempList.get(k);
						// int rowIndex = hssfCell.getRowIndex();
						// System.out.print(rowIndex);
						XSSFRow row = sheet.getRow(i);
						value = cellToString(row.getCell(colIndex));
						// System.out.println("Value of " + colName +
						// " for the test case " + rowName + " is :" + value);
					}
				}
				k++;
			} while (k < sizeOfList);
			System.out.println();
			workBook.close();
		}
		return value;
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getStringCellValue();
		}
		return result.toString();
	}

	public static void putData(String SheetName, String columnName, String tcName, String data) throws IOException {
		String defDataSheet = getElementFromPropFile("DefaultDataSheet");
		String fileName = System.getProperty("user.dir") + "\\TestData\\" + defDataSheet + ".xlsx";
		String dataSheetName = SheetName;
		String colName = columnName;
		String rowName = tcName;
		String dataToUpdate = data;
		List cellDataList = new ArrayList();
		FileInputStream fileInputStream = new FileInputStream(fileName);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workBook.getSheet(dataSheetName);
		Iterator rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			XSSFRow row = (XSSFRow) rowIterator.next();
			Iterator iterator = row.cellIterator();
			List cellTempList = new ArrayList();
			while (iterator.hasNext()) {
				XSSFCell cell = (XSSFCell) iterator.next();
				cellTempList.add(cell);
			}
			cellDataList.add(cellTempList);
		}
		// System.out.println(cellDataList);
		for (int i = 0; i < cellDataList.size(); i++) {
			int k = 0;
			List cellTempList = (List) cellDataList.get(i);
			int sizeOfList = cellTempList.size();
			do {
				if (cellTempList.get(k).toString().equals(colName)) {
					System.out.println(cellTempList.get(k).toString());
					XSSFCell hssfCell = (XSSFCell) cellTempList.get(k);
					colIndex = hssfCell.getColumnIndex();
					System.out.print(colIndex);
				} else {
					if (cellTempList.get(k).toString().equals(rowName)) {
						System.out.println(cellTempList.get(k).toString());
						XSSFCell hssfCell = (XSSFCell) cellTempList.get(k);
						int rowIndex = hssfCell.getRowIndex();
						System.out.print(rowIndex);
						XSSFRow row = sheet.getRow(i);
						XSSFCell cell = row.createCell(colIndex);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(dataToUpdate);
						String value = cell.getStringCellValue();
						System.out.println("Value updated into " + colName + " for the test case " + rowName + " is :" + value);
						FileOutputStream fileOut = new FileOutputStream(fileName);
						workBook.write(fileOut);
						fileOut.close();
					}
				}
				k++;
			} while (k < sizeOfList);
			System.out.println();
		}
	}

	public static String convertSecondsToHours(Long time) {
		String formatTime = null;
		try {
			System.out.println("time in Seconds ...." + time);

			int hrs = (int) (time / 3600);
			System.out.println("No of Hrs ::" + hrs);

			int mins = (int) (time % 3600) / 60;
			System.out.println("No of Mins ::" + mins);

			int secs = (int) ((time % 3600) % 60);
			System.out.println("No of Secs ::" + secs);

			formatTime = hrs + " Hr " + mins + " Mins " + secs + " Secs";

		} catch (Exception e) {
			System.out.println("Error occured while converting the Time from Seconds to Hours..." + e.getMessage());
		}
		return formatTime;
	}

	private static String timeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}

	public static void createDir(String dirName) {
		File f = new File(dirName);
		try {
			if (!f.exists()) {
				f.mkdir();
				CreateLogger.LOGGER.info("Directory Created :: " + dirName);
			}
		} catch (Throwable e) {
			CreateLogger.LOGGER.error("Unable to create directory  '" + dirName + "'");
		}
	}
	public static void removeDir(String dirName) {
		File f = new File(dirName);
		String[]entries = f.list();
		for(String s: entries){
		    File currentFile = new File(f.getPath(),s);
		    currentFile.delete();
		}
		f.delete();
		}

	public void customType(String Locator, String Text) {
		int count = 0;
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
		try {
			int fieldLength = Text.length();
			String actualText;
			if (driver.findElement(By.name(Locator)).isDisplayed() && fieldLength != 0) {
				do {
					driver.findElement(By.name(Locator)).clear();
					driver.findElement(By.name(Locator)).sendKeys(Text);
					actualText = driver.findElement(By.name(Locator)).getAttribute("value");
					count = count + 1;
				} while (!Text.equals(actualText) && count != 10);
			}
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		}
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}

	public void comboCustomType(String identifier, String Locator, String Text) {
		int count = 0;
		customSleepOne();
		if (identifier.equals("name")) {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			try {
				int fieldLength = Text.length();
				String actualText;
				if (driver.findElement(By.name(Locator)).isDisplayed() && fieldLength != 0) {
					do {
						driver.findElement(By.name(Locator)).clear();
						driver.findElement(By.name(Locator)).sendKeys(Text);
						actualText = driver.findElement(By.name(Locator)).getAttribute("value");
						count = count + 1;
					} while (!Text.equals(actualText) && count != 10);
					driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
					driver.findElement(By.name(Locator)).sendKeys(Keys.RETURN);
					customSleep();
					driver.findElement(By.xpath("//nobr[text(),'" + Text + "']")).click();
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			}
		} else if (identifier.equals("xpath")) {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			try {
				int fieldLength = Text.length();
				String actualText;
				if (driver.findElement(By.xpath(Locator)).isDisplayed() && fieldLength != 0) {
					do {
						driver.findElement(By.xpath(Locator)).clear();
						driver.findElement(By.xpath(Locator)).sendKeys(Text);
						actualText = driver.findElement(By.name(Locator)).getAttribute("value");
						count = count + 1;
					} while (!Text.equals(actualText) && count != 10);
					driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
					customSleep();
					driver.findElement(By.xpath("//nobr[text(),'" + Text + "']")).click();
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			}
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		}
	}

	public void customType(String locatorType, String Locator, String Text) throws Exception {
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
		if (locatorType.contains("name")) {
			try {
				if (driver.findElement(By.name(Locator)).isDisplayed()) {
					driver.findElement(By.name(Locator)).clear();
					for (int i = 0; i < Text.length(); i++) {
						try {
							driver.findElement(By.name(Locator)).sendKeys(Character.toString(Text.charAt(i)));
							try {
								Thread.sleep(40);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							System.out.println("Continue Typing");
						}
					}
				}
			} catch (Exception e) {

				throw new Exception("Error in entering data in " + Locator);
			}
		} else {
			try {
				int fieldLength = Text.length();
				if (driver.findElement(By.xpath(Locator)).isDisplayed() && fieldLength != 0) {
					driver.findElement(By.xpath(Locator)).clear();
					for (int i = 0; i < Text.length(); i++) {
						try {
							driver.findElement(By.xpath(Locator)).sendKeys(Character.toString(Text.charAt(i)));
							try {
								Thread.sleep(40);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							System.out.println("Continue Typing");
						}
					}
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				throw new Exception("Error in entering data");
			}
		}
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
	}

	public void customType(String locatorType, String Locator, String Text, int waitTime) throws Exception {
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
		if (locatorType.contains("name")) {
			try {
				if (driver.findElement(By.name(Locator)).isDisplayed()) {
					driver.findElement(By.name(Locator)).clear();
					for (int i = 0; i < Text.length(); i++) {
						try {
							driver.findElement(By.name(Locator)).sendKeys(Character.toString(Text.charAt(i)));
							try {
								Thread.sleep(waitTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							System.out.println("Continue Typing");
						}
					}
				}
			} catch (Exception e) {

				throw new Exception("Error in entering data in " + Locator);
			}
		} else {
			try {
				if (driver.findElement(By.xpath(Locator)).isDisplayed()) {
					driver.findElement(By.xpath(Locator)).clear();
					for (int i = 0; i < Text.length(); i++) {
						try {

							driver.findElement(By.xpath(Locator)).sendKeys(Character.toString(Text.charAt(i)));
							try {
								Thread.sleep(waitTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							System.out.println("Continue Typing");
						}
					}
				}
			} catch (Exception e) {

				throw new Exception("Error in entering data");
			}
		}
		customSleepOne();
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
	}

	public void customSleepOne() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void custom9Sleep() {
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getElementXPath(WebDriver driver, WebElement element) {
		String javaScript = "function getElementXPath(elt){" + "var path = \"\";" + "for (; elt && elt.nodeType == 1; elt = elt.parentNode){" + "idx = getElementIdx(elt);" + "xname = elt.tagName;" + "if (idx > 1){" + "xname += \"[\" + idx + \"]\";" + "}" + "path = \"/\" + xname + path;" + "}" + "return path;" + "}" + "function getElementIdx(elt){" + "var count = 1;" + "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){" + "if(sib.nodeType == 1 && sib.tagName == elt.tagName){"
				+ "count++;" + "}" + "}" + "return count;" + "}" + "return getElementXPath(arguments[0]).toLowerCase();";

		return (String) ((JavascriptExecutor) driver).executeScript(javaScript, element);
	}

	public void webdriverDynamicWait(String findby, final String Element) throws Exception {
		if (findby.equals("id")) {
			try {
				WebElement DynamicElement = (new WebDriverWait(driver, 180)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.id(Element));

					}
				});
			} catch (Exception e) {
				throw new Exception("Element " + Element + " is not found");
			}
		} else if (findby.equals("name")) {
			try {
				WebElement DynamicElement = (new WebDriverWait(driver, 180)).until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						return d.findElement(By.name(Element));

					}
				});
			} catch (Exception e) {
				throw new Exception("Element " + Element + " is not found");
			}

		} else if (findby.equals("xpath")) {
			try {
				WebElement DynamicElement = (new WebDriverWait(driver, 180)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath(Element));

					}
				});
			} catch (Exception e) {
				throw new Exception("Element " + Element + " is not found");
			}

		} else if (findby.equals("css")) {
			try {
				WebElement DynamicElement = (new WebDriverWait(driver, 60)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.cssSelector(Element));

					}
				});
			} catch (Exception e) {
				throw new Exception("Element " + Element + " is not found");
			}
		} else if (findby.equals("link")) {
			try {
				WebElement DynamicElement = (new WebDriverWait(driver, 180)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.linkText(Element));

					}
				});
			} catch (Exception e) {
				throw new Exception("Element " + Element + " is not found");
			}

		} else
			System.out.println("data not entered");
	}

	public void customWait(String Type, String Element) {
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		if (Type.equals("name")) {
			do {
				try {
					Thread.sleep(waittime);
					timecount = timecount + waittime;
					System.out.println("current waitime for " + Element + " is " + timecount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!driver.findElement(By.name(Element)).isDisplayed() && timecount != maxtimecount);
			timecount = 0;
		}
		if (Type.equals("id")) {
			do {
				try {
					Thread.sleep(waittime);
					timecount = timecount + waittime;
					System.out.println("current waitime for " + Element + " is " + timecount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!driver.findElement(By.id(Element)).isDisplayed() && timecount != maxtimecount);
			timecount = 0;
		}
		if (Type.equals("xpath")) {
			do {
				try {
					Thread.sleep(waittime);
					timecount = timecount + waittime;
					System.out.println("current waitime for " + Element + " is " + timecount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!driver.findElement(By.xpath(Element)).isDisplayed() && timecount != maxtimecount);
			timecount = 0;
		}
		if (Type.equals("text")) {
			String pageText = null;
			do {
				try {
					Thread.sleep(waittime);
					timecount = timecount + waittime;
					pageText = driver.findElement(By.tagName("body")).getText();
					System.out.println("current waitime for " + Element + " is " + timecount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!pageText.contains(Element) && timecount != maxtimecount);
			timecount = 0;
		}
	}

	public void dynamicWait(long waitTime, String Type, String Element) {
		if (Type.equals("name")) {
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(Element)));
		} else if (Type.equals("id")) {
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(Element)));
		} else if (Type.equals("xpath")) {
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Element)));
		} else if (Type.equals("text")) {
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			boolean element = wait.until(ExpectedConditions.textToBePresentInElement(By.tagName("td"), Element));
		} else if (Type.equals("div")) {
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			boolean element = wait.until(ExpectedConditions.textToBePresentInElement(By.tagName("div"), Element));
		}
	}

	public void customWaitForElement(String Type, String Element) {
		boolean isElementDisplayed = false;
		int counter = 0;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		if (Type.equals("name")) {
			do {
				try {
					customSleep();
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					if (driver.findElement(By.name(Element)).isDisplayed())
						isElementDisplayed = true;
				} catch (Exception e) {
					isElementDisplayed = false;
					counter = counter + 1;
				}
			} while (!isElementDisplayed && counter != maxtimecounter);
			counter = 0;
			isElementDisplayed = false;
		} else if (Type.equals("id")) {
			do {
				try {
					customSleep();
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					if (driver.findElement(By.id(Element)).isDisplayed())
						isElementDisplayed = true;
				} catch (Exception e) {
					isElementDisplayed = false;
					counter = counter + 1;
				}
			} while (!isElementDisplayed && counter != maxtimecounter);
			counter = 0;
			isElementDisplayed = false;
		} else if (Type.equals("xpath")) {
			do {
				try {
					customSleep();
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					if (driver.findElement(By.xpath(Element)).isDisplayed())
						isElementDisplayed = true;
				} catch (Exception e) {
					isElementDisplayed = false;
					counter = counter + 1;
				}
			} while (!isElementDisplayed && counter != maxtimecounter);
			counter = 0;
			isElementDisplayed = false;
		}
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}

	public void selectDropDownValue(String dropDown, String xPath, String Value) {
		driver.findElement(By.xpath(dropDown)).click();
		int count = 0;
		int a = xPath.indexOf("[i]");
		String newxPath = xPath.substring(0, a);
		String newxPath1 = xPath.substring(a + 2, xPath.length());
		int countOne = driver.findElements(By.xpath(newxPath)).size();

		for (int i = 1; i <= countOne; i++) {
			String actualValue = driver.findElement(By.xpath(newxPath + "[" + i + newxPath1)).getText().trim();
			if (!actualValue.isEmpty()) {
				if (actualValue.trim().toLowerCase().equals(Value.trim().toLowerCase())) {
					driver.findElement(By.xpath(newxPath + "[" + i + newxPath1)).click();
					count = count + 1;
					break;
				}
			}
		}
		if (count == 0)
			System.out.println("Error in selecting dropdown value: " + Value);
		if (count == 1)
			System.out.println(Value + " is selected as dropdown value");
	}

		
	public static void typeCharacter(Robot robot, String letter) {
		for (int i = 0; i < letter.length(); i++) {
			try {
				boolean upperCase = Character.isUpperCase(letter.charAt(i));
				String KeyVal = Character.toString(letter.charAt(i));
				String variableName = "VK_" + KeyVal.toUpperCase();
				Class clazz = KeyEvent.class;
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(null);
				robot.delay(500);
				if (upperCase)
					robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);
				if (upperCase)
					robot.keyRelease(KeyEvent.VK_SHIFT);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	public void ResolvedStatusCheck(String filepath,String username,String password, String application) {
		TC02_UploadCsvFile oTC02_UploadCsvFile= new TC02_UploadCsvFile();
		String applicationName=application;
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		try {
			if(filepath.isEmpty()||filepath.equals(null)) {
				oTC02_UploadCsvFile.UploadFile("",username,password);	
			}
			else
			{
				oTC02_UploadCsvFile.UploadFile(filepath,username,password);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String caseID=RetrieveCaseId(applicationName);
		custom3MSleep();
		custom3MSleep();
		custom2MSleep();
		driver.navigate().refresh();
		wait1.until(ExpectedConditions.visibilityOfElementLocated(CancelPage.resolvedRequests));
		driver.findElement(CancelPage.resolvedRequests).click();//clicking on the resolved requests
		logger.log(LogStatus.INFO, "Case created which moved to resolved has been clicked");

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+caseID+"')]")));
		WebElement createdCase= driver.findElement(By.xpath("//a[contains(text(),'"+caseID+"')]"));
		createdCase.click();
		custom6Sleep();
		List<WebElement> excepCaseStatus = null;
		try
		{
			  excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
		}
		catch(Exception e)
		{
		}
		if (excepCaseStatus ==null)
		{
			driver.findElement(CancelPage.caseSummary).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-attribute-name='Status']/div/span")));
			excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
		}
		for(WebElement e:excepCaseStatus) {
			Assert.assertEquals("Resolved-Completed", e.getText().trim());
		}
		
	}
	public void checkAfterReValidate(int custShip,String caseID) {
		WebDriverWait wait= new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(NonPickUpDate.configurationButton));
		 driver.findElement(NonPickUpDate.configurationButton).click();
		 custom3Sleep();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Edit']")));
		 driver.findElement(By.xpath("//div[text()='Edit']")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='pySearchTerm']")));
			driver.findElement(By.xpath("//input[@id='pySearchTerm']")).sendKeys(Integer.toString(custShip));
			 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='pi pi-search']")));
			 driver.findElement(By.xpath("//i[@class='pi pi-search']")).click();
			 custom9Sleep();
			 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@data-attribute-name='RemainingReceivingCapacity']/div[@class='oflowDivM ']")));
			 String remainingUpdated=driver.findElement(By.xpath("//td[@data-attribute-name='RemainingReceivingCapacity']/div[@class='oflowDivM ']")).getText();
			 remainingUpdated=remainingUpdated.trim();
			 Assert.assertEquals(remainingUpdated, Integer.toString(custShip+63));
			 driver.findElement(HomePage.idpCasesButton).click();
			 custom6Sleep();
			 wait.until(ExpectedConditions.visibilityOfElementLocated(CancelPage.resolvedRequests));
				driver.findElement(CancelPage.resolvedRequests).click();//clicking on the resolved requests
				

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+caseID+"')]")));
				WebElement createdCase= driver.findElement(By.xpath("//a[contains(text(),'"+caseID+"')]"));
				createdCase.click();
				List<WebElement> excepCaseStatus = null;
				try
				{
					  excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
				}
				catch(Exception e)
				{
				}
				if (excepCaseStatus ==null)
				{
					driver.findElement(CancelPage.caseSummary).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-attribute-name='Status']/div/span")));
					excepCaseStatus = driver.findElements(By.xpath("//td[@data-attribute-name='Status']/div/span"));
				}
				for(WebElement e:excepCaseStatus) {
					Assert.assertEquals("Resolved-Completed", e.getText().trim());
				}
	}
	public void addValuesInCapacityTable(int custShip,String futureDate) {
		DateUtils du= new DateUtils();
		Login("AutoTestUser","rules");
		 WebDriverWait wait= new WebDriverWait(driver,40);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(NonPickUpDate.configurationButton));
		 driver.findElement(NonPickUpDate.configurationButton).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Edit']")));
		 driver.findElement(By.xpath("//div[text()='Edit']")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='+ Add record']")));
		 WebElement addRecord=driver.findElement(By.xpath("//a[text()='+ Add record']"));
		 addRecord.click();
		 
		String dateInMax=du.getParseDateMdyyyyFormat(futureDate);	
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@validationtype='date']")));
		 driver.findElement(By.xpath("//input[@validationtype='date']")).sendKeys(dateInMax.toString());
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'CustShipToCd')]")));
		 
		 driver.findElement(By.xpath("//input[contains(@id,'CustShipToCd')]")).sendKeys(Integer.toString(custShip));
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'MinReceivingCapacity')]")));
		 driver.findElement(By.xpath("//input[contains(@id,'MinReceivingCapacity')]")).sendKeys(Integer.toString(custShip));
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'MaxReceivingCapacity')]")));
		 driver.findElement(By.xpath("//input[contains(@id,'MaxReceivingCapacity')]")).sendKeys(Integer.toString(custShip+69));
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'RemainingReceivingCapacity')]")));
		 driver.findElement(By.xpath("//input[contains(@id,'RemainingReceivingCapacity')]")).sendKeys(Integer.toString(custShip+63));
		 addRecord.click();
		 custom9Sleep();
		 
		 driver.navigate().refresh();
		 //driver.switchTo().alert().accept();
		 custom9Sleep();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='icons avatar name-a ']")));
		 driver.findElement(By.xpath("//i[@class='icons avatar name-a ']")).click();
		 wait.until(ExpectedConditions.presenceOfElementLocated(HomePage.lnkLogOff));
			driver.findElement(HomePage.lnkLogOff).click();
	}
	public void mouseMove() {
		Random r = new Random();
		int a = r.nextInt(800);
		int b = r.nextInt(700);
		System.out.println(a);
		System.out.println(b);
		try {
			Robot robot = new Robot();
			robot.mouseMove(a, b);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void clickElement(String Element, String Text, String ElementName) throws Exception {
		try {
			String pageText;
			int count = 0;
			do {
				driver.findElement(By.xpath(Element)).click();
				customSleep();
				customSleep();
				count = count + 1;
				pageText = driver.findElement(By.tagName("body")).getText();

			} while (!pageText.toLowerCase().contains(Text.trim().toLowerCase()) && count != 40);
			System.out.println(ElementName + " is clicked");
		} catch (Exception e) {
			throw new Exception("Error in entering clicking on" + ElementName);
		}
	}

	public boolean isDateBetween(String Stdate, String EndDt, String TestDt) throws ParseException {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfDate1 = new SimpleDateFormat("dd/MM/yyyy");
		Date DtStrt = sdfDate.parse(Stdate);
		Date DtEnd = sdfDate.parse(EndDt);
		Date DtTest = sdfDate.parse(TestDt);
		if ((DtStrt.compareTo(DtTest) <= 0) && (DtTest.compareTo(DtEnd) <= 0))
			return true;
		else
			return false;
	}

	public boolean isDateBetweenParsing(String Stdate, String EndDt, String TestDt) throws ParseException {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat sdfDate1 = new SimpleDateFormat("dd/MM/yyyy");
		Date DtStrt = sdfDate.parse(Stdate);
		Date DtEnd = sdfDate.parse(EndDt);
		Date DtTest = sdfDate1.parse(TestDt);
		if ((DtStrt.compareTo(DtTest) <= 0) && (DtTest.compareTo(DtEnd) <= 0))
			return true;
		else
			return false;
	}

	public String subtractDate(String timeZone, int minusDate) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		TimeZone T1 = TimeZone.getTimeZone(timeZone);
		dateFormat.setTimeZone(T1);
		Date dateBefore = new Date(date.getTime() - minusDate * 24 * 3600 * 1000);
		String previousDate = dateFormat.format(dateBefore);
		return previousDate;
	}

	public void highlightElement(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		changeColor(element, js);
		// changeColor(bgcolor, element, js);
	}

	public void changeColor(WebElement element, JavascriptExecutor js) {
		js.executeScript("arguments[0].style.border='2px solid red'", element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	public void waitForElement(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
		}
	}

	public void waitForElementToBeClickable(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
		}
	}

	public void scrollDown() {
		try {
			EventFiringWebDriver driver1 = new EventFiringWebDriver(driver);
			driver1.executeAsyncScript("scroll(0,2000)");
		} catch (Exception e) {
		}
	}

	public void validatePlaceHolderText(String pageName, String fieldName, String txtXPath, String expText) throws Exception {
		try {
			String getHelpText = driver.findElement(By.xpath(txtXPath)).getAttribute("placeholder");
			if (getHelpText.equals(expText))
				System.out.println("Place Holder Text is displayed as expected for the " + fieldName + " field in " + pageName);
			else
				System.out.println("Place Holder Text is NOT displayed as expected for the " + fieldName + " field in " + pageName);
		} catch (Exception e) {
			throw new Exception("Error in validating Default Help Text displayed in all the fields of different pages");
		}
	}

	public void Login(String LoginType, String Password) {
		try {
			
			String userName=LoginType; 
			String password=Password;
			
			
			//Identifying Login ID
		WebElement loginId= driver.findElement(LoginPage.txtUserName);
		
		Actions actions = new Actions(driver);
		actions.moveToElement(loginId);
		actions.click();
		actions.sendKeys(userName);// sending Login details
		actions.build().perform();
		WebElement loginPassword= driver.findElement(LoginPage.txtPassword);
		actions.moveToElement(loginPassword);
		actions.click();
		actions.sendKeys(password);// sending Password details
		actions.build().perform();
		
		WebElement loginButton=driver.findElement(LoginPage.btnLogin);
		actions.moveToElement(loginButton);
		actions.click();
		actions.build().perform();
		
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			//wait.until(ExpectedConditions.presenceOfElementLocated(HomePage.homePageTitle));
			//Assert.assertTrue(driver.findElement(HomePage.homePageTitle).isDisplayed());
			logger.log(LogStatus.INFO, "Logged in to application successfully using " + userName + " " + "and" + " " + password);
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Application Login Failed" + e.toString());
			Assert.fail("Application Login Failed" + e.toString());
		}
	}

	public  void ReadAndParseDataFromCsv(String fileName) throws IOException {
		String[] fieldsList=new String [] {"CustSoldToCd","CustShipToCd","SODocHdrNbr","SoLineItem","SOSchedLnNbr","MaterialCode","Quantity","DaysLate","NoDSMQty","CancellationCode","WholesaleVal"};
		List<List<String>> downloadSheetExceptionList = new ArrayList<>();
		int lineNo = 0,a=0,b=0,i=0;
		lineNo = 1;
		
		FileInputStream fileInputStream = new FileInputStream(fileName);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		Sheet sheet  = workBook.getSheet("Cancel");
		int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		System.out.println(sheet.getSheetName());
		int arrayNo=0;
		for (int v = 0; v < rowCount + 1; v++)  {
			Row row = sheet.getRow(v);
			List<String> cellTempList = new ArrayList();
			int columnNo = 1;
			
			for (int g = 0; g < 11;g++ )  {

				if((row.getCell(g)==null||row.getCell(g).toString().isBlank())&&(b<1)) {
					b++;
				}
				
				if(row.getCell(g)==null||row.getCell(g).toString().isBlank())
				{
					i++;
					System.out.println("Field Missing is :"+fieldsList[columnNo-1] +"in record "+(lineNo-1));
					if(i>1) {
						missingFieldsInRe[arrayNo-1]=missingFieldsInRe[arrayNo-1]+", "+fieldsList[columnNo-1];
						System.out.println(missingFieldsInRe[arrayNo-1]);
						
					}
					else {
						missingFieldsInRe[arrayNo]="Missing values:"+ " "+fieldsList[columnNo-1];
						System.out.println(missingFieldsInRe[arrayNo]);
						arrayNo++;
					}
				}
				columnNo++;
			}
			if(b!=0) {
				a++;
			}
			b=0;
			i=0;
			
			lineNo++;
		}
		
	/*
		File file = new File(fileName);//reading the file
		inputStream = new Scanner(file);
		//ReadAndParseDataFromCsv
		while(inputStream.hasNext()){
			line= inputStream.next();
			String[] values = line.split(",",-1);
			// this adds the currently parsed line to the 2-dimensional string array
			lines.add(Arrays.asList(values));
		}
		//logger.log(LogStatus.INFO, "Input file has been read");
		Path path = Paths.get(fileName);
		long lineCount = Files.lines(path).count() ;
		//logger.log(LogStatus.INFO, "Total number of lines in the CSV "+lineCount);
		System.out.println(lineCount);

		lineNo = 1;
		
		for(List<String> lineIn: lines) {
			int columnNo = 1;
			for (String value: lineIn) {
				if(value==null&&b<1) {
					b++;
				}
				System.out.println(value);
				if(value.isEmpty())
				{
					i++;
					System.out.println("Field Missing is :"+fieldsList[columnNo-1] +"in record "+(lineNo-1));
					
					if(i>1) {
		
						missingFieldsInRe[arrayNo]=missingFieldsInRe[arrayNo]+", "+fieldsList[columnNo-1];
						System.out.println(missingFieldsInRe[arrayNo]);
						
						arrayNo++;
					}
					else {
						missingFieldsInRe[arrayNo]="Missing values:"+ " "+fieldsList[columnNo-1];
						
						System.out.println(missingFieldsInRe[arrayNo]);
						
					}
					
	
					
				}
				columnNo++;}
			if(b!=0) {
				a++;
			}
			b=0;
			i=0;
			
			lineNo++;   
		}
		*/
		System.out.println(a);
		this.noOfMissingFields=a;
		

}	
	public void AddAttachment(String fileName) {
		try {
		WebDriverWait wait= new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.presenceOfElementLocated(CancelPage.addAttachment));
		driver.findElement(CancelPage.addAttachment).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='File from device']")));
		driver.findElement(By.xpath("//span[text()='File from device']")).click();
		logger.log(LogStatus.INFO, "File from device from options has been selected");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"Browsefiles\"]")));
		String s = (fileName);
		Actions ac = new Actions(driver);
		// ac.moveToElement(driver.findElement(By.xpath("//input[contains(@title,'Select
		// file')]")));
		ac.moveToElement(driver.findElement(By.xpath("//*[@id=\"Browsefiles\"]"))).click().build().perform();
	 
		StringSelection stre = new StringSelection(s);
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		clip.setContents(stre, null);
		Robot rb;
		
			rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		 
		
		// driver.findElement(By.xpath("(//td[@class='buttonTdMiddle'])[1]")).sendKeys(fileName);
		logger.log(LogStatus.INFO, "Select files has been clicked");
		custom9Sleep();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Attach']")));
		driver.findElement(By.xpath("//button[@title='Attach']")).click();
		logger.log(LogStatus.INFO, "attach button has been clicked");
		custom9Sleep();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@id,'att_feed_desc')]")));
		driver.findElement(By.xpath("//a[contains(@id,'att_feed_desc')]"));
		logger.log(LogStatus.INFO, "Uploaded file has been shown in the screen");
	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void LogOut() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			driver.findElement(HomePage.btnLogOut).click();
			
			wait.until(ExpectedConditions.presenceOfElementLocated(HomePage.lnkLogOff));
			driver.findElement(HomePage.lnkLogOff).click();
			custom3Sleep();
			//Assert.assertEquals("Log in", driver.findElement(LoginPage.btnLogin).getText());
			logger.log(LogStatus.INFO, "Logged out of the application successfully");
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Application LogOut Failed" + e.toString());
			Assert.fail("Application LogOut Failed" + e.toString());
		}
	}

	

	@DataProvider(name = "Logins")
	public static Object[][] credentials() throws IOException {
		String userName1 = getData("TestDataSheet", "UserName", "TC04_LoginWithDiffUsers");
		String password1 = getData("TestDataSheet", "Password", "TC04_LoginWithDiffUsers");
		String userName2 = getData("TestDataSheet", "ManagerUserName", "TC04_LoginWithDiffUsers");
		String password2 = getData("TestDataSheet", "ManagerPassword", "TC04_LoginWithDiffUsers");
		String userName3 = getData("TestDataSheet", "AdminUserName", "TC04_LoginWithDiffUsers");
		String password3 = getData("TestDataSheet", "AdminPassword", "TC04_LoginWithDiffUsers");
		return new Object[][] { { userName1, password1 }, { userName2, password2 }, { userName3, password3 } };
	}

	@DataProvider(name = "DiffLoanAmounts")
	public static Object[][] DiffLoanAmounts() throws IOException {
		String loanAmount1 = getData("TestDataSheet", "LoanAmount", "TC06_BusinessRulesAndDecisioning");
		String loanType1 = getData("TestDataSheet", "LoanType", "TC06_BusinessRulesAndDecisioning");
		String decision1 = getData("TestDataSheet", "Decision1", "TC06_BusinessRulesAndDecisioning");
		String decision2 = getData("TestDataSheet", "Decision2", "TC06_BusinessRulesAndDecisioning");
		String decision3 = getData("TestDataSheet", "Decision3", "TC06_BusinessRulesAndDecisioning");

		String loanAmount2 = getData("TestDataSheet", "LoanAmount", "TC06_BusinessRulesAndDecisioning2");
		String loanType2 = getData("TestDataSheet", "LoanType", "TC06_BusinessRulesAndDecisioning2");
		String decision11 = getData("TestDataSheet", "Decision1", "TC06_BusinessRulesAndDecisioning2");
		String decision22 = getData("TestDataSheet", "Decision2", "TC06_BusinessRulesAndDecisioning2");
		String decision33 = getData("TestDataSheet", "Decision3", "TC06_BusinessRulesAndDecisioning2");
		return new Object[][] { { loanAmount1, loanType1, decision1, decision2, decision3 }, { loanAmount2, loanType2, decision11, decision22, decision33 } };
	}

	
	
	
	
	

	

}