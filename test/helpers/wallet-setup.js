const { waitForElement, waitAndClick, waitForTransition, enterPasscode } = require('../helpers/waiters');
const { SELECTORS, TEST_DATA, TIMEOUTS } = require('../helpers/constants');
const { takeScreenshot } = require('../helpers/screenshot');

/**
 * Complete wallet creation flow
 * This is a reusable setup fixture for tests that require a created wallet
 * @param {WebdriverIO.Browser} driver - WebDriver instance
 * @param {Object} testContext - Mocha test context for screenshots
 * @param {Object} options - Setup options
 * @param {string} options.passcode - Passcode to set (default: TEST_DATA.DEFAULT_PASSCODE)
 * @returns {Promise<Object>} Wallet setup result
 */
async function createWallet(driver, testContext = null, options = {}) {
  const passcode = options.passcode || TEST_DATA.DEFAULT_PASSCODE;

  console.log('Starting wallet creation setup...');

  try {
    // Step 1: Click "Create a new Wallet" button
    console.log('Step 1: Waiting for Create Wallet button...');
    await waitAndClick(driver, SELECTORS.CREATE_WALLET_BTN, TIMEOUTS.APP_LAUNCH);
    await waitForTransition(driver, 2000);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-01-create-wallet-clicked');
    }

    // Step 2: Click "Setup Passcode" button
    console.log('Step 2: Waiting for Setup Passcode button...');
    await waitAndClick(driver, SELECTORS.SETUP_PASSCODE_BTN, TIMEOUTS.ELEMENT_DISPLAY);

    // Step 3: Wait for passcode screen
    console.log('Step 3: Waiting for passcode screen...');
    await waitForElement(driver, SELECTORS.SETUP_PASSCODE_SCREEN, TIMEOUTS.ELEMENT_DISPLAY);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-02-passcode-screen');
    }

    // Step 4: Enter passcode
    console.log('Step 4: Entering passcode...');
    await enterPasscode(driver, passcode);
    await waitForTransition(driver, 5000);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-03-passcode-entered');
    }

    // Step 5: Confirm passcode
    console.log('Step 5: Confirming passcode...');
    await enterPasscode(driver, passcode);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-04-passcode-confirmed');
    }

    // Step 6: Wait for credentials screen (confirms wallet was created)
    console.log('Step 6: Waiting for credentials screen...');
    const credentialScreen = await waitForElement(
      driver,
      SELECTORS.CREDENTIALS_SCREEN,
      TIMEOUTS.ELEMENT_DISPLAY
    );

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-05-wallet-created');
    }

    console.log('✓ Wallet creation setup completed successfully');

    return {
      success: true,
      passcode,
      message: 'Wallet created successfully',
    };
  } catch (error) {
    console.error(`✗ Wallet creation setup failed: ${error.message}`);

    if (testContext) {
      await takeScreenshot(driver, testContext, 'setup-error-wallet-creation');
    }

    throw new Error(`Wallet setup failed: ${error.message}`);
  }
}

/**
 * Navigate to settings or specific screens
 * Add more navigation helpers as needed
 */
async function navigateToSettings(driver, testContext = null) {
  // TODO: Implement when settings navigation is available
  console.log('Navigate to settings - not yet implemented');
}

module.exports = {
  createWallet,
  navigateToSettings,
};
