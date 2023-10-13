package org.devi. routes;

import org.devi.controllers.CredentialManager;
import org.devi.controllers.Navigator;
import org.devi.controllers.ProfilePageController;
import org.devi.controllers.Tasker;
import org.devi.interfaces.Route;
import org.devi.views.ProfilePageView;


public class ProfilePage implements Route {
    @Override
    public void build() {
        ProfilePageView view = new ProfilePageView();
        view.showProfilePage(CredentialManager.getLoggedInUser());
        view.printDashSeparator();
        view.showProfileOptions();
        ProfilePageController controller = new ProfilePageController(view);
        Tasker tasker = new Tasker(this.toString());
        tasker.addTask(1, controller::cashIn);
        tasker.addTask(0, Navigator::gotoLastRoute);
        tasker.runPrompt();
    }
}
