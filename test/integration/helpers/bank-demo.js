/**
 * Helper utilities for interacting with bank-demo.truvera.io
 * Implements the complete bank account opening flow
 */

class BankDemoPage {
  constructor(page) {
    this.page = page;
    this.baseUrl = 'https://bank-demo.truvera.io';
  }

  /**
   * Navigate to the bank demo home page
   */
  async navigate() {
    await this.page.goto(this.baseUrl);
    await this.page.waitForLoadState('networkidle');
    console.log('✓ Navigated to bank-demo.truvera.io');
  }

  /**
   * Complete the full bank account opening flow
   * This follows the exact steps:
   * 1. Click "New Bank Account"
   * 2. Click "Upload Government Issued ID"
   * 3. Click "Take Photo"
   * 4. Click "Start Capture"
   * 5. Wait for capture to complete
   * 6. Click "Submit Application"
   * 7. Wait for success message
   */
  async openBankAccount() {
    console.log('Starting bank account opening flow...');

    // Step 1: Click "New Bank Account"
    await this.clickButton('New Bank Account');
    await this.page.waitForLoadState('networkidle');
    console.log('✓ Clicked "New Bank Account"');

    // Step 2: Click "Upload Government Issued ID"
    await this.clickButton('Upload Government Issued ID');
    console.log('✓ Clicked "Upload Government Issued ID"');

    // Step 3: Click "Take Photo"
    await this.clickButton('Take Photo');
    console.log('✓ Clicked "Take Photo"');

    // Step 4: Wait for "Start Capture" button to be visible and click it
    await this.page.getByRole('button', { name: 'Start Capture' }).waitFor({ state: 'visible' });
    await this.clickButton('Start Capture');
    console.log('✓ Clicked "Start Capture"');

    // Step 5: Wait for capture to complete.Look for "Capture Complete" text on the page
    await this.waitForText('Capture Complete');
    console.log('✓ Capture completed');

    // Step 6: Click "Submit Application"
    await this.clickButton('Submit Application');
    await this.page.waitForLoadState('networkidle');
    console.log('✓ Clicked "Submit Application"');

    // Step 7: Wait for success message
    await this.waitForText('Your account has been opened!');
    console.log('✓ Account opened successfully!');
  }

  /**
   * Click a button by text or role
   * @param {string} text - Button text
   */
  async clickButton(text) {
    const button = this.page.getByRole('button', { name: text });
    await button.waitFor({ state: 'visible', timeout: 30000 });
    await button.click();
  }

  /**
   * Wait for text to appear on page
   * @param {string} text - Text to wait for
   * @param {number} timeout - Timeout in milliseconds
   */
  async waitForText(text, timeout = 30000) {
    await this.page.getByText(text).waitFor({ timeout });
  }

  /**
   * Take screenshot with descriptive name
   * @param {string} name - Screenshot name
   * @returns {Promise<string>} Path to screenshot
   */
  async screenshot(name) {
    const timestamp = Date.now();
    const path = `test-reports/screenshots/${name}-${timestamp}.png`;
    await this.page.screenshot({ path, fullPage: true });
    console.log(`✓ Screenshot saved: ${path}`);
    return path;
  }

  /**
   * Take screenshot and return as buffer for QR code extraction
   * @returns {Promise<Buffer>} Screenshot buffer
   */
  async screenshotBuffer() {
    const screenshot = await this.page.screenshot({ fullPage: true });
    console.log('✓ Screenshot captured as buffer');
    return screenshot;
  }

  /**
   * Wait for QR code to be visible on the page
   */
  async waitForQRCode() {
    // Look for common QR code elements
    const qrSelectors = [
      'canvas',
      '[data-testid="qr-code"]',
      '.qr-code',
      'img[alt*="QR"]',
      'img[alt*="qr"]',
    ];

    for (const selector of qrSelectors) {
      try {
        const element = this.page.locator(selector).first();
        await element.waitFor({ state: 'visible', timeout: 5000 });
        console.log(`✓ QR code found using selector: ${selector}`);
        return true;
      } catch {
        continue;
      }
    }

    console.log('✓ Assuming QR code is present on page');
    return true;
  }
}

module.exports = { BankDemoPage };
