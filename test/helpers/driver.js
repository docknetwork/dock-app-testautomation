const { remote } = require('webdriverio');
const path = require('path');
const { TIMEOUTS } = require('./constants');
const dotenv = require('dotenv');
dotenv.config();

// Android capabilities for APK testing
const getCapabilities = (options = {}) => {
  return {
    platformName: 'Android',
    'appium:automationName': 'UiAutomator2',
    'appium:deviceName': 'Android',

    // Path to APK file
    'appium:app': path.join(__dirname, '..', '..', 'app', 'truvera-wallet.apk'),

    // Test behavior - configurable for different test scenarios
    'appium:noReset': options.noReset || false,
    'appium:fullReset': options.fullReset || false,
    'appium:autoGrantPermissions': true,

    // App activity configuration
    'appium:appWaitActivity': 'com.dockapp.MainActivity',
    'appium:appWaitDuration': TIMEOUTS.APP_LAUNCH,

    // Timeouts
    'appium:newCommandTimeout': TIMEOUTS.SESSION_COMMAND,
    'appium:androidInstallTimeout': TIMEOUTS.ANDROID_INSTALL,
    // clipboard 
    'appium:clipboardPermission': true,
  };
};

const getWebDriverOptions = (capabilities) => {
  return {
    hostname: process.env.APPIUM_HOST || 'localhost',
    port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
    logLevel: 'info',
    capabilities,
  };
};

/**
 * Initialize WebDriver session
 * @param {Object} options - Driver options
 * @param {boolean} options.noReset - Don't reset app state (default: false)
 * @param {boolean} options.fullReset - Uninstall app after test (default: false)
 * @returns {Promise<WebdriverIO.Browser>}
 */
async function initializeDriver(options = {}) {
  console.log('Initializing WebDriver session...');
  const capabilities = getCapabilities(options);
  const wdOpts = getWebDriverOptions(capabilities);

  const driver = await remote(wdOpts);
  console.log('✓ WebDriver session initialized');

  // Wait for app to fully load
  await driver.pause(TIMEOUTS.SCREEN_TRANSITION);

  return driver;
}

/**
 * Close WebDriver session
 * @param {WebdriverIO.Browser} driver
 */
async function closeDriver(driver) {
  if (driver) {
    await driver.deleteSession();
    console.log('✓ WebDriver session closed');
  }
}

/**
 * Set clipboard content on Android device
 * @param {WebdriverIO.Browser} driver
 * @param {string} text - Text to copy to clipboard
 */
async function setClipboard(driver, text) {
  await driver.execute('mobile: setClipboard', {
    content: Buffer.from(text).toString('base64'),
    contentType: 'plaintext'
  });
}

/**
 * Get clipboard content from Android device
 * @param {WebdriverIO.Browser} driver
 * @returns {Promise<string>} Clipboard content
 */
async function getClipboard(driver) {
  const base64Content = await driver.execute('mobile: getClipboard');
  return Buffer.from(base64Content, 'base64').toString('utf-8');
}

const NO_RESET = process.env.NO_RESET === 'true' || false;

module.exports = {
  NO_RESET,
  initializeDriver,
  closeDriver,
  getCapabilities,
  getWebDriverOptions,
  setClipboard,
  getClipboard,
};
