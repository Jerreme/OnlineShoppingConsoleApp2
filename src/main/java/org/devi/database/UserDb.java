package org.devi.database;

import org.devi.interfaces.Credential;
import org.devi.models.Admin;
import org.devi.models.User;
import org.devi.views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDb {

    public void addUser(User user) {
        final String sql = "INSERT INTO users(username, password, balance) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, user.username());
            pstmt.setString(2, user.password());
            pstmt.setInt(3, user.balance());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void addAdmin(Admin admin) {
        final String sql = "INSERT INTO admins(username, password) VALUES(?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, admin.username());
            pstmt.setString(2, admin.password());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public User getUser(Credential credential) {
        final String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, credential.username());
            pstmt.setString(2, credential.password());
            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("balance")
                );
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return user;
    }

    public ArrayList<User> getAllUsers() {
        final String sql = "SELECT * FROM users";
        final ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("balance")
                ));
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return users;
    }


    public ArrayList<Admin> getAllAdmins() {
        final String sql = "SELECT * FROM admins";
        final ArrayList<Admin> admins = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            final ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                admins.add(new Admin(
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return admins;
    }


    public Admin getAdmin(Credential credential) {
        final String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        Admin admin = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, credential.username());
            pstmt.setString(2, credential.password());
            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return admin;
    }

    public boolean isUserAlreadyExist(String username) {
        boolean exists = true;

        try (Connection conn = DatabaseManager.getConnection()) {
            final String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                final ResultSet rs = pstmt.executeQuery();
                exists = rs.next();
            } catch (Exception e) {
                Warn.debugMessage(e.getMessage());
            }

            final String sql2 = "SELECT * FROM admins WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                pstmt.setString(1, username);
                final ResultSet rs = pstmt.executeQuery();
                exists = rs.next();
            } catch (Exception e) {
                Warn.debugMessage(e.getMessage());
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }

        return exists;
    }

    public void updateUserBalance(User user) {
        final String sql = "UPDATE users SET balance = ? WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, user.balance());
            pstmt.setString(2, user.username());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }
}
