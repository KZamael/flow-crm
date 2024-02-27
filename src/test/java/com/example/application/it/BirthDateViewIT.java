package com.example.application.it;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.example.application.data.constants.DataEntityConstants.ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BirthDateViewIT extends AbstractViewTest {

    public BirthDateViewIT() {
        super(ROUTE);
    }

    @Disabled
    @Test
    public void shouldShowNotificationOnNullDate() {
        // arrange
        DatePickerElement datePicker = $(DatePickerElement.class).first();
        datePicker.clear();
        ButtonElement button = $(ButtonElement.class).first();

        // act - click the button
        button.click();

        NotificationElement notification = $(NotificationElement.class).waitForFirst();
        boolean isOpen = notification.isOpen();

        // assert
        assertTrue(isOpen);
    }

    @Disabled
    @Test
    public void shouldAddParagraphOnNonNullDate() {
        int expectedCount = $(ParagraphElement.class).all().size() + 1;

        DatePickerElement datePickerElement = $(DatePickerElement.class).first();
        datePickerElement.setDate(LocalDate.now());
        ButtonElement button = $(ButtonElement.class).first();
        button.click();

        int actualCount = $(ParagraphElement.class).all().size();

        assertEquals(expectedCount, actualCount);
    }
}
