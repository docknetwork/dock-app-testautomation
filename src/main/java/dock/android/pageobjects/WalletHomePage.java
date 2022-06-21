package dock.android.pageobjects;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

import dock.utilities.Selector;
import io.appium.java_client.android.AndroidDriver;

public class WalletHomePage extends BasePage {
    public By importExistingWallet = Selector.contentDesc("ImportExistingBtn");
    public By btnImportWallet = Selector.contentDesc("importWallet.submitBtn");
    public By btnSend = By.xpath("//android.widget.TextView[contains(@text,'Send')]");
    public By btnCredentials = By.xpath("//android.widget.TextView[contains(@text,'Credentials')]");
    public By btnReceive = By.xpath("//android.widget.TextView[contains(@text,'Receive')]");
    public By labelAccountNext = By.xpath("//*[contains(@text,'test1')]");
    public By labelDockBalance = By.xpath("//android.widget.TextView[contains(@text,'DOCK')]");
    private By btnNext = By.xpath("//android.widget.TextView[contains(@text,'Next')]");
    private By btnSkip = By.xpath("//*[contains(@text,'Skip')]");
    private By txtBxPassword = By.xpath("//*[contains(@text,'Password')]");
    private By btnCreateNewAccount = By.xpath("//*[contains(@text,'Create new account')]");
    private By txtBxAccountName = By.xpath("//android.widget.EditText[contains(@text,'Account name')]");
    private By btnDoThisLater = By.xpath("//*[contains(@text,'Do this later')]");
    private By btnTokens = By.xpath("//*[contains(@text,'Tokens')]");
    private By btnPlus = Selector.contentResourceID("accountsScreen.addAccountMenuBtn");
    private By optionImportExistingAccount = Selector.contentResourceID("importExistingOption");
    private By btnAlreadyHaveAWallet = By.id("ImportExistingBtn");
    private By uploadJsonFile = By.xpath("//*[contains(@text,'Upload JSON file')]");

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


    public WalletHomePage clickTokens() {
        click(btnTokens);
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
        clickNext();
        clickSkip();
        return this;
    }

    public WalletHomePage enterAccountAccountInfo(String testName) {
        sendText(txtBxAccountName, testName);
        return this;
    }

    public WalletHomePage enterNewAccountName(String testName) {
        sendText(Selector.contentDesc("ImportAccountNameInput"), testName);
        return this;
    }

    public String getDockBalance() {
        return getText(labelDockBalance);
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

    public WalletHomePage clickNext() {
        waitABit(2000);
        click(btnNext);
        return this;
    }

    public WalletHomePage clickSkip() {
        click(btnSkip);
        return this;
    }

    public int generateRandomNumber() {
        int min = 50;
        int max = 100;
        //Generate random int value from 50 to 100
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
