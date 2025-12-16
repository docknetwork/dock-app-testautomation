/**
 * Integration test for bank-demo.truvera.io and Android Wallet
 *
 * This test demonstrates the complete flow:
 * 1. Open bank account on bank-demo.truvera.io using Playwright
 * 2. Extract QR code from the success page
 * 3. Scan QR code in Android wallet using Appium
 */

const { test, expect } = require('@playwright/test');
const { BankDemoPage } = require('./helpers/bank-demo');
const { waitAndExtractQRCode } = require('./helpers/qr-extractor');
const { initializeDriver, closeDriver } = require('../helpers/driver');
const { scanQRCode } = require('../helpers/qr-code');
const { selectWalletNetwork } = require('../helpers/network-switch');
const { createWallet } = require('../helpers/wallet-setup');
const { SELECTORS } = require('../helpers/constants');
const { waitAndClick, waitForElement } = require('../helpers/waiters');
const { takeScreenshot } = require('../helpers/screenshot');

test.describe('Bank Demo + Wallet Integration', () => {
  let mobileDriver;

  test.beforeAll(async () => {
    // Initialize mobile driver once for all tests
    console.log('\n=== Starting Mobile Driver ===');
    mobileDriver = await initializeDriver({ app: 'quotient-wallet.apk' });
  });

  test.afterAll(async () => {
    // Close mobile driver after all tests
    if (mobileDriver) {
      console.log('\n=== Closing Mobile Driver ===');
      await closeDriver(mobileDriver);
    }
  });

  test('should open bank account and import credential to wallet', async ({ page }) => {
    console.log('\n=== Starting Integration Test ===');

    // PART 1: Bank Demo Flow (Playwright)
    console.log('\n--- PART 1: Bank Demo Flow ---');
    const bankDemo = new BankDemoPage(page);

    // Navigate to bank demo
    await bankDemo.navigate();

    // Complete the bank account opening flow
    await bankDemo.openBankAccount();

    // Take screenshot of success page
    const screenshotPath = await bankDemo.screenshot('bank-account-opened');
    console.log('✓ Success page screenshot saved');

    // Wait for QR code to be visible
    await bankDemo.waitForQRCode();

    // Extract QR code from the page
    console.log('\n--- Extracting QR Code ---');
    const qrCodeData = await waitAndExtractQRCode(page, 5, 2000);
    console.log('✓ QR code extracted successfully');
    console.log(`  QR Code Data: ${qrCodeData.substring(0, 100)}...`);

    // Verify QR code contains expected data
    expect(qrCodeData).toBeTruthy();
    expect(qrCodeData.length).toBeGreaterThan(0);

    // PART 2: Wallet Flow (Appium)
    console.log('\n--- PART 2: Wallet Flow ---');

    // create wallet 
    await createWallet(mobileDriver);
    await selectWalletNetwork("testnet", mobileDriver);

    // Scan QR code in the wallet
    await scanQRCode(mobileDriver, qrCodeData);
    console.log('✓ QR code scanned in wallet');

    // click continue button
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_CONTINUE_BTN);

    // select credential and click continue
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_CHECKBOX);
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_CONTINUE_BTN);

    // select attributes to share
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_SELECT_ALL, { screenshotName: "verification-select-all" });
    // click continue 
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_CONTINUE_BTN, { screenshotName: "verification-continue" });
    // click share
    await waitAndClick(mobileDriver, SELECTORS.VERIFICATION_SHARE_BTN, { screenshotName: "verification-share" });

    // // wait for verification message
    await waitForElement(
      mobileDriver,
      SELECTORS.VERIFICATION_WAIT_FOR_VERIFIER,
      30000
    );

    await takeScreenshot(mobileDriver, this, "verification-waiting-for-verifier");

    // wait for verification success
    await waitForElement(
      mobileDriver,
      SELECTORS.VERIFICATION_SUCCESS,
      30000
    );
    // Wait for credential to be processed
    await mobileDriver.pause(3000);

    // navigate to credentials screen
    await waitAndClick(mobileDriver, SELECTORS.NAV_CREDENTIALS_BTN);

    // wait for credential to be visible
    await waitForElement(
      mobileDriver,
      SELECTORS.CREDENTIAL_FOR_SURE_BIOMETRIC,
      30000
    );

    // Wait for 
    await waitForElement(
      mobileDriver,
      SELECTORS.CREDENTIAL_QUOTIENT_BANK_IDENTITY,
      30000
    );

    await waitForElement(
      mobileDriver,
      SELECTORS.CREDENTIAL_EQUALNET_CREDIT_SCORE,
      30000
    );

    // Take screenshot of wallet after scanning
    await mobileDriver.saveScreenshot('test-reports/screenshots/wallet-after-scan.png');
    console.log('✓ Wallet screenshot saved');

    console.log('\n=== Integration Test Completed Successfully ===');
  });
});
