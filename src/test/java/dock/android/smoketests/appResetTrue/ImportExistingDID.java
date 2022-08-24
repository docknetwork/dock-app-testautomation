package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportExistingDID {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to Import existing Did option and verification of default DID alongw ith new Wallet creation")
    public void verifyImportExistingDid() {
        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();
        String didName = "testDID" + walletHomePage.generateRandomNumber();

        // Verify that a default DID is created when a new Wallet is created
        walletHomePage.clickDID();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Default DID"));

        // Verify that DID is imported successfully via Json
        walletHomePage.clickPlusButtonToCreatDID()
                .clickImportExistingDID()
                .uploadFile("didImport.json")
                .enterDIDPassword("123456789Qw!")
                .clickNext();
        Assert.assertTrue(walletHomePage.isDisplayedByText("DockCerts-DID1"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
