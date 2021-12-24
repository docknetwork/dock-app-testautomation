package dock.android.pageobjects;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

/**
 * @author Riffat Shahzad on 24.12.21
 */

public class WalletHomePage extends BasePage{

    public WalletHomePage(final AndroidDriver driver) {
        super(driver);
    }

    public WalletHomePage createNewWallet(){
        click(By.xpath("//*[contains(@text,'Create a new wallet')]"));
        return this;
    }
}
