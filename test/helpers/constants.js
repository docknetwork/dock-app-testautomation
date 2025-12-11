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
    SETUP_PASSCODE_SCREEN: '//android.widget.TextView[@text="Create your passcode"]',
    CREDENTIALS_SCREEN: '~CredentialsScreen',
    KEYBOARD_NUMBER_1: '~keyboardNumber1',
    
    // TODO: replace with proper testId
    NAV_CREDENTIALS_BTN: '//android.widget.TextView[@text="Credentials"]',
    NAV_SCAN_BTN: '//android.widget.TextView[@text="Scan"]',
    NAV_DID_BTN: '//android.widget.TextView[@text="DIDs"]',
    NAV_SETTINGS_BTN: '//android.widget.TextView[@text="Settings"]',
    SETTINGS_TEST_MODE_BTN: '//android.widget.TextView[@text="Test mode"]',
    TEST_MODE_SWITCH: '//android.widget.Switch',
    TEST_MODE_BADGE: '//android.widget.TextView[@text="Test mode"]',
    PROCESSING_CREDENTIAL: '~LoadingScreen',
    CREDENTIAL_RECEIVED: '~credential-received',
    CREDENTIAL_TYPE_CITY_RESIDENT: '//android.widget.TextView[@text="City Resident"]',
    CREDENTIAL_FOR_SURE_BIOMETRIC: '//android.widget.TextView[@text="For Sur Biometric"]',
    CREDENTIAL_QUOTIENT_BANK_IDENTITY: '//android.widget.TextView[@text="Quotient Bank Identity"]',
    CREDENTIAL_EQUALNET_CREDIT_SCORE: '//android.widget.TextView[@text="Equi Net Credit Score"]',
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

    // credential offer screen
    SUBMIT_CLAIMS_BTN: '~submitClaims',

    // verification
    VERIFICATION_PURPOSE: '//android.widget.TextView[contains(@text, "Wallet Smoke Tests")]',
    VERIFICATION_CHECKBOX: '//android.widget.CheckBox',
    VERIFICATION_CONTINUE_BTN: '//android.widget.TextView[@text="Continue"]',
    VERIFICATION_SHARE_BTN: '//android.widget.TextView[@text="Share"]',
    VERIFICATION_SELECT_DETAILS_TO_SHARE: '//android.widget.TextView[@text="Select which details to share"]',
    VERIFICATION_WAIT_FOR_VERIFIER: '//android.widget.TextView[@text="Waiting for Verification"]',
    VERIFICATION_SUCCESS: '//android.widget.TextView[@text="Verification Successful"]',
    VERIFICATION_SELECT_ALL: '//android.widget.TextView[@text="Select all"]',
  },
};
