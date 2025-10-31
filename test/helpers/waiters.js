const { TIMEOUTS } = require('./constants');

/**
 * Wait for an element to be displayed
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} selector - Element selector
 * @param {number} timeout - Timeout in milliseconds
 * @returns {Promise<WebdriverIO.Element>}
 */
async function waitForElement(driver, selector, timeout = TIMEOUTS.ELEMENT_DISPLAY) {
  const element = await driver.$(selector);
  await element.waitForDisplayed({ timeout });
  return element;
}

/**
 * Wait for an element and click it
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} selector - Element selector
 * @param {number} timeout - Timeout in milliseconds
 */
async function waitAndClick(driver, selector, timeout = TIMEOUTS.ELEMENT_DISPLAY) {
  const element = await waitForElement(driver, selector, timeout);
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
    const button = await driver.$(selector);
    await button.waitForDisplayed({ timeout: TIMEOUTS.ELEMENT_DISPLAY });
    await button.click();
    await driver.pause(TIMEOUTS.KEYPRESS_DELAY);
    console.log(`✓ Entered digit ${i + 1}/${passcode.length}`);
  }
}

module.exports = {
  waitForElement,
  waitAndClick,
  waitForTransition,
  enterPasscode,
};
