package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

/**
 * Basic implementation of a ListView.
 * Elements in this View are implemented with a Vertical and Horizontal Layout.
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout {

    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField("");
    ContactForm form;
    CrmService service;

    // Field for the services to have access to it the entire view
    public ListView(CrmService service) {
        this.service = service;

        // Field for the form
        addClassName("list-view");
        setSizeFull(); // Entire Browser Window

        // Configured form and grid
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid); // Corresponds 2/3 des Spaces
        content.setFlexGrow(1, form); // Corresponds to 1/3 des Spaces
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    // Instantiating a new form, passing in a list of Names and Statuses from the Backend
    private void configureForm() {
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");

        // Listener for the Save-Button
        form.addSaveListener(this::saveContact);
        form.addDeleteListener(this::deleteContact);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid"); // For later styling
        grid.setSizeFull(); // Entire Browser Window
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); // Gets all the Columns, grabs every and sets the Autowith to true
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        // Multi and single select modes are supported
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    /**
     * Toolbar of the Application
     *
     * @returns the toolbars Elements in a horizontal layout
     */
    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); // Does not hit the Database on every Keystroke
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    public void editContact(Contact contact) {
        if(contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    // Enriches the grid with the filter criteria's matching contact-elements from the Service
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    // Save Button Logic...
    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    // Delete Button Logic...
    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }
}
