package ReadOrWriteDataFiles;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.testng.Assert;

import LibraryFiles.ReUsableLibrary;

import java.net.MalformedURLException;
import java.net.URL;

public class ReadPDF extends ReUsableLibrary {
	public String pdftoText(String fileName) {
		PDFParser parser;
		String parsedText = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File(fileName);
		if (!file.isFile()) {
			System.err.println("File " + fileName + " does not exist.");
			return null;
		}
		try {
			parser = new PDFParser(new FileInputStream(file));
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e);
			return null;
		}
		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(5);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.err.println("An exception occured in parsing the PDF Document." + e);
		} finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsedText;
	}

	public void readPDFOpenedInBrowser() throws InterruptedException, IOException {

		// To check the page URL if it is containing PDF or not
		driver.get("http://www.seleniummaster.com/sitecontent/images/Selenium_Master_Test_Case_Base_Template.pdf");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
		URL url = new URL(getURL);
		BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
		PDFParser pdfParser = new PDFParser(fileToParse);
		pdfParser.parse();
		String pdftxt = new PDFTextStripper().getText(pdfParser.getPDDocument());
		if (pdftxt.contains("Selenium Master Testcase Template"))
			System.out.println("Text displayed in the PDF");
		else
			System.out.println("Text not displayed in the PDF");
		pdfParser.getPDDocument().close();

	}
}
