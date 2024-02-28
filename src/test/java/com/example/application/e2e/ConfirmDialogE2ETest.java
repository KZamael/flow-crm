package com.example.application.e2e;

import com.example.application.e2e.elements.LoginViewElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;
import com.vaadin.testbench.IPAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.example.application.constants.TestConstants.PASSWORD;
import static com.example.application.constants.TestConstants.USERNAME_USER;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfirmDialogE2ETest extends BrowserTestBase {

    @Autowired
    Environment environment;

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @BeforeEach
    void openBrowser() {
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":"
                + environment.getProperty("local.server.port") + "/confirm-dialog-basic");
    }

    @AfterEach
    void tearDown(){
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":"
                + environment.getProperty("local.server.port") + "");
    }

    @BrowserTest
    public void clickConfirmationButtonAndThenSave() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        ButtonElement confirmDialogButton = $(ButtonElement.class).get(1);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        // act
        confirmDialogButton.click();

        ButtonElement save = $(ButtonElement.class).get(3);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        save.click();

        // assert
        SpanElement spanElement = $(SpanElement.class).first();
        Assertions.assertEquals("Status: Saved", spanElement.getText());
    }

    @BrowserTest
    public void clickConfirmationButtonAndThenCancel() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        ButtonElement confirmDialogButton = $(ButtonElement.class).get(1);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        // act
        confirmDialogButton.click();

        ButtonElement save = $(ButtonElement.class).get(2);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        save.click();

        // assert
        SpanElement spanElement = $(SpanElement.class).first();
        Assertions.assertEquals("Status: Canceled", spanElement.getText());
    }

    @BrowserTest
    public void clickConfirmationButtonAndThenDiscard() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        ButtonElement confirmDialogButton = $(ButtonElement.class).get(1);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        // act
        confirmDialogButton.click();

        ButtonElement save = $(ButtonElement.class).get(4);
        Assertions.assertTrue(confirmDialogButton.isDisplayed());

        save.click();

        // assert
        SpanElement spanElement = $(SpanElement.class).first();
        Assertions.assertEquals("Status: Discarded", spanElement.getText());
    }
}