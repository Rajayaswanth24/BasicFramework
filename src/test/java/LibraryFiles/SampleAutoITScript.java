package LibraryFiles;

import autoitx4java.AutoItX;

public class SampleAutoITScript extends ReUsableLibrary{

	public static void sampleAutoIT() {
		try {
			AutoItX x = new AutoItX();
			x.run("notepad.exe", "", AutoItX.SW_SHOW);
			x.winActivate("Untitled - Notepad");
			x.winWaitActive("Untitled - Notepad");
			x.send("Typed Text In NotePad Using Autoit");
			x.winClose("Untitled - Notepad");
			x.winWaitActive("Notepad", "Save");
			x.send("!n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
