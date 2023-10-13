package org.devi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.devi.views.Warn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseManager {
    private static DataSource dataSource = null;

//    static {
//        try {
//            dataSource = CloudSqlConnectionPoolFactory.createDataSource();
//        } catch (Exception e) {
//            Warn.debugMessage(e.getMessage());
//            dataSource = null;
//        }
//    }

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
    private static final String INSTANCE_CONNECTION_NAME = "shaped-shuttle-401611:asia-southeast1:demo-sql-app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_NAME = "online_shopping";


    protected static DataSource createDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
        config.setUsername(DB_USER);
        config.setPassword(DB_PASS);

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

        config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
        config.setConnectionTimeout(1500);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(30000);

        return new HikariDataSource(config);
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
                CREATE TABLE IF NOT EXISTS admins (
                	username VARCHAR(255) PRIMARY KEY NOT NULL,
                	password VARCHAR(255) NOT NULL
                )
                """);
    }

    public static void createUserTable() {
        createTable("""
                CREATE TABLE IF NOT EXISTS users (
                	username VARCHAR(255) PRIMARY KEY NOT NULL,
                	password VARCHAR(255) NOT NULL,
                	balance INT NOT NULL
                )
                """);
    }

    public static void createCartTable() {
        createTable("""
                CREATE TABLE IF NOT EXISTS cart (
                	username VARCHAR(255) NOT NULL,
                	product_key INT NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL
                )
                """);
    }

    public static void createProductTable() {
        createTable("""
                CREATE TABLE IF NOT EXISTS products (
                	product_key INT PRIMARY KEY NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL
                )
                """);
    }

    public static void createPurchasedTable() {
        createTable("""
                CREATE TABLE IF NOT EXISTS purchased (
                	username VARCHAR(255) NOT NULL,
                	product_key INT NOT NULL,
                	product_name VARCHAR(255) NOT NULL,
                	product_price INT NOT NULL,
                	date VARCHAR(255) NOT NULL
                )
                """);
    }
}