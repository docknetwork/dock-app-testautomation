package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
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

        // Clear any file sharing screen
        if(walletHomePage.checkElementExist(Selector.contentResourceID("android:id/contentPanel"))){
            walletHomePage.click(Selector.contentResourceID("android:id/contentPanel"));
        }

        // Try to Backup the Wallet
        walletHomePage.clickSettings()
                .clickBackupWallet().waitABit(2000);
        walletHomePage.enterPassword(password)
                .enterConfirmPassword(password);

        walletHomePage.clickNext();
        walletHomePage.waitABit(2000);

        // Verify that wallet-backup***.Json is displayed
//        Assert.assertTrue(walletHomePage.isDisplayedByText("Share"));
        walletHomePage.waitElementVisibility("Share");
        walletHomePage.waitElementVisibility(Selector.contentResourceID("android:id/contentPanel"));
        walletHomePage.click(Selector.contentResourceID("android:id/contentPanel"));
        walletHomePage.waitElementVisibility("Wallet exported successfully");
        Assert.assertTrue(walletHomePage.isDisplayedByText("Settings"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
