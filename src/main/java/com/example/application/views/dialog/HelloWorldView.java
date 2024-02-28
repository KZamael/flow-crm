package com.example.application.views.dialog;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;


@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "hello", layout = MainLayout.class)
@PageTitle("Hello World | Vaadin CRM")
public class HelloWorldView extends HorizontalLayout {
    TextField name;
    Button greet;

    public HelloWorldView() {
        setId("hello-world-view");
        name = new TextField("Your name");
        greet = new Button("Say hello");

        // Handle clicks
        greet.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, greet);
        add(name, greet);
    }
}
