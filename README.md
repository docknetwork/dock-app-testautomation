# Truvera Wallet E2E Test Automation

Smoke test suite for the Truvera Wallet mobile application using Appium, WebdriverIO, and Mocha.

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Test Reports](#test-reports)
- [CI/CD Integration](#cicd-integration)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Overview

This repository contains automated smoke tests for the Truvera Wallet Android application. The tests verify critical user flows including:

- Wallet creation and setup
- Passcode configuration
- App navigation and UI elements
- Core wallet functionality

**Tech Stack:**
- **Test Framework:** Mocha
- **Automation Tool:** Appium with WebdriverIO
- **Reporting:** Mochawesome, JUnit XML, CTRF
- **CI/CD:** GitHub Actions

## Prerequisites

Before you begin, ensure you have the following installed:

### Required Software

- **Node.js** 22.x or higher ([Download](https://nodejs.org/))
- **Java Development Kit (JDK)** 17 ([Download](https://adoptium.net/))
- **Android SDK** ([Download](https://developer.android.com/studio))
- **Appium** 2.x ([Installation Guide](https://appium.io/docs/en/latest/quickstart/install/))

### Android Setup

1. **Install Android SDK Platform Tools:**
   ```bash
   # macOS (using Homebrew)
   brew install --cask android-platform-tools

   # Or download Android Studio which includes SDK tools
   ```

2. **Set environment variables:**
   ```bash
   # Add to ~/.zshrc or ~/.bash_profile
   export ANDROID_HOME=$HOME/Library/Android/sdk
   export PATH=$PATH:$ANDROID_HOME/platform-tools
   export PATH=$PATH:$ANDROID_HOME/emulator
   ```

3. **Install Android Emulator:**
   ```bash
   # Create an AVD (Android Virtual Device)
   # Recommended: Pixel 7 Pro with API Level 30
   ```

### Appium Setup

1. **Install Appium globally:**
   ```bash
   npm install -g appium
   ```

2. **Install UiAutomator2 driver:**
   ```bash
   appium driver install uiautomator2
   ```

3. **Verify installation:**
   ```bash
   appium driver list
   ```

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/docknetwork/dock-app-testautomation.git
   cd dock-app-testautomation
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Place APK file:**
   - Download or build the Truvera Wallet APK
   - Place it in the `app/` directory with the name `truvera-wallet.apk`
   ```bash
   # Directory structure
   app/
   └── truvera-wallet.apk
   ```

## Running Tests

### Local Execution

1. **Start Appium server:**
   ```bash
   appium
   ```
   The server will start on `http://localhost:4723`

2. **Start Android Emulator:**
   ```bash
   # List available emulators
   emulator -list-avds

   # Start emulator
   emulator -avd <your_avd_name>
   ```

3. **Run tests:**
   ```bash
   # Run all tests
   npm test

   ```

## Test Reports

The test suite generates multiple report formats:

### 1. **Mochawesome Report** (HTML)
- **Location:** `test-reports/mochawesome/report.html`
- **Features:** Interactive HTML report with charts and screenshots
- Open in browser for detailed test results

### 3. **CTRF Report** (JSON)
- **Location:** `ctrf/ctrf-report.json`
- **Features:** Common Test Report Format for standardized reporting
- Automatically published to GitHub Actions summary

### 4. **Screenshots**
- **Location:** `test-reports/screenshots/`
- **Captured:** After each test (pass or fail)
- **Naming:** `{status}-{test_name}-{timestamp}.png`

## CI/CD Integration

### GitHub Actions

The repository includes a comprehensive CI/CD workflow:

**Workflow File:** `.github/workflows/smoke-tests.yml`

**Triggers:**
- Push to any branch
- Manual workflow dispatch with optional build number

**Features:**
- Automated APK download from S3
- Android emulator setup (Pixel 7 Pro, API 30)
- Test execution with artifact retention
- Multiple test reports (HTML, CTRF)
- Slack notifications with test results
- GitHub Actions summary with CTRF report

**Manual Trigger:**
```bash
# Via GitHub UI: Actions > End-to-end test > Run workflow
# Optionally provide build number (e.g., v1.7.0 build-904)
```

**Automatic Trigger:**
- Runs on every push
- Automatically uses latest build from S3

### Slack Notifications

Test results are sent to Slack with:
- Build number and branch
- Test summary (passed/failed/skipped)
- Duration
- Failed test names (if any)
- Link to GitHub Actions run
