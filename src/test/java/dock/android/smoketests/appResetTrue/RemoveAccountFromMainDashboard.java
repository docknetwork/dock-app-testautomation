package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class RemoveAccountFromMainDashboard extends BaseTestCaseAndroid {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(enabled = false, groups = TestGroup.SmokeTest, description = "Test to verify Remove Account functionality by clicking three dots from main dashboard")
    public void verifyRemoveAccountFromDashboard() {

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();

        // Create New Account
        String accountName = "A_Test_" + walletHomePage.generateRandomNumber();
        walletHomePage.createNewAccount(accountName).waitABit(3000);

        // Try to remove the new created account from the Accounts Dashboard
        walletHomePage.clickThreeDotsFromTopRightCorner()
                .clickRemoveAccountAfterThreeDotsClick();

        // Verify account has been removed
        Assert.assertFalse(walletHomePage.checkElementExistByXpath(accountName));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
