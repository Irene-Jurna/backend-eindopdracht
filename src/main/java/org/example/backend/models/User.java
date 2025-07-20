package org.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.Role;
import org.example.backend.enums.SubscriptionType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime lastLogin;
    private List<CuisineType> preferredCuisineTypes;
    private List<Recipe> bookmarkedRecipes;
    private Role role;
    private SubscriptionType subscriptionType;
    private Boolean volunteer;
}
