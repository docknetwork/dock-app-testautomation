package dock.ios.pageobjects;

import static io.appium.java_client.touch.offset.PointOption.point;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import dock.utilities.TestListener;
import dock.utilities.WebDriverBuilder;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;

public class BasePage<T extends BasePage> {
    protected final By labelTitle = By.name("labelTitle");
    public By suchen = By.name("Suchen");
    public By search = By.name("search");
    public By btnSpeichern = By.name("speichern");
    public By btnAbbrechen = By.name("abbrechen");
    public By btnWeiter = By.name("weiter");
    public By txtBxTitle = By.name("Versicherungsumfang");
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected IOSDriver driver;

    public BasePage(IOSDriver driver) {
        this.driver = driver;
    }

    public BasePage click(By locator) {
        driver.findElement(locator).click();
        log.info("Element with locator = [" + locator + "] has been clicked.");
        return this;
    }

    public T scrollIntoViewByText(String text) {
        swipeDownUntillElementVisibile(text);
        log.info("Swiping down to the text " + text);
        return (T) this;
    }

    public BasePage click(IOSElement element) {
        element.click();
        log.info("*****Element with locator = [ " + element + " ] has been clicked.**********");
        return this;
    }

    public BasePage clickByAccessibilityId(String accessibilityId) {
        driver.findElementByAccessibilityId(accessibilityId).click();
        log.info("*****Element with locator = [ " + accessibilityId + " ] has been clicked.**********");
        return this;
    }

    public BasePage inputValueByAccessibilityId(String accessibilityId, String value) {
        ((IOSElement) driver.findElementByAccessibilityId(accessibilityId)).setValue(value);
        return this;
    }

    public String getTextByAccessibilityId(String accessibilityId) {
        String text = driver.findElementByAccessibilityId(accessibilityId).getText();
        log.info("Element displayed text = " + text);
        return text;
    }

    public T clickWeiter() {
        takeScreenshot();
        click(btnWeiter);
        return (T) this;
    }

    public T clickKameraStarten() {
        click(By.name("Kamera starten"));
        return (T) this;
    }

    public T waitABit(long milis) {
        try {
            Thread.sleep(milis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    public IOSElement getElement(By locator) {
        return (IOSElement) driver.findElement(locator);
    }

    public List<IOSElement> getElements(By locator) {
        turnOffImplicitWait();
        List listElements = driver.findElements(locator);
        turnOnImplicitWait();
        return listElements;
    }

    public void clickOptionByXpathUsingName(String Value) {
        getElement(By.xpath("//*[contains(@name,'" + Value + "')]")).click();
        log.info("Value selected: " + Value);
    }

    public void clickOptionviaSuchen(String Value) {
        getElement(suchen).click();
        getElement(suchen).sendKeys(Value);
        IOSElement element = (IOSElement) driver.findElementByName(Value);
        element.click();
    }


    public BasePage waitForElementVisibility(By locator) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    public BasePage waitForElementVisibilityByAccessibilityId(String locator) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(locator)));
        return this;
    }

    public BasePage waitForElementInvisibility(By locator) {
        turnOffImplicitWait();
        new WebDriverWait(driver, 60).pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        turnOnImplicitWait();
        return this;
    }


    public BasePage waitForElementVisibility(By locator, int sec) {
        new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    public BasePage waitForElementVisibility(IOSElement element) {
        turnOffImplicitWait();
        new WebDriverWait(driver, 30).pollingEvery(Duration.ofMillis(1000))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.visibilityOf(element));
        turnOnImplicitWait();
        return this;
    }

    public BasePage hideKeyboard() {
        waitABit(500);
        if (checkElementExist(By.name("Fertig"))) {
            driver.findElementByName("Fertig").click();
        }
        return this;
    }

    public BasePage hideKeyboardByReturn() {
        waitABit(500);
        if (checkElementExist(By.name("Return"))) {
            getElement(By.name("Return")).click();
        }
        waitABit(100);
        return this;
    }

    //Write Text
    public BasePage sendText(By locator, String text) {
        if (locator.toString().contains("name")) {
            swipeDownUntillElementVisibile(locator.toString().replace("By.name:", "").trim());
        }
        IOSElement element = (IOSElement) driver.findElement(locator);
        element.click();
        element.sendKeys(text);
        log.info("locator = [" + locator + "], Text = [" + text + "] has been entered.");
        hideKeyboard();
        return this;
    }

