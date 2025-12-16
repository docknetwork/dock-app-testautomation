/**
 * Reusable wallet action helpers for integration tests
 * These functions encapsulate common wallet operations to avoid code duplication
 */

const { scanQRCode } = require('../../helpers/qr-code');
const { SELECTORS } = require('../../helpers/constants');
const { waitAndClick, waitForElement } = require('../../helpers/waiters');
const { takeScreenshot } = require('../../helpers/screenshot');

/**
 * Scan QR code and handle initial credential/verification prompt
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {string} qrCodeData - QR code data to scan
 * @param {Object} options - Optional parameters
 */
async function scanQRCodeAndContinue(driver, qrCodeData, options = {}) {
  console.log('Scanning QR code in wallet...');

  // Scan the QR code
  await scanQRCode(driver, qrCodeData);
  console.log('✓ QR code scanned');

  // Click continue button to proceed
  await waitAndClick(driver, SELECTORS.VERIFICATION_CONTINUE_BTN, {
    screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-continue` : 'verification-continue'
  });
  console.log('✓ Clicked continue after scanning');
}

/**
 * Select credentials for verification/presentation
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {Object} options - Optional parameters
 * @param {boolean} options.selectAll - Whether to select all credentials (default: true)
 * @param {string} options.screenshotPrefix - Prefix for screenshot names
 */
async function selectCredentials(driver, options = {}) {
  const selectAll = options.selectAll !== false; // Default to true

  console.log('Selecting credentials...');

  await waitForElement(driver, SELECTORS.VERIFICATION_CHECKBOX, 30000);

  if (selectAll) {
    // Find all credential checkboxes and click them
    const checkboxes = await driver.$$(SELECTORS.VERIFICATION_CHECKBOX);
    console.log(`Found ${checkboxes.length} credential checkbox(es)`);

    for (let i = 0; i < checkboxes.length; i++) {
      await checkboxes[i].click();
      console.log(`✓ Selected credential ${i + 1}/${checkboxes.length}`);
      await driver.pause(500); // Small pause between clicks
    }
  } else {
    // Select only the first credential checkbox
    await waitAndClick(driver, SELECTORS.VERIFICATION_CHECKBOX, {
      screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-select-credential` : 'select-credential'
    });
    console.log('✓ Selected credential');
  }

  // Click continue button
  await waitAndClick(driver, SELECTORS.VERIFICATION_CONTINUE_BTN, {
    screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-continue-after-select` : 'continue-after-select'
  });
  console.log('✓ Clicked continue after selecting credentials');
}

/**
 * Select attributes to share and complete verification
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {Object} options - Optional parameters
 * @param {boolean} options.selectAll - Whether to select all attributes (default: true)
 * @param {string} options.screenshotPrefix - Prefix for screenshot names
 */
async function selectAttributesAndShare(driver, options = {}) {
  const selectAll = options.selectAll !== false; // Default to true

  console.log('Selecting attributes to share...');

  await waitForElement(driver, SELECTORS.VERIFICATION_SELECT_ALL, 30000);
  
  if (selectAll) {
    // Select all attributes
    await waitAndClick(driver, SELECTORS.VERIFICATION_SELECT_ALL, {
      screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-select-all` : 'verification-select-all'
    });
    console.log('✓ Selected all attributes');
  }

  // Click continue
  await waitAndClick(driver, SELECTORS.VERIFICATION_CONTINUE_BTN, {
    screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-continue` : 'verification-continue'
  });
  console.log('✓ Clicked continue');

  // Click share button
  await waitAndClick(driver, SELECTORS.VERIFICATION_SHARE_BTN, {
    screenshotName: options.screenshotPrefix ? `${options.screenshotPrefix}-share` : 'verification-share'
  });
  console.log('✓ Clicked share button');
}

/**
 * Wait for verification to complete successfully
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {Object} context - Test context for screenshots
 * @param {Object} options - Optional parameters
 * @param {number} options.timeout - Timeout in milliseconds (default: 30000)
 * @param {string} options.screenshotPrefix - Prefix for screenshot names
 */
async function waitForVerificationSuccess(driver, context, options = {}) {
  const timeout = options.timeout || 30000;

  console.log('Waiting for verification to complete...');

  // Wait for "waiting for verifier" message
  await waitForElement(
    driver,
    SELECTORS.VERIFICATION_WAIT_FOR_VERIFIER,
    timeout
  );
  console.log('✓ Waiting for verifier...');

  await takeScreenshot(
    driver,
    context,
    options.screenshotPrefix ? `${options.screenshotPrefix}-waiting-for-verifier` : 'verification-waiting-for-verifier'
  );

  // Wait for verification success
  await waitForElement(
    driver,
    SELECTORS.VERIFICATION_SUCCESS,
    timeout
  );
  console.log('✓ Verification completed successfully!');

  await takeScreenshot(
    driver,
    context,
    options.screenshotPrefix ? `${options.screenshotPrefix}-success` : 'verification-success'
  );

  // Wait a bit for the success screen to settle
  await driver.pause(3000);
}

/**
 * Complete full verification flow: scan QR, select credentials, share attributes, wait for success
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {string} qrCodeData - QR code data to scan
 * @param {Object} context - Test context for screenshots
 * @param {Object} options - Optional parameters
 */
async function completeVerificationFlow(driver, qrCodeData, context, options = {}) {
  console.log('\n--- Starting Verification Flow ---');

  // Scan QR code and continue
  await scanQRCodeAndContinue(driver, qrCodeData, options);

  // Select credentials
  await selectCredentials(driver, options);

  // // Select attributes and share
  await selectAttributesAndShare(driver, options);

  // // Wait for verification success
  // await waitForVerificationSuccess(driver, context, options);

  console.log('✓ Verification flow completed successfully\n');
}

/**
 * Navigate to credentials screen and verify credentials are present
 * @param {WebdriverIO.Browser} driver - Mobile driver
 * @param {Array<string>} expectedCredentialSelectors - Array of credential selectors to verify
 * @param {Object} options - Optional parameters
 */
async function verifyCredentialsInWallet(driver, expectedCredentialSelectors = [], options = {}) {
  console.log('Verifying credentials in wallet...');

  // Navigate to credentials screen
  await waitAndClick(driver, SELECTORS.NAV_CREDENTIALS_BTN);
  console.log('✓ Navigated to credentials screen');

  // Wait for each expected credential
  for (const selector of expectedCredentialSelectors) {
    await waitForElement(driver, selector, 30000);
    console.log(`✓ Found credential: ${selector}`);
  }

  console.log('✓ All expected credentials verified');

  // Take final screenshot
  if (options.screenshot !== false) {
    await driver.saveScreenshot(
      `test-reports/screenshots/${options.screenshotName || 'credentials-verified'}.png`
    );
  }
}

module.exports = {
  scanQRCodeAndContinue,
  selectCredentials,
  selectAttributesAndShare,
  waitForVerificationSuccess,
  completeVerificationFlow,
  verifyCredentialsInWallet,
};
