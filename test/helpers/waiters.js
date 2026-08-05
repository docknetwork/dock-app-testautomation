const { TIMEOUTS, SELECTORS, TEST_DATA } = require('./constants');
const { takeScreenshot } = require('./screenshot');

const ANR_WAIT_BTN = 'android:id/aerr_wait';

/**
 * Dismiss the system "isn't responding" (ANR) dialog if present, by tapping "Wait".
 * The emulator can raise this on the launcher/app under CPU pressure, and it sits on
 * top of the app blocking every element lookup until dismissed.
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @returns {Promise<boolean>} whether the dialog was found and dismissed
 */
async function dismissAnrDialogIfPresent(driver) {
  const waitBtn = await driver.$(`id=${ANR_WAIT_BTN}`);
  if (!(await waitBtn.isDisplayed().catch(() => false))) {
    return false;
  }
  console.log('⚠ ANR dialog detected, tapping "Wait"');
  await waitBtn.click().catch(() => {});
  return true;
}

/**
 * Wait for an element to be displayed
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} selector - Element selector
 * @param {number} timeout - Timeout in milliseconds
 * @returns {Promise<WebdriverIO.Element>}
 */
async function waitForElement(driver, selector, timeout = TIMEOUTS.ELEMENT_DISPLAY) {
  const element = await driver.$(selector);
  await driver.waitUntil(
    async () => {
      if (await element.isDisplayed().catch(() => false)) {
        return true;
      }
      await dismissAnrDialogIfPresent(driver);
      return false;
    },
    { timeout, interval: 1000, timeoutMsg: `element ("${selector}") still not displayed after ${timeout}ms` }
  );
  return element;
}

/**
 * Wait for an element and click it
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} selector - Element selector
 * @param {number} timeout - Timeout in milliseconds
 */
async function waitAndClick(driver, selector, options = {}) {
  if (typeof options === 'number') {
    options = { timeout: options };
  }
  const element = await waitForElement(driver, selector, options.timeout || TIMEOUTS.ELEMENT_DISPLAY);

  if (options.screenshotName) {
    await takeScreenshot(driver, options.screenshotName);
  }

  await element.click();

  console.log(`✓ Clicked element: ${selector}`);
}

/**
 * Wait for screen transition
 * @param {number} duration - Wait duration in milliseconds
 */
async function waitForTransition(driver, duration = TIMEOUTS.SCREEN_TRANSITION) {
  await driver.pause(duration);
}

/**
 * Enter a passcode using the numeric keyboard
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} passcode - Passcode to enter (e.g., '111111')
 * @param {string} keyboardSelector - Base selector for keyboard buttons
 */
async function enterPasscode(driver, passcode, keyboardSelector = '~keyboardNumber') {
  console.log(`Entering passcode: ${'*'.repeat(passcode.length)}`);

  for (let i = 0; i < passcode.length; i++) {
    const digit = passcode[i];
    const selector = `${keyboardSelector}${digit}`;
    const button = await waitForElement(driver, selector, TIMEOUTS.ELEMENT_DISPLAY);
    await button.click();
    await driver.pause(TIMEOUTS.KEYPRESS_DELAY);
    console.log(`✓ Entered digit ${i + 1}/${passcode.length}`);
  }
}

/**
 * Unlock the wallet if the unlock screen is shown.
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} passcode - Passcode to enter (default: TEST_DATA.DEFAULT_PASSCODE)
 */
async function unlockWallet(driver, passcode = TEST_DATA.DEFAULT_PASSCODE) {
  const unlockScreen = await driver.$(SELECTORS.UNLOCK_WALLET_SCREEN);
  const isLocked = await unlockScreen
    .waitForDisplayed({ timeout: TIMEOUTS.SCREEN_TRANSITION })
    .then(() => true)
    .catch(() => false);

  if (!isLocked) {
    console.log('✓ Wallet already unlocked');
    return;
  }

  console.log('Unlocking wallet...');
  await enterPasscode(driver, passcode);
  await waitForElement(driver, SELECTORS.CREDENTIALS_SCREEN, TIMEOUTS.ELEMENT_DISPLAY);
  console.log('✓ Wallet unlocked');
}

module.exports = {
  waitForElement,
  waitAndClick,
  waitForTransition,
  enterPasscode,
  unlockWallet,
  dismissAnrDialogIfPresent,
};
