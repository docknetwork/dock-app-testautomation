const { initializeDriver, closeDriver } = require('../helpers/driver');
const { captureTestScreenshot, takeScreenshot } = require('../helpers/screenshot');
const { createWallet } = require('../fixtures/wallet-setup');
const { waitForElement, waitAndClick, waitForTransition } = require('../helpers/waiters');
const { TIMEOUTS, SELECTORS } = require('../helpers/constants');

let driver;

describe('Feature: Import OID4VC Credential', function() {
  this.timeout(300000); // 5 minutes timeout

  let oid4vcCredentialOfferUrl;
  before(async function() {
    console.log('\n========================================');
    console.log('FEATURE: Import OID4VC Credential');
    console.log('========================================\n');

    // Initialize driver with fresh app install
    driver = await initializeDriver({ noReset: false, fullReset: false });

    // Setup: Create a wallet first
    console.log('Setting up test: Creating wallet...');
    await createWallet(driver, this);
    console.log('✓ Test setup completed - wallet created\n');
  });

  after(async function() {
    await closeDriver(driver);
  });

  afterEach(async function() {
    await captureTestScreenshot(driver, this);
  });

  it('generate oid4vc credential offer url', async function() {
    // generate oid4vc credential offer url
    console.log('Generating OID4VC credential offer url...');
    oid4vcCredentialOfferUrl = await issueOpenIDCredential();
    // copy link to clipboard

    console.log('OID4VC credential offer url:', oid4vcCredentialOfferUrl);
    driver.setClipboard(oid4vcCredentialOfferUrl);
    console.log('✓ OID4VC credential offer url copied to clipboard');
  });

  it('scan qr code', async function() {
    await waitAndClick(driver, SELECTORS.QR_CODE_BTN);
    console.log('✓ QR code screen is visible');

    await waitAndClick(driver, SELECTORS.SCAN_QR_CODE_BTN);
    console.log('✓ Scan QR code button clicked');

    takeScreenshot(driver, this, 'scan-qr-code');
  });

  it('wait for import success', async function() {
    const importSuccess = await waitForElement(driver, SELECTORS.IMPORT_SUCCESS);
    console.log('✓ Import success');
    takeScreenshot(driver, this, 'import-success');
  });
});
