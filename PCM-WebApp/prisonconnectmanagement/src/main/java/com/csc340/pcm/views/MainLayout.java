package com.csc340.pcm.views;

import com.csc340.pcm.security.SecurityService;
import com.csc340.pcm.views.admin.AdminView;
import com.csc340.pcm.views.admin.DashboardView;
import com.csc340.pcm.views.organization.OrganizationView;
import com.csc340.pcm.views.visitor.VisitorView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
public class MainLayout extends AppLayout {

    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("PCM");
        logo.addClassNames("text-l", "m-m");

        Button  logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo,
                logout
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink adminLink = new RouterLink("Admin", AdminView.class);
        RouterLink organLink = new RouterLink("Organization", OrganizationView.class);
        RouterLink visitorLink = new RouterLink("Visitor", VisitorView.class);
        adminLink.setHighlightCondition(HighlightConditions.sameLocation());
        visitorLink.setHighlightCondition(HighlightConditions.sameLocation());

        if(securityService.getAuthenticatedUser().getUsername() == "admin"){
            addToDrawer(new VerticalLayout(
                    adminLink,
                    new RouterLink("Dashboard", DashboardView.class))
            );
        }
        else if(securityService.getAuthenticatedUser().getUsername() == "organ"){
            addToDrawer(new VerticalLayout(
                    organLink
            ));
        }
        else{
            addToDrawer(new VerticalLayout(
                    visitorLink
            ));
        }

    }

}