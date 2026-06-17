package com.sgic.Expense.Tracker.mapper;


import com.sgic.Expense.Tracker.dto.CategoriesDto;
import com.sgic.Expense.Tracker.entity.Categories;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {
CategoriesDto toDto(Categories categories);

Categories toEntity(CategoriesDto categoriesDto);

List<CategoriesDto> toDtoList(List<Categories> categoriesList);

}