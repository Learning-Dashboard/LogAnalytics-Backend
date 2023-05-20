package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.CategoryController;
import com.upc.gessi.loganalytics.app.domain.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {

    @Mock
    CategoryController categoryController;

    @InjectMocks
    CategoryRestController categoryRestController;

    @Test
    void findAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        Category c = new Category("test");
        categoryList.add(c);
        when(categoryController.getAll()).thenReturn(categoryList);
        List<Category> output = categoryRestController.findAllCategories();
        assertEquals(categoryList, output);
    }
}