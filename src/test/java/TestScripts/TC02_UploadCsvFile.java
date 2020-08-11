package TestScripts;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import LibraryFiles.ReUsableLibrary;
import ObjectRepository.HomePage;
import ObjectRepository.ManualUploadPage;
import junit.framework.Assert;

public class TC02_UploadCsvFile extends ReUsableLibrary {
	
	public void UploadFile(String Filepath,String usrname ,String password) throws IOException {
		if(Filepath.isEmpty()||Filepath.equals(null)) {
			String userDir=System.getProperty("user.dir");
			Filepath=userDir+"\\TestData\\Cancel_CXLGoesToResolved(2).xlsx";
		}
		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		launchApplication();
		Login(usrname,password);
		String extension=FilenameUtils.getExtension(Filepath);
		driver.findElement(HomePage.manualUpload).click();//Clicking on the Manual Upload button from home screen
		logger.log(LogStatus.INFO, "Manual Upload Page opened");
		if(Filepath.toLowerCase().contains("_CXL".toLowerCase())) {
			
			wait.until(ExpectedConditions.presenceOfElementLocated(ManualUploadPage.cancelUploadButton));
			logger.log(LogStatus.INFO, "Upload Button was clicked");
			driver.findElement(ManualUploadPage.cancelUploadButton).click();//Clicking on Upload CSV button from manual upload screen		
		}
		else{
			wait.until(ExpectedConditions.presenceOfElementLocated(ManualUploadPage.excelUploadButton));
			logger.log(LogStatus.INFO, "Upload Button was clicked");
			driver.findElement(ManualUploadPage.excelUploadButton).click();//Clicking on Upload excel button from manual upload screen
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(ManualUploadPage.chooseFileButton));
		driver.findElement(ManualUploadPage.chooseFileButton).sendKeys(Filepath);
		wait.until(ExpectedConditions.presenceOfElementLocated(ManualUploadPage.submitButton));
		driver.findElement(ManualUploadPage.submitButton).click();
		custom3Sleep();
		wait.until(ExpectedConditions.presenceOfElementLocated(ManualUploadPage.uploadMessage));
		logger.log(LogStatus.INFO, "Waiting for the upload message to appear");
		String UploadMessage=driver.findElement(ManualUploadPage.uploadMessage).getText();
		Assert.assertEquals("File processed successfully!!", UploadMessage);
	
	}

}