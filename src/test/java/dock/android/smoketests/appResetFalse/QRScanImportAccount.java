package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class QRScanImportAccount extends BaseTestCaseAndroid {
    private By btnSelectCredential = By.xpath("//*[@content-desc=\"Select credential\"]");
    private By btnContinue = By.xpath("(//android.widget.Button[@content-desc=\"PresentCredentialBtn\"])[2]");
    private By btnSelectAttribute = By.xpath("(//android.view.ViewGroup[@content-desc=\"Select attribute\"])[1]");
    private By btnContinuePresent = By.xpath("//android.widget.Button[@content-desc=\"PresentCredentialBtnContinue\"]");
    private By btnSelectDid = By.xpath("//android.view.ViewGroup[@content-desc=\"showContent\"]");

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR credential")
    public void verifyScanImportAccount() {
        String qrCodeValue = "{\"encoded\":\"vEWv+0kxsH6Jo6c4ycvaxjyQP7RV8/YEH+GEjIfhvCEAgAAAAQAAAAgAAAB/ijDJSVTdxdHthiRDcuQc1YyihA97m6Y5m06wynIA18HzI9+v9/Qj3H3ln+ByqrC4lEjh7a2eeuM6Wt4cthCUWIq8qJNsarky9gBuLCisHZOpw2s9Efq1nPZ/qAo+qkptLh+aJ9Lo8Rl27USkmbN9zUwWJel8n/mCKZMMD2DSTHUkvCex6Lw7OTzXAGt5wsz+3xCgiiZQXzcFYO1J\",\"encoding\":{\"content\":[\"pkcs8\",\"sr25519\"],\"type\":[\"scrypt\",\"xsalsa20-poly1305\"],\"version\":\"3\"},\"address\":\"3C4VGFTSftFRpG98LdR1eh8SzSTbPzSKtA775B2hM5u44EU3\",\"meta\":{}}";
        AndroidDriver driver = getDriverInstance();
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet()
                .ensureHasCredential()
                .ensureHasDID();
        walletHomePage.clickScan().scanQRCode(qrCodeValue);
        
        walletHomePage.click(btnContinue);
        walletHomePage.click(btnSelectAttribute);
        if(driver.findElements(btnSelectDid).size() > 0){
            walletHomePage.click(btnSelectDid);
            walletHomePage.click(By.xpath("//android.widget.TextView[contains(@text,'did:key')]"));
        }
        walletHomePage.click(btnContinuePresent);
        walletHomePage.click(btnContinuePresent);
        
    }
}
