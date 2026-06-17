package com.sgic.Expense.Tracker.service;

import com.sgic.Expense.Tracker.dto.GroupDto;
import com.sgic.Expense.Tracker.dto.User_group_Dto;
import com.sgic.Expense.Tracker.entity.Categories;
import com.sgic.Expense.Tracker.entity.Group;
import com.sgic.Expense.Tracker.entity.User;
import com.sgic.Expense.Tracker.exceptions.ResourceNotFoundException;
import com.sgic.Expense.Tracker.repository.CategoriesRepository;
import com.sgic.Expense.Tracker.repository.GroupRepository;
import com.sgic.Expense.Tracker.repository.UserRepository;
import com.sgic.Expense.Tracker.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;

    @Override
    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDto getGroupById(Long id) {
        return toDto(groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id)));
    }

    @Override
    public GroupDto createGroup(GroupDto dto) {
        Categories category = categoriesRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Group group = new Group();
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setUrl(dto.getUrl());
        group.setCategories(category);

        return toDto(groupRepository.save(group));
    }

    @Override
    public GroupDto updateGroup(Long id, GroupDto dto) {
        Group existing = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Categories category = categoriesRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setUrl(dto.getUrl());
        existing.setCategories(category);

        return toDto(groupRepository.save(existing));
    }

    @Override
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found with id: " + id);
        }
        groupRepository.deleteById(id);
    }

    @Override
    public String joinGroup(User_group_Dto dto) {
        Group group = groupRepository.findById(dto.getGroup_id())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!group.getUserList().contains(user)) {
            group.getUserList().add(user);
            groupRepository.save(group);
        }

        return "User successfully joined the group";
    }

    private GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setUrl(group.getUrl());

        if (group.getCategories() != null) {
            dto.setCategory_id(group.getCategories().getId());
        }

        return dto;
    }
}