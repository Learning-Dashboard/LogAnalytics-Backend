package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    private List<InternalMetric> internalMetrics;

    @BeforeEach
    void setUp() {
        internalMetrics = new ArrayList<>();
        category = new Category("test", internalMetrics);
    }

    @AfterEach
    void tearDown() {
        internalMetrics = new ArrayList<>();
        category = null;
    }

    @Test
    void getId() {
        assertEquals("test", category.getId());
    }

    @Test
    void setId() {
        category.setId("test2");
        assertEquals("test2", category.getId());
    }

    @Test
    void getInternalMetrics() {
        assertEquals(internalMetrics, category.getInternalMetrics());
    }

    @Test
    void setInternalMetrics() {
        List<InternalMetric> internalMetrics2 = new ArrayList<>();
        InternalMetric im = new InternalMetric("id", "name");
        internalMetrics2.add(im);
        category.setInternalMetrics(internalMetrics2);
        assertEquals(internalMetrics2, category.getInternalMetrics());
    }

    @Test
    void testToString() {
        String res = "Category{id='test'}";
        assertEquals(res, category.toString());
    }
}