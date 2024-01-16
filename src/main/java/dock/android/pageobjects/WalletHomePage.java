package dock.android.pageobjects;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import dock.utilities.Selector;
import io.appium.java_client.android.AndroidDriver;

public class WalletHomePage extends BasePage {
    public By importExistingWallet = Selector.contentDesc("ImportExistingBtn");
    public By copyAddress = By.xpath("//android.view.ViewGroup[@content-desc=\"ReceiveTokenScreen\"]/android.widget.Button[1]");
    public By shareAddress = By.xpath("//android.widget.Button[@content-desc=\"ReceiveTokenShareAddress\"]");
    public By btnImportWallet = Selector.contentDesc("importWallet.submitBtn");
    public By btnOk = By.xpath("//*[contains(@text,'OK')]");
    public By btnCredentials = By.xpath("//android.widget.TextView[contains(@text,'Credentials')]");
    public By btnReceive = By.xpath("//android.widget.TextView[contains(@text,'Receive')]");
    public By labelAccountNext = By.xpath("//*[contains(@text,'test1')]");
    public By labelDockBalance = By.xpath("//android.widget.TextView[contains(@text,'DOCK')]");
    private By btnNext = By.xpath("//*[contains(@text,'Next')]");
    private By optionCreateNewAccount = By.xpath("//android.widget.TextView[contains(@text,'Create new account')]");
    private By btnSkip = By.xpath("//*[contains(@text,'Skip')]");
    private By txtBxPassword = By.xpath("//*[contains(@text,'Password')]");
    private By txtBxConfirmPassword = By.xpath("//*[contains(@text,'Confirm password')]");
    private By btnCreateNewWallet = By.xpath("//*[contains(@content-desc,'CreateWalletBtn')]");
    private By btnCreateNewAccount = Selector.contentResourceID("CreateNewAccount");
    private By txtBxAccountName = By.xpath("//android.widget.EditText[contains(@text,'Account name')]");
    private By txtBxSendAddress = By.xpath("//android.widget.EditText[contains(@text,'Recipient address')]");
    private By btnDoThisLater = By.xpath("//*[contains(@text,'Do this later')]");
    private By btnScan = By.xpath("//*[contains(@text,'Scan')]");
    private By btnTokens = By.xpath("//*[contains(@text,'Tokens')]");
    private By btnPlus = Selector.contentResourceID("action-button");
    private By optionImportExistingAccount = Selector.contentResourceID("importExistingOption");
    private By btnAlreadyHaveAWallet = By.id("ImportExistingBtn");
    private By uploadJsonFile = By.xpath("//*[contains(@text,'Upload JSON file')]");
    private By optionDeleteAccount = By.xpath("//android.widget.TextView[contains(@text,'Delete account')]");
    private By optionExportAccount = By.xpath("//android.widget.TextView[contains(@text,'Export account')]");
    private By btnPlusCredential = By.xpath("//android.view.ViewGroup[@content-desc=\"CredentialsScreen\"]/android.view.ViewGroup[2]");
    private By btnThreeDots = By.xpath("//*[@content-desc=\"credentialMenuButton\"]");
    private By btnContinueTransak = By.xpath("//android.widget.Button[@content-desc=\"ContinueToTransak\"]");
    private By btnSave = By.xpath("//android.widget.Button[contains(@text,'Save')]");
    private By btnDID = By.xpath("//android.widget.TextView[contains(@text,'DIDs')]");
    private By btnPlusDID = By.xpath("//android.view.ViewGroup[@content-desc=\"DIDListScreen\"]/android.view.ViewGroup[2]");
    private By btnCreateNewDID = By.xpath("//android.widget.TextView[contains(@text,'Create New DID')]");
    private By didDownArrowKey = By.xpath("//android.view.ViewGroup[@content-desc='CreateNewDIDScreen']/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[4]/android.widget.TextView");
    private By btnCreateDID = By.xpath("//android.widget.Button[@content-desc=\"CreateNewDIDScreenDIDCreate\"]/android.widget.TextView");
    private By btnImportExistingDID = By.xpath("//android.widget.TextView[contains(@text,'Import existing DID')]");
    private By txtBxPasswordDid = By.xpath("//android.widget.EditText[@content-desc=\"Password\"]");
    private By threeIconsDid = By.xpath("(//android.widget.Button[@content-desc=\"DIDListItemOptionButton\"])[1]");
    private By editDid = By.xpath("//android.widget.TextView[contains(@text,'Edit DID')]");
    private By exportDid = By.xpath("//android.widget.TextView[contains(@text,'Export DID')]");
    private By txtBxDid = By.xpath("//android.widget.EditText[@content-desc=\"DIDName\"]");
    private By txtBxEditDid = By.xpath("//android.widget.EditText[@content-desc=\"EditDIDScreenDIDName\"]");
    private By txtBxConfirmDIDPassword = By.xpath("//android.widget.EditText[@content-desc=\"ConfirmPassword\"]");
    private By deleteDid = By.xpath("//android.widget.TextView[contains(@text,'Delete DID')]");
    private By delete = By.xpath("//android.widget.TextView[contains(@text,'Delete')]");
    private By btnShare = By.xpath("//android.widget.TextView[contains(@text,'Share')]");
    private By btnTestMode = Selector.contentResourceID("testMode");
    private By btnPasteToScan = Selector.contentResourceID("pasteToScan");

