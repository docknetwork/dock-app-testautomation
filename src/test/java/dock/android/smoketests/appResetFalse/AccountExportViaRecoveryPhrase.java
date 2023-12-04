package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class AccountExportViaRecoveryPhrase extends BaseTestCaseAndroid {

    @Test(enabled = false, groups = TestGroup.SmokeTest, description = "Test to verify account export as Json")
    public void verifyExportAccountAsJson() {
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test" + walletHomePage.generateRandomNumber();
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickTokens();
        walletHomePage.waitElementVisibility("Accounts");
        walletHomePage.clickPlusButtonToCreateAccount()
                .clickImportExistingAccount()
                .clickAccountRecoveryPhrase()
                .enterMememicPhrase("shiver aspect midnight brush loan resemble poet sea team hill mountain spoil")
                .clickNext()
                .enterNewAccountName(accountName)
                .clickNext();
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
    }
}
