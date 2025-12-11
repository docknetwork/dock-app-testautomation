/**
 * Manual test script for OID4VC helper
 * Run with: node test/helpers/test-oid4vc.js
 */

const { issueOpenIDCredential, createProofRequest } = require('./credentials');

async function runTest() {
  console.log('Testing OID4VC credential issuance...\n');

  try {
    const url = await issueOpenIDCredential();
    console.log('\n✓ Test completed successfully!');
    console.log(`\nCredential Offer URL:\n${url}`);
  } catch (error) {
    console.error('\n✗ Test failed:', error.message);
    process.exit(1);
  }

  try {
    const proofRequest = await createProofRequest();
    console.log('\n✓ Test completed successfully!');
    console.log(`\nProof Request:\n${proofRequest}`);
  } catch (error) {
    console.error('\n✗ Test failed:', error.message);
    process.exit(1);
  }
}

runTest();
