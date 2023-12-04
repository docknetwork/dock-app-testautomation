package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ExportDID extends BaseTestCaseAndroid {

    @Test(enabled = false, groups = TestGroup.SmokeTest, description = "Test to verify Export DID option")
    public void verifyExportDID() {
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String password = "123456789Qw!";
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickDID().waitABit(2000);
        walletHomePage.clickThreeIconsDID()
                .clickExportDID()
                .enterDIDPassword(password)
                .enterConfirmDIDPassword(password)
                .clickNext();

        // Verify export DID options are displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Shar"));
    }
}
