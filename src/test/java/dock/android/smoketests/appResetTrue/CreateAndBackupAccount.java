package dock.android.smoketests.appResetTrue;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

public class CreateAndBackupAccount {

    AndroidDriver driver;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private String recoveryPhrase;

    @BeforeMethod
    public synchronized void openApp() {
        driver = WebDriverBuilder.getInstance().getAndroidDriverByAppReset();
    }

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify Account backup")
    public void verifyCreateAndBackupAccount() {
        // Create new Wallet
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        walletHomePage.createNewWallet().clickTokens();

        // Create a new Account
        walletHomePage.createNewAccount("test1").waitABit(3000);
        Assert.assertTrue(walletHomePage.checkElementExist(By.xpath("//*[@content-desc=\"AlertIcon\"]")));
        walletHomePage.clickThreeDotsFromTopRightCorner()
                .clickRemoveAccountAfterThreeDotsClick();
        walletHomePage.click(Selector.contentResourceID("CreateNewAccount"));
        walletHomePage.enterNewAccountInfo("test2");
        walletHomePage.clickNext();
        walletHomePage.click(By.xpath("//android.view.ViewGroup[@content-desc=\"Backup agreement\"]"));
        walletHomePage.clickNext();
        walletHomePage.click(By.xpath("//*[contains(@text,'Copy to clipboard')]"));
        walletHomePage.clickNext();
        recoveryPhrase = driver.getClipboardText();
        String index1Text = driver.findElement(Selector.contentResourceID("verifyWordIndex1")).getText();
        String index2Text = driver.findElement(Selector.contentResourceID("verifyWordIndex2")).getText();
        Integer index1 = Integer.parseInt(index1Text)-1;
        Integer index2 = Integer.parseInt(index2Text)-1;
        String[] words = recoveryPhrase.split(" ");
        walletHomePage.sendText(Selector.contentResourceID("verifyWord1"), words[index1]);
        walletHomePage.sendText(Selector.contentResourceID("verifyWord2"), words[index2]);
        walletHomePage.clickNext();
        Assert.assertFalse(walletHomePage.checkElementExist(By.xpath("//android.view.ViewGroup[@content-desc=\"AlertIcon\"]")));
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        driver.quit();
    }
}
