package model;

import java.util.Date;
import java.util.List;

public class LogbookEntry {
    private int id;
    private int userId;
    private Date date;
    private Date starttime;
    private Date endtime;
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

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
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
