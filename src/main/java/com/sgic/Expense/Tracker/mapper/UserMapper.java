package com.sgic.Expense.Tracker.mapper;

import com.sgic.Expense.Tracker.dto.UserDto;
import com.sgic.Expense.Tracker.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDto> toDtoList (List<User> userList);
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<User> toEntityList(List<UserDto> userDtos);
}
