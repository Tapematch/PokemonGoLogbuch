package service.interfaces;

import model.User;

import java.sql.SQLException;

public interface IUserService {
    int getUserIdBySessionId(String sessionId) throws SQLException, ReflectiveOperationException;
    User getUserById(int userId) throws SQLException, ReflectiveOperationException;
}
