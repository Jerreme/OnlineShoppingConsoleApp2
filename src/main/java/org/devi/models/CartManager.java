package org.devi. models;

import org.devi.controllers.CredentialManager;
import org.devi.database.CartDb;

import java.util.ArrayList;

public class CartManager {
    private static final ArrayList<Product> cartItems = new ArrayList<>();

    public static ArrayList<Product> getCartItems(String username) {
        final CartDb cartDb = new CartDb();
        cartItems.clear();
        cartItems.addAll(cartDb.getCartItems(username));
        return cartItems;
    }

    public static void addToCart(String username, Product product) {
        cartItems.add(product);
        // Add product to the database
        final CartDb cartDb = new CartDb();
        cartDb.addProductToCart(username, product);
    }

    public static void emptyCart() {
        cartItems.clear();
        // Empty the cart in the database
        final CartDb cartDb = new CartDb();
        cartDb.emptyCart(CredentialManager.getLoggedInUser().username());
    }

    public static int getTotalPrice() {
        int totalPrice = 0;
        for (final Product product : cartItems) {
            totalPrice += product.price();
        }
        return totalPrice;
    }
}
