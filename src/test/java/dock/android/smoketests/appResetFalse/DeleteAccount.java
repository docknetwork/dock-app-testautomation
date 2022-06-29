package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class DeleteAccount extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Delete Account functionality")
    public void verifyDeleteAccount() {
        AndroidDriver driver = getDriverInstance();

        // Create New Account
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.enterPassCodeOneTime()
                .clickPlusButtonToCreatAccount()
                .clickCreateNewAccountFromAddAccountWidget()
                .enterNewAccountInfo(accountName)
                .clickNext()
                .clickSkip()
                .clickByXpathAndroidWidgetTextView(accountName);

        // Try to remove the new created account
        walletHomePage.clickRemoveAccount()
                .clickDeleteAccountFromAddAccountWidget();

        // Verify account has been removed
        Assert.assertFalse(walletHomePage.checkElementExistByXpath(accountName));
    }
}
