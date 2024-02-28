package com.example.application.views.dialog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ConfirmDialogBasicTest {

    @Autowired
    private ConfirmDialogBasic confirmDialogBasic;


    @BeforeEach
    void setupData() {

    }

    @Test
    public void clickConfirmationButtonAndThenSave() {
        // arrange
        boolean visible = confirmDialogBasic.status.isVisible();
        assertFalse(visible);

        // act

        // assert
    }
}