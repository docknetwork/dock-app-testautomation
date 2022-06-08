package dock.android.smoketests;

import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class ImportWallet extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Creation")
    public void createWallet() {
        AndroidDriver driver = getDriverInstance();

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        // Create a new Account
        walletHomePage.importWallet();

    }
}
