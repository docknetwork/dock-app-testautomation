package dock.utilities;

import static dock.utilities.TestListener.LOG_BASE_FOLDER;
import static dock.utilities.TestListener.createScreenshot;
import static dock.utilities.TestListener.prepareLinkLocationForReporting;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 1;
    private final int maxCount = 2;

    private final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);

    public boolean isRetryAvailable() {
        return count < maxCount;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (isRetryAvailable()) {
            if (count <= maxCount) {
                logger.info("Going to retry test case: " + result.getName() + " " + count + "  time out of " + maxCount);
            }
            count++;
            result.setStatus(ITestResult.SKIP);
            String testMethodName = MDC.get("testMethodName");
            createScreenshot(testMethodName, result);
            logger.error("Error Stacktrace: ", result.getThrowable());

            logger.info("**********************************************************************************************");
            logger.info("End of " + testMethodName + " Test Case execution Antrag_Strecke Failed");
            logger.info("**********************************************************************************************");
            prepareLinkLocationForReporting(new File(LOG_BASE_FOLDER + "/separateLogsTestCases/" + testMethodName + ".log"), "logfile");
            return true;
        }
        else {
            return false;
        }
    }
}
