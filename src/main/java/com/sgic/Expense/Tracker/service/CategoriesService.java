package com.sgic.Expense.Tracker.service;


import com.sgic.Expense.Tracker.dto.CategoriesDto;

import java.util.List;

public interface CategoriesService {

    List<CategoriesDto> getAllCategories();

    CategoriesDto getCategoryById(Long id);

    CategoriesDto createCategory(CategoriesDto dto);

    CategoriesDto updateCategory(Long id, CategoriesDto dto);

    void deleteCategory(Long id);

}
