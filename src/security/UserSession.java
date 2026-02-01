package security;

import security.Role;

import java.util.EnumMap;
import java.util.Map;

public class UserSession implements  IUserSession{

    private Role currentRole = Role.EDITOR;
    private final Map<Role, String> rolePasswords = new EnumMap<>(Role.class);

    private UserSession(){
        rolePasswords.put(Role.ADMIN, "ULTRA_GOD_PASSWORD");
        rolePasswords.put(Role.MANAGER, "676767");
        rolePasswords.put(Role.EDITOR, "notEDITOR");

    }

    private static class UserSessionHolder {
        private static final UserSession INSTANCE = new UserSession();
    }

    public static UserSession getInstance(){
        return UserSessionHolder.INSTANCE;
    }

    public Role getCurrentRole(){
        return currentRole;
    }
    public void login(Role role, String password){
        if(role == null){
            throw new IllegalArgumentException("Role must be chosen");
        }
        String expected = rolePasswords.get(role);
        if (expected == null || password == null || !expected.equals(password)){
            throw new IllegalArgumentException("Invalid credential for role " + role);
        }
        this.currentRole = role;
    }
}
