package service;

import model.User;
import service.interfaces.ILoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoginService implements ILoginService {

    @Override
    public User loginUser(String username, String password) throws SQLException, ReflectiveOperationException {
        boolean success = false;
        int userId = 0;
        Connection conn = DBHelper.getConnection();
        if(conn!=null){

            PreparedStatement select = conn.prepareStatement("SELECT Password, Id FROM User WHERE Username=?");
            select.setString(1, username);
            ResultSet rs = select.executeQuery();
            while (rs.next())
                if(rs.getString(1).equals(password)){
                    userId = Integer.parseInt(rs.getString(2));
                    String sessionId = UUID.randomUUID().toString();
                    PreparedStatement update = conn.prepareStatement("UPDATE User SET SessionId=? WHERE Id=?");
                    update.setString(1, sessionId);
                    update.setInt(2, userId);

                    if (update.executeUpdate()>0) {
                        success = true;
                    }

                    update.close();
                }
            select.close();
            conn.close();
        }
        if (success){
            UserService userService = new UserService();
            return userService.getUserById(userId);
        }
        return null;
    }

    @Override
    public boolean sessionIdIsValid(String sessionId) throws ReflectiveOperationException, SQLException {
        boolean valid = false;

        Connection conn = DBHelper.getConnection();
        if(conn!=null) {
            PreparedStatement select = conn.prepareStatement("SELECT SessionId FROM User WHERE SessionId=?");
            select.setString(1, sessionId);
            ResultSet rs = select.executeQuery();
            while (rs.next())
                if (rs.getString(1).equals(sessionId)) {
                    valid = true;
                }
            select.close();
            conn.close();
        }

        return valid;
    }

    @Override
    public void logoutUser(int userId) throws ReflectiveOperationException, SQLException {

        Connection conn = DBHelper.getConnection();
        if(conn!=null) {
            PreparedStatement update = conn.prepareStatement("UPDATE User SET SessionId=NULL WHERE Id=?");
            update.setInt(1, userId);
            update.executeUpdate();
            update.close();
            conn.close();
        }
    }
}
