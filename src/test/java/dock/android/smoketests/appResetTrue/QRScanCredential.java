package dock.android.smoketests.appResetTrue;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
        String qrCodeValue = "https://creds-testnet.dock.io/6064d2eb3778bb7e0a8fa68384f4b345c3c5386492fab4789de2a7f0b0b57c6b";
        String password = "Password1!";

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet().waitABit(300);
        walletHomePage.ensureTestnet().waitABit(300);
        walletHomePage.ensureHasDID();
        walletHomePage.scanQRCode(qrCodeValue);

        walletHomePage.sendTextVisibleKeyboard(By.xpath("//*[contains(@text,'Password')]"), password);
        walletHomePage.clickOk();
        walletHomePage.waitABit(10000);

        if(walletHomePage.checkElementExistByXpathContains("Import Credential")){
            walletHomePage.clickOk();
        }

        // Verify Credential is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Test Credential"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
