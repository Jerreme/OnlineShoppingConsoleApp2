package org.devi. controllers;

import org.devi.interfaces.ExitCode;
import org.devi.interfaces.Route;
import org.devi.views.Warn;

import java.util.HashMap;
import java.util.Map;

/**
 * Navigator class is used to navigate between routes.
 * A Page must implement the Route interface. See {@link Route}
 * <p>
 * Example usage:
 * <pre>
 *         {@code
 *         // create a navigator instance
 *         Navigator navigator = new Navigator();
 *         // add routes to the navigator
 *         navigator.addRoute(1, new ProductsPage());
 *         navigator.addRoute(2, new PurchaseLogsPage());
 *         navigator.addRoute(3, new ProfilePage());
 *         navigator.addRoute(0, new IntroPage());
 *         // add pre callbacks to the navigator
 *         navigator.addPreCallback(0, this::logout);
 *         // start prompt then the navigator automatically runs the route
 *         navigator.runPrompt();
 *         }
 *    </pre>
 * <p>
 * The navigator will automatically run the route based on the user input.
 * The user input is the key-bind of the route.
 * <br>
 * The key-bind is the first parameter of the addRoute method.
 * <br>
 * The route is the second parameter of the addRoute method.
 * <br>
 * The pre callback is a runnable that will be executed before the route is run.
 * <br>
 * The key-bind is the first parameter of the addPreCallback method.
 * <br>
 * The pre callback is the second parameter of the addPreCallback method.
 * <br>
 * The navigator will run the pre callback first before running the route.
 * <br>
 * The navigator will run the route based on the user input.
 */
public class Navigator {

    /**
     * The last route that was run.
     * <br>
     * This is used to go back to the last route.
     * <br>
     * This is set when the user runs a route.
     * <br>
     * This is set to null when the user goes back to the last route.
     * <br>
     */
    private static Route lastRoute;
    /**
     * The current route that is running.
     * <br>
     * This is used to go back to the last route.
     * <br>
     * This is set when the user runs a route.
     * <br>
     * This is set to null when the user goes back to the last route.
     * <br>
     */
    private static Route currentRoute;

    /**
     * The routes that the navigator will run. Added Routes view instance call are stored here.
     * <br>
     * The key-bind is the first parameter of the addRoute method and must be Integer.
     * <br>
     * The route is the second parameter of the addRoute method and must be a class that
     * implements the Route interface.
     * <br>
     */
    private final Map<Integer, Route> routes = new HashMap<>();
    /**
     * The pre callbacks that the navigator will run. Added pre callbacks are stored here.
     * <br>
     * The key-bind is the first parameter of the addPreCallback method and must be Integer.
     * <br>
     * The pre callback is the second parameter of the addPreCallback method and must be a
     * Runnable.
     * <br>
     */
    private final Map<Integer, Runnable> preCallbacks = new HashMap<>();

    /**
     * Run the route manually.
     * <br>
     * This is used to run the route manually.
     * <br>
     * This is used to run the route without the user input.
     * <br>
     * This is used to run the route without the navigator.
     * <br>
     * This is used to run the route without the navigator prompt.
     * <br>
     * This is used to run the route without the navigator key-bind.
     * <br>
     * This is used to run the route without the navigator pre callbacks.
     */
    public static void runRouteManually(Route route) {
        if (currentRoute != null) {
            lastRoute = currentRoute;
            currentRoute.dispose();
        }
        currentRoute = route;
        currentRoute.init();
        currentRoute.build();
    }

    /**
     * Run the route manually.
     * <br>
     * This is used to run the route manually.
     * <br>
     * This is used to run the route without the user input.
     * <br>
     * This is used to run the route without the navigator.
     * <br>
     * This is used to run the route without the navigator prompt.
     * <br>
     * This is used to run the route without the navigator key-bind.
     * <br>
     * This is used to run the route without the navigator pre callbacks.
     */
    public static void gotoLastRoute() {
        if (lastRoute != null) {
            currentRoute.dispose();
            currentRoute = lastRoute;
            lastRoute = null;
            currentRoute.build();
        } else {
            Warn.debugMessage("No last route found!");
            currentRoute.build();
        }
    }

    /**
     * Rebuild the active route.
     * <br>
     * This is used to rebuild the active route.
     * <br>
     * This is used to rebuild the route without the user input.
     * <br>
     * This is used to rebuild the route without the navigator.
     * <br>
     * This is used to rebuild the route without the navigator prompt.
     * <br>
     * This is used to rebuild the route without the navigator key-bind.
     * <br>
     * This is used to rebuild the route without the navigator pre callbacks.
     */
    public static void rebuildActiveRoute() {
        if (currentRoute != null) {
            currentRoute.dispose();
            currentRoute.build();
        } else {
            Warn.debugMessage("No active route found!");
        }
    }

    /**
     * Add route to the navigator.
     * <br>
     * The key-bind is the first parameter of the addRoute method and must be Integer.
     * <br>
     * The route is the second parameter of the addRoute method and must be a class that
     * implements the Route interface.
     */
    public void addRoute(int keyBind, Route route) {
        routes.put(keyBind, route);
    }

    /**
     * Add pre callback to the navigator.
     * <br>
     * The key-bind is the first parameter of the addPreCallback method and must be Integer.
     * <br>
     * The pre callback is the second parameter of the addPreCallback method and must be a
     * Runnable.
     */
    public void addPreCallback(int keyBind, Runnable callback) {
        preCallbacks.put(keyBind, callback);
    }

    /**
     * Run the prompt.
     * <br>
     * This is used to start running the prompt after routes are added.
     */
    public void runPrompt() {
        runRoute(getInput());
    }

    private int getInput() {
        if (routes.isEmpty()) {
            Warn.debugMessageAndExit("Routes is empty!", ExitCode.EXIT_FAILURE);
        }
        System.out.print(">> ");
        try {
            return Integer.parseInt(new java.util.Scanner(System.in).nextLine());
        } catch (NumberFormatException e) {
            Warn.invalidInput();
            return getInput();
        }
    }

    private void runRoute(int keyBind) {
        if (!routes.containsKey(keyBind)) {
            Warn.systemMessage("No route found!");
            currentRoute.build();
            return;
        }

        final Route route = routes.get(keyBind);
        if (route == null) {
            Warn.debugMessage("Route is null!");
            currentRoute.build();
            return;
        }

        currentRoute.dispose();
        lastRoute = currentRoute;

        if (preCallbacks.containsKey(keyBind)) {
            preCallbacks.get(keyBind).run();
        }

        currentRoute = route;
        route.init();
        route.build();
    }
}
