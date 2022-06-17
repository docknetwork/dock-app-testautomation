package dock.android.smoketests;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportWalletWithExistingAccountAndCredentials extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Import Wallet, import of existing account and credentials")
    public void verifyImportWalletWithExistingAccountAndCredentials() {
        AndroidDriver driver = getDriverInstance();

        // Import Existing wallet via wallet-backup.Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("dockWalletBackup.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterPassCode()
                .clickDoThisLater()
                .clickCredentials();

        // Verify that Account is imported
        Assert.assertTrue(walletHomePage.isDisplayedByText("Bsc in Computer Science"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Hans M\u00FCller"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("5/9/2021"));

        // Verify import of old account
        walletHomePage.clickTokens();
        String accountName = "test" + walletHomePage.generateRandomNumber();

        // Work around for refresh, create a new account, so imported account get displayed
        walletHomePage.createNewAccount(accountName);
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("0"));
        Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.btnSend));
        Assert.assertTrue(walletHomePage.isDisplayed(walletHomePage.btnReceive));

        // Verify Import of old account
        Assert.assertTrue(walletHomePage.isDisplayedByText("test1"));
    }
}
