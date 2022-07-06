package dock.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebDriverBuilder {
    public final static int IMPLICIT_WAIT_TIME = 15;
    private static final String gridHubName = LocalPropertiesReader.getGridHubName();
    private static WebDriverBuilder instance = null;
    private final Logger log = LoggerFactory.getLogger(WebDriverBuilder.class);

    private WebDriverBuilder() {
    }

    public static WebDriverBuilder getInstance() {
        if (instance == null) {
            instance = new WebDriverBuilder();
        }
        return instance;
    }

    private URL createGridRemoteAddress() {
        URL remoteAddress = null;
        try {
            remoteAddress = new URL(gridHubName);
        }
        catch (MalformedURLException e) {
            log.error("Selenium Grid address is malformed", e);
            Assert.fail("Selenium Grid address is malformed", e);
        }
        return remoteAddress;
    }

    public AndroidDriver getAndroidDriver() {
        AndroidDriver driver = null;
        DesiredCapabilities caps = new DesiredCapabilities();
        // simulator
        //caps.setCapability(MobileCapabilityType.DEVICE_NAME, LocalPropertiesReader.getAndroidPhoneName());
        //caps.setCapability(MobileCapabilityType.UDID, LocalPropertiesReader.getAndroidUdid());
        //caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, LocalPropertiesReader.getAndroidOSVersion());
        //caps.setCapability(MobileCapabilityType.NO_RESET, true);
        //caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/src/test/resources/apps/app-release.apk");
        //caps.setCapability("appPackage", "com.dockapp");
        //caps.setCapability("appActivity", "com.dockapp.MainActivity");

        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.NO_RESET, true);
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+"/apps/app-release.apk");
        caps.setCapability("appPackage", "com.dockapp");

        try {
            driver = new AndroidDriver(new URL(LocalPropertiesReader.getGridHubName()), caps);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public AndroidDriver getAndroidDriverByAppReset() {
        AndroidDriver driver = null;
        System.setProperty("testType", "android1");
        DesiredCapabilities caps = new DesiredCapabilities();
        // simulator
        //caps.setCapability(MobileCapabilityType.DEVICE_NAME, LocalPropertiesReader.getAndroidPhoneName());
        //caps.setCapability(MobileCapabilityType.UDID, LocalPropertiesReader.getAndroidUdid());
        //caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, LocalPropertiesReader.getAndroidOSVersion());
        //caps.setCapability(MobileCapabilityType.NO_RESET, false);
        //caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/src/test/resources/apps/app-release.apk");
        //caps.setCapability("appPackage", "com.dockapp");
        //caps.setCapability("appActivity", "com.dockapp.MainActivity");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.NO_RESET, false);
        caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+"/apps/app-release.apk");
        // caps.setCapability("appPackage", "com.dockapp");
        try {
            driver = new AndroidDriver<>(new URL(LocalPropertiesReader.getGridHubName()), caps);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private DesiredCapabilities setiOSCapabilities(DesiredCapabilities cap) {
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        cap.setCapability("bundleId", "com.dockapp");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        cap.setCapability("deviceName", "iPhone");
        cap.setCapability("udid", "iOSUdid");
        cap.setCapability("useNewWDA", true);
        cap.setCapability("platformVersion", "iOSVersion");
        return cap;
    }

    public IOSDriver getIOSDriver() {
        String executionMode = LocalPropertiesReader.getExecutionMode();
        IOSDriver driver = null;
        DesiredCapabilities cap = new DesiredCapabilities();
        cap = setiOSCapabilities(cap);
        if (executionMode.equals("local")) {
            try {
                driver = new IOSDriver<IOSElement>(new URL("http://localhost:4723/wd/hub"), cap);
            }
            catch (MalformedURLException e) {
                log.info("Problem in opening the driver: " + e);
            }
        }
        else {
            driver = new IOSDriver(createGridRemoteAddress(), cap);
        }
        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }

    public String prepareAbsoluteFilePathForSeleniumGrid(String path) {
        String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        return currentDirectory + path;
    }
}