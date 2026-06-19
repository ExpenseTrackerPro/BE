package com.sgic.Expense.Tracker.controller;

import com.sgic.Expense.Tracker.dto.GroupDto;
import com.sgic.Expense.Tracker.dto.User_group_Dto;
import com.sgic.Expense.Tracker.entity.Group;
import com.sgic.Expense.Tracker.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto>getGroupById(@PathVariable Long id){
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto dto){
        return ResponseEntity.ok(groupService.createGroup(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto>
    updateGroup(@PathVariable Long id,
                @RequestBody GroupDto dto){
        return ResponseEntity.ok(groupService.updateGroup(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.ok("Group deleted successfully");

    }

    @PostMapping("/join")
    public ResponseEntity<String>joinGroup(@RequestBody User_group_Dto dto ){
        return ResponseEntity.ok(groupService.joinGroup(dto));
    }



}