    //Write Text
    public BasePage sendTextWitClear(By locator, String text) {
        if (locator.toString().contains("name")) {
            swipeDownUntillElementVisibile(locator.toString().replace("By.name:", "").trim());
        }
        IOSElement element = (IOSElement) driver.findElement(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
        log.info("locator = [" + locator + "], Text = [" + text + "] has been entered.");
        hideKeyboard();
        return this;
    }

    //Write Text
    public BasePage sendText(IOSElement element, String text) {
        element.sendKeys(text);
        log.info("Text = [" + text + "] has been entered.");
        return this;
    }

    public BasePage clearText(By locator) {
        driver.findElement(locator).clear();
        log.info("Text field has been cleared.");
        return this;
    }

    //Read Text
    public String getText(By locator) {
        String text = driver.findElement(locator).getText();
        log.info("Element displayed text = " + text);
        return text;
    }

    public boolean isDisplayed(By locator) {
        boolean status = driver.findElement(locator).isDisplayed();
        if (status)
            log.info(locator + " is displayed.");
        else
            log.info(locator + " is not displayed.");
        return status;
    }

    public boolean contains(By locator, String expectedText) {
        String actualText = driver.findElement(locator).getText();
        if (actualText.contains(expectedText)) {
            log.info("Actual Text = [" + actualText + "] contains expected Text = [" + expectedText + "]");
            return true;
        }
        else {
            log.info("Actual Text = [" + actualText + "] does not contain expected Text = [" + expectedText + "]");
            return false;
        }
    }

    public BasePage navigateBack() {
        waitABit(2000);
        driver.navigate().back();
        waitABit(2000);
        return this;
    }

    public boolean checkElementExist(By locator) {
        boolean status = getElementStatusWithVisibilityStatus(locator);
        return status;
    }

    public boolean checkElementExist(By locator, int sec) {
        turnOffImplicitWait(sec);
        List<IOSElement> elements = driver.findElements(locator);
        boolean status = false;
        if (elements.size() > 0) {
            status = elements.get(0).isDisplayed();
        }
        log.info("Checking visibility of Element with locator: " + locator + " ---- Visibility status: " + status);
        turnOnImplicitWait();
        return status;
    }

    public boolean checkElementExistByTurningOffImplicitWait(By locator) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean status = driver.findElements(locator).size() > 0;
        log.info("Checking visibility of Element with locator: " + locator);
        turnOnImplicitWait();
        return status;
    }

    public boolean getElementStatusWithVisibilityStatus(By locator) {
        turnOffImplicitWait();
        List<IOSElement> elements = driver.findElements(locator);
        boolean status = false;
        if (elements.size() > 0) {
            status = elements.get(0).isDisplayed();
        }
        log.info("Checking visibility of Element with locator: " + locator + " ---- Visibility status: " + status);
        turnOnImplicitWait();
        return status;
    }

