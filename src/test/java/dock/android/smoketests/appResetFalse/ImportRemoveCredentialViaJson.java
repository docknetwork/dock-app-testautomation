package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportRemoveCredentialViaJson extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Credential functionality via Json")
    public void verifyImportCredentialViaJson() {
        AndroidDriver driver = getDriverInstance();

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickPlusBtnCredentials()
                .uploadFile("credImport.json")
                .clickOkCredentialImport();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Enterprise in the Community"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("NSC Balgowlah Boys Campus"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("June 26, 2022"));
    }
}
