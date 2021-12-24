package dock.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTestCase extends AbstractWebDriverEventListener {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    String infoMessage;

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        infoMessage = "Trying to find an element with locator: " + by;
        log.info(infoMessage);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        infoMessage = "Element found with locator: " + by;
        log.info(infoMessage);
    }

    @Override
    public void onException(Throwable error, WebDriver driver) {
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        infoMessage = "Before clicking on: " + element;
        log.info(infoMessage);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        infoMessage = "After clicking on: " + element;
        log.info(infoMessage);
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        log.info("Preparing to execute: " + script);
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        log.info("Following script was executed: " + script);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        log.info("Preparing to navigate back");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        log.info("Preparing to navigate forward");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        log.info("Finished navigating back");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        log.info("Finished navigating forward");
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void afterNavigateRefresh(WebDriver arg0) {
    }

    @Override
    public void beforeNavigateRefresh(WebDriver arg0) {
    }

}
