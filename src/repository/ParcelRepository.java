package repository;

import data.IDB;
import model.Client;
import model.Parcel;
import model.ParcelCategory;
import model.ParcelStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParcelRepository implements IParcelRepository{

    private final IDB db;

    public ParcelRepository(IDB db) {
        this.db = db;
    }
    @Override
    public void save(Parcel p, int senderId, int receiverId) {
        String sql = "INSERT INTO posilka(type, weight, cost, sender_id, receiver_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, p.getCategory().name());
            st.setDouble(2, p.getWeight());
            st.setDouble(3, p.getCost());

            st.setInt(4, senderId);
            st.setInt(5, receiverId);
            st.setString(6, p.getStatus().name());

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

    @Override
    public List<Parcel> getAll(){
        List<Parcel> parcels = new ArrayList<>();
        String sql = "SELECT p.id AS parcel_id, p.type AS parcel_type, p.weight AS parcel_weight, " +
                "p.cost AS parcel_cost, p.status AS parcel_status, p.created_at AS parcel_created_at, " +
                "s.id AS sender_id, s.name AS sender_name, s.surname AS sender_surname, s.adress AS sender_adress, " +
                "r.id AS receiver_id, r.name AS receiver_name, r.surname AS receiver_surname, r.adress AS receiver_adress " +
                "FROM posilka p " +
                "JOIN client s ON p.sender_id = s.id " +
                "JOIN client r ON p.receiver_id = r.id " +
                "ORDER BY p.id";


        try(Connection con = db.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()){
            while (rs.next()){
                parcels.add(mapRow(rs));
            }
            return parcels;
        }catch (Exception e){
            throw new RuntimeException("DB error: " + e.getMessage());
        }
    }

    @Override
            public Parcel getById(int id) {
                String sql =  "SELECT p.id AS parcel_id, p.type AS parcel_type, p.weight AS parcel_weight, " +
                        "       p.cost AS parcel_cost, p.status AS parcel_status, p.created_at AS parcel_created_at, " +
                        "       s.id AS sender_id, s.name AS sender_name, s.surname AS sender_surname, s.adress AS sender_adress, " +
                        "       r.id AS receiver_id, r.name AS receiver_name, r.surname AS receiver_surname, r.adress AS receiver_adress " +
                        "FROM posilka p " +
                        "JOIN client s ON p.sender_id = s.id " +
                        "JOIN client r ON p.receiver_id = r.id " +
                        "WHERE p.id = ?";


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

        @Override
        public Parcel getFullById(int id) {
                return getById(id);
        }

        @Override
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

                p.setId(rs.getInt("parcel_id"));
                p.setWeight(rs.getDouble("parcel_weight"));
                p.setCost(rs.getDouble("parcel_cost"));
                p.setStatus(ParcelStatus.valueOf(rs.getString("parcel_status")));
                p.setCategory(ParcelCategory.valueOf(rs.getString("parcel_type")));
                p.setCreatedAt(rs.getTimestamp("parcel_created_at").toLocalDateTime());

                Client sender = new Client();
                sender.setId(rs.getInt("sender_id"));
                sender.setName(rs.getString("sender_name"));
                sender.setSurname(rs.getString("sender_surname"));
                sender.setAddress(rs.getString("sender_adress"));

                Client receiver = new Client();
                receiver.setId(rs.getInt("receiver_id"));
                receiver.setName(rs.getString("receiver_name"));
                receiver.setSurname(rs.getString("receiver_surname"));
                receiver.setAddress(rs.getString("receiver_adress"));


                p.setSender(sender);
                p.setRecipient(receiver);


                return p;
            }
        }