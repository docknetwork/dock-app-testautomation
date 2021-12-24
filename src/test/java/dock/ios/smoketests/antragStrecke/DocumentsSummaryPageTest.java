package dock.ios.smoketests.antragStrecke;

import org.testng.annotations.Test;

import dock.ios.pageobjects.BaseTestCaseIOS;
import dock.utilities.TestGroup;
import io.appium.java_client.ios.IOSDriver;

public class DocumentsSummaryPageTest extends BaseTestCaseIOS {

    @Test(groups = TestGroup.SmokeTest, description = "End to end Test to check links working successfully.")
    public void verificationOfDocumentsLinksSummaryPage() {

        IOSDriver driver = getDriverInstance();
    }
}
