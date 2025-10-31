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
    QR_CODE_BTN: '~QRCodeBtn',
    SCAN_QR_CODE_BTN: '~ScanQRCodeBtn',
    IMPORT_SUCCESS: '~ImportSuccess',
  },
};
