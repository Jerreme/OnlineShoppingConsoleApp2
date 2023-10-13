package org.devi.views;

import org.devi.interfaces.Messenger;
import org.devi.models.Product;
import org.devi.models.PurchasedLog;

import java.util.ArrayList;

public class PurchaseLogsView extends Messenger {
    public void showPurchasedLogs(ArrayList<PurchasedLog> logs) {
        printHeader("Purchased Logs");

        String tempDate = "";
        for (PurchasedLog log : logs) {
            int totalPrice = 0;
            ArrayList<String> products = new ArrayList<>();
            for (Product product : log.products()) {
                totalPrice += product.price();
                final String name = product.productName();
                final String formatted = String.format(" · %s%s₱%s",
                        name, generateSpaces(name.length() + 1), product.price());
                products.add(formatted);
            }

            final String date = log.date();
            if (!tempDate.equals(date)) {
                println(String.format("%s\t\t Amount paid: ₱%d", date, totalPrice));
                tempDate = date;
            }

            for (String product : products) {
                println(product);
            }
            newLine();
        }
    }

    public void showNoPurchasesYet() {
        systemMessage("No purchases yet.");
    }

    public void promptAnyInput() {
        print("Press enter to continue.");
    }
}
