package dto;

import model.Pokemon;

import java.sql.Time;

/**
 * Created by Niklas on 07.09.2016.
 */
public class PokemonPutModel {
    private int number;
    private int userId;
    private String name;
    private long time;
    private String coordinates;
    private String locationName;
    private int wp;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getWp() {
        return wp;
    }

    public void setWp(int wp) {
        this.wp = wp;
    }

    public Pokemon toPokemon(){
        Pokemon pokemon = new Pokemon(number, userId);
        pokemon.setTime(new Time(time));
        pokemon.setLocationName(locationName);
        pokemon.setName(name);
        pokemon.setCoordinates(coordinates);
        pokemon.setWp(wp);

        return pokemon;
    }
}
