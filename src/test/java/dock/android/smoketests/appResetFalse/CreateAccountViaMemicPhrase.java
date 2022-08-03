package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class CreateAccountViaMemicPhrase extends BaseTestCaseAndroid {

    @Test(groups = TestGroup.SmokeTest, description = "Test to create account verification via Memic")
    public void verifyCreateAccountViaMemic() {
        AndroidDriver driver = getDriverInstance();

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.enterPassCodeOneTime()
                .clickPlusButtonToCreatAccount()
                .clickImportExistingAccount()
                .clickAccountRecoveryPhrase()
                .enterMememicPhrase("argue glow aerobic acoustic artefact exact flush fetch skill void direct rib")
                .clickNext()
                .enterNewAccountName(accountName)
                .clickNext();
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
    }
}
