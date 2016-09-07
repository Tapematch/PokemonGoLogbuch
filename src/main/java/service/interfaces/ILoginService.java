package service.interfaces;

import dto.Identity;

import java.sql.SQLException;

public interface ILoginService {
    Identity loginUser(String username, String password) throws ReflectiveOperationException, SQLException;

    boolean sessionIdIsValid(int userId, String sessionId) throws ReflectiveOperationException, SQLException;

    void logoutUser(int userId) throws ReflectiveOperationException, SQLException;
}
