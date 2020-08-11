package ObjectRepository;

import org.openqa.selenium.By;

public class HomePage {

	// User HomePage
	public static final By cancelCasesButton = By.xpath("//span[text()='View Cancel Cases']");
	
	public static final By manualUpload = By.xpath("//span[text()='Manual Upload']");
	public static final By idpCasesButton = By.xpath("//span[text()='View IDP Cases']");
	public static final By casesButton = By.xpath("//span[text()='Cases']");
	public static final By btnLogOut = By.xpath("//i[@class='icons avatar name-n ']");
	public static final By lnkLogOff = By.xpath("//span[text()='Log off']");
	public static final By homePageTitle = By.xpath("//a[@data-test-id='2014101912034700464268']");
	public static final By reports = By.xpath("//span[text()='Reports']");
	
}
