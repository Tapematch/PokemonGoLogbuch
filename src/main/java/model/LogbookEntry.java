package model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class LogbookEntry {
    private int id;
    private int userId;
    private Date date;
    private Time starttime;
    private Time endtime;
    private int startlevel;
    private boolean levelUp;
    private int startEp;
    private int endEp;
    private List<WayPoint> wayPoints;
    private List<Pokemon> pokemon;
    private List<Gym> gyms;

    public LogbookEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public int getStartlevel() {
        return startlevel;
    }

    public void setStartlevel(int startlevel) {
        this.startlevel = startlevel;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    public int getStartEp() {
        return startEp;
    }

    public void setStartEp(int startEp) {
        this.startEp = startEp;
    }

    public int getEndEp() {
        return endEp;
    }

    public void setEndEp(int endEp) {
        this.endEp = endEp;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public List<Gym> getGyms() {
        return gyms;
    }

    public void setGyms(List<Gym> gyms) {
        this.gyms = gyms;
    }
}
