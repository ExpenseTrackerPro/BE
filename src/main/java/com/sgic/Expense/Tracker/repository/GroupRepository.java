package com.sgic.Expense.Tracker.repository;

import com.sgic.Expense.Tracker.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
