// Test constants and configuration
module.exports = {
  // Timeouts
  TIMEOUTS: {
    APP_LAUNCH: 30000,
    ELEMENT_DISPLAY: 30000,
    SCREEN_TRANSITION: 5000,
    KEYPRESS_DELAY: 200,
    SESSION_COMMAND: 240000,
    ANDROID_INSTALL: 90000,
  },

  // Test data
  TEST_DATA: {
    DEFAULT_PASSCODE: '111111', // 6 digits
  },

  // Element selectors
  SELECTORS: {
    CREATE_WALLET_BTN: '~CreateWalletBtn',
    SETUP_PASSCODE_BTN: '~CreateWalletBtn', // TODO: rename in app
    SETUP_PASSCODE_SCREEN: '~SetupPasscodeScreen',
    CREDENTIALS_SCREEN: '~CredentialsScreen',
    KEYBOARD_NUMBER_1: '~keyboardNumber1',

    // TODO: replace with proper testId
    NAV_CREDENTIALS_BTN: '//android.widget.TextView[@text="Credentials"]',
    NAV_SCAN_BTN: '//android.widget.TextView[@text="Scan"]',
    NAV_SETTINGS_BTN: '//android.widget.TextView[@text="Settings"]',
    SETTINGS_TEST_MODE_BTN: '//android.widget.TextView[@text="Test mode"]',
    TEST_MODE_SWITCH: '//android.widget.Switch',
    TEST_MODE_BADGE: '//android.widget.TextView[@text="Test mode"]',
    PROCESSING_CREDENTIAL: '//android.widget.TextView[@text="Processing credential..."]',
    CREDENTIAL_RECEIVED: '//android.widget.TextView[@text="Credential received"]',
    CREDENTIAL_TYPE_CITY_RESIDENT: '//android.widget.TextView[@text="City Resident"]',
    CREDENTIAL_TYPE_CREDIT_SCORE: '//android.widget.TextView[@text="Credit Score"]',

    CREDENTIAL_IS_VALID: '//android.widget.TextView[@text="Valid"]',

    // Password modal selectors
    PASSWORD_MODAL_TITLE: '//android.widget.TextView[@text="Enter password"]',
    PASSWORD_INPUT_FIELD: '//android.widget.EditText[@hint="Password" or @text="Password"]',
    PASSWORD_MODAL_OK_BTN: '//android.widget.TextView[@text="OK"]',

    // import credential modal
    IMPORT_CREDENTIAL_MODAL: '//android.widget.TextView[@text="Import Credential"]',
    IMPORT_CREDENTIAL_OK_BTN: '//android.widget.TextView[@text="OK"]',

    SCAN_QR_CODE_BTN: '~pasteToScan',
    IMPORT_SUCCESS: '~ImportSuccess',
  },
};
