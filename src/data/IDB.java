package data;

import java.sql.Connection;

public interface IDB {
    Connection getConnection() throws Exception;
    void close();
}