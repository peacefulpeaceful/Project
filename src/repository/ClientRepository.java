package repository;

import data.IDB;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientRepository implements IClientRepository {

    private final IDB db;

    public ClientRepository(IDB db) {
        this.db = db;
    }

    @Override
    public int save(Client client) throws Exception {
        String sql = "INSERT INTO client(name, surname, adress) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            st.setString(1, client.getName());
            st.setString(2, client.getSurname());
            st.setString(3, client.getAddress());

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }

        throw new RuntimeException("Client not saved");
    }

    @Override
    public Client findByNameSurname(String name, String surname) throws Exception {
        String sql = "SELECT id, name, surname, adress FROM client WHERE name = ? AND surname = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, name);
            st.setString(2, surname);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setSurname(rs.getString("surname"));
                    client.setAddress(rs.getString("adress"));
                    return client;
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }
}
