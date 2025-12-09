const { initializeDriver, closeDriver, NO_RESET } = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement, waitAndClick } = require("../helpers/waiters");
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
    driver = await initializeDriver({ noReset: NO_RESET, fullReset: false });
  });

  after(async function () {
    await closeDriver(driver);
  });

  it("should successfully request claims", async function () {
    if (!NO_RESET) {
      // Create wallet
      console.log("Creating wallet...");
      await createWallet(driver, this);
      console.log("✓ Wallet created\n");
    }

    await selectWalletNetwork("testnet", driver);

    // Generate credential offer URL
    console.log("Generating OID4VC credential offer...");

    // navigate to did screen
    await waitAndClick(driver, SELECTORS.NAV_DID_BTN);

    // find text starting with did:key:
    const did = await waitForElement(driver, '//android.widget.TextView[starts-with(@text, "did:key:")]', 30000);
    console.log("✓ DID found");

    const didText = await did.getText();
    console.log("✓ DID text:", didText);

    const qrUrl = await requestClaims();
    console.log("✓ QR URL:", qrUrl);

    await scanQRCode(driver, qrUrl);

    // wait for Import Credential modal to be visible
    await waitForElement(driver, SELECTORS.IMPORT_CREDENTIAL_MODAL, 30000);
    console.log("✓ Import Credential modal found");
    await waitAndClick(driver, SELECTORS.IMPORT_CREDENTIAL_OK_BTN, 30000);
    console.log("✓ Import Credential modal clicked");

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

    await takeScreenshot(driver, this, "credential-received");
  });
});
