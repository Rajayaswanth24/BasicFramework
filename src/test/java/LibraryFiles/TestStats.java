package LibraryFiles;

import java.util.Date;

public class TestStats extends Listeners.MyListener {
	public static int passedTestCases;
	public static int failedTestCases;
	public static int skippedTestCases;
	public static Date startDateAndTime;
	public static Date endDateAndTime;
	public static int total;
	public static long startTime;
	public static long endTime;
	public static long diffTime;
	public static long diffDays;

	public static int printTestStats() {
		passedTestCases = passedtests.size();
		failedTestCases = failedtests.size();
		skippedTestCases = skippedtests.size();
		startDateAndTime = startDate;
		endDateAndTime = endDate;
		total = passedTestCases + failedTestCases + skippedTestCases;
		startTime = startDateAndTime.getTime();
		endTime = endDateAndTime.getTime();
		diffTime = endTime - startTime;
		diffDays = diffTime / (1000 * 60 * 60 * 24);
		System.out.println("Number of Passed Tests :" + passedTestCases);
		System.out.println("Number of Failed Tests :" + failedTestCases);
		System.out.println("Number of Skipped Tests :" + skippedTestCases);
		System.out.println("Test Start Date And Time :" + startDateAndTime);
		System.out.println("Test End Date And Time :" + endDateAndTime);
		System.out.println("Time Taken :" + diffTime);
		System.out.println("Time Taken in Days :" + diffDays);
		CreateLogger.LOGGER.info("Printed Test Stats to Console");
		return total;

	}
}
