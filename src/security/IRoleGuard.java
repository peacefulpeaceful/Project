package security;

public interface IRoleGuard {
    void requireAny(Role role, Role... allowed);
}
