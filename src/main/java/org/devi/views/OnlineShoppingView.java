package org.devi. views;

import org.devi.models.Product;

import java.util.ArrayList;

public class OnlineShoppingView {
    private final String ownerName;
    private final String systemName = "System";

    public OnlineShoppingView(String name) {
        this.ownerName = name;
    }

    public void showShoppingList(ArrayList<Product> products) {
        if (products.isEmpty()) {
            warnForEmptyProducts();
            return;
        }

        System.out.println("--- Welcome to " + ownerName.toUpperCase() + "'s Grocery Store ---\n");
        System.out.println("Here is the list of items we have in stock:");
        for (Product product : products) {
            String formatted = String.format("[%s] %s ₱%s", product.key(), product.productName(), product.price());
            System.out.println(formatted);
        }
        System.out.println();
    }

    private void displayMessage(String message, String prefix) {
        prefix = prefix.toUpperCase();
        if (prefix.equals(systemName.toUpperCase())) {
            System.out.println("<" + prefix + "> " + message);
        } else {
            System.out.print("<" + prefix + "> " + message);
        }

    }

    public void warnForInvalidInput() {
        displayMessage("Invalid Input!", systemName);
    }

    public void warnForEmptyProducts() {
        displayMessage("No products available!", systemName);
    }

    public void warnForInvalidOrder() {
        displayMessage("Order is invalid!", systemName);
    }

    public void askForOrder(int productsCount) {
        displayMessage(" What would you like to buy ", ownerName);
        System.out.print(productsCount > 0 ? String.format("[%d-%d]: ", 1, productsCount) : "[Empty]: ");
    }

    public void askForQuantity() {
        displayMessage("Enter the quantity: ", ownerName);
    }


    public void displayOrder(int orderId, int quantity, ArrayList<Product> products) {
        // Try to find the product if present in the product lists
        Product selectedProduct = null;
        for (Product product : products) {
            if (product.key() == orderId) {
                selectedProduct = product;
                break;
            }
        }

        // Guard Clause
        if (selectedProduct == null) {
            warnForInvalidOrder();
            return;
        }

        displayMessage(" Your order is " + selectedProduct.productName() + "\n", systemName);

        System.out.println("---------- Receipt ----------");
        System.out.println("product:  " + selectedProduct.productName());
        System.out.println("price:  ₱" + selectedProduct.price());
        System.out.println("quantity:  " + quantity);
        final int totalAmount = selectedProduct.price() * quantity;
        System.out.println("Total amount to pay: ₱" + totalAmount);
        System.out.println("-----------------------------");
    }

    public void closingSpiel() {
        System.out.println();
        System.out.println("Thank you for shopping at " + ownerName.toUpperCase() + "'s Grocery Store!");
        System.out.println("--- We hope to see you again soon! ---");
    }
}
