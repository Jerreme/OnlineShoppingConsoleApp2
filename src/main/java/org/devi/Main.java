package org.devi;
/*
 * ### Online Shopping Console App ###
 * A fully functional shopping console app, includes users page for browsing and buying products,
 * and an admins page that can manage products and view registered users.
 *
 * The app utilize `SQLite` as its primary database, and uses `sqlite-jdbc` to connect to it.
 * After you clone the repo, you might want to add the sqlite-jdbc-3.34.0.jar as libraries to this project.
 * If you haven't downloaded the sqlite driver, you can download it here:
 * direct download: https://github.com/xerial/sqlite-jdbc/releases/download/3.43.0.0/sqlite-jdbc-3.43.0.0.jar
 * visit sqlite-jdbc GitHub page: https://github.com/xerial/sqlite-jdbc/releases
 *
 * Features as of 10-09-23:
 *  - Login and Register (Admin and User)
 *
 * # For Users:
 *  - Browse Products
 *  - Buy Products
 *  - View Purchase Logs
 *  - View Profile
 *  - Deposit to E-Wallet
 *
 * # For Admins:
 *  - Manage Products
 *  - View Registered Users and Admins
 */

import org.devi.controllers.Navigator;
import org.devi.database.DatabaseManager;
import org.devi.routes.IntroPage;

public class Main {

    public static void main(String[] args) {
        // init database tables
        DatabaseManager.init();

        // run the app, starting from the intro page
        Navigator.runRouteManually(new IntroPage());
    }


}