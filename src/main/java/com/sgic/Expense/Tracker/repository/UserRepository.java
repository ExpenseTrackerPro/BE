package com.sgic.Expense.Tracker.repository;

import com.sgic.Expense.Tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository extends JpaRepository which already provides:
 * save(), findById(), findAll(), delete(), etc.
 *
 * We add findByEmail() because Spring Security loads users by username (email).
 * existsByEmail() is used during registration to prevent duplicate accounts.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
