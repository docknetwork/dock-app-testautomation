package dock.android.pageobjects;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.LocalFileDetector;

import dock.utilities.Selector;
import io.appium.java_client.android.AndroidDriver;

public class WalletHomePage extends BasePage {

    public WalletHomePage(final AndroidDriver driver) {
        super(driver);
    }

    public WalletHomePage createNewWallet() {
        click(By.xpath("//*[contains(@text,'Create a new wallet')]"));
        click(Selector.contentDesc("CreateWalletBtn"));
        // Type password
        for (int i = 1; i <= 6; i++) {
            click(Selector.contentDesc("keyboardNumber" + i));
        }
        // ReType password
        for (int j = 1; j <= 6; j++) {
            click(Selector.contentDesc("keyboardNumber" + j));
        }
        click(By.xpath("//*[contains(@text,'Do this later')]"));
        return this;
    }

    public WalletHomePage createNewAccount(String testName) {
        click(By.xpath("//*[contains(@text,'Create new account')]"));
        sendText(By.xpath("//*[contains(@text,'Account name')]"), testName);
        click(By.xpath("//*[contains(@text,'Next')]"));
        click(By.xpath("//*[contains(@text,'Skip')]"));
        return this;
    }

    public WalletHomePage importWallet() {
        click(Selector.contentDesc("ImportExistingBtn"));

        driver.setFileDetector(new LocalFileDetector());
        click(Selector.contentDesc("importWallet.submitBtn"));

        try {
            File classpathRoot = new File(System.getProperty("user.dir"));
            File assetDir = new File(classpathRoot, "src/test/resources/configfiles/");
            File img = new File(assetDir.getCanonicalPath(), "walletBackup.json");
            // actually push the file
            driver.pushFile("/sdcard/Download" + img.getName(), img);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Switch to Native_App
        Set<String> contextNames = driver.getContextHandles();
        for (String strContextName : contextNames) {
            if (strContextName.contains("NATIVE_APP")) {
                driver.context("NATIVE_APP");
                break;
            }
        }

        //driver.pushFile(Selector.contentDesc("importWallet.submitBtn"), new File("src/test/resources/configfiles/walletBackup.json"));

        return this;
    }


}
