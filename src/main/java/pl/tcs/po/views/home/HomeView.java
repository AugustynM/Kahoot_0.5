package pl.tcs.po.views.home;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import pl.tcs.po.views.MainLayout;

@PageTitle("Kahoot v 0.5")
@Route(value = "/", layout = MainLayout.class)
@RouteAlias(value = "/", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private String welcomeText = "Welcome to Kahoot v 0.5";

    public HomeView() {
        add(welcomeText);
    }

}
