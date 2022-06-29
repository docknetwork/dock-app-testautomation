package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class RemoveAccountFromMainDashboard extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Remove Account functionality by clicking three dots from main dashboard")
    public void verifyRemoveAccountFromDashboard() {
        AndroidDriver driver = getDriverInstance();

        // Create New Account
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.enterPassCodeOneTime()
                .clickPlusButtonToCreatAccount()
                .clickCreateNewAccountFromAddAccountWidget()
                .enterNewAccountInfo(accountName)
                .clickNext()
                .clickSkip();

        // Try to remove the new created account from the Accounts Dashboard
        walletHomePage.clickThreeDotsFromTopRightCorner()
                .clickRemoveAccountAfterThreeDotsClick();

        // Verify account has been removed
        Assert.assertFalse(walletHomePage.checkElementExistByXpath(accountName));
    }
}
