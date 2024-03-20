package se.ju23.typespeeder.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public boolean createUser(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String inGameName) {
        return userService.createUser(username, password, inGameName);
    }

    @PostMapping("/update")
    public User updateUser(@RequestParam Long id,
                           @RequestParam String newPassword,
                           @RequestParam String newInGameName) {
        return userService.updateUser(id, newPassword, newInGameName);
    }
}
