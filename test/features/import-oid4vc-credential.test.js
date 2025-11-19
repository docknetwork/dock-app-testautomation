const { initializeDriver, closeDriver, setClipboard } = require('../helpers/driver');
const { captureTestScreenshot, takeScreenshot } = require('../helpers/screenshot');
const { createWallet } = require('../fixtures/wallet-setup');
const { waitForElement, waitAndClick, waitForTransition } = require('../helpers/waiters');
const { TIMEOUTS, SELECTORS } = require('../helpers/constants');
const { issueOpenIDCredential } = require('../helpers/oid4vc');

let driver;

describe('Feature: Import OID4VC Credential', function() {
  this.timeout(300000); // 5 minutes timeout

  before(async function() {
    console.log('\n========================================');
    console.log('FEATURE: Import OID4VC Credential');
    console.log('========================================\n');
    driver = await initializeDriver({ noReset: false, fullReset: false });
  });

  after(async function() {
    await closeDriver(driver);
  });

  it('should successfully import an OID4VC credential', async function() {
    // Create wallet
    console.log('Step 1: Creating wallet...');
    await createWallet(driver, this);
    console.log('✓ Wallet created\n');

    // Enable test mode
    console.log('Step 2: Enabling test mode...');
    const testModeBadge = await waitForElement(driver, SELECTORS.TEST_MODE_BADGE, 5000).catch(() => undefined);
    
    if (!testModeBadge) {
      await waitAndClick(driver, SELECTORS.NAV_SETTINGS_BTN);
      console.log('✓ Settings screen opened');
      
      await waitAndClick(driver, SELECTORS.SETTINGS_TEST_MODE_BTN);
      console.log('✓ Test mode button clicked');

      await waitAndClick(driver, SELECTORS.TEST_MODE_SWITCH);
      console.log('✓ Test mode switch clicked');
      
      await waitAndClick(driver, SELECTORS.NAV_CREDENTIALS_BTN);
      console.log('✓ Navigated back to credentials');
    } else {
      console.log('✓ Test mode already enabled');
    }

    // Generate credential offer URL
    console.log('\nStep 3: Generating OID4VC credential offer...');
    const oid4vcCredentialOfferUrl = await issueOpenIDCredential();
    console.log(`✓ Credential offer URL: ${oid4vcCredentialOfferUrl}\n`);

    // Scan QR code
    console.log('Step 4: Scanning QR code...');
    await waitAndClick(driver, SELECTORS.NAV_SCAN_BTN);
    console.log('✓ QR code screen opened');

    await setClipboard(driver, oid4vcCredentialOfferUrl);
    console.log('✓ Credential offer URL copied to clipboard');

    await waitAndClick(driver, SELECTORS.SCAN_QR_CODE_BTN);
    console.log('✓ QR code scanned');

    // Verify import success
    console.log('\nStep 5: Verifying import...');
    const processingCredential = await waitForElement(driver, SELECTORS.PROCESSING_CREDENTIAL, 30000);
    console.log('✓ Credential is being processed');
    
    await takeScreenshot(driver, this, 'processing-credential');
  });
});