package org.devi. controllers;

import org.devi.database.PurchasedDb;
import org.devi.interfaces.ExitCode;
import org.devi.interfaces.UserBalanceException;
import org.devi.models.CartManager;
import org.devi.models.Product;
import org.devi.models.PurchasedLog;
import org.devi.models.User;
import org.devi.views.ProductsPageView;
import org.devi.views.Warn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ProductsPageController {

    private final ProductsPageView view;

    public ProductsPageController(ProductsPageView view) {
        this.view = view;
    }

    public void showProducts() {
        view.showProducts(ProductsManager.getProducts());
        Navigator.rebuildActiveRoute();
    }

    public void addToCart() {
        int productId = promptProductId();
        if (productId == 0) {
            Navigator.rebuildActiveRoute();
            return;
        }

        final Product product = ProductsManager.getProductById(productId);
        if (product == null) {
            view.showProductNotFound();
        } else {
            final String username = CredentialManager.getLoggedInUser().username();
            CartManager.addToCart(username, product);
            view.showAddedToCart(product);
        }
        Navigator.rebuildActiveRoute();
    }

    public void checkout() {
        final String username = CredentialManager.getLoggedInUser().username();
        ArrayList<Product> cartItems = CartManager.getCartItems(username);
        if (cartItems.isEmpty()) {
            view.showEmptyCart();
            Navigator.rebuildActiveRoute();
            return;
        }
        view.showCartItems(cartItems);
        final int balance = CredentialManager.getLoggedInUser().getWalletBalance();
        view.showWalletBalance(balance);
        view.showCartOptions();

        Tasker tasker = new Tasker(this.toString());
        tasker.addTask(1, this::placeOrder);
        tasker.addTask(2, this::emptyCart);
        tasker.addTask(0, null);
        tasker.runPrompt();
        Navigator.rebuildActiveRoute();
    }

    private int promptProductId() {
        final Scanner scanner = new Scanner(System.in);
        view.askForOrder(ProductsManager.getProducts().size());
        int productId;
        try {
            productId = scanner.nextInt();
        } catch (Exception e) {
            Warn.invalidInput();
            productId = promptProductId();
        }
        return productId;
    }

    private void placeOrder() {
        final User currentUser = CredentialManager.getLoggedInUser();
        if (currentUser == null) {
            Warn.debugMessageAndExit("No user logged in!", ExitCode.EXIT_FAILURE);
            return;
        }

        try {
            // update user balance
            final int totalPrice = CartManager.getTotalPrice();
            CredentialManager.updateUser(currentUser.spend(totalPrice));
            view.systemMessage("Products has been checked out ✓");

            final int newBalance = CredentialManager.getLoggedInUser().getWalletBalance();
            view.showWalletBalance(newBalance);

            // Get current date
            final Date date = new Date();
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String dateString = formatter.format(date);

            // Create purchased log
            final PurchasedLog purchase = new PurchasedLog(
                    currentUser.username(),
                    CartManager.getCartItems(currentUser.username()),
                    dateString
            );

            // add to db of completed orders
            final PurchasedDb db = new PurchasedDb();
            db.addPurchased(purchase);

            // finally empty the cart
            CartManager.emptyCart();
        } catch (UserBalanceException e) {
            view.warnInsufficientMoney(e.getMessage());
        }
    }

    private void emptyCart() {
        CartManager.emptyCart();
        view.systemMessage("Cart has been emptied ✓");
    }

}
