package LibraryFiles;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptExecutorFunctions extends ReUsableLibrary {
	JavascriptExecutor js = (JavascriptExecutor) driver;

	public void changeVisibilityOfHiddenElement(WebElement element) {
		String str = "arguments[0].style.visibility='visible';";
		js.executeScript(str, element);
	}

	public void changeCSSOfElement(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
		js.executeScript("document.getElementsByName('q')[0].style.left='40%'");
		js.executeScript("arguments[0].setAttribute('style', 'top:22.3333px')", element);
	}

	public void DropdownWithSmallScroller() throws InterruptedException {
		driver.get("https://accounts.google.com/SignUp?service=mail&hl=en&continue=http%3A%2F%2Fmail.google.com%2Fmail%2F%3Fpc%3Den-ha-apac-in-bk-refresh13&utm_campaign=en&utm_source=en-ha-apac-in-bk-refresh13&utm_medium=ha");
		driver.manage().window().maximize();
		js.executeScript("window.scrollBy(0,600)");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//strong[text()='Location']/parent::label/div[1]")).click();
		Thread.sleep(1000);
		WebElement element = driver.findElement(By.xpath(".//*[@id='CountryCode']/div[2]"));
		Long height = (Long) (js.executeScript("return arguments[0].scrollHeight;", element));
		System.out.println("The height of the scroller area is " + height.toString());
		Long newScrollHeight = 0L;
		while (height > newScrollHeight) {
			((JavascriptExecutor) driver).executeScript("return arguments[0].scrollTop = " + newScrollHeight.toString() + ";", element);
			newScrollHeight = newScrollHeight + 10;
			Thread.sleep(1000);
		}
	}

	public void enableElement() {
		js.executeScript("document.getElementsByName('q')[0].removeAttribute('disabled');");
	}

	public void disableElement() {
		js.executeScript("document.getElementsByName('q')[0].setAttribute('disabled', '');");
	}

	public void generateAlert() throws InterruptedException {
		js.executeScript("alert('Test Case Execution Is started Now..');");
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
	}

	public String getDomain() {
		String DomainUsingJS = (String) js.executeScript("return document.domain");
		return DomainUsingJS;
	}

	public void scrollToView(WebElement element) throws InterruptedException {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(1000);
	}
}