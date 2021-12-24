package dock.utilities;

public class DeviceStatus {

    private String statusAndroid1;
    private String statusAndroid2;

    public DeviceStatus() {
        this.statusAndroid1 = "free";
        this.statusAndroid2 = "free";
    }

    public String getStatusAndroid1() {
        return this.statusAndroid1;
    }

    public void setStatusAndroid1(String statusAndroid1) {
        this.statusAndroid1 = statusAndroid1;
    }

    public String getStatusAndroid2() {
        return this.statusAndroid2;
    }

    public void setStatusAndroid2(String statusAndroid2) {
        this.statusAndroid2 = statusAndroid2;
    }
}
