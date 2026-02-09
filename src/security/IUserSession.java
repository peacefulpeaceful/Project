package security;

import security.Role;

public interface IUserSession {
    Role getCurrentRole();
    void login(Role role, String password);

}
