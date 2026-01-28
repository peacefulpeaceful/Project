package security;

public class AccessManager {
    public boolean canChangeStatus(Role role){
        return role == Role.MANAGER || role == Role.ADMIN;
    }
}
