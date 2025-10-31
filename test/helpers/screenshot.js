const path = require('path');
const fs = require('fs');
const addContext = require('mochawesome/addContext');

// Create screenshots directory if it doesn't exist
const screenshotsDir = path.join(__dirname, '..', '..', 'test-reports', 'screenshots');
if (!fs.existsSync(screenshotsDir)) {
  fs.mkdirSync(screenshotsDir, { recursive: true });
}

/**
 * Take a screenshot and add it to the test report
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {Object} testContext - Mocha test context (this)
 * @param {string} filename - Screenshot filename (without extension)
 * @returns {Promise<string>} Path to the saved screenshot
 */
async function takeScreenshot(driver, testContext, filename) {
  try {
    const timestamp = new Date().toISOString().replace(/:/g, '-').replace(/\./g, '-');
    const screenshotPath = path.join(screenshotsDir, `${filename}-${timestamp}.png`);
    const screenshot = await driver.takeScreenshot();
    fs.writeFileSync(screenshotPath, screenshot, 'base64');

    // Add screenshot to mochawesome report
    if (testContext) {
      addContext(testContext, {
        title: filename,
        value: `../screenshots/${path.basename(screenshotPath)}`
      });
    }

    console.log(`✓ Screenshot saved: ${path.basename(screenshotPath)}`);
    return screenshotPath;
  } catch (error) {
    console.error(`Failed to take screenshot: ${error.message}`);
  }
}

/**
 * Setup automatic screenshot capture after each test
 * Should be called in afterEach hook
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {Object} testContext - Mocha test context (this)
 */
async function captureTestScreenshot(driver, testContext) {
  if (testContext.currentTest && driver) {
    const testName = testContext.currentTest.title.replace(/[^a-z0-9]/gi, '_');
    const status = testContext.currentTest.state || 'unknown';
    await takeScreenshot(driver, testContext, `${status}-${testName}`);
  }
}

module.exports = {
  takeScreenshot,
  captureTestScreenshot,
  screenshotsDir,
};
