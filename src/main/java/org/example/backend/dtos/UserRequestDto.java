package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.SubscriptionType;

import java.util.List;

@Getter
@Setter
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<CuisineType> preferredCuisineTypes;

    // Alleen voor Harvesters
    private SubscriptionType subscriptionType;
    private Boolean volunteer;
}
