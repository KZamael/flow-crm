package com.example.application.e2e;

import com.example.application.e2e.elements.LoginViewElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;
import com.vaadin.testbench.IPAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.time.LocalDate;

import static com.example.application.constants.TestConstants.PASSWORD;
import static com.example.application.constants.TestConstants.USERNAME_USER;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BirthDateViewE2ETest extends BrowserTestBase {

    @Autowired
    Environment environment;

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @BeforeEach
    void openBrowser() {
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":"
                + environment.getProperty("local.server.port") + "/birthdate");
    }

    @BrowserTest
    public void putTodayInDatePickerElementAndThenClickButton() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        LocalDate localDate = LocalDate.now();

        DatePickerElement datePickerElement = $(DatePickerElement.class).first();
        datePickerElement.setDate(localDate);

        Assertions.assertTrue(datePickerElement.isDisplayed());
        Assertions.assertEquals(localDate, datePickerElement.getDate());

        ButtonElement calculationButton = $(ButtonElement.class).get(1);
        Assertions.assertTrue(calculationButton.isDisplayed());

        // act
        calculationButton.click();

        // assert
        var amountYears = LocalDate.now().getYear() - localDate.getYear();

        ParagraphElement paragraphElement = $(ParagraphElement.class).first();
        Assertions.assertEquals(String.format("Age: %s years old", amountYears), paragraphElement.getText());
    }

    @BrowserTest
    public void putThreeYearsAgoInDatePickerElementAndThenClickButton() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        LocalDate expectedDate = LocalDate.of(2020, 1, 20);

        DatePickerElement datePickerElement = $(DatePickerElement.class).first();
        datePickerElement.setDate(expectedDate);

        Assertions.assertTrue(datePickerElement.isDisplayed());
        Assertions.assertEquals(expectedDate, datePickerElement.getDate());

        ButtonElement calculationButton = $(ButtonElement.class).get(1);
        Assertions.assertTrue(calculationButton.isDisplayed());

        // act
        calculationButton.click();

        // assert
        var amountYears = LocalDate.now().getYear() - expectedDate.getYear();

        ParagraphElement paragraphElement = $(ParagraphElement.class).first();
        Assertions.assertEquals(String.format("Age: %s years old", amountYears), paragraphElement.getText());
    }
}
