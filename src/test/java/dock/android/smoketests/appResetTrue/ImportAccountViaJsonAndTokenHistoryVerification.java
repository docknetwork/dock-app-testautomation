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
        walletHomePage.createNewWallet();

        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.clickPlusButtonToCreatAccount()
                .clickImportExistingAccount()
                .clickUploadJsonFile()
                .uploadFile("importAccount.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterNewAccountName(accountName)
                .clickNext().waitABit(2000);
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("3 DOCK"));

        // Click the imported account to see the history
        walletHomePage.clickAccountDetails(accountName);
        Assert.assertTrue(walletHomePage.isDisplayedByText("3 DOCK"));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
