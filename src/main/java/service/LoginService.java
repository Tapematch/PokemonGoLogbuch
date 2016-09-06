package service;

import dto.Identity;
import service.interfaces.ILoginService;

public class LoginService implements ILoginService {
    @Override
    public Identity loginUser(String username, String password) {
        return null;
    }

    @Override
    public boolean sessionIdIsValid(String sessionId) {
        return false;
    }
}
