package com.example.application.views.list;

import com.example.application.data.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class CheckboxViewTest {

    @Autowired
    private CheckboxView checkboxView;

    @Test
    public void boxCheckedWhenFirstAndThirdCompanySelected() {
        // arrange & act
        List<Company> companies = checkboxView.service.findAllCompanies();
        checkboxView.preSelectCheckboxes(companies.get(0), companies.get(2));

        Set<Company> selectedItems  = checkboxView.checkboxGroup.getSelectedItems();
        /*System.out.println(selectedItems.stream()
                .map(Company::getName)
                .collect(Collectors.joining(", ")));

        // All checkboxes
        System.out.println(companies.stream()
                .map(Company::getName)
                .collect(Collectors.joining(", ")));
        */

        // assert
        assertTrue(checkboxView.checkboxGroup.isSelected(companies.get(0)));
        assertFalse(checkboxView.checkboxGroup.isSelected(companies.get(1)));
        assertTrue(checkboxView.checkboxGroup.isSelected(companies.get(2)));
        assertFalse(checkboxView.checkboxGroup.isSelected(companies.get(3)));

        assertEquals("Phillips Van Heusen Corp., Laboratory Corporation of America Holdings",
                selectedItems.stream().map(Company::getName).collect(Collectors.joining(", ")));
    }

    @Test
    public void boxCheckedWhenFirstAndSecondCompanySelected() {
        // arrange & act
        List<Company> companies = checkboxView.service.findAllCompanies();
        checkboxView.preSelectCheckboxes(companies.get(0), companies.get(1));

        Set<Company> selectedItems  = checkboxView.checkboxGroup.getSelectedItems();

        // assert
        assertTrue(checkboxView.checkboxGroup.isSelected(companies.get(0)));
        assertTrue(checkboxView.checkboxGroup.isSelected(companies.get(1)));
        assertTrue(checkboxView.checkboxGroup.isSelected(companies.get(2)));
        assertFalse(checkboxView.checkboxGroup.isSelected(companies.get(3)));

        assertEquals("Phillips Van Heusen Corp., Avaya Inc., Laboratory Corporation of America Holdings",
                selectedItems.stream().map(Company::getName).collect(Collectors.joining(", ")));
    }
}
