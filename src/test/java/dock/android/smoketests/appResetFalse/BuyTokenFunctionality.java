package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
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
        walletHomePage.enterPassCodeOneTime()
                .clickAccountDetails(accountName)
                .clickBuy()
                .clickContinueTransak()
                .clickBuyNow();

        // Verify that Buy Dock button is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Buy DOCK"));
    }
}
