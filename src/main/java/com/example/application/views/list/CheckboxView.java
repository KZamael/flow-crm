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
    Checkbox checkbox;
    List<Company> companies;

    CheckboxGroup<Company> checkboxGroup;

    public CheckboxView(CrmService service) {
        this.service = service;
        setPadding(false);

        checkbox = new Checkbox("Enable Checkboxlist");
        companies = service.findAllCompanies();
        checkboxGroup = new CheckboxGroup<>();

        adjustCheckboxGroup();
        addValueChangeListener();

        // Check some boxes beforehand...
        preSelectCheckboxes(companies.get(0), companies.get(2));

        add(checkbox, checkboxGroup);
    }

    protected void preSelectCheckboxes(Company... company) {
        checkboxGroup.select(company);
    }

    private void addValueChangeListener() {
        checkbox.addValueChangeListener(event -> {
           if (checkbox.getValue()) {
               checkboxGroup.setValue(new HashSet<>(companies));
           } else {
               checkboxGroup.deselectAll();
           }
        });
    }

    private void adjustCheckboxGroup() {
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
