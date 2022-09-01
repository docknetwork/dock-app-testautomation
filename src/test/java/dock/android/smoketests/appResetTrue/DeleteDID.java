package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class DeleteDID {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Delete Did functionality")
    public void verifyDeleteDid() {

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();
        String didName = "testDID" + walletHomePage.generateRandomNumber();

        // Verify that a default DID is created when a new Wallet is created
        walletHomePage.clickDID();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Default DID"));

        // Verify that DID is imported successfully via Json
        walletHomePage.clickThreeIconsDID()
                        .clickDeleteDID()
                .clickDelete();
        Assert.assertTrue(walletHomePage.isDisplayedByText(didName));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
