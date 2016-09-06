package service.interfaces;

import dto.Identity;

public interface ILoginService {
    Identity loginUser(String username, String password);

    boolean sessionIdIsValid(String sessionId);
}
