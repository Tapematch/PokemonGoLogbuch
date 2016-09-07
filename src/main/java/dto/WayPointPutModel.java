package dto;

import model.WayPoint;

import java.sql.Time;

/**
 * Created by Niklas on 07.09.2016.
 */
public class WayPointPutModel {
    private int number;
    private int userId;
    private long time;
    private String coordinates;
    private String locationName;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public WayPoint toWayPoint(){
        WayPoint wayPoint = new WayPoint(number, userId);
        wayPoint.setCoordinates(coordinates);
        wayPoint.setLocationName(locationName);
        wayPoint.setTime(new Time(time));

        return wayPoint;
    }
}
