package dock.ios.pageobjects;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import dock.utilities.AbstractTestCase;
import dock.utilities.LocalPropertiesReader;
import dock.utilities.TestListener;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.ios.IOSDriver;

@Listeners(TestListener.class)
public class BaseTestCaseIOS extends AbstractTestCase {

    private final static Boolean quitDriver = LocalPropertiesReader.getQuitDriverMode();
    private static final ThreadLocal<IOSDriver> threadInstanceWebDriver = new ThreadLocal<IOSDriver>();
    protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private final boolean downLoadAppStatus = LocalPropertiesReader.getStatusdownloadApp();

    @BeforeSuite(alwaysRun = true)
    public synchronized void downloadApp() {
        log.info("Inside Before Suite");
        log.info("downLoadAppStatus is " + downLoadAppStatus);
        if (downLoadAppStatus) {
            log.info("Installing the iOS app ....");
            //WebDriverBuilder.getInstance().installiOSApp();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void openApp() {
        System.setProperty("testType", "ios");
        IOSDriver driver = null;
        int count = 1;
        while (driver == null && count <= 5) {
            try {
                driver = WebDriverBuilder.getInstance().getIOSDriver();
            }
            catch (WebDriverException exception) {
                try {
                    wait(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                log.info("Trying to get session again");
            }
        }
        threadInstanceWebDriver.set(driver);

    }

    @AfterMethod(alwaysRun = true)
    public synchronized void closeApp() {
        try {
            IOSDriver driver = threadInstanceWebDriver.get();
            if (LocalPropertiesReader.getExecutionMode().equals("grid")) {
                driver.quit();
                log.info("Driver has been closed");
            }
            else if (quitDriver) {
                driver.quit();
                log.info("Driver has been closed");
            }
        }
        catch (WebDriverException e) {
            log.info("Error during closeBrowser" + e);
        }
    }

    public synchronized IOSDriver getDriverInstance() {
        if (threadInstanceWebDriver.get() != null) {
            return threadInstanceWebDriver.get();
        }
        else {
            Assert.fail("Could not get connection with iOS device");
            return threadInstanceWebDriver.get();
        }
    }
}


