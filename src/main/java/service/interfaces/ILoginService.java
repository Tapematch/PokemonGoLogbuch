package service.interfaces;

import dto.Identity;

public interface ILoginService {
    Identity loginUser(String username, String password);

    boolean sessionIdIsValid(int userId, String sessionId);

    void logoutUser(int userId);
}
