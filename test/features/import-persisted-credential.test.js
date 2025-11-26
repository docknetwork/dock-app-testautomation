const { initializeDriver, closeDriver } = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement, waitAndClick } = require("../helpers/waiters");
const { SELECTORS } = require("../helpers/constants");
const { selectWalletNetwork } = require("../helpers/network-switch");
const { scanQRCode } = require("../helpers/qr-code");
const { enterCredentialPassword } = require("../helpers/credential-password");
const testCredentials = require("../../test-credentials.json");

let driver;

describe("Feature: Import Persisted Credential", function () {
  this.timeout(300000); // 5 minutes timeout

  before(async function () {
    console.log("\n========================================");
    console.log("FEATURE: Import Persisted Credential");
    console.log("========================================\n");
    driver = await initializeDriver({ noReset: false, fullReset: false });

    // Create wallet
    console.log("Creating wallet...");
    await createWallet(driver, this);
    console.log("✓ Wallet created\n");
  });

  after(async function () {
    await closeDriver(driver);
  });

  it("should successfully import a persisted credential using credential password", async function () {
    await selectWalletNetwork("testnet", driver);

    const credentialURL = testCredentials.testnet.bbs.url;
    const credentialPassword = testCredentials.testnet.bbs.password;

    await scanQRCode(driver, credentialURL);

    // Enter credential password
    console.log("Entering credential password...");
    await enterCredentialPassword(driver, credentialPassword, this);
    console.log("✓ Credential password entered\n");

    await waitForElement(driver, SELECTORS.IMPORT_CREDENTIAL_MODAL, 30000);
    await waitAndClick(driver, SELECTORS.IMPORT_CREDENTIAL_OK_BTN, 30000);
    console.log("✓ Credential received");

    await takeScreenshot(driver, this, "credential-received");

    await waitForElement(driver, SELECTORS.CREDENTIAL_TYPE_CREDIT_SCORE, 30000);
    console.log("✓ Credential type is Credit Score");
    await waitForElement(driver, SELECTORS.CREDENTIAL_IS_VALID, 30000);
    console.log("✓ Credential is valid");
  });
});
