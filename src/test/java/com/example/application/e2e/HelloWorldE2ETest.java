package com.example.application.e2e;

import com.example.application.e2e.elements.LoginViewElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;
import com.vaadin.testbench.IPAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.example.application.constants.TestConstants.PASSWORD;
import static com.example.application.constants.TestConstants.USERNAME_USER;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldE2ETest extends BrowserTestBase {

    @Autowired
    Environment environment; // Core WebEnvironment

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @BeforeEach
    void openBrowser() {
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":"
                + environment.getProperty("local.server.port") + "/hello");
    }

    @BrowserTest
    public void setTextClickButtonNotificationIsShown() {
        // arrange
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assertions.assertTrue(loginView.login(USERNAME_USER, PASSWORD));

        TextFieldElement textFieldElement = $(TextFieldElement.class).first();
        textFieldElement.setValue("Bro");

        ButtonElement buttonElement = $(ButtonElement.class).get(1);
        Assertions.assertTrue(buttonElement.isDisplayed());

        // act
        buttonElement.click();

        // assert
        NotificationElement notificationElement = $(NotificationElement.class).first();
        Assertions.assertEquals("Hello Bro", notificationElement.getText());
    }
}
