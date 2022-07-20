package dock.android.smoketests.appResetTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class BackupWallet {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Backup wallet functionality")
    public void verifyBackupwallet() {

        String password = "123456789Qw!";

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("walletBackup-Mike.json")
                .enterPassword("Test1234!")
                .clickNext()
                .enterPassCodeTwoTimes()
                .clickDoThisLater();

        // Verify Import of old account
        Assert.assertTrue(walletHomePage.isDisplayedByText("Bob"));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("0.2065 DOCK"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("frank"));
        Assert.assertTrue(walletHomePage.getDockBalance(2).contains("0.435 DOCK"));

        // Try to Backup the Wallet
        walletHomePage.enterPassCodeOneTime()
                .clickSettings()
                .clickBackupWallet()
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickNext();

        // Verify that wallet-backup***.Json is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("walletBackup-"));
        Assert.assertTrue(walletHomePage.isDisplayedByText(".json"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Link Sharing"));

        walletHomePage.waitABit(3000);
        walletHomePage.swipeDownUntillElementVisibileByExactText("Drive");
        WebElement ele = (WebElement) driver.findElements(By.xpath("//*[contains(@text,'Drive')]")).get(1);
        ele.click();

        String fileName = driver.findElement(By.className("android.widget.EditText")).getText();
        System.out.println(fileName);

        walletHomePage.waitABit(3000);
        driver.findElement(By.xpath("//android.widget.Button[contains(@text,'Save')]")).click();
        walletHomePage.waitABit(7000);

        driver.findElement(By.xpath("//*[contains(@text,'Remove wallet')]")).click();
        walletHomePage.enterPassCodeOneTime();
        driver.findElement(By.xpath("//*[contains(@text,'Skip')]")).click();
        walletHomePage.waitABit(3000);
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Remove')]")).click();
        walletHomePage.waitABit(3000);

        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet();
        walletHomePage.clickByXpathAndroidWidgetTextView(fileName);

        walletHomePage.enterPassword("123456789Qw!")
                .clickNext()
                .enterPassCodeTwoTimes()
                .clickDoThisLater();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Bob"));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("0.2065 DOCK"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
