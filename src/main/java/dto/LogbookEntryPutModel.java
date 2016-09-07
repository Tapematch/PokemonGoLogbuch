package dto;

import model.Gym;
import model.LogbookEntry;
import model.Pokemon;
import model.WayPoint;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class LogbookEntryPutModel {
    private int id;
    private int userId;
    private long date;
    private long starttime;
    private long endtime;
    private int startlevel;
    private boolean levelUp;
    private int startEp;
    private int endEp;
    private List<WayPointPutModel> waypoints;
    private List<GymPutModel> gyms;
    private List<PokemonPutModel> pokemon;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
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

    public List<WayPointPutModel> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WayPointPutModel> waypoints) {
        this.waypoints = waypoints;
    }

    public List<GymPutModel> getGyms() {
        return gyms;
    }

    public void setGyms(List<GymPutModel> gyms) {
        this.gyms = gyms;
    }

    public List<PokemonPutModel> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<PokemonPutModel> pokemon) {
        this.pokemon = pokemon;
    }

    public LogbookEntry toLogbookEntry(){
        LogbookEntry entry = new LogbookEntry();
        entry.setId(id);
        entry.setUserId(userId);
        entry.setDate(new Date(date));
        entry.setStarttime(new Time(starttime));
        entry.setEndtime(new Time(endtime));
        entry.setStartEp(startEp);
        entry.setEndEp(endEp);
        entry.setLevelUp(levelUp);
        entry.setStartlevel(startlevel);

        List<WayPoint> wayPoints = new ArrayList<>();
        for (WayPointPutModel point : waypoints){
            wayPoints.add(point.toWayPoint());
        }

        List<Gym> gymList = new ArrayList<>();
        for (GymPutModel gym: gyms){
            gymList.add(gym.toGym());
        }

        List<Pokemon> pokemonList = new ArrayList<>();
        for (PokemonPutModel poke : pokemon){
            pokemonList.add(poke.toPokemon());
        }

        entry.setWayPoints(wayPoints);
        entry.setGyms(gymList);
        entry.setPokemon(pokemonList);

        return entry;
    }
}
