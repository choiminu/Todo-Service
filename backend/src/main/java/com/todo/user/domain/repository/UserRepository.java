package com.todo.user.domain.repository;

import com.todo.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUserById(Long id);

    @Query("""
    SELECT CASE
               WHEN COUNT(u) > 0 THEN TRUE
               ELSE FALSE
           END
    FROM User u
    WHERE u.email = :email
    """)
    boolean existsByEmail(String email);
}
