package dock.android.pageobjects;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

import dock.utilities.Selector;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class WalletHomePage extends BasePage {
    public By importExistingWallet = Selector.contentDesc("ImportExistingBtn");
    public By copyAddress = By.xpath("//android.view.ViewGroup[@content-desc=\"ReceiveTokenScreen\"]/android.widget.Button[1]");
    public By shareAddress = By.xpath("//android.widget.Button[@content-desc=\"ReceiveTokenShareAddress\"]");
    public By btnImportWallet = Selector.contentDesc("importWallet.submitBtn");
    public By btnSend = By.xpath("//android.widget.TextView[contains(@text,'Send')]");
    public By btnCredentials = By.xpath("//android.widget.TextView[contains(@text,'Credentials')]");

    public By btnReceive = By.xpath("//android.widget.TextView[contains(@text,'Receive')]");
    public By labelAccountNext = By.xpath("//*[contains(@text,'test1')]");
    public By labelDockBalance = By.xpath("//android.widget.TextView[contains(@text,'DOCK')]");
    private By btnNext = By.xpath("//android.widget.TextView[contains(@text,'Next')]");
    private By optionCreateNewAccount = By.xpath("//android.widget.TextView[contains(@text,'Create new account')]");
    private By btnSkip = By.xpath("//*[contains(@text,'Skip')]");
    private By txtBxPassword = By.xpath("//*[contains(@text,'Password')]");
    private By txtBxConfirmPassword = By.xpath("//*[contains(@text,'Confirm password')]");
    private By btnCreateNewAccount = By.xpath("//*[contains(@text,'Create new account')]");
    private By txtBxAccountName = By.xpath("//android.widget.EditText[contains(@text,'Account name')]");
    private By txtBxSendAddress = By.xpath("//android.widget.EditText[contains(@text,'Recipient address')]");
    private By btnDoThisLater = By.xpath("//*[contains(@text,'Do this later')]");
    private By btnTokens = By.xpath("//*[contains(@text,'Tokens')]");
    private By btnPlus = Selector.contentResourceID("accountsScreen.addAccountMenuBtn");
    private By optionImportExistingAccount = Selector.contentResourceID("importExistingOption");
    private By btnAlreadyHaveAWallet = By.id("ImportExistingBtn");
    private By uploadJsonFile = By.xpath("//*[contains(@text,'Upload JSON file')]");
    private By optionDeleteAccount = By.xpath("//android.widget.TextView[contains(@text,'Delete account')]");
    private By btnPlusCredential = By.xpath("//android.view.ViewGroup[@content-desc=\"CredentialsScreen\"]/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup");
    private By btnThreeDots = By.xpath("//android.view.ViewGroup[@content-desc=\"CredentialsScreen\"]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup");
    private By btnContinueTransak = By.xpath("//android.widget.Button[@content-desc=\"ContinueToTransak\"]");

    public WalletHomePage(final AndroidDriver driver) {
        super(driver);
    }

    public WalletHomePage createNewWallet() {
        click(By.xpath("//*[contains(@text,'Create a new wallet')]"));
        click(Selector.contentDesc("CreateWalletBtn"));
        enterPassCodeTwoTimes();
        clickDoThisLater();
        return this;
    }

    public WalletHomePage enterPassCodeTwoTimes() {
        // Type password
        for (int i = 1; i <= 6; i++) {
            click(Selector.contentDesc("keyboardNumber" + i));
        }
        // ReType password
        for (int j = 1; j <= 6; j++) {
            click(Selector.contentDesc("keyboardNumber" + j));
        }
        return this;
    }

    public WalletHomePage enterPassCodeOneTime() {
        // Type password
        for (int i = 1; i <= 6; i++) {
            click(Selector.contentDesc("keyboardNumber" + i));
        }
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
        click(btnPlusCredential);
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

    public WalletHomePage clickDeleteAccountFromAddAccountWidget() {
        click(optionDeleteAccount);
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
        AndroidElement remove = (AndroidElement) driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'Remove')]")).get(1);
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

    public WalletHomePage clickPlusButtonToCreatAccount() {
        click(btnPlus);
        return this;
    }

    public WalletHomePage clickImportExistingAccount() {
        click(optionImportExistingAccount);
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
        clickDoThisLater();
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
            getElement(By.xpath("//android.widget.TextView[@text='Downloads']")).click();
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WalletHomePage enterPassword(String password) {
        sendText(txtBxPassword, password);
        return this;
    }

    public WalletHomePage enterConfirmPassword(String password) {
        sendText(txtBxConfirmPassword, password);
        return this;
    }

    public WalletHomePage clickNext() {
        waitABit(3000);
        click(btnNext);
        return this;
    }

    public WalletHomePage clickSkip() {
        click(btnSkip);
        return this;
    }

    public WalletHomePage clickRemoveAccount() {
        click(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup"));
        return this;
    }

    public WalletHomePage clickThreeDotsFromTopRightCorner() {
        click(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]/android.view.ViewGroup"));
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
