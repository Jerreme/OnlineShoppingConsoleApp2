package org.devi.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.devi.views.Warn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseManager {
    private static DataSource dataSource = null;

    public static Connection getConnection() throws Exception {
        if (dataSource == null) dataSource = CloudSqlConnectionPoolFactory.createDataSource();
        return dataSource.getConnection();
    }

    public static void init() {
        // Create Tables if not exists
        TablesDb.createAdminTable();
        TablesDb.createUserTable();
        TablesDb.createCartTable();
        TablesDb.createProductTable();
        TablesDb.createPurchasedTable();
    }

}


class CloudSqlConnectionPoolFactory {
    private static final String DB_USER = "applogin";
    private static final String DB_PASS = "d2sd$asdAsaAQ";
    private static final String DB_NAME = "online_shopping";
    private static final String SERVER_NAME = "devi-server.database.windows.net";
    private static final int PORT = 1433;
    private static final int TIMEOUT = 2; // in seconds


    protected static DataSource createDataSource() {
        final SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(DB_USER);
        ds.setPassword(DB_PASS);
        ds.setServerName(SERVER_NAME);
        ds.setPortNumber(PORT);
        ds.setDatabaseName(DB_NAME);
        ds.setQueryTimeout(TIMEOUT);
        return ds;
    }
}

class TablesDb {

    private static void createTable(String query) {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public static void createAdminTable() {
        createTable("""
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='admins' AND xtype='U')
                CREATE TABLE admins (
                	username VARCHAR(255) PRIMARY KEY NOT NULL,
                	password VARCHAR(255) NOT NULL
                )
                """);
    }

    public static void createUserTable() {
        createTable("""
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='users' AND xtype='U')
                CREATE TABLE users (
                	username VARCHAR(255) PRIMARY KEY NOT NULL,
                	password VARCHAR(255) NOT NULL,
                	balance INT NOT NULL
                )
                """);
    }

    public static void createCartTable() {
        createTable("""
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='cart' AND xtype='U')
                CREATE TABLE cart (
                	username VARCHAR(255) NOT NULL,
                	product_key INT NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL
                )
                """);
    }

    public static void createProductTable() {
        createTable("""
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='products' AND xtype='U')
                CREATE TABLE products (
                	product_key INT PRIMARY KEY NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL
                )
                """);
    }

    public static void createPurchasedTable() {
        createTable("""
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='purchased' AND xtype='U')
                CREATE TABLE purchased (
                	username VARCHAR(255) NOT NULL,
                	product_key INT NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL,
                	date VARCHAR(255) NOT NULL
                )
                """);
    }
}