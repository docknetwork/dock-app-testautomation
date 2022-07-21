package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class RemoveWallet extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Remove wallet functionality")
    public void verifyRemoveWallet() {
        AndroidDriver driver = getDriverInstance();
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickSettings()
                .clickRemoveWallet()
                .enterPassCodeOneTime()
                .clickSkip()
                .clickRemoveOnFinalNotificationMessage();
        // Verify Wallet has been removed
        Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.importExistingWallet));
    }
}
