const { setClipboard } = require("./driver");
const { waitAndClick } = require("./waiters");
const { SELECTORS } = require("./constants");

/**
 * Scan QR Code
 * This is using the wallet ability to read QR Code data from the clipboard
 * @param {*} driver 
 * @param {*} data 
 */
async function scanQRCode(driver, data) {
  console.log("Scanning QR code...");
  await waitAndClick(driver, SELECTORS.NAV_SCAN_BTN);
  console.log("✓ QR code screen opened");

  await setClipboard(driver, data);
  console.log("✓ QR Code data copied to clipboard");

  await waitAndClick(driver, SELECTORS.SCAN_QR_CODE_BTN);
  console.log("✓ QR code scanned");
}

module.exports = {
  scanQRCode,
};