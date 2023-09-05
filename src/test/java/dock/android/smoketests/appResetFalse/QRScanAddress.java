package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class QRScanAddress extends BaseTestCaseAndroid {
    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR credential")
    public void verifyScanAddress() {
        String address = "3C7Hq5jQGxeYzL7LnVASn48tEfr6D7yKtNYSuXcgioQoWWsB";
        AndroidDriver driver = getDriverInstance();
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet();
        walletHomePage.scanQRCode(address).waitABit(5000);

        Assert.assertTrue(walletHomePage.isDisplayedByText(address));
        walletHomePage.click(By.xpath("//android.widget.TextView[contains(@text,'Next')]"));
        walletHomePage.clickSendMax();
        
    }
}
