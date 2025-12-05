require("dotenv").config();
const axios = require("axios").default;

const API_KEY = process.env.CERTS_API_KEY;
const API_URL = process.env.CERTS_API_URL;
const ISSUER_DID = process.env.ISSUER_DID;

// Validate required environment variables
if (!API_KEY || !API_URL || !ISSUER_DID) {
  throw new Error(
    "Missing required environment variables. Please ensure CERTS_API_KEY, CERTS_API_URL, and ISSUER_DID are set in .env file"
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
  timeout: 30000,
});

apiClient.interceptors.response.use(undefined, async (error) => {
  const { config } = error;

  // Initialize retry config if not present
  if (!config || !config.retry) {
    config.retry = { count: 0, limit: 3, delay: 1000 };
  }

  config.retry.count += 1;

  // Check if we've exceeded retry limit
  if (config.retry.count >= config.retry.limit) {
    console.error(
      `Max retries (${config.retry.limit}) exceeded for ${config.url}`
    );
    return Promise.reject(error);
  }

  // Don't retry on 4xx errors (except 408 timeout and 429 rate limit)
  if (
    error.response &&
    error.response.status >= 400 &&
    error.response.status < 500
  ) {
    if (error.response.status !== 408 && error.response.status !== 429) {
      return Promise.reject(error);
    }
  }

  // Calculate exponential backoff delay
  const delay = config.retry.delay * Math.pow(2, config.retry.count - 1);
  console.log(
    `Retry attempt ${config.retry.count}/${config.retry.limit} for ${config.url} in ${delay}ms...`
  );

  await new Promise((resolve) => setTimeout(resolve, delay));
  return apiClient(config);
});

async function createOpenIDIssuer() {
  try {
    const response = await apiClient.post("/openid/issuers", {
      credentialOptions: {
        schema: "https://schema.truvera.io/CityResident-V1-1753885626252.json",
        credential: {
          type: ["VerifiableCredential", "CityResident"],
          issuer: ISSUER_DID,
          subject: {
            id: "did:key:z6MkqhEm4rqY4zA5W6XgYE2U57iujsMQWCMXZ98b3EUsgijK",
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
    console.error("Failed to create OpenID issuer:", error.message || error);
    throw error;
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


async function requestClaims() {
  const response = await apiClient.post("/credentials/request-claims", {
    schema: "https://schema.truvera.io/CityResident-V1-1753885626252.json",
    claims: ["id"],
    credentialOptions: {
      schema: "https://schema.truvera.io/CityResident-V1-1753885626252.json",
      credential: {
        type: ["VerifiableCredential", "CityResident"],
        issuer: ISSUER_DID,
        subject: {
          address: "Spear street 20",
          city: "San Francisco",
          state: "CA",
          zip: "94133",
          country: "USA",
        },
      },
      algorithm: "dockbbs",
    },
  });
  return response.data.qrUrl;
}

async function issueCredential({ subjectDID, distribute }) {
  return apiClient
    .post("/credentials", {
      anchor: false,
      persist: false,
      credential: {
        type: ["VerifiableCredential", "CityResident"],
        issuer: ISSUER_DID,
        subject: {
          id: subjectDID,
          address: "Spear street 20",
          city: "San Francisco",
          state: "CA",
          zip: "94133",
          country: "USA",
        },
      },
      algorithm: "dockbbs+",
      distribute: distribute || false,
    })
    .then((response) => response.data);
}

module.exports = {
  createOpenIDIssuer,
  generateOID4VCOffer,
  issueOpenIDCredential,
  requestClaims,
  issueCredential,
};
