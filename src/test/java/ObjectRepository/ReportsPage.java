package ObjectRepository;

import org.openqa.selenium.By;

public class ReportsPage {

	// User HomePage
	public static final By cancellationReports = By.xpath("//a[@href='#' and @title='Cancellation Status Report']");
	public static final By showAllCancellationReports = By.xpath("//a[@title='Audit report created to show all cancel cases' and text()='Cancellation Report']");
	public static final By allDefaultFieldsInReport=By.xpath("//table[@class='report_FilterTable']//tr/td/a");
	public static final By searchBar=By.xpath("//input[@class='autocomplete_input']");
	//input[@searchmode='bb']
	//*[@id="PEGA_GRID1"]/div[1]/div[2]/table/tbody/tr/td[2]//tbody/tr/th

	//table[@class='report_FilterTable']//tr/td/a
}
