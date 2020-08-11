package Listeners;

import java.io.IOException;
import LibraryFiles.CreateLogger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import LibraryFiles.ReUsableLibrary;

public class MyListener implements ITestListener, ISuiteListener {
	public static List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();
	public static Date endDate;
	public static Date startDate;
	public static String suiteName;

	public void onFinish(ISuite suite) {
		try {
			ReUsableLibrary.driver.quit();
		} catch (Exception e) {
			System.out.println("All the open windows closed already ");
		}
	}

	public void onStart(ISuite arg0) {
		CreateLogger.tCreateLogger();
		CreateLogger.LOGGER.info("CREATED LOGGER BEFORE SUITE START");
	}

	public void onFinish(ITestContext context) {
		endDate = context.getEndDate();
	}

	public void onStart(ITestContext context) {
		startDate = context.getStartDate();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestFailure(ITestResult testResult) {
		try {
			String ssForFailedTests = ReUsableLibrary.getElementFromPropFile("ScreenShotForFail");
			CreateLogger.LOGGER.info("TOOK SCREENSHOTS FOR THE FAILED TESTS");
			if (ssForFailedTests.equals("True"))
				ReUsableLibrary.takeScreenShot(testResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		failedtests.add(testResult.getMethod());
	}

	public void onTestSkipped(ITestResult testResult) {
		try {
			CreateLogger.LOGGER.info("TOOK SCREENSHOTS FOR THE SKIPPED TESTS");
			String ssForSkippeddTests = ReUsableLibrary.getElementFromPropFile("ScreenShotForSkip");
			if (ssForSkippeddTests.equals("True"))
				ReUsableLibrary.takeScreenShot(testResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		skippedtests.add(testResult.getMethod());
	}

	public void onTestStart(ITestResult arg0) {

	}

	public void onTestSuccess(ITestResult testResult) {
		try {
			CreateLogger.LOGGER.info("TOOK SCREENSHOTS FOR THE PASSED TESTS");
			String ssForPassedTests = ReUsableLibrary.getElementFromPropFile("ScreenShotForPass");
			if (ssForPassedTests.equals("True"))
				ReUsableLibrary.takeScreenShot(testResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		passedtests.add(testResult.getMethod());
	}
}
