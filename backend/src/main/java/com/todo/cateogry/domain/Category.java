package com.todo.cateogry.domain;

import com.todo.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void validateOwner(Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw new RuntimeException("잘못된");
        }
    }
    public void update(String name) {
        if (name != null) {
            this.name = name;
        }
    }


}
