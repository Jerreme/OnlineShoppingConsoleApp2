package org.devi. controllers;

import org.devi.models.Product;
import org.devi.views.OnlineShoppingView;

import java.util.ArrayList;
import java.util.Scanner;

public class OnlineShoppingController {
    private final ArrayList<Product> shoppingList;
    private final OnlineShoppingView onlineShoppingView;

    public OnlineShoppingController(ArrayList<Product> shoppingList, OnlineShoppingView groceryStoreView) {
        this.shoppingList = shoppingList;
        this.onlineShoppingView = groceryStoreView;
    }

    public static ArrayList<Product> generateProductsFromArray(String[] productNames, int[] productPrices) {
        final ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < productNames.length; i++) {
            products.add(new Product(i, productNames[i], productPrices[i]));
        }
        return products;
    }

    public void showShoppingList() {
        onlineShoppingView.showShoppingList(shoppingList);
    }

    public void closingSpiel() {
        onlineShoppingView.closingSpiel();
    }

    public int promptOrderId() {
        int toBuy = 0;
        do {
            final Scanner scanner = new Scanner(System.in);
            onlineShoppingView.askForOrder(shoppingList.size());

            try {
                toBuy = scanner.nextInt();
            } catch (Exception e) {
                onlineShoppingView.warnForInvalidInput();
            }
        } while (toBuy == 0);
        return toBuy;
    }

    public int promptQuantity() {
        int quantity = 0;
        do {
            final Scanner scanner = new Scanner(System.in);
            onlineShoppingView.askForQuantity();

            try {
                quantity = scanner.nextInt();
            } catch (Exception e) {
                onlineShoppingView.warnForInvalidInput();
            }
        } while (quantity == 0);
        return quantity;
    }

    public void displayOrder(int orderId, int quantity) {
        // Guard Clause
        if (orderId == 0 || quantity == 0) {
            onlineShoppingView.warnForInvalidOrder();
            return;
        }
        onlineShoppingView.displayOrder(orderId, quantity, shoppingList);
    }

}