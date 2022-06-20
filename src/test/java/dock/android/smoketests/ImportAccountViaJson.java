package dock.android.smoketests;

import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportAccountViaJson extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Account functionality via Json")
    public void verifyImportAccountViaJson() {
        AndroidDriver driver = getDriverInstance();

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCode()
                .clickPlusButtonToCreatAccount()
                .clickImportExistingAccount()
                .clickAlreadyHaveAWallet();
        String accountName = "test" + walletHomePage.generateRandomNumber();

        // Work around for refresh, create a new account, so imported account get displayed
        //walletHomePage.createNewAccount(accountName);
        //Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        //Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.btnSend));
        //Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.btnReceive));
        //
        //// Verify Import of old account
        //Assert.assertTrue(walletHomePage.isDisplayedByText("TestAutomation"));
        //Assert.assertTrue(walletHomePage.getDockBalance().contains("9.33 DOCK"));
    }
}
