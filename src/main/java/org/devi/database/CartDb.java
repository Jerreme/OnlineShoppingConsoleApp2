package org.devi.database;

import org.devi.models.Product;
import org.devi.views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDb {

    public ArrayList<Product> getCartItems(String username) {
        final String sql = "SELECT * FROM cart WHERE username = ?";
        final ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            final ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_key"),
                        rs.getString("product_name"),
                        rs.getInt("product_price")
                ));
            }
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return products;
    }

    public void addProductToCart(String username, Product product) {
        String sql = "INSERT INTO cart(username, product_key, product_name, product_price) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.setInt(2, product.key());
            pstmt.setString(3, product.productName());
            pstmt.setInt(4, product.price());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void emptyCart(String username) {
        String sql = "DELETE FROM cart WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }
}
