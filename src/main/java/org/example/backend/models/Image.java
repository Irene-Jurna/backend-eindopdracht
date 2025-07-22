package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseModel {
    private String fileName;

    @Lob
    private byte[] file;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
