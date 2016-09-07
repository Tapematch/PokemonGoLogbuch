package service;

import com.mysql.jdbc.Statement;
import model.*;
import service.interfaces.ILogbookEntryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogbookEntryService implements ILogbookEntryService {

    private List<PreparedStatement> statements = new ArrayList<>();

    @Override
    public List<LogbookEntry> getLogbookEntriesByUserId(int userId) throws SQLException, ReflectiveOperationException {
        List<LogbookEntry> entries = new ArrayList<>();
        Connection conn = DBHelper.getConnection();

        try {
            PreparedStatement select = conn.prepareStatement("SELECT Id, Date, Starttime, Endtime, Startlevel, LevelUp, StartEp, EndEp FROM logbookentry WHERE UserId=?");
            select.setInt(1, userId);
            ResultSet rs = select.executeQuery();
            while (rs.next()){
                int entryId = rs.getInt(1);

                LogbookEntry entry = new LogbookEntry();
                entry.setId(entryId);
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
                statements.add(selectWayPoints);

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
                statements.add(selectPokemon);

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
                statements.add(selectGyms);

                entries.add(entry);
            }
            statements.add(select);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            for (PreparedStatement statement : statements)
                statement.close();
            statements.clear();
            conn.close();
        }

        return entries;
    }

    @Override
    public void addLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException {

        Connection conn = DBHelper.getConnection();
        try {

            PreparedStatement insert = conn.prepareStatement("INSERT INTO logbookentry (UserId, Date, Starttime, Endtime, Startlevel, LevelUp, StartEp, EndEp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insert.setInt(1, entry.getUserId());
            insert.setDate(2, entry.getDate());
            insert.setTime(3, entry.getStarttime());
            insert.setTime(4, entry.getEndtime());
            insert.setInt(5, entry.getStartlevel());
            insert.setBoolean(6, entry.isLevelUp());
            insert.setInt(7, entry.getStartEp());
            insert.setInt(8, entry.getEndEp());
            insert.executeUpdate();

            ResultSet tableKeys = insert.getGeneratedKeys();
            tableKeys.next();
            entry.setId(tableKeys.getInt(1));

            statements.add(insert);

            conn = insertLinkedItems(conn, entry);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            for (PreparedStatement statement : statements)
                statement.close();
            statements.clear();
            conn.close();
        }
    }

    @Override
    public void updateLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException {

        Connection conn = DBHelper.getConnection();
        try {

            int entryId = entry.getId();

            PreparedStatement update = conn.prepareStatement("UPDATE logbookentry SET UserId=?, Date=?, Starttime=?, Endtime=?, Startlevel=?, LevelUp=?, StartEp=?, EndEp=? WHERE Id=?");
            update.setInt(1, entry.getUserId());
            update.setDate(2, entry.getDate());
            update.setTime(3, entry.getStarttime());
            update.setTime(4, entry.getEndtime());
            update.setInt(5, entry.getStartlevel());
            update.setBoolean(6, entry.isLevelUp());
            update.setInt(7, entry.getStartEp());
            update.setInt(8, entry.getEndEp());
            update.setInt(9, entryId);
            update.executeUpdate();
            statements.add(update);

            conn = deleteLinkedItems(conn, entryId);
            conn = insertLinkedItems(conn, entry);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            for (PreparedStatement statement : statements)
                statement.close();
            statements.clear();
            conn.close();
        }
    }

    @Override
    public void deleteLogbookEntry(int entryId) throws SQLException, ReflectiveOperationException {

        Connection conn = DBHelper.getConnection();

        try {
            conn = deleteLinkedItems(conn, entryId);
            PreparedStatement delete = conn.prepareStatement("DELETE FROM logbookentry WHERE Id=?");
            delete.setInt(1, entryId);
            delete.executeUpdate();
            statements.add(delete);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            for (PreparedStatement statement : statements)
                statement.close();
            statements.clear();
            conn.close();
        }
    }

    private Connection deleteLinkedItems(Connection conn, int entryId) throws SQLException {
        PreparedStatement deleteWayPoints = conn.prepareStatement("DELETE FROM waypoint WHERE EntryId=?");
        deleteWayPoints.setInt(1, entryId);
        deleteWayPoints.executeUpdate();
        statements.add(deleteWayPoints);

        PreparedStatement deletePokemon = conn.prepareStatement("DELETE FROM pokemon WHERE EntryId=?");
        deletePokemon.setInt(1, entryId);
        deletePokemon.executeUpdate();
        statements.add(deletePokemon);

        PreparedStatement deleteGyms = conn.prepareStatement("DELETE FROM gym WHERE EntryId=?");
        deleteGyms.setInt(1, entryId);
        deleteGyms.executeUpdate();
        statements.add(deleteGyms);

        return conn;
    }

    private Connection insertLinkedItems(Connection conn, LogbookEntry entry) throws SQLException {
        for (WayPoint wayPoint : entry.getWayPoints()) {
            PreparedStatement insertWayPoint = conn.prepareStatement("INSERT INTO waypoint (Number, EntryId, Time, Coordinates, LocationName) VALUES (?, ?, ?, ?, ?)");
            insertWayPoint.setInt(1, wayPoint.getNumber());
            insertWayPoint.setInt(2, entry.getId());
            insertWayPoint.setTime(3, wayPoint.getTime());
            insertWayPoint.setString(4, wayPoint.getCoordinates());
            insertWayPoint.setString(5, wayPoint.getLocationName());
            insertWayPoint.executeUpdate();
            statements.add(insertWayPoint);
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
            statements.add(insertPokemon);
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
            statements.add(insertGym);
        }

        return conn;
    }
}
