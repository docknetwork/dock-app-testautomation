package dock.android.smoketests.appResetFalse;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.Selector;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class QRScanEncryptedWallet extends BaseTestCaseAndroid {
    private By btnContinue = Selector.contentResourceID("next-btn");
    private By passwordInput = Selector.contentResourceID("Password");

    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR credential")
    public void verifyScanEncryptedWallet() {
        String qrCodeValue = "{\"@context\":[\"https://w3id.org/wallet/v1\"],\"id\":\"did:key:z6LSd1HEdsPzejHbcKpWH44bF5oA4ET2hvxRBGJK17m4EQgH#encrypted-wallet\",\"type\":[\"EncryptedWallet\"],\"issuer\":\"did:key:z6LSd1HEdsPzejHbcKpWH44bF5oA4ET2hvxRBGJK17m4EQgH\",\"issuanceDate\":\"2022-03-28T11:44:06.296Z\",\"credentialSubject\":{\"id\":\"did:key:z6LSd1HEdsPzejHbcKpWH44bF5oA4ET2hvxRBGJK17m4EQgH\",\"encryptedWalletContents\":{}}}";
        String password = "Password";

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
        walletHomePage.scanQRCode(qrCodeValue);
        walletHomePage.sendText(passwordInput, password);

        walletHomePage.click(btnContinue);
        
    }
}
