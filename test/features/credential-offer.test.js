const { initializeDriver, closeDriver } = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement } = require("../helpers/waiters");
const { SELECTORS } = require("../helpers/constants");
const { selectWalletNetwork } = require("../helpers/network-switch");
const { scanQRCode } = require("../helpers/qr-code");
const { requestClaims } = require("../helpers/credentials");

let driver;

describe("Feature: Credential Distribution", function () {
  this.timeout(300000); // 5 minutes timeout

  before(async function () {
    console.log("\n========================================");
    console.log("FEATURE: Credential Distribution");
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

    // navigate to did screen
    await waitAndClick(driver, SELECTORS.NAV_DID_BTN);

    // find text starting with did:key:
    const did = await waitForElement(driver, '//android.widget.TextView[@text="did:key:"]', 30000);
    console.log("✓ DID found");

    const didText = await did.getText();
    console.log("✓ DID text:", didText);

    const qrUrl = await requestClaims();
    console.log("✓ QR URL:", qrUrl);

    await scanQRCode(driver, qrUrl);

    // Wait for "Submit Claims" button to be visible
    await waitForElement(driver, SELECTORS.SUBMIT_CLAIMS_BTN, 30000);
    console.log("✓ Submit Claims button found");
    await waitAndClick(driver, SELECTORS.SUBMIT_CLAIMS_BTN, 30000);
    console.log("✓ Submit Claims button clicked");

    // wait for Import Credential modal to be visible
    await waitForElement(driver, SELECTORS.IMPORT_CREDENTIAL_MODAL, 30000);
    console.log("✓ Import Credential modal found");
    await waitAndClick(driver, SELECTORS.IMPORT_CREDENTIAL_OK_BTN, 30000);
    console.log("✓ Import Credential modal clicked");

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
