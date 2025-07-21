package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.Role;
import org.example.backend.enums.SubscriptionType;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<CuisineType> preferredCuisineTypes;
    private Role role;

    // Alleen voor Harvesters
    private SubscriptionType subscriptionType;
    private Boolean volunteer;
}
