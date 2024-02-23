package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.List;

/**
 * Demonstration View for the implementation of checkboxes
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "checkbox", layout = MainLayout.class)
@PageTitle("Checkbox | Vaadin CRM")
public class CheckboxView extends VerticalLayout {

    CrmService service;

    public CheckboxView(CrmService service) {
        this.service = service;
        setPadding(false);

        Checkbox checkbox = new Checkbox("Enable Checkboxlist");
        List<Company> companies = service.findAllCompanies();
        CheckboxGroup<Company> checkboxGroup = new CheckboxGroup<>();

        adjustCheckboxGroup(checkboxGroup, companies, checkbox);
        addValueChangeListener(checkbox, checkboxGroup, companies);

        // Check some boxes beforehand...
        checkboxGroup.select(companies.get(0), companies.get(2));

        add(checkbox, checkboxGroup);
    }

    private static void addValueChangeListener(Checkbox checkbox, CheckboxGroup<Company> checkboxGroup, List<Company> companies) {
        checkbox.addValueChangeListener(event -> {
           if (checkbox.getValue()) {
               checkboxGroup.setValue(new HashSet<>(companies));
           } else {
               checkboxGroup.deselectAll();
           }
        });
    }

    private static void adjustCheckboxGroup(CheckboxGroup<Company> checkboxGroup, List<Company> companies, Checkbox checkbox) {
        checkboxGroup.setLabel("Enable and Disable Company Names");
        checkboxGroup.setItemLabelGenerator(Company::getName);
        checkboxGroup.setItems(companies);
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.addValueChangeListener(event -> {
            if (event.getValue().size() == companies.size()) {
                setCheckBoxes(checkbox, true);
            } else if (event.getValue().isEmpty()) {
                setCheckBoxes(checkbox, false);
            } else {
                checkbox.setIndeterminate(true);
            }
        });
    }

    private static void setCheckBoxes(Checkbox checkbox, boolean value) {
        checkbox.setValue(value);
        checkbox.setIndeterminate(false);
    }

}
