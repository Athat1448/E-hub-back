package athat.ehubback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.User;
import athat.ehubback.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws ResponseStatusException{
        String message = userService.login(user.getUsername(), user.getPassword());
        return message;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws ResponseStatusException {
        User createdUser = userService.register(user.getUsername(), user.getPassword());
        return createdUser;
    }

}