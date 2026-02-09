package repository;

import data.IDB;
import security.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    public Role findRole(String username, String password) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Role.valueOf(rs.getString("role").toUpperCase());
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }
}