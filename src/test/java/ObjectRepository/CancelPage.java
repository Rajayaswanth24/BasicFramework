package ObjectRepository;

import org.openqa.selenium.By;

public class CancelPage {

	// User HomePage
	public static final By cancelCasesButton = By.xpath("//span[text()='View Cancel Cases']");
	public static final By manualUpload = By.xpath("//span[text()='Manual Upload']");
	public static final By idpCasesButton = By.xpath("//span[text()='View IDP Cases']");
	public static final By casesButton = By.xpath("//span[text()='Cases']");
	public static final By caseSummary = By.xpath("//h3[text()='Cancellations case summary']");
	public static final By pendingRequests = By.xpath("//h2[text()='Pending']");
	public static final By exceptionRequests = By.xpath("//h2[text()='Exceptions']");
	public static final By resolvedRequests=By.xpath("//h2[text()='Resolved Requests']");
	public static final By cancelCaseStatusList =By.xpath("//a[@data-test-id='2016072109335505834280']");
	public static final By cancelCaseCreatedStatus=By.xpath("//a[@class='ellipsis']");
	public static final By cancelCaseExceptionSubCasesId=By.xpath("//div[@class='oflowDivM ' and contains(text(),'XL') ]");
	public static final By cancelCaseExceptionMessageSymbol=By.xpath("//img[@data-test-id='20181025114956063931141']");
	public static final By cancelCaseExceptionMessage=By.xpath("//span[@data-test-id='2018103006192905673344']");
	public static final By cancelCaseDownloadCase=By.xpath("//button[text()='Download Case Details']");
	public static final By addAttachment=By.xpath("//a[@data-test-id='2014121601304102071215']");
	
	
	
	
}
