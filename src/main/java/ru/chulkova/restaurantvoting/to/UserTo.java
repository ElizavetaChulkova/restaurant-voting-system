package ru.chulkova.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.chulkova.restaurantvoting.util.NoHtml;
import ru.chulkova.restaurantvoting.web.HasIdAndEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserTo extends BaseTo implements HasIdAndEmail {

    @NotBlank
    @Size(min = 2, max = 100)
    @NoHtml
    String name;

    @Email
    @NotBlank
    @Size(max = 100)
    @NoHtml
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
