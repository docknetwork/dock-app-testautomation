package dock.utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.ios.pageobjects.BaseTestCaseIOS;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class TestListener implements ITestListener {
    public static final String LOG_BASE_FOLDER = "build/reports/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    private final boolean retryMode = Boolean.valueOf(LocalPropertiesReader.getRetryMode());
    protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final Logger log = LoggerFactory.getLogger(TestListener.class);

    protected static void createScreenshot(String testMethodName, ITestResult testResult) {
        Object x = testResult.getInstance();
        String appType = System.getProperty("testType");
        if (appType.equals("android")) {
            BaseTestCaseAndroid currentCase = (BaseTestCaseAndroid) x;
            WebDriver driver = currentCase.getDriverInstance();
//            TODO : Restore this when CI android version restriction is resolved (context: https://creds-testnet.dock.io/proof/06579da1-4195-428b-81d2-4bb7ff2787c4)
//            savePNG(testMethodName, driver);
        }
        else if (appType.equals("ios")) {
            BaseTestCaseIOS currentCase = (BaseTestCaseIOS) x;
            WebDriver driver = currentCase.getDriverInstance();
            savePNG(testMethodName, driver);
        }
    }

    public static void savePNG(final String testMethodName, WebDriver driver) {
        File destFile;
        File srcFile;
        String executionMode = LocalPropertiesReader.getExecutionMode();
        String fileName = testMethodName + ".png";
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        if (driver != null) {
            if (executionMode.equals("local")) {
                srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            }
            else {
                WebDriver augmentedDriver = new Augmenter().augment(driver);
                srcFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            }

            destFile = new File(LOG_BASE_FOLDER + "/screenshots", fileName);
            try {
                FileUtils.copyFile(srcFile, destFile);
            }
            catch (IOException e) {
            }
            Allure.addAttachment("Expand here to view the last step screenshot: " + testMethodName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
            Reporter.setEscapeHtml(false);
            prepareLinkLocationForReporting(destFile, "screenshot");
        }
    }

    public static String prepareLinkLocationForReporting(final File destFile, String linkType) {
        String url = null;

        if (LocalPropertiesReader.getExecutionMode().equals("grid")) {
            try {
                String absoluteFileLocation = destFile.getAbsolutePath();
                String repfileLocation = absoluteFileLocation.replace(System.getenv("WORKSPACE"), "artifact");
                String httpVarReplace = System.getenv("BUILD_URL").replace("http://", "");
                url = "https://" + httpVarReplace + repfileLocation;
            }
            catch (NullPointerException e) {
            }
        }
        else {
            String workingDir = System.getProperty("user.dir");
            String projectAbsolutePath = workingDir.substring(workingDir.lastIndexOf("/") + 1);
            url = destFile.getAbsolutePath().replace(workingDir, "/" + projectAbsolutePath);
        }
        if (linkType.equalsIgnoreCase("logfile")) {
            Reporter.log("Here is the complete log of executed test case:<br></br>");
            Reporter.log("<a href=" + url + ">Click link to view complete log file</a><br></br>");
        }
        else if (linkType.equalsIgnoreCase("screenshot")) {
            Reporter.log("Captured screen shot with following name: " + destFile.getName() + "<br></br>");
            Reporter.log("<a href=" + url + ">Click link to view the screenshot</a><br></br>");
        }
        return url;
    }

    @Override
    public synchronized void onTestSkipped(ITestResult testResult) {
        log.debug("Test was skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
        log.debug("Entering onTestFailedButWithinSuccessPercentage");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.debug("Entering onFinish");
        Iterator<ITestResult> failedTestCases = context.getFailedTests().getAllResults().iterator();
        while (failedTestCases.hasNext()) {
            ITestResult failedTest = failedTestCases.next();
            ITestNGMethod failedMethod = failedTest.getMethod();

            Collection<ITestNGMethod> passedMethodsCollection = context.getPassedTests().getAllMethods();

            for (ITestNGMethod pass : passedMethodsCollection) {
                if (pass.getMethodName().equals(failedMethod.getMethodName())) {
                    failedTestCases.remove();
                }
            }

            while (context.getFailedTests().getResults(failedMethod).size() > 1) {
                failedTestCases.remove();
            }
        }
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("Entering onStart");
        if (retryMode) {
            log.debug("Setting retry");
            for (ITestNGMethod method : context.getAllTestMethods()) {
                method.setRetryAnalyzerClass(new RetryAnalyzer().getClass());
            }
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult testResult) {
        log.info("Entering onTestFailure");
        String testMethodName = MDC.get("testMethodName");
        String logFilePath = LOG_BASE_FOLDER + "/separateLogsTestCases/" + testMethodName + ".log";
        if (testResult.getMethod().getRetryAnalyzerClass() != null && (retryMode)) {
            if (new RetryAnalyzer().isRetryAvailable()) {
                log.info("Entering on test failure");
                createScreenshot(testMethodName, testResult);
                log.error("Error Stacktrace: ", testResult.getThrowable());
                endTestCaseWithFailedStatus(testMethodName);
                prepareLinkLocationForReporting(new File(logFilePath), "logfile");
                addLogFiletoAllureReport(logFilePath);
            }
        }
        else {
            log.info("Error Stacktrace: ", testResult.getThrowable());
            endTestCaseWithFailedStatus(testMethodName);
            createScreenshot(testMethodName, testResult);
            prepareLinkLocationForReporting(new File(logFilePath), "logfile");
            addLogFiletoAllureReport(logFilePath);
        }
    }

    @Override
    public synchronized void onTestStart(ITestResult arg0) {
        String testMethodName = arg0.getMethod().getMethodName() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
        log.debug("Entering onTestStart log file: " + testMethodName);
        MDC.put("testMethodName", testMethodName);
        startTestCase(testMethodName);
        log.info("Log has been started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult arg0) {
        String testMethodName = MDC.get("testMethodName");
        log.info("Entering onTestSuccess");
        endTestCaseWithPassedStatus(testMethodName);
        createScreenshot(testMethodName, arg0);
        String filePath = LOG_BASE_FOLDER + "/separateLogsTestCases/" + testMethodName + ".log";
        prepareLinkLocationForReporting(new File(filePath), "logfile");
        addLogFiletoAllureReport(filePath);
    }

    protected void startTestCase(String testMethodName) {
        log.info("\n");
        log.info("**********************************************************************************************");
        log.info("Start of " + testMethodName + " Test Case execution!");
        log.info("**********************************************************************************************");
        log.info("\n");
    }

    protected void endTestCaseWithPassedStatus(String testMethodName) {
        log.info("\n");
        log.info("**********************************************************************************************");
        log.info("End of " + testMethodName + " Test Case execution Antrag_Strecke Passed");
        log.info("**********************************************************************************************");
        log.info("\n");
    }

    protected void endTestCaseWithFailedStatus(String testMethodName) {
        log.info("\n");
        log.info("**********************************************************************************************");
        log.info("End of " + testMethodName + " Test Case execution Antrag_Strecke Failed");
        log.info("**********************************************************************************************");
        log.info("\n");
    }

    private void addLogFiletoAllureReport(String fileName) {
        try {
            Allure.addAttachment("Expand here to view the test steps: ", new String(Files.readAllBytes(Paths.get(fileName))));
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
