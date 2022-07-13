package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportAccountViaJsonAndVerifyTokensHistory extends BaseTestCaseAndroid {
    String accountName;

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Account functionality via Json")
    public void verifyImportAccountViaJsonAndTokensHistory() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        accountName = "test" + walletHomePage.generateRandomNumber();
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

        // Click the imported account to see the history
        walletHomePage.clickAccountDetails(accountName);
        Assert.assertTrue(walletHomePage.isDisplayedByText("3 DOCK"));
    }

 //   @Test(dependsOnMethods = "verifyImportAccountViaJsonAndTokensHistory", groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")
    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")

    public void verifyReceiveButton() {
        AndroidDriver driver = getDriverInstance();
        accountName = "TestAutomation";

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickAccountDetails(accountName);
    }
}
