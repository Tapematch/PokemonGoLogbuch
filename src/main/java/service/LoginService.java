package service;

import dto.Identity;
import service.interfaces.ILoginService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoginService implements ILoginService {

@Override
    public Identity loginUser(String username, String password) {
        Identity identity = null;
        Connection conn = DBHelper.getConnection();

        if(conn!=null){
            try {
                PreparedStatement select = conn.prepareStatement("SELECT Password, Id FROM User WHERE Username=?");
                select.setString(1, username);
                ResultSet rs = select.executeQuery();
                while (rs.next())
                    if(rs.getString(1).equals(password)){
                        int userId = Integer.parseInt(rs.getString(2));
                        String sessionId = UUID.randomUUID().toString();
                        PreparedStatement update = conn.prepareStatement("UPDATE User SET SessionId=? WHERE Id=?");
                        update.setString(1, sessionId);
                        update.setInt(2, userId);

                        if (update.executeUpdate()>0) {
                            identity = new Identity();
                            identity.setUsername(username);
                            identity.setUserId(userId);
                            identity.setSessionId(sessionId);
                        }

                        update.close();
                    }
                select.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return identity;
    }

    @Override
    public boolean sessionIdIsValid(int userId, String sessionId) {
        boolean valid = false;
        Connection conn = DBHelper.getConnection();

        if(conn!=null) {
            try {
                PreparedStatement select = conn.prepareStatement("SELECT SessionId FROM User WHERE Id=?");
                select.setInt(1, userId);
                ResultSet rs = select.executeQuery();
                while (rs.next())
                    if (rs.getString(1).equals(sessionId)) {
                        valid = true;
                    }
                select.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public void logoutUser(int userId) {
        Connection conn = DBHelper.getConnection();

        if(conn!=null) {
            try {
                PreparedStatement update = conn.prepareStatement("UPDATE User SET SessionId=NULL WHERE Id=?");
                update.setInt(1, userId);
                ResultSet rs = update.executeQuery();
                update.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
