package dock.android.smoketests;

import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class ImportWallet extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Import Wallet")
    public void verifyImportWallet() {
        AndroidDriver driver = getDriverInstance();

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        // Create a new Account
        walletHomePage.clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("dockWalletBackup.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterPassCode()
                .clickDoThisLater();
    }
}
