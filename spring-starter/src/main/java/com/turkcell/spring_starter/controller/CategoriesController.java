package com.turkcell.spring_starter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.spring_starter.dto.CreateCategoryRequest;
import com.turkcell.spring_starter.dto.CreatedCategoryResponse;
import com.turkcell.spring_starter.dto.ListCategoryResponse;
import com.turkcell.spring_starter.service.CategoryServiceImpl;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private final CategoryServiceImpl categoryServiceImpl;

    public CategoriesController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PostMapping
    public CreatedCategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return categoryServiceImpl.create(request);
    }

    @GetMapping
    public List<ListCategoryResponse> getAll() {
        return categoryServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public ListCategoryResponse getById(@PathVariable UUID id) {
        return categoryServiceImpl.getById(id);
    }

    @PutMapping("/{id}")
    public CreatedCategoryResponse update(@PathVariable UUID id, @RequestBody CreateCategoryRequest request) {
        return categoryServiceImpl.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        categoryServiceImpl.delete(id);
    }
}
