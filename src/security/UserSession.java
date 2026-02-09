package security;

import security.Role;

public class UserSession implements IUserSession {

    private Role currentRole = Role.EDITOR;

    private UserSession() {
    }

    private static class UserSessionHolder {
        private static final UserSession INSTANCE = new UserSession();
    }

    public static UserSession getInstance() {
        return UserSessionHolder.INSTANCE;
    }

    public Role getCurrentRole() {
        return currentRole;
    }

    public void login(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role must be provided");
        }
        this.currentRole = role;
    }
}

