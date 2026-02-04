package security;

public interface IUserSession {
    Role getCurrentRole();
    void login(Role role, String password);

}