    public boolean checkElementVisibilityByAccessibilityId(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        log.info("Checking visibility of Element with locator: " + locator + " ----  is starting");
        final boolean displayed = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(locator))).isDisplayed();
        log.info("Checking visibility of Element with locator: " + locator + " ---- Visibility status: " + displayed);
        return displayed;
    }

    public boolean getElementStatusWithInvisibilityStatus(By locator) {
        turnOffImplicitWait();
        List<IOSElement> elements = driver.findElements(locator);
        boolean status = elements.size() > 0;
        log.info("Checking visibility of Element with locator: " + locator + " ---- Visibility status: " + status);
        turnOnImplicitWait();
        return status;
    }

    public boolean getElementDisplayStatus(By locator) {
        turnOffImplicitWait();
        boolean status = false;
        if (driver.findElements(locator).size() > 0) {
            status = driver.findElement(locator).isDisplayed();
            log.info("Checking visibility of Element with locator: " + locator + " ---- Visibility status: " + status);
        }
        turnOnImplicitWait();
        return status;
    }

    public BasePage swipe(int x1, int y1, int x2, int y2) {
        log.info("swiping starts ...");
        log.info("x1: " + x1 + " , y1: " + y1);
        log.info("x2: " + x2 + " , y1: " + y2);
        new TouchAction(driver)
                .press(point(x1, y1))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(point(x2, y2))
                .release().perform();
        log.info("swiping ends ...");
        return this;
    }

    protected BasePage swipeFromTopToBottom() {
        log.info("swiping starts ...");
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int endx = (int) (size.width * 0.7);
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.5);
        log.info("x1: " + startx + " , y1: " + endx);
        log.info("x2: " + starty + " , y1: " + endy);
        new TouchAction(driver)
                .press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(point(endx, endy))
                .release().perform();
        log.info("swiping ends ...");
        waitABit(1000);
        return this;
    }

    protected T swipeLittle() {
        log.info("swiping starts ...");
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int endx = (int) (size.width * 0.7);
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.6);
        log.info("x1: " + startx + " , y1: " + endx);
        log.info("x2: " + starty + " , y1: " + endy);
        new TouchAction(driver)
                .press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(point(endx, endy))
                .release().perform();
        log.info("swiping ends ...");
        waitABit(1000);
        return (T) this;
    }

    public BasePage swipeFromLeftToRight() {
        log.info("swiping starts ...");
        new TouchAction(driver)
                .press(point(10, 333))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(point(262, 333))
                .release().perform();
        log.info("swiping ends ...");
        return this;
    }

    public BasePage swipeFromRightToLeft() {
        log.info("swiping starts ...");
        new TouchAction(driver)
                .press(point(262, 333))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
                .moveTo(point(10, 333))
                .release().perform();
        log.info("swiping ends ...");
        return this;
    }

    protected void enterDate(By locator, String date) {
        IOSElement dayTxtElement = getElement(locator);
        dayTxtElement.sendKeys(date);
        waitABit(500);
    }

    public void turnOffImplicitWait() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    public void turnOffImplicitWait(int sec) {
        driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
    }

    public void turnOnImplicitWait() {
        driver.manage().timeouts().implicitlyWait(WebDriverBuilder.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
    }

    public BasePage swipeUpUntillElementVisibile(String elementText) {
        int count = 1;
        boolean visibility = checkElementExist(By.name(elementText));
        while (visibility == false) {
            swipeUp();
            count++;
            visibility = checkElementExist(By.name(elementText));
            if (count == 8) {
                break;
            }
        }
        return this;
    }

    public BasePage swipeDownUntillElementVisibile(String elementText, int size) {
        int count = 1;
        boolean visibility = checkElementExist(By.name(elementText));
        while (!visibility) {
            swipeDown();
            count++;
            visibility = checkElementExist(By.name(elementText));
            if (count == size) {
                break;
            }
        }
        return this;
    }


    public BasePage swipeDownUntillElementVisibile(String elementText) {
        int count = 1;
        boolean visibility = checkElementExist(By.name(elementText));
        while (visibility == false) {
            swipeDown();
            count++;
            visibility = checkElementExist(By.name(elementText));
            if (count == 8) {
                break;
            }
        }
        return this;
    }

    public BasePage swipeDownUntillElementVisibile(By locator, int size) {
        int count = 1;
        boolean visibility = checkElementExist(locator);
        while (visibility == false) {
            swipeDown();
            count++;
            visibility = checkElementExist(locator);
            if (count == size) {
                break;
            }
        }
        return this;
    }

    public BasePage swipeDownUntillElementVisibile(By locator) {
        int count = 1;
        boolean visibility = checkElementExist(locator);
        while (visibility == false) {
            swipeDown();
            count++;
            visibility = checkElementExist(locator);
            if (count == 8) {
                break;
            }
        }
        return this;
    }

    public BasePage swipeUpUntillElementVisibile(By locator) {
        int count = 1;
        boolean visibility = checkElementExist(locator);
        while (visibility == false) {
            swipeUp();
            count++;
            visibility = checkElementExist(locator);
            if (count == 8) {
                break;
            }
        }
        return this;
    }


    public T swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int starty = (int) (size.height * 0.9);
        int endy = (int) (size.height * 0.6);
        new TouchAction(driver)
                .press(point(startx, endy))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(point(startx, starty))
                .release().perform();
        return (T) this;
    }

    public T swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int endx = (int) (size.width * 0.7);
        int starty = (int) (size.height * 0.7);
        int endy = (int) (size.height * 0.4);//5
        new TouchAction(driver)
                .press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(point(endx, endy))
                .release().perform();
        return (T) this;
    }

    public BasePage takeScreenshot() {
        String testMethodName = MDC.get("testMethodName") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.SSS"));
        TestListener.savePNG(testMethodName, driver);
        return this;
    }

    public BasePage clickAdd() {
        waitABit(2000);
        driver.findElement(By.name("Add")).click();
        waitABit(2000);
        return this;
    }

    public BasePage clickCancel() {
        waitABit(2000);
        driver.findElement(By.name("Cancel")).click();
        waitABit(2000);
        return this;
    }
}
