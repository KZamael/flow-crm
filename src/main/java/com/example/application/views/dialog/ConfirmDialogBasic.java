package com.example.application.views.dialog;

import com.example.application.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "confirm-dialog-basic", layout = MainLayout.class)
@PageTitle("Confirm Dialog | Vaadin CRM")
public class ConfirmDialogBasic extends Div {

    public ConfirmDialog dialog;
    private final Span status;

    public ConfirmDialogBasic() {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        status = new Span();
        status.setVisible(false);

        dialog = getConfirmDialog();

        Button button = new Button("Open confirm dialog");
        button.addClickListener(event -> {
           dialog.open();
           status.setVisible(false);
        });

        dialog.open();

        horizontalLayout.add(button, status);
        add(horizontalLayout);

        // Center the button within the example
        setButtonToCenter();
    }

    private void setButtonToCenter() {
        getStyle().set("position", "fixed").set("top", "0").set("right", "0")
                        .set("bottom", "0").set("left", "0").set("display", "flex")
                        .set("align-items", "center").set("justify-content", "center");
    }

    private ConfirmDialog getConfirmDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Unsaved changes");
        dialog.setText("There are unsaved changes. Do you want to discard or save them?");

        dialog.setCancelable(true);
        dialog.addCancelListener(event -> setStatus("Canceled"));

        dialog.setRejectable(true);
        dialog.setRejectText("Discard");
        dialog.addRejectListener(event -> setStatus("Discarded"));

        dialog.setConfirmText("Save");
        dialog.addConfirmListener(event -> setStatus("Saved"));
        return dialog;
    }

    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
    }

}
