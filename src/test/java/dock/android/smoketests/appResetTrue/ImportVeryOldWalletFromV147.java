package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportVeryOldWalletFromV147 {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Import Wallet which was generated from V-147")
    public void verifyImportWalletGeneratedFromVersion147() {

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("walletBackup-Mike.json")
                .enterPassword("Test1234!")
                .clickNext()
                .enterPassCodeTwoTimes();
                // .clickDoThisLater();

         Assert.assertTrue(walletHomePage.isDisplayedByText("Beta Testing Credential"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
