package org.devi. routes;

import org.devi.controllers.LoginController;
import org.devi.controllers.Navigator;
import org.devi.interfaces.Credential;
import org.devi.interfaces.Route;
import org.devi.models.Admin;
import org.devi.models.User;
import org.devi.views.LoginPageView;

public class LoginPage implements Route {
    @Override
    public void build() {
        final LoginPageView view = new LoginPageView();
        final LoginController controller = new LoginController(view);
        final Credential userForLogin = controller.promptLogin();

        if (userForLogin == null) {
            Navigator.gotoLastRoute();
            return;
        }

        final Credential user = controller.submitLoginCredential(userForLogin);
        if (user == null) {
            build();
            return;
        }

        if (user instanceof Admin) {
            Navigator.runRouteManually(new AdminPage());
        } else if (user instanceof User) {
            Navigator.runRouteManually(new HomePage());
        }
    }
}
