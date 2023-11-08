package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class CreateNewDID extends BaseTestCaseAndroid{

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Create New DID using did:key option")
    public void verifyCreateNewDIDByDIDKeyOption() {
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String didName = "testDID" + walletHomePage.generateRandomNumber();
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.clickDID().waitABit(2000);
        walletHomePage.clickPlusButtonToCreatDID()
                                .clickCreateNewDID()
                .enterDIDName(didName)
                .selectDidKeyAsDIDType()
                .clickCreate().waitABit(5000);
        Assert.assertTrue(walletHomePage.isDisplayedByText(didName));
    }
}
