package org.devi.database;

import org.devi.models.Product;
import org.devi.views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDb {

    public void clearProducts() {
        final String sql = "DELETE FROM products";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void addProducts(ArrayList<Product> products) {
        final String sql = "INSERT INTO products(product_key, product_name, product_price) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            for (Product product : products) {
                pstmt.setInt(1, product.key());
                pstmt.setString(2, product.productName());
                pstmt.setInt(3, product.price());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void addProduct(Product product) {
        final String sql = "INSERT INTO products(product_key,product_name, product_price) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.key());
            pstmt.setString(2, product.productName().toLowerCase());
            pstmt.setInt(3, product.price());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void modifyProduct(Product product) {
        final String sql = "UPDATE products SET product_name = ?, product_price = ? WHERE product_key = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, product.productName());
            pstmt.setInt(2, product.price());
            pstmt.setInt(3, product.key());
            pstmt.executeUpdate();

        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public void deleteProduct(Product product) {
        final String sql = "DELETE FROM products WHERE product_key = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, product.key());
            pstmt.executeUpdate();
        } catch (Exception e) {
            Warn.databaseError(e);
        }
    }

    public ArrayList<Product> getProducts() {
        final String sql = "SELECT * FROM products ORDER BY product_key ASC";
        final ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            ResultSet rs = pstmt.executeQuery();
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

    public int getHighestProductId() {
        final String sql = "SELECT MAX(product_key) as highest_id FROM products";
        int maxId = 1;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) maxId = rs.getInt("highest_id");
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return maxId;
    }

    public boolean isProductExist(String productName) {
        final String sql = "SELECT * FROM products WHERE product_name = ?";
        boolean isExist = false;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) isExist = true;
        } catch (Exception e) {
            Warn.databaseError(e);
        }
        return isExist;
    }
}
