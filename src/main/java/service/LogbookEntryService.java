package service;

import model.*;
import service.interfaces.ILogbookEntryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogbookEntryService implements ILogbookEntryService {
    @Override
    public List<LogbookEntry> getLogbookEntriesByUserId(int userId) throws SQLException, ReflectiveOperationException {
        List<LogbookEntry> entries = new ArrayList<>();
        Connection conn = DBHelper.getConnection();

        if(conn!=null) {
            PreparedStatement select = conn.prepareStatement("SELECT Id, Date, Starttime, Endtime, Startlevel, LevelUp, StartEp, EndEp FROM logbookentry WHERE UserId=?");
            select.setInt(1, userId);
            ResultSet rs = select.executeQuery();
            while (rs.next()){
                int entryId = rs.getInt(1);

                LogbookEntry entry = new LogbookEntry(entryId);
                entry.setUserId(userId);
                entry.setDate(rs.getDate(2));
                entry.setStarttime(rs.getDate(3));
                entry.setEndtime(rs.getDate(4));
                entry.setStartlevel(rs.getInt(5));
                entry.setLevelUp(rs.getBoolean(6));
                entry.setStartEp(rs.getInt(7));
                entry.setEndEp(rs.getInt(8));

                List<WayPoint> waypoints = new ArrayList<>();
                PreparedStatement selectWayPoints = conn.prepareStatement("SELECT Number, Coordinates, LocationName FROM waypoint WHERE EntryId=?");
                selectWayPoints.setInt(1, entryId);
                ResultSet rsWayPoints = selectWayPoints.executeQuery();
                while (rsWayPoints.next()){
                    WayPoint waypoint = new WayPoint(rsWayPoints.getInt(1), entryId);
                    waypoint.setCoordinates(rsWayPoints.getString(2));
                    waypoint.setLocationName(rsWayPoints.getString(3));
                    waypoints.add(waypoint);
                }
                entry.setWayPoints(waypoints);
                selectWayPoints.close();

                List<Pokemon> pokemonList = new ArrayList<>();
                PreparedStatement selectPokemon = conn.prepareStatement("SELECT Number, Name, Time, Coordinates, Wp, LocationName FROM pokemon WHERE EntryId=?");
                selectPokemon.setInt(1, entryId);
                ResultSet rsPokemon = selectPokemon.executeQuery();
                while (rsPokemon.next()){
                    Pokemon pokemon = new Pokemon(rsPokemon.getInt(1), entryId);
                    pokemon.setName(rsPokemon.getString(2));
                    pokemon.setTime(rsPokemon.getDate(3));
                    pokemon.setCoordinates(rsPokemon.getString(4));
                    pokemon.setWp(rsPokemon.getInt(5));
                    pokemon.setLocationName(rsPokemon.getString(6));
                    pokemonList.add(pokemon);
                }
                entry.setPokemon(pokemonList);
                selectPokemon.close();

                List<Gym> gyms = new ArrayList<>();
                PreparedStatement selectGyms = conn.prepareStatement("SELECT Number, Time, Coordinates, LocationName, Level, Team, Win FROM gym WHERE EntryId=?");
                selectGyms.setInt(1, entryId);
                ResultSet rsGyms = selectGyms.executeQuery();
                while (rsGyms.next()){
                    Gym gym = new Gym(rsGyms.getInt(1), entryId);
                    gym.setTime(rsGyms.getDate(2));
                    gym.setCoordinates(rsGyms.getString(3));
                    gym.setLocationName(rsGyms.getString(4));
                    gym.setLevel(rsGyms.getInt(5));
                    gym.setTeam(Team.getTeam(rsGyms.getString(6)));
                    gym.setWin(rsGyms.getBoolean(7));
                    gyms.add(gym);
                }
                entry.setGyms(gyms);
                selectGyms.close();

                entries.add(entry);
            }
            select.close();
            conn.close();
        }

        return entries;
    }

    @Override
    public void addLogbookEntry(int userId, LogbookEntry entry) {

    }

    @Override
    public void updateLogbookEntry(int userId, LogbookEntry entry) {

    }

    @Override
    public void deleteLogbookEntry(int entryId) {

    }
}
