package com.sgic.Expense.Tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgic.Expense.Tracker.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends DateAudit {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String email;
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "userList")
    private List<Group> groupList;

}
