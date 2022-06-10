package dock.android.smoketests;

import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class CreateWallet {

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Wallet Creation")
    public void createWallet() {
        AndroidDriver driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();

        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet();

        // Create a new Account
        walletHomePage.createNewAccount("test1");
    }
}
