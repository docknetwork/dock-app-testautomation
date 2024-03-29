package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ExportAccountAsJson extends BaseTestCaseAndroid {

    @Test( groups = TestGroup.SmokeTest, description = "Test to verify Export Account as Json functionality")
    public void verifyExportAccount() {
        // Create New Account
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test";
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickTokens().waitABit(2000);
        walletHomePage.clickPlusButtonToCreateAccount()
                .clickCreateNewAccountFromAddAccountWidget()
                .enterNewAccountInfo(accountName)
                .clickNext()
                .clickSkip().waitElementVisibility(("//*[@text='Account name']"));
        walletHomePage.clickByXpathAndroidWidgetTextView(accountName);

        // Try to export new account
        String password = "123456789Qw!";
        walletHomePage.clickThreeDotsFromTopRightCorner()
                .clickExportAccountFromOptionsWidget()
                .clickExportAccountAsJson()
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickNext();

        // Verify that account***.Json is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText(".json"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Link Sharing"));
    }
}
