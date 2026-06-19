package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.GroupDto;
import com.sgic.Expense.Tracker.dto.User_group_Dto;

import java.util.List;

public interface GroupService {
    List<GroupDto> getAllGroups();

    GroupDto getGroupById(Long id);

    GroupDto createGroup(GroupDto dto);

    GroupDto updateGroup(Long id,GroupDto dto);

    void deleteGroup(Long id);

    String joinGroup(User_group_Dto dto);
}