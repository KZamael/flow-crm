package com.example.application.views.list;

import com.example.application.service.AgeService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "birthdate-view", layout = MainLayout.class)
@PageTitle("BirthDate | Vaadin CRM")
public class BirthDateView extends VerticalLayout {

    private final AgeService ageService;

    public BirthDateView(AgeService ageService) {
        this.ageService = ageService;

        DatePicker datePicker = new DatePicker("Birth date");
        datePicker.setId("birth-date");
        Button button = new Button("Calculate age");
        add(datePicker, button);

        button.addClickListener(event -> calculateAge(datePicker.getValue()));
    }

    public void calculateAge(LocalDate date) {
        if (date == null) {
            showError();
        } else {
            showAge(date);
        }
    }

    public void showError() {
        Notification.show("Please enter a date.");
    }

    public void showAge(LocalDate date) {
        int age = ageService.getAgeByBirthDate(date);
        String text = String.format("Age: %s years old", age);
        add(new Paragraph(text));
    }
}