    public WalletHomePage(final AndroidDriver driver) {
        super(driver);
    }

    public boolean getWalletStatus() {
        return checkElementExistByXpath("Create a new wallet");
    }

    public WalletHomePage createNewWallet() {

        click(btnCreateNewWallet).waitABit(2000);
        click(Selector.contentDesc("CreateWalletBtn")).waitABit(2000);
        enterPassCodeTwoTimes();
//        clickDoThisLater();
        return this;
    }

    public WalletHomePage removeWalletComplete() {
        enterPassCodeOneTime()
                .clickSettings()
                .clickRemoveWallet()
                .enterPassCodeOneTime()
                .clickSkip()
                .clickRemoveOnFinalNotificationMessage();
        Assert.assertTrue(isDisplayed(importExistingWallet));
        return this;
    }

    public WalletHomePage importWalletComplete() {
        clickImportExistingWallet()
                .clickBtnImportWallet()
                .uploadFile("walletBackup-Mike.json")
                .enterPassword("Test1234!")
                .clickNext()
                .enterPassCodeTwoTimes();

        clickTokens();

        // Assert.assertTrue(isDisplayedByText("Accoun"));
        return this;
    }

    public WalletHomePage enterPassCodeTwoTimes() {
        waitABit(1000);
        // Type password
        for (int i = 1; i <= 6; i++) {
            click(Selector.contentDesc("keyboardNumber" + i));
        }
        waitABit(1000);
        // ReType password
        for (int j = 1; j <= 6; j++) {
            click(Selector.contentDesc("keyboardNumber" + j));
        }
        return this;
    }

    public WalletHomePage enterPassCodeOneTime() {
        waitABit(1000);
        // Type password
        for (int i = 1; i <= 6; i++) {
            click(Selector.contentDesc("keyboardNumber" + i));
            waitABit(200);
        }
        return this;
    }

    public WalletHomePage checkAccountOrElseCreateIt(String accountName) {
        clickTokens();
        waitElementVisibility("Accounts");
        if (!checkElementExistByXpath(accountName)) {
            clickPlusButtonToCreateAccount()
                    .clickImportExistingAccount()
                    .clickUploadJsonFile()
                    .uploadFile("importAccount.json")
                    .waitABit(3000);
            enterPassword("123456789Qw!")
                    .clickNext()
                    .enterNewAccountName(accountName)
                    .clickNext();
            waitElementVisibility("Accounts");
            Assert.assertTrue(isDisplayedByText(accountName));
        }
        return this;
    }

    public WalletHomePage ensureTestnet() {
        if (!checkElementExistByXpath("Test mode")) {
            clickSettings();
            click(btnTestMode);
            click(By.xpath("//android.widget.Switch"));
            clickCredentials();
            Assert.assertTrue(isDisplayedByText("Test mode"));
        }
        return this;
    }

