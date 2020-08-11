package LibraryFiles;
/*
import com.qc.ClassFactory;
import com.qc.IBaseFactory;
import com.qc.IList;
import com.qc.IProjectDescriptor;
import com.qc.IRun;
import com.qc.IRunFactory;
import com.qc.ITDConnection4;
import com.qc.ITSTest;
import com.qc.ITestSet;
import com.qc.ITestSetFolder;
import com.qc.ITestSetTreeManager;
import com4j.Com4jObject;

public class HPALMIntegration {
	public static void sendRequest(String strTestCaseId, String strStatus) {

		ITDConnection4 connection = null;

		// QC url
		String url = "http://<QCURL>/qcbin";
		// username for login
		String username = "<QC USERNAME>";
		// password for login
		String password = "<QC PASSWORD";
		// domain
		String domain = "TRAINING";

		// project
		String project = "<ProjectName>";
		String strTestLabPath = "<Root\\TestLabFolderName>";
		String strTestSetName = "<TestSetName>";

		try {

			// QC Connection
			connection = ClassFactory.createTDConnection();
			connection.initConnectionEx(url);
			connection.login(username, password);

			// To get all projects name
			for (Com4jObject obj : connection.getAllVisibleProjectDescriptors()) {
				IProjectDescriptor pd = obj.queryInterface(IProjectDescriptor.class);

			}

			connection.connect(domain, project);

			// To get the Test Set folder in Test Lab
			ITestSetTreeManager objTestSetTreeManager = (connection.testSetTreeManager()).queryInterface(ITestSetTreeManager.class);
			ITestSetFolder objTestSetFolder = (objTestSetTreeManager.nodeByPath(strTestLabPath)).queryInterface(ITestSetFolder.class);

			IList tsTestList = objTestSetFolder.findTestSets(null, true, null);

			for (int i = 1; i <= tsTestList.count(); i++) {
				Com4jObject comObj = (Com4jObject) tsTestList.item(i);
				ITestSet tst = comObj.queryInterface(ITestSet.class);

				if (tst.name().equalsIgnoreCase(strTestSetName)) {

					IBaseFactory testFactory = tst.tsTestFactory().queryInterface(IBaseFactory.class);

					IList testInstances = testFactory.newList("");

					// To get Test Case ID instances
					for (Com4jObject testInstanceObj : testInstances) {
						ITSTest testInstance = testInstanceObj.queryInterface(ITSTest.class);

						if (testInstance.testName().equalsIgnoreCase(strTestCaseId)) {
							IRunFactory runfactory = testInstance.runFactory().queryInterface(IRunFactory.class);

							IRun run = runfactory.addItem("Selenium").queryInterface(IRun.class);
							run.status(strStatus);
							run.post();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			connection.logout();
			connection.disconnect();
			connection.releaseConnection();
		}
	}

}
*/

// Install HPQC
// Convert OTAClient.dll to jar
// Add the jar to pom xml
// http://seleniumintegration.blogspot.com/2014/08/integrating-selenium-with-hp-test.html