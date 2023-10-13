package org.devi. routes;

import org.devi.controllers.Navigator;
import org.devi.controllers.RegistrationController;
import org.devi.interfaces.Credential;
import org.devi.interfaces.Route;
import org.devi.views.RegistrationPageView;

public class RegistrationPage implements Route {
    @Override
    public void build() {
        final RegistrationPageView view = new RegistrationPageView();
        final RegistrationController controller = new RegistrationController(view);
        final Credential userForRegistration = controller.promptRegistration();

        if (userForRegistration == null) return;

        final Credential user = controller.submitRegistrationCredential(userForRegistration);
        if (user == null) {
            build();
        } else {
            Navigator.gotoLastRoute();
        }
    }
}