    public WalletHomePage ensureMainnet() {
        if (checkElementExistByXpath("Test mode")) {
            clickSettings();
            click(btnTestMode);
            click(By.xpath("//android.widget.Switch"));
            clickCredentials();
            Assert.assertFalse(isDisplayedByText("Test mode"));
        }
        return this;
    }

    public WalletHomePage ensureHasCredential() {
        clickCredentials();
        if (!checkElementExistByXpath("Test Credential")) {
            scanQRCode("https://creds-testnet.dock.io/dc20aa279fd2edcc890a5b7025b99814b288a12de1569863447e77242a7e94bb");
            sendTextVisibleKeyboard(txtBxPassword, "Password1!");
            clickOk();
            waitABit(20000);
            waitElementVisibility("Import Credential");
            clickOk();
        }
        return this;
    }

    public WalletHomePage ensureHasDID() {
        clickDID();
        if (!checkElementExistByXpath("Test DID")) {
            clickDID();
            clickPlusButtonToCreatDID();
            waitABit(2000);
            clickCreateNewDID();
            enterDIDName("Test DID");
            selectDidKeyAsDIDType();
            clickCreate();
            waitABit(2000);
        }
        return this;
    }

    public WalletHomePage scanQRCode(String code) {
        clickScan();
        waitABit(3000);
        driver.setClipboardText(code);
        click(btnPasteToScan);
        return this;
    }

    public WalletHomePage clickDoThisLater() {
        click(btnDoThisLater);
        return this;
    }

    public WalletHomePage clickCredentials() {
        click(btnCredentials);
        return this;
    }

    public WalletHomePage removeCredentials() {
        click(btnThreeDots);
        clickByXpathAndroidWidgetTextView("Remove");
        return this;
    }

    public WalletHomePage clickPlusBtnCredentials() {
        click(btnPlus);
        return this;
    }

    public WalletHomePage clickOk() {
        click(btnOk);
        return this;
    }

    public WalletHomePage clickScan() {
        click(btnScan);
        return this;
    }

    public WalletHomePage clickTokens() {
        click(btnTokens);
        return this;
    }

    public WalletHomePage clickCreateNewAccountFromAddAccountWidget() {
        click(optionCreateNewAccount);
        return this;
    }

    public WalletHomePage clickDeleteAccountFromOptionsWidget() {
        click(optionDeleteAccount);
        return this;
    }

    public WalletHomePage clickExportAccountFromOptionsWidget() {
        click(optionExportAccount);
        return this;
    }

    public WalletHomePage clickExportAccountAsJson() {
        clickByXpathAndroidWidgetTextView("JSON");
        return this;
    }

    public WalletHomePage clickAccountDetails(String accountName) {
        clickByXpathAndroidWidgetTextView(accountName);
        return this;
    }

    public WalletHomePage clickRemoveAccountAfterThreeDotsClick() {
        clickByXpathAndroidWidgetTextView("Remove");
        waitForElementInVisibility("Accounts Removed");
        return this;
    }

    public WalletHomePage clickDetails() {
        clickByXpathAndroidWidgetTextView("Details");
        return this;
    }

    public WalletHomePage clickReceive() {
        waitABit(2000);
        clickByXpathAndroidWidgetTextView("Receive");
        return this;
    }

    public WalletHomePage clickSend() {
        waitABit(2000);
        clickByXpathAndroidWidgetTextView("Send");
        return this;
    }

