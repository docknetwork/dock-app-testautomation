/**
 * Bridge utility for coordinating Playwright (web) and Appium (mobile) interactions
 * This helper enables seamless integration testing between bank-demo and the Android wallet
 */

const { initializeDriver, closeDriver, setClipboard, getClipboard } = require('../../helpers/driver');

/**
 * Bridge class to manage both Playwright and Appium sessions
 */
class PlaywrightAppiumBridge {
  constructor(playwrightPage) {
    this.page = playwrightPage; // Playwright page
    this.mobileDriver = null;    // Appium driver
  }

  /**
   * Initialize the mobile driver
   * @param {Object} options - Driver initialization options
   */
  async initializeMobile(options = {}) {
    console.log('Initializing mobile driver for integration test...');
    this.mobileDriver = await initializeDriver(options);
    return this.mobileDriver;
  }

  /**
   * Close the mobile driver
   */
  async closeMobile() {
    if (this.mobileDriver) {
      await closeDriver(this.mobileDriver);
      this.mobileDriver = null;
    }
  }

  /**
   * Transfer data from web to mobile via clipboard
   * @param {string} data - Data to transfer
   */
  async transferToMobile(data) {
    console.log('Transferring data from web to mobile...');
    await setClipboard(this.mobileDriver, data);
    return data;
  }

  /**
   * Get data from mobile clipboard
   * @returns {Promise<string>} Clipboard content
   */
  async getFromMobile() {
    console.log('Getting data from mobile clipboard...');
    return await getClipboard(this.mobileDriver);
  }

  /**
   * Extract credential offer URL from bank-demo page
   * This is a common pattern for OID4VC flows
   * @returns {Promise<string>} Credential offer URL
   */
  async extractCredentialOfferUrl() {
    // Look for elements that typically contain credential offers
    // Adjust selectors based on actual bank-demo implementation
    const possibleSelectors = [
      '[data-testid="credential-offer-url"]',
      '[data-testid="qr-code-url"]',
      'input[type="text"][readonly]',
      '.credential-offer-url',
    ];

    for (const selector of possibleSelectors) {
      try {
        const element = await this.page.locator(selector).first();
        if (await element.isVisible({ timeout: 5000 })) {
          const url = await element.inputValue().catch(() => element.textContent());
          if (url && url.startsWith('openid-credential-offer://')) {
            console.log('✓ Credential offer URL found:', url.substring(0, 50) + '...');
            return url;
          }
        }
      } catch (e) {
        // Continue to next selector
      }
    }

    throw new Error('Could not find credential offer URL on page');
  }

  /**
   * Extract QR code data from the page
   * @returns {Promise<string>} QR code data
   */
  async extractQRCodeData() {
    // Check for QR code data attribute or canvas
    try {
      // Try to get data from data attribute
      const qrElement = await this.page.locator('[data-qr-value]').first();
      if (await qrElement.isVisible({ timeout: 5000 })) {
        return await qrElement.getAttribute('data-qr-value');
      }
    } catch (e) {
      console.log('QR code data attribute not found, checking other methods...');
    }

    // If no direct data attribute, try to find the URL displayed near QR code
    return await this.extractCredentialOfferUrl();
  }

  /**
   * Wait for a specific condition on the web page
   * @param {Function} condition - Condition function
   * @param {number} timeout - Timeout in milliseconds
   */
  async waitForWebCondition(condition, timeout = 30000) {
    await this.page.waitForFunction(condition, { timeout });
  }

  /**
   * Wait for a specific condition on the mobile app
   * @param {Function} condition - Condition function
   * @param {number} timeout - Timeout in milliseconds
   */
  async waitForMobileCondition(condition, timeout = 30000) {
    const startTime = Date.now();
    while (Date.now() - startTime < timeout) {
      if (await condition(this.mobileDriver)) {
        return true;
      }
      await this.mobileDriver.pause(1000);
    }
    throw new Error('Mobile condition timeout');
  }

  /**
   * Synchronize state between web and mobile
   * Useful for waiting for both platforms to be ready
   */
  async synchronize() {
    await Promise.all([
      this.page.waitForLoadState('networkidle'),
      this.mobileDriver.pause(2000) // Give mobile app time to settle
    ]);
  }

  /**
   * Take synchronized screenshots of both web and mobile
   * @param {string} prefix - Filename prefix
   */
  async captureScreenshots(prefix) {
    const timestamp = Date.now();
    await Promise.all([
      this.page.screenshot({ path: `test-reports/screenshots/${prefix}-web-${timestamp}.png` }),
      this.mobileDriver.saveScreenshot(`test-reports/screenshots/${prefix}-mobile-${timestamp}.png`)
    ]);
    console.log(`✓ Screenshots captured: ${prefix}`);
  }
}

module.exports = { PlaywrightAppiumBridge };
