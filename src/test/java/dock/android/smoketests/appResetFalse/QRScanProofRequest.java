package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.By;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;

public class QRScanProofRequest extends BaseTestCaseAndroid {
    private By btnSelectCredential = By.xpath("//*[@content-desc=\"Select credential\"]");
    private By btnContinue = By.xpath("(//android.widget.Button[@content-desc=\"PresentCredentialBtn\"])[2]");
    private By btnSelectAttribute = By.xpath("(//android.view.ViewGroup[@content-desc=\"Select attribute\"])[1]");
    private By btnContinuePresent = By.xpath("//android.widget.Button[@content-desc=\"PresentCredentialBtnContinue\"]");
    private By btnSelectDid = By.xpath("//android.view.ViewGroup[@content-desc=\"showContent\"]");

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR credential")
    public void verifyScanProofRequest() {
        String qrCodeValue = "https://creds-testnet.dock.io/proof/06579da1-4195-428b-81d2-4bb7ff2787c4";
        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet()
                .ensureHasDID()
                .ensureHasCredential();
        walletHomePage.scanQRCode(qrCodeValue).click(btnSelectCredential);
        walletHomePage.click(btnContinue);
        walletHomePage.click(btnContinuePresent);
        walletHomePage.waitABit(2000);
        if(driver.findElements(btnSelectDid).size() > 0){
            walletHomePage.click(btnSelectDid);
            walletHomePage.click(By.xpath("//android.widget.TextView[contains(@text,'did:key')]"));
        }
        walletHomePage.click(btnContinuePresent);
        walletHomePage.waitABit(5000);

        Assert.assertTrue(walletHomePage.isDisplayedByText("Verification"));
    }
}
