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
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.options.app.SupportsAppActivityOption;
import io.appium.java_client.android.options.app.SupportsAppPackageOption;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.app.SupportsBundleIdOption;
import io.appium.java_client.ios.options.wda.SupportsUseNewWdaOption;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.remote.options.SupportsAutomationNameOption;
import io.appium.java_client.remote.options.SupportsDeviceNameOption;
import io.appium.java_client.remote.options.SupportsNoResetOption;
import io.appium.java_client.remote.options.SupportsPlatformVersionOption;
import io.appium.java_client.remote.options.SupportsUdidOption;

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
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability(SupportsAutoGrantPermissionsOption.AUTO_GRANT_PERMISSIONS_OPTION, true);
        options.setCapability(SupportsNoResetOption.NO_RESET_OPTION, true);
        if (LocalPropertiesReader.getExecutionMode().equals("local")) {
            options.setCapability(SupportsDeviceNameOption.DEVICE_NAME_OPTION, LocalPropertiesReader.getAndroidPhoneName());
            options.setCapability(SupportsUdidOption.UDID_OPTION, LocalPropertiesReader.getAndroidUdid());
            options.setCapability(SupportsPlatformVersionOption.PLATFORM_VERSION_OPTION, LocalPropertiesReader.getAndroidOSVersion());
        }
        else {
            options.setCapability(SupportsAppOption.APP_OPTION, System.getProperty("user.dir") + "/apps/app-release.apk");
            options.setCapability(SupportsAutomationNameOption.AUTOMATION_NAME_OPTION, "UiAutomator2");
            options.setCapability("platformName", "Android");
//            options.setCapability(SupportsPlatformVersionOption.PLATFORM_VERSION_OPTION, "7.1.1");
            options.setCapability(SupportsDeviceNameOption.DEVICE_NAME_OPTION, "Android Emulator");
        }
        options.setCapability(SupportsAppPackageOption.APP_PACKAGE_OPTION, "com.dockapp");
        options.setCapability(SupportsAppActivityOption.APP_ACTIVITY_OPTION, "com.dockapp.MainActivity");
        try {
            driver = new AndroidDriver(new URL(LocalPropertiesReader.getGridHubName()), options);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public AndroidDriver getAndroidDriverByAppReset() {
        AndroidDriver driver = null;
        System.setProperty("testType", "android1");
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability(SupportsAutoGrantPermissionsOption.AUTO_GRANT_PERMISSIONS_OPTION, true);
        options.setCapability(SupportsNoResetOption.NO_RESET_OPTION, false);
        if (LocalPropertiesReader.getExecutionMode().equals("local")) {
            options.setCapability(SupportsDeviceNameOption.DEVICE_NAME_OPTION, LocalPropertiesReader.getAndroidPhoneName());
            options.setCapability(SupportsUdidOption.UDID_OPTION, LocalPropertiesReader.getAndroidUdid());
            options.setCapability(SupportsPlatformVersionOption.PLATFORM_VERSION_OPTION, LocalPropertiesReader.getAndroidOSVersion());
        }
        else {
            options.setCapability(SupportsAppOption.APP_OPTION, System.getProperty("user.dir") + "/apps/app-release.apk");
            options.setCapability(SupportsAutomationNameOption.AUTOMATION_NAME_OPTION, "UiAutomator2");
            options.setCapability("platformName", "Android");
            //options.setCapability(SupportsPlatformVersionOption.PLATFORM_VERSION_OPTION, "7.1.1");
            options.setCapability(SupportsDeviceNameOption.DEVICE_NAME_OPTION, "Android Emulator");
        }
        options.setCapability(SupportsAppPackageOption.APP_PACKAGE_OPTION, "com.dockapp");
        options.setCapability(SupportsAppActivityOption.APP_ACTIVITY_OPTION, "com.dockapp.MainActivity");
        try {
            driver = new AndroidDriver(new URL(LocalPropertiesReader.getGridHubName()), options);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private XCUITestOptions setiOSCapabilities(XCUITestOptions options) {
        options.setCapability("platformName", "iOS");
        options.setCapability(SupportsBundleIdOption.BUNDLE_ID_OPTION, "com.dockapp");
        options.setCapability(SupportsAutomationNameOption.AUTOMATION_NAME_OPTION, "XCUITest");
        options.setCapability(SupportsDeviceNameOption.DEVICE_NAME_OPTION, "iPhone");
        options.setCapability(SupportsUdidOption.UDID_OPTION, "iOSUdid");
        options.setCapability(SupportsUseNewWdaOption.USE_NEW_WDA_OPTION, true);
        options.setCapability(SupportsPlatformVersionOption.PLATFORM_VERSION_OPTION, "iOSVersion");
        return options;
    }

    public IOSDriver getIOSDriver() {
        String executionMode = LocalPropertiesReader.getExecutionMode();
        IOSDriver driver = null;
        XCUITestOptions options = new XCUITestOptions();
        options = setiOSCapabilities(options);
        if (executionMode.equals("local")) {
            try {
                driver = new IOSDriver(new URL("http://localhost:4723"), options);
            }
            catch (MalformedURLException e) {
                log.info("Problem in opening the driver: " + e);
            }
        }
        else {
            driver = new IOSDriver(createGridRemoteAddress(), options);
        }
        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }

    public String prepareAbsoluteFilePathForSeleniumGrid(String path) {
        String currentDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
        return currentDirectory + path;
    }
}