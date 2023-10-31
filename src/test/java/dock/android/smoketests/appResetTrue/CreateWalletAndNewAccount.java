package dock.android.smoketests.appResetTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class CreateWalletAndNewAccount {

    AndroidDriver driver;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Creation and new Account", enabled = false)
    public void verifyCreateWalletAndCreateNewAccount() {
        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet().clickTokens();

        // Create a new Account
       walletHomePage.createNewAccount("test1");
       Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.labelAccountNext));
       Assert.assertTrue(walletHomePage.getDockBalance().contains("0"));
    }
}
