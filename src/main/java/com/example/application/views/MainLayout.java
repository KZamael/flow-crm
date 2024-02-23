package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.CheckboxView;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Main Layout of the application
 */
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontSize.MEDIUM);

        String user = securityService.getAuthenticatedUser().getUsername();

        // Logout Button
        Button logOut = new Button("Log out " + user, e -> securityService.logout());
        var header = new HorizontalLayout(new DrawerToggle(), logo, logOut);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontSize.MEDIUM);

        addToNavbar(header);
    }

    // Left Sidebar
    private void createDrawer() {
        RouterLink listView = new RouterLink("list", ListView.class);
        RouterLink dashboardView = new RouterLink("dashboard", DashboardView.class);
        RouterLink checkboxGroupBasic = new RouterLink("checkbox", CheckboxView.class);

        listView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listView,
                dashboardView,
                checkboxGroupBasic
        ));
    }
}
