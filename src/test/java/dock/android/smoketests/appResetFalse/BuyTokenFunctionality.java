package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class BuyTokenFunctionality extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Buy Token")
    public void verifyBuyToken() {
        AndroidDriver driver = getDriverInstance();

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
        walletHomePage.waitElementVisibility("Accounts");
        if (!walletHomePage.checkElementExistByXpath(accountName)) {
            walletHomePage.clickPlusButtonToCreateAccount()
                    .clickImportExistingAccount()
                    .clickUploadJsonFile()
                    .uploadFile("importAccount.json");
            walletHomePage.enterPassword("123456789Qw!")
                    .clickNext()
                    .enterNewAccountName(accountName);
            walletHomePage.clickNext().waitABit(2000);
        }
        walletHomePage.clickAccountDetails(accountName)
                .clickBuy()
                .clickContinueTransak()
                .clickBuyNow();

        // Verify that Buy Dock button is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Buy DOCK"));
    }
}
