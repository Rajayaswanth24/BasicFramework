package LibraryFiles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import LibraryFiles.DateUtils;
import LibraryFiles.ReUsableLibrary;
import ObjectRepository.NonPickUpDate;

public class CheckExpressHubAndNonPickUpDate extends ReUsableLibrary {
	List<Date> dates= new ArrayList(); 
	List<WebElement> elements;
	WebElement nonPickUpCalendarValues;
	WebElement nonPickUpCalendarType;
	String expressCalendarType;
	String standardHubCalendarType;
	int val;
	
	public List<Date> ExpressDateCheck(String hub,int type ,String add) {
		launchApplication();
		Login("nonpickupadmin","rules");
		 WebDriverWait wait= new WebDriverWait(driver,40);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(NonPickUpDate.configurationButton));
		 driver.findElement(NonPickUpDate.configurationButton).click();
		 driver.switchTo().frame(NonPickUpDate.homePageFrame);
		 wait.until(ExpectedConditions.presenceOfElementLocated(NonPickUpDate.nonPickUpDateButton));
		 nonPickUpCalendarType= driver.findElement(NonPickUpDate.nonPickUpDateButton);
		 nonPickUpCalendarType.click();
		 driver.switchTo().defaultContent();
		 custom9Sleep();
		 driver.switchTo().frame(NonPickUpDate.calendarTypeHomePageFrame);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(NonPickUpDate.searchBar));
		 driver.findElement(NonPickUpDate.searchBar).sendKeys(hub);
		 driver.findElement(NonPickUpDate.searchBar).sendKeys(Keys.ENTER);
		 custom6Sleep();
		 val=type;
		
		 if(val==40) {
			 List<WebElement> hubDetails=driver.findElements(NonPickUpDate.isExpressHub);
			 List<WebElement> evenRowCalendar=driver.findElements(NonPickUpDate.calendarType);
			 Iterator<WebElement> itrhub = hubDetails.iterator();
			 Iterator<WebElement> itrEvenRows = evenRowCalendar.iterator();
			 while(itrhub.hasNext() && itrEvenRows.hasNext()){
				 WebElement w=itrhub.next();
				 WebElement e=itrEvenRows.next();
				 if(w.getText().equalsIgnoreCase("Yes")) {
					 expressCalendarType =e.getText();
					 //standardHubCalendarType =driver.findElement(NonPickUpDate.oddRowCalendarType).getText(); 
				 } 
				 else 
					 if(w.getText().equalsIgnoreCase("No")) {
						 standardHubCalendarType=e.getText();
					 }
			 }
			 
			 
		 }
		 else {
			 List<WebElement> hubDetails=driver.findElements(NonPickUpDate.isExpressHub);
			 List<WebElement> evenRowCalendar=driver.findElements(NonPickUpDate.calendarType);
			 Iterator<WebElement> itrhub = hubDetails.iterator();
			 Iterator<WebElement> itrEvenRows = evenRowCalendar.iterator();
			 while(itrhub.hasNext() && itrEvenRows.hasNext()){
				 WebElement w=itrhub.next();
				 WebElement e=itrEvenRows.next();
				 if(w.getText().equalsIgnoreCase("No")) {
					 standardHubCalendarType =e.getText();
					 //standardHubCalendarType =driver.findElement(NonPickUpDate.oddRowCalendarType).getText(); 
				 } 
				 else {
					 if(w.getText().equalsIgnoreCase("Yes")) {
						 expressCalendarType=e.getText();
					 }
				 } 
		 }
		 }
		 System.out.println(expressCalendarType);
		 System.out.println(standardHubCalendarType);
		 driver.switchTo().defaultContent();
		 driver.findElement(NonPickUpDate.configurationButton).click();
		
		 driver.switchTo().frame(NonPickUpDate.homePageFrame);
		 custom9Sleep();
		Actions actions = new Actions(driver);
		 wait.until(ExpectedConditions.presenceOfElementLocated(NonPickUpDate.nonPickUpDatesButton));
		
		 nonPickUpCalendarValues= driver.findElement(NonPickUpDate.nonPickUpDatesButton);
		 actions.moveToElement(nonPickUpCalendarValues).click().build().perform();
		 driver.switchTo().defaultContent();
		 custom9Sleep();
		 driver.switchTo().frame(NonPickUpDate.calendarPageFrame);
		 custom9Sleep();
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='pySearchTerm']")));
			WebElement searchBar=driver.findElement( By.xpath("//input[@id='pySearchTerm']"));
			if(val==40) {
				driver.findElement(By.xpath("//input[@id='pySearchTerm']")).sendKeys(expressCalendarType);
				System.out.println(expressCalendarType);
			}
			else 
			{
				driver.findElement(By.xpath("//input[@id='pySearchTerm']")).sendKeys(standardHubCalendarType);
				System.out.println("Standard Hub");
			}
			driver.findElement(By.xpath("//i[@class='pi pi-search']")).click();
			custom9Sleep();
			ExpressDateValue(add,val);
			driver.switchTo().defaultContent();
			LogOut();
			return dates;
		 
	}
	public String ExpressDateValue(String add,Integer x) {
		WebElement previousPage=null;
		WebElement nextPage=null;
		DateUtils nd= new DateUtils();
		int n=0;
		//add.isBlank()
		if(!(add.isEmpty()||add==null)) {
			
				List<WebElement> webElements=driver.findElements(By.xpath("//a[text()='+ Add record']"));
				System.out.println(webElements.size());
				//By.xpath("//input[@id='pySearchTerm'][1]");
				WebElement addRecord=webElements.get(webElements.size()-1);
				addRecord.click();
				
				if(x==40) {
					driver.findElement(By.xpath("(//input[@type='text' and @size='20'])[1]")).sendKeys(expressCalendarType);
				
			}
			else {
				driver.findElement(By.xpath("(//input[@type='text' and @size='20'])[1]")).sendKeys(standardHubCalendarType);
			}
				driver.findElement(By.xpath("(//input[@type='text' and @size='20'])[2]")).sendKeys(add);
				custom6Sleep();
				addRecord.click();
			return "";
		}
		else {
		do {
			try
			{
				n++;
				nextPage=driver.findElement(By.xpath("//a[@title='Next Page']"));
				System.out.println("next page"+nextPage.getText());
			}
			catch(Exception e)
			{
			}
		}while(n<=2);
		n=0;
		do {
			try
			{
				n++;
				previousPage= driver.findElement(NonPickUpDate.previousPage);
				System.out.println("previous page"+previousPage.getText());
			}
			catch(Exception e)
			{
			}
		}while(n<=2);
		if (previousPage !=null||nextPage==null)
		{
			custom9Sleep();
			elements= driver.findElements(NonPickUpDate.nonPickUpDates);
			for(WebElement el:elements) {
				String ndDate=nd.getParseDateyyyyMMddFormat(el.getText());
				Date date1=null;
				try {
					date1 = new SimpleDateFormat("yyyyMMdd").parse(ndDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(ndDate);	
				dates.add(date1);
			}
			return "";
		}
		else {
			custom9Sleep();
			elements= driver.findElements(NonPickUpDate.nonPickUpDates);
			for(WebElement el:elements) {
				String ndDate=nd.getParseDateyyyyMMddFormat(el.getText());
				Date date1=null;
				try {
					date1 = new SimpleDateFormat("yyyyMMdd").parse(ndDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(ndDate);	
				dates.add(date1);		
			}
			int p = 0;
			do {
				try {
					p++;
					nextPage.click();
					
					Thread.sleep(200);
				} catch (Exception e) {
				}
			} while (p <= 2);
			ExpressDateValue(add,val);
		}
		return "";
		}
	}
	

}
