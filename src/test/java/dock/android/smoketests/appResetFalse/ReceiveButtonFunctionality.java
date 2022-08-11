package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ReceiveButtonFunctionality extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")
    public void verifyReceiveButton() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.checkAccountOrElseCreateIt(accountName);
        walletHomePage.clickAccountDetails(accountName)
                .clickReceive()
                .clickCopyAddress();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Copied"));

        walletHomePage.clickShareAddress().waitABit(3000);
        Assert.assertTrue(walletHomePage.isDisplayedByText("Copy"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Nearby Share"));
    }
}
