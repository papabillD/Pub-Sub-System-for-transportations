package Final;

import java.io.Serializable;

public class BusPosition implements Serializable {
    private static final long serialVersionUID= -6128237785147214805L;


    double latitude;
    double longitude;
    String lineCode,routeCode,vehicleId,date;
    boolean fail ;
    boolean end;

    public BusPosition(double latitude, double longitude, String lineCode, String routeCode, String vehicleId, String date,boolean end) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.lineCode = lineCode;
        this.routeCode = routeCode;
        this.vehicleId = vehicleId;
        this.date = date;
        this.fail=false;
        this.end=end;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLineCode() {
        return lineCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDate() {
        return date;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFailure(boolean fail){
        this.fail=fail;
    }

    public boolean getFailure(){
        return fail;
    }

    public boolean getEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "BusPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude + /*
                ", lineCode='" + lineCode + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", vehicleId='" + vehicleId + '\'' +*/
                ", date=" + date +
                '}';
    }

    public String toString2() {
        return "BusPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", lineCode='" + lineCode + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", date=" + date +
                '}';
    }
}
