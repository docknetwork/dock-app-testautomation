package dock.android.pageobjects;

import static io.appium.java_client.touch.offset.PointOption.point;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import dock.utilities.WebDriverBuilder;
import dock.android.Selector;
import dock.utilities.TestListener;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {
    protected static String appVersion;
    protected static String bundleId;
    public By txtBxDay = By.id(appVersion + "c24iconized_datePicker_day");
    public By txtBxMonth = By.id(appVersion + "c24iconized_datePicker_month");
    public By txtBxYear = By.id(appVersion + "c24iconized_datePicker_year");
    public By framePageTitle = By.id(appVersion + "car_bs_driver_title_view");
    public By txtBxTitle = By.id(appVersion + "c24iconized_title_text");
    public By txtBxEditTextDay = By.id(appVersion + "edit_text_d");
    public By txtBxEditTextMonth = By.id(appVersion + "edit_text_m");
    public By txtBxEditTextYear = By.id(appVersion + "edit_text_y");
    public By title = By.id(appVersion + "title");
    public By value = By.id(appVersion + "value");
    protected By txtBxEditText = By.id(appVersion + "edit_text");
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
    protected String fieldPath = "//android.widget.TextView[@text='%s']/following-sibling::android.widget.TextView";
    protected By btnOpenWith = By.id(appVersion + "floating_action_button");
    protected By info = By.id(appVersion + "info");
    protected By tarif_name = By.id(appVersion + "tarif_name");
    protected By text = By.id(appVersion + "text");

    protected AndroidDriver driver;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        bundleId = "com.dockapp";
        appVersion = bundleId + ":id/";
    }

    protected void swipeDownToButtonWeiter() {
        swipeDownUntillElementVisibileByContains("weiter");
    }

    public BasePage waitForElementToBeClickable(By locator, long seconds) {
        new WebDriverWait(driver, seconds).pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
        return this;
    }

    public BasePage click(By locator) {
        WebElement element = getElement(locator);
        element.click();
        log.info("Element with locator = [" + locator + "] has been clicked.");
        return this;
    }

    public void clickByView(String value) {
        click(By.xpath("//android.view.View[contains(@text,'" + value + "')]"));
        log.info("Value clicked: " + value);
    }

    public void clickOptionAfterSearch(String value) {
        AndroidElement searchTxtBx = (AndroidElement) driver.findElement(By.id(appVersion + "search_src_text"));
        searchTxtBx.sendKeys(value);
        click(By.id(appVersion + "text_view"));
        log.info("Value clicked: " + value);
    }


    public void clickBySwitchButton(String contentDec) {
        getElementByContentDescription(contentDec).findElement(By.className("android.widget.Switch")).click();
        log.info("Value clicked: " + contentDec);
    }

    public void clickByCheckedTextView(String contentDesc, int number) {
        getElementByContentDescription(contentDesc).findElements(By.className("android.widget.CheckedTextView")).get(--number).click();
        log.info("Value clicked: " + contentDesc);
    }

    public void clickByTextView(String contentDesc, String valueText) {
        getElementByContentDescription(contentDesc).findElement(By.xpath("//android.widget.TextView[contains(@text,'" + valueText + "')]")).click();
        log.info("Value clicked: " + contentDesc);
    }

    public void clickByTextView(String contentDesc, int number) {
        getElementByContentDescription(contentDesc).findElements(By.className("android.widget.TextView")).get(--number).click();
        log.info("Value clicked: " + contentDesc);
    }

    public void clickByEditTextView(String contentDesc, int number) {
        getElementByContentDescription(contentDesc).findElements(By.className("android.widget.EditText")).get(--number).click();
        log.info("Value clicked: " + contentDesc);
    }

    public void clickByTouchLayer(String contentDesc, int number) {
        getElementByContentDescription(contentDesc).findElements(By.id(appVersion + "touch_layer")).get(--number).click();
    }

    public BasePage sendTextByContentDesc(String text, String descNr) {
        scrollIntoViewByDescs(descNr);
        AndroidElement element = getElement(Selector.contentDesc(descNr)).findElement(txtBxEditText);
        element.click();
        element.sendKeys(text);
        hideKeyboard();
        log.info("Element with locator = [" + text + "] has been clicked.");
        return this;
    }

    public BasePage clickByContentDesc(String descNr) {
        click(Selector.contentDesc(descNr));
        log.info("Element with locator = [" + descNr + "] has been clicked.");
        return this;
    }

    public String getTitle() {
        return getElement(framePageTitle).findElement(txtBxTitle).getText();
    }

    public void scrollIntoViewById(String locator) {
        String uiSelector = "new UiSelector().resourceId(\"" + locator + "\")";
        String command = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).swipeDownUntillElementVisibile("
                + uiSelector + ");";
        driver.findElementByAndroidUIAutomator(command);
    }

    public void scrollIntoViewByCoordinates(int yOffset, int yMoveTo) {
        new TouchAction(driver).press(PointOption.point(550, yOffset)).waitAction().moveTo(PointOption.point(550, yMoveTo)).release().perform();
    }

    public BasePage swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int starty = (int) (size.height * 0.9);
        int endy = (int) (size.height * 0.6);
        slideScreen(startx, startx, endy, starty, 3000);
        return this;
    }

    public BasePage swipeUpUntillElementVisibile(String elementText) {
        int count = 1;
        boolean visibility = checkElementExist(By.xpath("//*[@text='" + elementText + "']"));
        while (!visibility) {
            swipeUp();
            count++;
            visibility = checkElementExist(By.xpath("//*[@text='" + elementText + "']"));
            if (count == 6) {
                break;
            }
        }
        return this;
    }

    public BasePage swipeDownUntillElementVisibile(String elementText) {
        int count = 1;
        boolean visibility = checkElementExist(By.xpath("//*[@text='" + elementText + "']"));
        while (!visibility) {
            swipeDown();
            count++;
            visibility = checkElementExist(By.xpath("//*[@text='" + elementText + "']"));
            if (count == 9) {
                break;
            }
        }
        waitABit(1000);
        return this;
    }

    public BasePage swipeDownUntillElementVisibile(By elementText) {
        int count = 1;
        boolean visibility = checkElementExist(elementText);
        while (!visibility) {
            swipeDown();
            count++;
            visibility = checkElementExist(elementText);
            if (count == 9) {
                break;
            }
        }
        waitABit(1000);
        return this;
    }

    public BasePage swipeUpUntillElementVisibile(By elementText) {
        int count = 1;
        boolean visibility = checkElementExist(elementText);
        while (!visibility) {
            swipeUp();
            count++;
            visibility = checkElementExist(elementText);
            if (count == 9) {
                break;
            }
        }
        waitABit(1000);
        return this;
    }


    public BasePage swipeDownUntillElementVisibileByContains(String elementText) {
        int count = 1;
        boolean visibility = checkElementExist(By.xpath("//*[contains(@text , '" + elementText + "')]"));
        while (!visibility) {
            swipeDown();
            count++;
            visibility = checkElementExist(By.xpath("//*[contains(@text , '" + elementText + "')]"));
            if (count == 9) {
                break;
            }
        }
        return this;
    }

    public AndroidElement getElementByXpath(String elementText) {
        return (AndroidElement) driver.findElement(By.xpath("//*[@text='" + elementText + "']"));
    }

    public AndroidElement getElementByXpathContains(String elementText) {
        return (AndroidElement) driver.findElement(By.xpath("//*[contains(@text , '" + elementText + "')]"));
    }

    public BasePage click(WebElement element) {
        element.click();
        log.info("Following element = [" + element.getText() + "] has been clicked.");
        return this;
    }

    public BasePage waitABit(long milis) {
        try {
            Thread.sleep(milis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WebElement getElement(By locator) {
        waitElementVisibility(locator);
        return driver.findElement(locator);
    }

    public AndroidElement getElementByContentDescription(String contentDescription) {
        waitElementVisibility(Selector.contentDesc(contentDescription));
        return (AndroidElement) driver.findElement(Selector.contentDesc(contentDescription));
    }

    public void sendText(String contentDescription, String value) {
        scrollIntoViewByDescs(contentDescription);
        AndroidElement element = driver.findElement(Selector.contentDesc(contentDescription)).findElement(txtBxEditText);
        element.clear();
        element.click();
        element.sendKeys(value);
        log.info("Value entered: " + value + " Content desc: " + contentDescription);
        hideKeyboard();
    }

    public List<AndroidElement> getElements(By locator) {
        waitElementVisibility(locator);
        return driver.findElements(locator);
    }

    public void clickDropDownOption(String Value) {
        scrollIntoViewByTextContains(Value);
        getElement(By.xpath("//android.widget.TextView[contains(@text,'" + Value + "')]")).click();
        log.info("Value selected: " + Value);
    }

    public void clickByXpathCheckedTextView(String Value) {
        scrollIntoViewByTextContains(Value);
        getElement(By.xpath("//android.widget.CheckedTextView[contains(@text,'" + Value + "')]")).click();
        log.info("Value selected: " + Value);
    }

    public void clickByXpathAndroidWidgetTextView(String Value) {
        scrollIntoViewByTextContains(Value);
        getElement(By.xpath("//android.widget.TextView[contains(@text,'" + Value + "')]")).click();
        log.info("Value selected: " + Value);
    }

    public void clickDropDownOptionEs(String Value) {
        scrollIntoViewByTextContains(Value);
        getElement(By.xpath("//android.widget.CheckedTextView[@text='" + Value + "']")).click();
        log.info("Value selected: " + Value);
    }

    public void clickButtonByXpath(String Value) {
        scrollIntoViewByTextContains(Value);
        getElement(By.xpath("//*[contains(@text,'" + Value + "')]")).click();
        log.info("Value clicked: " + Value);
    }

    public BasePage waitElementVisibility(By locator) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    public BasePage waitElementVisibility(String locator) {
        final String selector = "new UiSelector().text(\"" + locator + "\").className(\"android.widget.TextView\")";
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator(selector)));
        return this;
    }

    public BasePage waitForElementInVisibility(String locator) {
        final String selector = "new UiSelector().text(\"" + locator + "\").className(\"android.widget.TextView\")";
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AndroidUIAutomator(selector)));
        return this;
    }

    public BasePage waitElementVisibility(By locator, int sec) {
        takeScreenshot();
        new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    public void waitElementInVisibility(By locator, int sec) {
        new WebDriverWait(driver, sec).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public BasePage waitElementVisibility(WebElement element) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public BasePage hideKeyboard() {
        waitABit(500);
        if (driver.isKeyboardShown()) {
            driver.hideKeyboard();
            waitABit(200);
        }
        return this;
    }

    public BasePage sendText(By locator, String text) {
        AndroidElement element = (AndroidElement) getElement(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
        log.info("locator = [" + locator + "], Text = [" + text + "] has been entered.");
        hideKeyboard();
        return this;
    }

    public BasePage sendText(AndroidElement element, String text) {
        waitElementVisibility(element);
        element.click();
        element.clear();
        element.sendKeys(text);
        log.info("Text = [" + text + "] has been entered.");
        hideKeyboard();
        return this;
    }

    public BasePage sendTextDate(AndroidElement element, String text) {
        waitElementVisibility(element);
        if (text.length() == 1) {
            log.info("textlength is " + text.length());
            text = "0" + text;
        }
        if (element.getText().equals("TT") || element.getText().equals("MM") || element.getText().equals("JJJJ")) {
            element.setValue(text);
        }
        else {
            waitElementVisibility(element);
            element.replaceValue(text);
        }
        log.info("Text = [" + text + "] has been entered.");
        return this;
    }

    //Read Text
    public String getText(By locator) {
        String text = getElement(locator).getText();
        log.info("Element displayed text = " + text);
        return text;
    }

    public String getText(WebElement element) {
        String text = element.getText();
        log.info("Element displayed text = " + text);
        return text;
    }

    public boolean isDisplayed(By locator) {
        boolean status = getElement(locator).isDisplayed();
        if (status)
            log.info(locator + " is displayed.");
        else
            log.info(locator + " is not displayed.");
        return status;
    }

    public boolean isDisplayedByText(String text) {
        By element = By.xpath("//*[contains(@text,'"+text+"')]");
        boolean status = getElement(element).isDisplayed();
        if (status)
            log.info(text + " is displayed.");
        else
            log.info(text + " is not displayed.");
        return status;
    }

    public boolean contains(By locator, String expectedText) {
        String actualText = getElement(locator).getText();
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
        waitABit(500);
        driver.navigate().back();
        waitABit(500);
        return this;
    }

    public boolean checkElementExist(By locator) {
        turnOffImplicitWait();
        boolean status = driver.findElements(locator).size() > 0;
        if (status) {
            log.info("Element Found with status : " + locator + " : " + status);
        }
        else {
            log.info("Element not present with status : " + locator + " : " + status);
        }
        turnOnImplicitWait();
        return status;
    }

    public boolean checkElementExistByXpath(String text) {
        turnOffImplicitWait();
        boolean status = driver.findElements(By.xpath("//*[@text='" + text + "']")).size() > 0;
        if (status) {
            log.info("Element Found with status : " + text + " : " + status);
        }
        else {
            log.info("Element not present with status : " + text + " : " + status);
        }
        turnOnImplicitWait();
        return status;
    }

    public boolean checkElementExistByXpathContains(String text) {
        turnOffImplicitWait();
        boolean status = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + text + "')]")).size() > 0;
        if (status) {
            log.info("Element Found with status : " + text + " : " + status);
        }
        else {
            log.info("Element not present with status : " + text + " : " + status);
        }
        turnOnImplicitWait();
        return status;
    }

    public void turnOffImplicitWait() {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void turnOnImplicitWait() {
        driver.manage().timeouts().implicitlyWait(WebDriverBuilder.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
    }

    public AndroidElement scrollIntoViewByDescs(String desc) {
        AndroidElement element = (AndroidElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                ".scrollDescriptionIntoView(" + "\"" + desc + "\"" + ");");
        waitABit(400);
        return element;
    }

    public BasePage scrollIntoViewByTextContains(String text) {
        swipeDownUntillElementVisibileByContains(text);
        return this;
    }

    public BasePage swipe(int x1, int y1, int x2, int y2) {
        slideScreen(x1, x2, y1, y2, 1000);
        return this;
    }

    public void dragSeekBarTo(int scrollPosition, int seekbar) {
        log.info("Scrolling to: " + scrollPosition);
        WebElement seekBarele = (WebElement) driver.findElements(By.className("android.widget.SeekBar")).get(--seekbar);
        int start = seekBarele.getLocation().getX();
        int y = seekBarele.getLocation().getY();
        int end = start + seekBarele.getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        int moveTo = (int) (end * ((float) scrollPosition / 100));
        action.longPress(PointOption.point(start, y))
                .moveTo(PointOption.point(moveTo, y))
                .release().perform();
    }

    public void enterDate(By locator, String date) {
        String month = String.valueOf(LocalDate.parse(date, localDateFormatter).getMonth().getValue());
        String day = String.valueOf(LocalDate.parse(date, localDateFormatter).getDayOfMonth());
        String year = String.valueOf(LocalDate.parse(date, localDateFormatter).getYear());

        AndroidElement dayTxtElement = getElement(locator).findElement(txtBxDay);
        sendTextDate(dayTxtElement, day);

        AndroidElement monthTxtElement = getElement(locator).findElement(txtBxMonth);
        sendTextDate(monthTxtElement, month);

        AndroidElement yearTxtElement = getElement(locator).findElement(txtBxYear);
        sendTextDate(yearTxtElement, year);

        log.info("Date entered: " + date);
        hideKeyboard();
    }

    public BasePage swipeLittle() {
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
        return this;
    }

    public BasePage swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int endx = (int) (size.width * 0.7);
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.5);
        slideScreen(startx, endx, starty, endy, 3000);
        return this;
    }

    public void slideABitFromTopToBottom() {
        waitABit(300);
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.3);
        int endx = (int) (size.width * 0.3);
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.6);
        slideScreen(startx, endx, starty, endy, 3000);
        waitABit(300);
    }

    private void slideScreen(final int startx, final int endx, final int starty, final int endy, final long waitOptions) {
        new TouchAction(driver)
                .press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitOptions)))
                .moveTo(point(endx, endy))
                .release().perform();
    }

    public void enterDateByDescription(String date, By locator) {
        log.info("Date to enetered: " + date);
        AndroidElement txtBxDay = getElement(locator).findElement(txtBxEditTextDay);
        String day = date.substring(0, 2);
        txtBxDay.click();
        txtBxDay.clear();
        txtBxDay.sendKeys(day);

        AndroidElement txtBxMonth = getElement(locator).findElement(txtBxEditTextMonth);
        String month = date.substring(3, 5);
        txtBxMonth.clear();
        txtBxMonth.sendKeys(month);

        AndroidElement txtBxJahr = getElement(locator).findElement(txtBxEditTextYear);
        String year = date.substring(6, 10);
        txtBxJahr.clear();
        txtBxJahr.sendKeys(year);
        hideKeyboard();
    }

    public String getValueFromTheField(String nameOfTheField) {
        return driver.findElement(By.xpath(String.format(fieldPath, nameOfTheField))).getText();
    }

    public BasePage takeScreenshot() {
        String testMethodName = MDC.get("testMethodName") + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.SSS"));
        TestListener.savePNG(testMethodName, driver);
        return this;
    }

    public boolean isBtnOpenWithDisplayed() {
        return checkElementExist(btnOpenWith);
    }

    public String getInfo() {
        return getText(info);
    }

    public String getText() {
        return getText(text);
    }

    public String getTitleText() {
        return getText(title);
    }

    public void waitforResults() {
        waitElementVisibility(tarif_name, 120);
    }

    protected void clickByUiAutomator(final String selector) {
        log.info("Click on selector ....." + selector);
        driver.findElement(MobileBy.AndroidUIAutomator(selector)).click();
        log.info("Successfully clicked on selector ....." + selector);
    }

    protected String getTextByUiAutomator(final String selector) {
        log.info("Getting Text on selector ....." + selector);
        return driver.findElement(MobileBy.AndroidUIAutomator(selector)).getText();
    }
}
