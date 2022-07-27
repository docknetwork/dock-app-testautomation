package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class AccountTests extends BaseTestCaseAndroid {
    String accountName;

    @Test(dependsOnMethods = "verifyImportAccountViaJsonAndTokensHistory", groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")
    public void verifyReceiveButton() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickAccountDetails(accountName)
                .clickReceive()
                .clickCopyAddress();
        Assert.assertTrue(walletHomePage.isDisplayedByText("Copied"));

        walletHomePage.clickShareAddress();
        Assert.assertTrue(walletHomePage.isDisplayed(By.xpath(".//*[@resource-id = 'android:id/sem_chooser_chip_button1']")));
    }

    @Test(dependsOnMethods = "verifyImportAccountViaJsonAndTokensHistory", groups = TestGroup.SmokeTest, description = "Test to verify Receive Button")
    public void verifyMaxTokensSendButton() {
        AndroidDriver driver = getDriverInstance();
        String recipient = "3DyCKfVoGZL8iTWruPtekwhDy9SFqaq9gHtbWF3QGYXDzHSK";

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickAccountDetails(accountName)
                .clickSend()
                .enterAddress(recipient)
                .clickNext()
                .clickSendMax()
                .clickNext();

        //Verify max token amount is displayed which is 3 Dock tokens
        Assert.assertTrue(walletHomePage.isDisplayedByText("3 DOCK"));

        // Click Next and verify the widget of Confirm
        Assert.assertTrue(walletHomePage.isDisplayedByText("Confirm"));
        Assert.assertTrue(walletHomePage.isDisplayedByText(recipient));
        Assert.assertTrue(walletHomePage.getDockTokenFee() > 0);
        Assert.assertTrue(walletHomePage.isDisplayedByText("OK"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("Cancel"));
        Assert.assertTrue(walletHomePage.isDisplayedByText("3 DOCK"));
    }

    @Test(dependsOnMethods = "verifyImportAccountViaJsonAndTokensHistory", groups = TestGroup.SmokeTest, description = "Test to verify Buy Token")
    public void verifyBuyToken() {
        AndroidDriver driver = getDriverInstance();

        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.enterPassCodeOneTime()
                .clickAccountDetails(accountName)
                .clickBuy()
                .clickContinueTransak()
                .clickBuyNow();

        // Verify that Buy Dock button is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Buy DOCK"));
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to create account verification via Memic")
    public void verifyCreateAccountViaMemic() {
        AndroidDriver driver = getDriverInstance();

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        accountName = "test" + walletHomePage.generateRandomNumber();
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

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify account export as Json")
    public void verifyExportAccountAsJson() {
        AndroidDriver driver = getDriverInstance();

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        accountName = "test" + walletHomePage.generateRandomNumber();
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
