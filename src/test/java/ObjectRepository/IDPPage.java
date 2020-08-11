package ObjectRepository;

import org.openqa.selenium.By;

public class IDPPage { 
		public static final By idpCasesButton = By.xpath("//span[text()='View IDP Cases']");
		public static final By pendingRequests = By.xpath("//h2[text()='Pending Requests']");
		public static final By exceptionRequests = By.xpath("//h2[text()='Exceptions']");
		public static final By resolvedRequests=By.xpath("//h2[text()='Resolved Requests']");
		public static final By idpCaseStatusList =By.xpath("//a[@data-test-id='2016072109335505834280']");
		public static final By searchbyMPOTxt=By.id("MPOUserName");
		public static final By showall_btn=By.xpath("//div[@node_name='TEIDPDashboard']//button[contains(text(),'Show All')]");
		public static final By idpTableFirstCaseCell=By.xpath("//div[@id='EXPAND-OUTERFRAME']//table[@pl_prop_class='Assign-WorkBasket']/tbody/tr[2]/td[1]//a[@class='Work_grid_item']");
		public static final By checkBox=By.xpath("(//input[@type='checkbox' and @class='checkbox chkBxCtl'])[2]");
		public static final By retryIdp=By.xpath("//button[text()='Retry IDP']");
		public static final By monthSelector=By.xpath("//span[@id='monthSpinner']/input[@type='text']");
		public static final By yearSelector=By.xpath("//span[@id='yearSpinner']/input[@type='text']");
		public static final By updatedTag=By.xpath("//a[text()='Updated']");
		public static final By updateSelection=By.xpath("//button[text()='Update Selection']");
		public static final By calendarImg=By.xpath("//span[@aria-label='IDP Date']/img");
		public static final By closeTag=By.xpath("//a[@class='close-link-days']");
		public static final By exceptionCaseId=By.xpath("//span[@data-test-id='20141009112850013217103']");
		public static final By removeLine=By.xpath("//button[text()='Remove Line']");
		
}