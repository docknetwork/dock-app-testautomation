/**
 * Integration test for bank-demo.truvera.io and Android Wallet
 *
 * This test demonstrates the complete end-to-end flow:
 *
 * PART 1 - Open Bank Account (Web):
 *   1. Navigate to bank-demo.truvera.io
 *   2. Complete bank account opening flow
 *   3. Extract credentials QR code from success page
 *
 * PART 2 - Setup Wallet & Receive Credentials (Mobile):
 *   4. Create wallet and select testnet
 *   5. Scan QR code and complete verification
 *   6. Verify credentials appear in wallet (Biometric, Identity, Credit Score)
 *
 * PART 3 - Obtain Auto Loan (Web):
 *   7. Navigate to auto loan flow
 *   8. Extract verification request QR code
 *
 * PART 4 - Complete Auto Loan Verification (Mobile):
 *   9. Scan auto loan QR code
 *   10. Select credentials to share
 *   11. Complete verification flow
 */

const { test, expect } = require('@playwright/test');
const { BankDemoPage } = require('./helpers/bank-demo');
const { waitAndExtractQRCode } = require('./helpers/qr-extractor');
const { initializeDriver, closeDriver } = require('../helpers/driver');
const { selectWalletNetwork } = require('../helpers/network-switch');
const { createWallet } = require('../helpers/wallet-setup');
const { SELECTORS } = require('../helpers/constants');
const {
  completeVerificationFlow,
  verifyCredentialsInWallet,
} = require('./helpers/wallet-actions');

test.describe('Bank Demo + Wallet Integration', () => {
  let mobileDriver;

  test.beforeAll(async () => {
    // Initialize mobile driver once for all tests
    console.log('\n=== Starting Mobile Driver ===');
    mobileDriver = await initializeDriver({ app: 'quotient-wallet.apk', noReset: false });
  });

  test.afterAll(async () => {
    // Close mobile driver after all tests
    if (mobileDriver) {
      console.log('\n=== Closing Mobile Driver ===');
      await closeDriver(mobileDriver);
    }
  });

  test('should open bank account and complete auto loan verification', async ({ page }) => {
    console.log('\n=== Starting Bank Demo Integration Test ===');

    // PART 1: Open Bank Account (Playwright)
    console.log('\n--- PART 1: Opening Bank Account ---');
    const bankDemo = new BankDemoPage(page);

    // Navigate to bank demo
    await bankDemo.navigate();

    // // Complete the bank account opening flow
    await bankDemo.openBankAccount();

    // // Take screenshot of success page
    await bankDemo.screenshot('bank-account-opened');
    console.log('✓ Bank account opened successfully');

    // // Wait for QR code to be visible
    await bankDemo.waitForQRCode();

    // Extract QR code from the page
    console.log('\n--- Extracting Bank Account QR Code ---');
    const bankAccountQRCode = await waitAndExtractQRCode(page, 5, 2000);
    console.log('✓ Bank account QR code extracted');
    console.log(`  QR Code Data: ${bankAccountQRCode.substring(0, 100)}...`);

    // // Verify QR code contains expected data
    expect(bankAccountQRCode).toBeTruthy();
    expect(bankAccountQRCode.length).toBeGreaterThan(0);

    // PART 2: Setup Wallet (Appium)
    console.log('\n--- PART 2: Setting Up Wallet ---');

    // Create wallet and select network
    await createWallet(mobileDriver);
    await selectWalletNetwork("testnet", mobileDriver);
    console.log('✓ Wallet created and network selected');

    // Complete verification flow for bank account
    await completeVerificationFlow(
      mobileDriver,
      bankAccountQRCode,
      this,
      { screenshotPrefix: 'bank-account' }
    );

    // Verify credentials were received
    await verifyCredentialsInWallet(
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
      SELECTORS.CREDENTIAL_FOR_SUR_BIOMETRIC,
      30000
    );

    console.log('✓ Bank account credentials verified in wallet');

    // PART 3: Obtain Auto Loan (Playwright)
    console.log('\n--- PART 3: Obtaining Auto Loan ---');

    // Navigate to auto loan flow
    await bankDemo.obtainAutoLoan();
    console.log('✓ Started auto loan flow');

    // Wait for QR code
    await bankDemo.waitForQRCode();

    // Extract auto loan QR code
    console.log('\n--- Extracting Auto Loan QR Code ---');
    const autoLoanQRCode = await waitAndExtractQRCode(page, 5, 2000);
    console.log('✓ Auto loan QR code extracted');
    console.log(`  QR Code Data: ${autoLoanQRCode.substring(0, 100)}...`);

    // Verify QR code
    expect(autoLoanQRCode).toBeTruthy();
    expect(autoLoanQRCode.length).toBeGreaterThan(0);

    // PART 4: Complete Auto Loan Verification (Appium)
    console.log('\n--- PART 4: Completing Auto Loan Verification ---');

    // Complete verification flow for auto loan
    await completeVerificationFlow(
      mobileDriver,
      autoLoanQRCode,
      this,
      { screenshotPrefix: 'auto-loan' }
    );

    console.log('✓ Auto loan verification completed');

    // Take final screenshot
    await mobileDriver.saveScreenshot('test-reports/screenshots/final-state.png');
    console.log('✓ Final wallet screenshot saved');

    console.log('\n=== Bank Demo Integration Test Completed Successfully ===');
  });
});
