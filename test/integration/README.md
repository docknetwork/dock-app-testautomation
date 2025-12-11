# Integration Tests - Bank Demo + Android Wallet

This directory contains integration tests that combine Playwright (for web automation) and Appium (for mobile automation) to test the complete flow between bank-demo.truvera.io and the Truvera Android Wallet.

## Overview

The integration tests demonstrate end-to-end flows:

1. **Web Flow (Playwright)**: Open bank account on bank-demo.truvera.io
2. **QR Code Extraction**: Extract credential offer QR code from the web page
3. **Mobile Flow (Appium)**: Scan QR code in Android wallet and import credential

## Project Structure

```
test/integration/
├── README.md                                  # This file
├── bank-demo-wallet-integration.spec.js       # Main integration test
└── helpers/
    ├── bank-demo.js                          # Bank demo page object
    ├── qr-extractor.js                       # QR code extraction utility
    └── playwright-appium-bridge.js           # Bridge for coordinating both platforms
```

## Prerequisites

### Required Software

1. **Node.js** 22.x or higher
2. **Appium** 2.x with UiAutomator2 driver
3. **Android Emulator** or physical device
4. **Playwright** (installed via npm)

### Setup

All dependencies are already included in the project. If you need to reinstall:

```bash
npm install
```

## Running Integration Tests

### 1. Start Appium Server

In a separate terminal:

```bash
appium
```

The server should start on `http://localhost:4723`

### 2. Start Android Emulator

```bash
# List available emulators
emulator -list-avds

# Start emulator
emulator -avd <your_avd_name>
```

### 3. Run Integration Tests

```bash
# Run all integration tests (headless)
npm run test:integration

# Run with browser UI visible (headed mode)
npm run test:integration:headed

# Run in debug mode (step through test)
npm run test:integration:debug

# Run with Playwright UI mode
npm run test:integration:ui
```

## Test Flow

### Bank Account Opening Flow

The test follows these exact steps:

1. Navigate to `https://bank-demo.truvera.io`
2. Click "New Bank Account"
3. Wait for page to load
4. Click "Upload Government Issued ID"
5. Click "Take Photo"
6. Wait for "Start Capture" button to be visible
7. Click "Start Capture"
8. Wait 5 seconds for capture to complete
9. Click "Submit Application"
10. Wait for page with text "Your account has been opened!"
11. Take screenshot of the page
12. Extract QR code from the screenshot
13. Open Android wallet using Appium
14. Scan QR code using wallet's scan functionality

## Test Reports

Integration tests generate multiple report formats:

### HTML Report
```bash
# View HTML report
npm run test:integration:report
```

Location: `test-reports/playwright-html/index.html`

### JSON Report
Location: `test-reports/playwright-json/results.json`

### JUnit XML Report
Location: `test-reports/playwright-junit/results.xml`

### Screenshots and Videos
- Screenshots: `test-results/` and `test-reports/screenshots/`
- Videos: `test-results/` (only on failure)
- Traces: `test-results/` (only on failure)

## Helper Utilities

### BankDemoPage (`helpers/bank-demo.js`)

Page object for bank-demo.truvera.io:

```javascript
const { BankDemoPage } = require('./helpers/bank-demo');

// In your test
const bankDemo = new BankDemoPage(page);
await bankDemo.navigate();
await bankDemo.openBankAccount();
const screenshot = await bankDemo.screenshot('test-name');
```

### QR Code Extractor (`helpers/qr-extractor.js`)

Extracts QR code data from screenshots:

```javascript
const { waitAndExtractQRCode } = require('./helpers/qr-extractor');

// Extract QR code from current page
const qrData = await waitAndExtractQRCode(page);
console.log('QR Code:', qrData);
```

### Playwright-Appium Bridge (`helpers/playwright-appium-bridge.js`)

Coordinates between web and mobile:

```javascript
const { PlaywrightAppiumBridge } = require('./helpers/playwright-appium-bridge');

const bridge = new PlaywrightAppiumBridge(page);
await bridge.initializeMobile();
await bridge.captureScreenshots('test-state');
await bridge.closeMobile();
```

## Configuration

### Playwright Config (`playwright.config.js`)

Key settings:
- `testDir`: `./test/integration`
- `baseURL`: `https://bank-demo.truvera.io`
- `workers`: 1 (sequential execution for integration tests)
- `timeout`: 120 seconds per test

### Environment Variables

Create a `.env` file if needed:

```bash
APPIUM_HOST=localhost
APPIUM_PORT=4723
```

## Troubleshooting

### QR Code Extraction Fails

If QR code extraction fails:
1. Check that the QR code is visible on the page
2. Try increasing wait time in `waitAndExtractQRCode`
3. Check screenshot manually in `test-results/`

### Mobile Driver Connection Issues

1. Verify Appium is running: `curl http://localhost:4723/status`
2. Check Android device is connected: `adb devices`
3. Verify APK is in `app/` directory

### Test Timeout

1. Increase timeout in `playwright.config.js`
2. Check network connectivity
3. Verify both Appium and emulator are running

## Tips

1. **Run Tests in Headed Mode**: Use `--headed` flag to see what's happening
2. **Debug Mode**: Use `--debug` to step through tests
3. **Screenshots**: Screenshots are automatically taken on failure
4. **Parallel Execution**: Integration tests run sequentially (workers: 1) to avoid conflicts

## Example Test

```javascript
const { test } = require('@playwright/test');
const { BankDemoPage } = require('./helpers/bank-demo');
const { waitAndExtractQRCode } = require('./helpers/qr-extractor');
const { scanQRCode } = require('../helpers/qr-code');

test('bank demo integration', async ({ page }) => {
  // Web flow
  const bankDemo = new BankDemoPage(page);
  await bankDemo.navigate();
  await bankDemo.openBankAccount();

  // Extract QR code
  const qrData = await waitAndExtractQRCode(page);

  // Mobile flow (with mobile driver initialized)
  await scanQRCode(mobileDriver, qrData);
});
```

## Resources

- [Playwright Documentation](https://playwright.dev)
- [Appium Documentation](https://appium.io)
- [WebdriverIO Documentation](https://webdriver.io)
- [jsQR Library](https://github.com/cozmo/jsQR)
