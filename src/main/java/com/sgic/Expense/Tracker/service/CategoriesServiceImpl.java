package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.CategoriesDto;
import com.sgic.Expense.Tracker.entity.Categories;
import com.sgic.Expense.Tracker.exceptions.ResourceNotFoundException;
import com.sgic.Expense.Tracker.mapper.CategoriesMapper;
import com.sgic.Expense.Tracker.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoriesMapper categoriesMapper;

    @Override
    public List<CategoriesDto> getAllCategories() {
        return categoriesMapper.toDtoList(categoriesRepository.findAll());
    }

    @Override
    public CategoriesDto getCategoryById(Long id) {
       Categories categories = categoriesRepository.findById(id)
               .orElseThrow(() ->
                       new ResourceNotFoundException("Category not found with id:" +id));
       return categoriesMapper.toDto(categories);
    }

    @Override
    public CategoriesDto createCategory(CategoriesDto dto) {
       Categories categories = categoriesMapper.toEntity(dto);
       return categoriesMapper.toDto(categoriesRepository.save(categories));
    }

    @Override
    public CategoriesDto updateCategory(Long id, CategoriesDto dto) {
        Categories exiting = categoriesRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: "+id));
        exiting.setName(dto.getName());
        exiting.setDescription(dto.getDescription());
        exiting.setStatus(dto.getStatus());

        return categoriesMapper.toDto(categoriesRepository.save(exiting));
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoriesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        categoriesRepository.deleteById(id);
    }


}