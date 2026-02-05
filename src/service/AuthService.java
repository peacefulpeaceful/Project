package service;

import repository.UserRepository;
import security.Role;
import security.UserSession;

public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final UserSession session = UserSession.getInstance();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void login(String username, String password) {
        Role role = userRepository.findRole(username, password);
        if (role == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        session.login(role);
    }
}

