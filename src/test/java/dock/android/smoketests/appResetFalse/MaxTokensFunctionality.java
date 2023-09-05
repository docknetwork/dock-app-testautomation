package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class MaxTokensFunctionality extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")
    public void verifyMaxTokensSendButton() {
        AndroidDriver driver = getDriverInstance();
        String recipient = "3DyCKfVoGZL8iTWruPtekwhDy9SFqaq9gHtbWF3QGYXDzHSK";

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureMainnet();
        walletHomePage.clickTokens();
        walletHomePage.checkAccountOrElseCreateIt(accountName);
        walletHomePage.clickAccountDetails(accountName)
                .clickSend()
                .enterAddress(recipient)
                .clickNextDockAddress()
                .clickSendMax()
                .clickNext();

        //Verify max token amount is displayed which is Dock tokens
        Assert.assertTrue(walletHomePage.isDisplayedByText("DOCK"));

        // Click Next and verify the widget of Confirm
        Assert.assertTrue(walletHomePage.isDisplayedByText("Confirm"));
        Assert.assertTrue(walletHomePage.isDisplayedByText(recipient));
        Assert.assertTrue(walletHomePage.getDockTokenFee() > 0);
        Assert.assertTrue(walletHomePage.isDisplayedByText("OK"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Cancel"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("DOCK"));
    }
}
