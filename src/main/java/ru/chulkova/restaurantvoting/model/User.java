package ru.chulkova.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;

    private String name;

    private String password;

    private Set<Role> roles;
}
