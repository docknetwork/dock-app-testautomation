package dock.android.smoketests.appResetTrue;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class DefaultDIDCreation {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify default did creation in new wallet")
    public void verifyDefaultDIDCreation() {

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();

        // Check Default DID
        walletHomePage.clickDID().waitABit(2000);
        walletHomePage.clickShare();
        walletHomePage.click(By.xpath("//*[contains(@text,'Copy DID')]")).waitABit(2000);
        String firstDID = driver.getClipboardText();

        // Remove current wallet
        walletHomePage.clickSettings().waitABit(2000);
        walletHomePage.clickRemoveWallet();
        walletHomePage.enterPassCodeOneTime()
                .clickSkip();
        walletHomePage.click(By.xpath("//android.widget.Button[@content-desc=\"Remove\"]"));

        walletHomePage.createNewWallet();
        walletHomePage.clickDID().waitABit(2000);
        walletHomePage.clickShare();
        walletHomePage.click(Selector.contentResourceID("copyDIDButton")).waitABit(2000);
        String secondDID = driver.getClipboardText();

        Assert.assertFalse(firstDID.equals(secondDID));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
