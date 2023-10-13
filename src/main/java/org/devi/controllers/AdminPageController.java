package org.devi. controllers;

import org.devi.models.Admin;
import org.devi.models.Product;
import org.devi.models.User;
import org.devi.views.AdminPageView;
import org.devi.views.ProductsPageView;
import org.devi.views.Warn;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminPageController {
    final AdminPageView view;

    public AdminPageController(AdminPageView view) {
        this.view = view;
    }

    public void showProducts() {
        if (isProductsEmpty()) return;
        new ProductsPageView().showProducts(ProductsManager.getProducts());
        Navigator.rebuildActiveRoute();
    }

    public void showRegisteredUsers() {
        final CredentialManager credentialManager = new CredentialManager();
        final ArrayList<User> registeredUsers = credentialManager.getAllUsers();
        view.showRegisteredUsers(registeredUsers);
        Navigator.rebuildActiveRoute();
    }

    public void showRegisteredAdmins() {
        final CredentialManager credentialManager = new CredentialManager();
        final ArrayList<Admin> registeredAdmins = credentialManager.getAllAdmins();
        view.showRegisteredAdmins(registeredAdmins);
        Navigator.rebuildActiveRoute();
    }

    public void addProduct() {
        view.showAddProductPrompt();
        final Product newProduct = promptNewProduct();
        if (!isProductDetailsValid(null, newProduct)) return;

        ProductsManager.addProduct(newProduct);
        view.showProductAddedSuccessfully();
        Navigator.rebuildActiveRoute();
    }

    private boolean isProductsEmpty() {
        if (ProductsManager.getProducts().isEmpty()) {
            view.showEmptyProducts();
            Navigator.rebuildActiveRoute();
            return true;
        }
        return false;
    }

    public void modifyProduct() {
        if (isProductsEmpty()) return;
        view.showModifyProductPrompt();

        final int productId = promptProductKeyToModify();
        final Product productToModify = ProductsManager.getProductById(productId);
        if (productId == 0) {
            Navigator.rebuildActiveRoute();
            return;
        }

        view.newLine();
        final Product productDetails = promptNewProduct();
        if (!isProductDetailsValid(productToModify, productDetails)) return;

        assert productDetails != null;
        final Product newProduct = new Product(productId, productDetails.productName(), productDetails.price());
        ProductsManager.modifyProduct(newProduct);
        view.showProductModifiedSuccessfully();
        Navigator.rebuildActiveRoute();
    }

    public void deleteProduct() {
        if (isProductsEmpty()) return;
        view.showDeleteProductPrompt();

        final int productId = promptProductKeyToModify();
        final Product productToDelete = ProductsManager.getProductById(productId);
        if (productId == 0) {
            Navigator.rebuildActiveRoute();
            return;
        }

        if (productToDelete == null) {
            view.showProductNotFound();
            Navigator.rebuildActiveRoute();
            return;
        }

        view.newLine();
        if (!confirmDeleteProduct(productToDelete)) {
            view.showDeletingProductAborted();
            Navigator.rebuildActiveRoute();
            return;
        }

        ProductsManager.deleteProduct(productToDelete);
        ProductsManager.refreshProductsId();
        view.showProductDeletedSuccessfully();
        Navigator.rebuildActiveRoute();
    }

    public void deleteAllProducts() {
        if (isProductsEmpty()) return;
        view.showDeleteAllProductsPrompt();
        if (!confirmDeleteAllProducts()) {
            view.showDeletingProductAborted();
            Navigator.rebuildActiveRoute();
            return;
        }
        ProductsManager.clearProducts();
        view.showAllProductsDeletedSuccessfully();
        Navigator.rebuildActiveRoute();
    }

    private boolean confirmDeleteAllProducts() {
        final Scanner scanner = new Scanner(System.in);
        final String DELETE_ALL_KEY = "DELETE-ALL";
        view.showDeleteAllProductsConfirmation(DELETE_ALL_KEY);
        final String confirmation = scanner.nextLine().trim();
        return confirmation.equals(DELETE_ALL_KEY);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isProductDetailsValid(Product oldProduct, Product newProduct) {
        if (newProduct == null) {
            view.showInvalidProduct();
            Navigator.rebuildActiveRoute();
            return false;
        }

        if (!confirmProduct(oldProduct, newProduct)) {
            view.showAddingProductAborted();
            Navigator.rebuildActiveRoute();
            return false;
        }

        if (ProductsManager.isProductExists(newProduct.productName())) {
            view.showProductAlreadyExists();
            Navigator.rebuildActiveRoute();
            return false;
        }
        return true;
    }

    private int promptProductKeyToModify() {
        final Scanner scanner = new Scanner(System.in);
        view.askForProductKey(ProductsManager.getProducts().size());
        int productId;
        try {
            productId = scanner.nextInt();
        } catch (Exception e) {
            Warn.invalidInput();
            productId = promptProductKeyToModify();
        }
        return productId;
    }

    private Product promptNewProduct() {
        final Scanner scanner = new Scanner(System.in);
        final String productName;
        final int productPrice;

        try {
            view.showProductName();
            productName = scanner.nextLine().trim().toLowerCase();
            view.showProductPrice();
            productPrice = scanner.nextInt();
        } catch (Exception e) {
            return null;
        }

        if (productName.isEmpty() || productPrice <= 0) return null;
        return new Product(ProductsManager.getHighestProductId() + 1, productName, productPrice);
    }

    private boolean confirmProduct(Product oldProduct, Product newProduct) {
        final Scanner scanner = new Scanner(System.in);
        if (oldProduct != null) view.showOldProduct(oldProduct);
        if (newProduct != null) view.showProductConfirmation(newProduct);
        final String confirmation = scanner.nextLine().trim().toLowerCase();
        return confirmation.equals("y") || confirmation.equals("yes");
    }

    private boolean confirmDeleteProduct(Product productToDelete) {
        final Scanner scanner = new Scanner(System.in);
        view.showOldProduct(productToDelete);
        view.showProductDeletionConfirmation();
        final String confirmation = scanner.nextLine().trim().toLowerCase();
        return confirmation.equals("y") || confirmation.equals("yes");
    }

}
