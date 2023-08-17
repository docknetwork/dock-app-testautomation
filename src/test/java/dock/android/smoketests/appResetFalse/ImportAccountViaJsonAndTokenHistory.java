package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportAccountViaJsonAndTokenHistory extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Account functionality via Json")
    public void verifyImportAccountViaJsonAndTokensHistory() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }

        walletHomePage.clickTokens();

        if (!walletHomePage.checkElementExistByXpath(accountName)) {
            walletHomePage.clickPlusButtonToCreateAccount()
                    .clickImportExistingAccount()
                    .clickUploadJsonFile()
                    .uploadFile("importAccount.json")
                    .enterPassword("123456789Qw!")
                    .clickNext()
                    .enterNewAccountName(accountName)
                    .clickNext().waitABit(4000);
            Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        }

        walletHomePage.swipeDownUntillElementVisibileByExactText(accountName);
        Assert.assertTrue(walletHomePage.getDockBalance().contains("DOCK"));

        // Click the imported account to see the history
        walletHomePage.clickAccountDetails(accountName);
        Assert.assertTrue(walletHomePage.isDisplayedByText("DOCK"));
    }
}
