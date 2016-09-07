package dto;

import model.Gym;
import model.Team;

import java.sql.Time;

public class GymPutModel {
    private int number;
    private int userId;
    private long time;
    private String coordinates;
    private String locationName;
    private int level;
    private String team;
    private boolean win;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Gym toGym(){
        Gym gym = new Gym(number, userId);
        gym.setCoordinates(coordinates);
        gym.setLevel(level);
        gym.setLocationName(locationName);
        gym.setTeam(Team.getTeam(team));
        gym.setTime(new Time(time));
        gym.setWin(win);

        return gym;
    }
}
