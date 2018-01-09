package nikolai023;

public class RaceResult {
    private String driverName;
    private String vehicleName;
    private String time;

    public RaceResult(String driverName, String vehicleName, String time) {
        this.driverName = driverName;
        this.vehicleName = vehicleName;
        this.time = time;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getTime() {
        return time;
    }

    public String getDriverName() {
        return driverName;
    }
}
