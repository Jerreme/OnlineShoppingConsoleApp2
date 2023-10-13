package org.devi.views;

import org.devi.interfaces.Messenger;
import org.devi.models.Product;

import java.util.ArrayList;

public class ProductsPageView extends Messenger {
    public void showProductsPage() {
        printHeader("Buy");
        println("[1] Show Products");
        println("[2] Add To Cart");
        println("[3] Check Out");
        println("[0] Back");
    }

    public void showProducts(ArrayList<Product> products) {
        if (products.isEmpty()) {
            showEmptyProducts();
            return;
        }
        printHeader("List of Products");
        int i = 1;
        for (Product product : products) {
            final String productName = product.productName().substring(0, 1).toUpperCase() +
                                       product.productName().substring(1);
            String formatted = String.format("[%s] %s ₱%s",
                    product.key(), productName, product.price());
            final int count = formatted.length() > 23 ? 1 : 25 - formatted.length();
            if (i % 3 == 0) println(formatted);
            else print(formatted + " ".repeat(count));
            i++;
        }
        newLine();
    }

    public void askForOrder(int productsCount) {
        printHeader("Add to Cart");
        print(productsCount > 0 ? String.format("[%d-%d]", 1, productsCount) : "[Empty]");
        println(" Choose Product");
        println("[0] Cancel");
        print(">> ");
    }

    public void showProductNotFound() {
        systemMessage("Product not found!");
    }

    public void showAddedToCart(Product product) {
        systemMessage(String.format("%s has been added to cart ✓", product.productName()));
    }

    public void showCartItems(ArrayList<Product> cartItems) {
        printHeader("Cart Items");
        int totalPrice = 0;
        for (Product product : cartItems) {
            totalPrice += product.price();
            final String name = product.productName();
            println(String.format(" · %s%s₱%s",
                    name, generateSpaces(name.length() + 1), product.price()));
        }
        showTotalPrice(totalPrice);
        printDashSeparator();
    }

    public void showTotalPrice(int totalPrice) {
        final String label = "Total Price:";
        println(String.format("%s%s  ₱%s", label, generateSpaces(label.length()), totalPrice));
    }

    public void showEmptyCart() {
        systemMessage("Cart is empty!");
    }

    public void showEmptyProducts() {
        systemMessage("Products is empty!");
    }

    public void warnInsufficientMoney(String remainingMoney) {
        systemMessage("Insufficient funds!");
        systemMessage(remainingMoney);
    }

    public void showCartOptions() {
        println("[1] Place Order");
        println("[2] Empty Cart");
        println("[0] Back");
    }

    public void showWalletBalance(int walletBalance) {
        final String label = "E-Wallet Balance:";
        println(String.format("%s%s  ₱%s", label, generateSpaces(label.length()), walletBalance));
    }


}
