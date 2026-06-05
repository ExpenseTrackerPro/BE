package com.sgic.Expense.Tracker.mapper;

import com.sgic.Expense.Tracker.dto.CategoriesDto;
import com.sgic.Expense.Tracker.entity.Categories;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {
  CategoriesDto toDto(Categories category);
  Categories toEntity(CategoriesDto dto);
  List<Categories> toEntityList(List<CategoriesDto> categoriesDtos);
  List<CategoriesDto> toDtoList(List<Categories> categories);

}