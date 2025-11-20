const { waitForElement, waitAndClick, waitForTransition } = require('./waiters');
const { SELECTORS, TIMEOUTS } = require('./constants');
const { takeScreenshot } = require('./screenshot');

/**
 * Enter credential password in the password modal dialog
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {string} password - Password to enter
 * @param {Object} testContext - Optional Mocha test context for screenshots
 * @returns {Promise<void>}
 */
async function enterCredentialPassword(driver, password, testContext = null) {
  try {
    console.log('Waiting for password modal...');
    
    // Wait for the password modal title to appear
    await waitForElement(driver, SELECTORS.PASSWORD_MODAL_TITLE, TIMEOUTS.ELEMENT_DISPLAY);
    console.log('✓ Password modal appeared');

    if (testContext) {
      await takeScreenshot(driver, testContext, 'password-modal-appeared');
    }

    // Wait for and find the password input field
    console.log('Locating password input field...');
    const passwordInput = await waitForElement(
      driver,
      SELECTORS.PASSWORD_INPUT_FIELD,
      TIMEOUTS.ELEMENT_DISPLAY
    );
    console.log('✓ Password input field found');

    // Click the input field to focus it
    await passwordInput.click();
    await waitForTransition(driver, 500);

    // Enter the password using setValue
    console.log(`Entering password: ${'*'.repeat(password.length)}`);
    await passwordInput.setValue(password);
    console.log('✓ Password entered');

    if (testContext) {
      await takeScreenshot(driver, testContext, 'password-entered');
    }

    // Click the OK button
    console.log('Clicking OK button...');
    await waitAndClick(driver, SELECTORS.PASSWORD_MODAL_OK_BTN, TIMEOUTS.ELEMENT_DISPLAY);
    console.log('✓ OK button clicked');

    // Wait for modal to close/dismiss
    await waitForTransition(driver, 2000);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'password-modal-dismissed');
    }

    console.log('✓ Password entry completed successfully');
  } catch (error) {
    console.error(`✗ Failed to enter credential password: ${error.message}`);
    
    if (testContext) {
      await takeScreenshot(driver, testContext, 'password-entry-error');
    }

    throw new Error(`Password entry failed: ${error.message}`);
  }
}

module.exports = {
  enterCredentialPassword,
};

