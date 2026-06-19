package com.sgic.Expense.Tracker.mapper;

import com.sgic.Expense.Tracker.dto.GroupDto;
import com.sgic.Expense.Tracker.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);
    Group toEntity(GroupDto groupDto);
}