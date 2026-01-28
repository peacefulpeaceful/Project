package security;

import model.Role;

import java.util.List;
import java.util.Map;

public class UserSession {
    private final static UserSession INSTANCE = new UserSession();

    private Role currentRole = Role.EDITOR;
    private final Map<Role, String> rolePasswords = new EnumMap<Role.class>;

    private UserSession(){
        rolePasswords.put(Role.ADMIN, "YA_100%_NE_ADMIN");
        rolePassword.put(Role.MANAGER, "676767");
        rolePassword.put(Role.EDITOR, "KAVABANGA");


    }
    public static UserSession getInstance(){
        return INSTANCE;
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
