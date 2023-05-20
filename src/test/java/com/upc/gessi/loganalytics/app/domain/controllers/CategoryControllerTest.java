package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Category;
import com.upc.gessi.loganalytics.app.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    @Test
    void getAll() {
        Category c = new Category("test");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(c);
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> output = categoryController.getAll();
        assertEquals(output, categoryList);
    }

    @Test
    void getByName() {
        Category c = new Category("test");
        Mockito.when(categoryRepository.findById("test")).thenReturn(Optional.of(c));
        Mockito.when(categoryRepository.findById("test2")).thenReturn(Optional.empty());
        assertEquals(c, categoryController.getByName("test"));
        assertNull(categoryController.getByName("test2"));
    }
}