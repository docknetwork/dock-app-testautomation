package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class BackupWallet extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Backup wallet functionality")
    public void verifyBackupwallet() {
        AndroidDriver driver = getDriverInstance();

        String password = "123456789Qw!";

        // Try to Backup the Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickSettings()
                .clickBackupWallet()
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickNext();

        // Verify that wallet-backup***.Json is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("walletBackup-"));
        Assert.assertTrue(walletHomePage.isDisplayedByText(".json"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Link Sharing"));
    }
}