    public WalletHomePage clickRemoveOnFinalNotificationMessage() {
        WebElement remove = (WebElement) driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'Remove')]")).get(1);
        remove.click();
        return this;
    }

    public WalletHomePage clickSettings() {
        clickByXpathAndroidWidgetTextView("Settings");
        return this;
    }

    public WalletHomePage clickRemoveWallet() {
        click(By.xpath("//*[contains(@text,'Remove wallet')]"));
        return this;
    }

    public WalletHomePage clickBackupWallet() {
        clickByXpathAndroidWidgetTextView("Backup wallet");
        return this;
    }

    public WalletHomePage clickBuy() {
        waitABit(2000);
        clickByXpathAndroidWidgetTextViewWithoutScrolling("Buy");
        return this;
    }

    public WalletHomePage clickContinueTransak() {
        waitABit(2000);
        click(btnContinueTransak);
        return this;
    }

    public WalletHomePage clickBuyNow() {
        waitABit(5000);
        click(By.xpath("//android.widget.Button[contains(@text,'Buy Now')]"));
        return this;
    }

    public WalletHomePage clickSendMax() {
        waitABit(2000);
        clickByXpathAndroidWidgetTextView("Send Max");
        return this;
    }

    public WalletHomePage clickCopyAddress() {
        waitABit(2000);
        driver.findElement(copyAddress).click();
        return this;
    }

    public WalletHomePage clickShareAddress() {
        click(shareAddress);
        return this;
    }

    public WalletHomePage clickUploadJsonFile() {
        click(uploadJsonFile);
        return this;
    }

    public WalletHomePage clickPlusButtonToCreateAccount() {
        click(btnPlus);
        return this;
    }

    public WalletHomePage clickCreateNewDID() {
        click(btnCreateNewDID);
        return this;
    }

    public WalletHomePage clickImportExistingDID() {
        click(btnImportExistingDID);
        return this;
    }

    public WalletHomePage clickPlusButtonToCreatDID() {
        click(btnPlus);
        return this;
    }

    public WalletHomePage clickThreeIconsDID() {
        click(threeIconsDid);
        return this;
    }

    public WalletHomePage clickEditDID() {
        waitABit(2000);
        WebElement element = (WebElement) driver.findElements(editDid).get(1);
        element.click();
        waitABit(2000);
        return this;
    }

    public WalletHomePage clickDeleteDID() {
        waitABit(2000);
        WebElement element = (WebElement) driver.findElements(deleteDid).get(0);
        element.click();
        waitABit(2000);
        return this;
    }

    public WalletHomePage clickDelete() {
        waitABit(2000);
        WebElement element = (WebElement) driver.findElements(delete).get(1);
        element.click();
        return this;
    }

    public WalletHomePage clickExportDID() {
        waitABit(2000);
        WebElement element = (WebElement) driver.findElements(exportDid).get(0);
        element.click();
        waitABit(2000);
        return this;
    }

    public WalletHomePage clickDID() {
        clickByXpathAndroidWidgetTextView("DIDs");
        return this;
    }

    public WalletHomePage clickShare() {
        click(btnShare);
        return this;
    }

    public WalletHomePage clickImportExistingAccount() {
        click(optionImportExistingAccount);
        return this;
    }

    public WalletHomePage clickAccountRecoveryPhrase() {
        clickByXpathAndroidWidgetTextView("Account recovery phrase");
        return this;
    }

    public WalletHomePage enterMememicPhrase(String value) {
        sendText(By.xpath("//android.widget.EditText[@content-desc=\"EnterText\"]"), value);
        waitABit(2000);
        return this;
    }

    public WalletHomePage enterDIDName(String value) {
        sendText(txtBxDid, value);
        waitABit(2000);
        return this;
    }

    public WalletHomePage enterEditDIDName(String value) {
        sendText(txtBxEditDid, value);
        waitABit(2000);
        return this;
    }

    public WalletHomePage selectDidKeyAsDIDType() {
        click(didDownArrowKey);
        click(didDownArrowKey);
        click(By.xpath("//android.widget.TextView[contains(@text,'did:key')]"));
        waitABit(2000);
        return this;
    }

    public WalletHomePage clickCreate() {
        click(btnCreateDID);
        return this;
    }

    public WalletHomePage clickAlreadyHaveAWallet() {
        click(btnAlreadyHaveAWallet);
        return this;
    }

    public WalletHomePage createNewAccount(String testName) {
        click(btnCreateNewAccount);
        sendText(txtBxAccountName, testName);
        clickNext().clickSkip();
        return this;
    }

    public WalletHomePage enterNewAccountInfo(String testName) {
        sendText(txtBxAccountName, testName);
        return this;
    }

    public WalletHomePage enterAddress(String address) {
        waitABit(2000);
        sendText(txtBxSendAddress, address);
        return this;
    }

    public WalletHomePage enterNewAccountName(String testName) {
        sendText(Selector.contentDesc("ImportAccountNameInput"), testName);
        return this;
    }

    public String getDockBalance() {
        waitABit(3000);
        String balance = getText(labelDockBalance);
        log.info("Balance is: " + balance);
        return getText(labelDockBalance);
    }

    public String getDockBalance(int accountNo) {
        return getElements(labelDockBalance).get(--accountNo).getText();
    }

    public WalletHomePage importWallet() {
        clickImportExistingWallet();
        clickBtnImportWallet();
        uploadFile("dockWalletBackup.json");
        enterPassword("123456789Qw!");
        clickNext();
        enterPassCodeTwoTimes();
        // clickDoThisLater();
        return this;
    }

    public WalletHomePage clickBtnImportWallet() {
        click(btnImportWallet);
        return this;
    }

    public WalletHomePage clickImportExistingWallet() {
        click(importExistingWallet);
        return this;
    }

    public WalletHomePage uploadFile(String fileName) {
        try {
            waitABit(3000);
            driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Show roots\"]")).click();
            List<WebElement> downlaods = getElements(By.xpath("//android.widget.TextView[@text='Downloads']"));
            System.out.println("downloadsicon size: " + downlaods.size());

            if (downlaods.size() > 1) {
                downlaods.get(1).click();
            }
            else {
                downlaods.get(0).click();
            }
            waitABit(2000);
            File classpathRoot = new File(System.getProperty("user.dir"));
            File assetDir = new File(classpathRoot, "src/test/resources/configfiles/");
            File walletBackUpFile = new File(assetDir.getCanonicalPath(), fileName);
            // actually push the file
            String directoryPath = "/sdcard/Download/";
            driver.pushFile(directoryPath + fileName, walletBackUpFile);
            driver.context("NATIVE_APP");
            driver.pullFile(directoryPath + fileName);
            click(By.xpath("//*[contains(@text,'" + fileName + "')]"));
            waitABit(5000);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WalletHomePage enterPassword(String password) {
        waitABit(2000);
        sendText(txtBxPassword, password);
        waitABit(2000);
        return this;
    }

    public WalletHomePage enterDIDPassword(String password) {
        waitABit(2000);
        sendText(txtBxPasswordDid, password);
        waitABit(2000);
        return this;
    }


    public WalletHomePage enterConfirmPassword(String password) {
        sendText(txtBxConfirmPassword, password);
        waitABit(2000);
        return this;
    }

    public WalletHomePage enterConfirmDIDPassword(String password) {
        sendText(txtBxConfirmDIDPassword, password);
        waitABit(2000);
        return this;
    }

    public WalletHomePage clickNext() {
        waitABit(2000);
        driver.findElement(btnNext).click();

        //if (checkElementExist(btnNext)) {
        //    click(btnNext);
        //}
        return this;
    }

    public WalletHomePage clickNextDockAddress() {
        waitABit(2000);
        driver.findElement(btnNext).click();
        if (checkElementExist(btnNext)) {
            click(btnNext);
        }
        return this;
    }

    public WalletHomePage clickSaveDIDChanges() {
        click(By.xpath("//android.widget.Button[@content-desc=\"EditDIDScreenSave\"]"));
        return this;
    }

    public WalletHomePage clickSkip() {
        click(btnSkip);
        return this;
    }

    public WalletHomePage clickRemoveAccount() {
        click(By.xpath("//*[@content-desc=\"account-item-menu-delete\"]"));
        return this;
    }

    public WalletHomePage clickThreeDotsFromTopRightCorner() {
        waitABit(5000);
        driver.findElement(By.xpath("//*[@content-desc=\"MenuButton\"]")).click();
        return this;
    }

    public int generateRandomNumber() {
        int min = 50;
        int max = 10000;
        //Generate random int value from 50 to 100
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
