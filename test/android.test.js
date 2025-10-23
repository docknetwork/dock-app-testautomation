const { remote } = require('webdriverio');
const path = require('path');

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

async function runAndroidTest() {
  console.log('Starting Android test...');
  console.log('Connecting to Appium server...');

  const driver = await remote(wdOpts);

  try {
    console.log('✓ App installed and launched successfully');


    // Wait for button with testID "CreateWalletBtn"
    console.log('Waiting for "Create a new Wallet" button...');

    // wait for 2 seconds
    await driver.pause(2000);

    const selector = '~CreateWalletBtn';
    const button = await driver.$(selector);
    await button.waitForDisplayed({ timeout: 10000 });

    console.log('✓ "Create a new Wallet" button found');


    // click on the button
    await button.click();
    console.log('✓ "Create a new Wallet" button clicked');


    // click on Setup passcode button
    // button has testID "SetupPasscodeBtn"
    // TODO: rename testID to SetupPasscodeBtn
    const setupPasscodeButton = await driver.$('~CreateWalletBtn');
    await setupPasscodeButton.waitForDisplayed({ timeout: 30000 });

    // click on the button
    await setupPasscodeButton.click();
    console.log('✓ "Setup passcode" button clicked');

    console.log('✓ "Create a new Wallet" button clicked');


    // wait for SetupPasscodeScreen
    const createPasscodeScreen = await driver.$('~SetupPasscodeScreen');
    await createPasscodeScreen.waitForDisplayed({ timeout: 30000 });
    console.log('✓ Create passcode screen is visible');

    // look for keyboardNumber1 button and click on it
    let keyboardNumber1Button = await driver.$('~keyboardNumber1');
    await keyboardNumber1Button.waitForDisplayed({ timeout: 30000 });

    // click 6 times 
    for (let i = 0; i < 6; i++) {
      await keyboardNumber1Button.click();
      await driver.pause(200);
    }

    await driver.pause(1000);
    // Password confirmation
    // look for keyboardNumber1 button and click on it
    keyboardNumber1Button = await driver.$('~keyboardNumber1');
    await keyboardNumber1Button.waitForDisplayed({ timeout: 30000 });
    // click 6 times 
    for (let i = 0; i < 6; i++) {
      await keyboardNumber1Button.click();
      await driver.pause(200);
    }

    // Asset that CredentialsScreen is visible
    const credentialScreen = await driver.$('~CredentialsScreen');
    await credentialScreen.waitForDisplayed({ timeout: 30000 });
    console.log('✓ Credential screen is visible');
    // take screenshot of the screen
    const screenshot = await driver.takeScreenshot();
    require('fs').writeFileSync('app-screenshot.png', screenshot, 'base64');

    console.log('✓ Screenshot saved as app-screenshot.png');
    console.log('✓ Android test completed successfully!');
  } catch (error) {
    console.error('✗ Android test failed:', error.message);

    // Take screenshot on failure
    try {
      const screenshot = await driver.takeScreenshot();
      require('fs').writeFileSync('error-screenshot.png', screenshot, 'base64');
      console.log('✓ Error screenshot saved as error-screenshot.png');
    } catch (screenshotError) {
      console.error('Could not save screenshot:', screenshotError.message);
    }

    throw error;
  } finally {
    // await driver.deleteSession();
    console.log('✓ Session closed');
  }
}

runAndroidTest().catch(console.error);
