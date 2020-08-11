package LibraryFiles;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class CreateLogger {
	public static Logger LOGGER = Logger.getLogger(CreateLogger.class);
	private static String strlogDir = new SimpleDateFormat("MMddyy").format(new Date());
	private static SimpleDateFormat strLogFileName = new SimpleDateFormat("MMddyy_HHmmss");

	public static void tCreateLogger() {
		try {
			String strEnv = ReUsableLibrary.getElementFromPropFile("Environment");
			String strEnvDir = ".//LogFiles//" + strEnv;
			ReUsableLibrary.createDir(strEnvDir);
			String release = ReUsableLibrary.getElementFromPropFile("Release");
			String strModuleDir = strEnvDir + "//" + release;
			ReUsableLibrary.createDir(strModuleDir);
			String logDirPath = strModuleDir + "//" + strlogDir;
			ReUsableLibrary.createDir(logDirPath);
			String logFilePath = logDirPath + "//log_" + strEnv + "_" + strLogFileName.format(new Date()) + ".txt";

			FileAppender appender = new FileAppender();
			appender.setName("MyFileAppender");
			appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m %n"));
			appender.setFile(logFilePath);
			appender.setAppend(true);
			appender.setThreshold(Level.INFO);
			appender.activateOptions();

			LOGGER.addAppender(appender);

			File logFile = new File(logFilePath);
			String strLogFilePath = logFile.getCanonicalPath();

			System.out.println(" Logger Path ::" + strLogFilePath);
		} catch (Exception e) {
			System.out.println("Error Occured while creating the Logger Object..::" + e.getMessage());
		}
	}

}
