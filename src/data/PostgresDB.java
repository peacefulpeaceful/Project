package data;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDB implements IDB {
    private final String url;
    private final String user;
    private final String password;

    public PostgresDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password; 
    }

    @Override
    public Connection getConnection() throws Exception {

        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void close() {
    }
}