const {
  initializeDriver,
  closeDriver,
  NO_RESET,
} = require("../helpers/driver");
const { takeScreenshot } = require("../helpers/screenshot");
const { createWallet } = require("../helpers/wallet-setup");
const { waitForElement, waitAndClick, unlockWallet } = require("../helpers/waiters");
const { SELECTORS } = require("../helpers/constants");
const {
  issueCredential,
  issueOpenIDCredential,
  CREDENTIAL_ALGORITHMS,
  createProofRequest,
} = require("../helpers/credentials");
const { selectWalletNetwork } = require("../helpers/network-switch");
const { scanQRCode } = require("../helpers/qr-code");

let driver;

describe("Feature: Verification BBS", function () {
  this.timeout(300000); // 5 minutes timeout

  before(async function () {
    console.log("\n========================================");
    console.log("FEATURE: Verification BBS");
    console.log("========================================\n");
    driver = await initializeDriver({ noReset: NO_RESET, fullReset: false });
  });

  after(async function () {
    await closeDriver(driver);
  });

  it("should successfully verify a bbs credential", async function () {
    if (!NO_RESET) {
      console.log("Creating wallet...");
      await createWallet(driver, this);
      console.log("✓ Wallet created\n");

      await selectWalletNetwork("testnet", driver);

      // Generate credential offer URL
      console.log("Generating OID4VC credential offer...");
      const oid4vcCredentialOfferUrl = await issueOpenIDCredential(
        CREDENTIAL_ALGORITHMS.DOCKBBS
      );
      console.log(`✓ Credential offer URL: ${oid4vcCredentialOfferUrl}\n`);
  
      await scanQRCode(driver, oid4vcCredentialOfferUrl);
  
      // check if credential is in the credentials screen
      console.log("Verifying credential...");
      await waitForElement(
        driver,
        SELECTORS.CREDENTIAL_TYPE_CITY_RESIDENT,
        30000
      );
  
      await takeScreenshot(driver, this, "credential-received");
    } else {
      unlockWallet(driver);
    }

    const proofRequestUrl = await createProofRequest();
    console.log(`✓ Proof request URL: ${proofRequestUrl}\n`);

    await scanQRCode(driver, proofRequestUrl);

    await waitForElement(driver, SELECTORS.VERIFICATION_PURPOSE, 30000);

    await takeScreenshot(driver, this, "verification-started");

    await waitAndClick(driver, SELECTORS.APPROVE_AND_SHARE);

    await takeScreenshot(driver, this, "verification-select-details-to-share");

    // wait for verification message
    await waitForElement(
      driver,
      SELECTORS.VERIFICATION_WAIT_FOR_VERIFIER,
      30000
    );

    await takeScreenshot(driver, this, "verification-waiting-for-verifier");

    // wait for verification success
    await waitForElement(
      driver,
      SELECTORS.VERIFICATION_SUCCESS,
      30000
    );

    await takeScreenshot(driver, this, "verification-success");
  });
});
