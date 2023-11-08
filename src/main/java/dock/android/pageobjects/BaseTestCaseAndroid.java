package dock.android.pageobjects;

import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import dock.utilities.LocalPropertiesReader;
import dock.utilities.TestListener;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.android.AndroidDriver;

@Listeners(TestListener.class)
public class BaseTestCaseAndroid {
    public String accountName = "testImport";
    private static final Boolean quitDriver = true;
    private static final ThreadLocal<AndroidDriver> threadInstanceWebDriver = new ThreadLocal<AndroidDriver>();
    private static final String executionMode = LocalPropertiesReader.getExecutionMode();
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    public AndroidDriver driver = null;
    public WalletHomePage walletHomePage;


   @BeforeMethod(alwaysRun = true)
   public synchronized void openApp() {
       System.setProperty("testType", "android");
       driver = WebDriverBuilder.getInstance().getAndroidDriver();
       threadInstanceWebDriver.set(driver);
   }

   @AfterMethod(alwaysRun = true)
   public synchronized void closeApp() {
       try {
           if (executionMode.equals("grid") && driver != null) {
               driver.close();
               log.info("Driver has been closed");
           }
           else if (quitDriver && driver != null) {
               driver.close();
               log.info("Driver has been closed");
           }
       }
       catch (WebDriverException e) {
           log.error("Error during closeBrowser" + e);
       }
   }

    public synchronized AndroidDriver getDriverInstance() {
        return threadInstanceWebDriver.get();
    }
}
