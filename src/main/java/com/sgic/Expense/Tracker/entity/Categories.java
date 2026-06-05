package com.sgic.Expense.Tracker.entity;

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
public class Categories extends DateAudit {
    @Id
    @GeneratedValue
   private Long id;
   private String name;
   private String Description;
   private String Status;
    @OneToMany(mappedBy = "categories")
    List<Group>groupList;

}
