package service;

import model.Team;
import model.User;
import service.interfaces.IUserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by philt on 07.09.2016.
 */
public class UserService implements IUserService {
    @Override
    public int getUserIdBySessionId(String sessionId) throws SQLException, ReflectiveOperationException {
        int userId = 0;
        Connection conn = DBHelper.getConnection();
        if(conn!=null) {
            PreparedStatement select = conn.prepareStatement("SELECT Id FROM User WHERE SessionId=?");
            select.setString(1, sessionId);
            ResultSet rs = select.executeQuery();
            while (rs.next())
                userId=rs.getInt(1);
            select.close();
            conn.close();
        }

        return userId;
    }

    @Override
    public User getUserById(int userId) throws SQLException, ReflectiveOperationException {
        User user = null;

        Connection conn = DBHelper.getConnection();
        if(conn!=null) {
            PreparedStatement select = conn.prepareStatement("SELECT Username, Team, Startdate, SessionId FROM User WHERE Id=?");
            select.setInt(1, userId);
            ResultSet rs = select.executeQuery();
            while (rs.next()){
                user = new User(userId);
                user.setUsername(rs.getString(1));
                user.setTeam(Team.getTeam(rs.getString(2)));
                user.setStartdate(rs.getDate(3));
                user.setSessionId(rs.getString(4));
            }
            select.close();
            conn.close();
        }
        return user;
    }
}
