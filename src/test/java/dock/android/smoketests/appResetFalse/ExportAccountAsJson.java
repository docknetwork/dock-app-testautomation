package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ExportAccountAsJson extends BaseTestCaseAndroid {

    @Test( groups = TestGroup.SmokeTest, description = "Test to verify Export Account as Json functionality")
    public void verifyExportAccount() {
        // Create New Account
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test";

        // Clear any file sharing screen
//        if(walletHomePage.checkElementExist(Selector.contentResourceID("android:id/contentPanel"))){
//            walletHomePage.click(Selector.contentResourceID("android:id/contentPanel"));
//        }

        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickTokens().waitABit(2000);
        walletHomePage.clickPlusButtonToCreateAccount();
        walletHomePage.clickCreateNewAccountFromAddAccountWidget()
                .enterNewAccountInfo(accountName)
                .clickNext()
                .clickSkip();
        walletHomePage.waitElementVisibility(accountName);
        walletHomePage.waitElementVisibility("Account successfully created");
        walletHomePage.waitForElementInVisibility("Account successfully created");
        walletHomePage.clickByXpathAndroidWidgetTextView(accountName);

        // Try to export new account
        String password = "123456789Qw!";
        walletHomePage.accountDetailsKebabMenu()
                .clickExportAccountFromOptionsWidget()
                .clickExportAccountAsJson()
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickNext();
        walletHomePage.waitABit(2000);
        walletHomePage.navigateBack();
//        TODO: Confirm export
//        Assert.assertTrue(walletHomePage.isDisplayedByText(".json"));
//        Assert.assertTrue(walletHomePage.isDisplayedByText("Account exported"));
    }
}
