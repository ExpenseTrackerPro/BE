package com.sgic.Expense.Tracker.controller;

import com.sgic.Expense.Tracker.dto.CategoriesDto;
import com.sgic.Expense.Tracker.service.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoriesDto>> getAllCategories() {
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriesService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriesDto> createCategory(
            @Valid @RequestBody CategoriesDto dto) {

        CategoriesDto createdCategory = categoriesService.createCategory(dto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoriesDto dto) {

        return ResponseEntity.ok(categoriesService.updateCategory(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}