package athat.ehubback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.dto.LoginDto;
import athat.ehubback.model.User;
import athat.ehubback.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginDto login(@RequestBody User user) throws ResponseStatusException{
        LoginDto loginDto = userService.login(user.getUsername(), user.getPassword());
        return loginDto;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String register(@RequestBody User user) throws ResponseStatusException {
        String token = userService.register(user.getUsername(), user.getPassword());
        return token;
    }

    @PostMapping("/validate")
    public String validateUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String role = userService.validateUser(token);
        return role;
    }

}