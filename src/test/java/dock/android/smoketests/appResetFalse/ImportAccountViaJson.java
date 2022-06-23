package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportAccountViaJson extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Account functionality via Json")
    public void verifyImportAccountViaJson() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.enterPassCodeOneTime()
                .clickPlusButtonToCreatAccount()
                .clickImportExistingAccount()
                .clickUploadJsonFile()
                .uploadFile("importAccount.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterNewAccountName(accountName)
                .clickNext();
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("3 DOCK"));
    }
}