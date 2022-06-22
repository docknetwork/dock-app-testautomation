package dock.android.smoketests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class CreateWalletAndNewAccount {

    AndroidDriver driver;

    @Test(priority = 1, groups = TestGroup.SmokeTest, description = "Test to verify Wallet Creation and new Account")
    public void verifyCreateWalletAndCreateNewAccount() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();

        // Create a new Account
        walletHomePage.createNewAccount("test1");
        Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.labelAccountNext));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("0"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
