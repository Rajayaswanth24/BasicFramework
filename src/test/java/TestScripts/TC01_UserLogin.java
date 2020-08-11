package TestScripts;

import java.io.IOException;

import LibraryFiles.ReUsableLibrary;

public class TC01_UserLogin extends ReUsableLibrary {

	public void UserLogin() throws IOException {
		launchApplication();
		Login("AutoTestUser","rules");
		LogOut();
	}

}
