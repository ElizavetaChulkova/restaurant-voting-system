package ru.chulkova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends AbstractPersistable<Integer> {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @Size(max = 128)
    private String email;

    @Column(name = "name")
    @Size(max = 128)
    private String name;

    @Column(name = "password")
    @Size(max = 256)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
