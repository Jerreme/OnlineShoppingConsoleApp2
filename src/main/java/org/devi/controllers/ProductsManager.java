package org.devi. controllers;

import org.devi.database.ProductDb;
import org.devi.models.Product;

import java.util.ArrayList;

public class ProductsManager {

    private static final ArrayList<Product> products = new ArrayList<>();

    public static ArrayList<Product> generateProductsFromArray(String[] productNames, int[] productPrices) {
        final ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < productNames.length; i++) {
            products.add(new Product(i + 1, productNames[i], productPrices[i]));
        }
        return products;
    }

    @SuppressWarnings("SameReturnValue")
    public static ArrayList<Product> getProducts() {
        if (!ProductsManager.products.isEmpty()) return ProductsManager.products;
        final ProductDb productDb = new ProductDb();
        ProductsManager.products.clear();
        ProductsManager.products.addAll(productDb.getProducts());
        return ProductsManager.products;
    }

    private static void setProducts(ArrayList<Product> products) {
        final ProductDb productDb = new ProductDb();
        productDb.addProducts(products);
        ProductsManager.products.clear();
    }

    public static void addProduct(Product product) {
        final ProductDb productDb = new ProductDb();
        productDb.addProduct(product);
        ProductsManager.products.add(product);
    }

    public static void modifyProduct(Product product) {
        final ProductDb productDb = new ProductDb();
        productDb.modifyProduct(product);
        ProductsManager.products.set(product.key() - 1, product);
    }

    public static void deleteProduct(Product product) {
        final ProductDb productDb = new ProductDb();
        productDb.deleteProduct(product);
        ProductsManager.products.remove(product);
    }

    public static void refreshProductsId() {
        final ArrayList<Product> newProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            final Product product = products.get(i);
            newProducts.add(new Product(i + 1, product.productName(), product.price()));
        }
        ProductsManager.clearProducts();
        ProductsManager.setProducts(newProducts);
    }

    public static Product getProductById(int id) {
        for (Product product : products) {
            if (product.key() == id) return product;
        }
        return null;
    }

    public static int getHighestProductId() {
        final ProductDb productDb = new ProductDb();
        return productDb.getHighestProductId();
    }

    public static boolean isProductExists(String productName) {
        final ProductDb productDb = new ProductDb();
        return productDb.isProductExist(productName);
    }

    public static void clearProducts() {
        final ProductDb productDb = new ProductDb();
        productDb.clearProducts();
        ProductsManager.products.clear();
    }
}
