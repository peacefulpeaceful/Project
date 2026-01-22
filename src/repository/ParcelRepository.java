package repository;

import data.IDB;
import model.Client;
import model.Parcel;
import model.ParcelStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParcelRepository {

    private final IDB db;

    public ParcelRepository(IDB db) {
        this.db = db;
    }

    public void save(Parcel p) {
        String sql = "INSERT INTO posilka(type, weight,cost, sender_name, sender_surname, sender_adress, receiver_name, receiver_adress, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, "REGULAR");
            st.setDouble(2, p.getWeight());
            st.setDouble(3, p.getCost());

            st.setString(4, p.getSender().getName());
            st.setString(5, p.getSender().getSurname());
            st.setString(6, p.getSender().getAddress());

            st.setString(7, p.getRecipient().getName());
            st.setString(8, p.getRecipient().getAddress());

            st.setString(9, p.getStatus().name());

            st.executeUpdate();


            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }


    public List<Parcel> getAll() {
        List<Parcel> parcels = new ArrayList<>();
        String sql = "SELECT * FROM posilka ORDER BY id";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                parcels.add(mapRow(rs));
            }

            return parcels;

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }


    public Parcel getById(int id) {
        String sql = "SELECT * FROM posilka WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }


    public void updateStatus(int id, ParcelStatus status) {
        String sql = "UPDATE posilka SET status = ? WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, status.name());
            st.setInt(2, id);
            st.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }


    private Parcel mapRow(ResultSet rs) throws Exception {
        Parcel p = new Parcel();
        p.setId(rs.getInt("id"));
        p.setWeight(rs.getDouble("weight"));
        p.setCost(rs.getDouble("cost"));
        p.setStatus(ParcelStatus.valueOf(rs.getString("status")));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        Client sender = new Client();
        sender.setName(rs.getString("sender_name"));
        sender.setSurname(rs.getString("sender_surname"));
        sender.setAddress(rs.getString("sender_adress"));

        Client receiver = new Client();
        receiver.setName(rs.getString("receiver_name"));
        receiver.setAddress(rs.getString("receiver_adress"));

        p.setSender(sender);
        p.setRecipient(receiver);


        return p;
    }
}
