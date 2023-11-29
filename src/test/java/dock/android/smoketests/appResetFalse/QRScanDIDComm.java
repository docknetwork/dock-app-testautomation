package dock.android.smoketests.appResetFalse;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.By;

import dock.android.pageobjects.BaseTestCaseAndroid;
import dock.android.pageobjects.WalletHomePage;
import dock.utilities.TestGroup;
import io.appium.java_client.android.AndroidDriver;

public class QRScanDIDComm extends BaseTestCaseAndroid {
    @Test(groups = TestGroup.SmokeTest, description = "Test to verify scanning QR DIDComm")
    public void verifyScanDIDComm() {
        String qrCodeValue = "didcomm://eyJhbGciOiJFZERTQSIsImtpZCI6ImRpZDpkb2NrOjVHNW42TkQ2djUyTDNXVVR1VEI5eGZwbWZUcnNFdlAyRHZRZTlmTkI1aU56cjNyWCNrZXlzLTEifQ.eyJ0eXBlIjoiaHR0cHM6Ly9kaWRjb21tLm9yZy9pc3N1ZS1jcmVkZW50aWFsLzIuMC9pc3N1ZS1jcmVkZW50aWFsIiwic2VuZGVyRGlkIjoiZGlkOmRvY2s6NUc1bjZORDZ2NTJMM1dVVHVUQjl4ZnBtZlRyc0V2UDJEdlFlOWZOQjVpTnpyM3JYIiwicGF5bG9hZCI6eyJkb21haW4iOiJhcGkuZG9jay5pbyIsImNyZWRlbnRpYWxzIjpbeyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSIseyJkayI6Imh0dHBzOi8vbGQuZG9jay5pby9jcmVkZW50aWFscyMiLCJCYXNpY0NyZWRlbnRpYWwiOiJkazpCYXNpY0NyZWRlbnRpYWwiLCJuYW1lIjoiZGs6bmFtZSIsImRlc2NyaXB0aW9uIjoiZGs6ZGVzY3JpcHRpb24iLCJsb2dvIjoiZGs6bG9nbyJ9XSwiaWQiOiJodHRwczovL2NyZWRzLXN0YWdpbmcuZG9jay5pby82MzU3Y2ZkZjI0NWNhNmJmYTdiODA1MDg4OGNmOGIwNzczZDc2NjkzYWU1YjBjYjIyZGIwYmI3ZWRlYWE2YjAxIiwidHlwZSI6WyJWZXJpZmlhYmxlQ3JlZGVudGlhbCIsIkJhc2ljQ3JlZGVudGlhbCJdLCJjcmVkZW50aWFsU3ViamVjdCI6eyJuYW1lIjoidGVzdCJ9LCJpc3N1YW5jZURhdGUiOiIyMDIzLTA0LTI3VDE2OjA5OjUzLjI2NVoiLCJuYW1lIjoiQmFzaWMgQ3JlZGVudGlhbCIsImlzc3VlciI6eyJuYW1lIjoidGVzdCBzdGFnaW5nIiwiZGVzY3JpcHRpb24iOiIiLCJsb2dvIjoiIiwiaWQiOiJkaWQ6ZG9jazo1RzVuNk5ENnY1MkwzV1VUdVRCOXhmcG1mVHJzRXZQMkR2UWU5Zk5CNWlOenIzclgifSwicHJvb2YiOnsidHlwZSI6IkVkMjU1MTlTaWduYXR1cmUyMDE4IiwiY3JlYXRlZCI6IjIwMjMtMDQtMjdUMTY6Mjg6NDlaIiwidmVyaWZpY2F0aW9uTWV0aG9kIjoiZGlkOmRvY2s6NUc1bjZORDZ2NTJMM1dVVHVUQjl4ZnBtZlRyc0V2UDJEdlFlOWZOQjVpTnpyM3JYI2tleXMtMSIsInByb29mUHVycG9zZSI6ImFzc2VydGlvbk1ldGhvZCIsImp3cyI6ImV5SmhiR2NpT2lKRlpFUlRRU0lzSW1JMk5DSTZabUZzYzJVc0ltTnlhWFFpT2xzaVlqWTBJbDE5Li5EYVJWSXZwYnN0R1dHdUxhc0l4M0REUzZ4dUdETFl1OWd4NHQxTExsTUFYXzZLSEdhdms5eHRGUVhlOWxhU2RyQXkzYWE1ZWE5UWtwWGQxdE9LeFVCUSJ9fV19fQ.v-xtRg9US1RDO9HfgJSa-UxIm5Zd2w-81IihtOyOyyaH713mUIa9FI34Yu1qfliFfklsQ1E7YCg_6bNKmoIzAg";

        WalletHomePage walletHomePage = new WalletHomePage(driver);
        if (walletHomePage.getWalletStatus()) {
            walletHomePage.createNewWallet();
        }
        else {
            walletHomePage.enterPassCodeOneTime();
        }
        walletHomePage.ensureTestnet().ensureHasDID()
                .scanQRCode(qrCodeValue);
        walletHomePage.waitABit(3000);

        // Verify Credential is displayed
        Assert.assertTrue(walletHomePage.isDisplayedByText("Basic Credential"));
    }
}
