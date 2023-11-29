package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class QRScanCredentialOffer extends BaseTestCaseAndroid {
    private By btnProceed = By.xpath("//android.widget.TextView[contains(@text,'Proceed')]");
    private By txtField = Selector.contentResourceID("root.name");
    private By btnSubmit = Selector.contentResourceID("vc-form-submit");


    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR credential offer")
    public void verifyScanCredentialOffer() {
        String name = "Test name";
        String qrValue = "openid://discovery?issuer=https://api-testnet.dock.io/openid/connect/issuers/eda7ac1a-4a33-4b5e-8017-22c20631149d";

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet().ensureHasDID();
        walletHomePage.scanQRCode(qrValue).waitABit(7000);

        walletHomePage.click(btnProceed);

        walletHomePage.waitABit(10000);

        walletHomePage.sendText(txtField, name);
        walletHomePage.click(btnSubmit);
        walletHomePage.waitABit(20000);
        walletHomePage.waitElementVisibility("Import Credential");
        walletHomePage.clickOk();

        Assert.assertTrue(walletHomePage.isDisplayedByText(name));
    }
}
