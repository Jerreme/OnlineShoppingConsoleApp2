package org.devi. views;

import org.devi.interfaces.Messenger;
import org.devi.models.Admin;
import org.devi.models.Product;
import org.devi.models.User;

import java.util.ArrayList;

public class AdminPageView extends Messenger {
    public void showAdminOptions() {
        printHeader("ADMIN PAGE");
        println("[1] Show Products".toUpperCase());
        println("[2] Add Product".toUpperCase());
        println("[3] Modify Product".toUpperCase());
        println("[4] Delete Product".toUpperCase());
        println("[5] Delete All Product".toUpperCase());
        println("[6] Show Registered Users".toUpperCase());
        println("[7] Show Registered Admins".toUpperCase());
        println("[0] Logout".toUpperCase());
    }

    public void welcomeAdmin(String username) {
        systemMessage("WELCOME " + username.toUpperCase() + "!");
    }

    public void showLogoutMessage() {
        systemMessage("You have been logged out.".toUpperCase());
    }

    public void showAddProductPrompt() {
        printHeader("ADD PRODUCT");
    }

    public void showModifyProductPrompt() {
        printHeader("MODIFY PRODUCT");
    }

    public void showDeleteProductPrompt() {
        printHeader("DELETE PRODUCT");
    }

    public void showDeleteAllProductsPrompt() {
        printHeader("DELETE ALL PRODUCTS");
    }

    public void showDeleteAllProductsConfirmation(String key) {
        println(String.format("[%s] Type %s to confirm deletion.", key, key));
        print(">> ");
    }

    public void showRegisteredUsers(ArrayList<User> registeredUsers) {
        printHeader("REGISTERED USERS");
        if (registeredUsers.isEmpty()) {
            systemMessage("No registered users.");
            return;
        }
        for (User user : registeredUsers) {
            println(" · " + user.username());
        }
    }

    public void showRegisteredAdmins(ArrayList<Admin> registeredAdmins) {
        printHeader("REGISTERED ADMINS");
        if (registeredAdmins.isEmpty()) {
            systemMessage("No registered admins.");
            return;
        }
        for (Admin admin : registeredAdmins) {
            println(" · " + admin.username());
        }
    }

    public void askForProductKey(int productsCount) {
        print(productsCount > 0 ? String.format("[%d-%d]", 1, productsCount) : "[Empty]");
        println(" Choose Product");
        println("[0] Cancel");
        print(">> ");
    }

    public void showProductName() {
        print("Enter product name: ");
    }

    public void showProductPrice() {
        print("Enter product price: ");
    }

    public void showInvalidProduct() {
        systemMessage("Invalid product details.".toUpperCase());
    }

    public void showProductNotFound() {
        systemMessage("Product not found.".toUpperCase());
    }

    public void showProductAddedSuccessfully() {
        systemMessage("Product added successfully.".toUpperCase());
    }

    public void showAddingProductAborted() {
        systemMessage("Product Aborted.".toUpperCase());
    }

    public void showDeletingProductAborted() {
        systemMessage("Invalid!, Deletion Aborted.".toUpperCase());
    }

    public void showOldProduct(Product oldProduct) {
        final String formatted = String.format(" - %s%s₱%s",
                oldProduct.productName(), generateSpaces(oldProduct.productName().length() + 1), oldProduct.price());
        println(formatted);
    }

    public void showProductConfirmation(Product product) {
        final String formatted = String.format(" + %s%s₱%s",
                product.productName(), generateSpaces(product.productName().length() + 1), product.price());
        println(formatted);
        print("Confirm product details? [Y/N]: ");
    }

    public void showProductDeletionConfirmation() {
        print("Confirm product deletion? [Y/N]: ");
    }

    public void showProductAlreadyExists() {
        systemMessage("Product already exists.".toUpperCase());
    }

    public void showProductModifiedSuccessfully() {
        systemMessage("Product modified successfully.".toUpperCase());
    }

    public void showProductDeletedSuccessfully() {
        systemMessage("Product has been deleted successfully.".toUpperCase());
    }

    public void showAllProductsDeletedSuccessfully() {
        systemMessage("All products has been deleted successfully.".toUpperCase());
    }

    public void showEmptyProducts() {
        systemMessage("Products is empty!");
    }
}
