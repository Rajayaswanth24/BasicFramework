package TestScripts;

import java.io.File;
import Listeners.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import LibraryFiles.CreateLogger;
import LibraryFiles.ReUsableLibrary;
import LibraryFiles.TestStats;

@Listeners(MyListener.class)
public class BaseSuiteFile extends ReUsableLibrary {

	private static String strHtmlDir = new SimpleDateFormat("MMddyy").format(new Date());
	private static SimpleDateFormat strExtentReportName = new SimpleDateFormat("MMddyy_HHmmss");
	//normal comment
		@BeforeSuite
	public void beforeSuite() {
		CreateLogger.LOGGER.info("Suite STARTED");
		System.out.println("Suite Started");
	}

	@BeforeClass
	public void setup()  {
			}

	@Parameters({ "optional-browser" })
	@BeforeMethod
	public void beforeMethod(@Optional("optionalvalue") String browserFromSuiteXML, Method method) throws IOException {
		try {
			if (browserFromSuiteXML.equals("optionalvalue")) {
				System.out.println("Browser value not passed in the suite xml");
				OpenWDInstance();
				logger = extent.startTest(method.getName(), method.getAnnotation(Test.class).description());
			} else {
				// Multi Browser SetUp
				if (browserFromSuiteXML.equals("Firefox")) {
					String GeckoDriverPath = getElementFromPropFile("GeckoDriverPath");
					System.setProperty("webdriver.gecko.driver", GeckoDriverPath);
					driver = new FirefoxDriver();
				} else if (browserFromSuiteXML.equals("IE")) {
					String ieDriverPath = getElementFromPropFile("InternetExplorerDriverPath");
					System.setProperty("webdriver.ie.driver", ieDriverPath);
					driver = new InternetExplorerDriver();
				} else if (browserFromSuiteXML.equals("Chrome")) {
					String userDir=System.getProperty("user.dir");
					
					String chromeDriverPath =userDir+"//SupportingSoftware//chromedriver.exe";
					System.setProperty("webdriver.chrome.driver", chromeDriverPath);
					driver = new ChromeDriver();
				} else if (browserFromSuiteXML.equals("PhantomJS")) {
					String phantomJSexePath = getElementFromPropFile("PhantomJSPath");
					File file = new File(phantomJSexePath);
					System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
					driver = new PhantomJSDriver();
				}
				driver.manage().window().maximize();
				System.out.println("Created Driver Instance and Launched Browser: " + browserFromSuiteXML);
				extent.addSystemInfo("Browser", browserFromSuiteXML);
				logger = extent.startTest(method.getName(), method.getAnnotation(Test.class).description());
				logger.log(LogStatus.INFO, "Created Driver Instance And Launched Browser: " + browserFromSuiteXML);
			}
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed to Create Driver Instance and Launched Browser: " + e.toString());
			Assert.fail("Failed to Create Driver Instance and Launched Browser: " + e.toString());
		}

	}

	@BeforeTest
	public void beforeTest() throws IOException {
		try {
			// Extent Report Configuration
			String environment = getElementFromPropFile("Environment");
			String projectName = getElementFromPropFile("ProjectName");
			String release = getElementFromPropFile("Release");
			String moduleName = getElementFromPropFile("ModuleName");
			String strEnv = ReUsableLibrary.getElementFromPropFile("Environment");
			String strEnvDir = ".//TestReports//HTMLReports//" + strEnv;
			ReUsableLibrary.createDir(strEnvDir);
			String strModuleDir = strEnvDir + "//" + release;
			ReUsableLibrary.createDir(strModuleDir);
			String htmlDirPath = strModuleDir + "//" + strHtmlDir;
			ReUsableLibrary.createDir(htmlDirPath);
			String htmlReportPath = htmlDirPath;
			String file = htmlReportPath + "\\STMExtentReport" + strExtentReportName.format(new Date()) + ".html";

			extent = new ExtentReports(file, false);
			extent.addSystemInfo("Project Name", projectName).addSystemInfo("Environment", environment).addSystemInfo("Release", release).addSystemInfo("Module Name", moduleName);
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
			System.out.println("Extent Report Configuration Completed");
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Failed To Configure Extent Reports" + e.toString());
			Assert.fail("Failed To Configure Extent Reports" + e.toString());
		}
	}

		}

	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		String userDir=System.getProperty("user.dir");
		String downloadFilePath=userDir+"//DownloadCases//";
		ReUsableLibrary.removeDir(downloadFilePath);
		driver.close();
		extent.endTest(logger);
	}

	@AfterTest
	public void afterTest() {
		custom3Sleep();
		extent.flush();
		extent.close();
		
	}

	@AfterClass
	public void afterClass() {

	}

	@AfterSuite
	public void afterSuite() {
		TestStats.printTestStats();
		CreateLogger.LOGGER.info("Suite ENDED");
		System.out.println("Suite Ended");
	}

}
