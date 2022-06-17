package dock.android.pageobjects;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;

import dock.utilities.Selector;
import io.appium.java_client.android.AndroidDriver;

public class WalletHomePage extends BasePage {
    public By importExistingWallet = Selector.contentDesc("ImportExistingBtn");
    public By btnImportWallet = Selector.contentDesc("importWallet.submitBtn");
    private By btnNext = By.xpath("//*[contains(@text,'Next')]");
    public By btnSend = By.xpath("//android.widget.TextView[contains(@text,'Send')]");
    public By btnCredentials = By.xpath("//android.widget.TextView[contains(@text,'Credentials')]");
    public By btnReceive = By.xpath("//android.widget.TextView[contains(@text,'Receive')]");
    public By labelAccountNext = By.xpath("//*[contains(@text,'test1')]");
    public By labelDockBalance = By.xpath("//android.widget.TextView[contains(@text,'DOCK')]");
    private By btnSkip = By.xpath("//*[contains(@text,'Skip')]");
    private By txtBxPassword = By.xpath("//*[contains(@text,'Password')]");
    private By btnCreateNewAccount = By.xpath("//*[contains(@text,'Create new account')]");
    private By txtBxAccountName = By.xpath("//*[contains(@text,'Account name')]");
    private By btnDoThisLater = By.xpath("//*[contains(@text,'Do this later')]");
    private By btnTokens =  By.xpath("//*[contains(@text,'Tokens')]");;

    public WalletHomePage(final AndroidDriver driver) {
        super(driver);
    }

    public WalletHomePage createNewWallet() {
        click(By.xpath("//*[contains(@text,'Create a new wallet')]"));
        click(Selector.contentDesc("CreateWalletBtn"));
        enterPassCode();
        clickDoThisLater();
        return this;
    }

    public WalletHomePage enterPassCode() {
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

    public WalletHomePage createNewAccount(String testName) {
        click(btnCreateNewAccount);
        sendText(txtBxAccountName, testName);
        clickNext();
        clickSkip();
        return this;
    }

    public String getDockBalance(){
        return getText(labelDockBalance);
    }

    public WalletHomePage importWallet() {
        clickImportExistingWallet();
        clickBtnImportWallet();
        uploadFile("dockWalletBackup.json");
        enterPassword("123456789Qw!");
        clickNext();
        enterPassCode();
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
            driver.pushFile("/sdcard/" + fileName, walletBackUpFile);
            driver.context("NATIVE_APP");
            driver.pullFile("/sdcard/" + fileName);
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
        click(btnNext);
        return this;
    }

    public WalletHomePage clickSkip() {
        click(btnSkip);
        return this;
    }

    public int generateRandomNumber(){
        int min = 50;
        int max = 100;
        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }
}
