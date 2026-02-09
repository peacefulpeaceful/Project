package repository;

import data.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class BlacklistRepository {
    private final IDB db;

    public BlacklistRepository(IDB db) {
        this.db = db;
    }

    public void add(int clientId, String reason) {
        String sql = "INSERT INTO blacklist(client_id, reason, created_at) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, clientId);
            st.setString(2, reason);
            st.setObject(3, LocalDateTime.now());
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }

    public boolean isBlacklisted(int clientId) {
        String sql = "SELECT 1 FROM blacklist WHERE client_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, clientId);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }
}
