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

    @BeforeSuite(alwaysRun = true)
    public synchronized void installApp() {

    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void openApp() {
        System.setProperty("testType", "android");
        AndroidDriver driver = null;
        int count = 1;
        while (driver == null && count <= 5) {
            try {
                driver = WebDriverBuilder.getInstance().getAndroidDriver();
                wait(5000);
            }
            catch (WebDriverException exception) {
                count++;
                log.info("Trying to get session again");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadInstanceWebDriver.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        try {
            AndroidDriver driver = threadInstanceWebDriver.get();
            if (executionMode.equals("grid") && driver != null) {
                driver.quit();
                log.info("Driver has been closed");
            }
            else if (quitDriver && driver != null) {
                driver.quit();
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
