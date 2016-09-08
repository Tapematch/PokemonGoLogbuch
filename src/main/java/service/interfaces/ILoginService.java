package service.interfaces;

import model.User;

import java.sql.SQLException;

public interface ILoginService {
    User loginUser(String username, String password) throws ReflectiveOperationException, SQLException;
    boolean sessionIdIsValid(String sessionId) throws ReflectiveOperationException, SQLException;
    void logoutUser(int userId) throws ReflectiveOperationException, SQLException;
}
