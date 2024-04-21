package dock.android.smoketests.appResetTrue;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class QRScanCredential {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Edit Did functionality")
    public void verifyScanCredential() {
        String qrCodeValue = "https://creds-testnet.dock.io/bfc887b205e54bfa7bb0b1352562fbee334fcf591b07b01c98ed1a7356509768";
        String password = "Password1!";

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet().waitABit(300);
        walletHomePage.ensureTestnet().waitABit(300);
        walletHomePage.ensureHasDID().waitABit(2000);
        walletHomePage.scanQRCode(qrCodeValue);

        walletHomePage.sendTextVisibleKeyboard(By.xpath("//*[contains(@text,'Password')]"), password);
        walletHomePage.clickOk();
        walletHomePage.waitElementVisibility(By.xpath("//*[contains(@text,\"Import Credential\")]"), Duration.ofSeconds(120));
        walletHomePage.clickOk();

        // Verify Credential is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Test Credential"));
//        walletHomePage.waitABit(10000);
//        Assert.assertTrue(walletHomePage.isDisplayedByText("Verified"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
