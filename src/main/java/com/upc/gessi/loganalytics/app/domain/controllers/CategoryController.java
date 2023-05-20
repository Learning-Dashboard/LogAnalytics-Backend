package com.upc.gessi.loganalytics.app.domain.controllers;


import com.upc.gessi.loganalytics.app.domain.models.Category;
import com.upc.gessi.loganalytics.app.domain.repositories.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostConstruct
    public void storeCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Session management"));
        categoryList.add(new Category("Indicator accesses"));
        categoryList.add(new Category("Factor accesses"));
        categoryList.add(new Category("Metric accesses"));
        categoryList.add(new Category("Quality Model accesses"));
        categoryList.add(new Category("Other kinds of accesses"));
        categoryRepository.saveAll(categoryList);
    }

    public List<Category> getAll() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<>();
        categoryIterable.forEach(categoryList::add);
        return categoryList;
    }

    public Category getByName(String name) {
        Optional<Category> res = categoryRepository.findById(name);
        return res.orElse(null);
    }
}
