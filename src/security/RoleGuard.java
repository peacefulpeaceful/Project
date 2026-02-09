package security;

import security.Role;

import java.util.EnumSet;

public class RoleGuard implements IRoleGuard {
    private static final RoleGuard INSTANCE = new RoleGuard();

    private RoleGuard() {
    }

    public static RoleGuard getInstance() {
        return INSTANCE;
    }

    @Override
    public void requireAny(Role role, Role... allowed) {
        EnumSet<Role> allowedSet = EnumSet.noneOf(Role.class);
        for (Role allowedRole : allowed) {
            allowedSet.add(allowedRole);
        }
        if (!allowedSet.contains(role)) {
            throw new IllegalStateException("Access denied for role: " + role);
        }
    }
}
