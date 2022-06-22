package dock.android.smoketests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportWalletWithExistingAccountAndCredentials {
    AndroidDriver driver;

    @Test(priority = 0, groups = TestGroup.SmokeTest, description = "Test to verify Wallet Import Wallet, import of existing account and credentials")
    public void verifyImportWalletWithExistingAccountAndCredentials() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("dockWalletBackup.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterPassCodeTwoTimes()
                .clickDoThisLater();

        // Verify Import of old account
        Assert.assertTrue(walletHomePage.isDisplayedByText("TestAutomation"));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("9.33 DOCK"));

        // Verify that Account is imported
        walletHomePage.clickCredentials();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Bsc in Computer Science"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Hans M\u00FCller"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("5/9/2021"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
