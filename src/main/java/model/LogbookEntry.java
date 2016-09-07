package model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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

    public LogbookEntry() {

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

    public JsonObject toJson(){
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("userId", userId)
                .add("date", date.getTime())
                .add("startTime", starttime.getTime())
                .add("endTime", endtime.getTime())
                .add("startLevel", startlevel)
                .add("levelUp", levelUp)
                .add("startEp", startEp)
                .add("endEp", endEp);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (WayPoint wayPoint : wayPoints){
            arrayBuilder.add(wayPoint.toJson());
        }

        builder.add("waypoints", arrayBuilder.build());

        arrayBuilder = Json.createArrayBuilder();
        for (Gym gym : gyms){
            arrayBuilder.add(gym.toJson());
        }

        builder.add("arenas", arrayBuilder.build());

        arrayBuilder = Json.createArrayBuilder();
        for (Pokemon poke : pokemon){
            arrayBuilder.add(poke.toJson());
        }

        builder.add("pokemons", arrayBuilder.build());

        return builder.build();
    }
}
