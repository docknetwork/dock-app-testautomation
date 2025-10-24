const { remote } = require('webdriverio');
const path = require('path');
const fs = require('fs');
const addContext = require('mochawesome/addContext');

// Create screenshots directory if it doesn't exist
const screenshotsDir = path.join(__dirname, '..', 'test-reports', 'screenshots');
if (!fs.existsSync(screenshotsDir)) {
  fs.mkdirSync(screenshotsDir, { recursive: true });
}

// Android capabilities for APK testing
const capabilities = {
  platformName: 'Android',
  'appium:automationName': 'UiAutomator2',
  'appium:deviceName': 'Android',

  // Path to your APK file (update this path to your APK location)
  'appium:app': path.join(__dirname, '..', 'app', 'truvera-wallet.apk'),

  // Test behavior
  'appium:noReset': false,        // Reset app state before test
  'appium:fullReset': false,      // Don't uninstall app after test
  'appium:autoGrantPermissions': true, // Auto-grant app permissions

  // App activity configuration - wait for the main activity to load
  'appium:appWaitActivity': 'com.dockapp.MainActivity',
  'appium:appWaitDuration': 30000,

  // Timeouts
  'appium:newCommandTimeout': 240,
  'appium:androidInstallTimeout': 90000,
};

const wdOpts = {
  hostname: process.env.APPIUM_HOST || 'localhost',
  port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
  logLevel: 'info',
  capabilities,
};

let driver;

// Helper function to take screenshot
async function takeScreenshot(testContext, filename) {
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

describe('Truvera Wallet Smoke Tests', function() {
  this.timeout(300000); // 5 minutes timeout for all tests

  before(async function() {
    console.log('Starting Android test...');
    console.log('Connecting to Appium server...');
    driver = await remote(wdOpts);
    console.log('✓ App installed and launched successfully');
  });

  after(async function() {
    if (driver) {
      await driver.deleteSession();
      console.log('✓ Session closed');
    }
  });

  afterEach(async function() {
    // Take screenshot after each test (success or failure)
    if (this.currentTest) {
      const testName = this.currentTest.title.replace(/[^a-z0-9]/gi, '_');
      const status = this.currentTest.state || 'unknown';
      await takeScreenshot(this, `${status}-${testName}`);
    }
  });

  it('should display Create Wallet button on app launch', async function() {
    console.log('Waiting for "Create a new Wallet" button...');

    // Wait longer for app to fully load (especially important in CI environments)
    await driver.pause(5000);

    const selector = '~CreateWalletBtn';
    const button = await driver.$(selector);
    await button.waitForDisplayed({ timeout: 30000 });

    console.log('✓ "Create a new Wallet" button found');
    await takeScreenshot(this, 'initial-screen');
  });

  it('should navigate to setup screen when Create Wallet is clicked', async function() {
    const selector = '~CreateWalletBtn';
    const button = await driver.$(selector);
    await button.waitForDisplayed({ timeout: 30000 });

    await button.click();
    console.log('✓ "Create a new Wallet" button clicked');

    // Wait for next screen
    await driver.pause(2000);
  });

  it('should display Setup Passcode button', async function() {
    // button has testID "SetupPasscodeBtn"
    // TODO: rename testID to SetupPasscodeBtn
    const setupPasscodeButton = await driver.$('~CreateWalletBtn');
    await setupPasscodeButton.waitForDisplayed({ timeout: 30000 });

    console.log('✓ "Setup passcode" button found');
  });

  it('should navigate to passcode screen when Setup Passcode is clicked', async function() {
    const setupPasscodeButton = await driver.$('~CreateWalletBtn');
    await setupPasscodeButton.click();
    console.log('✓ "Setup passcode" button clicked');

    // wait for SetupPasscodeScreen
    const createPasscodeScreen = await driver.$('~SetupPasscodeScreen');
    await createPasscodeScreen.waitForDisplayed({ timeout: 30000 });
    console.log('✓ Create passcode screen is visible');
  });

  it('should enter passcode successfully', async function() {
    // look for keyboardNumber1 button and click on it
    let keyboardNumber1Button = await driver.$('~keyboardNumber1');
    await keyboardNumber1Button.waitForDisplayed({ timeout: 30000 });

    console.log('✓ Keyboard number 1 button found');

    // click 6 times
    for (let i = 0; i < 6; i++) {
      await keyboardNumber1Button.click();
      console.log(`✓ Keyboard number 1 button clicked ${i + 1}/6`);
      await driver.pause(200);
    }

    await driver.pause(5000);
    console.log('✓ Passcode entered');
  });

  it('should confirm passcode successfully', async function() {
    console.log('Wait transition to password confirmation');

    // Password confirmation
    let keyboardNumber1Button = await driver.$('~keyboardNumber1');
    await keyboardNumber1Button.waitForDisplayed({ timeout: 30000 });

    // click 6 times
    for (let i = 0; i < 6; i++) {
      await keyboardNumber1Button.click();
      console.log(`✓ Keyboard number 1 button clicked ${i + 1}/6`);
      await driver.pause(200);
    }

    console.log('✓ Passcode confirmed');
  });

  it('should navigate to credentials screen after passcode setup', async function() {
    console.log('Wait transition to credentials screen');

    // Assert that CredentialsScreen is visible
    const credentialScreen = await driver.$('~CredentialsScreen');
    await credentialScreen.waitForDisplayed({ timeout: 30000 });
    console.log('✓ Credential screen is visible');

    // Take final screenshot
    await takeScreenshot(this, 'credentials-screen-final');
    console.log('✓ Android test completed successfully!');
  });
});
