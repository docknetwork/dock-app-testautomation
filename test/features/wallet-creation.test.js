const { initializeDriver, closeDriver } = require('../helpers/driver');
const { captureTestScreenshot, takeScreenshot } = require('../helpers/screenshot');
const { waitForElement, waitAndClick, waitForTransition, enterPasscode } = require('../helpers/waiters');
const { SELECTORS, TEST_DATA, TIMEOUTS } = require('../helpers/constants');

let driver;

describe('Feature: Wallet Creation', function() {
  this.timeout(300000); // 5 minutes timeout

  before(async function() {
    console.log('\n========================================');
    console.log('FEATURE: Wallet Creation');
    console.log('========================================\n');
    driver = await initializeDriver({ noReset: false, fullReset: false });
  });

  after(async function() {
    await closeDriver(driver);
  });

  afterEach(async function() {
    await captureTestScreenshot(driver, this);
  });

  it('should display Create Wallet button on app launch', async function() {
    console.log('Waiting for "Create a new Wallet" button...');

    const button = await waitForElement(driver, SELECTORS.CREATE_WALLET_BTN, TIMEOUTS.APP_LAUNCH);
    console.log('✓ "Create a new Wallet" button found');

    await takeScreenshot(driver, this, 'initial-screen');
  });

  it('should navigate to setup screen when Create Wallet is clicked', async function() {
    await waitAndClick(driver, SELECTORS.CREATE_WALLET_BTN);
    console.log('✓ "Create a new Wallet" button clicked');

    // Wait for next screen
    await waitForTransition(driver, 2000);
  });

  it('should display Setup Passcode button', async function() {
    // button has testID "SetupPasscodeBtn"
    // TODO: rename testID to SetupPasscodeBtn in app
    const setupPasscodeButton = await waitForElement(
      driver,
      SELECTORS.SETUP_PASSCODE_BTN,
    );
    console.log('✓ "Setup passcode" button found');
  });

  it('should navigate to passcode screen when Setup Passcode is clicked', async function() {
    await waitAndClick(driver, SELECTORS.SETUP_PASSCODE_BTN);
    console.log('✓ "Setup passcode" button clicked');

    // wait for SetupPasscodeScreen
    const createPasscodeScreen = await waitForElement(
      driver,
      SELECTORS.SETUP_PASSCODE_SCREEN,
    );
    console.log('✓ Create passcode screen is visible');
  });

  it('should enter passcode successfully', async function() {
    await enterPasscode(driver, TEST_DATA.DEFAULT_PASSCODE);
    await waitForTransition(driver);
    console.log('✓ Passcode entered');
  });

  it('should confirm passcode successfully', async function() {
    console.log('Wait transition to password confirmation');
    await enterPasscode(driver, TEST_DATA.DEFAULT_PASSCODE);
    console.log('✓ Passcode confirmed');
  });

  it('should navigate to credentials screen after passcode setup', async function() {
    console.log('Wait transition to credentials screen');

    // Assert that CredentialsScreen is visible
    const credentialScreen = await waitForElement(
      driver,
      SELECTORS.CREDENTIALS_SCREEN,
    );
    console.log('✓ Credential screen is visible');

    // Take final screenshot
    await takeScreenshot(driver, this, 'credentials-screen-final');
    console.log('✓ Wallet creation completed successfully!');
  });
});
