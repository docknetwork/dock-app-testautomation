require('dotenv').config();
const axios = require("axios").default;

const API_KEY = process.env.CERTS_API_KEY;
const API_URL = process.env.CERTS_API_URL;
const ISSUER_DID = process.env.ISSUER_DID;

// Validate required environment variables
if (!API_KEY || !API_URL || !ISSUER_DID) {
  throw new Error(
    'Missing required environment variables. Please ensure CERTS_API_KEY, CERTS_API_URL, and ISSUER_DID are set in .env file'
  );
}

const requestOptions = {
  headers: {
    "Content-Type": "application/json",
    "User-Agent": "insomnia/9.3.2",
    "DOCK-API-TOKEN": API_KEY,
  },
};

const apiClient = axios.create({
  baseURL: API_URL,
  headers: requestOptions.headers,
});

async function createOpenIDIssuer() {
  try {
    const response = await apiClient.post("/openid/issuers", {
      credentialOptions: {
        schema: 'https://schema.truvera.io/CityResident-V1-1753885626252.json',
        credential: {
          type: ["VerifiableCredential", "CityResident"],
          issuer: ISSUER_DID,
          subject: {
            id: 'did:key:z6MkqhEm4rqY4zA5W6XgYE2U57iujsMQWCMXZ98b3EUsgijK',
            address: "Spear street 20",
            city: "San Francisco",
            state: "CA",
            zip: "94133",
            country: "USA",
          },
        },
        algorithm: "dockbbs",
      },
      singleUse: false,
    });
    console.log(`OpenID issuer ${response.data.id} was created.`);
    return response.data;
  } catch (error) {
    debugger;
    console.error("Failed to create OpenID issuer:", error);
  }
}

async function generateOID4VCOffer(issuerID) {
  try {
    const response = await apiClient.post("/openid/credential-offers", {
      id: issuerID,
    });
    return response.data.url;
  } catch (error) {
    console.error("Failed to generate OID4VC offer:", error);
  }
}

async function issueOpenIDCredential() {
  const openIDIssuer = await createOpenIDIssuer();
  const url = await generateOID4VCOffer(openIDIssuer.id);
  return url;
}

module.exports = {
  createOpenIDIssuer,
  generateOID4VCOffer,
  issueOpenIDCredential,
};
