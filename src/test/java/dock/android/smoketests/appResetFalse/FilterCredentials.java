package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class FilterCredentials extends BaseTestCaseAndroid {
    private By searchInput = Selector.contentResourceID("search-input");
    private By btnFilter = By.xpath("//android.view.ViewGroup[@content-desc=\"CredentialsScreen\"]/android.widget.Button[3]");
    private By btnBack = By.xpath(".//*[@resource-id =\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[2]");
    private By fromDateInput = By.xpath(".//*[@resource-id =\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[6]");
    private By toDateInput = By.xpath(".//*[@resource-id =\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[8]");

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify filter credentials")
    public void verifyFilterCredentials() {
        // Create New Account
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        String invalidSearchTerm = "search term" + walletHomePage.generateRandomNumber();
        String validSearchTerm = "Test Credential";

        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet();
        walletHomePage.ensureHasCredential();
        walletHomePage.sendText(searchInput, invalidSearchTerm);
        Assert.assertFalse(walletHomePage.checkElementExistByXpath(validSearchTerm));
        walletHomePage.sendText(searchInput, validSearchTerm);
        Assert.assertTrue(walletHomePage.checkElementExistByXpath(validSearchTerm));
        walletHomePage.sendText(searchInput, "");
        walletHomePage.click(btnFilter);

        walletHomePage.waitABit(2000);

        walletHomePage.clickByView("Credential Type");

        if(walletHomePage.checkElementExistByXpathContains("Credential Type")){
            walletHomePage.clickByView("Credential Type");
        }
        walletHomePage.clickByView("BasicCredential");
        walletHomePage.clickByView("Issuer DID");
        walletHomePage.clickByView("did:dock:5CRMra6S2P3y992T7vM44RrUqNAXTEn2MbtWqw2PyGn5UgoV");
        walletHomePage.clickByView("Issuance Date");
        walletHomePage.click(fromDateInput);
        walletHomePage.clickByView("Confirm");
        walletHomePage.click(toDateInput);
        walletHomePage.clickByView("Confirm");
        walletHomePage.clickByView("Apply filter");
        walletHomePage.clickByView("Show results");
        Assert.assertFalse(walletHomePage.checkElementExistByXpath(validSearchTerm));
    }
}
