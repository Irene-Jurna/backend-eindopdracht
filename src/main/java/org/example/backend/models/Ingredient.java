package org.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
public abstract class Ingredient extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }
}
