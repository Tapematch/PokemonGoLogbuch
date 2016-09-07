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
                entry.setStarttime(rs.getTime(3));
                entry.setEndtime(rs.getTime(4));
                entry.setStartlevel(rs.getInt(5));
                entry.setLevelUp(rs.getBoolean(6));
                entry.setStartEp(rs.getInt(7));
                entry.setEndEp(rs.getInt(8));

                List<WayPoint> waypoints = new ArrayList<>();
                PreparedStatement selectWayPoints = conn.prepareStatement("SELECT Number, Coordinates, LocationName, Time FROM waypoint WHERE EntryId=?");
                selectWayPoints.setInt(1, entryId);
                ResultSet rsWayPoints = selectWayPoints.executeQuery();
                while (rsWayPoints.next()){
                    WayPoint waypoint = new WayPoint(rsWayPoints.getInt(1), entryId);
                    waypoint.setCoordinates(rsWayPoints.getString(2));
                    waypoint.setLocationName(rsWayPoints.getString(3));
                    waypoint.setTime(rsWayPoints.getTime(4));
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
                    pokemon.setTime(rsPokemon.getTime(3));
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
                    gym.setTime(rsGyms.getTime(2));
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
    public void addLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException {

        Connection conn = DBHelper.getConnection();
        if(conn!=null) {

            PreparedStatement insert = conn.prepareStatement("INSERT INTO logbookentry (Id, UserId, Date, Starttime, Endtime, Startlevel, LevelUp, StartEp, EndEp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insert.setInt(1, entry.getId());
            insert.setInt(2, entry.getUserId());
            insert.setDate(3, entry.getDate());
            insert.setTime(4, entry.getStarttime());
            insert.setTime(5, entry.getEndtime());
            insert.setInt(6, entry.getStartlevel());
            insert.setBoolean(7, entry.isLevelUp());
            insert.setInt(8, entry.getStartEp());
            insert.setInt(9, entry.getEndEp());
            insert.executeUpdate();
            insert.close();

            for (WayPoint wayPoint : entry.getWayPoints()) {
                PreparedStatement insertWayPoint = conn.prepareStatement("INSERT INTO waypoint (Number, EntryId, Time, Coordinates, LocationName) VALUES (?, ?, ?, ?, ?)");
                insertWayPoint.setInt(1, wayPoint.getNumber());
                insertWayPoint.setInt(2, entry.getId());
                insertWayPoint.setTime(3, wayPoint.getTime());
                insertWayPoint.setString(4, wayPoint.getCoordinates());
                insertWayPoint.setString(5, wayPoint.getLocationName());
                insertWayPoint.executeUpdate();
                insertWayPoint.close();
            }

            for (Pokemon pokemon : entry.getPokemon()) {
                PreparedStatement insertPokemon = conn.prepareStatement("INSERT INTO pokemon (Number, EntryId, Name, Time, Coordinates, Wp, LocationName) VALUES (?, ?, ?, ?, ?, ?, ?)");
                insertPokemon.setInt(1, pokemon.getNumber());
                insertPokemon.setInt(2, entry.getId());
                insertPokemon.setString(3, pokemon.getName());
                insertPokemon.setTime(4, pokemon.getTime());
                insertPokemon.setString(5, pokemon.getCoordinates());
                insertPokemon.setInt(6, pokemon.getWp());
                insertPokemon.setString(7, pokemon.getLocationName());
                insertPokemon.executeUpdate();
                insertPokemon.close();
            }

            for (Gym gym : entry.getGyms()) {
                PreparedStatement insertGym = conn.prepareStatement("INSERT INTO gym (Number, EntryId,  Time, Coordinates, LocationName, Level, Team, Win) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                insertGym.setInt(1, gym.getNumber());
                insertGym.setInt(2, entry.getId());
                insertGym.setTime(3, gym.getTime());
                insertGym.setString(4, gym.getCoordinates());
                insertGym.setString(5, gym.getLocationName());
                insertGym.setInt(6, gym.getLevel());
                insertGym.setString(7, gym.getTeam().name());
                insertGym.setBoolean(8, gym.isWin());
                insertGym.executeUpdate();
                insertGym.close();
            }

            conn.close();
        }
    }

    @Override
    public void updateLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException {
        deleteLogbookEntry(entry.getId());
        addLogbookEntry(entry);
    }

    @Override
    public void deleteLogbookEntry(int entryId) throws SQLException, ReflectiveOperationException {

        Connection conn = DBHelper.getConnection();

        if(conn!=null) {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM logbookentry WHERE Id=?");
            delete.setInt(1, entryId);
            delete.executeUpdate();
            delete.close();

            PreparedStatement deleteWayPoints = conn.prepareStatement("DELETE FROM waypoint WHERE EntryId=?");
            deleteWayPoints.setInt(1, entryId);
            deleteWayPoints.executeUpdate();
            deleteWayPoints.close();

            PreparedStatement deletePokemon = conn.prepareStatement("DELETE FROM pokemon WHERE EntryId=?");
            deletePokemon.setInt(1, entryId);
            deletePokemon.executeUpdate();
            deletePokemon.close();

            PreparedStatement deleteGyms = conn.prepareStatement("DELETE FROM gym WHERE EntryId=?");
            deleteGyms.setInt(1, entryId);
            deleteGyms.executeUpdate();
            deleteGyms.close();

            conn.close();
        }
    }
}
