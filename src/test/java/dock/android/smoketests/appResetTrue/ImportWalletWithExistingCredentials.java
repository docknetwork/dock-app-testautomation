package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportWalletWithExistingCredentials {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Import Wallet, import of existing credentials")
    public void verifyImportWalletWithExistingCredentials() {

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("walletBackup-Mike.json")
                .enterPassword("Test1234!")
                .clickNext()
                .enterPassCodeTwoTimes()
                .waitABit(2000);

        // Verify that credential is imported
        Assert.assertTrue(walletHomePage.isDisplayedByText("Beta Testing Credential"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("May 10, 2022"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
