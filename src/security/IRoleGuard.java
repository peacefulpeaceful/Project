package security;

import security.Role;

public interface IRoleGuard {
    void requireAny(Role role, Role... allowed);
}
