package ru.chulkova.restaurantvoting.web.admin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.restaurantvoting.model.User;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUsersController {

    public List<User> getAll(){
return null;
    }
}
