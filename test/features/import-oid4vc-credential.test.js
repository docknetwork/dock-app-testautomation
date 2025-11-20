const { initializeDriver, closeDriver } = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement } = require("../helpers/waiters");
const { SELECTORS } = require("../helpers/constants");
const { issueOpenIDCredential } = require("../helpers/oid4vc");
const { selectWalletNetwork } = require("../helpers/network-switch");
const { scanQRCode } = require("../helpers/qr-code");

let driver;

describe("Feature: Import OID4VC Credential", function () {
  this.timeout(300000); // 5 minutes timeout

  before(async function () {
    console.log("\n========================================");
    console.log("FEATURE: Import OID4VC Credential");
    console.log("========================================\n");
    driver = await initializeDriver({ noReset: false, fullReset: false });
  });

  after(async function () {
    await closeDriver(driver);
  });

  it("should successfully import an OID4VC credential", async function () {
    // Create wallet
    console.log("Creating wallet...");
    await createWallet(driver, this);
    console.log("✓ Wallet created\n");

    await selectWalletNetwork("testnet", driver);

    // Generate credential offer URL
    console.log("Generating OID4VC credential offer...");
    const oid4vcCredentialOfferUrl = await issueOpenIDCredential();
    console.log(`✓ Credential offer URL: ${oid4vcCredentialOfferUrl}\n`);

    await scanQRCode(driver, oid4vcCredentialOfferUrl);

    // Verify import success
    console.log("Verifying import...");
    await waitForElement(driver, SELECTORS.PROCESSING_CREDENTIAL, 30000);
    console.log("✓ Credential is being processed");
    await waitForElement(driver, SELECTORS.CREDENTIAL_RECEIVED, 30000);
    console.log("✓ Credential received");

    await takeScreenshot(driver, this, "credential-received");

    // check if credential is in the credentials screen
    console.log("Verifying credential...");
    await waitForElement(
      driver,
      SELECTORS.CREDENTIAL_TYPE_CITY_RESIDENT,
      30000
    );
    console.log("✓ Credential type is City Resident");
    await waitForElement(driver, SELECTORS.CREDENTIAL_IS_VALID, 30000);
    console.log("✓ Credential is valid");
  });
});
