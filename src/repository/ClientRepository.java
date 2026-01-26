package repository;

import data.IDB;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientRepository implements IClientRepository{
    private final IDB db;

    public ClientRepository(IDB db){
        this.db = db;
    }
    @Override
    public int save(Client client) throws Exception{
        String sql = "INSERT INTO client(name, surname, adress)"
        + "VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){

            st.setString(1, client.getName());
            st.setString(2, client.getSurname());
            st.setString(3, client.getAddress());

            st.executeUpdate();

            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch(Exception e){
            throw new RuntimeException("DB error: " + e.getMessage());
    }
        throw new RuntimeException("Client not saved")
    }
}
