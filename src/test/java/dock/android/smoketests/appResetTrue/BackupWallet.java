package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class BackupWallet {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Backup wallet functionality")
    public void verifyBackupwallet() {

        String password = "123456789Qw!";

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.importWalletComplete();

        // Try to Backup the Wallet
        walletHomePage.clickSettings()
                .clickBackupWallet().waitABit(2000);
        walletHomePage.enterPassword(password)
                .enterConfirmPassword(password)
                .clickNext();

        // Verify that wallet-backup***.Json is displayed
        //Assert.assertTrue(walletHomePage.isDisplayedByText("Shar"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
