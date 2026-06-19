package com.sgic.Expense.Tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgic.Expense.Tracker.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clean User Entity representing users in the database.
 * Completely decoupled from Spring Security.
 */
@Entity
@Table(name = "users") // "user" is a reserved word in PostgreSQL
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "userList")
    private List<Group> groupList;

}
