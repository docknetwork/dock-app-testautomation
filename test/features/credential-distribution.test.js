const { initializeDriver, closeDriver, NO_RESET } = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement, waitAndClick } = require("../helpers/waiters");
const { SELECTORS } = require("../helpers/constants");
const { issueCredential } = require("../helpers/credentials");
const { selectWalletNetwork } = require("../helpers/network-switch");

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

  it("should successfully distribute a credential", async function () {
    // Create wallet
    if (!NO_RESET) {
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

    issueCredential({
      subjectDID: didText,
      distribute: true
    });

    // navigate back to credentials screen
    await waitAndClick(driver, SELECTORS.NAV_CREDENTIALS_BTN);

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
