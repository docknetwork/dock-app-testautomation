package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ExportDID extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Export DID option")
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
                .enterConfirmDIDPassword(password);

        walletHomePage.clickNext();

//        TODO: Confirm export
//        walletHomePage.waitABit(2000);
//        Assert.assertTrue(walletHomePage.isDisplayedByText("did_"));
//        walletHomePage.navigateBack();
//        Assert.assertTrue(walletHomePage.isDisplayedByText("DID exported successfully"));
    }
}
