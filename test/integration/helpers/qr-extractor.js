/**
 * QR Code extraction utility for Playwright screenshots
 * Extracts QR code data from screenshots taken by Playwright
 */

const jsQR = require('jsqr');
const sharp = require('sharp');

/**
 * Extract QR code data from a screenshot buffer or file
 * @param {Buffer|string} screenshotSource - Screenshot buffer or file path
 * @returns {Promise<string>} QR code data
 */
async function extractQRCode(screenshotSource) {
  console.log('Extracting QR code from screenshot...');

  try {
    // Load image using sharp
    let imageBuffer;
    if (Buffer.isBuffer(screenshotSource)) {
      imageBuffer = screenshotSource;
    } else {
      imageBuffer = await sharp(screenshotSource).toBuffer();
    }

    // Convert to raw pixel data
    const { data, info } = await sharp(imageBuffer)
      .ensureAlpha()
      .raw()
      .toBuffer({ resolveWithObject: true });

    // Use jsQR to decode
    const code = jsQR(
      new Uint8ClampedArray(data),
      info.width,
      info.height
    );

    if (code && code.data) {
      console.log('✓ QR code extracted successfully');
      console.log(`  Data preview: ${code.data.substring(0, 60)}...`);
      return code.data;
    }

    throw new Error('No QR code found in screenshot');
  } catch (error) {
    console.error('Failed to extract QR code:', error.message);
    throw error;
  }
}

/**
 * Extract QR code from Playwright page screenshot
 * @param {import('@playwright/test').Page} page - Playwright page
 * @returns {Promise<string>} QR code data
 */
async function extractQRCodeFromPage(page) {
  console.log('Taking screenshot to extract QR code...');
  const screenshot = await page.screenshot({ fullPage: true });
  return await extractQRCode(screenshot);
}

/**
 * Wait for QR code to appear and extract it
 * @param {import('@playwright/test').Page} page - Playwright page
 * @param {number} maxAttempts - Maximum number of attempts
 * @param {number} delayMs - Delay between attempts in milliseconds
 * @returns {Promise<string>} QR code data
 */
async function waitAndExtractQRCode(page, maxAttempts = 5, delayMs = 2000) {
  console.log(`Waiting for QR code to appear (max ${maxAttempts} attempts)...`);

  for (let attempt = 1; attempt <= maxAttempts; attempt++) {
    try {
      console.log(`  Attempt ${attempt}/${maxAttempts}`);
      const qrData = await extractQRCodeFromPage(page);
      return qrData;
    } catch (error) {
      if (attempt === maxAttempts) {
        throw new Error(`Failed to extract QR code after ${maxAttempts} attempts: ${error.message}`);
      }
      console.log(`  QR code not found, waiting ${delayMs}ms before retry...`);
      await page.waitForTimeout(delayMs);
    }
  }
}

module.exports = {
  extractQRCode,
  extractQRCodeFromPage,
  waitAndExtractQRCode,
};
