package org.devi. routes;

import org.devi.controllers.PurchaseLogsController;
import org.devi.interfaces.Route;
import org.devi.views.PurchaseLogsView;

public class PurchaseLogsPage implements Route {

    @Override
    public void build() {
        PurchaseLogsView view = new PurchaseLogsView();
        PurchaseLogsController controller = new PurchaseLogsController(view);
        controller.showPurchasedLogs();
    }
}
