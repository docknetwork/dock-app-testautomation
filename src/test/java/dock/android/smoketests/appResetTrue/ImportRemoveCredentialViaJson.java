package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportRemoveCredentialViaJson{

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Credential functionality via Json")
    public void verifyImportCredentialViaJson() {

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.waitABit(500);
        walletHomePage.ensureTestnet();
        walletHomePage.clickPlusBtnCredentials()
                .uploadFile("credImport.json").waitABit(3000);
        walletHomePage.clickOk().waitABit(10000);
        Assert.assertTrue(walletHomePage.isDisplayedByText("Enterprise in the Community"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("NSC Balgowlah Boys Campus"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("June 26, 2022"));
        
        // Remove the added Cred and verify its removed properly
        walletHomePage.removeCredentials();
        Assert.assertFalse(walletHomePage.checkElementExistByXpath("Enterprise in the Community"));
        Assert.assertFalse(walletHomePage.checkElementExistByXpath("NSC Balgowlah Boys Campus"));
        Assert.assertFalse(walletHomePage.checkElementExistByXpath("June 26, 2022"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
