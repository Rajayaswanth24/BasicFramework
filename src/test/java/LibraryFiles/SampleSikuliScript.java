package LibraryFiles;

import java.io.IOException;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class SampleSikuliScript extends ReUsableLibrary {
	public static void main(String[] args) throws InterruptedException, IOException, FindFailed {
		OpenWDInstance();
		driver.get("http://www.google.com");
		Screen screen = new Screen();
		Pattern image = new Pattern("C:\\Users\\sw20041258\\eclipse-workspace\\Framework\\SupportingSoftware\\GmailLink.jpg");
		screen.wait(image, 10);
		screen.click(image);
	}
}
