package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ShareDID extends BaseTestCaseAndroid {
    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Share DID option")
    public void verifyShareDID() {
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickDID().waitABit(2000);
        walletHomePage.clickShare();

        // Verify Share DID options are displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Copy DID"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Share DID"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("did:key:"));
    }
}
