package org.example.backend.models;

import jakarta.persistence.*;
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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<CuisineType> preferredCuisineTypes;

    @ManyToMany
    @JoinTable(
            name = "user_bookmarked_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> bookmarkedRecipes;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    private Boolean volunteer;

    @OneToMany(mappedBy = "createdBy")
    private List<Recipe> recipes;
}
