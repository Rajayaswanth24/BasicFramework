package ObjectRepository;

import org.openqa.selenium.By;

public class LoginPage {
	public static final By txtUserName = By.xpath("//div[@class='field user']");
	public static final By txtPassword = By.xpath("//div[@class='field password']");
	public static final By btnLogin = By.xpath("//div[@id='submit_row']");
	public static final By loginErrorMessage=By.xpath("//(div[@id='error'])");
}
