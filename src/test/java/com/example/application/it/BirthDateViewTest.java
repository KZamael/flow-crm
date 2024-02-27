package com.example.application.it;

import com.example.application.views.list.BirthDateView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class BirthDateViewTest {

    @Disabled
    @Test
    public void shouldShowErrorAndNoAgeOnNullDate() {
        BirthDateView birthDateView = mock(BirthDateView.class);
        doCallRealMethod().when(birthDateView).calculateAge(anyObject());

        birthDateView.calculateAge(null);
        verify(birthDateView).showError();
        verify(birthDateView, never()).showAge(anyObject());
    }

}