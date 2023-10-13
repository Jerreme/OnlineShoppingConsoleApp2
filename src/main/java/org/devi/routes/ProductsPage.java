package org.devi. routes;

import org.devi.controllers.Navigator;
import org.devi.controllers.ProductsPageController;
import org.devi.controllers.Tasker;
import org.devi.interfaces.Route;
import org.devi.views.ProductsPageView;

public class ProductsPage implements Route {

    /*
     * This snippet was used to create a dummy products for testing purposes.
     * As a default, the app does not have any products in the database.
     */
//    @Override
//    public void init() {
//        ProductsManager.setProducts(ProductsManager.generateProductsFromArray(
//                new String[]{"Milk", "Eggs", "Bread", "Cheese", "Chicken", "Beef", "Pork"},
//                new int[]{95, 50, 45, 100, 150, 200, 180}
//        ));
//        Route.super.init();
//    }

    @Override
    public void build() {
        ProductsPageView view = new ProductsPageView();
        view.showProductsPage();
        ProductsPageController controller = new ProductsPageController(view);

        Tasker tasker = new Tasker(this.toString());
        tasker.addTask(1, controller::showProducts);
        tasker.addTask(2, controller::addToCart);
        tasker.addTask(3, controller::checkout);
        tasker.addTask(0, this::back);
        tasker.runPrompt();
    }

    private void back() {
        Navigator.gotoLastRoute();
    }
}
