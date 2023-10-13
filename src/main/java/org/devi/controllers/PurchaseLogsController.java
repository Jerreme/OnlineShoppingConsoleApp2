package org.devi. controllers;

import org.devi.database.PurchasedDb;
import org.devi.models.PurchasedLog;
import org.devi.views.PurchaseLogsView;

import java.util.ArrayList;

public class PurchaseLogsController {
    final PurchaseLogsView view;

    public PurchaseLogsController(PurchaseLogsView view) {
        this.view = view;
    }

    public void showPurchasedLogs() {

        final PurchasedDb db = new PurchasedDb();
        final ArrayList<PurchasedLog> logs = db.getPurchased(CredentialManager.getLoggedInUser().username());

        // TODO get purchased logs from database
        if (logs.isEmpty()) {
            view.showNoPurchasesYet();
        } else {
            view.showPurchasedLogs(logs);
            waitForInput();
        }
        Navigator.gotoLastRoute();
    }

    private void waitForInput() {
        view.promptAnyInput();
        new java.util.Scanner(System.in).nextLine();
    }
}
