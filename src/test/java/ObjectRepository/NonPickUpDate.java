package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NonPickUpDate {

	// User HomePage
	public static final By configurationButton = By.xpath("//span[text()='Configuration']");
	public static final By nonPickUpDateButton= By.xpath("(//div[@class='pzbtn-mid' and text()='Edit'])[2]");
	public static final By nonPickUpDatesButton= By.xpath("(//div[@class='pzbtn-mid' and text()='Edit'])[1]");
	public static final By searchBar= By.xpath("(//input[@id='pySearchTerm'])[1]");
	public static final By oddRows=By.xpath("//tr[@class='oddRow cellCont']");
	public static final By oddRowsIsExpressHub=By.xpath("//tr[@class='oddRow cellCont']/td[@data-attribute-name='IsExpress']");
	public static final By isExpressHub=By.xpath("//td[@data-attribute-name='IsExpress']");
	public static final By calendarType=By.xpath("//td[@data-attribute-name='Calendar']");
	public static final By evenRows=By.xpath("//tr[@class='evenRow cellCont']");
	public static final By evenRowsIsExpressHub=By.xpath("//tr[@class='evenRow cellCont']/td[@data-attribute-name='IsExpress']");
	public static final By oddRowCalendarType=By.xpath("//tr[@class='oddRow cellCont']/td[@data-attribute-name='Calendar']");
	public static final By evenRowsCalendarType=By.xpath("//tr[@class='evenRow cellCont']/td[@data-attribute-name='Calendar']");
	public static final By nextPage=By.xpath("//a[@title='Next']");
	public static final By calendarTypeHomePage=By.xpath("//input[@value='Non Pickup Table']");
	public static final By previousPage=By.xpath("//a[@title='Previous Page']");
	public static final By nonPickUpDates=By.xpath("//td[@data-attribute-name='Non Pick Up Date(Required)']");
	public static final By refreshButton=By.xpath("//img[@src='webwb/pysocialrefreshicon_11727967502.png!!.png']");
	public static final String homePageFrame="PegaGadget1Ifr";
	
	public static final String calendarPageFrame="PegaGadget3Ifr";
	public static final String calendarTypeHomePageFrame="PegaGadget2Ifr";
	//input[@value='Non Pickup Table']
	
	
}
