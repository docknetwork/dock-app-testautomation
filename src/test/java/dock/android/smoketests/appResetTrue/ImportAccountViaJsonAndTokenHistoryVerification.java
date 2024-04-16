package dock.android.smoketests.appResetTrue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportAccountViaJsonAndTokenHistoryVerification {

    AndroidDriver driver;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Creation and new Account")
    public void verifyImportExistingAccount() {
        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet().clickTokens().waitABit(2000);

        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.clickPlusButtonToCreateAccount()
                .clickImportExistingAccount()
                .clickUploadJsonFile()
                .uploadFile("importAccount.json")
                .enterPassword("123456789Qw!");
        walletHomePage.clickNext().waitABit(2000);
        walletHomePage.enterNewAccountName(accountName).waitABit(1000);
        walletHomePage.clickNext();
        walletHomePage.waitElementVisibility("Account successfully imported");
        walletHomePage.waitForElementInVisibility("Account successfully imported");
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
