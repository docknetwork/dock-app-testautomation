const { defineConfig, devices } = require('@playwright/test');

/**
 * Playwright configuration for bank-demo integration tests
 * @see https://playwright.dev/docs/test-configuration
 */
module.exports = defineConfig({
  testDir: './test/integration',

  // Maximum time one test can run for
  timeout: 120 * 1000,

  // Test execution settings
  fullyParallel: false, // Sequential execution for integration tests
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 1 : 0,
  workers: 1, // Run one test at a time for integration tests

  // Reporter configuration
  reporter: [
    ['list'],
    ['html', { outputFolder: 'test-reports/playwright-html', open: 'never' }],
    ['json', { outputFile: 'test-reports/playwright-json/results.json' }],
    ['junit', { outputFile: 'test-reports/playwright-junit/results.xml' }]
  ],

  // Shared settings for all projects
  use: {
    // Base URL for bank-demo
    baseURL: 'https://bank-demo.truvera.io',

    // Collect trace when retrying the failed test
    trace: 'retain-on-failure',

    // Screenshot on failure
    screenshot: 'only-on-failure',

    // Video on failure
    video: 'retain-on-failure',

    // Maximum time each action can take
    actionTimeout: 30 * 1000,

    // Viewport size
    viewport: { width: 1280, height: 720 },
  },

  // Configure projects for major browsers
  projects: [
    {
      name: 'chromium',
      use: {
        ...devices['Desktop Chrome'],
        // Additional context options
        permissions: ['clipboard-read', 'clipboard-write'],
      },
    },

    // Uncomment if you need to test on other browsers
    // {
    //   name: 'firefox',
    //   use: { ...devices['Desktop Firefox'] },
    // },
    // {
    //   name: 'webkit',
    //   use: { ...devices['Desktop Safari'] },
    // },
  ],

  // Output folder for test artifacts
  outputDir: 'test-results/',
});
