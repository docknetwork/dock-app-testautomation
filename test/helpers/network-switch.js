const { waitAndClick, waitForElement } = require("./waiters");
const { SELECTORS } = require("./constants");

/**
 * Navigate to test mode settings and tap test mode switch
 * Can be used to enable or disable test mode
 * @param {*} driver
 */
async function tapTestModeSwitch(driver) {
  await waitAndClick(driver, SELECTORS.NAV_SETTINGS_BTN);
  console.log("✓ Settings screen opened");

  await waitAndClick(driver, SELECTORS.SETTINGS_TEST_MODE_BTN);
  console.log("✓ Test mode button clicked");

  await waitAndClick(driver, SELECTORS.TEST_MODE_SWITCH);
  console.log("✓ Test mode switch clicked");

  await waitAndClick(driver, SELECTORS.NAV_CREDENTIALS_BTN);
  console.log("✓ Navigated back to credentials");
}

/**
 * Selects the wallet network
 *
 * @param {*} networkId mainnet or testnet
 * @param {*} driver
 */

async function selectWalletNetwork(networkId, driver) {
  console.log("Selecting wallet network...");

  const testModeBadge = await waitForElement(
    driver,
    SELECTORS.TEST_MODE_BADGE,
    5000
  ).catch(() => undefined);

  const isTestModeEnabled = !!testModeBadge;
  const shouldEnableTestMode = networkId === "testnet";

  if (isTestModeEnabled !== shouldEnableTestMode) {
    console.log("Switching to test mode...");
    await tapTestModeSwitch(driver);
    console.log("✓ Test mode enabled");
  } else {
    console.log("✓ Network already selected, skipping test mode switch");
  }
}

module.exports = {
  selectWalletNetwork,
};
