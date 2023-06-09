package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.StringUtils;
import ru.chulkova.restaurantvoting.util.JsonDeserializers;
import ru.chulkova.restaurantvoting.util.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(min = 5, max = 128)
    @NoHtml
    @NotNull
    private String email;

    @Column(name = "name")
    @Size(min = 2, max = 128)
    @NoHtml
    @NotNull
    private String name;

    @Column(name = "password")
    @Size(max = 256)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = JsonDeserializers.PasswordDeserializer.class)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Role> roles;

    public User(Integer id, String email, String name, String password, Set<Role> roles) {
        this(email, name, password, roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles));
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = StringUtils.hasText(email) ? email.toLowerCase() : null;
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }
}
