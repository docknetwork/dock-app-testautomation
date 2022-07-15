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

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Import Account functionality via Json")
    public void verifyImportAccountViaJsonAndTokensHistory() {
        AndroidDriver driver = getDriverInstance();
        // Import Existing account via Json
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        accountName = "test" + walletHomePage.generateRandomNumber();
        walletHomePage.enterPassCodeOneTime()
                .clickPlusButtonToCreatAccount()
                .clickImportExistingAccount()
                .clickUploadJsonFile()
                .uploadFile("importAccount.json")
                .enterPassword("123456789Qw!")
                .clickNext()
                .enterNewAccountName(accountName)
                .clickNext();
        Assert.assertTrue(walletHomePage.isDisplayedByText(accountName));
        Assert.assertTrue(walletHomePage.getDockBalance().contains("3 DOCK"));

        // Click the imported account to see the history
        walletHomePage.clickAccountDetails(accountName);
        Assert.assertTrue(walletHomePage.isDisplayedByText("3 DOCK"));
    }

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
}
